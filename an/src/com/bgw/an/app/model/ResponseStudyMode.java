package com.bgw.an.app.model;
/** 
 * @author ���� staryumou@163.com: 
 * @version ����ʱ�䣺2016��1��3�� ����5:17:52 
 * ��˵�� : 
 */

import java.io.Serializable;

public class ResponseStudyMode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _id;//ѧϰ��¼ID
	private String name;//ѧϰ�ı���
	private String url;//ѧϰ���������url

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

