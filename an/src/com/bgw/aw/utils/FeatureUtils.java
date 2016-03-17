package com.bgw.aw.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Window;

//直接使用这个作为android5.0以后的改进
public class FeatureUtils extends Activity{
	public static final void featureUtils(Context context,Window window){
		AndroidFeature af = new AndroidFeature(context,window);
		af.androidFeature();
	}

}
