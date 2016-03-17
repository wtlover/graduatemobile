package com.bgw.an.app.activity.chat.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseActivity;

public class WelcomeActivity extends BaseActivity implements OnClickListener {

	private Button mBtnLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		mBtnLogin = (Button) findViewById(R.id.welcome_btn_login);
	}

	@Override
	protected void initEvents() {
		mBtnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.welcome_btn_login:
			startActivity(LoginActivity.class);
			break;

		}
	}

	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub

	}
}
