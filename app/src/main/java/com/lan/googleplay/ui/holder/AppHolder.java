package com.lan.googleplay.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lan.googleplay.R;
import com.lan.googleplay.domain.AppInfo;
import com.lan.googleplay.http.HttpHelper;
import com.lan.googleplay.utils.BitmapHelper;
import com.lan.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by lan on 2016/7/26.
 */
public class AppHolder extends BaseHolder<AppInfo>{

    private TextView tvName;
    private TextView tvSize;
    private RatingBar rbStar;
    private ImageView ivIcon;
    private TextView tvDes;
    private BitmapUtils bitmapUtils;


    @Override
    public View initView() {
        // 1. 加载布局
        View view = UIUtils.inflate(R.layout.list_item_home);
        // 2. 初始化控件
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDes = (TextView) view.findViewById(R.id.tv_des);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        rbStar = (RatingBar) view.findViewById(R.id.rb_star);
        bitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tvName.setText(data.name);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        tvDes.setText(data.des);
        rbStar.setRating(data.stars);
        //x.image().bind(ivIcon,"http://192.168.1.5:8080/apps/app/com.youyuan.yyhl/icon.jpg");
        //xUtils3的方法会报异常
        //x.image().bind(ivIcon, HttpHelper.URL +data.iconUrl);
        bitmapUtils.display(ivIcon, HttpHelper.URL +data.iconUrl);
    }
}
