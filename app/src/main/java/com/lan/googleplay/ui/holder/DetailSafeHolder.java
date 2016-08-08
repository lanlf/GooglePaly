package com.lan.googleplay.ui.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lan.googleplay.R;
import com.lan.googleplay.domain.AppInfo;
import com.lan.googleplay.http.HttpHelper;
import com.lan.googleplay.utils.BitmapHelper;
import com.lan.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * 应用详情页-安全模块
 * 
 * @author Kevin
 * @date 2015-11-1
 */
public class DetailSafeHolder extends BaseHolder<AppInfo> {

	private ImageView[] mSafeIcons;// 安全标识图片
	private ImageView[] mDesIcons;// 安全描述图片
	private TextView[] mSafeDes;// 安全描述文字
	private LinearLayout[] mSafeDesBar;// 安全描述条目(图片+文字)
	private BitmapUtils mBitmapUtils;

	private RelativeLayout rlDesRoot;
	private LinearLayout llDesRoot;
	private ImageView ivArrow;

	private int mDesHeight;
	private LinearLayout.LayoutParams mParams;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_safeinfo);

		mSafeIcons = new ImageView[4];
		mSafeIcons[0] = (ImageView) view.findViewById(R.id.iv_safe1);
		mSafeIcons[1] = (ImageView) view.findViewById(R.id.iv_safe2);
		mSafeIcons[2] = (ImageView) view.findViewById(R.id.iv_safe3);
		mSafeIcons[3] = (ImageView) view.findViewById(R.id.iv_safe4);

		mDesIcons = new ImageView[4];
		mDesIcons[0] = (ImageView) view.findViewById(R.id.iv_des1);
		mDesIcons[1] = (ImageView) view.findViewById(R.id.iv_des2);
		mDesIcons[2] = (ImageView) view.findViewById(R.id.iv_des3);
		mDesIcons[3] = (ImageView) view.findViewById(R.id.iv_des4);

		mSafeDes = new TextView[4];
		mSafeDes[0] = (TextView) view.findViewById(R.id.tv_des1);
		mSafeDes[1] = (TextView) view.findViewById(R.id.tv_des2);
		mSafeDes[2] = (TextView) view.findViewById(R.id.tv_des3);
		mSafeDes[3] = (TextView) view.findViewById(R.id.tv_des4);

		mSafeDesBar = new LinearLayout[4];
		mSafeDesBar[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
		mSafeDesBar[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
		mSafeDesBar[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
		mSafeDesBar[3] = (LinearLayout) view.findViewById(R.id.ll_des4);

		rlDesRoot = (RelativeLayout) view.findViewById(R.id.rl_des_root);
		rlDesRoot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle();
			}
		});

		mBitmapUtils = BitmapHelper.getBitmapUtils();

		llDesRoot = (LinearLayout) view.findViewById(R.id.ll_des_root);
		ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);

		return view;
	}

	private boolean isOpen = false;// 标记安全描述开关状态,默认关

	// 打开或者关闭安全描述信息
	// 导入jar包: nineoldandroids-2.4.0.jar
	protected void toggle() {
		ValueAnimator animator = null;
		if (isOpen) {
			// 关闭
			isOpen = false;
			// 属性动画
			animator = ValueAnimator.ofInt(mDesHeight, 0);// 从某个值变化到某个值
		} else {
			// 开启
			isOpen = true;
			// 属性动画
			animator = ValueAnimator.ofInt(0, mDesHeight);
		}

		// 动画更新的监听
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			// 启动动画之后, 会不断回调此方法来获取最新的值
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				// 获取最新的高度值
				Integer height = (Integer) animator.getAnimatedValue();

				System.out.println("最新高度:" + height);

				// 重新修改布局高度
				mParams.height = height;
				llDesRoot.setLayoutParams(mParams);
			}
		});

		animator.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0) {

			}

			@Override
			public void onAnimationRepeat(Animator arg0) {

			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// 动画结束的事件
				// 更新小箭头的方向
				if (isOpen) {
					ivArrow.setImageResource(R.drawable.arrow_up);
				} else {
					ivArrow.setImageResource(R.drawable.arrow_down);
				}
			}

			@Override
			public void onAnimationCancel(Animator arg0) {

			}
		});

		animator.setDuration(200);// 动画时间
		animator.start();// 启动动画
	}

	@Override
	public void refreshView(AppInfo data) {
		ArrayList<AppInfo.SafeInfo> safe = data.safe;

		for (int i = 0; i < 4; i++) {
			if (i < safe.size()) {
				// 安全标识图片
				AppInfo.SafeInfo safeInfo = safe.get(i);
				mBitmapUtils.display(mSafeIcons[i], HttpHelper.URL
						 + safeInfo.safeUrl);
				// 安全描述文字
				mSafeDes[i].setText(safeInfo.safeDes);
				// 安全描述图片
				mBitmapUtils.display(mDesIcons[i], HttpHelper.URL
						+ safeInfo.safeDesUrl);
			} else {
				// 剩下不应该显示的图片
				mSafeIcons[i].setVisibility(View.GONE);

				// 隐藏多余的描述条目
				mSafeDesBar[i].setVisibility(View.GONE);
			}
		}

		// 获取安全描述的完整高度
		llDesRoot.measure(0, 0);
		mDesHeight = llDesRoot.getMeasuredHeight();

		System.out.println("安全描述高度:" + mDesHeight);

		// 修改安全描述布局高度为0,达到隐藏效果
		mParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
		mParams.height = 0;
		llDesRoot.setLayoutParams(mParams);
	}

}
