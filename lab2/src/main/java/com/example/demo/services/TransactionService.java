package com.example.demo.services;

import com.example.demo.Dtos.TransactionsByStore;
import com.example.demo.Dtos.Transfer;
import com.example.demo.Dtos.Unusual;
import com.example.demo.entities.Transactions;
import com.example.demo.entities.Users;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private Transfer transfer;

    // Finders
    public List<Transactions> getAllTransactions() { return transactionRepository.findAll(); }
    public Transactions getTransactionById(Long id_transaction) { return transactionRepository.findById( id_transaction ); }
    public List<Transactions> getTransactionsByType(String type_transaction) { return transactionRepository.findByType_transaction(type_transaction) ; }
    public List<Transactions> getTransactionsByDate(Date date_transaction) { return transactionRepository.findByDate_transaction(date_transaction) ; }
    public List<Transactions> getTransactionsByIDProduct(Long id_product) {return transactionRepository.findById_Product(id_product);}
    public List<TransactionsByStore> getTransactionsByIDStore(Long id_store) {return transactionRepository.findById_store(id_store);}
    public List<Transactions> getTransactionsByIDStoreOR(Long id_storeOR) {return transactionRepository.findById_storeOR(id_storeOR);}
    public List<Transactions> getTransactionsByIDStoreDE(Long id_storeDE) {return transactionRepository.findById_storeDE(id_storeDE);}
    public List<Transactions> getRecentTransactions() {return transactionRepository.recentTransactions();}

    // Create
    public int createTransaction(Transactions transaction) { return transactionRepository.save(transaction); }

    // Update
    public int updateTransaction(Transactions transaction) { return transactionRepository.update(transaction); }

    // Delete
    public int deleteTransaction(Long id_transaction) { return transactionRepository.delete(id_transaction); }

    // Others
    public List<Unusual> unusualTransaction(){
        return transactionRepository.unusualTransactions();
    }

    public int transferInventory(Transfer transfer) {
        return transactionRepository.transferInventory(
                transfer.getId_product(),
                transfer.getId_storeOR(),
                transfer.getId_storeDE(),
                transfer.getAmount_product());
    }

}
