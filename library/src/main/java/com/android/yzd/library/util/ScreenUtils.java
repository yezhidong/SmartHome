package com.android.yzd.library.util;

import android.content.Context;

/**
 * <p>Title:        ScreenUtils
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2017/7/12 下午4:18
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public class ScreenUtils {


    private ScreenUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }
    /**
     *  * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *  *
     *  * @return 返回状态栏高度的像素值。
     *  
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
