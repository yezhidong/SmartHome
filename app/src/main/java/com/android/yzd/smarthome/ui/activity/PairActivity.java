package com.android.yzd.smarthome.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.yzd.library.ui.activity.BaseActivity;
import com.android.yzd.library.util.Log;
import com.android.yzd.library.util.UiHandler;
import com.android.yzd.smarthome.R;
import com.android.yzd.smarthome.module.net.PairHelper;
import com.android.yzd.smarthome.utils.WifiUtils;
import com.hiflying.smartlink.ISmartLinker;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v3.SnifferSmartLinker;
import com.hiflying.smartlink.v7.MulticastSmartLinker;

import butterknife.BindView;

import static android.R.attr.type;

/**
 * <p>Title:        PairActivity
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2017/10/9 下午3:07
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public class PairActivity extends BaseActivity {

    public static final String EXTRA_SMARTLINK_VERSION = "EXTRA_SMARTLINK_VERSION";

    @BindView(R.id.editText_hiflying_smartlinker_ssid)
    EditText mSsidEditText;
    @BindView(R.id.editText_hiflying_smartlinker_password)
    EditText mPasswordEditText;
    @BindView(R.id.button_hiflying_smartlinker_start)
    Button mStartButton;
    private PairHelper mPairHelper;

    public static void start(Context context, int smartLinkVersion) {
        Intent intent = new Intent(context, PairActivity.class);
        intent.putExtra(EXTRA_SMARTLINK_VERSION, smartLinkVersion);
        context.startActivity(intent);
    }

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {
        super.onBeforeInit(savedInstanceState);
        int smartLinkVersion = getIntent().getIntExtra(EXTRA_SMARTLINK_VERSION, 3);
        mPairHelper = new PairHelper(this, smartLinkVersion);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pair;
    }

    @Override
    protected void initComponent(Bundle savedInstanceState) {
        mSsidEditText.setText(WifiUtils.getInstants().getSSid(this));
    }

    @Override
    protected void setListener() {
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPairHelper.start(mSsidEditText.getText().toString().trim(),
                        mPasswordEditText.getText().toString().trim());
            }
        });
    }

    @Override
    protected boolean isApplyButterKnife() {
        return true;
    }

}
