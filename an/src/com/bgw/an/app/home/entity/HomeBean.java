package com.bgw.an.app.home.entity;

import java.io.Serializable;

public class HomeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title ;
	private int _id ;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}

	

}
