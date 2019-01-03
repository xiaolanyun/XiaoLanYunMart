package com.example.xiaolanyun.mart.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiaolanyun.mart.R;
import com.example.xiaolanyun.mart.beans.Buyer;
import com.example.xiaolanyun.mart.constants.Constants;
import com.example.xiaolanyun.mart.ui.UserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 买家列表适配器
 *
 * @author Lei Dong
 */
public class BuyerListAdapter extends RecyclerView.Adapter<BuyerListAdapter.ViewHolder> {
    //Context
    private Context mContext;

    //买家数组
    private Buyer[] mBuyers;

    /**
     * 构造器
     *
     * @param context Context
     * @param mBuyers 买家数组
     */
    public BuyerListAdapter(Context context, Buyer[] mBuyers) {
        this.mContext = context;
        this.mBuyers = mBuyers;
    }

    @Override
    public BuyerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.buyer_list_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(mBuyers.length > 0) {
            holder.buyerId.setText(mBuyers[position].getId() + "");
            holder.buyerName.setText(mBuyers[position].getUsername());

            holder.buyerItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //传递用户Id和用户身份码
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constants.USER_ID, mBuyers[position].getId());
                    bundle.putInt(Constants.USER_MODE, Constants.BUYER_MODE);
                    intent.putExtra(Constants.USER_DATA, bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mBuyers != null) {
            return mBuyers.length;
        }
        return 0;
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.buyer_item_layout)
        LinearLayout buyerItemLayout;

        @BindView(R.id.buyer_id)
        TextView buyerId;

        @BindView(R.id.buyer_name)
        TextView buyerName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
