package com.android.yzd.library.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.yzd.library.R;
import com.android.yzd.library.util.NetworkUtils;
import com.android.yzd.library.util.UiHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * <p>Title:        BaseLazyFragment
 * <p>Description:  延迟加载fragment(用于tab页面中有需要网络延迟加载fragment的基类)
 * <p>@author:      yzd
 * <p>Create Time:  2017/8/24 下午9:32
 */
public abstract class BaseLazyFragment extends LazyFragment {
    protected String TAG = getClass().getSimpleName();
    public static final int STATE_LOADING = 1;
    public static final int STATE_COMPLETED = 2;
    public static final int STATE_ERROR = -1;
    public static final int STATE_NODATA = 3;//无数据
    public static final int STATE_LOGIN = 4;

    public Context mContext;

    @IntDef(value = {STATE_LOADING, STATE_COMPLETED, STATE_ERROR, STATE_NODATA})
    protected @interface ViewState {
    }

    /**
     * 是否初始化
     */
    private boolean mIsInit = false;

    /**
     * 内容布局
     */
    protected View rlContent;
    /**
     * 加载中
     */
    protected ViewGroup loadingLayout;
    /**
     * 加载中文字
     */
    protected TextView loadingTxt;
    /********
     * 重新加载布局
     ******/
    protected View reloadLayout;
    /**
     * 重新加载图片
     **/
    protected ImageView reloadImage;
    /**
     * 重新加载提示语视图
     */
    protected TextView reloadTextView;
    private int currentState = -1;

    protected ViewGroup noDataLayout;// 无数据提示
    private ImageView noDataImageView;// 无数据显示的图片
    private TextView noDataTextView;// 无数据提示文字
    private TextView noDataButton;//无数据功能按钮
    protected Activity mActivity;

