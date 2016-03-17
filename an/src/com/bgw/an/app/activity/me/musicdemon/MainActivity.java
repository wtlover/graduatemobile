package com.bgw.an.app.activity.me.musicdemon;

/*
 * author:Demon 大连东软信息学院
 * date:2015.06.10
 * project name:MusicDemon 简单音乐播放器
 * 重要说明：如果再此代码上修改请不要把图片移除，相当重要
 * */

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;

import com.bgw.an.R;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

/*		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);*/
		setContentView(R.layout.activity_main_music);
		if (null != MusicActivity.player && MusicActivity.player.isPlaying()) {
			Intent intent = new Intent(this, MusicActivity.class);
			intent.putExtra("id", MusicActivity._id);
			startActivity(intent);
		}
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec tabSpec;
		Intent intent;

		intent = new Intent().setClass(this, ItemActivity.class);
		tabSpec = tabHost.newTabSpec("音乐列表")
				.setIndicator("音乐列表", res.getDrawable(R.drawable.item))
				.setContent(intent);
		tabHost.addTab(tabSpec);

		intent = new Intent().setClass(this, RecentActivity.class);
		tabSpec = tabHost.newTabSpec("Recently")
				.setIndicator("最近播放", res.getDrawable(R.drawable.album))
				.setContent(intent);
		tabHost.addTab(tabSpec);

		tabHost.setCurrentTab(0);
	}
}
