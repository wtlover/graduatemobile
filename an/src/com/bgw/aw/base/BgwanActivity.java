package com.bgw.aw.base;

import android.os.Bundle;
import android.widget.Toast;

import com.bg.base.BaseActivity;
import com.bgw.comm.Combi;

public abstract class BgwanActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 固件化的操作
		super.onCreate(savedInstanceState);
		//DaqiApp.geCookieStore(this);

//		EMChat.getInstance().init(this.getApplicationContext());
		initView();
	}
	
	protected void showToast(final String msg) {
		
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
//				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
			}
		});
//		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 各种对象、组件的初始化
	 */
	public abstract void initView();
	
	public Combi combi() {
		return Combi.newInstance();
	}
}
