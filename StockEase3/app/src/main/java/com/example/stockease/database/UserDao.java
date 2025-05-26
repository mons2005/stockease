package com.example.stockease.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.stockease.models.User;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("SELECT * FROM User WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);
}