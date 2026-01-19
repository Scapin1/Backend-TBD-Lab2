package com.example.demo.Dtos;

public class Top5 {
    private String top_5_product;
    private String name_store;
    private int quantity_sold;
    private Long id_store;

    public Top5(String top_5_product, String name_store, int quantity_sold, long id_store) {
        this.top_5_product = top_5_product;
        this.name_store = name_store;
        this.quantity_sold = quantity_sold;
        this.id_store = id_store;
    }

    //Getters
    public String getTop_5_product() {return top_5_product;}
    public String getName_store() {return name_store;}
    public int getQuantity_sold() {return quantity_sold;}
    public long getId_store() {return id_store;}

    //Setters
    public void setTop_5_product(String top_5_product) {this.top_5_product = top_5_product;}
    public void setName_store(String name_store) {this.name_store = name_store;}
    public void setQuantity_sold(int quantity_sold) {this.quantity_sold = quantity_sold;}
    public void setId_store(long id_store) {this.id_store = id_store;}

}
