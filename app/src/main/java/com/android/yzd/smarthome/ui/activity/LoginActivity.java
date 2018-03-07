package com.android.yzd.smarthome.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.yzd.library.ui.activity.BaseActivity;
import com.android.yzd.library.util.Log;
import com.android.yzd.smarthome.R;
import com.android.yzd.smarthome.entity.BaseEntity;
import com.android.yzd.smarthome.entity.RegisterEntity;
import com.android.yzd.smarthome.entity.UserRegisterApi;
import com.android.yzd.smarthome.module.net.OkGoUtils;
import com.android.yzd.smarthome.utils.SoftKeyUtils;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * <p>Title:        LoginActivity
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2017/9/27 下午8:05
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public class LoginActivity extends BaseActivity {

    private EditText mName;
    private EditText mPsd;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponent(Bundle savedInstanceState) {
        mName = (EditText) findViewById(R.id.name);
        mPsd = (EditText) findViewById(R.id.psd);
        final Button regist = (Button) findViewById(R.id.regist);
        Button login = (Button) findViewById(R.id.login);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftKeyUtils.getInstants().hide(LoginActivity.this, mPsd);
                if (!isEditTextEmpty()) {
                    return;
                }
                BaseEntity<UserRegisterApi> entity = new BaseEntity<>();
                UserRegisterApi userRegisterApi = new UserRegisterApi();
                RegisterEntity registerEntity = new RegisterEntity();
//                registerEntity.setPassword(mPsd.getText().toString().trim());
//                registerEntity.setPhoneNum(mName.getText().toString().trim());
                registerEntity.setPassword("2121212121");
                registerEntity.setPhoneNum("15980767174");
                registerEntity.setVerifyCode("888888");
                userRegisterApi.setRegister(registerEntity);
                entity.setData(userRegisterApi);
                OkGoUtils.getPostObservable(userRegisterApi).subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<String> stringResponse) {
                        Log.d("yzd", "success");
                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("yzd", "error");
                        Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftKeyUtils.getInstants().hide(LoginActivity.this, mPsd);
                if (isEditTextEmpty()) {
                    return;
                }
                OkGoUtils.getPostObservable(null).subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<String> stringResponse) {
                        Log.d("yzd", "success");
                        PairActivity.start(LoginActivity.this, 3);
                        LoginActivity.this.finish_Activity();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("yzd", "error");
                        Toast.makeText(LoginActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });
    }

    private boolean isEditTextEmpty() {
        if (TextUtils.isEmpty(mName.getText()) || TextUtils.isEmpty(mPsd.getText())) {
            Toast.makeText(LoginActivity.this, "账号或者密码不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
