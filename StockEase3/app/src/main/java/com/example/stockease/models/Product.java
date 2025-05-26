package com.example.stockease.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int quantity;
    private double price;
    private String shopName;

    // Constructor
    public Product(String name, int quantity, double price, String shopName) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.shopName = shopName;
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getShopName() {
        return shopName;
    }

    // Convenience method to get stock
    public int getStock() {
        return quantity;
    }

    // --- Setters ---
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStock(int stock) {
        this.quantity = stock;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
