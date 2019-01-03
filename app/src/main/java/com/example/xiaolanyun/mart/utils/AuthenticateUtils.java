package com.example.xiaolanyun.mart.utils;

import com.example.xiaolanyun.mart.MyApplication;
import com.example.xiaolanyun.mart.beans.Buyer;
import com.example.xiaolanyun.mart.beans.Root;
import com.example.xiaolanyun.mart.beans.Seller;
import com.example.xiaolanyun.mart.greendao.BuyerDao;
import com.example.xiaolanyun.mart.greendao.RootDao;
import com.example.xiaolanyun.mart.greendao.SellerDao;

import java.util.List;

/**
 * 认证相关工具类
 *
 * @author Lei Dong
 */
public class AuthenticateUtils {
    /**
     * 认证Root管理员的登陆信息
     *
     * @param usernameTemp 待认证的Root用户名
     * @param passwordTemp 待认证的Root密码
     * @return 认证是否成功
     */
    public static boolean authenticateRoot(String usernameTemp, String passwordTemp) {
        RootDao rootDao = MyApplication.getInstance().getDaoSession().getRootDao();
        List<Root> rootList = rootDao.loadAll();
        Root root = rootList.get(0);
        String username = root.getUsername();
        String password = root.getPassword();
        return username.equals(usernameTemp) && password.equals(passwordTemp);
    }

    /**
     * 认证商家的登录信息
     *
     * @param usernameTemp 待认证的卖家用户名
     * @param passwordTemp 待认证的卖家密码
     * @return 认证是否成功
     */
    public static boolean authenticateSeller(String usernameTemp, String passwordTemp) {
        //TODO
        SellerDao sellerDao = MyApplication.getInstance().getDaoSession().getSellerDao();
        List<Seller> sellerList = sellerDao.loadAll();
        if(sellerList.size() == 0){
            return false;
        }
        String realUsername = sellerList.get(0).getUsername();
        String realPassword = sellerList.get(0).getPassword();
        return realUsername.equals(usernameTemp)
                && realPassword.equals(passwordTemp);
    }

    /**
     * 认证买家的登录信息
     *
     * @param usernameTemp 待认证的买家用户名
     * @param passwordTemp 待认证的买家密码
     * @return 认证是否成功
     */
    public static boolean authenticateBuyer(String usernameTemp, String passwordTemp) {
        //TODO
        BuyerDao buyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
        List<Buyer> buyerList = buyerDao.loadAll();
        if(buyerList.size() == 0){
            return false;
        }
        for(Buyer buyer : buyerList){
            if(buyer.getUsername().equals(usernameTemp) && buyer.getPassword().equals(passwordTemp)){
                return true;
            }

        }
        return false;
    }

    /**
     * 判断此Buyer是否已经存在
     *
     * @param username 买家用户名
     * @return 买家是否存在
     */
    public static boolean buyerNotExsit(String username) {
        BuyerDao buyerDao = MyApplication.getInstance().getDaoSession().getBuyerDao();
        List<Buyer> buyerList = buyerDao.loadAll();
        if(buyerList.size() == 0){
            return true;
        }
        for(Buyer buyer : buyerList){
            if(buyer.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }
}
