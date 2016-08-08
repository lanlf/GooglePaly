package com.lan.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lan.googleplay.R;
import com.lan.googleplay.domain.SubjectInfo;
import com.lan.googleplay.http.HttpHelper;
import com.lan.googleplay.utils.BitmapHelper;
import com.lan.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 专题holder
 * 
 * @author Kevin
 * @date 2015-10-30
 */
public class SubjectHolder extends BaseHolder<SubjectInfo> {

	private ImageView ivPic;
	private TextView tvTitle;

	private BitmapUtils mBitmapUtils;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item_subject);
		ivPic = (ImageView) view.findViewById(R.id.iv_pic);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);

		mBitmapUtils = BitmapHelper.getBitmapUtils();

		return view;
	}

	@Override
	public void refreshView(SubjectInfo data) {
		tvTitle.setText(data.des);
		mBitmapUtils.display(ivPic, HttpHelper.URL + data.url);
	}

}
