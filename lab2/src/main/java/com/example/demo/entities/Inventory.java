package com.example.demo.entities;

public class Inventory {
    private Long id_storein;
    private Long id_productin;
    private Integer stock_inventory;

    //Getters
    public Long getId_storein() { return id_storein; }
    public Long getId_productin() { return id_productin; }
    public Integer getStock_inventory() { return stock_inventory; }

    //Setters
    public void setId_storein(Long id_storein) { this.id_storein = id_storein; }
    public void setId_productin(Long id_productin) { this.id_productin = id_productin; }
    public void setStock_inventory(Integer stock_inventory) { this.stock_inventory = stock_inventory; }
}
