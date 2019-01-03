package com.example.xiaolanyun.mart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.xiaolanyun.mart.MyApplication;
import com.example.xiaolanyun.mart.R;
import com.example.xiaolanyun.mart.adapters.ProductsBuyerAdapter;
import com.example.xiaolanyun.mart.beans.Product;
import com.example.xiaolanyun.mart.constants.Constants;
import com.example.xiaolanyun.mart.storage.MySharedPreferences;
import com.example.xiaolanyun.mart.utils.LoadUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 买家商品Fragment
 *
 * @author Lei Dong
 */
public class ProductsBuyerFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ProductsBuyerFragment";

    //搜索框
    @BindView(R.id.search_view)
    SearchView mSearchView;

    //商品列表
    @BindView(R.id.products_list)
    RecyclerView mProductsRecyclerView;

    @BindView(R.id.category_fruits_ibt)
    ImageView mCategoryFruitsIbt;

    @BindView(R.id.category_vegetables_ibt)
    ImageView mCategoryVegetablesIbt;

    @BindView(R.id.category_grains_ibt)
    ImageView mCategoryGrainsIbt;

    //水果
    @BindView(R.id.fruits_ibt)
    TextView mFruits_ibt;
    //蔬菜
    @BindView(R.id.vegetables_ibt)
    TextView mVegetables_ibt;
    //谷物
    @BindView(R.id.grains_ibt)
    TextView mGrains_ibt;
    //全部商品
    private Product[] mProducts;

    //用户身份码
    private int mUserMode;

    //买家Id
    private Long mBuyerId;

    //MySharedPreferences
    private MySharedPreferences mMySharedPreferences;

    //商品目录码
    private Long mCategoryMode = Constants.CATEGORY_FRUITS;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_buyer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
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
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    if(mCategoryMode == Constants.CATEGORY_FRUITS) {
                        mProducts = LoadUtils.loadBuyerProductsByCategoryId(Constants.CATEGORY_FRUITS);
                    }
                    else if(mCategoryMode == Constants.CATEGORY_VEGETABLES){
                        mProducts = LoadUtils.loadBuyerProductsByCategoryId(Constants.CATEGORY_VEGETABLES);
                    }
                    else if(mCategoryMode == Constants.CATEGORY_GRAINS){
                        mProducts = LoadUtils.loadBuyerProductsByCategoryId(Constants.CATEGORY_GRAINS);
                    }
                    else{

                    }
                } else {
                    if(mCategoryMode == Constants.CATEGORY_FRUITS){
                        mProducts = LoadUtils.loadBuyerProductsByNameAndCategoryId(s, Constants.CATEGORY_FRUITS);
                    }
                    else if(mCategoryMode == Constants.CATEGORY_VEGETABLES){
                        mProducts = LoadUtils.loadBuyerProductsByNameAndCategoryId(s, Constants.CATEGORY_VEGETABLES);
                    }
                    else if(mCategoryMode == Constants.CATEGORY_GRAINS){
                        mProducts = LoadUtils.loadBuyerProductsByNameAndCategoryId(s, Constants.CATEGORY_GRAINS);
                    }
                    else{

                    }

                }
                mProductsRecyclerView.setAdapter(new ProductsBuyerAdapter(MyApplication.getsContext(), mProducts));
                return true;
            }
        });
        mGrains_ibt.setOnClickListener(this);
        mFruits_ibt.setOnClickListener(this);
        mVegetables_ibt.setOnClickListener(this);
        mCategoryFruitsIbt.setOnClickListener(this);
        mCategoryVegetablesIbt.setOnClickListener(this);
        mCategoryGrainsIbt.setOnClickListener(this);
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(MyApplication.getsContext());
        mUserMode = mMySharedPreferences.load(Constants.USER_MODE, 0);
        mBuyerId = mMySharedPreferences.load(Constants.BUYER_ID, 0L);

        mProducts = LoadUtils.loadBuyerProductsByCategoryId(Constants.CATEGORY_FRUITS);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyApplication.getsContext(), 2);
        mProductsRecyclerView.setLayoutManager(gridLayoutManager);
        mProductsRecyclerView.setAdapter(new ProductsBuyerAdapter(MyApplication.getsContext(), mProducts));
    }

    @Override
    public void onStart() {
        super.onStart();
        initActions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 按钮点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fruits_ibt:
                mCategoryMode = Constants.CATEGORY_FRUITS;
                mProducts = LoadUtils.loadBuyerProductsByCategoryId(mCategoryMode);
                mProductsRecyclerView.setAdapter(new ProductsBuyerAdapter(MyApplication.getsContext(), mProducts));
                break;
            case R.id.category_fruits_ibt:
                mCategoryMode = Constants.CATEGORY_FRUITS;
                mProducts = LoadUtils.loadBuyerProductsByCategoryId(mCategoryMode);
                mProductsRecyclerView.setAdapter(new ProductsBuyerAdapter(MyApplication.getsContext(), mProducts));
                break;

            case R.id.vegetables_ibt:
                mCategoryMode = Constants.CATEGORY_VEGETABLES;
                mProducts = LoadUtils.loadBuyerProductsByCategoryId(mCategoryMode);
                mProductsRecyclerView.setAdapter(new ProductsBuyerAdapter(MyApplication.getsContext(), mProducts));
                break;
            case R.id.category_vegetables_ibt:
                mCategoryMode = Constants.CATEGORY_VEGETABLES;
                mProducts = LoadUtils.loadBuyerProductsByCategoryId(mCategoryMode);
                mProductsRecyclerView.setAdapter(new ProductsBuyerAdapter(MyApplication.getsContext(), mProducts));
                break;

            case R.id.grains_ibt:
                mCategoryMode = Constants.CATEGORY_GRAINS;
                mProducts = LoadUtils.loadBuyerProductsByCategoryId(mCategoryMode);
                mProductsRecyclerView.setAdapter(new ProductsBuyerAdapter(MyApplication.getsContext(), mProducts));
                break;
            case R.id.category_grains_ibt:
                mCategoryMode = Constants.CATEGORY_GRAINS;
                mProducts = LoadUtils.loadBuyerProductsByCategoryId(mCategoryMode);
                mProductsRecyclerView.setAdapter(new ProductsBuyerAdapter(MyApplication.getsContext(), mProducts));
                break;
            default:
                break;
        }
    }
}
