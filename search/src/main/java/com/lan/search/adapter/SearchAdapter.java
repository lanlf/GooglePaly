package com.lan.search.adapter;

import android.content.Context;

import com.lan.search.bean.Bean;
import com.lan.search.R;
import com.lan.search.ui.holder.ViewHolder;

import java.util.List;

/**
 * Created by lan on 2016/8/8.
 */
public class SearchAdapter extends CommonAdapter<Bean>{

    public SearchAdapter(Context context, List<Bean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, int position) {
        holder.setText(R.id.tv_tilte,mData.get(position).getTitle());
    }
}
