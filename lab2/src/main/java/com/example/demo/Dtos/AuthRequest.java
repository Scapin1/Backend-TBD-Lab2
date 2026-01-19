package com.example.demo.Dtos;

public class AuthRequest {
    private String email;
    private String password;
    private Roles role;
    private String name_user;
    private Long storeU_id;

    //Getters
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Roles getRole() {
        return role;
    }
    public String getName_user() {
        return name_user;
    }
    public Long getStoreU_id() {
        return storeU_id;
    }

    //setters
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRol(Roles role) {
        this.role = role;
    }
    public void setName_user(String name_user) {
        this.name_user = name_user;
    }
    public void setStoreU_id(Long storeU_id) {
        this.storeU_id = storeU_id;
    }
}
