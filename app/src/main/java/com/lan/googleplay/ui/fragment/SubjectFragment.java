package com.lan.googleplay.ui.fragment;

import android.view.View;

import com.lan.googleplay.domain.SubjectInfo;
import com.lan.googleplay.http.protocol.SubjectProtocol;
import com.lan.googleplay.ui.adapter.MyBaseAdapter;
import com.lan.googleplay.ui.holder.BaseHolder;
import com.lan.googleplay.ui.holder.SubjectHolder;
import com.lan.googleplay.ui.view.LoadingPager;
import com.lan.googleplay.ui.view.MyListView;
import com.lan.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by lan on 2016/7/25.
 */
public class SubjectFragment extends BaseFragment {

    private ArrayList<SubjectInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new SubjectAdapter(data));
        return view;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        SubjectProtocol protocol = new SubjectProtocol();
        data = protocol.getData("sublist1");
        return check(data);
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {

        private ArrayList<SubjectInfo> moreData;

        public SubjectAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<SubjectInfo> getHolder(int position) {
            return new SubjectHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onLoadMore() {
            SubjectProtocol protocol = new SubjectProtocol();
            if (getListSize() > 19&&getListSize() <= 39) {
                moreData = protocol.getData("sublist2");
            }else if (getListSize()> 39&&getListSize() <= 59) {
                moreData = protocol.getData("sublist3");
            }else if (getListSize() > 19&&getListSize() <= 39) {
                moreData = protocol.getData("sublist4");
            }else if (getListSize()> 39&&getListSize() <= 59) {
                moreData = protocol.getData("sublist5");
            }
            return moreData;
        }

    }
}
