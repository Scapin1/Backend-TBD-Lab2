/*
 Consulta para mostrar al proveedor con más productos vendidos durante el MES ANTERIOR.
*/
WITH Sales_Last_Month AS (
    SELECT
        id_product,
        amount_product
    FROM
        Transactions
    WHERE
        type_transaction = 'Sale'

      -- 'date_trunc' trunca la fecha al inicio del período (mes).
      -- 'NOW()' es la fecha y hora actual.
      -- 'interval '1 month'' es un mes calendario.

      -- Se consideran ventas posteriores a la media noche del primer día del mes pasado.
      AND date_transaction >= date_trunc('month', NOW() - interval '1 month')

      -- y que sean anteriores a la media noche del primer día de este mes.
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