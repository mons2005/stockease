package com.example.stockease.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockease.R;
import com.example.stockease.adapters.SalesHistoryAdapter;
import com.example.stockease.database.AppDatabase;
import com.example.stockease.models.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SalesHistoryActivity extends AppCompatActivity {

    private RecyclerView rvSalesHistory;
    private Button btnViewGraph, btnBack, btnClearHistory;
    private List<Sale> allSalesList = new ArrayList<>();
    private SalesHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_history);

        rvSalesHistory = findViewById(R.id.rvSalesHistory);
        btnViewGraph = findViewById(R.id.btnViewGraph);
        btnBack = findViewById(R.id.btnBack);
        btnClearHistory = findViewById(R.id.btnClearHistory); // New button

        rvSalesHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SalesHistoryAdapter(allSalesList);
        rvSalesHistory.setAdapter(adapter);

        loadAllSales();  // Load full sales history

        btnBack.setOnClickListener(v -> finish());

        btnViewGraph.setOnClickListener(v -> {
            Intent intent = new Intent(SalesHistoryActivity.this, SalesGraphActivity.class);
            startActivity(intent);
        });

        btnClearHistory.setOnClickListener(v -> clearSalesHistory());
    }

    private void loadAllSales() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<Sale> sales = AppDatabase.getInstance(getApplicationContext())
                        .saleDao()
                        .getAllSales();

                for (Sale s : sales) {
                    Log.d("SalesDebug", "Sale: " + s.getProductName() + " | " + s.getDate());
                }

                runOnUiThread(() -> {
                    if (sales != null && !sales.isEmpty()) {
                        allSalesList.clear();
                        allSalesList.addAll(sales);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "No sales found in history.", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Error loading sales: " + e.getMessage(), Toast.LENGTH_LONG).show());
                e.printStackTrace();
            }
        });
    }

    private void clearSalesHistory() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                AppDatabase.getInstance(getApplicationContext()).saleDao().deleteAllSales();

                runOnUiThread(() -> {
                    allSalesList.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Sales history cleared!", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Failed to clear history: " + e.getMessage(), Toast.LENGTH_LONG).show());
                e.printStackTrace();
            }
        });
    }
}
