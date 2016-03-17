package com.bgw.an.app.model;

import java.io.Serializable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��9��3�� ����5:56:00 ��˵�� :
 */
public class RespondModeArr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JsonArray body;
	private String msg;
	private boolean success;

	public JsonArray getBody() {
		return body;
	}

	public void setBody(JsonArray body) {
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
