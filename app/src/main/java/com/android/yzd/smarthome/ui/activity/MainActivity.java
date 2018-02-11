package com.android.yzd.smarthome.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.yzd.library.ui.activity.BaseActivity;
import com.android.yzd.library.ui.widget.SViewPager;
import com.android.yzd.smarthome.R;
import com.android.yzd.smarthome.ui.fragment.my.MyFragment;
import com.android.yzd.smarthome.ui.fragment.room.RoomFragment;
import com.android.yzd.smarthome.ui.fragment.situation.SituationFragment;

public class MainActivity extends BaseActivity {

    private int[] tabTitle = {
            R.string.title_home,
            R.string.title_dashboard,
            R.string.title_notifications};

    private SViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent(Bundle savedInstanceState) {
        setBtnBackVisible(false);
        setTitle(R.string.title_home);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPager = (SViewPager) findViewById(R.id.content);
        mViewPager.setCanScroll(false);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(tabTitle.length);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle(R.string.title_home);
                    mViewPager.setCurrentItem(0, false);
                    return true;
                case R.id.navigation_dashboard:
                    setTitle(R.string.title_dashboard);
                    mViewPager.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_notifications:
                    setTitle(R.string.title_notifications);
                    mViewPager.setCurrentItem(2, false);
                    return true;
            }
            return false;
        }

    };

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new RoomFragment();
                case 1:
                    return new SituationFragment();
                case 2:
                    return new MyFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }
    }

}
