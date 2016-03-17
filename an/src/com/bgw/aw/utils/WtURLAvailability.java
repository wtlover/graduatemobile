package com.bgw.aw.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 作�?? staryumou@163.com:
 * @version 创建时间�?2016�?1�?5�? 上午10:37:33 类说�? :
 */
public class WtURLAvailability {
	public static boolean isURLAvailability(String url) {
		boolean status = false;
		URL urls;
		try {
			urls = new URL(url);
			InputStream is = urls.openStream();
			System.out.println("地址链接可用");
			status = true;

		} catch (Exception e) {
			System.out.println("地址链接不可�?");
			status = false;
		}
		return status;
	}

	private static URL url;

	private static HttpURLConnection con;

	private static int state = -1;

	/**
	 * 
	 * 功能：检测当前URL是否可连接或是否有效,
	 * 
	 * 描述：最多连接网�? 5 �?, 如果 5 次都不成功，视为该地�?不可�?
	 * 
	 * @param urlStr
	 *            指定URL网络地址
	 * 
	 * @return URL,为空无效，不为空有效链接�?
	 */

	public synchronized URL isConnect(String urlStr) {
		int counts = 0;
		if (urlStr == null || urlStr.length() <= 0) {
			return null;
		}
		while (counts < 5) {
			try {
				url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				state = con.getResponseCode();
				System.out.println(counts + "= " + state);
				if (state == 200) {
					System.out.println("URL可用�?");
				}
				break;
			} catch (Exception ex) {
				counts++;
				System.out.println("URL不可用，连接�? " + counts + " �?");
				urlStr = null;
				continue;
			}
		}
		return url;
	}
}
