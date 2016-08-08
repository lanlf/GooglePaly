package com.lan.googleplay.ui.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lan.googleplay.R;
import com.lan.googleplay.domain.CategoryInfo;
import com.lan.googleplay.http.HttpHelper;
import com.lan.googleplay.utils.BitmapHelper;
import com.lan.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class CategoryHolder extends BaseHolder<CategoryInfo> implements
		OnClickListener {

	private TextView tvName1, tvName2, tvName3;
	private ImageView ivIcon1, ivIcon2, ivIcon3;
	private LinearLayout llGrid1, llGrid2, llGrid3;

	private BitmapUtils mBitmapUtils;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item_category);

		tvName1 = (TextView) view.findViewById(R.id.tv_name1);
		tvName2 = (TextView) view.findViewById(R.id.tv_name2);
		tvName3 = (TextView) view.findViewById(R.id.tv_name3);

		ivIcon1 = (ImageView) view.findViewById(R.id.iv_icon1);
		ivIcon2 = (ImageView) view.findViewById(R.id.iv_icon2);
		ivIcon3 = (ImageView) view.findViewById(R.id.iv_icon3);

		llGrid1 = (LinearLayout) view.findViewById(R.id.ll_grid1);
		llGrid2 = (LinearLayout) view.findViewById(R.id.ll_grid2);
		llGrid3 = (LinearLayout) view.findViewById(R.id.ll_grid3);

		llGrid1.setOnClickListener(this);
		llGrid2.setOnClickListener(this);
		llGrid3.setOnClickListener(this);

		mBitmapUtils = BitmapHelper.getBitmapUtils();

		return view;
	}

	@Override
	public void refreshView(CategoryInfo data) {

		tvName1.setText(data.name1);
		tvName2.setText(data.name2);
		tvName3.setText(data.name3);

		mBitmapUtils.display(ivIcon1, HttpHelper.URL + "/"
				+ data.url1);
		mBitmapUtils.display(ivIcon2, HttpHelper.URL + "/"
				+ data.url2);
		mBitmapUtils.display(ivIcon3, HttpHelper.URL + "/"
				+ data.url3);

	}

	@Override
	public void onClick(View v) {
		CategoryInfo info = getData();

		switch (v.getId()) {
		case R.id.ll_grid1:
			Toast.makeText(UIUtils.getContext(), info.name1, Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.ll_grid2:
			Toast.makeText(UIUtils.getContext(), info.name2, Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.ll_grid3:
			Toast.makeText(UIUtils.getContext(), info.name3, Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
	}

}
