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
	// ����video����֮�����Ƶ�ŵ�������ȥ
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

	// ����web
	@SuppressLint({ "SetJavaScriptEnabled", "InlinedApi", "NewApi" })
	private void LoadUrl() {
		// TODO Auto-generated method stubs
		// ����WebView���ԣ��ܹ�ִ��Javascript�ű�
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setWebChromeClient(new DefaultWebChromeClient()); // ������Ƶ
		webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.loadUrl("http://www.iqiyi.com/");
	}

	private class DefaultWebChromeClient extends WebChromeClient {
		// һ���ص��ӿ�ʹ�õ�����Ӧ�ó���֪ͨ��ǰҳ����Զ�����ͼ�ѱ���ְ

		// ����ȫ����ʱ��
		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			// ��ֵ��callback
			customViewCallback = callback;
			// ����webView����
			webView.setVisibility(View.GONE);
			back_btn.setVisibility(View.VISIBLE);
			// ��video�ŵ���ǰ��ͼ��
			video.addView(view);
			// ������ʾ
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			// ����ȫ��
			setFullScreen();
		}

		// �˳�ȫ����ʱ��
		@Override
		public void onHideCustomView() {
			if (customViewCallback != null) {
				// ���ص�
				customViewCallback.onCustomViewHidden();
			}
			// �û���ǰ����ѡ����
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// �˳�ȫ��
			quitFullScreen();
			// ����WebView�ɼ�
			webView.setVisibility(View.VISIBLE);
			back_btn.setVisibility(View.GONE);
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
		}
	}

	/**
	 * ����ȫ��
	 */
	private void setFullScreen() {
		// ����ȫ����������ԣ���ȡ��ǰ����Ļ״̬��Ȼ������ȫ��
		getActivity().getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ȫ���µ�״̬�룺1098974464
		// �����µ�״̬��1098973440
	}

	/**
	 * �˳�ȫ��
	 */
	private void quitFullScreen() {
		// ������ǰ��Ļ״̬�Ĳ�������ȡ
		final WindowManager.LayoutParams attrs = getActivity().getWindow()
				.getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActivity().getWindow().setAttributes(attrs);
		getActivity().getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	// ����webview ��
	class MyWebViewClient extends WebViewClient {

		// ���ؽ�����ʱ��
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
		}

	}

	// �ֻ����ؼ�����
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// �����ȫ��״̬ �����ؼ����ɷ�ȫ��״̬������ִ�з��ز���
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
				// ���ص�
				customViewCallback.onCustomViewHidden();
			}
			// �û���ǰ����ѡ����
			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// �˳�ȫ��
			quitFullScreen();
			// ����WebView�ɼ�
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
