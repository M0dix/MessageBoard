package com.example.messageboard.ViewHolders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.messageboard.Interface.ItemClickListner;
import com.example.messageboard.Model.Products;
import com.example.messageboard.R;
import com.example.messageboard.ui.Users.ProductItemActivity;


public class ProductViewHolder extends RecyclerView.ViewHolder
{
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;
    private ProductViewHolder.ClickListener mClickListener;

    public ProductViewHolder(View itemView)
    {
        super(itemView);

        imageView = itemView.findViewById(R.id.product_image);
        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductDescription = itemView.findViewById(R.id.product_description);
        txtProductPrice = itemView.findViewById(R.id.product_price);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
    }

    public interface ClickListener{
        public void onItemClick(View view, int position);
    }

    public void setOnClickListener(ProductViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
