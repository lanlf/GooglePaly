package com.lan.googleplay.domain;
import android.os.Environment;

import com.lan.googleplay.manager.DownloadManager;

import java.io.File;

/**
 * 下载对象
 * 
 * 注意: 一定要有读写sdcard的权限!!!!
 * 
 *  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * 
 * @author Kevin
 * @date 2015-11-4
 */
public class DownloadInfo {

	public String id;
	public String name;
	public String downloadUrl;
	public long size;
	public String packageName;

	public long currentPos;// 当前下载位置
	public int currentState;// 当前下载状态
	public String path;// 下载到本地文件的路径

	public static final String GOOGLE_MARKET = "GOOGLE_MARKET";// sdcard根目录文件夹名称
	public static final String DONWLOAD = "download";// 子文件夹名称, 存放下载的文件

	// 获取下载进度(0-1)
	public float getProgress() {
		if (size == 0) {
			return 0;
		}

		float progress = currentPos / (float) size;
		return progress;
	}

	// 拷贝对象, 从AppInfo中拷贝出一个DownloadInfo
	public static DownloadInfo copy(AppInfo info) {
		DownloadInfo downloadInfo = new DownloadInfo();

		downloadInfo.id = info.id;
		downloadInfo.name = info.name;
		downloadInfo.downloadUrl = info.downloadUrl;
		downloadInfo.packageName = info.packageName;
		downloadInfo.size = info.size;

		downloadInfo.currentPos = 0;
		downloadInfo.currentState = DownloadManager.STATE_UNDO;// 默认状态是未下载
		downloadInfo.path = downloadInfo.getFilePath();

		return downloadInfo;
	}

	// 获取文件下载路径
	public String getFilePath() {
		StringBuffer sb = new StringBuffer();
		String sdcard = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		sb.append(sdcard);
		// sb.append("/");
		sb.append(File.separator);
		sb.append(GOOGLE_MARKET);
		sb.append(File.separator);
		sb.append(DONWLOAD);

		if (createDir(sb.toString())) {
			// 文件夹存在或者已经创建完成
			return sb.toString() + File.separator + name + ".apk";// 返回文件路径
		}

		return null;
	}

	private boolean createDir(String dir) {
		File dirFile = new File(dir);

		// 文件夹不存在或者不是一个文件夹
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return dirFile.mkdirs();
		}
		return true;// 文件夹存在
	}

}
