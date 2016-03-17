package com.anaw;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class HessianHello {
	private String jsonData;

	public String seeHello() throws MalformedURLException {

		try {
			HessianProxyFactory factory = new HessianProxyFactory(); // 实例化Hessian框架的factory对象，它用于连接远程服务
			factory.setDebug(true); // 开启Hessian的DUBUG模式，可以在LogCat显示更多调试信息
			factory.setReadTimeout(10000);

			String url = "http://192.168.191.1:8080/uu/hessian"; // 服务端你的访问地址
			IHessianHello basic = (IHessianHello) factory.create(
					IHessianHello.class, url);

			factory.setHessian2Reply(false); // Hessian协议的版本，false=第一版 true
			factory.setHessian2Request(false);

			jsonData = basic.seeHello();
			return jsonData;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return jsonData;
	}

}
