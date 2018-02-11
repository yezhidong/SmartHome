package com.android.yzd.library.module.http;


import retrofit2.Retrofit;

/**
 * desc: 接口管理类
 * author:caowy
 * date:2016-05-19
 */
public class ApiServiceManager {

    private static final String TAG = "ApiServiceManager";
    /**
     * retrofit
     */
    private Retrofit mRetrofit;

    /**
     * 构造
     * author:caowy
     * date:2016-08-25
     */
    private ApiServiceManager() {
        mRetrofit = new Retrofit.Builder()
            .baseUrl("")
            .client(new OkHttpClientManager().getClient())
            .build();
    }


    /**
     * 创建接口
     * author:caowy
     * date:2016-08-25
     *
     * @param <T>
     * @param apiService
     * @return
     */
    public static <T> T create(Class<T> apiService) {
        return InstanceHolder.instance.createApiService(apiService);
    }

    /**
     * desc: 创建接口服务
     * author:caowy
     * date:2016-05-19
     *
     * @param <T>
     * @param service
     * @return
     */
    private <T> T createApiService(Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * 单例
     * author:caowy
     * date:2016-08-25
     */
    private static class InstanceHolder {
        /**
         *
         */
        private static  ApiServiceManager instance = new ApiServiceManager();
    }

    /**
     * 创建新的单例
     */
    public static void createNewInstance(){
        InstanceHolder.instance=new ApiServiceManager();
    }

}
