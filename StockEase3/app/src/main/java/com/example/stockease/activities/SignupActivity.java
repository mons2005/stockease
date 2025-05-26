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

public class SignupActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtConfirmPassword, edtShopName;
    Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtShopName = findViewById(R.id.edtShopName);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin); // Updated to Button

        // Sign-up button click listener
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                String shopName = edtShopName.getText().toString();

                // Validate input fields
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || shopName.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Save user data to SharedPreferences under the username key
                    SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(username + "_password", password);
                    editor.putString(username + "_shopName", shopName);
                    editor.putBoolean(username + "_isLoggedIn", true); // User is logged in after sign-up
                    editor.apply();

                    // Show success message and navigate to Dashboard
                    Toast.makeText(SignupActivity.this, "Sign-up successful", Toast.LENGTH_SHORT).show();
                    Intent dashboardIntent = new Intent(SignupActivity.this, DashboardActivity.class);
                    dashboardIntent.putExtra("shopName", shopName); // Pass shop name to dashboard
                    startActivity(dashboardIntent);
                    finish(); // Close sign-up page
                }
            }
        });

        // Navigate to login page using the Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish(); // Close sign-up page
            }
        });
    }
}
