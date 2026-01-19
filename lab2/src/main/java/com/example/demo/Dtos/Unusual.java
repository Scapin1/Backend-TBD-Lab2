package com.example.demo.Dtos;

public class Unusual {
    private String product_name;
    private Long id_store;
    private String store_name;
    private Integer amount_product;

    public Unusual(Long id_store, String store_name, String product_name, int amount_product) {
        this.id_store = id_store;
        this.store_name = store_name;
        this.product_name = product_name;
        this.amount_product = amount_product;
    }

    //Getters
    public String getProduct_name() {
        return product_name;
    }
    public String getStore_name() {
        return store_name;
    }
    public int getAmount_product() {return amount_product;}
    public Long getId_store() {return id_store;}

    //Setters
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
    public void setAmount_product(int amount_product) {this.amount_product = amount_product; }
    public void setId_store(Long id_store) {this.id_store = id_store;}


}
