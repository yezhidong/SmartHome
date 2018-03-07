package com.android.yzd.smarthome.entity;

import java.util.List;

/**
 * <p>Title:        UserLoginApi
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2018/3/2 下午10:21
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public class UserLoginApi {

    private List<LoginEntity> login;

    public List<LoginEntity> getLogin() {
        return login;
    }

    public void setLogin(List<LoginEntity> login) {
        this.login = login;
    }

}
