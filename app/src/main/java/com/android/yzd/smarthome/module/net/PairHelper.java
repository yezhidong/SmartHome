package com.android.yzd.smarthome.module.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.android.yzd.library.util.Log;
import com.android.yzd.library.util.UiHandler;
import com.android.yzd.smarthome.R;
import com.hiflying.smartlink.ISmartLinker;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v3.SnifferSmartLinker;
import com.hiflying.smartlink.v7.MulticastSmartLinker;

/**
 * <p>Title:        PairHelper
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2018/3/2 下午10:57
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public class PairHelper implements OnSmartLinkListener {

    private final Context mContext;
    private boolean mIsConnecting = false;
    private ISmartLinker mSnifferSmartLinker;
    private ProgressDialog mWaitingDialog;

    public PairHelper(Context context, int smartLinkVersion) {
        mContext = context;
        if (smartLinkVersion == 7) {
            mSnifferSmartLinker = MulticastSmartLinker.getInstance();
        } else {
            mSnifferSmartLinker = SnifferSmartLinker.getInstance();
        }
    }

    public void start(String ssid, String passWord) {
        if (!mIsConnecting) {
            if (mWaitingDialog == null) {
                mWaitingDialog = new ProgressDialog(mContext);
                mWaitingDialog.setMessage(mContext.getString(R.string.hiflying_smartlinker_waiting));
                mWaitingDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, mContext.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                mWaitingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mSnifferSmartLinker.setOnSmartLinkListener(null);
                        mSnifferSmartLinker.stop();
                        mIsConnecting = false;
                    }
                });
            }
            //设置要配置的ssid 和pswd
            try {
                mSnifferSmartLinker.setOnSmartLinkListener(this);
                //开始 smartLink
                mSnifferSmartLinker.start(mContext,
                        ssid,
                        passWord);
                mIsConnecting = true;
                mWaitingDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLinked(final SmartLinkedModule module) {
        Log.d("yzd", "mac" + module.getMac());
        Log.d("yzd", "ip" + module.getIp());
        Log.d("yzd", "moduleIp" + module.getModuleIP());
        UiHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext,
                        mContext.getString(R.string.hiflying_smartlinker_new_module_found),
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
                Toast.makeText(mContext,
                        mContext.getString(R.string.hiflying_smartlinker_completed),
                        Toast.LENGTH_SHORT).show();
                mIsConnecting = false;
            }
        });
    }


    @Override
    public void onTimeOut() {
        UiHandler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(mContext,
                        mContext.getString(R.string.hiflying_smartlinker_timeout),
                        Toast.LENGTH_SHORT).show();
                mIsConnecting = false;
                mWaitingDialog.dismiss();
            }
        });
    }
}
