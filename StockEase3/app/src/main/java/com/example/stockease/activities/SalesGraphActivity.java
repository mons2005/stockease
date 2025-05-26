package com.example.stockease.activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stockease.R;
import com.example.stockease.database.AppDatabase;
import com.example.stockease.models.Sale;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class SalesGraphActivity extends AppCompatActivity {

    private WebView webView;
    private Button btnBackToHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_graph);

        webView = findViewById(R.id.webView);
        btnBackToHistory = findViewById(R.id.btnBackToHistory);

        btnBackToHistory.setOnClickListener(v -> finish());

        loadGraph();
    }

    private void loadGraph() {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Sale> todaySales = AppDatabase.getInstance(getApplicationContext())
                    .saleDao()
                    .getSalesByDate(today);

            if (todaySales == null || todaySales.isEmpty()) {
                runOnUiThread(() ->
                        Toast.makeText(this, "No sales today to show graph.", Toast.LENGTH_SHORT).show());
                return;
            }

            StringBuilder data = new StringBuilder();
            for (Sale sale : todaySales) {
                data.append("['").append(sale.getProductName()).append("', ")
                        .append(sale.getSoldQuantity()).append("],");
            }

            String html = "<html><head>" +
                    "<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>" +
                    "<script type=\"text/javascript\">" +
                    "google.charts.load('current', {'packages':['corechart']});" +
                    "google.charts.setOnLoadCallback(drawChart);" +
                    "function drawChart() {" +
                    "var data = google.visualization.arrayToDataTable([" +
                    "['Product', 'Quantity Sold']," +
                    data.toString() +
                    "]);" +
                    "var options = {" +
                    "title: 'Today\\'s Sales Distribution'," +
                    "titleTextStyle: { fontSize: 22, bold: true }," +
                    "legend: { position: 'none' }," +
                    "hAxis: { title: 'Product', textStyle: { fontSize: 18 }, titleTextStyle: { fontSize: 20 } }," +
                    "vAxis: { title: 'Quantity Sold', textStyle: { fontSize: 18 }, titleTextStyle: { fontSize: 20 } }," +
                    "bar: { groupWidth: '65%' }" +
                    "};" +
                    "var chart = new google.visualization.ColumnChart(document.getElementById('barchart'));" +
                    "chart.draw(data, options);" +
                    "}" +
                    "</script>" +
                    "</head><body style='margin:0;padding:0;'>" +
                    "<div style='display:flex; justify-content:center; align-items:center; height:100vh;'>" +
                    "<div id='barchart' style='width:90%; max-width:800px; height:500px;'></div>" +
                    "</div>" +
                    "</body></html>";

            runOnUiThread(() -> {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
            });
        });
    }
}
