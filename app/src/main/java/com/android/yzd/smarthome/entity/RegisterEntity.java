package com.android.yzd.smarthome.entity;

/**
 * <p>Title:        RegisterEntity
 * <p>Description:
 * <p>@author:      yzd
 * <p>Create Time:  2018/3/5 下午10:07
 */
public class RegisterEntity {

    private String phoneNum;
    private String password;
    private String verifyCode;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}