package com.example.xiaolanyun.mart.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiaolanyun.mart.R;
import com.example.xiaolanyun.mart.beans.Product;
import com.example.xiaolanyun.mart.constants.Constants;
import com.example.xiaolanyun.mart.ui.ProductManageActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 卖家产品列表适配器
 *
 * @author Lei Dong
 */
public class ProductsSellerAdapter extends RecyclerView.Adapter<ProductsSellerAdapter.ViewHolder> {
    //Context
    private Context mContext;

    //商品数组
    private Product[] mProducts;

    /**
     * 构造器
     *
     * @param context Context
     * @param products 商品数组
     */
    public ProductsSellerAdapter(Context context, Product[] products) {
        this.mContext = context;
        this.mProducts = products;
    }

    @Override
    public ProductsSellerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.product_seller_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(mProducts != null) {
            Picasso.get().load(mProducts[position].getProductImageUrl()).into(holder.productImage);
            holder.productName.setText(mProducts[position].getProductName());
            holder.productPrice.setText(mProducts[position].getProductPrice() + "  元");

            holder.productItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ProductManageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constants.PRODUCT_ID, mProducts[position].getId());
                    intent.putExtra(Constants.PRODUCT_MANAGE, bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mProducts != null) {
            return mProducts.length;
        }
        return 0;
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.product_buyer_item_layout)
        LinearLayout productItemLayout;

        @BindView(R.id.product_buyer_item_image)
        ImageView productImage;

        @BindView(R.id.product_buyer_item_name)
        TextView productName;

        @BindView(R.id.product_buyer_item_price)
        TextView productPrice;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
