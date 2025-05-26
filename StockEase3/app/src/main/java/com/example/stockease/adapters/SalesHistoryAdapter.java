package com.example.stockease.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockease.R;
import com.example.stockease.models.Sale;

import java.util.List;

public class SalesHistoryAdapter extends RecyclerView.Adapter<SalesHistoryAdapter.ViewHolder> {

    private List<Sale> sales;

    public SalesHistoryAdapter(List<Sale> sales) {
        this.sales = sales;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSaleDetails;

        public ViewHolder(View view) {
            super(view);
            tvSaleDetails = view.findViewById(R.id.tvSaleDetails);
        }
    }

    @NonNull
    @Override
    public SalesHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sale, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesHistoryAdapter.ViewHolder holder, int position) {
        Sale sale = sales.get(position);
        String details = "Product: " + sale.getProductName() +
                "\nStock Before: " + sale.getStockBefore() +
                "\nStock After: " + sale.getStockAfter() +
                "\nSold Qty: " + sale.getSoldQuantity() +
                "\nPrice: ₹" + sale.getPrice() +
                "\nDate: " + sale.getDate();
        holder.tvSaleDetails.setText(details);
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }
}
