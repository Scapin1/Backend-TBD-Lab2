-- Crear la vista materializada para la consulta 10
CREATE MATERIALIZED VIEW IF NOT EXISTS resumen_stock_tienda AS
SELECT
    t.id_store,
    t.name_store,
    SUM(i.stock_inventory * p.price) AS valor_total_inventario,
    COUNT(DISTINCT i.id_productin) AS productos_unicos
FROM stores t
         JOIN inventory i ON t.id_store = i.id_storein
         JOIN products p ON i.id_productin = p.id_product
GROUP BY t.id_store, t.name_store;

