package com.lan.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lan.googleplay.R;
import com.lan.googleplay.ui.fragment.BaseFragment;
import com.lan.googleplay.ui.fragment.FragmentFactory;
import com.lan.googleplay.ui.view.PagerTab;
import com.lan.googleplay.utils.UIUtils;

public class MainActivity extends BaseActivity {

    private PagerTab pt;
    private String[] array;
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pt = (PagerTab) findViewById(R.id.Pager_Tab);
        vp = (ViewPager) findViewById(R.id.ViewPager);
        array = UIUtils.getStringArray(R.array.tab_names);
        FragmentManager fm = getSupportFragmentManager();
        vp.setAdapter(new MyAdapter(fm));
        pt.setViewPager(vp);
        pt.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //选中当前页面时加载数据
            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.createFragment(position);
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createFragment(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return array[position];
        }

        @Override
        public int getCount() {
            return array.length;
        }
    }
}
