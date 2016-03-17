package com.bgw.an.app.activity.chat.activity.maintabs;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseApplication;
import com.bgw.an.app.activity.chat.view.HandyTextView;
import com.bgw.aw.utils.FeatureUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * TODO<>
 * 
 * @author 孙顺涛
 * @data: 2015年10月5日 下午4:40:21
 * @version: V1.0
 */
@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity implements OnTabChangeListener {
	protected static boolean isTabActive;
	private TabHost mTabHost;
	private static String state1 = "com.bgw.an.app.activity.chat.activity.maintabs.NearByActivity";
	private static String state2 = "com.bgw.an.app.activity.chat.activity.maintabs.SessionListActivity";
	private static String state3 = "com.bgw.an.app.activity.chat.activity.maintabs.SettingActivity";

	@ViewInject(R.id.tab_chat_number)
	private static HandyTextView mHtvSessionNumber;
	@ViewInject(android.R.id.tabcontent)
	private FrameLayout frameLayout;
	@ViewInject(R.id.mainTabRelativeLayout)
	private RelativeLayout mainTabRelativeLayout;

	private RelativeLayout relativieLayout1;
	private RelativeLayout relativieLayout2;
	private RelativeLayout relativieLayout3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FeatureUtils.featureUtils(this, getWindow());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintabs);
		mainTabRelativeLayout = (RelativeLayout) findViewById(R.id.mainTabRelativeLayout);
		mainTabRelativeLayout.setBackgroundColor(0xff42ab82);
		initTabs();
		initViews();
		initEvents();
	}

	@Override
	public void onResume() {
		super.onResume();
		isTabActive = true;
	}

	@Override
	public void onPause() {
		super.onPause();
		isTabActive = false;
	}

	private void initTabs() {
		mTabHost = getTabHost(); // 从TabActivity上面获取放置Tab的TabHost
		LayoutInflater inflater = LayoutInflater.from(MainTabActivity.this);

		// 附近
		// common_bottombar_tab_nearby存放该Tab布局，inflate可将xml实例化成View
		View nearbyView = inflater.inflate(
				R.layout.common_bottombar_tab_nearby, null);
		View sessionListView = inflater.inflate(
				R.layout.common_bottombar_tab_chat, null);
		View userSettingView = inflater.inflate(
				R.layout.common_bottombar_tab_profile, null);

		relativieLayout1 = (RelativeLayout) nearbyView
				.findViewById(R.id.relativeLayout1);
		relativieLayout2 = (RelativeLayout) sessionListView
				.findViewById(R.id.relativeLayout2);
		relativieLayout3 = (RelativeLayout) userSettingView
				.findViewById(R.id.relativeLayout3);

		mTabHost.setOnTabChangedListener(this);

		// 创建TabHost.TabSpec的对象，并设置该对象的tag，最后关联该Tab的View
		TabHost.TabSpec nearbyTabSpec = mTabHost.newTabSpec(
				NearByActivity.class.getName()).setIndicator(nearbyView);
		nearbyTabSpec.setContent(new Intent(MainTabActivity.this, // 跳转activity
				NearByActivity.class));
		mTabHost.addTab(nearbyTabSpec); // 添加该Tab

		// 消息
		TabHost.TabSpec sessionListTabSpec = mTabHost.newTabSpec(
				SessionListActivity.class.getName()).setIndicator(
				sessionListView);
		sessionListTabSpec.setContent(new Intent(MainTabActivity.this,
				SessionListActivity.class));
		mTabHost.addTab(sessionListTabSpec);

		// 设置

		TabHost.TabSpec userSettingTabSpec = mTabHost.newTabSpec(
				SettingActivity.class.getName()).setIndicator(userSettingView);
		userSettingTabSpec.setContent(new Intent(MainTabActivity.this,
				SettingActivity.class));
		mTabHost.addTab(userSettingTabSpec);
	}

	private void initEvents() {
	}

	private void initViews() {
		mHtvSessionNumber = (HandyTextView) findViewById(R.id.tab_chat_number);
	}

	public static boolean getIsTabActive() {
		return isTabActive;
	}

	public static void sendEmptyMessage() {
		if (isTabActive)
			handler.sendEmptyMessage(0);
	}

	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int unReadPeopleSize = BaseApplication.getInstance()
					.getUnReadPeopleSize();
			switch (unReadPeopleSize) { // 判断人数作不同处理
			case 0: // 为0，隐藏数字提示
				mHtvSessionNumber.setVisibility(View.GONE);
				break;

			default: // 不为0，则显示未读数
				mHtvSessionNumber.setText(String.valueOf(unReadPeopleSize));
				mHtvSessionNumber.setVisibility(View.VISIBLE);
				break;
			}
		}
	};

	@Override
	public void onTabChanged(String tabId) {
		if (getState(tabId) == 1) {
			relativieLayout1.setBackgroundResource(R.anim.linearlayout01s);
			relativieLayout2.setBackgroundResource(R.anim.linearlayout01);
			relativieLayout3.setBackgroundResource(R.anim.linearlayout01);

		} else if (getState(tabId) == 2) {
			relativieLayout1.setBackgroundResource(R.anim.linearlayout01);
			relativieLayout2.setBackgroundResource(R.anim.linearlayout01s);
			relativieLayout3.setBackgroundResource(R.anim.linearlayout01);
		} else if (getState(tabId) == 3) {
			relativieLayout1.setBackgroundResource(R.anim.linearlayout01);
			relativieLayout2.setBackgroundResource(R.anim.linearlayout01);
			relativieLayout3.setBackgroundResource(R.anim.linearlayout01s);
		} else {
			Toast.makeText(getApplication(), "测试" + 0, Toast.LENGTH_LONG)
					.show();
		}

	}

	public int getState(String tabId) {
		if (state1.equals(tabId)) {
			return 1;
		} else if (state2.equals(tabId)) {
			return 2;
		} else if (state3.equals(tabId)) {
			return 3;
		} else {
			return 0;
		}
	}
}
