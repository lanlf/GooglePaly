package com.lan.googleplay.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lan.googleplay.R;
import com.lan.googleplay.http.HttpHelper;
import com.lan.googleplay.utils.BitmapHelper;
import com.lan.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * 首页轮播条holder
 * 
 * @author Kevin
 * @date 2015-11-1
 */
public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {

	private ViewPager mViewPager;

	private ArrayList<String> data;

	private LinearLayout llContainer;

	private int mPreviousPos;// 上个圆点位置

	@Override
	public View initView() {
		// 创建根布局, 相对布局
		RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
		// 初始化布局参数, 根布局上层控件是listview, 所以要使用listview定义的LayoutParams
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(
				AbsListView.LayoutParams.MATCH_PARENT, UIUtils.dip2px(150));
		rlRoot.setLayoutParams(params);

		// ViewPager
		mViewPager = new ViewPager(UIUtils.getContext());
		RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		// mViewPager.setLayoutParams(vpParams);
		rlRoot.addView(mViewPager, vpParams);// 把viewpager添加给相对布局

		// 初始化指示器
		llContainer = new LinearLayout(UIUtils.getContext());
		llContainer.setOrientation(LinearLayout.HORIZONTAL);// 水平方向

		RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		// 设置内边距
		int padding = UIUtils.dip2px(10);
		llContainer.setPadding(padding, padding, padding, padding);

		// 添加规则, 设定展示位置
		llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);// 底部对齐
		llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);// 右对齐

		// 添加布局
		rlRoot.addView(llContainer, llParams);

		return rlRoot;
	}

	@Override
	public void refreshView(final ArrayList<String> data) {
		this.data = data;
		// 填充viewpager的数据
		mViewPager.setAdapter(new HomeHeaderAdapter());
		//设置第一张图片也可以循环
		mViewPager.setCurrentItem(data.size() * 10000);

		// 初始化指示器
		for (int i = 0; i < data.size(); i++) {
			ImageView point = new ImageView(UIUtils.getContext());

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			if (i == 0) {// 第一个默认选中
				point.setImageResource(R.drawable.indicator_selected);
			} else {
				point.setImageResource(R.drawable.indicator_normal);
				params.leftMargin = UIUtils.dip2px(4);// 左边距
			}

			point.setLayoutParams(params);

			llContainer.addView(point);
		}

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				position = position % data.size();

				// 当前点被选中
				ImageView point = (ImageView) llContainer.getChildAt(position);
				point.setImageResource(R.drawable.indicator_selected);

				// 上个点变为不选中
				ImageView prePoint = (ImageView) llContainer
						.getChildAt(mPreviousPos);
				prePoint.setImageResource(R.drawable.indicator_normal);

				mPreviousPos = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		// UIUtils.getHandler().postDelayed(r, delayMillis)
		//启动轮播条自动播放
		HomeHeaderTask task = new HomeHeaderTask();
		task.start();
	}

	class HomeHeaderTask implements Runnable {

		public void start() {
			// 移除之前发送的所有消息, 避免消息重复
			UIUtils.getHandler().removeCallbacksAndMessages(null);
			UIUtils.getHandler().postDelayed(this, 3000);
		}

		@Override
		public void run() {
			int currentItem = mViewPager.getCurrentItem();
			currentItem++;
			mViewPager.setCurrentItem(currentItem);

			// 继续发延时3秒消息, 实现内循环
			UIUtils.getHandler().postDelayed(this, 3000);
		}

	}

	class HomeHeaderAdapter extends PagerAdapter {

		private BitmapUtils mBitmapUtils;

		public HomeHeaderAdapter() {
			mBitmapUtils = BitmapHelper.getBitmapUtils();
		}

		@Override
		public int getCount() {
			// return data.size();
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position = position % data.size();

			String url = data.get(position);

			ImageView view = new ImageView(UIUtils.getContext());
			view.setScaleType(ScaleType.FIT_XY);

			mBitmapUtils.display(view, HttpHelper.URL + "/" + url);
			//System.out.println("------"+HttpHelper.URL + "/" + url);
			container.addView(view);

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

}
