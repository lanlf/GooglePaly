package com.lan.googleplay.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.lan.googleplay.domain.AppInfo;
import com.lan.googleplay.http.protocol.HomeProtocol;
import com.lan.googleplay.ui.activity.HomeDetailActivity;
import com.lan.googleplay.ui.adapter.MyBaseAdapter;
import com.lan.googleplay.ui.holder.BaseHolder;
import com.lan.googleplay.ui.holder.HomeHeaderHolder;
import com.lan.googleplay.ui.holder.HomeHolder;
import com.lan.googleplay.ui.view.LoadingPager;
import com.lan.googleplay.ui.view.MyListView;
import com.lan.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by lan on 2016/7/25.
 */
public class HomeFragment extends BaseFragment {

    // 轮播条数据
    private ArrayList<String> mPictureList;

    private ArrayList<AppInfo> data;

    @Override
    protected View onCreateSuccessView() {
        MyListView lv = new MyListView(UIUtils.getContext());

        // 给listview增加头布局展示轮播条
        HomeHeaderHolder header = new HomeHeaderHolder();
        lv.addHeaderView(header.getRootView());// 先添加头布局,再setAdapter
        if (mPictureList != null) {
            // 设置轮播条数据
            header.setData(mPictureList);
        }
        lv.setAdapter(new HomeAdapter(data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                AppInfo appInfo = data.get(position - 1);// 去掉头布局

                if (appInfo != null) {
                    Intent intent = new Intent(UIUtils.getContext(),
                            HomeDetailActivity.class);
                    intent.putExtra("packageName", appInfo.packageName);
                    intent.putExtra("name",appInfo.name);
                    startActivity(intent);
                }
            }
        });
        return lv;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        // 请求网络, HttpClient, HttpUrlConnection, XUtils
        // data = new ArrayList<String>();
        // for (int i = 0; i < 20; i++) {
        // data.add("测试数据:" + i);
        // }
        HomeProtocol protocol = new HomeProtocol();
        data = protocol.getData("homelist0");// 加载第一页数据
        mPictureList = protocol.getPictures();
        return check(data);// 校验数据并返回
    }

    private class HomeAdapter extends MyBaseAdapter {

        private ArrayList<AppInfo> moreData;
        public HomeAdapter(ArrayList<AppInfo> data) {
            super(data);
        }
        @Override
        public BaseHolder getHolder(int position) {
            return new HomeHolder();
        }
        @Override
        public ArrayList onLoadMore() {
            HomeProtocol protocol = new HomeProtocol();
            // 20, 40, 60....
            // 下一页数据的位置 等于 当前集合大小
            if (getListSize() > 19&&getListSize() <= 39) {
                moreData = protocol.getData("homelist1");
            }else if (getListSize()> 39&&getListSize() <= 59) {
                moreData = protocol.getData("homelist2");
            }else if (getListSize() > 59&&getListSize() <= 79) {
                moreData = protocol.getData("homelist3");
            }
            return moreData;
        }
    }
}
