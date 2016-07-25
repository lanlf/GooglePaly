package com.lan.googleplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lan.googleplay.R;
import com.lan.googleplay.utils.UIUtils;

/**
 * 根据当前状态来显示不同页面的自定义控件
 * <p/>
 * - 未加载 - 加载中 - 加载失败 - 数据为空 - 加载成功
 * eated by lan on 2016/7/25.
 */
public abstract class LoadingPager extends FrameLayout {

    private static final int STATE_LOAD_UNDO = 1;// 未加载
    private static final int STATE_LOAD_LOADING = 2;// 正在加载
    private static final int STATE_LOAD_ERROR = 3;// 加载失败
    private static final int STATE_LOAD_EMPTY = 4;// 数据为空
    private static final int STATE_LOAD_SUCCESS = 5;// 加载成功

    private int mCurrentState = STATE_LOAD_UNDO;// 当前状态

    private View mLoadingPage;
    private View mErrorPage;
    private View mEmptyPage;
    private View mSuccessPage;

    public LoadingPager(Context context) {
        super(context);
        initView();
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 初始化加载中的布局
        if (mLoadingPage == null) {
            mLoadingPage = UIUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);// 将加载中的布局添加给当前的帧布局
        }

        // 初始化加载失败布局
        if (mErrorPage == null) {
            mErrorPage = UIUtils.inflate(R.layout.page_error);
            addView(mErrorPage);
        }

        // 初始化数据为空布局
        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }
        showRightPage();
    }

    private void showRightPage() {
        mLoadingPage
                .setVisibility((mCurrentState == STATE_LOAD_UNDO || mCurrentState == STATE_LOAD_LOADING) ? View.VISIBLE
                        : View.GONE);

        mErrorPage
                .setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE
                        : View.GONE);

        mEmptyPage
                .setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE
                        : View.GONE);

        // 当成功布局为空,并且当前状态为成功,才初始化成功的布局
        if (mSuccessPage == null && mCurrentState == STATE_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();
            if (mSuccessPage != null) {
                addView(mSuccessPage);
            }
        }

        if (mSuccessPage != null) {
            mSuccessPage
                    .setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE
                            : View.GONE);
        }
    }

    /**
     * 开始加载数据
     */
    public void loadData() {
        if (mCurrentState != STATE_LOAD_LOADING)
            mCurrentState = STATE_LOAD_LOADING;
        showRightPage();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (onLoad() != null)
                    mCurrentState = onLoad().state;
                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        showRightPage();
                    }
                });
            }
        }).start();
    }


    public enum ResultState {
        EMPTY(STATE_LOAD_EMPTY), SUCESS(STATE_LOAD_SUCCESS), ERROR(STATE_LOAD_ERROR);
        private int state;

        ResultState(int state) {
            this.state = state;
        }
    }

    //向服务器请求数据
    public abstract ResultState onLoad();

    //请求成功后加载视图
    public abstract View onCreateSuccessView();
}
