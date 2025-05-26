package com.example.stockease.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String email;
    private String shopName;
    private String password;

    // Constructor
    public User(String username, String email, String shopName, String password) {
        this.username = username;
        this.email = email;
        this.shopName = shopName;
        this.password = password;
    }

// Getters and setters...

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getShopName() { return shopName; }
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    public void setPassword(String password) { this.password = password; }
}

