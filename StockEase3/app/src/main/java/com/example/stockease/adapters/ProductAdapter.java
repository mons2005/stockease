package com.example.stockease.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockease.R;
import com.example.stockease.activities.ViewProductActivity;
import com.example.stockease.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    // Method to update the product list and refresh RecyclerView
    public void updateList(List<Product> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductQty, tvProductPrice;
        Button btnView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductQty = itemView.findViewById(R.id.tvProductQty);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            btnView = itemView.findViewById(R.id.btnView);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductQty.setText("Qty: " + product.getStock());
        holder.tvProductPrice.setText("Price: ₹" + product.getPrice());

        holder.btnView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ViewProductActivity.class);
            intent.putExtra("product", product); // Make sure Product implements Serializable or Parcelable
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
