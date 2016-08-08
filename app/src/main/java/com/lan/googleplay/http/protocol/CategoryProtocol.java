package com.lan.googleplay.http.protocol;

import com.lan.googleplay.domain.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 分类模块请求网络
 * 
 * @author Kevin
 * @date 2015-11-1
 */
public class CategoryProtocol extends BaseProtocol<ArrayList<CategoryInfo>> {

	@Override
	public String getKey() {
		return "app/";
	}

	@Override
	public String getParams() {
		return "";
	}

	@Override
	public String getKind() {
		return "cate";
	}

	@Override
	public ArrayList<CategoryInfo> parseData(String result) {
		try {
			JSONArray ja = new JSONArray(result);

			ArrayList<CategoryInfo> list = new ArrayList<CategoryInfo>();
			for (int i = 0; i < ja.length(); i++) {// 遍历大分类, 2次
				JSONObject jo = ja.getJSONObject(i);

				// 初始化标题对象
				if (jo.has("title")) {// 判断是否有title这个字段
					CategoryInfo titleInfo = new CategoryInfo();
					titleInfo.title = jo.getString("title");
					titleInfo.isTitle = true;
					list.add(titleInfo);
				}

				// 初始化分类对象
				if (jo.has("infos")) {
					JSONArray ja1 = jo.getJSONArray("infos");

					for (int j = 0; j < ja1.length(); j++) {// 遍历小分类
						JSONObject jo1 = ja1.getJSONObject(j);
						CategoryInfo info = new CategoryInfo();
						info.name1 = jo1.getString("name1");
						info.name2 = jo1.getString("name2");
						info.name3 = jo1.getString("name3");
						info.url1 = jo1.getString("url1");
						info.url2 = jo1.getString("url2");
						info.url3 = jo1.getString("url3");
						info.isTitle = false;

						list.add(info);
					}
				}
			}

			return list;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
