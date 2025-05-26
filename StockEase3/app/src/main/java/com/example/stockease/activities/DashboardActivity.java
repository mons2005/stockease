package com.example.stockease.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockease.R;
import com.example.stockease.adapters.ProductAdapter;
import com.example.stockease.database.AppDatabase;
import com.example.stockease.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {

    TextView tvShopName;
    Button btnLogout, btnAddProduct, btnViewSalesHistory;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    String shopName;
    List<Product> fullProductList = new ArrayList<>();

    EditText edtSearch;
    CheckBox cbLowStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize UI elements
        tvShopName = findViewById(R.id.tvShopName);
        btnLogout = findViewById(R.id.btnLogout);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnViewSalesHistory = findViewById(R.id.btnViewSalesHistory);

        recyclerView = findViewById(R.id.recyclerView);
        edtSearch = findViewById(R.id.edtSearch);
        cbLowStock = findViewById(R.id.cbLowStock);

        // Get shop name from intent
        shopName = getIntent().getStringExtra("shopName");
        if (shopName == null) shopName = "Shop";
        tvShopName.setText("Welcome to " + shopName);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Add Product button click
        btnAddProduct.setOnClickListener(v -> {
            Intent i = new Intent(DashboardActivity.this, AddProductActivity.class);
            i.putExtra("shopName", shopName);
            startActivity(i);
        });

        // View Sales History button click
        btnViewSalesHistory.setOnClickListener(v -> {
            Intent i = new Intent(DashboardActivity.this, SalesHistoryActivity.class);
            i.putExtra("shopName", shopName); // Send shop name in case filtering by shop is needed
            startActivity(i);
        });

        // Logout button click
        btnLogout.setOnClickListener(v -> {
            Intent loginIntent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        });

        // Search filter
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Low stock checkbox filter
        cbLowStock.setOnCheckedChangeListener((buttonView, isChecked) -> filterProducts());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    private void loadProducts() {
        Executors.newSingleThreadExecutor().execute(() -> {
            fullProductList = AppDatabase.getInstance(this)
                    .productDao().getProductsForShop(shopName);
            runOnUiThread(this::filterProducts);
        });
    }

    private void filterProducts() {
        String query = edtSearch.getText().toString().toLowerCase();
        boolean lowStockOnly = cbLowStock.isChecked();

        List<Product> filteredList = new ArrayList<>();
        for (Product p : fullProductList) {
            if (p == null || p.getName() == null) continue;

            boolean matchesSearch = p.getName().toLowerCase().contains(query);
            boolean matchesStock = !lowStockOnly || p.getStock() < 5;

            if (matchesSearch && matchesStock) {
                filteredList.add(p);
            }
        }

        adapter.updateList(filteredList); // Update adapter with filtered list
    }
}
