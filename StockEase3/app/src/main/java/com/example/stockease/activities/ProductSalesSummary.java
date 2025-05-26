package com.example.stockease.models;

public class ProductSalesSummary {
    public String productName;
    public int totalQuantity;

    // Empty constructor required by Room
    public ProductSalesSummary() {}

    public String getProductName() {
        return productName;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
