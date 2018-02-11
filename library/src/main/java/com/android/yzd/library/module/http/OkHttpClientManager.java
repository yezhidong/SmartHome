package com.android.yzd.library.module.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * <p>Title:        OkHttpClientManager
 * <p>Description:
 * <p>@author:      yzd
 * <p>Create Time:  2017/10/10 上午9:50
 */
public class OkHttpClientManager {

    /**
     * client
     */
    private OkHttpClient mClient;

    /**
     * 创建
     * author:yzd
     * date:2016-12-15
     */
    private void initClient() {
        OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS);
        mClient = builder.build();
    }

    /**
     * author:yzd
     * date:2016-12-15
     *
     * @return
     */
    public OkHttpClient getClient() {
        synchronized (OkHttpClientManager.this) {
            if(mClient == null) {
                initClient();
            }
        }
        return mClient;
    }

}
