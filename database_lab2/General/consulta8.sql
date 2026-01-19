/*
 Consulta para identificar productos que no han tenido ningún tipo de movimiento
 (venta, recepción o transferencia) en los últimos 90 días.
 
 Útil para detectar productos obsoletos o con baja rotación que pueden requerir
 acciones de marketing, descuentos o evaluación de stock.
*/

SELECT
    p.name_product AS product_name,
    s.name_store AS store_name,
    i.stock_inventory AS stock,
    MAX(t.date_transaction) AS las_date
    /*
    .- MAX(t.date_transaction): Obtiene la fecha de la última transacción del producto.
      Si el producto no tiene transacciones, devuelve NULL.
    */
FROM products p
         LEFT JOIN inventory i ON p.id_product = i.id_productIn
         /*
         .- LEFT JOIN con inventory: Permite incluir todos los productos,
           incluso si no tienen registro en el inventario de alguna tienda.
         */
         LEFT JOIN transactions t ON p.id_product = t.id_product
         /*
         .- LEFT JOIN con transactions: Permite incluir productos sin transacciones,
           que son candidatos a productos obsoletos.
         */
         LEFT JOIN stores s ON s.id_store = i.id_storein
         /*
         .- LEFT JOIN con stores: Obtiene el nombre de la tienda donde está el producto.
         */
GROUP BY p.id_product, p.name_product, p.sku, s.id_store, i.stock_inventory
/*
.- GROUP BY: Agrupa por producto y tienda para obtener el stock y última fecha
  de transacción por cada combinación producto-tienda.
*/
HAVING MAX(t.date_transaction) IS NULL OR MAX(t.date_transaction) < CURRENT_DATE - INTERVAL '90 days'
/*
.- HAVING: Filtra los resultados después del GROUP BY.
  .- MAX(t.date_transaction) IS NULL: Incluye productos que nunca han tenido transacciones.
  .- MAX(t.date_transaction) < CURRENT_DATE - INTERVAL '90 days': Incluye productos cuya
    última transacción fue hace más de 90 días.
  .- INTERVAL '90 days': Resta 90 días a la fecha actual para obtener el umbral temporal.
*/