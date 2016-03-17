package com.bgw.an.app;

import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.an.server.UserService;
import com.bgw.an.Global;
import com.bgw.an.R;
import com.bgw.an.api.ApiBgwAN;
import com.bgw.aw.base.BgwanActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��12��15�� ����7:31:39 ��˵�� :
 */
@ContentView(R.layout.sst_aw_login)
public class LoginActivity extends BgwanActivity implements OnClickListener {
	private static final String loginSharePreference = "LOGIN_SHAREPREFERENCE";
	private SharedPreferences sp;

	@ViewInject(R.id.etusername)
	private EditText etUserName;

	@ViewInject(R.id.etpassword)
	private EditText etPassWord;

	@ViewInject(R.id.btnLogin)
	private Button button;

	@ViewInject(R.id.tv_register)
	private TextView tv_register;

	@ViewInject(R.id.nextText)
	private TextView nextText;

	@Override
	public void initView() {

		ApiBgwAN an = new ApiBgwAN();
		an.loadProperties(this);

		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);

		nextText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // �»���
		tv_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // �»���
		nextText.getPaint().setAntiAlias(true);// �����
		tv_register.getPaint().setAntiAlias(true);// �����
		android18UIStandard();
		button.setOnClickListener(this);
		nextText.setOnClickListener(this);
		tv_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {

		case R.id.btnLogin:
			String username = etUserName.getText().toString().trim();
			String password = etPassWord.getText().toString().trim();

			if (username == null || username.equals("") || username == ""
					|| password == null || password.equals("")
					|| password == "") {
				showToast("�˺����벻��Ϊ��");
			} else {
				UserService us = new UserService();
				int status;
				try {
					SharedPreferences mSharedPreferences = getSharedPreferences(
							loginSharePreference, Activity.MODE_PRIVATE);
					status = us.login(username, password);
					if (status == 1) {
						showToast("��½�ɹ�");
						Global.newInstance().username = username;
						Global.newInstance().password = password;
						Global.newInstance().sp = sp;
						Editor editor = Global.newInstance().sp.edit();
						editor.putString("username", username);
						editor.putString("password", password);
						editor.commit();
						intent = new Intent(LoginActivity.this,
								SplashActivity.class);
						startActivity(intent);
						LoginActivity.this.finish();
					} else if (status == 0) {
						showToast("�û������������");
					} else {
						showToast("���ܽ�����������ַ");
					}

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			break;

		case R.id.tv_register:
			intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.nextText:
			intent = new Intent(LoginActivity.this, SplashActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
			break;
		default:
			break;
		}
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
