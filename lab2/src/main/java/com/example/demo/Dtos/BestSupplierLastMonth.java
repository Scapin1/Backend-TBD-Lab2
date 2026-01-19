package com.example.demo.Dtos;

public class BestSupplierLastMonth {
    private String name_supplier;
    private int total_products_sold_last_month;

    public BestSupplierLastMonth(String name_supplier, int total_products_sold_last_month) {
        this.name_supplier = name_supplier;
        this.total_products_sold_last_month = total_products_sold_last_month;
    }

    //Getters

    public String getName_supplier() {return name_supplier;}
    public int getTotal_products_sold_last_month() {return total_products_sold_last_month;}

    //Setters

    public void setName_supplier(String name_supplier) {this.name_supplier = name_supplier;}
    public void setTotal_products_sold_last_month(int total_products_sold_last_month) {this.total_products_sold_last_month = total_products_sold_last_month;}
}
