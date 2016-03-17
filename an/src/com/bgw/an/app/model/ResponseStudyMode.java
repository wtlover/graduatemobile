package com.bgw.an.app.model;
/** 
 * @author 作者 staryumou@163.com: 
 * @version 创建时间：2016年1月3日 下午5:17:52 
 * 类说明 : 
 */

import java.io.Serializable;

public class ResponseStudyMode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _id;//学习记录ID
	private String name;//学习的标题
	private String url;//学习标题的链接url

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

