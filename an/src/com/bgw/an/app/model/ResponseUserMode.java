package com.bgw.an.app.model;

/** 
 * @author ���� staryumou@163.com: 
 * @version ����ʱ�䣺2016��1��1�� ����6:44:15 
 * ��˵�� : 
 */

import java.io.Serializable;

public class ResponseUserMode implements Serializable {
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
	private String url;// �û�ͷ��
	private String sign;// �û�����ǩ��

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

	public ResponseUserMode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseUserMode(int _id, String username, String password,
			String email) {
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
