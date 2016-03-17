package com.bgw.an.app;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.bgw.an.R;
import com.bgw.an.api.ApiBgwAN;
import com.bgw.aw.base.BgwanActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年12月15日 下午7:31:39 类说明 :
 */
@ContentView(R.layout.sst_person_register)
public class RegisterActivity extends BgwanActivity implements OnClickListener {
	@ViewInject(R.id.username)
	private EditText username;
	@ViewInject(R.id.password)
	private EditText password;
	@ViewInject(R.id.etEmail)
	private EditText etEmail;
	@ViewInject(R.id.etSex)
	private EditText etSex;
	@ViewInject(R.id.url)
	private EditText url;
	@ViewInject(R.id.etQQ)
	private EditText etQQ;
	@ViewInject(R.id.etSign)
	private EditText etSign;

	@ViewInject(R.id.btnRegister)
	private Button button;

	@Override
	public void initView() {

		android18UIStandard();
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnRegister:
			String usernames = username.getText().toString().trim();
			String passwords = password.getText().toString().trim();

			if (usernames == null || usernames.equals("") || usernames == ""
					|| passwords == null || passwords.equals("")
					|| passwords == "") {
				showToast("用户名和密码不能为空");
			} else {
				String sexs = etSex.getText().toString().trim();
				String Qqs = etQQ.getText().toString().trim();
				String emails = etEmail.getText().toString().trim();
				String urls = url.getText().toString().trim();
				String signs = etSign.getText().toString().trim();

				String url = ApiBgwAN.user();
				FinalHttp fp = new FinalHttp();
				AjaxParams params = new AjaxParams();
				params.put("cmd", "register");
				params.put("username", usernames);
				params.put("password", passwords);
				params.put("sex", sexs);
				params.put("email", emails);
				params.put("url", urls);
				params.put("qq", Qqs);
				params.put("sign", signs);

				fp.post(url, params, new AjaxCallBack<String>() {
					@Override
					public void onFailure(Throwable t, String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, strMsg);
						showToast("网络错误");
					}

					@Override
					public void onSuccess(String ret) {
						// TODO Auto-generated method stub
						super.onSuccess(ret);
						showToast(ret);
					}
				});

			}

			break;

		default:
			break;
		}
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
