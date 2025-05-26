package com.example.stockease.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.stockease.models.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM Product WHERE shopName = :shopName")
    List<Product> getProductsForShop(String shopName);

    // 🔍 Added: Get product by ID
    @Query("SELECT * FROM Product WHERE id = :productId")
    Product getProductById(int productId);
}
