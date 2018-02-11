package com.android.yzd.smarthome.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import static android.content.Context.WIFI_SERVICE;

/**
 * <p>Title:        WifiUtils
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2017/10/9 下午3:17
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public class WifiUtils {

    private WifiUtils() {

    }

    public static WifiUtils getInstants() {
        return Holder.single;
    }

    private static class Holder {
        private static WifiUtils single = new WifiUtils();
    }

    /**
     * 获取所连接Wi-Fi的 ssid
     * @param context
     * @return
     */
    public String getSSid(Context context){

        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        if(wm != null){
            WifiInfo wi = wm.getConnectionInfo();
            if(wi != null){
                String ssid = wi.getSSID();
                if(ssid.length()>2 && ssid.startsWith("\"") && ssid.endsWith("\"")){
                    return ssid.substring(1,ssid.length()-1);
                }else{
                    return ssid;
                }
            }
        }

        return "";
    }
}
