package com.example.demo.services;

import com.example.demo.Dtos.*;
import com.example.demo.entities.Products;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Products> getProductBySKU(String sku) {
        return productRepository.findBySKU(sku);
    }

    public List<Products> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    public Products createProduct(Products product) {
        // Validar que el SKU no exista
        if (productRepository.findBySKU(product.getSKU()).isPresent()) {
            throw new RuntimeException("El SKU ya existe: " + product.getSKU());
        }

        // Validar datos requeridos
        if (product.getName_product() == null || product.getName_product().trim().isEmpty()) {
            throw new RuntimeException("El nombre del producto es requerido");
        }

        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new RuntimeException("El precio debe ser mayor a 0");
        }

        if (product.getSKU() == null || product.getSKU().trim().isEmpty()) {
            throw new RuntimeException("El SKU es requerido");
        }

        productRepository.save(product);
        return product;
    }

    public Products updateProduct(Products product) {
        // Validar que el producto existe
        Products existingProduct = productRepository.findById(product.getId());
        if (existingProduct == null) {
            throw new RuntimeException("Producto no encontrado con ID: " + product.getId());
        }

        // Validar que el SKU no esté duplicado (si cambió)
        if (!existingProduct.getSKU().equals(product.getSKU())) {
            if (productRepository.findBySKU(product.getSKU()).isPresent()) {
                throw new RuntimeException("El SKU ya existe: " + product.getSKU());
            }
        }

        // Validar datos requeridos
        if (product.getName_product() == null || product.getName_product().trim().isEmpty()) {
            throw new RuntimeException("El nombre del producto es requerido");
        }

        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new RuntimeException("El precio debe ser mayor a 0");
        }

        if (product.getSKU() == null || product.getSKU().trim().isEmpty()) {
            throw new RuntimeException("El SKU es requerido");
        }

        productRepository.update(product);
        return product;
    }

    public void deleteProduct(Long id) {
        Products existingProduct = productRepository.findById(id);
        if (existingProduct == null) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }

        productRepository.delete(id);
    }

    public boolean productExists(Long id) {
        try {
            productRepository.findById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public List<ninetyDays> productsWithNoMovementsIn90Days() {
        return productRepository.ProductsWithNoMovement();
    }

    /*Consulta 2*/
    public List<LowStock> totalStockLowerThan50() {return productRepository.TotalStockLowerThan50();}

    /*Consulta 3*/
    public List<Top5> top5ProductsSalesPerStore() {return productRepository.Top5GlobalProducts();}

    /*Consulta 1*/
    public List<DtoC1> lastquarter_analysis(){
        return productRepository.AverageInventoryPerQuarter();
    }

    /*Consulta 9*/
    public BestSupplierLastMonth bestSupplierLastMonth() {return productRepository.SupplierLastMonth();}

    /*Consulta 5*/
    public List<DtoC5> average_salesPerMonth(){
        return productRepository.AverageSalesPerMonth();
    }

}
