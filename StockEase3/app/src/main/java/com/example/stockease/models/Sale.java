package com.example.stockease.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sale {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String productName;
    private int stockBefore;
    private int stockAfter;
    private int soldQuantity;
    private float price;
    private String date; // Format: yyyy-MM-dd

    public Sale(String productName, int stockBefore, int stockAfter, int soldQuantity, float price, String date) {
        this.productName = productName;
        this.stockBefore = stockBefore;
        this.stockAfter = stockAfter;
        this.soldQuantity = soldQuantity;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public int getStockBefore() {
        return stockBefore;
    }

    public int getStockAfter() {
        return stockAfter;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public float getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setStockBefore(int stockBefore) {
        this.stockBefore = stockBefore;
    }

    public void setStockAfter(int stockAfter) {
        this.stockAfter = stockAfter;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
