package com.example.demo.repositories;


import com.example.demo.Dtos.Roles;
import com.example.demo.Dtos.TransactionsByStore;
import com.example.demo.Dtos.TransactionsTipes;
import com.example.demo.Dtos.Unusual;
import com.example.demo.entities.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class TransactionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Transactions> rowMapper = (rs, rowNum) -> {
        Transactions transaction = new Transactions();
        transaction.setId_transaction(rs.getLong("id_transaction"));
        String typeStr = rs.getString("type_transaction");
        TransactionsTipes type = TransactionsTipes.valueOf(typeStr);
        transaction.setType_transaction(type);

        transaction.setDate_transaction(rs.getDate("date_transaction"));
        transaction.setAmount_product(rs.getInt("amount_product"));
        transaction.setId_storeDE(rs.getLong("id_storede"));
        transaction.setId_storeOR(rs.getLong("id_storeor"));
        transaction.setId_product(rs.getLong("id_product"));
        return transaction;
    };

    public List<Transactions> findAll() {
        String sql = "SELECT * FROM transactions";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Transactions> findByType_transaction(String type_transaction) {
        String sql = "SELECT * FROM transactions WHERE type_transaction = ?";
        return jdbcTemplate.query(sql, rowMapper, type_transaction);
    }

    public List<Transactions> findByDate_transaction(Date date_transaction) {
        String sql = "SELECT * FROM transactions WHERE date_transaction = ?";
        return jdbcTemplate.query(sql, rowMapper, date_transaction);
    }

    public Transactions findById(Long id) {
        String sql = "SELECT * FROM transactions WHERE id_transaction = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Transactions> findById_Product(Long id_product) {
        String sql = "SELECT * FROM transactions WHERE id_product = ?";
        return jdbcTemplate.query(sql, rowMapper, id_product);
    }

    public List<TransactionsByStore> findById_store(Long storeID) {
        String sql = "SELECT " +
                "t.id_transaction, " +
                "t.type_transaction, " +
                "t.date_transaction, " +
                "t.amount_product, " +
                "p.name_product, " +
                "t.id_storeor, " +
                "s_or.name_store AS origin_name, " +
                "t.id_storede, " +
                "s_de.name_store AS dest_name " +
                "FROM transactions t " +
                "JOIN products p ON t.id_product = p.id_product " +
                "LEFT JOIN stores s_or ON t.id_storeor = s_or.id_store " +
                "LEFT JOIN stores s_de ON t.id_storede = s_de.id_store " +
                "WHERE t.id_storeor = ? OR t.id_storede = ? " +
                "ORDER BY t.date_transaction DESC";
        RowMapper<TransactionsByStore> transactions = (rs, rowNum) -> {
            TransactionsByStore transactionsByStore = new TransactionsByStore();
            transactionsByStore.setIdTransaction(rs.getLong("id_transaction"));
            transactionsByStore.setTypeTransaction(rs.getString("type_transaction"));
            transactionsByStore.setDateTransaction(rs.getDate("date_transaction"));
            transactionsByStore.setAmountProduct(rs.getInt("amount_product"));
            transactionsByStore.setNameProduct(rs.getString("name_product"));

            // Manejo de nulos
            String origen = rs.getString("origin_name");
            String destino = rs.getString("dest_name");
            transactionsByStore.setNameStoreOR(origen != null ? origen : "Externo");
            transactionsByStore.setNameStoreDE(destino != null ? destino : "Externo");

            // Lógica de Flujo: Comparar IDs
            long idOrigin = rs.getLong("id_storeor");
            long idDestiny = rs.getLong("id_storede");

            if (idDestiny == storeID) {
                transactionsByStore.setFlow("ENTRADA (+)");
            } else if (idOrigin == storeID) {
                transactionsByStore.setFlow("SALIDA (-)");
            } else {
                transactionsByStore.setFlow("NEUTRO");
            }
            return transactionsByStore;
        };

        return jdbcTemplate.query(sql, transactions, storeID, storeID);
    }

    public List<Transactions> findById_storeOR(Long id_storeOR) {
        String sql = "SELECT * FROM transactions WHERE id_storeOR = ?";
        return jdbcTemplate.query(sql, rowMapper, id_storeOR);
    }

    public List<Transactions> findById_storeDE(Long id_storeDE) {
        String sql = "SELECT * FROM transactions WHERE id_storeDE = ?";
        return jdbcTemplate.query(sql, rowMapper, id_storeDE);
    }

    public int save(Transactions transaction) {
        String sql = "INSERT INTO transactions (type_transaction, date_transaction, amount_product, id_product, id_storeOR, id_storeDE) VALUES (?, ?, ?, ?, ?, ?    )";
        return jdbcTemplate.update(sql,
                transaction.getType_transaction().toString(),
                transaction.getDate_transaction(),
                transaction.getAmount_product(),
                transaction.getId_product(),
                transaction.getId_storeOR(),
                transaction.getId_storeDE()
        );
    }

    public int update(Transactions transaction) {
        String sql = "UPDATE transactions SET type_transaction = ?, date_transaction = ?, amount_product = ? WHERE id_transaction = ?";
        return jdbcTemplate.update(sql,
                transaction.getType_transaction(),
                transaction.getDate_transaction(),
                transaction.getAmount_product());
    }

    public int delete(Long id) {
        String sql = "DELETE FROM transactions WHERE id_transaction = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Transactions> recentTransactions() {
        String sql = "SELECT * FROM transactions " +
                "WHERE" +
                "    date_transaction >= NOW() - INTERVAL '7 days'\n" +
                "ORDER BY" +
                "    date_transaction DESC;";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Unusual> unusualTransactions() {
        String sql = "SELECT stores.name_store,id_storeor, amount_product , products.name_product FROM (\n" +
                "    Select\n" +
                "        id_storeor,\n" +
                "        amount_product,\n" +
                "        id_product,\n" +
                "        ROW_NUMBER() OVER (PARTITION BY id_storeor ORDER BY amount_product DESC) as number_row\n" +
                "    FROM transactions\n" +
                "    WHERE type_transaction = 'Sale')\n" +
                "AS ranked\n" +
                "INNER JOIN stores\n" +
                "        ON ranked.id_storeor = stores.id_store\n" +
                "INNER JOIN products\n" +
                "        ON ranked.id_product = products.id_product\n" +
                "WHERE number_row <= 3;";
        RowMapper<Unusual> rowMapper = (rs, rowNum) -> new Unusual(
                rs.getLong("id_storeor"),
                rs.getString("name_store"),
                rs.getString("name_product"),
                rs.getInt("amount_product")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    public int transferInventory(Long id_product,Long id_store_origin, Long id_store_destiny, int amount_product) {
        String sql = "CALL transferir_inventario(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, id_product, id_store_origin, id_store_destiny, amount_product);
    }
}
