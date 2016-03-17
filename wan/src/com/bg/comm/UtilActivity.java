package com.bg.comm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Process;

/**
 * Activity²É¼¯Æ÷.
 * 
 * @author William Lee
 * @version 1.0 2014-10-21
 */
public class UtilActivity {

	private UtilActivity() {

	}

	/**
	 * 
	 */
	private static List<Activity> activities = new ArrayList<Activity>();

	/**
	 * @param activity
	 */
	public static void addActivity(Activity activity) {
		if (!activities.contains(activity)) {
			activities.add(activity);
		}
	}

	/**
	 * @param activity
	 */
	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	/**
	 * 
	 */
	public static void finishAll() {
		for (Activity activity : activities) {
			activity.finish();
		}
		Process.killProcess(Process.myPid());
	}
}
