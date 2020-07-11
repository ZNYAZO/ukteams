package com.ukteams.ecommerce.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ukteams.ecommerce.Interface.ItemClickListner;
import com.ukteams.ecommerce.R;


public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //this class is for accessing all of our products layout and all of its controllers

    public TextView txtProductName, txtProductDescription, txtProductPrice,txtProductStatus;
    public ImageView imageView;
    public ItemClickListner listner;

    //constructor to access our product fields
    public ItemViewHolder(View itemView) {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.product_seller_image);
        txtProductName = (TextView) itemView.findViewById(R.id.seller_product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_seller_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_seller_price);
        txtProductStatus = (TextView) itemView.findViewById(R.id.seller_product_state);
    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
