package com.example.demo.Dtos;

public class LowStock {
    private String name_product;
    private int stock_total;

    public LowStock(String name_product, int stock_total) {
        this.name_product = name_product;
        this.stock_total = stock_total;
    }

    // Getters

    public String getName_product() {return name_product;}
    public int getStock_total() {return stock_total;}

    //Setters

    public void setName_product(String name_product) {this.name_product = name_product;}
    public void setStock_total(int stock_total) {this.stock_total = stock_total;}
}
