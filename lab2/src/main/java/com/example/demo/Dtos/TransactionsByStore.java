package com.example.demo.Dtos;

import java.util.Date;

public class TransactionsByStore {
    private Long idTransaction;
    private String typeTransaction;
    private Date dateTransaction;
    private String nameProduct;
    private Integer amountProduct;
    private String flow;  // Entrada o Salida
    private String NameStoreOR;
    private String NameStoreDE;

    public TransactionsByStore() {}

    //Getters

    public Long getIdTransaction() {return idTransaction;}
    public String getTypeTransaction() {return typeTransaction;}
    public Date getDateTransaction() {return dateTransaction;}
    public String getNameProduct() {return nameProduct;}
    public Integer getAmountProduct() {return amountProduct;}
    public String getFlow() {return flow;}
    public String getNameStoreOR() {return NameStoreOR;}
    public String getNameStoreDE() {return NameStoreDE;}

    //Setters

    public void setIdTransaction(Long idTransaction) {this.idTransaction = idTransaction;}
    public void setTypeTransaction(String typeTransaction) {this.typeTransaction = typeTransaction;}
    public void setDateTransaction(Date dateTransaction) {this.dateTransaction = dateTransaction;}
    public void setNameProduct(String nameProduct) {this.nameProduct = nameProduct;}
    public void setAmountProduct(Integer amountProduct) {this.amountProduct = amountProduct;}
    public void setFlow(String flow) {this.flow = flow;}
    public void setNameStoreOR(String nameStoreOR) {NameStoreOR = nameStoreOR;}
    public void setNameStoreDE(String nameStoreDE) {NameStoreDE = nameStoreDE;}
}
