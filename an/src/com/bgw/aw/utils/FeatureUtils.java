package com.bgw.aw.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Window;

//ֱ��ʹ�������Ϊandroid5.0�Ժ�ĸĽ�
public class FeatureUtils extends Activity{
	public static final void featureUtils(Context context,Window window){
		AndroidFeature af = new AndroidFeature(context,window);
		af.androidFeature();
	}

}
