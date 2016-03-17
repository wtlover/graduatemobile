package com.bgw.aw.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.Window;
import android.view.WindowManager;

public class AndroidFeature extends Activity {
	
	Context mContext ;
	Window mwindow;

	public AndroidFeature(Context context,Window window) {
		super();
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mwindow = window;
	}

	public void androidFeature() {
		onCreate(null);
	}

	@SuppressLint("InlinedApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			mwindow.addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			mwindow.addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

}
