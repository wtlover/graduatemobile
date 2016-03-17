package com.bgw.aw.utils.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bgw.aw.utils.Pullable;

public class Marco_PullToLinearlayout extends LinearLayout implements Pullable{

	public Marco_PullToLinearlayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public Marco_PullToLinearlayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
//		initView(context);
	}

	public Marco_PullToLinearlayout(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
//		initView(context);
	}

	@Override
	public boolean canPullDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPullUp() {
		// TODO Auto-generated method stub
		return false;
	}

}
