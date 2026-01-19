package com.example.demo.Dtos;

public class Transfer {
    private Long id_storeOR;
    private Long id_storeDE;
    private Long id_product;
    private Integer amount_product;

    // Getters
    public Long getId_storeOR() { return id_storeOR; }
    public Long getId_storeDE() { return id_storeDE; }
    public Long getId_product() { return id_product; }
    public Integer getAmount_product() { return amount_product; }

    // Setters
    public void setId_storeOR(Long id_storeOR) { this.id_storeOR = id_storeOR; }
    public void setId_storeDE(Long id_storeDE) { this.id_storeDE = id_storeDE; }
    public void setId_product(Long id_product) { this.id_product = id_product; }
    public void setAmount_product(Integer amount_product) { this.amount_product = amount_product; }
}
