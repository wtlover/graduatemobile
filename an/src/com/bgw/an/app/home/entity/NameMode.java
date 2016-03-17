package com.bgw.an.app.home.entity;

import java.io.Serializable;

public class NameMode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name ;
	private String uri ;
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
