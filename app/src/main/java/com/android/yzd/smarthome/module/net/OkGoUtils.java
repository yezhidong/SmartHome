package com.android.yzd.smarthome.module.net;

import com.android.yzd.smarthome.config.Config;
import com.android.yzd.smarthome.entity.BaseEntity;
import com.android.yzd.smarthome.entity.UserRegisterApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableResponse;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * <p>Title:        OkGoUtils
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2018/2/11 上午11:07
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public class OkGoUtils {

    public static Observable<Response<String>> getPostObservable(UserRegisterApi baseEntity) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntity<UserRegisterApi>>() {
        }.getType();
        String toJson = gson.toJson(baseEntity, UserRegisterApi.class);
        return OkGo.<String>post(Config.BASE_URL)
                .upJson(toJson)
                .converter(new StringConvert())
                .adapt(new ObservableResponse<String>());
    }

}
