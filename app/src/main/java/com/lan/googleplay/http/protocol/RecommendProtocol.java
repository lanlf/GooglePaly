package com.lan.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
/**
 * 推荐网络访问
 * @author Kevin
 * @date 2015-10-30
 */
public class RecommendProtocol extends BaseProtocol<ArrayList<String>> {

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
		return "recommend";
	}

	@Override
	public ArrayList<String> parseData(String result) {
		try {
			JSONArray ja = new JSONArray(result);

			ArrayList<String> list = new ArrayList<String>();

			for (int i = 0; i < ja.length(); i++) {
				String keyword = ja.getString(i);
				list.add(keyword);
			}

			return list;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
