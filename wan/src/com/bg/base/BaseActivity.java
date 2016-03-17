package com.bg.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bg.comm.UtilActivity;
import com.bg.wan.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;

/**
 * Activity基类.
 * 
 * @author 孙顺涛
 * @blog bgwan.blog.163.com
 * @version 1.0 2014-11-18
 */
public abstract class BaseActivity extends FragmentActivity {

	/** 基类(Activity)所使用的TAG标签 */
	protected String TAG = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 注解的入口
		ViewUtils.inject(this);

		// 用于确定当前界面是属于哪个活动(Activity), 让新加入开发的人快速锁定所在的界面,不得擅自移除.
		TAG = getClass().getSimpleName();
		LogUtils.d(TAG);

		// 将其子activity添加到activity采集器
		UtilActivity.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 将其子activity从activity采集器中移除
		UtilActivity.removeActivity(this);
	}

	// 覆盖以下方法,设置动画.

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.aim_common_right_in,
				R.anim.aim_common_left_out);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.aim_common_right_in,
				R.anim.aim_common_zoom_out);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.aim_common_left_in,
				R.anim.aim_common_right_out);
	}

	public String getViewValue(View view) {
		if (view instanceof EditText) {
			return ((EditText) view).getText().toString();
		} else if (view instanceof TextView) {
			return ((TextView) view).getText().toString();
		} else if (view instanceof CheckBox) {
			return ((CheckBox) view).getText().toString();
		} else if (view instanceof RadioButton) {
			return ((RadioButton) view).getText().toString();
		} else if (view instanceof Button) {
			return ((Button) view).getText().toString();
		}
		return null;
	}

}
