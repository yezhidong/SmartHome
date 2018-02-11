package com.android.yzd.library.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.android.yzd.library.BaseApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 网络帮助类
 * Created by zhouL on 2016/2/7.
 */
public class NetworkUtils {
    public static final int NETWORK_TYPE_NONE = -1;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_WIFI = 1;
    public static final int NETWORK_TYPE_2G = 2;
    public static final int NETWORK_TYPE_3G = 3;
    public static final int NETWORK_TYPE_4G = 4;

    public static final String NETWORK_TYPESTRING_UNKNOWN = "?";
    public static final String NETWORK_TYPESTRING_NONE = "-";
    public static final String NETWORK_TYPESTRING_WIFI = "WiFi";
    public static final String NETWORK_TYPESTRING_2G = "2G";
    public static final String NETWORK_TYPESTRING_3G = "3G";
    public static final String NETWORK_TYPESTRING_4G = "4G";

    public static interface NetworkListener {
        void onNetworkStatusChanged(boolean isNetworkAvailable, NetInfo netInfo);
    }

    public static class NetInfo {
        public int type;
        public int subType;
        public String extra;
    }

    private static NetInfo sCurNetworkInfo;

    private static final List<NetworkListener> sNetworkListeners = new ArrayList<NetworkListener>();

    public static void addNetworkListener(NetworkListener listener) {
        sNetworkListeners.add(listener);
    }

    public static void removeNetworkListener(NetworkListener listener) {
        sNetworkListeners.remove(listener);
    }

    /**
     * 类型转文字
     *
     * @param type
     * @return
     */
    public static String typeIntToString(int type) {
        switch (type) {
            case NETWORK_TYPE_WIFI:
                return NETWORK_TYPESTRING_WIFI;
            case NETWORK_TYPE_2G:
                return NETWORK_TYPESTRING_2G;
            case NETWORK_TYPE_3G:
                return NETWORK_TYPESTRING_3G;
            case NETWORK_TYPE_4G:
                return NETWORK_TYPESTRING_4G;
            case NETWORK_TYPE_UNKNOWN:
                return NETWORK_TYPESTRING_UNKNOWN;
            default:
                return NETWORK_TYPESTRING_UNKNOWN;
        }
    }

    public static String getNetworkTypeString() {
        int type = getNetworkType();
        return typeIntToString(type);
    }

    public static NetInfo getNetworkInfo() {
        synchronized (NetworkUtils.class) {
            if (sCurNetworkInfo == null) {
                updateNetworkInfo();
            }
        }
        return sCurNetworkInfo;
    }

    private static void updateNetworkInfo() {
        ConnectivityManager connManager = (ConnectivityManager) BaseApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        NetInfo netInfo = new NetInfo();
        if (info == null || !info.isAvailable()) {
            netInfo.type = NETWORK_TYPE_NONE;
            netInfo.subType = NETWORK_TYPE_NONE;
        } else {
            int networkType = NETWORK_TYPE_NONE;
            int type = info.getType();
            int subType = info.getSubtype();
            if (type == ConnectivityManager.TYPE_WIFI) {
                System.out.println("CONNECTED VIA WIFI");
                networkType = NETWORK_TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                switch (subType) {
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        networkType = NETWORK_TYPE_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        // case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case 12:
                        // case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case 14:
                        // case TelephonyManager.NETWORK_TYPE_HSPAP:
                    case 15:
                    case 17:
                    case 18:
                        networkType = NETWORK_TYPE_3G;
                        break;
                    // case TelephonyManager.NETWORK_TYPE_LTE:
                    case 13:
                        networkType = NETWORK_TYPE_4G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    default:
                        networkType = NETWORK_TYPE_UNKNOWN;
                        break;
                }
            }
            netInfo.type = networkType;
            netInfo.subType = subType;
            netInfo.extra = info.getExtraInfo();
        }
        sCurNetworkInfo = netInfo;
    }

    private static void notifyNetworkListeners() {
        for (Iterator<NetworkListener> iterator = sNetworkListeners.iterator(); iterator.hasNext();) {
            NetworkListener listener = iterator.next();
            if (listener != null) {
                listener.onNetworkStatusChanged(sCurNetworkInfo.type != NETWORK_TYPE_NONE, sCurNetworkInfo);
            } else {
                iterator.remove();
            }
        }
    }

    /**
     * 判断网络类型，2G\3G\4G\WIFI\NONE
     * @return
     */
    public static int getNetworkType() {
        return getNetworkInfo().type;
    }

    /**
     * 判断网络是否可用
     * @return
     */
    public static boolean isNetworkAvailable() {
        return getNetworkType() != NETWORK_TYPE_NONE;
    }

    /**
     * 是否是wifi
     *
     * @return
     */
    public static boolean isWifi() {
        return NetworkUtils.NETWORK_TYPE_WIFI == getNetworkType();
    }

    public static boolean is4G() {
        return NetworkUtils.NETWORK_TYPE_4G == getNetworkType();
    }

    /**
     * 是否使用移动网络
     *
     * @return
     */
    public static boolean isUsingMobileNetwork() {
        int networkType = getNetworkType();
        return networkType == NETWORK_TYPE_2G || networkType == NETWORK_TYPE_3G;
    }

    public static boolean isCMWAP() {
        String currentAPN = "";
        ConnectivityManager conManager = (ConnectivityManager) BaseApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        currentAPN = info.getExtraInfo();

        if (currentAPN == null || currentAPN == "") {
            return false;
        } else {
            if (currentAPN.toLowerCase().equals("cmwap")) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static String getWiFiSSID() {
        WifiManager mWifi = (WifiManager) BaseApplication.getApplication().getSystemService(Context.WIFI_SERVICE);

        if (mWifi != null) {
            WifiInfo wifiInfo = mWifi.getConnectionInfo();
            if (wifiInfo != null) {
                return wifiInfo.getSSID();
            }
        }
        return null;
    }

    private static ConnectBroadcastReceiver sConnectBroadcastReceiver;

    public static void init(Context context) {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        sConnectBroadcastReceiver = new ConnectBroadcastReceiver();
        context.registerReceiver(sConnectBroadcastReceiver, filter);
    }

    public static void release(Context context) {
        if (sConnectBroadcastReceiver != null) {
            context.unregisterReceiver(sConnectBroadcastReceiver);
            sConnectBroadcastReceiver = null;
        }
    }

    public static class ConnectBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                synchronized (NetworkUtils.class) {
                    updateNetworkInfo();
                    notifyNetworkListeners();
                }
            }
        }

    }
}
