package com.android.yzd.smarthome.entity.json;

import java.lang.reflect.Type;
/**
 * <p>Title:        ParameterizedType
 * <p>Description:
 * <p>@author:      yzd
 * <p>Create Time:  2018/3/5 下午10:35
 */
public interface ParameterizedType extends Type {

    // 返回Map<String,User>里的String和User，所以这里返回[String.class,User.clas]
    Type[] getActualTypeArguments();

    // Map<String,User>里的Map,所以返回值是Map.class
    Type getRawType();

    // 用于这个泛型上中包含了内部类的情况,一般返回null
    Type getOwnerType();
}
