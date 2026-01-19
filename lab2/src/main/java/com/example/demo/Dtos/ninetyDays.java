package com.example.demo.Dtos;

import java.sql.Date;

public class ninetyDays {
    private String product_name;
    private String store_name;
    private int stock;
    private Date las_date;

    public ninetyDays(String product_name,  String store_name, int stock, Date las_date) {
        this.product_name = product_name;
        this.store_name = store_name;
        this.stock = stock;
        this.las_date = las_date;
    }


    //geter y setters

    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getStore_name() {
        return store_name;
    }
    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public Date getLas_date() {
        return las_date;
    }
    public void setLas_date(Date las_date) {
        this.las_date = las_date;
    }
}
