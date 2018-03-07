package com.android.yzd.smarthome.entity;
/**
 * <p>Title:        LoginEntity
 * <p>Description:
 * <p>@author:      yzd
 * <p>Create Time:  2018/3/5 下午10:08
 */
public class LoginEntity {

    private String userId;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}