package com.bgw.an.app.activity.chat.util;

import com.alibaba.fastjson.JSON;

/**
 * @fileName JsonUtils.java
 * @package szu.wifichat.android.util
 * @description Json瑙ｆ瀽宸ュ叿绫�
 */
public class JsonUtils {

	/**
	 * 灏唈avaBean杞崲鎴恓son瀵硅薄
	 * 
	 * @param paramObject
	 *            闇�瑕佽В鏋愮殑瀵硅薄
	 */
	public static String createJsonString(Object paramObject) {
		String str = JSON.toJSONString(paramObject);
		return str;
	}

	/**
	 * 瀵瑰崟涓猨avaBean杩涜瑙ｆ瀽
	 * 
	 * @param <T>
	 * @param paramJson
	 *            闇�瑕佽В鏋愮殑json瀛楃涓�
	 * @param paramCls
	 *            闇�瑕佽浆鎹㈡垚鐨勭被
	 * @return
	 */
	public static <T> T getObject(String paramJson, Class<T> paramCls) {
		T t = null;
		try {
			t = JSON.parseObject(paramJson, paramCls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

}
