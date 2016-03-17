package com.bgw.comm.network;

import net.tsz.afinal.FinalHttp;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * 
 * @author acmersch
 * http������
 */
public class HttpRequest {
	/**
	 * Ĭ�ϳ�ʱʱ��
	 */
	private static final int DEFAULTTIMEOUT = 10000;
	/**
	 * Ĭ�����Ӵ������Դ���
	 */
	private static final int DEFAULRETRYCOUNT= 1;
	private static final String DEFAULTCHARSET = "UTF-8";
	/**
	 * ����ʱʱ��
	 */
	private int timeout;
	/**
	 * ���Ӵ������Դ���
	 */
	private int retrycount;
	/**
	 * http�Ự
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
		
		finalHttp.addHeader("Accept-Charset", DEFAULTCHARSET);//����http����ͷ
		finalHttp.configCharset(DEFAULTCHARSET);
		finalHttp.configCookieStore(cookieStore);
		finalHttp.configRequestExecutionRetryCount(retrycount);//����������Դ���
		finalHttp.configTimeout(timeout);//��ʱʱ��
		finalHttp.configUserAgent("combi/1.0");//���ÿͻ�����Ϣ
		if (this.jsessionid!=null && !this.jsessionid.trim().equals("")) {
			finalHttp.addHeader("Cookie", "JSESSIONID="+this.jsessionid);
		}
		
		return finalHttp;
	}
}
