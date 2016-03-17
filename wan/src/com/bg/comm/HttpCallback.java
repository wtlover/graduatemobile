package com.bg.comm;

import com.lidroid.xutils.http.RequestParams;

/**
 * Activity基类.
 * 
 * @author 孙顺涛
 * @blog bgwan.blog.163.com
 * @version 1.0 2014-11-18
 */
public interface HttpCallback {

	/**
	 * WANG luo 数据回掉接口
	 * 
	 * @param paramFlag
	 * @return
	 */
	public RequestParams onParams(int paramFlag);

	/**
	 * 献给亲爱的老师，郑东霞 ； 大连东软信息学院/
	 * 
	 * @param result
	 * @param callbackflag
	 */
	public void onSuccess(String result, int callbackflag);

}
