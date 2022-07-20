package com.example.messageboard.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageboard.Interface.ItemClickListner;
import com.example.messageboard.R;
import com.rey.material.widget.Button;

public class OrderViewHolder extends RecyclerView.ViewHolder
{
    public TextView txtProductDate, txtProductUserName, txtProductUserPhone, txtProductName, txtUserPhone, txtProductCount, txtProductPrice;
    public Button btnDeleteOrder;

    public OrderViewHolder(View itemView)
    {
        super(itemView);

        txtProductDate = itemView.findViewById(R.id.order_date);
        txtProductUserName = itemView.findViewById(R.id.order_username);
        txtProductUserPhone = itemView.findViewById(R.id.order_product_phone);
        txtProductName = itemView.findViewById(R.id.order_product_name);
        txtUserPhone = itemView.findViewById(R.id.order_userphone);
        txtProductCount = itemView.findViewById(R.id.order_product_count);
        txtProductPrice = itemView.findViewById(R.id.order_product_price);
        btnDeleteOrder = itemView.findViewById(R.id.order_delete_btn);
    }
}

