package com.example.stockease.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.stockease.models.Sale;

import java.util.List;

@Dao
public interface SaleDao {

    // Insert a new sale record
    @Insert
    void insert(Sale sale);

    // Get all sales made on a specific date
    @Query("SELECT * FROM Sale WHERE date = :todayDate")
    List<Sale> getSalesByDate(String todayDate);

    // Get all sales (full history)
    @Query("SELECT * FROM Sale ORDER BY date DESC")
    List<Sale> getAllSales();

    // 🔴 Clear all sales records
    @Query("DELETE FROM Sale")
    void deleteAllSales();
}
