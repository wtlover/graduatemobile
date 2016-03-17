package com.bgw.aw.utils.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.bgw.aw.utils.Pullable;

public class PullableWebView extends WebView implements Pullable
{

	public PullableWebView(Context context)
	{
		super(context);
	}

	public PullableWebView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PullableWebView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown()
	{
		if (getScrollY() == 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canPullUp()
	{
		
		if (getScrollY() >= getContentHeight() * getScale()
				- getMeasuredHeight())
			return true;
		else
			return false;
	}
}
