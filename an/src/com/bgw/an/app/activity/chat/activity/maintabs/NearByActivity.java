package com.bgw.an.app.activity.chat.activity.maintabs;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseApplication;
import com.bgw.an.app.activity.chat.view.HeaderLayout;
import com.bgw.an.app.activity.chat.view.HeaderLayout.HeaderStyle;
import com.bgw.an.app.activity.chat.view.HeaderLayout.SearchState;
import com.bgw.aw.utils.FeatureUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NearByActivity extends TabItemActivity {

	private HeaderLayout mHeaderLayout;
	private NearByPeopleFragment mPeopleFragment;
	@ViewInject(R.id.relativeLayout1)
	private RelativeLayout relativieLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FeatureUtils.featureUtils(this, getWindow());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby);
		initViews();
		initEvents();
		init();
	}

	@Override
	protected void initViews() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.nearby_header);
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
		mHeaderLayout.setDefaultTitle("AW", null);
		// relativieLayout.setBackgroundResource(R.anim.linearlayout01s);
	}

	@Override
	protected void initEvents() {

	}

	@Override
	protected void init() {
		mApplication = BaseApplication.getInstance();
		mPeopleFragment = new NearByPeopleFragment(mApplication,
				NearByActivity.this, this);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.nearby_layout_content, mPeopleFragment).commit();
	}

	@Override
	public void processMessage(android.os.Message msg) {
		mPeopleFragment.refreshAdapter();
	}

	@Override
	public void onBackPressed() {
		if (mHeaderLayout.searchIsShowing()) {
			clearAsyncTask();
			mHeaderLayout.dismissSearch();
			mHeaderLayout.clearSearch();
			mHeaderLayout.changeSearchState(SearchState.INPUT);
		} else {
			super.onBackPressed();
		}
	}

}
