package com.example.demo.repositories;

import com.example.demo.Dtos.*;
import com.example.demo.entities.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Products> rowMapper = (rs, rowNum) -> {
        Products product = new Products();
        product.setId(rs.getLong("id_product"));
        product.setName_product(rs.getString("name_product"));
        product.setDescription_product(rs.getString("description_product"));
        product.setPrice(rs.getInt("price"));
        product.setSKU(rs.getString("sku"));
        return product;
    };

    public List<Products> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Products findById(Long id) {
        String sql = "SELECT * FROM products WHERE id_product = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Optional<Products> findBySKU(String sku) {
        String sql = "SELECT * FROM products WHERE sku = ?";
        try {
            Products product = jdbcTemplate.queryForObject(sql, rowMapper, sku);
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int save(Products product) {
        String sql = "INSERT INTO products (name_product, description_product, price, sku) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                product.getName_product(),
                product.getDescription_product(),
                product.getPrice(),
                product.getSKU());
    }

    public int update(Products product) {
        String sql = "UPDATE products SET nombre_product = ?, descripcion_product = ?, price = ?, sku = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                product.getName_product(),
                product.getDescription_product(),
                product.getPrice(),
                product.getSKU(),
                product.getId());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM products WHERE id_product = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Products> findByNameContaining(String name) {
        String sql = "SELECT * FROM products WHERE name_product ILIKE ?";
        return jdbcTemplate.query(sql, rowMapper, "%" + name + "%");
    }

    //consulta 8 productos sin movimientos por mas de 90 dias
    public List<ninetyDays> ProductsWithNoMovement() {
        String sql = """
        SELECT
            p.name_product AS product_name,
            s.name_store AS store_name,
            i.stock_inventory AS stock,
            MAX(t.date_transaction) AS las_date
                        FROM products p
                    LEFT JOIN inventory i ON p.id_product = i.id_productIn
                    LEFT JOIN transactions t ON p.id_product = t.id_product
    				LEFT JOIN stores s ON s.id_store = i.id_storein
                    GROUP BY p.id_product, p.name_product, p.sku, s.id_store,i.stock_inventory
                    HAVING MAX(t.date_transaction) IS NULL OR MAX(t.date_transaction) < CURRENT_DATE - INTERVAL '90 days'""";

        RowMapper<ninetyDays> rowMapper = (rs, rowNum) -> new ninetyDays(
                rs.getString("product_name"),
                rs.getString("store_name"),
                rs.getInt("stock"),
                rs.getDate("las_date")
        );

        return jdbcTemplate.query(sql, rowMapper);
    }

    //consulta 2: productos con stock total < 50

    public List<LowStock> TotalStockLowerThan50() {
        String sql = """
                WITH  
                StockTotalPorProducto AS (       
                    SELECT
                        id_productIn,
                        SUM(stock_inventory) AS stock_total
                    FROM    Inventory
                    GROUP BY  id_productIn)
                SELECT
                    p.name_product,
                    st.stock_total
                FROM    StockTotalPorProducto st
                JOIN    Products p 
                    ON st.id_productIn = p.id_product
                WHERE   st.stock_total < 50; 
        """;
        RowMapper<LowStock> rowMapper = (rs, rowNum) -> new LowStock(
                rs.getString("name_product"),
                rs.getInt("stock_total")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    //consulta 3: comparación de ventas de productos top 5 entre tiendas

    public List<Top5> Top5GlobalProducts(){
        String sql = """
                WITH CTE_Top5_Products AS (
                    SELECT id_product, SUM(amount_product) AS global_total_sold
                    FROM Transactions
                    WHERE type_transaction = 'Sale'
                    GROUP BY id_product
                    ORDER BY global_total_sold DESC
                    LIMIT 5
                    ),
                
                    CTE_Grid AS (
                SELECT s.id_store, s.name_store, p.id_product, p.global_total_sold
                FROM Stores s
                    CROSS JOIN CTE_Top5_Products p 
                    )
                
                SELECT
                    p.name_product AS "top_5_product",
                    g.name_store AS "name_store",
                    g.id_store AS "id_store",
                    COALESCE((
                                 SELECT SUM(t.amount_product)
                                 FROM Transactions t
                                 WHERE t.type_transaction = 'Sale'
                                   AND t.id_product = g.id_product
                                   AND t.id_storeOR = g.id_store
                             ), 0) AS "quantity_sold"
                FROM
                    CTE_Grid g
                        JOIN
                    Products p ON g.id_product = p.id_product
                ORDER BY
                    g.global_total_sold DESC, 
                    g.name_store; 
        """;
        RowMapper<Top5> rowMapper = (rs, rowNum) -> new Top5(
                rs.getString("top_5_product"),
                rs.getString("name_store"),
                rs.getInt("quantity_sold"),
                rs.getLong("id_store")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    //consulta 1: dias inventario promedio en el ultimo trimestre

    public List<DtoC1> AverageInventoryPerQuarter() {
        String sql = """
                WITH
                last_quarter_sales AS (
                    SELECT
                        id_product,
                        date_transaction AS sale_date,
                        id_storeOR AS sale_store
                    FROM Transactions
                    WHERE type_transaction = 'Sale'
                      AND date_transaction >= DATE_TRUNC('quarter', CURRENT_DATE) - INTERVAL '3 months'
                      AND date_transaction < DATE_TRUNC('quarter', CURRENT_DATE)
                ),
                receipts AS (
                    SELECT
                        id_product,
                        date_transaction AS receipt_date,
                        id_storeDE AS receipt_store
                    FROM Transactions
                    WHERE type_transaction = 'Receipt'
                ),
                receipt_per_sale AS (
                    SELECT
                        v.id_product,
                        v.sale_date,
                        r.receipt_date
                    FROM last_quarter_sales v
                    JOIN LATERAL (
                        SELECT r.receipt_date
                        FROM receipts r
                        WHERE r.id_product = v.id_product
                          AND r.receipt_store = v.sale_store
                          AND r.receipt_date <= v.sale_date
                        ORDER BY r.receipt_date DESC
                        LIMIT 1
                    ) r ON TRUE
                ),
                turnover AS (
                    SELECT\s
                        v.id_product,
                        v.sale_date,
                        r.receipt_date,
                        (v.sale_date - r.receipt_date) AS days_in_inventory
                    FROM receipt_per_sale r
                    JOIN last_quarter_sales v\s
                      ON r.id_product = v.id_product AND r.sale_date = v.sale_date
                )
                SELECT\s
                    p.name_product,
                    ROUND(AVG(r.days_in_inventory)) AS average_days_in_inventory
                FROM turnover r
                JOIN Products p ON p.id_product = r.id_product
                GROUP BY p.name_product
                ORDER BY average_days_in_inventory ASC;
                    """;
        RowMapper<DtoC1> rowMapper = (rs, rowNum) -> new DtoC1(
                rs.getString("name_product"),
                rs.getInt("average_days_in_inventory")
        );

        return jdbcTemplate.query(sql, rowMapper);
    }

    //consulta 9: proveedor del cual más productos fueron vendidos en el último mes

    public BestSupplierLastMonth  SupplierLastMonth() {
        String sql = """
                WITH Sales_Last_Month AS (
                                  SELECT
                                      id_product,
                                      amount_product
                                  FROM
                                      Transactions
                                  WHERE
                                      type_transaction = 'Sale'
                                    AND date_transaction >= date_trunc('month', NOW() - interval '1 month')
                                    AND date_transaction < date_trunc('month', NOW())
                              )
                              SELECT
                                  s.supplier_name AS "name_supplier",
                                  SUM(slm.amount_product) AS "total_products_sold_last_month"
                              FROM
                                  Sales_Last_Month slm
                                      JOIN
                                  Supplier_Product sp ON slm.id_product = sp.product_idP
                                      JOIN
                                  Supplier s ON sp.supplier_idP = s.supplier_id
                              GROUP BY
                                  s.supplier_id, s.supplier_name
                              ORDER BY
                                  "total_products_sold_last_month" DESC
                                  LIMIT 1;
        """;
        RowMapper<BestSupplierLastMonth> rowMapper = (rs, rowNum) -> new BestSupplierLastMonth(
                rs.getString("name_supplier"),
                rs.getInt("total_products_sold_last_month")
        );
        return jdbcTemplate.query(sql, rowMapper)
                .stream()
                .findFirst()
                .orElse(null);

    }


    //consulta 5: promedio de ventas diarias en el último mes

    public List<DtoC5> AverageSalesPerMonth() {
        String sql = """ 
        WITH 
            daily_sales AS (
            SELECT
            DATE_TRUNC('day', date_transaction) AS date,
            EXTRACT(MONTH FROM date_transaction) AS month,
            EXTRACT(YEAR FROM date_transaction) AS year,
            SUM(amount_product) AS total_sales_day
        FROM Transactions
        WHERE type_transaction = 'Sale'
            AND date_transaction >= CURRENT_DATE - INTERVAL '1 year'
        GROUP BY date, month, year
        ),
            monthly_sales AS (
            SELECT
                year,
                month,
                SUM(total_sales_day) AS total_sales_month,
                EXTRACT(DAY FROM DATE_TRUNC('month', MAKE_DATE(year::int, month::int, 1)) + INTERVAL '1 month - 1 day') AS days_in_month
            FROM daily_sales
            GROUP BY year, month
            ),
                sales_with_difference AS (
                SELECT
                    TO_CHAR(TO_DATE(month::text, 'MM'), 'Month') AS month_name,
                    month,
                    year,
                    ROUND(total_sales_month / days_in_month, 2) AS average_daily_sales,
                    LAG(ROUND(total_sales_month / days_in_month, 2)) OVER (ORDER BY year, month) AS previous_month_avg,
                    ROUND((total_sales_month / days_in_month) - LAG(total_sales_month / days_in_month) OVER (ORDER BY year, month), 2) AS difference
                FROM monthly_sales)
        SELECT
            month_name,
            year,
            average_daily_sales,
            difference AS difference_from_previous_month
        FROM sales_with_difference
        ORDER BY year, month;
                    """;
        RowMapper<DtoC5> rowMapper = (rs, rowNum) -> new DtoC5(
                rs.getString("month_name"),
                rs.getInt("year"),
                rs.getInt("average_daily_sales"),
                rs.getInt("difference_from_previous_month")
        );

        return jdbcTemplate.query(sql, rowMapper);
    }

}
