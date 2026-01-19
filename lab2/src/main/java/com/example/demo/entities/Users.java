package com.example.demo.entities;

import com.example.demo.Dtos.Roles;

public class Users {
    private Long id_user;
    private String name_user;
    private String email_user;
    private String password_user;
    private Roles role;
    private Long storeU_id;

    // Getters
    public Long getId_user() { return id_user; }
    public String getName_user() { return name_user; }
    public String getEmail_user() { return email_user; }
    public String getPassword_user() { return password_user; }
    public Roles getRole() { return role; }
    public Long getStoreU_id() { return storeU_id; }

    //Setters
    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }
    public void setName_user(String name_user) {
        this.name_user = name_user;
    }
    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }
    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }
    public void setRole(Roles role) {
        this.role = role;
    }
    public void setStoreU_id(Long storeU_id) {this.storeU_id = storeU_id;}
}