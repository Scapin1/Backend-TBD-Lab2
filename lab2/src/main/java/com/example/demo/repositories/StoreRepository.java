package com.example.demo.repositories;

import com.example.demo.Dtos.SummaryStockStore;
import com.example.demo.entities.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoreRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Stores> rowMapper = (rs, rowNum) -> {
        Stores store = new Stores();
        store.setId_store(rs.getLong("id_store"));
        store.setName_store(rs.getString("name_store"));
        store.setCity_store(rs.getString("city_store"));
        // Assuming direct mapping or implicit string conversion
        try {
            store.setAddress_store(rs.getString("address_store"));
        } catch (Exception e) {
            store.setAddress_store(null);
        }
        return store;
    };

    /**
     * Encuentra la tienda más cercana a una ubicación dada.
     * 
     * @param lat Latitud
     * @param lon Longitud
     * @return La tienda más cercana
     */
    public Stores findNearestStore(double lat, double lon) {
        // Usa direction_store que es la columna en DB
        String sql = "SELECT id_store, name_store, city_store, address_store " +
                "FROM stores " +
                "ORDER BY address_store <-> ST_SetSRID(ST_MakePoint(?, ?), 4326) ASC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, rowMapper, lon, lat);
    }

    // Finders

    public List<Stores> findAll() {
        String sql = "SELECT id_store, name_store, city_store, address_store FROM stores";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Stores> findByCity(String city) {
        String sql = "SELECT id_store, name_store, city_store, address_store FROM stores WHERE city_store = ?";
        return jdbcTemplate.query(sql, rowMapper, city);
    }

    public Stores findById(Long id_store) {
        String sql = "SELECT id_store, name_store, city_store, address_store FROM stores WHERE id_store = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id_store);
    }

    public Stores findByName(String name) {
        String sql = "SELECT id_store, name_store, city_store, address_store FROM stores WHERE name_store = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, name);
    }

    public Stores findByAddress(String address) {
        // Buscamos por coincidencia directa (assuming DB casts string to
        // geography/geometry)
        String sql = "SELECT id_store, name_store, city_store, address_store FROM stores WHERE address_store = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, address);
        } catch (Exception e) {
            return null;
        }
    }

    // Create

    public int save(Stores store) {
        // Removing explicit function call. Assuming driver/DB handles the parameter.
        String sql = "INSERT INTO stores (name_store, address_store, city_store) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, store.getName_store(), store.getAddress_store(), store.getCity_store());
    }

    // Update

    public int update(Stores store) {
        String sql = "UPDATE stores SET name_store = ?, address_store = ?, city_store = ? WHERE id_store = ?";
        return jdbcTemplate.update(sql, store.getName_store(), store.getAddress_store(), store.getCity_store(),
                store.getId_store());
    }

    // Delete

    public int delete(Long id) {
        String sql = "DELETE FROM stores WHERE id_store = ?";
        return jdbcTemplate.update(sql, id);
    }

    // consulta 10

    public void refreshResumenStockTienda() {
        String sql = "REFRESH MATERIALIZED VIEW CONCURRENTLY resumen_stock_tienda";
        jdbcTemplate.execute(sql);
    }

    public List<SummaryStockStore> getResumenStockTienda() {
        String sql = "SELECT * FROM resumen_stock_tienda";

        RowMapper<SummaryStockStore> rowMapper = (rs, rowNum) -> {
            SummaryStockStore summary = new SummaryStockStore();
            summary.setIdTienda(rs.getLong("id_tienda"));
            summary.setNameStore(rs.getString("nombre_tienda"));
            summary.setTotalPriceInventory(rs.getDouble("valor_total_inventario"));
            summary.setUniqueProduct(rs.getInt("productos_unicos"));
            return summary;
        };

        return jdbcTemplate.query(sql, rowMapper);
    }

    // Spatial Analysis

    /**
     * Calculates the distance in kilometers between two stores.
     */
    public Double calculateDistance(Long storeId1, Long storeId2) {
        String sql = "SELECT ST_Distance(" +
                "(SELECT address_store FROM stores WHERE id_store = ?), " +
                "(SELECT address_store FROM stores WHERE id_store = ?)" +
                ")/1000 AS distance_km";
        return jdbcTemplate.queryForObject(sql, Double.class, storeId1, storeId2);
    }

    /**
     * Finds stores that overlap with a buffer of radius km around the center store.
     * 
     */
    public List<Stores> findStoresWithinBuffer(Long centerStoreId, double radiusKm) {
        // radiusKm * 1000 to convert to meters
        String sql = "SELECT id_store, name_store, city_store, address_store FROM stores " +
                "WHERE id_store != ? AND " +
                "ST_DWithin(" +
                "   address_store, " +
                "   (SELECT address_store FROM stores WHERE id_store = ?), " +
                "   ? * 1000" +
                ")";
        return jdbcTemplate.query(sql, rowMapper, centerStoreId, centerStoreId, radiusKm);
    }

    /**
     * Finds the nearest store that has stock of a specific product.
     * 
     * @param targetStoreId The store requesting stock
     * @param productId     The product needed
     * @param quantity      The logical quantity needed (usually > 0)
     * @return Nearest Store with stock
     */
    public Stores findNearestWithStock(Long targetStoreId, Long productId, int quantity) {
        String sql = "SELECT s.id_store, s.name_store, s.city_store, s.address_store " +
                "FROM stores s " +
                "JOIN inventory i ON s.id_store = i.id_storein " +
                "WHERE i.id_productin = ? AND i.stock_inventory >= ? AND s.id_store != ? " +
                "ORDER BY s.address_store <-> (SELECT address_store FROM stores WHERE id_store = ?) ASC " +
                "LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, productId, quantity, targetStoreId, targetStoreId);
        } catch (Exception e) {
            return null;
        }
    }

}
