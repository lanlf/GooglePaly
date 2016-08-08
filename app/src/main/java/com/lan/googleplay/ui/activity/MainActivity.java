package com.lan.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lan.googleplay.R;
import com.lan.googleplay.ui.fragment.BaseFragment;
import com.lan.googleplay.ui.fragment.FragmentFactory;
import com.lan.googleplay.ui.view.PagerTab;
import com.lan.googleplay.utils.UIUtils;

public class MainActivity extends BaseActivity {

    private PagerTab pt;
    private String[] array;
    private ViewPager vp;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
       // setTitle(R.string.app_name);
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

    private void initToolBar() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.tb_main);
        if (toolBar != null) {
            toolBar.setTitle(R.string.app_name);
            //toolBar.setTitleTextColor(Color.rgb(50,0,0));
            toolBar.setNavigationIcon(R.drawable.ic_drawer_am);
        }
        setSupportActionBar(toolBar);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        // 初始化抽屉开关
        toggle = new ActionBarDrawerToggle(this, drawer,
                R.drawable.ic_drawer_am, R.string.drawer_open,
                R.string.drawer_close);
        toggle.syncState();
        /*toolBar.setNavigationIcon(R.drawable.ic_drawer_am);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                // 初始化抽屉开关
                toggle.syncState();// 同步状态, 将DrawerLayout和开关关联在一起
            }
        });*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 切换抽屉
                toggle.onOptionsItemSelected(item);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
