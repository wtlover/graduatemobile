package com.an;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��12��1�� ����7:48:13 ��˵�� :
 */
public class ServiceHttp {
	private String ret;
	
	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String fromFinal() {
		String url = "http://192.168.191.1:8080/uu/hessian";
		FinalHttp fp = new FinalHttp();
		AjaxParams params = new AjaxParams();
		fp.post(url, params, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(ret);
				ret = t;
			}
		});
		return ret;
	}

}
