package com.android.yzd.library.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * <p>Title:        SViewPager
 * <p>Description:
 * <p>@author:      yzd
 * <p>Create Time:  2017/8/24 下午9:05
 */
public class SViewPager extends ViewPager {
    private boolean canScroll = false;

    public SViewPager(Context context) {
        super(context);
    }

    public SViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(this.canScroll) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException var3) {
                var3.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.canScroll?super.onTouchEvent(event):false;
    }

    public void toggleLock() {
        this.canScroll = !this.canScroll;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    public boolean isCanScroll() {
        return this.canScroll;
    }
}