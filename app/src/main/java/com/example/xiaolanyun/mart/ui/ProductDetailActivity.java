package com.example.xiaolanyun.mart.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaolanyun.mart.MyApplication;
import com.example.xiaolanyun.mart.R;
import com.example.xiaolanyun.mart.beans.Order;
import com.example.xiaolanyun.mart.beans.Product;
import com.example.xiaolanyun.mart.constants.Constants;
import com.example.xiaolanyun.mart.greendao.OrderDao;
import com.example.xiaolanyun.mart.greendao.ProductDao;
import com.example.xiaolanyun.mart.secure.SecureUtils;
import com.example.xiaolanyun.mart.storage.MySharedPreferences;
import com.example.xiaolanyun.mart.utils.FontUtils;
import com.example.xiaolanyun.mart.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品详情界面
 *
 * @author Lei Dong
 */
public class ProductDetailActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ProductDetailActivity";

    @BindView(R.id.product_detail_image)
    ImageView mProductDetailImage;

    @BindView(R.id.product_detail_name)
    TextView mProductDetailName;

    @BindView(R.id.product_detail_price)
    TextView mProductDetailPrice;

    @BindView(R.id.product_detail_desc)
    TextView mProductDetailDesc;

    @BindView(R.id.btn_collect)
    Button mBtnCollect;

    @BindView(R.id.btn_buy)
    Button mBtnBuy;

    private MySharedPreferences mMySharedPreferences;
    private int stock;
    private ProductDao productDao;
    private Product product;

    /*特定商品的信息*/
    private Long mProductId;
    private String mProductImageUrl;
    private String mProductName;
    private int mProductPrice;
    private String mProductDesc;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ButterKnife.bind(this);

        initWidgets();

        initActions();
    }

    /**
     * 初始化动作
     */
    private void initActions() {
        mBtnCollect.setOnClickListener(this);
        mBtnBuy.setOnClickListener(this);
    }

    /**
     * 初始化组件
     */
    @SuppressLint("SetTextI18n")
    private void initWidgets() {
        mMySharedPreferences = MySharedPreferences.getMySharedPreferences(this);

        //获取本界面需要的全部信息
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.PRODCUT_DETAIL);
        mProductId = bundle.getLong(Constants.PRODUCT_ID);
        mProductImageUrl = bundle.getString(Constants.PRODUCT_DETAIL_IMAGE_URL);
        mProductName = bundle.getString(Constants.PRODUCT_DETAIL_NAME);
        mProductPrice = bundle.getInt(Constants.PRODUCT_DETAIL_PRICE);
        mProductDesc = bundle.getString(Constants.PRODUCT_DETAIL_DESC);

        //填充本界面需要的全部信息
        Picasso.get().load(mProductImageUrl).into(mProductDetailImage);
        mProductDetailName.setText(mProductName);
        mProductDetailPrice.setText(mProductPrice + "元");
        mProductDetailDesc.setText(mProductDesc);

//        //设置字体
//        FontUtils.setFontFromAssets(mProductDetailName, "fonts/tongkati.ttf");
//        FontUtils.setFontFromAssets(mProductDetailPrice, "fonts/tongkati.ttf");
//        FontUtils.setFontFromAssets(mProductDetailDesc, "fonts/tongkati.ttf");
    }

    /**
     * 按键点击时间监听
     *
     * @param view 点击的View
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_collect:
                clickCollectBtn();
                break;
            case R.id.btn_buy:
                clickBuyBtn();
                break;
            default:
                break;
        }
    }

    /**
     * 点击购买按钮
     */
    @SuppressLint("ShowToast")
    private void clickBuyBtn() {
        productDao = MyApplication.getInstance().getDaoSession().getProductDao();
        product = productDao.queryBuilder().where(ProductDao.Properties.Id.eq(mProductId)).unique();

        //获取存量
        stock = product.getProductStock();

        //存量足够
        if(stock > 0){
            final EditText editText = new EditText(this);
            editText.setHint(R.string.input_password);
            editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setGravity(Gravity.CENTER);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.warning);
            builder.setIcon(R.drawable.app_icon);
            builder.setMessage(R.string.warning_buy);
            builder.setView(editText);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String passwordTemp = editText.getText().toString().trim();
                    Long buyerId = mMySharedPreferences.load(Constants.BUYER_ID, 0L);
                    if(SecureUtils.isBuyerPasswordRight(passwordTemp, buyerId)) {
                        addOrderToDb();
                        Toast.makeText(ProductDetailActivity.this, R.string.warning_buy_success, Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(ProductDetailActivity.this, R.string.warning_password_error, Toast.LENGTH_LONG).show();
                    }
                }
            });
            builder.setNegativeButton(R.string.cancel, null);
            builder.create().show();
        }
        //存量不足
        else{
            Toast.makeText(ProductDetailActivity.this, R.string.stock_not_enough, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 添加订单到数据库中
     */
    private void addOrderToDb() {
        stock -= 1;
        product.setProductStock(stock);
        productDao.update(product);

        //生成订单
        OrderDao orderDao = MyApplication.getInstance().getDaoSession().getOrderDao();
        Order order = new Order();
        Long buyerId = mMySharedPreferences.load(Constants.BUYER_ID, 0L);
        if(buyerId != 0L) {
            order.setBuyerId(buyerId);
            order.setProductId(mProductId);
            order.setOrderId(TimeUtils.generateOrderId());
            order.setOrderTime(TimeUtils.getCurrentSysTime());
            order.setProductNumber(1);
            order.setOrderState(Constants.ORDER_BOUGHT);
            orderDao.insert(order);
        }
    }

    /**
     * 点击加入购物车按钮
     */
    private void clickCollectBtn() {
        Toast.makeText(ProductDetailActivity.this, R.string.add_trolley_finish, Toast.LENGTH_LONG).show();
    }
}
