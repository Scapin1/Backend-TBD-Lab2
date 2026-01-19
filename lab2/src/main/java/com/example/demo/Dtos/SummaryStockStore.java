package com.example.demo.Dtos;

public class SummaryStockStore {

    private Long idTienda;
    private String nameStore;
    private Double totalPriceInventory;
    private Integer uniqueProduct;

    // Getters y Setters
    public Long getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Long idTienda) {
        this.idTienda = idTienda;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public Double getTotalPriceInventory() {
        return totalPriceInventory;
    }

    public void setTotalPriceInventory(Double totalPriceInventory) {
        this.totalPriceInventory = totalPriceInventory;
    }

    public Integer getUniqueProduct() {
        return uniqueProduct;
    }

    public void setUniqueProduct(Integer uniqueProduct) {
        this.uniqueProduct = uniqueProduct;
    }

}
