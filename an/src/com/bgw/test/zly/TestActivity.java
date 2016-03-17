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
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年9月6日 下午5:33:15 类说明 :
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
		// 实例化Hessian框架的factory对象，它用于连接远程服务
		// factory.setDebug(true); // 开启Hessian的DUBUG模式，可以在LogCat显示更多调试信息
		// factory.setReadTimeout(10000);
		//
		// String uri = "http://192.168.191.1:8080/uu/hessian"; // 服务端你的访问地址
		//
		// try {
		// IphoneService basic = (IphoneService) factory.create(
		// IphoneService.class, uri);
		// factory.setHessian2Reply(false); //
		// // Hessian协议的版本，false=第一版 true factory.setHessian2Request(false);
		// textView2.setText("最后的测试数据：" + basic.getJsonStrData());
		// } catch (MalformedURLException e) { // TODO Auto-generated catch
		// block
		// e.printStackTrace();
		// }

	}

	private void android18UIStandard() {
		// Android 4.0后主线程默认只能运行UI
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedSqlLiteObjects()
				.penaltyLog().penaltyDeath().build());
	}
}
