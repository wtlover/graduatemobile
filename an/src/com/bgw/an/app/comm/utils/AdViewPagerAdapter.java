package com.bgw.an.app.comm.utils;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class AdViewPagerAdapter extends PagerAdapter{

	private List<AdProcessImageView> list;
	
	public AdViewPagerAdapter(List<AdProcessImageView> imagesList){
		this.list = imagesList;
	}
	
	@Override
	public Object instantiateItem(View container,int position){
		((ViewPager) container).addView(list.get(position));
		return list.get(position);
	}
	@Override
	public void destroyItem(View container,int position,Object object){
		((ViewPager)container).removeView(list.get(position));
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

}
