package com.lan.googleplay.ui.fragment;

import android.view.View;

import com.lan.googleplay.domain.AppInfo;
import com.lan.googleplay.http.protocol.AppProtocol;
import com.lan.googleplay.ui.adapter.MyBaseAdapter;
import com.lan.googleplay.ui.holder.AppHolder;
import com.lan.googleplay.ui.holder.BaseHolder;
import com.lan.googleplay.ui.view.LoadingPager;
import com.lan.googleplay.ui.view.MyListView;
import com.lan.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by lan on 2016/7/25.
 */
public class AppFragment extends BaseFragment {

    private ArrayList<AppInfo> data;

    @Override
    protected View onCreateSuccessView() {
        MyListView lv = new MyListView(UIUtils.getContext());
        lv.setAdapter(new HomeAdapter(data));
        return lv;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        // 请求网络, HttpClient, HttpUrlConnection, XUtils
        // data = new ArrayList<String>();
        // for (int i = 0; i < 20; i++) {
        // data.add("测试数据:" + i);
        // }
        AppProtocol protocol = new AppProtocol();
        data = protocol.getData("applist1");// 加载第一页数据

        return check(data);// 校验数据并返回
    }

    private class HomeAdapter extends MyBaseAdapter {

        private ArrayList<AppInfo> moreData;
        public HomeAdapter(ArrayList<AppInfo> data) {
            super(data);
        }
        @Override
        public BaseHolder getHolder(int position) {
            return new AppHolder();
        }
        @Override
        public ArrayList onLoadMore() {
            AppProtocol protocol = new AppProtocol();
            // 20, 40, 60....
            // 下一页数据的位置 等于 当前集合大小
            if (getListSize() > 19&&getListSize() <= 39) {
                moreData = protocol.getData("applist2");
            }else if (getListSize()> 39&&getListSize() <= 59) {
                moreData = protocol.getData("applist3");
            }
            return moreData;
        }
    }
}
