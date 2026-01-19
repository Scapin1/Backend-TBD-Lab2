package com.example.demo.entities;

public class Products {
    private Long id;
    private String name_product;
    private String description_product;
    private Integer price;
    private String SKU;

    // Getters
    public Long getId() { return id; }
    public String getName_product() { return name_product; }
    public String getDescription_product() { return description_product; }
    public Integer getPrice() { return price; }
    public String getSKU() { return SKU; }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setName_product(String name_product) {
        this.name_product= name_product;
    }
    public void setDescription_product(String description_product) {
        this.description_product = description_product;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public void setSKU(String SKU) {
        this.SKU = SKU;
    }
}
