package com.example.demo.services;

import com.example.demo.entities.Inventory;
import com.example.demo.entities.Products;
import com.example.demo.repositories.InventoryRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getByIds(Long id_storein, Long id_productin) {
        return inventoryRepository.findByIds(id_storein, id_productin);
    }

    public int create(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public int update(Inventory inventory) {
        return inventoryRepository.update(inventory);
    }

    public int delete(Long id_storein, Long id_productin) {
        return inventoryRepository.delete(id_storein, id_productin);
    }

    public List<Inventory> getByStore(Long id_storein) {
        return inventoryRepository.findByStore(id_storein);
    }

    public List<Inventory> getByProduct(Long id_productin) {
        return inventoryRepository.findByProduct(id_productin);
    }

    public List<Products> getProductsByStore(Long id_storein) {
        List<Long> productIds = inventoryRepository.findProductIdsByStore(id_storein);
        List<Products> products = new ArrayList<>();

        for (Long productId : productIds) {
            try {
                Products product = productRepository.findById(productId);
                if (product != null) {
                    products.add(product);
                }
            } catch (Exception e) {
                System.err.println("⚠️ Error al obtener producto con ID " + productId + ": " + e.getMessage());
            }
        }

        return products;
    }
}
