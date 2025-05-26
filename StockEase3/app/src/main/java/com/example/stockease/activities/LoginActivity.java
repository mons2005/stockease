package com.example.stockease.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stockease.R;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        // Login button click listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);

                    // Check if the entered username exists in SharedPreferences
                    if (sharedPreferences.contains(username + "_password")) {
                        String savedPassword = sharedPreferences.getString(username + "_password", "");
                        String shopName = sharedPreferences.getString(username + "_shopName", "Default Shop");

                        if (password.equals(savedPassword)) {
                            // Successful login
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(username + "_isLoggedIn", true); // Mark user as logged in
                            editor.apply();

                            // Go to Dashboard with the shop name
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            intent.putExtra("shopName", shopName);
                            startActivity(intent);
                            finish(); // Finish login activity to prevent going back to it
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Username not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Sign-up button click listener
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish(); // Finish login activity to prevent going back to it
            }
        });
    }
}