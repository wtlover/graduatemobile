package com.bgw.aw.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseApplication;
import com.bgw.an.app.activity.chat.activity.maintabs.MainTabActivity;
import com.bgw.an.app.activity.chat.dialog.FlippingLoadingDialog;
import com.bgw.an.app.activity.chat.socket.udp.OnActiveChatActivityListenner;
import com.bgw.an.app.activity.chat.socket.udp.UDPSocketThread;
import com.bgw.an.app.activity.chat.util.ActivityCollectorUtils;
import com.bgw.an.app.activity.chat.util.WifiUtils;
import com.bgw.an.app.activity.chat.view.HandyTextView;

public abstract class BaseBgwanFragment extends BgwanFragment {
	protected static final String GlobalSharedName = "LocalUserInfo"; // SharedPreferences文件名
	protected static OnActiveChatActivityListenner activeChatActivityListenner = null; // 激活的聊天窗口

	protected BaseApplication mApplication;
	protected Context mContext;
	protected WifiUtils mWifiUtils;
	protected FlippingLoadingDialog mLoadingDialog;
	protected UDPSocketThread mUDPSocketThread;

	private static int notificationMediaplayerID;
	private static SoundPool notificationMediaplayer;
	private static Vibrator notificationVibrator;

	protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();

	/**
	 * 屏幕的宽度、高度、密度
	 */
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mDensity;

	/** 初始化视图 **/
	protected abstract void initViews();

	/** 初始化事件 **/
	protected abstract void initEvents();

	protected void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
		mAsyncTasks.add(asyncTask.execute());
	}

	/** 清理异步处理事件 */
	protected void clearAsyncTask() {
		Iterator<AsyncTask<Void, Void, Boolean>> iterator = mAsyncTasks
				.iterator();
		while (iterator.hasNext()) {
			AsyncTask<Void, Void, Boolean> asyncTask = iterator.next();
			if (asyncTask != null && !asyncTask.isCancelled()) {
				asyncTask.cancel(true);
			}
		}
		mAsyncTasks.clear();
	}

	/** 添加listener到容器中 **/
	protected void changeActiveChatActivity(
			OnActiveChatActivityListenner paramListener) {
		activeChatActivityListenner = paramListener;
	}

	/** 从容器中移除相应listener **/
	protected void removeActiveChatActivity() {
		activeChatActivityListenner = null;
	}

	/**
	 * 查询正在打开的聊天窗口的监听事件
	 * 
	 * @return
	 */
	public static OnActiveChatActivityListenner getActiveChatActivityListenner() {
		return activeChatActivityListenner;
	}

	/**
	 * 判断是否存在正在打开的聊天窗口
	 * 
	 * @return
	 */
	public static boolean isExistActiveChatActivity() {
		return (activeChatActivityListenner == null) ? false : true;
	}

	protected void showLoadingDialog(String text) {
		if (text != null) {
			mLoadingDialog.setText(text);
		}
		mLoadingDialog.show();
	}

	protected void dismissLoadingDialog() {
		if (mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
	}

	/** 短暂显示Toast提示(来自res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(mContext, getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast提示(来自String) **/
	protected void showShortToast(String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

	/** 长时间显示Toast提示(来自res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(mContext, getString(resId), Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongToast(String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
	}

	/** 显示自定义Toast提示(来自res) **/
	protected void showCustomToast(int resId) {
		View toastRoot = LayoutInflater.from(mContext).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text))
				.setText(getString(resId));
		Toast toast = new Toast(mContext);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(mContext).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(mContext);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	/** 通过Class跳转界面 **/
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/** 含有Bundle通过Class跳转界面 **/
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(mContext, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/** 通过Action跳转界面 **/
	protected void startActivity(String action) {
		startActivity(action, null);
	}

	/** 含有Bundle通过Action跳转界面 **/
	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/** 含有标题和内容的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(mContext)
				.setTitle(title).setMessage(message).show();
		return alertDialog;
	}

	/** 含有标题、内容、两个按钮的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message,
			String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(mContext)
				.setTitle(title).setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}

	/** 含有标题、内容、图标、两个按钮的对话框 **/
	protected AlertDialog showAlertDialog(String title, String message,
			int icon, String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(mContext)
				.setTitle(title).setMessage(message).setIcon(icon)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}

	/**
	 * 消息处理
	 * 
	 * <p>
	 * 相关子类需要重写此函数，以完成各自的UI更新
	 * 
	 * @param msg
	 *            接收到的消息对象
	 */
	public abstract void processMessage(android.os.Message msg);

	/**
	 * 新消息提醒 - 包括声音提醒、振动提醒
	 */
	public static void playNotification() {
		if (BaseApplication.getSoundFlag()) {
			notificationMediaplayer.play(notificationMediaplayerID, 1, 1, 0, 0,
					1);
		}
		if (BaseApplication.getVibrateFlag()) {
			notificationVibrator.vibrate(200);
		}

	}

	public static void sendEmptyMessage(int what) {
		handler.sendEmptyMessage(what);
	}

	public static void sendMessage(android.os.Message msg) {
		handler.sendMessage(msg);
	}

	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			MainTabActivity.sendEmptyMessage(); // 更新Tab信息
			if (ActivityCollectorUtils.getActivitiesNum() > 0)
				ActivityCollectorUtils.getLastActivity().processMessage(msg);
			playNotification(); // 新消息响提醒

		}
	};
}
