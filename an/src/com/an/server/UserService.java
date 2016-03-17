package com.an.server;

import java.net.MalformedURLException;

import com.bgw.an.api.ApiBgwAN;
import com.caucho.hessian.client.HessianProxyFactory;

public class UserService {
	private IUserService basic;

	public int login(String account, String password)
			throws MalformedURLException {
		int status = 1;
		HessianProxyFactory factory = new HessianProxyFactory(); // 实例化Hessian框架的factory对象，它用于连接远程服务
		factory.setDebug(true); // 开启Hessian的DUBUG模式，可以在LogCat显示更多调试信息
		factory.setReadTimeout(10000);

		String url = ApiBgwAN.login(); // 服务端你的访问地址
		try {

			basic = (IUserService) factory.create(IUserService.class, url);

			factory.setHessian2Reply(false); // Hessian协议的版本，false=第一版 true
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
		HessianProxyFactory factory = new HessianProxyFactory(); // 实例化Hessian框架的factory对象，它用于连接远程服务
		factory.setDebug(true); // 开启Hessian的DUBUG模式，可以在LogCat显示更多调试信息
		factory.setReadTimeout(10000);
		String url = "http://192.168.191.1:8080/uu/userhessian";
		// 服务端你的访问地址
		IUserService phoneService = (IUserService) factory.create(
				IUserService.class, url);

		factory.setHessian2Reply(false); // Hessian协议的版本，false=第一版 true
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
