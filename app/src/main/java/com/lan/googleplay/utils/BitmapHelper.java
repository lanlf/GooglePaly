package com.lan.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by lan on 2016/7/29.
 */

public class BitmapHelper {

    private static BitmapUtils mBitmapUtils = null;

    // 单例, 懒汉模式
    public static BitmapUtils getBitmapUtils() {
        if (mBitmapUtils == null) {
            synchronized (BitmapHelper.class) {
                if (mBitmapUtils == null) {
                    mBitmapUtils = new BitmapUtils(UIUtils.getContext());
                }
            }
        }

        return mBitmapUtils;
    }
}