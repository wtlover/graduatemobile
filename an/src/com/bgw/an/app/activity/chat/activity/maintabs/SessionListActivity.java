package com.bgw.an.app.activity.chat.activity.maintabs;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseApplication;
import com.bgw.an.app.activity.chat.dialog.FlippingLoadingDialog;
import com.bgw.an.app.activity.chat.util.ActivityCollectorUtils;
import com.bgw.an.app.activity.chat.view.HeaderLayout;
import com.bgw.an.app.activity.chat.view.HeaderLayout.HeaderStyle;

public class SessionListActivity extends TabItemActivity {

	private HeaderLayout mHeaderLayout;
	private SessionPeopleFragment mPeopleFragment;

	private static int notificationMediaplayerID;
	private static SoundPool notificationMediaplayer;
	private static Vibrator notificationVibrator;
	protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sessionlist);
		_initOnCreate();
		initViews();
		init();
	}

	private void _initOnCreate() {
		mApplication = BaseApplication.getInstance();
		mLoadingDialog = new FlippingLoadingDialog(this, "请求提交中");
		mContext = getApplicationContext();

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		mDensity = metric.density;

		ActivityCollectorUtils.addActivity(this);

		if (notificationMediaplayer == null) {
			notificationMediaplayer = new SoundPool(3,
					AudioManager.STREAM_SYSTEM, 5);
			notificationMediaplayerID = notificationMediaplayer.load(this,
					R.raw.crystalring, 1);
		}
		if (notificationVibrator == null) {
			notificationVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		MainTabActivity.sendEmptyMessage();
	}

	@Override
	public void onResume() {
		super.onResume();
		MainTabActivity.sendEmptyMessage();
		mPeopleFragment.refreshAdapter();
	}

	@Override
	protected void initViews() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.session_header);
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
		mHeaderLayout.setDefaultTitle("AW",
				null);
	}

	@Override
	protected void initEvents() {

	}

	@Override
	protected void init() {
		mPeopleFragment = new SessionPeopleFragment(mApplication, this, this);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.session_layout_content, mPeopleFragment).commit();
	}

	@Override
	public void processMessage(Message msg) {
		mPeopleFragment.refreshAdapter();
	}

}
