package com.android.yzd.library;

import com.android.yzd.library.module.analyze.AnalyzeModule;
import com.android.yzd.library.module.http.HttpModule;

/**
 * <p>Title:        Application
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2017/7/12 下午2:58
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public class BaseApplication extends android.app.Application {

    private static BaseApplication sInstance;

    public static BaseApplication getApplication() {
        return sInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        // umeng初始化
        new AnalyzeModule().init(this);

        // http初始化
        new HttpModule().init(this);
    }

}
