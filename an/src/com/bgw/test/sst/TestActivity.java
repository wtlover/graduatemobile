package com.bgw.test.sst;

import java.net.MalformedURLException;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anaw.HessianHello;
import com.bgw.an.R;
import com.bgw.an.api.ApiBgwAN;
import com.bgw.an.app.activity.aiqiyi.activity.AiqiyiActivity;
import com.bgw.aw.base.BgwanActivity;
import com.bgw.comm.Combi;
import com.caucho.hessian.client.HessianProxyFactory;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��9��6�� ����5:33:15 ��˵�� :
 */

@ContentView(R.layout.test_main)
public class TestActivity extends BgwanActivity {
	@ViewInject(R.id.imageView)
	private ImageView imageView;
	@ViewInject(R.id.button)
	private Button button;
	@ViewInject(R.id.textView)
	private TextView textView;
	@ViewInject(R.id.textView2)
	private TextView textView2;

	private static Handler handler = new Handler();

	private void init() {
		
//		ServiceHttp p = new ServiceHttp();
//		String ret = p.fromFinal();
//		System.out.println(ret);
		HessianHello service = new HessianHello();
		try {
			String jsonStr = service.seeHello();
			textView2.setText(jsonStr);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		// ģ���½�ͷ������ݵ�����
		// ����android�Ķ��̣߳����ܹ����з�װ�˵�������һ���⿪������ һ����½һ���������ݺ��ӳ٣�

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						AjaxParams params1 = ApiBgwAN.buildParams();
						String url1 = "http://test.wlxy.idaqi.com/mobile/api2/user.do?cmd=login&collegeid=13&collegecode=guali&account=guali_admin&password=123456&devicekey=1&loginuuid=1&debugmode=false&pushkey=1&version=1.0.0&_usetype=text";
						int _id1 = 0;
						login(url1, params1, _id1);
					}
				}, 3000);
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						AjaxParams params1 = ApiBgwAN.buildParams();
						String url1 = "http://test.wlxy.idaqi.com/mobile/api2/enterpriseshow.do?&cmd=detail&text=&pagenum=1&pagepercount=4&_usetype=text&id=1287226&version=1.0.0";
						int _id1 = 1;
						login(url1, params1, _id1);
					}
				}, 3000);
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						AjaxParams params1 = ApiBgwAN.buildParams();
						String url1 = null;
						int _id1 = 2;
						login(url1, params1, _id1);
					}
				}, 3000);
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						AjaxParams params1 = ApiBgwAN.buildParams();
						String url1 = null;
						int _id1 = 3;
						login(url1, params1, _id1);
					}
				}, 3000);
			}
		}).start();

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

		// imageView = (ImageView) findViewById(R.id.imageView);
		String url1 = "http://img1.cache.netease.com/catchpic/1/15/15F8D5985990FF28CFC73055543D8799.jpg";
		FinalBitmap fb = ApiBgwAN.buildFinalBitmap(getApplicationContext());
		fb.display(imageView, url1);

		/*
		 * BitmapUtils bu = ApiBgwAN.buildBitmapUtils(getApplicationContext());
		 * if(url == null){ Toast.makeText(TestActivity.this, "�������gsdfgfd",
		 * 2).show(); }else{ bu.display(imageView, url);
		 * Toast.makeText(TestActivity.this, "�������", 2).show(); }
		 */
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TestActivity.this,
						AiqiyiActivity.class);
				startActivity(intent);
			}
		});
		init();
	}

	public void login(String url, AjaxParams params, int _id) {
		FinalHttp fp = Combi.httpClient();
		if (_id == 0) {
			fp.post(url, params, new AjaxCallBack<String>() {
				@Override
				public void onFailure(Throwable t, String strMsg) {
					// TODO Auto-generated method stub
					showToast("��һ�������������");
				}

				@Override
				public void onSuccess(String ret) {
					// TODO Auto-generated method stub
					showToast("�ɹ���½");

				}
			});
		} else if (_id == 1) {
			fp.post(url, params, new AjaxCallBack<String>() {
				@Override
				public void onFailure(Throwable t, String strMsg) {
					// TODO Auto-generated method stub
					showToast("�ڶ��������������");

				}

				@Override
				public void onSuccess(String ret) {
					// TODO Auto-generated method stub
					showToast("���Բ�������");
					textView.setText("����" + ret);

				}
			});
		} else if (_id == 2) {
			fp.get("http://192.168.191.1:8080/an/hessian/an",
					new AjaxCallBack<String>() {
						@Override
						public void onFailure(Throwable t, String strMsg) {
							// TODO Auto-generated method stub
							super.onFailure(t, strMsg);
							showToast("�����������������");
						}

						@Override
						public void onSuccess(String t) {
							// TODO Auto-generated method stub
							super.onSuccess(t);
							textView.setText("�ٶȲ�������" + t);
						}
					});
		} else if (_id == 3) {
			HessianProxyFactory factory = new HessianProxyFactory(); // ʵ����Hessian��ܵ�factory��������������Զ�̷���
			factory.setDebug(true); // ����Hessian��DUBUGģʽ��������LogCat��ʾ���������Ϣ
			factory.setReadTimeout(10000);

			String uri = "http://192.168.191.1:8080/an/hessian/an"; // �������ķ��ʵ�ַ
			/*
			 * try { IphoneService basic = (IphoneService) factory.create(
			 * IphoneService.class, uri); factory.setHessian2Reply(false); //
			 * HessianЭ��İ汾��false=��һ�� true factory.setHessian2Request(false);
			 * textView.setText("���Ĳ������ݣ�"+basic.getJsonStrData()); } catch
			 * (MalformedURLException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */

		}
	}

}
