package com.android.yzd.smarthome.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.yzd.library.ui.activity.BaseActivity;
import com.android.yzd.library.util.Log;
import com.android.yzd.smarthome.R;
import com.android.yzd.smarthome.config.Config;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

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
    private ArrayList mArrayList = new ArrayList();
    private String json = "{\n" +
            "\t\"Register\" : \n" +
            "\t[\n" +
            "\t\t{\n" +
            "\t\t\t\"PhoneNum\" : \"15980767174\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"Password\" : \"123456\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"VerifyCode\" : \"888888\"\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";
    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponent(Bundle savedInstanceState) {
        mName = (EditText) findViewById(R.id.name);
        mPsd = (EditText) findViewById(R.id.psd);
        Button regist = (Button) findViewById(R.id.regist);
        Button login = (Button) findViewById(R.id.login);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String psd = mPsd.getText().toString().trim();
                OkGo.<String>post(Config.BASE_URL)
                        .upJson(json)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.d("yzd", "success");
                                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                Log.d("yzd", "error");
                                Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
