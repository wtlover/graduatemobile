package com.bgw.an.app.activity.home;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bgw.an.Global;
import com.bgw.an.MainActivity;
import com.bgw.an.R;
import com.bgw.an.api.ApiBgwAN;
import com.bgw.an.app.model.RespondModeInteger;
import com.bgw.aw.base.BgwanActivity;
import com.bgw.aw.utils.FeatureUtils;
import com.bgw.aw.utils.VaildataUtils;
import com.bgw.aw.utils.WtURLAvailability;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2016年1月1日 下午8:45:43 类说明 :
 *          修改密码。苦逼啊，元旦都放假了，还在写毕业设计，希望可以拿一个优秀毕业设计吧。
 */
@ContentView(R.layout.sst_home_addstudy)
public class AddInfoAcitivity extends BgwanActivity {
	@ViewInject(R.id.textView)
	private TextView textView;
	@ViewInject(R.id.btnAdd)
	private Button btn;
	@ViewInject(R.id.eturl)
	private EditText eturl;
	@ViewInject(R.id.etTitle)
	private EditText etTitle;

	@Override
	public void initView() {
		FeatureUtils.featureUtils(this, getWindow());
		textView.setBackgroundColor(0xff42ab82);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String title = etTitle.getText().toString();
				String url = eturl.getText().toString();
				if (url.equals("") || url == null || title.equals("")
						|| title == null) {
					showToast("资料标题和链接不能为空");
				} else {
					if (VaildataUtils.isURL(url)) {
						if (WtURLAvailability.isURLAvailability(url)) {
							_loadData(title, url);
						} else {
							showToast("链接打不开");
						}
					} else {
						showToast("链接格式不正确");
					}

				}
			}

			private void _loadData(String title, String dataUrl) {

				FinalHttp fp = Global.newInstance().buildHTTP();
				AjaxParams params = Global.newInstance().getParams();
				String url = ApiBgwAN.user();
				params.put("cmd", "adddata");
				params.put("name", title);
				params.put("url", dataUrl);
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
							RespondModeInteger mod = new RespondModeInteger();
							Gson gson = new Gson();
							mod = gson.fromJson(ret, RespondModeInteger.class);
							if (mod.isSuccess()) {
								if (mod.getBody() == 1) {
									Intent intent = new Intent(
											AddInfoAcitivity.this,
											MainActivity.class);
									startActivity(intent);
									showToast("添加成功");
									AddInfoAcitivity.this.finish();
								} else {
									showToast("添加失败");
								}
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
