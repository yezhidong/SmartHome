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
import com.android.yzd.smarthome.utils.WifiUtils;
import com.hiflying.smartlink.ISmartLinker;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v3.SnifferSmartLinker;
import com.hiflying.smartlink.v7.MulticastSmartLinker;

import butterknife.BindView;

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
public class PairActivity extends BaseActivity implements OnSmartLinkListener {

    public static final String EXTRA_SMARTLINK_VERSION = "EXTRA_SMARTLINK_VERSION";
    protected ISmartLinker mSnifferSmartLinker;

    @BindView(R.id.editText_hiflying_smartlinker_ssid)
    EditText mSsidEditText;
    @BindView(R.id.editText_hiflying_smartlinker_password)
    EditText mPasswordEditText;
    @BindView(R.id.button_hiflying_smartlinker_start)
    Button mStartButton;
    private boolean mIsConncting = false;
    private ProgressDialog mWaitingDialog;


    public void start(Context context, int type) {

        Intent intent = new Intent(context, PairActivity.class);
        intent.putExtra(EXTRA_SMARTLINK_VERSION, type);
        context.startActivity(intent);
    }

    @Override
    protected void onBeforeInit(Bundle savedInstanceState) {
        super.onBeforeInit(savedInstanceState);
        int smartLinkVersion = getIntent().getIntExtra(EXTRA_SMARTLINK_VERSION, 3);
        if (smartLinkVersion == 7) {
            mSnifferSmartLinker = MulticastSmartLinker.getInstance();
        } else {
            mSnifferSmartLinker = SnifferSmartLinker.getInstance();
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pair;
    }

    @Override
    protected void initComponent(Bundle savedInstanceState) {
        mSsidEditText.setText(WifiUtils.getInstants().getSSid(this));

        mWaitingDialog = new ProgressDialog(this);
        mWaitingDialog.setMessage(getString(R.string.hiflying_smartlinker_waiting));
        mWaitingDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        mWaitingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {

                mSnifferSmartLinker.setOnSmartLinkListener(null);
                mSnifferSmartLinker.stop();
                mIsConncting = false;
            }
        });
    }

    @Override
    protected void setListener() {
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsConncting) {

                    //设置要配置的ssid 和pswd
                    try {
                        mSnifferSmartLinker.setOnSmartLinkListener(PairActivity.this);
                        //开始 smartLink
                        mSnifferSmartLinker.start(getApplicationContext(), mPasswordEditText.getText().toString().trim(),
                                mSsidEditText.getText().toString().trim());
                        mIsConncting = true;
                        mWaitingDialog.show();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected boolean isApplyButterKnife() {
        return true;
    }

    @Override
    public void onLinked(final SmartLinkedModule module) {
        Log.d("yzd", "mac" + module.getMac());
        Log.d("yzd", "ip" + module.getIp());
        Log.d("yzd", "moduleIp" + module.getModuleIP());
        UiHandler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), getString(R.string.hiflying_smartlinker_new_module_found),
                        Toast.LENGTH_SHORT).show();
                mWaitingDialog.dismiss();
            }
        });
    }


    @Override
    public void onCompleted() {

        UiHandler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), getString(R.string.hiflying_smartlinker_completed),
                        Toast.LENGTH_SHORT).show();
                mIsConncting = false;
            }
        });
    }


    @Override
    public void onTimeOut() {

        UiHandler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), getString(R.string.hiflying_smartlinker_timeout),
                        Toast.LENGTH_SHORT).show();
                mIsConncting = false;
                mWaitingDialog.dismiss();
            }
        });
    }
}
