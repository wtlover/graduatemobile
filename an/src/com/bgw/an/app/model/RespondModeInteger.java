package com.bgw.an.app.model;

import java.io.Serializable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年9月3日 下午5:56:00 类说明 :
 */
public class RespondModeInteger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int body;
	private String msg;
	private boolean success;

	public int getBody() {
		return body;
	}

	public void setBody(int body) {
		this.body = body;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "RespondMode [body=" + body + ", msg=" + msg + ", success="
				+ success + "]";
	}

}
