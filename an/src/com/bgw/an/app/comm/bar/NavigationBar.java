package com.bgw.an.app.comm.bar;

import android.content.Context;
import android.util.AttributeSet;

import com.awf.view.AimActionBar;

public class NavigationBar extends AimActionBar {

	public NavigationBar(Context context) {
		super(context);
		_init();
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		_init();
	}

	public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		_init();
	}

	private void _init() {
		setBackgroundColor(0xff42ab82);
		getmTitleTextView().setTextColor(0xffffffff);
	}

}
