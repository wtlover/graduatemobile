package com.bgw.an.app.home.fragment;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bgw.an.R;
import com.bgw.aw.base.BgwanFragment;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AiqiyiFragment extends BgwanFragment implements OnClickListener {
	@ViewInject(R.id.webview)
	private WebView webView;
	@ViewInject(R.id.back_btn)
	private Button back_btn;
	// 声明video，把之后的视频放到这里面去
	@ViewInject(R.id.video)
	private FrameLayout video;
	private CustomViewCallback customViewCallback;

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
		return R.layout.fragment_aiqiyi;
	}

	@Override
	public void init() {
		webView.setWebViewClient(new MyWebViewClient());
		back_btn.setOnClickListener(this);
		LoadUrl();

	}

	// 加载web
	@SuppressLint({ "SetJavaScriptEnabled", "InlinedApi", "NewApi" })
	private void LoadUrl() {
		// TODO Auto-generated method stubs
		// 设置WebView属性，能够执行Javascript脚本
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setWebChromeClient(new DefaultWebChromeClient()); // 播放视频
		webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.loadUrl("http://www.iqiyi.com/");
	}

	private class DefaultWebChromeClient extends WebChromeClient {
		// 一个回调接口使用的主机应用程序通知当前页面的自定义视图已被撤职

		// 进入全屏的时候
		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			// 赋值给callback
			customViewCallback = callback;
			// 设置webView隐藏
			webView.setVisibility(View.GONE);
			back_btn.setVisibility(View.VISIBLE);
			// 将video放到当前视图中
			video.addView(view);
			// 横屏显示
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			// 设置全屏
			setFullScreen();
		}

		// 退出全屏的时候
		@Override
		public void onHideCustomView() {
			if (customViewCallback != null) {
				// 隐藏掉
				customViewCallback.onCustomViewHidden();
			}
			// 用户当前的首选方向
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// 退出全屏
			quitFullScreen();
			// 设置WebView可见
			webView.setVisibility(View.VISIBLE);
			back_btn.setVisibility(View.GONE);
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
		}
	}

	/**
	 * 设置全屏
	 */
	private void setFullScreen() {
		// 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
		getActivity().getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 全屏下的状态码：1098974464
		// 窗口下的状态吗：1098973440
	}

	/**
	 * 退出全屏
	 */
	private void quitFullScreen() {
		// 声明当前屏幕状态的参数并获取
		final WindowManager.LayoutParams attrs = getActivity().getWindow()
				.getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActivity().getWindow().setAttributes(attrs);
		getActivity().getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	// 关联webview 类
	class MyWebViewClient extends WebViewClient {

		// 加载结束的时候
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
		}

	}

	// 手机返回键监听
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// 如果是全屏状态 按返回键则变成非全屏状态，否则执行返回操作
			if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				quitFullScreen();
			} else {
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					getActivity().finish();
				}
			}

			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		webView.onPause();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.back_btn:
			if (customViewCallback != null) {
				// 隐藏掉
				customViewCallback.onCustomViewHidden();
			}
			// 用户当前的首选方向
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// 退出全屏
			quitFullScreen();
			// 设置WebView可见
			webView.setVisibility(View.VISIBLE);
			// expend_headerV.setVisibility(View.VISIBLE);
			// back_btn_video.setVisibility(View.GONE);
			video.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

}
