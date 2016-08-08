package com.lan.googleplay.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.lan.googleplay.R;
import com.lan.googleplay.domain.AppInfo;
import com.lan.googleplay.http.protocol.HomeDetailProtocol;
import com.lan.googleplay.ui.holder.DetailAppInfoHolder;
import com.lan.googleplay.ui.holder.DetailDesHolder;
import com.lan.googleplay.ui.holder.DetailDownloadHolder;
import com.lan.googleplay.ui.holder.DetailPicsHolder;
import com.lan.googleplay.ui.holder.DetailSafeHolder;
import com.lan.googleplay.ui.view.LoadingPager;
import com.lan.googleplay.utils.UIUtils;

/**
 * Created by lan on 2016/8/1.
 */
public class HomeDetailActivity extends BaseActivity {
    private LoadingPager mLoadingPage;
    private String packageName;
    private AppInfo data;
    private Toolbar back;
    private String name;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingPage = new LoadingPager(this) {

            @Override
            public LoadingPager.ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }

            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }
        };
        // setContentView(R.layout.activity_main);
        setContentView(mLoadingPage);// 直接将一个view对象设置给activity
        // 获取从HomeFragment传递过来的包名
        packageName = getIntent().getStringExtra("packageName");
        name = getIntent().getStringExtra("name");
        //initToolBar();
        // 开始加载网络数据
        mLoadingPage.loadData();
    }

    public View onCreateSuccessView() {
        // 初始化成功的布局
        View view = UIUtils.inflate(R.layout.page_home_detail);
       // back = (Toolbar) view.findViewById(R.id.tb_detail);
       // initToolBar();
        // 初始化应用信息模块
        FrameLayout flDetailAppInfo = (FrameLayout) view
                .findViewById(R.id.fl_detail_appinfo);
        // 动态给帧布局填充页面
        DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
        flDetailAppInfo.addView(appInfoHolder.getRootView());
        appInfoHolder.setData(data);

        // 初始化安全描述模块
        FrameLayout flDetailSafe = (FrameLayout) view
                .findViewById(R.id.fl_detail_safe);
        DetailSafeHolder safeHolder = new DetailSafeHolder();
        flDetailSafe.addView(safeHolder.getRootView());
        safeHolder.setData(data);

        // 初始化截图模块
        HorizontalScrollView hsvPic = (HorizontalScrollView) view
                .findViewById(R.id.hsv_detail_pics);
        DetailPicsHolder picsHolder = new DetailPicsHolder();
        hsvPic.addView(picsHolder.getRootView());
        picsHolder.setData(data);

        // 初始化描述模块
        FrameLayout flDetailDes = (FrameLayout) view
                .findViewById(R.id.fl_detail_des);
        DetailDesHolder desHolder = new DetailDesHolder();
        flDetailDes.addView(desHolder.getRootView());
        desHolder.setData(data);

        // 初始化下载模块
        FrameLayout flDetailDownload = (FrameLayout) view
                .findViewById(R.id.fl_detail_download);
        DetailDownloadHolder downloadHolder = new DetailDownloadHolder();
        flDetailDownload.addView(downloadHolder.getRootView());
        downloadHolder.setData(data);
        return view;
    }

    public LoadingPager.ResultState onLoad() {
        // 请求网络,加载数据
        HomeDetailProtocol protocol = new HomeDetailProtocol(packageName);
        data = protocol.getData(packageName + "/" + packageName);

        if (data != null) {
            return LoadingPager.ResultState.SUCCESS;
        } else {
            return LoadingPager.ResultState.ERROR;
        }
    }

    private void initToolBar() {
        Toolbar toolbar = new Toolbar(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        addContentView(toolbar,params);
        toolbar.setBackgroundColor(Color.rgb(155,0,0));
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
