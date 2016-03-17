package com.bgw.an.app.activity.chat.view;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年11月17日 上午10:56:36 类说明 :
 */
public class ParallaxPagerAdapter extends FragmentPagerAdapter {
	private List<ParallaxFragment> fragments;

	public ParallaxPagerAdapter(FragmentManager fm,
			List<ParallaxFragment> fragments) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size() ;
	}

}
