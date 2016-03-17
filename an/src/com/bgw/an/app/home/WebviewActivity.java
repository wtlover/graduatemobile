package com.bgw.an.app.home;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bgw.an.R;
import com.bgw.an.app.comm.bar.NavigationBar;
import com.bgw.aw.base.BgwanActivity;
import com.bgw.aw.utils.FeatureUtils;
import com.bgw.aw.utils.MyListener;
import com.bgw.aw.utils.Pullable;
import com.bgw.aw.utils.impl.Macro_PullToRefreshLayout;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@SuppressLint("SetJavaScriptEnabled")
@ContentView(R.layout.home_webview)
public class WebviewActivity extends BgwanActivity implements Pullable {
	@ViewInject(R.id.refresh_view)
	private Macro_PullToRefreshLayout macro_pullToRefreshLayout;
	@ViewInject(R.id.content_view)
	private WebView webView;
	private String uri;
	private String title;
	@ViewInject(R.id.bar_navigation)
	private NavigationBar mBar;

	public void setUri(String uri, String title) {
		this.uri = uri;
		this.title = title;
	}

	@Override
	public void initView() {
		FeatureUtils.featureUtils(this, getWindow());
		// TODO Auto-generated method stub
		// getWindow().requestFeature(Window.FEATURE_PROGRESS);
		// final Activity activity = getParent();
		macro_pullToRefreshLayout.setOnRefreshListener(new MyListener());
		Bundle bundle = this.getIntent().getExtras();
		this.uri = bundle.getString("uri");
		this.title = bundle.getString("title");
		mBar.setTitleText("×ÊÁÏ£º" + title);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				// activity.setProgress(newProgress*1000);
			}
		});
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				showToast("Oh no!" + description);
			}
		});
		// webView.getSettings().setBuiltInZoomControls(false);
		// webView.getSettings().setBlockNetworkImage(true);
		// webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		// webView.getSettings().setAllowFileAccess(true);
		// webView.getSettings().setAppCacheEnabled(true);
		// webView.getSettings().setSaveFormData(false);
		// webView.getSettings().setLoadsImagesAutomatically(true);

		// webView.loadDataWithBaseURL(null,uri,null,null,null);

		webView.loadUrl(uri);
	}

	@Override
	public boolean canPullDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPullUp() {
		// TODO Auto-generated method stub
		return true;
	}

}
