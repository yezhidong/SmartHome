package com.android.yzd.library.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
/**
 * <p>Title:        LazyFragment
 * <p>Description:
 * <p>@author:      yzd
 * <p>Create Time:  2017/8/24 下午9:31
 */
public class LazyFragment extends BaseFragment {
    private boolean isInit = false;
    private Bundle savedInstanceState;
    public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";
    private boolean isLazyLoad = true;
    private FrameLayout layout;
    private boolean isStart = false;

    public LazyFragment() {
    }

    protected final void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            this.isLazyLoad = bundle.getBoolean("intent_boolean_lazyLoad", this.isLazyLoad);
        }

        if(this.isLazyLoad) {
            if(this.getUserVisibleHint() && !this.isInit) {
                this.isInit = true;
                this.savedInstanceState = savedInstanceState;
                this.onCreateViewLazy(savedInstanceState);
            } else {
                this.layout = new FrameLayout(this.getApplicationContext());
                this.layout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                super.setContentView(this.layout);
            }
        } else {
            this.isInit = true;
            this.onCreateViewLazy(savedInstanceState);
        }

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && !this.isInit && this.getContentView() != null) {
            this.isInit = true;
            this.onCreateViewLazy(this.savedInstanceState);
            this.onResumeLazy();
        }

        if(this.isInit && this.getContentView() != null) {
            if(isVisibleToUser) {
                this.isStart = true;
                this.onFragmentStartLazy();
            } else {
                this.isStart = false;
                this.onFragmentStopLazy();
            }
        }

    }

    /** @deprecated */
    @Deprecated
    public final void onStart() {
        super.onStart();
        if(this.isInit && !this.isStart && this.getUserVisibleHint()) {
            this.isStart = true;
            this.onFragmentStartLazy();
        }

    }

    /** @deprecated */
    @Deprecated
    public final void onStop() {
        super.onStop();
        if(this.isInit && this.isStart && this.getUserVisibleHint()) {
            this.isStart = false;
            this.onFragmentStopLazy();
        }

    }

    protected void onFragmentStartLazy() {
    }

    protected void onFragmentStopLazy() {
    }

    protected void onCreateViewLazy(Bundle savedInstanceState) {
    }

    protected void onResumeLazy() {
    }

    protected void onPauseLazy() {
    }

    protected void onDestroyViewLazy() {
    }

    public void setContentView(int layoutResID) {
        if(this.isLazyLoad && this.getContentView() != null && this.getContentView().getParent() != null) {
            this.layout.removeAllViews();
            View view = this.inflater.inflate(layoutResID, this.layout, false);
            this.layout.addView(view);
        } else {
            super.setContentView(layoutResID);
        }

    }

    public void setContentView(View view) {
        if(this.isLazyLoad && this.getContentView() != null && this.getContentView().getParent() != null) {
            this.layout.removeAllViews();
            this.layout.addView(view);
        } else {
            super.setContentView(view);
        }

    }

    /** @deprecated */
    @Deprecated
    public final void onResume() {
        super.onResume();
        if(this.isInit) {
            this.onResumeLazy();
        }

    }

    /** @deprecated */
    @Deprecated
    public final void onPause() {
        super.onPause();
        if(this.isInit) {
            this.onPauseLazy();
        }

    }

    /** @deprecated */
    @Deprecated
    public final void onDestroyView() {
        super.onDestroyView();
        if(this.isInit) {
            this.onDestroyViewLazy();
        }

        this.isInit = false;
    }
}