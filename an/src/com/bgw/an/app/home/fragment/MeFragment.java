package com.bgw.an.app.home.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.h.m;
import com.bgw.an.Global;
import com.bgw.an.R;
import com.bgw.an.api.ApiBgwAN;
import com.bgw.an.app.LoginActivity;
import com.bgw.an.app.activity.chat.activity.AboutActivity;
import com.bgw.an.app.activity.chat.view.ScrollingTextView;
import com.bgw.an.app.activity.me.BaiduMapAcitivity;
import com.bgw.an.app.activity.me.UpdatePsAcitivity;
import com.bgw.an.app.activity.me.UserInfoAcitivity;
import com.bgw.an.app.activity.me.musicdemon.MainActivity;
import com.bgw.an.app.model.RespondModeObj;
import com.bgw.an.app.model.ResponseUserMode;
import com.bgw.aw.base.BgwanFragment;
import com.bgw.aw.view.SearchTag;
import com.bgw.aw.view.SearchTag.TagOnClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MeFragment extends BgwanFragment implements TagOnClickListener,
		OnClickListener {
	@ViewInject(R.id.searchTag)
	private SearchTag searchTag;
	@ViewInject(R.id.llFragment_me)
	private LinearLayout llFragment_me;
	@ViewInject(R.id.tvName)
	private TextView tvName;
	@ViewInject(R.id.tvSex)
	private TextView tvSex;
	@ViewInject(R.id.tvSign)
	private TextView tvSign;
	@ViewInject(R.id.userinfo)
	private TextView userinfo;
	@ViewInject(R.id.other)
	private TextView other;
	@ViewInject(R.id.updatePassword)
	private TextView updatePassword;

	@ViewInject(R.id.imgHead)
	private ImageView imageView;
	@ViewInject(R.id.imageView)
	private ImageView imageViewtrue;

	private String username;
	private String password;
	private Intent intent;

	@Override
	public RequestParams onParams(int paramFlag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSuccess(String result, int callbackflag) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_me;
	}

	@Override
	public void init() {
		username = Global.newInstance().username;
		password = Global.newInstance().password;
		if (username == null || password == null) {
			llFragment_me.setBackgroundColor(0xff42ab82);
			searchTag.setTagOnclick(this);
			updatePassword.setOnClickListener(this);
			other.setOnClickListener(this);
			userinfo.setOnClickListener(this);
			_loadLayout();
		} else {
			llFragment_me.setBackgroundColor(0xff42ab82);
			searchTag.setTagOnclick(this);
			updatePassword.setOnClickListener(this);
			other.setOnClickListener(this);
			userinfo.setOnClickListener(this);
			_loadLayout();
			_loadData();
		}

	}

	private void _loadLayout() {
		List<String> ListTag = new ArrayList<String>();
		ListTag.add("关于");
		ListTag.add("音乐");
		ListTag.add("通讯录");
		ListTag.add("源码");
		ListTag.add("博客");
		ListTag.add("注销");
		WindowManager windowManager = getActivity().getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenwidth = display.getWidth();
		searchTag.setTag(ListTag, screenwidth);

	}

	private void _loadData() {
		String url = ApiBgwAN.user();
		FinalHttp fp = Global.newInstance().buildHTTP();
		AjaxParams params = Global.newInstance().getParams();
		// FinalHttp fp = new FinalHttp();
		// AjaxParams params = new AjaxParams();
		params.put("cmd", "userinfo");
		params.put("username", username);
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
					super.onSuccess(ret);
					RespondModeObj mod = new RespondModeObj();
					Gson gson = new Gson();
					mod = gson.fromJson(ret, RespondModeObj.class);
					boolean isSuccess = mod.isSuccess();
					if (isSuccess) {
						JsonObject body = mod.getBody();
						ResponseUserMode rb = new ResponseUserMode();
						rb = gson.fromJson(body, ResponseUserMode.class);
						tvName.setText(rb.getUsername() == null ? "" : rb
								.getUsername());
						tvSex.setText(rb.getSex() == null ? "" : rb.getSex());
						tvSign.setScrollBarSize(ScrollingTextView.DRAWING_CACHE_QUALITY_AUTO);
						tvSign.setText(rb.getSign() == null ? "时间是让人触不及防的东西"
								: rb.getSign());

						if (rb.getUrl() != null) {
							FinalBitmap fb = FinalBitmap.create(mContext);
							fb.display(imageViewtrue, rb.getUrl());
						} else {
							imageViewtrue
									.setBackgroundResource(R.drawable.sun18);
						}
					} else {
						showToast(mod.getMsg());
					}
				}

			}
		});
	}

	@Override
	public void TagOnClick(int tagidx) {
		int status = tagidx + 1;
		// showToast((tagidx + 1) + "");
		if (status == 1) {
			Intent intent = new Intent(getActivity(), AboutActivity.class);
			startActivity(intent);

		} else if (status == 2) {
			Intent intent = new Intent(getActivity(), MainActivity.class);
			startActivity(intent);
		} else if (status == 3) {
			Intent intent = new Intent(getActivity(),
					com.bgw.an.app.activity.me.contact.MainActivity.class);
			startActivity(intent);
		} else if (status == 4) {
			String uri = "https://staryumou.taobao.com/shop/view_shop.htm?spm=a1z0e.1.0.0.mRAvqO&mytmenu=mdianpu&utkn=g,nvww243dn5tgszlmmq1408679659217&user_number_id=1678646712&scm=1028.1.1.20001";
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			startActivity(intent);
		} else if (status == 5) {
			String uri = "http://bgwan.blog.163.com/";
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			startActivity(intent);
		} else {
			AlertDialog.Builder normalDia = new AlertDialog.Builder(mContext);
			normalDia.setIcon(R.drawable.ic_launcher);
			normalDia.setTitle("提示");
			normalDia.setMessage("是否注销登陆");
			normalDia.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// System.exit(0);
							// SysApplication.getInstance().exit();
							Intent intent = new Intent(mContext,
									LoginActivity.class);
							startActivity(intent);
							getActivity().finish();
						}
					});
			normalDia.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			normalDia.create().show();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.other:
			intent = new Intent(mContext, BaiduMapAcitivity.class);
			startActivity(intent);
			break;
		case R.id.userinfo:
			intent = new Intent(mContext, UserInfoAcitivity.class);
			startActivity(intent);
			break;
		case R.id.updatePassword:
			intent = new Intent(mContext, UpdatePsAcitivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}
}
