package com.bgw.an.app;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bgw.an.MainActivity;
import com.bgw.an.R;
import com.bgw.an.app.activity.chat.view.ParallaxContainer;
import com.bgw.an.app.activity.chat.view.ParallaxContainer.OnCommClickListener;
import com.bgw.an.app.activity.chat.view.ParallaxFragment;
import com.bgw.comm.Combi;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��9��6�� ����2:29:10 ��˵�� : ����������ҳ��app������Ϣ�ļ��أ������뿪����Զ���
 */
public class SplashActivity extends FragmentActivity implements
		OnCommClickListener {

	private Button button;
	private Button button2;

	private String configDeveloper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shicha_home);
		/*
		 * ������ҳ�Ĵ��� Combi.newInstance().loadProperties(this); if
		 * (Combi.newInstance().configDevelopmode) { // ���� ������ģʽ String
		 * configDeveloper = Combi.newInstance().configDeveloper;
		 * 
		 * String className = "com.bgw.test." + configDeveloper +
		 * ".TestActivity"; LogUtils.i(className); try { Class clazz =
		 * Class.forName(className); Intent intent = new Intent(this, clazz);
		 * startActivity(intent); } catch (ClassNotFoundException e) {
		 * LogUtils.e("δ�ҵ�������(" + configDeveloper + ")"); } } else { Intent
		 * intent = new Intent(SplashActivity.this, MainActivity.class);
		 * startActivity(intent); }
		 */
		ParallaxContainer container = (ParallaxContainer) findViewById(R.id.parallaxContainer);
		List<ParallaxFragment> listPF = container.setUp(new int[] {
				R.layout.zly_shicha_knowlage, R.layout.zly_shicha_chat,
				R.layout.zly_shicha_video, R.layout.zly_shicha_mejoin });

		container.setcListener(this);
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		iv.setBackgroundResource(R.anim.zlyrun);
		// iv.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zlyrun));
		container.setIv(iv);

		button2 = (Button) findViewById(R.id.btn);

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "dianjile��ť2",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void OnCommClick() {
		// TODO Auto-generated method stub
//		Toast.makeText(getApplicationContext(), "��˳��", Toast.LENGTH_LONG).show();
		Combi.newInstance().loadProperties(this);
		boolean is = Combi.newInstance().configDevelopmode;
		// ������ҳ�Ĵ��� Combi.newInstance().loadProperties(this);
		if (is) { // ���� ������ģʽ String
			configDeveloper = Combi.newInstance().configDeveloper;

			String className = "com.bgw.test." + configDeveloper
					+ ".TestActivity";
			try {
				Class clazz = Class.forName(className);
				Intent intent = new Intent(this, clazz);
				startActivity(intent);
			} catch (ClassNotFoundException e) {

			}
		} else {
			Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			SplashActivity.this.finish();
		}

	}

}
