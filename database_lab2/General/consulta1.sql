
WITH 
last_quarter_sales AS (
/* 
.- DATE_TRUNC devuelve la fecha inicial del trimestre actual
.- quarter, devide el año en trimestres
*/
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
/*
.- JOIN LATERAL (propio de PostgreSQL)
Permite que una subconsulta acceda a columnas de la fila actual de la tabla principal en FROM.
Esto es útil cuando necesitas que la subconsulta dependa de cada fila externa, por ejemplo,
para obtener la recepción más reciente de un producto antes de su venta.

.-LIMIT define una cantidad especifica de resultados, en este caso se busca la recepcion mas 
actual y con la venta del producto en la misma tienda, por lo que se tendria solo un resultado.

.-ON TRUE funciona como la cláusula ON en un JOIN, pero sin una condición explícita.
Se usa cuando toda la lógica de filtrado ya está dentro de una subconsulta.
*/
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
    SELECT 
        v.id_product,
        v.sale_date,
        r.receipt_date,
        (v.sale_date - r.receipt_date) AS days_in_inventory
    FROM receipt_per_sale r
    JOIN last_quarter_sales v 
      ON r.id_product = v.id_product AND r.sale_date = v.sale_date
)
SELECT 
    p.name_product,
    ROUND(AVG(r.days_in_inventory)) AS average_days_in_inventory
	/*ROUND redondea el resultado del promedio*/
FROM turnover r
JOIN Products p ON p.id_product = r.id_product
GROUP BY p.name_product
ORDER BY average_days_in_inventory ASC;
