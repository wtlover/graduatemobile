package com.bgw.an;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bgw.an.app.home.WebviewActivity;
import com.bgw.an.app.home.adapter.FragmentAdapter;
import com.bgw.an.app.home.fragment.AiqiyiFragment;
import com.bgw.an.app.home.fragment.CharFragment;
import com.bgw.an.app.home.fragment.HomeFragment;
import com.bgw.an.app.home.fragment.HomeFragment.OnExpandableItemSelectedListener;
import com.bgw.an.app.home.fragment.MeFragment;
import com.bgw.aw.base.BgwanActivity;
import com.bgw.aw.utils.FeatureUtils;
import com.bgw.aw.utils.MyListener;
import com.bgw.aw.utils.impl.Macro_PullToRefreshLayout;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BgwanActivity implements
		OnExpandableItemSelectedListener {
	@ViewInject(R.id.refresh_view)
	private Macro_PullToRefreshLayout macro_pullToRefreshLayout;

	private LinearLayout[] linearLayouts;
	private TextView[] buttons;

	@ViewInject(R.id.viewPager)
	private ViewPager viewPagers;

	@Override
	public void initView() {
		FeatureUtils.featureUtils(this, getWindow());
		setlinearLayouts();
		settextview();
		viewPagerListenr();
		// 自定框架（刷新）
		macro_pullToRefreshLayout.setOnRefreshListener(new MyListener());

	}

	private void viewPagerListenr() {
		// TODO Auto-generated method stub

		List<Fragment> totalFragment = new ArrayList<Fragment>();
		// 把页面添加到ViewPager里
		totalFragment.add(new HomeFragment());
		totalFragment.add(new CharFragment());
		totalFragment.add(new AiqiyiFragment());
		totalFragment.add(new MeFragment());

		viewPagers.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
				totalFragment));
		// 设置显示哪页
		viewPagers.setCurrentItem(0);

		viewPagers.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				resetlaybg();
				linearLayouts[arg0]
						.setBackgroundResource(R.anim.linearlayout01s);
				buttons[arg0].setTextColor(getResources().getColor(
						R.color.textcolor));

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/** 初始化linerlayout */
	public void setlinearLayouts() {

		linearLayouts = new LinearLayout[4];
		linearLayouts[0] = (LinearLayout) findViewById(R.id.lay1);
		linearLayouts[1] = (LinearLayout) findViewById(R.id.lay2);
		linearLayouts[2] = (LinearLayout) findViewById(R.id.lay3);
		linearLayouts[3] = (LinearLayout) findViewById(R.id.lay4);
		linearLayouts[0].setBackgroundResource(R.anim.linearlayout01s);

	}

	/** 初始化textview */
	public void settextview() {
		buttons = new TextView[4];
		buttons[0] = (TextView) findViewById(R.id.fratext1);
		buttons[1] = (TextView) findViewById(R.id.fratext2);
		buttons[2] = (TextView) findViewById(R.id.fratext3);
		buttons[3] = (TextView) findViewById(R.id.fratext4);
		buttons[1].setTextColor(getResources().getColor(R.color.textcolor));
	}

	/** 点击linerlayout实现切换fragment的效果 */
	public void LayoutOnclick(View v) {
		// 每次点击都重置linearLayouts的背景、textViews字体颜色
		switch (v.getId()) {
		case R.id.lay1:
			resetlaybg();
			viewPagers.setCurrentItem(0);
			linearLayouts[0].setBackgroundResource(R.anim.linearlayout01s);
			buttons[0].setTextColor(getResources().getColor(R.color.textcolor));
			break;

		case R.id.lay2:
			resetlaybg();
			viewPagers.setCurrentItem(1);
			linearLayouts[1].setBackgroundResource(R.anim.linearlayout01s);
			buttons[1].setTextColor(getResources().getColor(R.color.textcolor));

			break;
		case R.id.lay3:
			resetlaybg();
			viewPagers.setCurrentItem(2);
			linearLayouts[2].setBackgroundResource(R.anim.linearlayout01s);
			buttons[2].setTextColor(getResources().getColor(R.color.textcolor));

			break;
		case R.id.lay4:
			resetlaybg();
			viewPagers.setCurrentItem(3);
			linearLayouts[3].setBackgroundResource(R.anim.linearlayout01s);
			buttons[3].setTextColor(getResources().getColor(R.color.textcolor));

			break;

		}
	}

	/** 重置linearLayouts、textViews */
	public void resetlaybg() {
		for (int i = 0; i < 4; i++) {
			buttons[i].setTextColor(getResources().getColor(R.color.white));
			linearLayouts[i].setBackgroundResource(R.anim.linearlayout01);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(Menu.NONE, Menu.FIRST, 1, "快速登录");
		menu.add(Menu.NONE, Menu.FIRST, 2, "分享");
		menu.add(Menu.NONE, Menu.FIRST, 3, "设置");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	@Override
	public void onExpandableItemSelected(String uri, String title) {
		// TODO Auto-generated method stub
		System.out.println("MainActivity URI:" + uri);
		Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("uri", uri);
		bundle.putString("title", title);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		AlertDialog.Builder normalDia = new AlertDialog.Builder(this);
		normalDia.setIcon(R.drawable.ic_launcher);
		normalDia.setTitle("提示");
		normalDia.setMessage("是否退出程序");
		normalDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.this.finish();
						// System.exit(0);
						// SysApplication.getInstance().exit();

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