    private View.OnClickListener mOnReloadListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mActivity = getActivity();

    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_lazy_base);
        initView();
        setBaseListeners();
        setContainerView();
        initComponent(rlContent);
        setListener();
        initData();
    }

    /**
     *
     */
    private void initView() {

        this.mOnReloadListener = getReloadListener();

    }

    /**
     * 实现该方法必须调用setContainerView方法
     */
    private void setContainerView() {

        if (getLayout() == -1)
            return;

        ViewStub contentStub = (ViewStub) findViewById(R.id.fragment_stub_content);
        contentStub.setLayoutResource(getLayout());
        rlContent = contentStub.inflate();

    }

    /**
     * 设置layout
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     * 用于初始化内容区域组件
     *
     * @param contentView
     */
    public abstract void initComponent(View contentView);

    /**
     * 用于初始化数据
     */
    public abstract void initData();

    /**
     * 用于设置监听器
     */
    public abstract void setListener();

    /**
     * 获取页面当前状态
     *
     * @return
     */
    public int getState() {
        return currentState;
    }

    /**
     * description:设置页面当前状态
     *
     * @author: jinyuef
     * @Create Time:2016/1/8
     * @version:
     */
    protected void setStateLoading(long delay) {
        UiHandler.remove(BaseLazyFragment.this);
        UiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setStateLoading();
            }
        }, BaseLazyFragment.this, delay);
    }

    /**
     * description:加载中
     *
     * @author: jinyuef
     * @Create Time:2016/1/8
     * @version:
     */
    protected void setStateLoading() {
        UiHandler.remove(BaseLazyFragment.this);
        currentState = STATE_LOADING;
        if (rlContent != null) {
            rlContent.setVisibility(View.GONE);
        }
        if (reloadLayout != null) {
            reloadLayout.setVisibility(View.GONE);
        }
        if (noDataLayout != null) {
            noDataLayout.setVisibility(View.GONE);
        }
        if (loadingLayout == null) {
            /******加载中布局*******/
            ViewStub stub = (ViewStub) findViewById(R.id.fragment_stub_progress);
            if (stub != null) {
                View view = stub.inflate();
                loadingLayout = (ViewGroup) view.findViewById(R.id.rlProgressWait);
                loadingTxt = (TextView) view.findViewById(R.id.wait_img);
            }
        }
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * description:加载完毕
     *
     * @author: jinyuef
     * @Create Time:2016/1/8
     * @version:
     */
    protected void setStateCompleted() {
        UiHandler.remove(BaseLazyFragment.this);
        currentState = STATE_COMPLETED;
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
        if (reloadLayout != null) {
            reloadLayout.setVisibility(View.GONE);
        }
        if (noDataLayout != null) {
            noDataLayout.setVisibility(View.GONE);
        }
        if (rlContent != null) {
            rlContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 接口数据解析异常请调用该方法设置错误页
     */
    protected void setStateErrorForApiData() {
        setStateError(R.string.exception_fail_api_msg);
    }

    /**
     * description:加载错误
     *
     * @author: jinyuef
     * @Create Time:2016/1/8
     * @version:
     */
    protected void setStateError(@StringRes int strResId) {
        UiHandler.remove(BaseLazyFragment.this);
        currentState = STATE_ERROR;
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
        if (noDataLayout != null) {
            noDataLayout.setVisibility(View.GONE);
        }
        if (rlContent != null) {
            rlContent.setVisibility(View.GONE);
        }
        if (reloadLayout == null) {
            ViewStub stub = (ViewStub) findViewById(R.id.fragment_stub_reload);
            if (stub != null) {
                View view = stub.inflate();
                reloadLayout = view.findViewById(R.id.reload_layout);
                reloadImage = (ImageView) view.findViewById(R.id.img_reload);
                reloadTextView = (TextView) view.findViewById(R.id.txt_reload);
            }
        }
        setReloadPromptContentByNet(strResId);
        if (reloadLayout != null) {
            reloadLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * description:加载无数据
     *
     * @author: jinyuef
     * @Create Time:2016/2/6
     * @version:
     */
    protected void setStateNoData() {
        UiHandler.remove(BaseLazyFragment.this);
        currentState = STATE_NODATA;
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
        if (noDataLayout != null) {
            noDataLayout.setVisibility(View.GONE);
        }
        if (rlContent != null) {
            rlContent.setVisibility(View.GONE);
        }
        if (noDataLayout == null) {
            /*********无数据显示布局********/
            initNoDataView();
        }
        if (noDataLayout != null) {
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }

    protected void setStateLogin() {
        UiHandler.remove(BaseLazyFragment.this);
        currentState = STATE_LOGIN;
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
        if (noDataLayout != null) {
            noDataLayout.setVisibility(View.GONE);
        }
        if (rlContent != null) {
            rlContent.setVisibility(View.GONE);
        }
        if (reloadLayout != null) {
            reloadLayout.setVisibility(View.GONE);
        }
    }


    /**
     * 根据网络情况设置重新加载页面的提示语内容
     */
    private void setReloadPromptContentByNet(@StringRes int strResId) {
        try {
            if (this.isAdded()) {
                if (!NetworkUtils.isNetworkAvailable()) {
                    reloadImage.setImageResource(R.drawable.ic_common_no_network);
                    reloadTextView.setText(getContext().getString(R.string.exception_none_net_msg));

                } else {
                    reloadImage.setImageResource(R.drawable.ic_common_bad_network);
                    reloadTextView.setText(strResId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * description:设置当页面异常，点击异常页面能重新加载数据页面
     *
     * @author: jinyuef
     * @Create Time:2016/1/8
     * @version:
     */
    protected View.OnClickListener getReloadListener() {
        return null;
    }

    /**
     * description:设置基础页面监听器
     *
     * @author: jinyuef
     * @Create Time:2016/1/8
     * @version:
     */
    private void setBaseListeners() {
        if (mOnReloadListener != null) {
            if (reloadLayout == null) {
                ViewStub stub = (ViewStub) findViewById(R.id.fragment_stub_reload);
                if (stub != null) {
                    View view = stub.inflate();
                    reloadLayout = view.findViewById(R.id.reload_layout);
                    reloadImage = (ImageView) view.findViewById(R.id.img_reload);
                    reloadTextView = (TextView) view.findViewById(R.id.txt_reload);
                }
            }
//            setReloadPromptContentByNet();
            if (reloadLayout != null) {
                reloadLayout.setOnClickListener(mOnReloadListener);
            }
        }
    }

    /**
     * 设置无数据提示图
     *
     * @param resId
     */
    public void setNoDataImage(int resId) {
        if (noDataImageView == null) {
            /*********无数据显示布局********/
            initNoDataView();
        }
        if (noDataImageView != null) {
            noDataImageView.setImageResource(resId);
        }
    }

    /**
     * 设置无内容提示文字
     *
     * @param text
     */
    public void setNoDataText(String text) {
        if (noDataTextView == null) {
            /*********无数据显示布局********/
            initNoDataView();
        }
        if (noDataTextView != null) {
            noDataTextView.setText(text);
        }
    }

    /**
     * 设置无内容功能按钮文字，并显示功能按钮
     *
     * @param text 文字
     */
    public void setNoDataButtonText(String text) {
        if (noDataButton == null) {
            /*********无数据显示布局********/
            initNoDataView();
        }

        if (noDataButton != null) {
            noDataButton.setText(text);
            noDataButton.setVisibility(View.VISIBLE);
        }
    }

    public TextView getNoDataButton() {
        if (noDataButton == null) {
            initNoDataView();
        }
        return noDataButton;
    }

    /**
     * 设置无内容功能按钮监听器
     */
    public void setNoDataButtonListener(View.OnClickListener listener) {
        if (noDataButton == null) {
            /*********无数据显示布局********/
            initNoDataView();
        }

        if (noDataButton != null) {
            noDataButton.setOnClickListener(listener);
        }
    }

    /***********
     * 无数据显示布局
     ********/
    private void initNoDataView() {
        ViewStub stub = (ViewStub) findViewById(R.id.fragment_stub_nodata);
        if (stub != null) {
            View view = stub.inflate();
            noDataLayout = (ViewGroup) view.findViewById(R.id.no_data);
            noDataImageView = (ImageView) view.findViewById(R.id.no_data_image);
            noDataTextView = (TextView) view.findViewById(R.id.no_data_txt);
            noDataButton = (TextView) view.findViewById(R.id.no_data_button);
        }
    }



    /**
     * desc:获取activity
     * <p/>
     * author: jinyuef
     * date: 2016/05/26
     *
     * @return
     */
    protected Activity getBaseActivity() {
        return mActivity;
    }

    /**
     * 返回按钮
     */
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        MobclickAgent.onPageEnd(TAG);
    }

    /**
     * desc:
     * <p/>
     * author: jinyuef
     * date: 2017/8/9
     */
    protected boolean isFragmentShow() {
        if (getUserVisibleHint()) {
            if (getParentFragment() != null) {
                return getParentFragment().getUserVisibleHint();
            } else {
                return true;
            }
        }
        return false;
    }

}
