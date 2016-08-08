package com.lan.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lan.googleplay.ui.view.LoadingPager;
import com.lan.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by lan on 2016/7/25.
 */
public abstract class BaseFragment extends Fragment{

    private LoadingPager lp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //使用textview显示当前类的类名
        /*TextView view = new TextView(UIUtils.getContext());
        view.setText(getClass().getSimpleName());*/
        lp = new LoadingPager(UIUtils.getContext()) {
            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }

            @Override
            public View onCreateSuccessView() {
                return BaseFragment.this.onCreateSuccessView();
            }
        };
        return lp;
    }
    // 对网络返回数据的合法性进行校验
    public LoadingPager.ResultState check(Object obj) {
        if (obj != null) {
            if (obj instanceof ArrayList) {// 判断是否是集合
                ArrayList list = (ArrayList) obj;

                if (list.isEmpty()) {
                    return LoadingPager.ResultState.EMPTY;
                } else {
                    return LoadingPager.ResultState.SUCCESS;
                }
            }
        }

        return LoadingPager.ResultState.ERROR;
    }
    protected abstract View onCreateSuccessView();

    protected abstract LoadingPager.ResultState onLoad();

    public void loadData(){
        if(lp != null){
            lp.loadData();
        }
    }
}
