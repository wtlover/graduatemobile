package com.an.server;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _id;// ���ݿ��id
	private String username;// �û�����
	private String password;// �û�����
	private String email;// �û��ʼ�
	private String sex;// �û��Ա�
	private String qq;// �û�QQ��ϵ��ʽ

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int _id, String username, String password, String email) {
		this._id = _id;
		this.username = username;
		this.password = password;
		this.email = email;

	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
