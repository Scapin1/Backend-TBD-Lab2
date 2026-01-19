package com.example.demo.Dtos;

public class DtoC1 {
    private String name_product;
    private Integer average_days_in_inventory;

    public DtoC1(String name_product, Integer average_days) {
        this.name_product = name_product;
        this.average_days_in_inventory = average_days;
    }

    public String getName_product() {
        return name_product;
    }
    public Integer getAverage_days() {
        return average_days_in_inventory;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }
    public void setAverage_days(Integer average_days) {
        this.average_days_in_inventory = average_days;
    }
}
