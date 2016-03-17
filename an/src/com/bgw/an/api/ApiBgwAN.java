package com.bgw.an.api;

import java.io.IOException;
import java.util.Properties;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.http.AjaxParams;
import android.content.Context;

import com.bgw.aw.base.BgwanActivity;
import com.lidroid.xutils.BitmapUtils;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��9��7�� ����3:16:55 ��˵�� : ����ӿ�API����ϸ���ݲο�����һ�������API�ĵ�
 */
public class ApiBgwAN extends BgwanActivity {

	public static String domain;
	public final static String uri = "/uu/";

	public static String getDomain() {
		return domain;
	}

	public static String getUri() {
		return uri;
	}

	public Properties getConfig() {
		return config;
	}

	public static void setDomain(String domain) {
		ApiBgwAN.domain = domain;
	}

	public void setConfig(Properties config) {
		this.config = config;
	}

	public static String user() {

		return "http://" + domain + uri + "user.do";
	}

	public static String login() {

		return "http://" + domain + uri + "userhessian";
	}

	private Properties config;

	public void loadProperties(Context context) {
		config = new Properties();
		try {
			config.load(context.getAssets().open("app.properties"));
			domain = config.getProperty("configDomain", "192.168.0.1:8080");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static AjaxParams buildParams() {
		AjaxParams params = new AjaxParams();
		if (true) {

		} else {

		}
		return params;
	}

	public static FinalBitmap buildFinalBitmap(Context context) {
		FinalBitmap finalBitmap = FinalBitmap.create(context);
		return finalBitmap;
	}

	public static BitmapUtils buildBitmapUtils(Context context) {
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		return bitmapUtils;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

}
