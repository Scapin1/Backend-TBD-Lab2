package com.example.demo.controllers;

import com.example.demo.Dtos.*;
import com.example.demo.entities.Products;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
        Products product = productService.getProductById(id);
        return ResponseEntity.ok(product);

    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<Products> getProductBySKU(@PathVariable String sku) {
        Optional<Products> product = productService.getProductBySKU(sku);
        return ResponseEntity.ok(product.get());

    }

    @GetMapping("/search")
    public ResponseEntity<List<Products>> searchProductsByName(@RequestParam String name) {
        List<Products> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);

    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Products product) {
        Products createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Products product) {
        product.setId(id);
        Products updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> productExists(@PathVariable Long id) {
        boolean exists = productService.productExists(id);
        return ResponseEntity.ok(exists);
    }
    @GetMapping("/NoMovements")
    public ResponseEntity<List<ninetyDays>> productsWithNoMovementsIn90Days() {
        List<ninetyDays> products = productService.productsWithNoMovementsIn90Days();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/LowStock")
    public ResponseEntity<List<LowStock>> getLowStock() {
        List<LowStock> products = productService.totalStockLowerThan50();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/Top5")
    public ResponseEntity<List<Top5>> getTop5Products() {
        List<Top5> products = productService.top5ProductsSalesPerStore();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/BestSupplierLastMonth")
    public ResponseEntity<BestSupplierLastMonth> getBestSupplierLastMonth() {
        BestSupplierLastMonth bestSupplierLastMonth = productService.bestSupplierLastMonth();
        return ResponseEntity.ok(bestSupplierLastMonth);
    }

    @GetMapping("/AverageDaysInventory")
    public ResponseEntity<List<DtoC1>> averageDaysInventory() {
        List<DtoC1> products = productService.lastquarter_analysis();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/AverageDaySalesPerMonth")
    public ResponseEntity<List<DtoC5>> averageDaySalesPerMonth() {
        List<DtoC5> products = productService.average_salesPerMonth();
        return ResponseEntity.ok(products);
    }
}
