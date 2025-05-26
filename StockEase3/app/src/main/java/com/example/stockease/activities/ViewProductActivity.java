package com.example.stockease.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stockease.R;
import com.example.stockease.database.AppDatabase;
import com.example.stockease.models.Product;
import com.example.stockease.models.Sale;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class ViewProductActivity extends AppCompatActivity {

    private TextView tvAppTitle, tvStock;
    private EditText edtName, edtPrice, edtSellQty;
    private Button btnSell, btnUpdate, btnDelete, btnBack;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        initViews();

        product = (Product) getIntent().getSerializableExtra("product");

        if (product == null) {
            Toast.makeText(this, "Product data not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        displayProductDetails();

        btnSell.setOnClickListener(v -> handleSell());
        btnUpdate.setOnClickListener(v -> handleUpdate());
        btnDelete.setOnClickListener(v -> handleDelete());
        btnBack.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvAppTitle = findViewById(R.id.tvAppTitle);
        edtName = findViewById(R.id.tvProductName);   // changed to EditText
        edtPrice = findViewById(R.id.tvProductPrice); // changed to EditText
        tvStock = findViewById(R.id.tvProductStock);
        edtSellQty = findViewById(R.id.edtSellQty);
        btnSell = findViewById(R.id.btnSell);
        btnUpdate = findViewById(R.id.btnUpdateProduct);
        btnDelete = findViewById(R.id.btnDeleteProduct);
        btnBack = findViewById(R.id.btnBack);
    }

    private void displayProductDetails() {
        edtName.setText(product.getName());
        edtPrice.setText(String.valueOf(product.getPrice()));
        tvStock.setText("Stock: " + product.getStock());
    }

    private void handleSell() {
        String qtyStr = edtSellQty.getText().toString().trim();

        if (qtyStr.isEmpty()) {
            Toast.makeText(this, "Enter a quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid quantity entered", Toast.LENGTH_SHORT).show();
            return;
        }

        if (qty <= 0 || qty > product.getStock()) {
            Toast.makeText(this, "Quantity out of range", Toast.LENGTH_SHORT).show();
            return;
        }

        int stockBefore = product.getStock();
        int newStock = stockBefore - qty;
        double price = product.getPrice();

        product.setStock(newStock);

        // Get today's date in yyyy-MM-dd format
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Executors.newSingleThreadExecutor().execute(() -> {
            // Update product stock
            AppDatabase.getInstance(this).productDao().updateProduct(product);

            // Create sale record and insert into database
            Sale sale = new Sale(
                    product.getName(),
                    stockBefore,
                    newStock,
                    qty,
                    (float) price,
                    todayDate
            );
            AppDatabase.getInstance(this).saleDao().insert(sale);

            runOnUiThread(() -> {
                Toast.makeText(this, "Sold successfully", Toast.LENGTH_SHORT).show();
                tvStock.setText("Stock: " + newStock);

                if (newStock < 5) {
                    new AlertDialog.Builder(this)
                            .setTitle("Low Stock Alert")
                            .setMessage("Stock is below 5. Please restock soon!")
                            .setPositiveButton("OK", null)
                            .show();
                }
            });
        });
    }

    private void handleUpdate() {
        String updatedName = edtName.getText().toString().trim();
        String updatedPriceStr = edtPrice.getText().toString().trim();

        if (updatedName.isEmpty() || updatedPriceStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double updatedPrice;
        try {
            updatedPrice = Double.parseDouble(updatedPriceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
            return;
        }

        product.setName(updatedName);
        product.setPrice(updatedPrice);

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).productDao().updateProduct(product);
            runOnUiThread(() -> {
                Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void handleDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        AppDatabase.getInstance(this).productDao().deleteProduct(product);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }
}
