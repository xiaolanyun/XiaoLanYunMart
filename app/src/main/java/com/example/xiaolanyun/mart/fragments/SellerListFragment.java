package com.example.xiaolanyun.mart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xiaolanyun.mart.MyApplication;
import com.example.xiaolanyun.mart.R;
import com.example.xiaolanyun.mart.adapters.SellerListAdapter;
import com.example.xiaolanyun.mart.beans.Seller;
import com.example.xiaolanyun.mart.greendao.SellerDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 卖家列表Fragment
 *
 * @author Lei Dong
 */
public class SellerListFragment extends Fragment{
    private static final String TAG = "SellerListFragment";

    //卖家列表
    @BindView(R.id.sellers_container)
    RecyclerView mSellersContainer;

    //全部卖家
    private Seller[] mSellers;

    private SellerDao mSellerDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_seller_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //初始化组件
        initWidgets();

        //初始化动作
        initActions();
    }

    /**
     * 初始化动作
     */
    private void initActions() {

    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        mSellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();
        List<Seller> sellerList = mSellerDao.loadAll();
        if(sellerList.size() > 0) {
            mSellers = new Seller[1];
            mSellers[0] = sellerList.get(0);
        }
        else{
            mSellers = new Seller[0];
        }

        mSellersContainer.setLayoutManager(new LinearLayoutManager(MyApplication.getsContext()));
        mSellersContainer.setAdapter(new SellerListAdapter(MyApplication.getsContext(), mSellers));
    }

    @Override
    public void onStart(){
        super.onStart();
        initWidgets();
    }
}
