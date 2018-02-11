package com.android.yzd.library.util;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * <p>Title:        PrintLog
 * <p>Description:
 * <p>@author:      yzd
 * <p>Create Time:  2017/6/1 下午8:47
 */
public class Log {

    private static boolean sPrintLog = true;

    private static final HashMap<String, Long> sTimeStamps = new HashMap<String, Long>();

    public static void setPrintLog(boolean printLog) {
        Log.sPrintLog = printLog;
    }

    //-------------------------- 自定义标签 ----------------------------
    public static void i(String tag, String msg) {
        if (Log.sPrintLog) {
            if (TextUtils.isEmpty(msg)) {
                android.util.Log.i(tag, "NULL");
            } else {
                android.util.Log.i(tag, msg);
            }
        }
    }


    public static void v(String tag, String msg) {
        if (Log.sPrintLog) {
            if (TextUtils.isEmpty(msg)) {
                android.util.Log.v(tag, "NULL");
            } else {
                android.util.Log.v(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (Log.sPrintLog) {
            if (TextUtils.isEmpty(msg)) {
                android.util.Log.d(tag, "NULL");
            } else {
                android.util.Log.d(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (Log.sPrintLog) {
            if (TextUtils.isEmpty(msg)) {
                android.util.Log.w(tag, "NULL");
            } else {
                android.util.Log.w(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (Log.sPrintLog) {
            if (TextUtils.isEmpty(msg)) {
                android.util.Log.e(tag, "NULL");
            } else {
                android.util.Log.e(tag, msg);
            }
        }
    }

//-------------------------- 打印时间戳 ----------------------------
    public static long startTimeTrack(String tag) {
        if (Log.sPrintLog) {
            long time = System.currentTimeMillis();
            sTimeStamps.put(tag, time);
            return time;
        }
        return -1;
    }

    public static long endTimeTrack(String tag, String sub) {
        if (Log.sPrintLog) {
            Long startTime = sTimeStamps.get(tag);
            if (startTime != null) {
                long timeCost = System.currentTimeMillis() - startTime;
                e("TimeCost#" + sub + "#" + tag, timeCost + "");
                sTimeStamps.remove(tag);
                return timeCost;
            }
        }
        return -1;
    }
}
