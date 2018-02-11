package com.android.yzd.library.module.analyze;

import android.content.Context;

/**
 * <p>Title:        IAnalyzeModule
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2017/7/12 下午8:06
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public interface IAnalyzeModule {

    void init(Context context);

    void onResume(Context context);

    void onPause(Context context);
}
