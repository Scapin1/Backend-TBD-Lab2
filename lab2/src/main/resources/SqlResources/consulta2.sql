WITH StockTotalPorProducto AS (
    -- 1. CTE: Calcula el stock total para CADA producto sumando el inventario de todas las tiendas
    SELECT
        id_productIn,
        SUM(stock_inventory) AS stock_total
    FROM
        Inventory
    GROUP BY
        id_productIn
)
-- 2. Consulta final: Une la CTE con la tabla de Productos
SELECT
    p.name_product,
    st.stock_total
FROM
    StockTotalPorProducto st
        JOIN
    Products p ON st.id_productIn = p.id_product
WHERE
    st.stock_total < 50; -- Filtra sólo aquellos productos con menos de 50 unidades