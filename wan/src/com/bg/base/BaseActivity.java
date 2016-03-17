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
 * Activity����.
 * 
 * @author ��˳��
 * @blog bgwan.blog.163.com
 * @version 1.0 2014-11-18
 */
public abstract class BaseActivity extends FragmentActivity {

	/** ����(Activity)��ʹ�õ�TAG��ǩ */
	protected String TAG = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ���ñ�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// ע������
		ViewUtils.inject(this);

		// ����ȷ����ǰ�����������ĸ��(Activity), ���¼��뿪�����˿����������ڵĽ���,���������Ƴ�.
		TAG = getClass().getSimpleName();
		LogUtils.d(TAG);

		// ������activity��ӵ�activity�ɼ���
		UtilActivity.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ������activity��activity�ɼ������Ƴ�
		UtilActivity.removeActivity(this);
	}

	// �������·���,���ö���.

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
