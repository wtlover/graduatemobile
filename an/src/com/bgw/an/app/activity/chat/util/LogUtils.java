package com.bgw.an.app.activity.chat.util;

import android.util.Log;

/**
 * @fileName LogUtils.java
 * @package szu.wifichat.android.util
 * @description 鏃ュ織鎿嶄綔绫�
 * @author _Hill3
 */

public class LogUtils {

	private static boolean isShow = true; // 鏄惁鎵撳嵃鏃ュ織

	public static void setLogStatus(boolean flag) {
		isShow = flag;
	}

	public static void d(String tag, String msg) {
		if (isShow)
			Log.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isShow)
			Log.e(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isShow)
			Log.v(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (isShow)
			Log.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (isShow)
			Log.w(tag, msg);
	}

	public static void wtf(String tag, String msg) {
		if (isShow)
			Log.wtf(tag, msg);
	}

}
