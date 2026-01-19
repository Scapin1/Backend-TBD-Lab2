package com.example.demo.repositories;

import com.example.demo.Dtos.Roles;
import com.example.demo.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Users> rowMapper = (rs, rowNum) -> {
        Users user = new Users();
        user.setId_user(rs.getLong("id_user"));
        user.setName_user(rs.getString("name_user"));
        user.setEmail_user(rs.getString("email_user"));
        user.setPassword_user(rs.getString("password_user"));

        String roleStr = rs.getString("role");
        Roles role = Roles.valueOf(roleStr.toUpperCase());
        user.setRole(role);

        Long storeU_id = rs.getLong("id_storeU");
        user.setStoreU_id(storeU_id);


        return user;
    };

    public List<Users> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Users findById(Long id) {
        String sql = "SELECT * FROM users WHERE id_user = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Optional<Users> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email_user = ?";
        try {
            Users user = jdbcTemplate.queryForObject(sql, rowMapper, email);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int save(Users user) {
        String sql = "INSERT INTO users (name_user, email_user, password_user, role, id_storeU) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, user.getName_user(), user.getEmail_user(), user.getPassword_user(), user.getRole().name(), user.getStoreU_id());
    }

    public int update(Users user) {
        String sql = "UPDATE users SET name_user = ?, email_user = ?, password_user = ?, role = ? WHERE id_user = ?";
        return jdbcTemplate.update(sql, user.getName_user(), user.getEmail_user(), user.getPassword_user(), user.getRole().name(), user.getId_user());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM users WHERE id_user = ?";
        return jdbcTemplate.update(sql, id);
    }


}
