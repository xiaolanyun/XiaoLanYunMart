package com.example.xiaolanyun.mart.utils;

import android.graphics.Typeface;
import android.widget.TextView;

import com.example.xiaolanyun.mart.MyApplication;

/**
 * 字体相关工具类
 *
 * @author Lei Dong
 */
public class FontUtils {
    /**
     * 设置字体
     *
     * @param textView
     * @param fontPath
     */
    public static void setFontFromAssets(TextView textView, String fontPath){
        Typeface typeface = Typeface.createFromAsset(MyApplication.getsAssetManager(), fontPath);
        textView.setTypeface(typeface);
    }
}
