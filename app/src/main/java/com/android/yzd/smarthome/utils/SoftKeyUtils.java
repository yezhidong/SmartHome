package com.android.yzd.smarthome.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * <p>Title:        SoftKeyUtils
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2018/3/2 下午11:19
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public class SoftKeyUtils {

    private SoftKeyUtils() {

    }

    public static SoftKeyUtils getInstants() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static SoftKeyUtils INSTANCE = new SoftKeyUtils();
    }


    /**
     * 隐藏软键盘
     * @param context
     * @param view
     */
    public void hide(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示软键盘
     * @param context
     * @param view
     */
    public void show(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }
}
