/*
 Muestra los 5 productos más vendidos y lista las ventas de CADA tienda en filas separadas.
*/

-- 1. CTE: Top 5 Global Products
WITH CTE_Top5_Products AS (
    SELECT id_product, SUM(amount_product) AS global_total_sold
    FROM Transactions
    WHERE type_transaction = 'Sale'
    GROUP BY id_product
    ORDER BY global_total_sold DESC
    LIMIT 5
    ),

-- 2. CTE: "Rejilla" de "Todos los Top 5" x "Todas las Tiendas"
    CTE_Grid AS (
SELECT s.id_store, s.name_store, p.id_product, p.global_total_sold
FROM Stores s
    CROSS JOIN CTE_Top5_Products p -- Combina cada tienda con cada producto del top 5
    )

-- 3. Se une la rejilla con las ventas reales
SELECT
    p.name_product AS "top_5_product",
    g.name_store AS "name_store",
    g.name_store AS "id_store",
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
    g.global_total_sold DESC, -- Ordena por el ranking global
    g.name_store; -- Ordena alfabéticamente las tiendas