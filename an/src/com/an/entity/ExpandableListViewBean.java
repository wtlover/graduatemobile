package com.an.entity;

import java.io.Serializable;

public class ExpandableListViewBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName ;
	private String passWorld;
	
	public ExpandableListViewBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExpandableListViewBean(String userName,String passWorld) {
		super();
		// TODO Auto-generated constructor stub
		this.userName = userName;
		this.passWorld = passWorld;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWorld() {
		return passWorld;
	}
	public void setPassWorld(String passWorld) {
		this.passWorld = passWorld;
	}
	@Override
	public String toString() {
		return "ExpandableListViewBean [userName=" + userName + ", passWorld="
				+ passWorld + "]";
	}
	

}
