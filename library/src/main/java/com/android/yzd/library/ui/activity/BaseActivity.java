package com.android.yzd.library.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.yzd.library.R;
import com.android.yzd.library.event.EventCenter;
import com.android.yzd.library.module.analyze.AnalyzeModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * <p>Title:        BaseActivity
 * <p>Description:
 * <p>@author:      yezd
 * <p>Copyright:    Copyright (c) 2010-2017
 * <p>Company:      @咪咕动漫
 * <p>Create Time:  2017/7/12 下午3:30
 * <p>@author:
 * <p>Update Time:
 * <p>Updater:
 * <p>Update Comments:
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private AnalyzeModule mAnalyzeModule;
    private View rlContent;// 内容布局
    private Toolbar mToolBar;
    private ImageView mBtnBack;
    private TextView mBtnRight;
    private TextView mToolBarTitle;
    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isApplyEventBus()) {
            EventBus.getDefault().register(this);
        }
        onBeforeInit(savedInstanceState);
        applyTranslucent();
        setContentView(R.layout.activity_base);
        setContainerView();
        setBaseListener();
        initComponent(savedInstanceState);
        setListener();
    }

    /**
     * 是否开启butterKnife
     *
     * @return
     */
    protected boolean isApplyButterKnife() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnalyzeModule = new AnalyzeModule();
        mAnalyzeModule.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAnalyzeModule.onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (isApplyEventBus()) {
            EventBus.getDefault().unregister(this);
        }

        if (isApplyButterKnife() && mBind != null) {
            mBind.unbind();
        }
        super.onDestroy();
    }

    private void setBaseListener() {
        mToolBar = (Toolbar) findViewById(R.id.baseToolbar);
        mBtnBack = (ImageView) findViewById(R.id.btnBack);
        mBtnRight = (TextView) findViewById(R.id.btnRight);
        mToolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
        mBtnBack.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);
    }

    protected void setRightText(String rightText) {
        mBtnRight.setText(rightText);
    }

    protected void setToolBarVisible(boolean visible) {
        if (visible) {
            mToolBar.setVisibility(View.VISIBLE);
        } else {
            mToolBar.setVisibility(View.GONE);
        }
    }

    protected void setBtnBackVisible(boolean visible) {
        if (visible) {
            mBtnBack.setVisibility(View.VISIBLE);
        } else {
            mBtnBack.setVisibility(View.GONE);
        }
    }

    protected void setTitle(String title) {
        mToolBarTitle.setText(title);
    }

    public void setTitle(@StringRes int titleId) {
        mToolBarTitle.setText(titleId);

    }

    /**
     * 基础构建前的动作
     *
     * @param savedInstanceState
     */
    protected void onBeforeInit(Bundle savedInstanceState) {
    }

    /**
     * 实现该方法必须调用setContainerView方法
     */
    private void setContainerView() {


        if (setLayoutId() == -1)
            return;

        ViewStub contentStub = (ViewStub) findViewById(R.id.stub_content);
        contentStub.setLayoutResource(setLayoutId());
        rlContent = contentStub.inflate();


        if (isApplyButterKnife()) {
            mBind = ButterKnife.bind(this);
        }
    }



    /**
     * api大于19的时候 沉浸式状态栏的支持
     */
    @TargetApi(19)
    private void applyTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View parentView = contentFrameLayout.getChildAt(0);
            if (parentView != null) {
                parentView.setFitsSystemWindows(true);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCenter eventCenter) {
        eventComing(eventCenter);
    }

    protected void eventComing(EventCenter eventCenter) {

    }

    protected void setListener() {
    }

    protected void btnRightClick() {
    }

    protected boolean isApplyEventBus() {
        return false;
    }

    protected abstract
    @LayoutRes
    int setLayoutId();

    protected abstract void initComponent(Bundle savedInstanceState);

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnBack) {
            finish_Activity();
        } else if (id == R.id.btnRight) {
            btnRightClick();
        }
    }

    /**
     * 关闭Activity, 伴随动画
     */
    protected void finish_Activity() {
        this.finish();
        if (showFinishAnim()) {
            overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_bottom_out);
        }
    }

    protected boolean showFinishAnim() {
        return false;
    }
}
