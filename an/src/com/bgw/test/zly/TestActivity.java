package com.bgw.test.zly;

import java.net.MalformedURLException;

import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.anaw.HessianHello;
import com.bgw.an.R;
import com.bgw.aw.base.BgwanActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��9��6�� ����5:33:15 ��˵�� :
 */

@ContentView(R.layout.zly)
public class TestActivity extends BgwanActivity {

	@ViewInject(R.id.textView2)
	private TextView textView2;

	private void init() {
		textView2.setMovementMethod(ScrollingMovementMethod.getInstance());

		// ServiceHttp p = new ServiceHttp();
		// String ret = p.fromFinal();
		// System.out.println(ret);
		HessianHello service = new HessianHello();
		try {
			String jsonStr = service.seeHello();
			textView2.setText(jsonStr);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initView() {
		android18UIStandard();
		init();
		// // TODO Auto-generated method stub
		// HessianProxyFactory factory = new HessianProxyFactory(); //
		// ʵ����Hessian��ܵ�factory��������������Զ�̷���
		// factory.setDebug(true); // ����Hessian��DUBUGģʽ��������LogCat��ʾ���������Ϣ
		// factory.setReadTimeout(10000);
		//
		// String uri = "http://192.168.191.1:8080/uu/hessian"; // �������ķ��ʵ�ַ
		//
		// try {
		// IphoneService basic = (IphoneService) factory.create(
		// IphoneService.class, uri);
		// factory.setHessian2Reply(false); //
		// // HessianЭ��İ汾��false=��һ�� true factory.setHessian2Request(false);
		// textView2.setText("���Ĳ������ݣ�" + basic.getJsonStrData());
		// } catch (MalformedURLException e) { // TODO Auto-generated catch
		// block
		// e.printStackTrace();
		// }

	}

	private void android18UIStandard() {
		// Android 4.0�����߳�Ĭ��ֻ������UI
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedSqlLiteObjects()
				.penaltyLog().penaltyDeath().build());
	}
}
