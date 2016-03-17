package com.bgw.an;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import android.content.SharedPreferences;

public class Global {
	public static Global global;

	public String configVersion;
	public boolean configDebugmode;
	public int configtimeout;
	public int configretrycount;
	public String configDeveloper;
	public int configCollegeid;
	public String configCollegeCode;

	public SharedPreferences sp;
	public SharedPreferences getSp() {
		return sp;
	}

	public void setSp(SharedPreferences sp) {
		this.sp = sp;
	}

	public String username;
	public String password;

	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static Global newInstance() {
		if (global == null) {
			global = new Global();
		}
		return global;
	}

	// 最好还是采用这里面的FinallHttp 这样是最正确的方式
	public FinalHttp buildHTTP() {
		FinalHttp finalHttp = new FinalHttp();
		CookieStore cookieStore = new BasicCookieStore();

		finalHttp.addHeader("Accept-Charset", "UTF-8");
		finalHttp.configCharset("UTF-8");
		finalHttp.configCookieStore(cookieStore);
		finalHttp
				.configRequestExecutionRetryCount(Global.newInstance().configretrycount);
		finalHttp.configTimeout(Global.newInstance().configtimeout);
		//自定义协议版本
		finalHttp.configUserAgent("combi/1.0");

		return finalHttp;
	}

	public AjaxParams getParams() {
		AjaxParams params = new AjaxParams();
		// params.put("version", Global.newInstance().configVersion);
		return params;
	}

}
