package com.android.yzd.smarthome.entity.json;

import java.lang.reflect.Type;
/**
 * <p>Title:        ParameterizedTypeImpl
 * <p>Description:
 * <p>@author:      yzd
 * <p>Create Time:  2018/3/5 下午10:36
 */
public class ParameterizedTypeImpl implements ParameterizedType {
    private final Class raw;
    private final Type[] args;
    public ParameterizedTypeImpl(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }
    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }
    @Override
    public Type getRawType() {
        return raw;
    }
    @Override
    public Type getOwnerType() {return null;}
}