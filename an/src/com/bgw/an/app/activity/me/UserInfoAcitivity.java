package com.bgw.an.app.activity.me;

import java.io.UnsupportedEncodingException;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgw.an.Global;
import com.bgw.an.R;
import com.bgw.an.api.ApiBgwAN;
import com.bgw.an.app.model.RespondModeObj;
import com.bgw.an.app.model.ResponseUserMode;
import com.bgw.aw.base.BgwanActivity;
import com.bgw.aw.utils.FeatureUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2016年1月1日 下午8:45:43 类说明 :
 *          修改密码。苦逼啊，元旦都放假了，还在写毕业设计，希望可以拿一个优秀毕业设计吧。
 */
@ContentView(R.layout.sst_person_userinfo)
public class UserInfoAcitivity extends BgwanActivity {
	@ViewInject(R.id.textView)
	private TextView textView;
	@ViewInject(R.id.btn)
	private Button btn;

	@ViewInject(R.id.username)
	private EditText username;
	@ViewInject(R.id.password)
	private EditText password;
	@ViewInject(R.id.etEmail)
	private EditText etEmail;
	@ViewInject(R.id.etSex)
	private EditText etSex;
	@ViewInject(R.id.url)
	private EditText eturl;
	@ViewInject(R.id.etQQ)
	private EditText etQQ;
	@ViewInject(R.id.etSign)
	private EditText etSign;

	@ViewInject(R.id.imageView)
	private ImageView imageView;

	private boolean isEdit = true;

	private FinalHttp fp;
	private String url;
	private int _id;

	@Override
	public void initView() {
		FeatureUtils.featureUtils(this, getWindow());
		textView.setBackgroundColor(0xff42ab82);

		fp = Global.newInstance().buildHTTP();
		url = ApiBgwAN.user();

		_loadUserInfo();
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isEdit) {
					etEmail.setEnabled(true);
					etEmail.setClickable(true);
					etSex.setEnabled(true);
					etSex.setClickable(true);
					eturl.setEnabled(true);
					eturl.setClickable(true);
					etQQ.setEnabled(true);
					etQQ.setClickable(true);
					etSign.setEnabled(true);
					etSign.setClickable(true);
					btn.setText("保存");

					isEdit = false;

				} else {
					btn.setText("编辑");
					etEmail.setEnabled(false);
					etEmail.setClickable(false);
					etSex.setEnabled(false);
					etSex.setClickable(false);
					eturl.setEnabled(false);
					eturl.setClickable(false);
					etQQ.setEnabled(false);
					etQQ.setClickable(false);
					etSign.setEnabled(false);
					etSign.setClickable(false);
					try {
						_loadData();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			private void _loadData() throws UnsupportedEncodingException {

				AjaxParams params = Global.newInstance().getParams();
				params.put("cmd", "updateuserinfo");
				params.put("_id", _id + "");

				String urlsss = "";
				params.put("username", Global.newInstance().username);
				params.put("password", Global.newInstance().password);
				params.put("email", etEmail.getText().toString());
				String sex = etSex.getText().toString();
				// sex = new String(sex.getBytes("iso-8859-1"), "UTF-8");

				params.put("sex", sex);
				params.put("url", eturl.getText().toString());
				params.put("sign", etSign.getText().toString());
				params.put("qq", etQQ.getText().toString());

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
						if (ret.equals("") || ret == null) {
						} else {
							showToast(ret);
							isEdit = true;
						}

					}
				});

			}
		});

	}

	private void _loadUserInfo() {
		AjaxParams params = Global.newInstance().getParams();
		params.put("cmd", "userinfo");
		params.put("username", Global.newInstance().username);
		params.put("password", Global.newInstance().password);
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
				RespondModeObj mod = new RespondModeObj();
				Gson gson = new Gson();
				mod = gson.fromJson(ret, RespondModeObj.class);
				boolean isSuccess = mod.isSuccess();
				if (isSuccess) {
					JsonObject body = mod.getBody();
					ResponseUserMode rb = new ResponseUserMode();
					rb = gson.fromJson(body, ResponseUserMode.class);
					username.setText(rb.getUsername() == null ? "" : rb
							.getUsername());
					etSex.setText(rb.getSex() == null ? "" : rb.getSex());
					etSign.setText(rb.getSign() == null ? "时间是让人触不及防的东西" : rb
							.getSign());
					password.setText(rb.getPassword() == null ? "" : rb
							.getPassword());
					etEmail.setText(rb.getEmail() == null ? "" : rb.getEmail());
					eturl.setText(rb.getUrl() == null ? "" : rb.getUrl());
					etQQ.setText(rb.getQq() == null ? "" : rb.getQq());
					_id = rb.get_id();
				} else {
					showToast(mod.getMsg());
				}
			}
		});

	}

}
