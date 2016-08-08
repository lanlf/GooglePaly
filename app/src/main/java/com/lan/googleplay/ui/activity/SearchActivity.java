package com.lan.googleplay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lan.googleplay.R;
import com.lan.googleplay.domain.AppInfo;
import com.lan.googleplay.ui.adapter.MyBaseAdapter;
import com.lan.googleplay.ui.holder.BaseHolder;
import com.lan.googleplay.ui.holder.SearchHolder;
import com.lan.googleplay.ui.view.MyListView;
import com.lan.googleplay.ui.view.SearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lan on 2016/8/8.
 */
public class SearchActivity extends BaseActivity implements SearchView.SearchViewListener {
    private ArrayList<AppInfo> apps;
    /**
     * 搜索结果列表view
     */
    private MyListView lvResults;

    /**
     * 搜索view
     */
    private SearchView searchView;


    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private SearchAdapter resultAdapter;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

    /**
     * 搜索结果的数据
     */
    private ArrayList<AppInfo> resultData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        SearchActivity.hintSize = hintSize;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_activity);
        initData();
        initViews();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        lvResults = (MyListView) findViewById(R.id.main_lv_search_results);
        searchView = (SearchView) findViewById(R.id.main_search_layout);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

       /* lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(UIUtils.getContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 获取db 数据
     */
    private void getDbData() {
        /*int size = 100;
        dbData = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            dbData.add(new Bean(0, "android开发必备技能" + (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
        }*/
        apps = (ArrayList<AppInfo>) getIntent().getSerializableExtra("apps");
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        if (hintData == null) {
            hintData = new ArrayList<String>(hintSize);
        }
        for (int i = 1; i <= hintSize; i++) {
            hintData.add(apps.get(i).name);
        }
        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < apps.size()
                    && count < hintSize; i++) {
                if (apps.get(i).name.contains(text.trim())) {
                    autoCompleteData.add(apps.get(i).name);
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<AppInfo>();
        } else {
            resultData.clear();
            for (int i = 0; i < apps.size(); i++) {
                if (apps.get(i).name.contains(text.trim())) {
                    resultData.add(apps.get(i));
                }
            }
        }

        if (resultAdapter == null) {
            resultAdapter = new SearchAdapter(resultData);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
        /*lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                AppInfo appInfo = resultData.get(position);// 去掉头布局

                if (appInfo != null) {
                    Intent intent = new Intent(UIUtils.getContext(),
                            HomeDetailActivity.class);
                    intent.putExtra("packageName", appInfo.packageName);
                    intent.putExtra("name",appInfo.name);
                    startActivity(intent);
                }
            }
        });*/
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     *
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lvResults.setAdapter(resultAdapter);
        } else {
            //更新搜索数据
            resultAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "完成搜索", Toast.LENGTH_SHORT).show();
    }

    private class SearchAdapter extends MyBaseAdapter {

        public SearchAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder(int position) {
            return new SearchHolder();
        }

        @Override
        public boolean hasMore() {
            return false;
        }

        @Override
        public ArrayList onLoadMore() {
            return null;
        }
    }
}
