package com.bgw.an.app.activity.me;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bgw.an.Global;
import com.bgw.an.R;
import com.bgw.an.api.ApiBgwAN;
import com.bgw.an.app.LoginActivity;
import com.bgw.an.app.model.RespondModeStr;
import com.bgw.aw.base.BgwanActivity;
import com.bgw.aw.utils.FeatureUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2016年1月1日 下午8:45:43 类说明 :
 *          修改密码。苦逼啊，元旦都放假了，还在写毕业设计，希望可以拿一个优秀毕业设计吧。
 */
@ContentView(R.layout.sst_update_password)
public class UpdatePsAcitivity extends BgwanActivity {
	@ViewInject(R.id.textView)
	private TextView textView;
	@ViewInject(R.id.btn)
	private Button btn;
	@ViewInject(R.id.password)
	private EditText password;

	@Override
	public void initView() {
		FeatureUtils.featureUtils(this, getWindow());
		textView.setBackgroundColor(0xff42ab82);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (password.getText().toString().equals("")
						|| password.getText().toString() == null) {
					showToast("密码不能为空");
				} else {
					if (!password.equals(Global.newInstance().password)) {
						_loadData(password.getText().toString());
					} else {
						showToast("密码不能和以前的密码相同");
					}

				}
			}

			private void _loadData(String password) {

				FinalHttp fp = Global.newInstance().buildHTTP();
				AjaxParams params = Global.newInstance().getParams();
				String url = ApiBgwAN.user();
				params.put("cmd", "updatepassword");
				params.put("username", Global.newInstance().username);
				params.put("password", password);
				fp.post(url, params, new AjaxCallBack<String>() {
					@Override
					public void onFailure(Throwable t, String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, strMsg);
						showToast("网络错误");
					}

					@Override
					public void onSuccess(String ret) {
						if (ret.equals("") || ret == null) {
						} else {
							// TODO Auto-generated method stub
							super.onSuccess(ret);
							RespondModeStr mod = new RespondModeStr();
							Gson gson = new Gson();
							mod = gson.fromJson(ret, RespondModeStr.class);
							if (mod.isSuccess()) {
								Intent intent = new Intent(
										UpdatePsAcitivity.this,
										LoginActivity.class);
								startActivity(intent);
								showToast(mod.getMsg());
								UpdatePsAcitivity.this.finish();
							} else {
								showToast(mod.getMsg());
							}
						}

					}
				});
			}
		});
	}

}
