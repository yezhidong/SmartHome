package com.android.yzd.library.module;

import android.content.Context;

/**
 * <p>Title:        IBaseActivity
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2017/7/12 下午5:18
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public interface IModule {

    void init(Context context);

    void onCreate();

    void onResume();

    void onPause();

    void onDestroy();
}
