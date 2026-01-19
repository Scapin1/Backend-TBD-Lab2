package com.example.demo.repositories;

import com.example.demo.entities.DistributionCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DistributionCenterRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<DistributionCenter> rowMapper = (rs, rowNum) -> {
        DistributionCenter center = new DistributionCenter();
        center.setId_center(rs.getInt("id_center"));
        center.setName_center(rs.getString("name_center"));
        // Assuming implicit conversion or driver handling for Geography to String
        try {
            center.setLocation(rs.getString("location"));
        } catch (Exception e) {
            center.setLocation(null);
        }
        return center;
    };

    public List<DistributionCenter> findAll() {
        String sql = "SELECT id_center, name_center, location FROM distribution_centers";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public DistributionCenter findById(Integer id) {
        String sql = "SELECT id_center, name_center, location FROM distribution_centers WHERE id_center = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public int save(DistributionCenter center) {
        String sql = "INSERT INTO distribution_centers (name_center, location) VALUES (?, ?)";
        return jdbcTemplate.update(sql, center.getName_center(), center.getLocation());
    }

    public int update(DistributionCenter center) {
        String sql = "UPDATE distribution_centers SET name_center = ?, location = ? WHERE id_center = ?";
        return jdbcTemplate.update(sql, center.getName_center(), center.getLocation(), center.getId_center());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM distribution_centers WHERE id_center = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * Finds the nearest Distribution Center to a given point.
     * 
     * @param lat Latitude
     * @param lon Longitude
     * @return Nearest DistributionCenter
     */
    public DistributionCenter findNearest(double lat, double lon) {
        String sql = "SELECT id_center, name_center, location FROM distribution_centers " +
                "ORDER BY location <-> ST_SetSRID(ST_MakePoint(?, ?), 4326) ASC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, rowMapper, lon, lat);
    }
}
