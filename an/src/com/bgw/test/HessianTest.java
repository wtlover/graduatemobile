package com.bgw.test;

import java.net.MalformedURLException;

import com.an.server.UserService;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��12��30�� ����3:21:35 ��˵�� :
 */
public class HessianTest {

	public static void main(String[] args) throws MalformedURLException {
		UserService us = new UserService();
		int status = us.login("bgw", "123456");
		if (status == 1) {
			System.out.println("�ɹ����˵�½");
		} else {
			System.out.println("ʧ���� ����½");
		}
	}

}
