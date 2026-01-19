package com.example.demo.entities;

public class DistributionCenter {

    private Integer id_center;
    private String name_center;
    // Mapped from GEOGRAPHY types in DB to WKT String in Java
    private String location;

    public DistributionCenter() {
    }

    public DistributionCenter(Integer id_center, String name_center, String location) {
        this.id_center = id_center;
        this.name_center = name_center;
        this.location = location;
    }

    public Integer getId_center() {
        return id_center;
    }

    public void setId_center(Integer id_center) {
        this.id_center = id_center;
    }

    public String getName_center() {
        return name_center;
    }

    public void setName_center(String name_center) {
        this.name_center = name_center;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
