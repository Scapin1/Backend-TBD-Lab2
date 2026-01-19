package com.example.demo.repositories;

import com.example.demo.entities.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InventoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Inventory> rowMapper = (rs, rowNum) -> {
        Inventory inventory = new Inventory();
        inventory.setId_storein(rs.getLong("id_storein"));
        inventory.setId_productin(rs.getLong("id_productin"));
        inventory.setStock_inventory(rs.getInt("stock_inventory"));
        return inventory;
    };  

    public List<Inventory> findAll() {
        String sql = "SELECT * FROM inventory";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Inventory> findByIds(Long id_storein, Long id_productin) {
        String sql = "SELECT * FROM inventory WHERE id_storein = ? AND id_productin = ?";
        try {
            Inventory inventory = jdbcTemplate.queryForObject(sql, rowMapper, id_storein, id_productin);
            return Optional.ofNullable(inventory);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int save(Inventory inventory) {
        String sql = "INSERT INTO inventory (id_storein, id_productin, stock_inventory) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                inventory.getId_storein(),
                inventory.getId_productin(),
                inventory.getStock_inventory());
    }

    public int update(Inventory inventory) {
        String sql = "UPDATE inventory SET stock_inventory = ? WHERE id_storein = ? AND id_productin = ?";
        return jdbcTemplate.update(sql,
                inventory.getStock_inventory(),
                inventory.getId_storein(),
                inventory.getId_productin());
    }

    public int delete(Long id_storein, Long id_productin) {
        String sql = "DELETE FROM inventory WHERE id_storein = ? AND id_productin = ?";
        return jdbcTemplate.update(sql, id_storein, id_productin);
    }

    public List<Inventory> findByStore(Long id_storein) {
        String sql = "SELECT * FROM inventory WHERE id_storein = ?";
        return jdbcTemplate.query(sql, rowMapper, id_storein);
    }

    public List<Inventory> findByProduct(Long id_productin) {
        String sql = "SELECT * FROM inventory WHERE id_productin = ?";
        return jdbcTemplate.query(sql, rowMapper, id_productin);
    }

    public List<Long> findProductIdsByStore(Long id_storein) {
        String sql = "SELECT id_productin FROM inventory WHERE id_storein = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id_productin"), id_storein);
    }
}
