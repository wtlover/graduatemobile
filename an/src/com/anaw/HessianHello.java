package com.anaw;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class HessianHello {
	private String jsonData;

	public String seeHello() throws MalformedURLException {

		try {
			HessianProxyFactory factory = new HessianProxyFactory(); // ʵ����Hessian��ܵ�factory��������������Զ�̷���
			factory.setDebug(true); // ����Hessian��DUBUGģʽ��������LogCat��ʾ���������Ϣ
			factory.setReadTimeout(10000);

			String url = "http://192.168.191.1:8080/uu/hessian"; // �������ķ��ʵ�ַ
			IHessianHello basic = (IHessianHello) factory.create(
					IHessianHello.class, url);

			factory.setHessian2Reply(false); // HessianЭ��İ汾��false=��һ�� true
			factory.setHessian2Request(false);

			jsonData = basic.seeHello();
			return jsonData;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return jsonData;
	}

}
