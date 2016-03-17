package com.bgw.aw.base;

/** 
 * @author ���� staryumou@163.com: 
 * @version ����ʱ�䣺2015��10��2�� ����4:25:33 
 * ��˵�� : 
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
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

public abstract class BaseBgwanActivity extends BgwanActivity {
	protected static final String GlobalSharedName = "LocalUserInfo"; // SharedPreferences�ļ���
	protected static OnActiveChatActivityListenner activeChatActivityListenner = null; // ��������촰��

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
	 * ��Ļ�Ŀ�ȡ��߶ȡ��ܶ�
	 */
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mDensity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = BaseApplication.getInstance();
		mLoadingDialog = new FlippingLoadingDialog(this, "�����ύ��");
		mContext = getApplicationContext();

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		mDensity = metric.density;

//		ActivityCollectorUtils.addActivity(this);

		if (notificationMediaplayer == null) {
			notificationMediaplayer = new SoundPool(3,
					AudioManager.STREAM_SYSTEM, 5);
			notificationMediaplayerID = notificationMediaplayer.load(this,
					R.raw.crystalring, 1);
		}
		if (notificationVibrator == null) {
			notificationVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		clearAsyncTask();
//		ActivityCollectorUtils.removeActivity(this);

	}

	@Override
	public void finish() {
		super.finish();
	}

	/** ��ʼ����ͼ **/
	protected abstract void initViews();

	/** ��ʼ���¼� **/
	protected abstract void initEvents();

	protected void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
		mAsyncTasks.add(asyncTask.execute());
	}

	/** �����첽�����¼� */
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

	/** ���listener�������� **/
	protected void changeActiveChatActivity(
			OnActiveChatActivityListenner paramListener) {
		activeChatActivityListenner = paramListener;
	}

	/** ���������Ƴ���Ӧlistener **/
	protected void removeActiveChatActivity() {
		activeChatActivityListenner = null;
	}

	/**
	 * ��ѯ���ڴ򿪵����촰�ڵļ����¼�
	 * 
	 * @return
	 */
	public static OnActiveChatActivityListenner getActiveChatActivityListenner() {
		return activeChatActivityListenner;
	}

	/**
	 * �ж��Ƿ�������ڴ򿪵����촰��
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

	/** ������ʾToast��ʾ(����res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** ������ʾToast��ʾ(����String) **/
	protected void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/** ��ʱ����ʾToast��ʾ(����res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
	}

	/** ��ʱ����ʾToast��ʾ(����String) **/
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/** ��ʾ�Զ���Toast��ʾ(����res) **/
	protected void showCustomToast(int resId) {
		View toastRoot = LayoutInflater.from(BaseBgwanActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text))
				.setText(getString(resId));
		Toast toast = new Toast(BaseBgwanActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	/** ��ʾ�Զ���Toast��ʾ(����String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(BaseBgwanActivity.this).inflate(
				R.layout.common_toast, null);
		((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(BaseBgwanActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	/** ͨ��Class��ת���� **/
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/** ����Bundleͨ��Class��ת���� **/
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/** ͨ��Action��ת���� **/
	protected void startActivity(String action) {
		startActivity(action, null);
	}

	/** ����Bundleͨ��Action��ת���� **/
	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/** ���б�������ݵĶԻ��� **/
	protected AlertDialog showAlertDialog(String title, String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message).show();
		return alertDialog;
	}

	/** ���б��⡢���ݡ�������ť�ĶԻ��� **/
	protected AlertDialog showAlertDialog(String title, String message,
			String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}

	/** ���б��⡢���ݡ�ͼ�ꡢ������ť�ĶԻ��� **/
	protected AlertDialog showAlertDialog(String title, String message,
			int icon, String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message).setIcon(icon)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
		return alertDialog;
	}

	/**
	 * ��Ϣ����
	 * 
	 * <p>
	 * ���������Ҫ��д�˺���������ɸ��Ե�UI����
	 * 
	 * @param msg
	 *            ���յ�����Ϣ����
	 */
	public abstract void processMessage(android.os.Message msg);

	/**
	 * ����Ϣ���� - �����������ѡ�������
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
			MainTabActivity.sendEmptyMessage(); // ����Tab��Ϣ
			if (ActivityCollectorUtils.getActivitiesNum() > 0)
				ActivityCollectorUtils.getLastActivity().processMessage(msg);
			playNotification(); // ����Ϣ������

		}
	};
}
