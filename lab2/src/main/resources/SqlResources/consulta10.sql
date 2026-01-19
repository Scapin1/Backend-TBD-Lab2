/*
 Vista materializada que resume el inventario por tienda, calculando:
 - El valor total del inventario (suma del stock * precio de cada producto)
 - La cantidad de productos únicos en cada tienda
 
 Una vista materializada almacena físicamente los resultados de la consulta,
 mejorando el rendimiento en consultas frecuentes a costa de requerir
 actualización manual cuando cambian los datos subyacentes.
*/

CREATE MATERIALIZED VIEW IF NOT EXISTS resumen_stock_tienda AS
SELECT
    t.id_store AS id_tienda,
    t.name_store AS nombre_tienda,
    SUM(i.stock_inventory * p.price) AS valor_total_inventario,
    /*
    .- SUM(i.stock_inventory * p.price): Calcula el valor total del inventario
      multiplicando la cantidad de stock por el precio unitario de cada producto
      y sumando todos los resultados.
    */
    COUNT(DISTINCT i.id_productin) AS productos_unicos
    /*
    .- COUNT(DISTINCT i.id_productin): Cuenta la cantidad de productos diferentes
      que tiene cada tienda en su inventario, sin contar duplicados.
    */
FROM stores t
         JOIN inventory i ON t.id_store = i.id_storein
         /*
         .- JOIN con inventory: Relaciona cada tienda con su inventario.
         */
         JOIN products p ON i.id_productin = p.id_product
         /*
         .- JOIN con products: Obtiene el precio de cada producto para calcular
           el valor total del inventario.
         */
GROUP BY t.id_store, t.name_store;
/*
.- GROUP BY: Agrupa los resultados por tienda para obtener métricas agregadas.
*/

-- Crear índice único para permitir actualizaciones concurrentes
CREATE UNIQUE INDEX idx_resumen_stock_tienda ON resumen_stock_tienda (id_tienda, nombre_tienda);
/*
.- CREATE UNIQUE INDEX: Crea un índice único que permite usar REFRESH CONCURRENTLY.
  El índice es necesario porque PostgreSQL requiere una clave única para poder
  actualizar la vista materializada sin bloquear las consultas de lectura.
*/

/*
 Para actualizar los datos de la vista materializada cuando cambien los inventarios,
 productos o precios, ejecutar:
*/
REFRESH MATERIALIZED VIEW CONCURRENTLY resumen_stock_tienda;
/*
.- REFRESH MATERIALIZED VIEW CONCURRENTLY: Actualiza la vista materializada
  sin bloquear las consultas de lectura. Requiere que exista al menos un índice
  único en la vista materializada (creado anteriormente).
  .- CONCURRENTLY: Permite que otras consultas lean la vista mientras se actualiza,
    aunque puede tomar más tiempo que una actualización bloqueante.
*/

/*
 Para consultar los datos de la vista materializada:
*/
SELECT * FROM resumen_stock_tienda;
/*
.- La vista materializada se consulta como una tabla normal, pero con la ventaja
  de que los datos están pre-calculados, lo que hace las consultas mucho más rápidas
  que calcular estos valores sobre la marcha desde las tablas base.
*/