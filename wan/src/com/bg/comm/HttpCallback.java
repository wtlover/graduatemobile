package com.bg.comm;

import com.lidroid.xutils.http.RequestParams;

/**
 * Activity����.
 * 
 * @author ��˳��
 * @blog bgwan.blog.163.com
 * @version 1.0 2014-11-18
 */
public interface HttpCallback {

	/**
	 * WANG luo ���ݻص��ӿ�
	 * 
	 * @param paramFlag
	 * @return
	 */
	public RequestParams onParams(int paramFlag);

	/**
	 * �׸��װ�����ʦ��֣��ϼ �� ����������ϢѧԺ/
	 * 
	 * @param result
	 * @param callbackflag
	 */
	public void onSuccess(String result, int callbackflag);

}
