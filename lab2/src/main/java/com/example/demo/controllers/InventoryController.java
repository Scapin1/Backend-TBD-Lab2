package com.example.demo.controllers;

import com.example.demo.entities.Inventory;
import com.example.demo.entities.Products;
import com.example.demo.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAll() {
        return inventoryService.getAll();
    }

    @GetMapping("/store/{id_storein}")
    public List<Inventory> getByStore(@PathVariable Long id_storein) {
        return inventoryService.getByStore(id_storein);
    }

    @GetMapping("/product/{id_productin}")
    public List<Inventory> getByProduct(@PathVariable Long id_productin) {
        return inventoryService.getByProduct(id_productin);
    }

    @GetMapping("/{id_storein}/{id_productin}")
    public Inventory getByIds(@PathVariable Long id_storein, @PathVariable Long id_productin) {
        return inventoryService.getByIds(id_storein, id_productin).orElse(null);
    }

    @PostMapping("/create")
    public int createInventory(@RequestBody Inventory inventory) {
        return inventoryService.create(inventory);
    }

    @PutMapping("/update")
    public int updateInventory(@RequestBody Inventory inventory) {
        return inventoryService.update(inventory);
    }

    @DeleteMapping("/delete/{id_storein}/{id_productin}")
    public int deleteInventory(@PathVariable Long id_storein, @PathVariable Long id_productin) {
        return inventoryService.delete(id_storein, id_productin);
    }

    @GetMapping("/store/{id_storein}/products")
    public List<Products> getProductsByStore(@PathVariable Long id_storein) {
        return inventoryService.getProductsByStore(id_storein);
    }
}
