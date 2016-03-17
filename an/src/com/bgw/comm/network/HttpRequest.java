package com.bgw.comm.network;

import net.tsz.afinal.FinalHttp;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * 
 * @author acmersch
 * http请求器
 */
public class HttpRequest {
	/**
	 * 默认超时时间
	 */
	private static final int DEFAULTTIMEOUT = 10000;
	/**
	 * 默认连接错误重试次数
	 */
	private static final int DEFAULRETRYCOUNT= 1;
	private static final String DEFAULTCHARSET = "UTF-8";
	/**
	 * 请求超时时间
	 */
	private int timeout;
	/**
	 * 连接错误重试次数
	 */
	private int retrycount;
	/**
	 * http会话
	 */
	private String jsessionid;
	
	
	public HttpRequest() {
		this(DEFAULTTIMEOUT, DEFAULRETRYCOUNT, null);
	}
	
	public HttpRequest(int timeout, int retrycount, String jsessionid) {
		this.timeout = timeout;
		this.retrycount = retrycount;
		this.jsessionid = jsessionid;
	}
	
	public FinalHttp buildHTTP() {
		FinalHttp finalHttp = new FinalHttp();		
		CookieStore cookieStore = new BasicCookieStore();
		
		finalHttp.addHeader("Accept-Charset", DEFAULTCHARSET);//配置http请求头
		finalHttp.configCharset(DEFAULTCHARSET);
		finalHttp.configCookieStore(cookieStore);
		finalHttp.configRequestExecutionRetryCount(retrycount);//请求错误重试次数
		finalHttp.configTimeout(timeout);//超时时间
		finalHttp.configUserAgent("combi/1.0");//配置客户端信息
		if (this.jsessionid!=null && !this.jsessionid.trim().equals("")) {
			finalHttp.addHeader("Cookie", "JSESSIONID="+this.jsessionid);
		}
		
		return finalHttp;
	}
}
