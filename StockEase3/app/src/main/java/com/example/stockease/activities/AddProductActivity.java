package com.example.stockease.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stockease.R;
import com.example.stockease.database.AppDatabase;
import com.example.stockease.models.Product;

import java.util.concurrent.Executors;

public class AddProductActivity extends AppCompatActivity {

    EditText edtProductName, edtQuantity, edtPrice;
    Button btnSaveProduct, btnCancel;
    String shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        shopName = getIntent().getStringExtra("shopName");

        edtProductName = findViewById(R.id.edtProductName);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtPrice = findViewById(R.id.edtPrice);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);
        btnCancel = findViewById(R.id.btnCancel);

        btnSaveProduct.setOnClickListener(v -> {
            String name = edtProductName.getText().toString().trim();
            String qtyStr = edtQuantity.getText().toString().trim();
            String priceStr = edtPrice.getText().toString().trim();

            if (name.isEmpty() || qtyStr.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int quantity = Integer.parseInt(qtyStr);
                double price = Double.parseDouble(priceStr);

                Product product = new Product(name, quantity, price, shopName);

                Executors.newSingleThreadExecutor().execute(() -> {
                    AppDatabase.getInstance(this).productDao().insert(product);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                });

            } catch (Exception e) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}
