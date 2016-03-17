package com.bgw.test;

import java.net.MalformedURLException;

import com.an.server.UserService;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年12月30日 下午3:21:35 类说明 :
 */
public class HessianTest {

	public static void main(String[] args) throws MalformedURLException {
		UserService us = new UserService();
		int status = us.login("bgw", "123456");
		if (status == 1) {
			System.out.println("成功，了登陆");
		} else {
			System.out.println("失败了 ，登陆");
		}
	}

}
