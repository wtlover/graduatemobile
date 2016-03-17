package com.an.server;

import java.net.MalformedURLException;

import com.bgw.an.api.ApiBgwAN;
import com.caucho.hessian.client.HessianProxyFactory;

public class UserService {
	private IUserService basic;

	public int login(String account, String password)
			throws MalformedURLException {
		int status = 1;
		HessianProxyFactory factory = new HessianProxyFactory(); // ʵ����Hessian��ܵ�factory��������������Զ�̷���
		factory.setDebug(true); // ����Hessian��DUBUGģʽ��������LogCat��ʾ���������Ϣ
		factory.setReadTimeout(10000);

		String url = ApiBgwAN.login(); // �������ķ��ʵ�ַ
		try {

			basic = (IUserService) factory.create(IUserService.class, url);

			factory.setHessian2Reply(false); // HessianЭ��İ汾��false=��һ�� true
			factory.setHessian2Request(false);

			User user = basic.login(account, password);
			if (user == null) {
				status = 0;
			} else {
				status = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = 2;
		}
		return status;

	}

	public int update(String account, String password)
			throws MalformedURLException {
		HessianProxyFactory factory = new HessianProxyFactory(); // ʵ����Hessian��ܵ�factory��������������Զ�̷���
		factory.setDebug(true); // ����Hessian��DUBUGģʽ��������LogCat��ʾ���������Ϣ
		factory.setReadTimeout(10000);
		String url = "http://192.168.191.1:8080/uu/userhessian";
		// �������ķ��ʵ�ַ
		IUserService phoneService = (IUserService) factory.create(
				IUserService.class, url);

		factory.setHessian2Reply(false); // HessianЭ��İ汾��false=��һ�� true
		factory.setHessian2Request(false);

		try {
			int rs = phoneService.update(account, password);
			if (rs == 1) {
				return rs;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
}
