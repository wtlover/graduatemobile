package com.bgw.an.app.activity.chat.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseActivity;
import com.bgw.an.app.activity.chat.BaseApplication;
import com.bgw.an.app.activity.chat.BaseDialog;
import com.bgw.an.app.activity.chat.activity.maintabs.MainTabActivity;
import com.bgw.an.app.activity.chat.activity.wifiap.WifiApConst;
import com.bgw.an.app.activity.chat.activity.wifiap.WifiapBroadcast;
import com.bgw.an.app.activity.chat.adapter.WifiapAdapter;
import com.bgw.an.app.activity.chat.dialog.FlippingLoadingDialog;
import com.bgw.an.app.activity.chat.entity.Users;
import com.bgw.an.app.activity.chat.socket.udp.UDPSocketThread;
import com.bgw.an.app.activity.chat.sql.SqlDBOperate;
import com.bgw.an.app.activity.chat.sql.UserInfo;
import com.bgw.an.app.activity.chat.util.ActivityCollectorUtils;
import com.bgw.an.app.activity.chat.util.DateUtils;
import com.bgw.an.app.activity.chat.util.LogUtils;
import com.bgw.an.app.activity.chat.util.SessionUtils;
import com.bgw.an.app.activity.chat.util.TextUtils;
import com.bgw.an.app.activity.chat.util.WifiUtils;
import com.bgw.an.app.activity.chat.view.HeaderLayout;
import com.bgw.an.app.activity.chat.view.HeaderLayout.HeaderStyle;
import com.bgw.an.app.activity.chat.view.HeaderLayout.onRightImageButtonClickListener;
import com.bgw.an.app.activity.chat.view.WifiSearchView;
import com.bgw.aw.utils.FeatureUtils;
import com.bgw.aw.utils.impl.DrawableView;

/**
 * TODO<>
 * 
 * @author 孙顺涛
 * @data: 2015年10月3日 下午3:37:50
 * @version: V1.0
 */
public class WifiapActivity extends BaseActivity implements OnClickListener,
		onRightImageButtonClickListener, WifiapBroadcast.EventHandler,
		DialogInterface.OnClickListener {

	private static final String TAG = "SZU_WifiapActivity";
	private int wifiapOperateEnum = WifiApConst.NOTHING;

	private String localIPaddress; // 本地WifiIP
	private String serverIPaddres; // 热点IP
	private String mDevice = getPhoneModel(); // 手机品牌型号
	private boolean isClient = true; // 客户端标识,默认为true
	private ArrayList<String> mWifiApList; // 符合条件的热点列表

	private WifiSearchView mWifisearchAnimation;
	private DrawableView drawableView;
	private HeaderLayout mHeaderLayout;
	private BaseDialog mDialog; // 提示窗口
	private Button mBtnBack;
	private Button mBtnLogin;
	private Button mBtnCreateAp;
	private ImageView mIvWifiApIcon;
	private LinearLayout mLlCreateAP;
	private ListView mLvWifiList;
	private TextView mTvWifiApInfo;
	private TextView mTvWifiApTips;

	private SearchApProcess mSearchApProcess;
	private WifiapAdapter mWifiApAdapter;
	private UserInfo mUserInfo; // 用户信息类实例
	private SqlDBOperate mSqlDBOperate;// 数据库操作实例
	private WifiapBroadcast mWifiapBroadcast;

	protected BaseApplication mApplication;
	protected Context mContext;
	protected WifiUtils mWifiUtils;
	protected FlippingLoadingDialog mLoadingDialog;
	protected UDPSocketThread mUDPSocketThread;

	protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FeatureUtils.featureUtils(this, getWindow());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifiap);
		initBroadcast(); // 注册广播
		mWifiUtils = WifiUtils.getInstance(getApplicationContext());
		_initOnCreate();
		initViews();
		initEvents();
		initAction();
	}

	private void _initOnCreate() {
		mApplication = BaseApplication.getInstance();
		mLoadingDialog = new FlippingLoadingDialog(this, "请求提交中");
		mContext = getApplicationContext();
		ActivityCollectorUtils.addActivity(this);

	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mWifiapBroadcast); // 撤销广播
		mWifiapBroadcast.removeehList(this);
		if (mSearchApProcess != null)
			mSearchApProcess.stop();
		super.onDestroy();
	}

	/** 动态注册广播 */
	public void initBroadcast() {
		mWifiapBroadcast = new WifiapBroadcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(mWifiapBroadcast, filter);
	}

	/** 初始化视图 获取控件对象 **/
	protected void initViews() {
		drawableView = (DrawableView) findViewById(R.id.drawableView);
		mHeaderLayout = (HeaderLayout) findViewById(R.id.wifiap_header);
		mBtnBack = (Button) findViewById(R.id.wifiap_btn_back);
		mBtnLogin = (Button) findViewById(R.id.wifiap_btn_login);

		mLlCreateAP = ((LinearLayout) findViewById(R.id.create_ap_llayout_wt_main));// 创建热点的view
		mTvWifiApInfo = ((TextView) findViewById(R.id.prompt_ap_text_wt_main));
		mBtnCreateAp = ((Button) findViewById(R.id.create_btn_wt_main)); // 创建热点的按钮
//		mWifisearchAnimation = ((WifiSearchView) findViewById(R.id.search_animation_wt_main));// 搜索时的动画
		drawableView.setVisibility(View.VISIBLE);
		mLvWifiList = ((ListView) findViewById(R.id.wt_list_wt_main));// 搜索到的热点
		mTvWifiApTips = (TextView) findViewById(R.id.wt_prompt_wt_main);
		mIvWifiApIcon = (ImageView) findViewById(R.id.radar_gif_wt_main);
	}

	/** 初始化全局设置 **/
	@Override
	protected void initEvents() {
	    mHeaderLayout.init(HeaderStyle.TITLE_RIGHT_IMAGEBUTTON);
		mHeaderLayout.setTitleRightImageButton("创建连接", "aw",
		R.drawable.search_wt, this);

		mWifiApList = new ArrayList<String>();
		mWifiApAdapter = new WifiapAdapter(this, mWifiApList);
		mLvWifiList.setAdapter(mWifiApAdapter);

		mWifiapBroadcast.addehList(this); // 监听广播

		mDialog = BaseDialog.getDialog(WifiapActivity.this,
				R.string.dialog_tips, "", "确 定", this, "取 消", this);
		mDialog.setButton1Background(R.drawable.btn_default_popsubmit);
//		mDialog.setButton1Background(R.anim.sst_superbuton_selector);

		mSearchApProcess = new SearchApProcess();

		mBtnCreateAp.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);
		mBtnLogin.setOnClickListener(this);
	}

	/** 初始化控件设置 **/
	protected void initAction() {
		if ((mSearchApProcess.running))
			return;

		if (!mWifiUtils.isWifiConnect() && !mWifiUtils.isWifiApEnabled()) { // 无开启热点无连接WIFI
			mWifiUtils.OpenWifi();
			mSearchApProcess.start();
			mWifiUtils.startScan();
//			mWifisearchAnimation.startAnimation();
			drawableView.setVisibility(View.VISIBLE);
			mLlCreateAP.setVisibility(View.GONE);
			mTvWifiApTips.setText(R.string.wifiap_text_searchap_searching);
			mTvWifiApTips.setVisibility(View.VISIBLE);
			mBtnCreateAp.setText("关闭热点");
		}
		if (mWifiUtils.isWifiConnect()) { // 已连接WIFI
			this.mWifiUtils.startScan();
			this.mSearchApProcess.start();
			drawableView.setVisibility(View.VISIBLE);
//			this.mWifisearchAnimation.startAnimation();
			this.mLlCreateAP.setVisibility(View.GONE);
			this.mTvWifiApTips.setText(R.string.wifiap_text_searchap_searching);
			this.mTvWifiApTips.setVisibility(View.VISIBLE);
			this.mBtnCreateAp.setText("创建热点");
			this.mIvWifiApIcon.setVisibility(View.GONE);
		}

		if (mWifiUtils.isWifiApEnabled()) { // 已开启热点
//			mWifisearchAnimation.stopAnimation();
			drawableView.setVisibility(View.GONE);
			if (mWifiUtils.getApSSID().startsWith(WifiApConst.WIFI_AP_HEADER)) {
				mTvWifiApTips.setVisibility(View.VISIBLE);
				mLlCreateAP.setVisibility(View.VISIBLE);
				mBtnCreateAp.setVisibility(View.VISIBLE);
				mIvWifiApIcon.setVisibility(View.VISIBLE);
				mBtnCreateAp.setText("关闭热点");
				mTvWifiApInfo
						.setText(getString(R.string.wifiap_text_createap_succeed)
								+ getString(R.string.wifiap_text_connectap_ssid)
								+ mWifiUtils.getApSSID());
				isClient = false;
			}
		}
	}

	/**
	 * 获取Wifi热点名
	 * 
	 * <p>
	 * BuildBRAND 系统定制商 ； BuildMODEL 版本
	 * </p>
	 * 
	 * @return 返回 定制商+版本 (String类型),用于创建热点。
	 */
	public String getLocalHostName() {
		String str1 = Build.BRAND;
		String str2 = TextUtils.getRandomNumStr(3);
		return str1 + "_" + str2;
	}

	public String getPhoneModel() {
		String str1 = Build.BRAND;
		String str2 = Build.MODEL;
		str2 = str1 + "_" + str2;
		return str2;
	}

	/**
	 * 刷新热点列表UI
	 * 
	 * @param list
	 */
	public void refreshAdapter(List<String> list) {
		mWifiApAdapter.setData(list);
		mWifiApAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置IP地址信息
	 * 
	 * @param isClient
	 *            是否为客户端
	 */
	public void setIPaddress(boolean isClient) {
		if (!isClient
				&& mWifiUtils.getApSSID()
						.startsWith(WifiApConst.WIFI_AP_HEADER)) {
			localIPaddress = mWifiUtils.getLocalIPAddress();
			serverIPaddres = localIPaddress;
			// serverIPaddres = localIPaddress = "192.168.43.1";
		} else {
			localIPaddress = mWifiUtils.getLocalIPAddress();
			serverIPaddres = mWifiUtils.getServerIPAddress();
		}
		LogUtils.d(TAG, "localIPaddress:" + localIPaddress + " serverIPaddres:"
				+ serverIPaddres);
	}

	/**
	 * IP地址正确性验证
	 * 
	 * @return boolean 返回是否为正确， 正确(true),不正确(false)
	 */
	private boolean isValidated() {

		setIPaddress(isClient);
		String nullIP = "0.0.0.0";

		if (nullIP.equals(localIPaddress) || nullIP.equals(serverIPaddres)
				|| localIPaddress == null || serverIPaddres == null) {
			showShortToast(R.string.wifiap_toast_connectap_unavailable);
			return false;
		}

		return true;
	}

	/** 执行登陆 **/
	private void doLogin() {
		if (!isValidated()) {
			return;
		}
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog(getString(R.string.wifiap_dialog_login_saveInfo));
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					mSqlDBOperate = new SqlDBOperate(mContext);
					String IMEI = SessionUtils.getIMEI();
					String nickname = SessionUtils.getNickname();
					String gender = SessionUtils.getGender();
					String constellation = SessionUtils.getConstellation();
					int age = SessionUtils.getAge();
					int avatar = SessionUtils.getAvatar();
					int onlineStateInt = SessionUtils.getOnlineStateInt();

					String logintime = DateUtils.getNowtime();

					// 录入数据库
					// 若数据库中有IMEI对应的用户记录，则更新此记录; 无则创建新用户
					if ((mUserInfo = mSqlDBOperate.getUserInfoByIMEI(IMEI)) != null) {
						mUserInfo.setIPAddr(localIPaddress);
						mUserInfo.setAvater(avatar);
						mUserInfo.setIsOnline(onlineStateInt);
						mUserInfo.setName(nickname);
						mUserInfo.setSex(gender);
						mUserInfo.setAge(age);
						mUserInfo.setDevice(mDevice);
						mUserInfo.setConstellation(constellation);
						mUserInfo.setLastDate(logintime);
						mSqlDBOperate.updateUserInfo(mUserInfo);
					} else {
						mUserInfo = new UserInfo(nickname, age, gender, IMEI,
								localIPaddress, onlineStateInt, avatar);
						mUserInfo.setLastDate(logintime);
						mUserInfo.setDevice(mDevice);
						mUserInfo.setConstellation(constellation);
						mSqlDBOperate.addUserInfo(mUserInfo);
					}

					int usserID = mSqlDBOperate.getIDByIMEI(IMEI); // 获取用户id
					// 设置用户Session
					SessionUtils.setLocalUserID(usserID);
					SessionUtils.setDevice(mDevice);
					SessionUtils.setIsClient(isClient);
					SessionUtils.setLocalIPaddress(localIPaddress);
					SessionUtils.setServerIPaddress(serverIPaddres);
					SessionUtils.setLoginTime(logintime);

					// 在SD卡中存储登陆信息
					SharedPreferences.Editor mEditor = getSharedPreferences(
							GlobalSharedName, Context.MODE_PRIVATE).edit();
					mEditor.putString(Users.IMEI, IMEI)
							.putString(Users.DEVICE, mDevice)
							.putString(Users.NICKNAME, nickname)
							.putString(Users.GENDER, gender)
							.putInt(Users.AVATAR, avatar)
							.putInt(Users.AGE, age)
							.putString(Users.BIRTHDAY,
									SessionUtils.getBirthday())
							.putInt(Users.ONLINESTATEINT, onlineStateInt)
							.putString(Users.CONSTELLATION, constellation)
							.putString(Users.LOGINTIME, logintime);
					mEditor.commit();

					// UDPThread
					mUDPSocketThread = UDPSocketThread.getInstance(
							mApplication, getApplicationContext());
					mUDPSocketThread.connectUDPSocket(); // 新建Socket线程
					mUDPSocketThread.notifyOnline(); // 发送上线广播

					return true;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (null != mSqlDBOperate) {
						mSqlDBOperate.close();
						mSqlDBOperate = null;
					}
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (result) {
					mContext = getApplicationContext();
					startActivity(MainTabActivity.class);
					finish();
				} else {
					showShortToast("操作失败,请检查网络是否正常。");
				}
			}
		});
	}

	/**
	 * 热点搜索线程类
	 * 
	 * <p>
	 * 线程启动后，热点搜索的结果将通过handler更新
	 * </p>
	 */
	class SearchApProcess implements Runnable {
		public boolean running = false;
		private long startTime = 0L;
		private Thread thread = null;

		SearchApProcess() {
		}

		public void run() {
			while (true) {
				if (!this.running)
					return;
				if (System.currentTimeMillis() - this.startTime >= 30000L) {
					Message msg = handler
							.obtainMessage(WifiApConst.ApSearchTimeOut);
					handler.sendMessage(msg);
				}
				try {
					Thread.sleep(10L);
				} catch (Exception localException) {
				}
			}
		}

		public void start() {
			try {
				this.thread = new Thread(this);
				this.running = true;
				this.startTime = System.currentTimeMillis();
				this.thread.start();
			} finally {
			}
		}

		public void stop() {
			try {
				this.running = false;
				this.thread = null;
				this.startTime = 0L;
			} finally {
			}
		}
	}

	/** 监听 热点搜索按钮 **/
	@Override
	public void onClick() {
		if (!mSearchApProcess.running) {// 如果搜索线程没有启动
			if (mWifiUtils.isWifiApEnabled()) {
				wifiapOperateEnum = WifiApConst.SEARCH;
				mDialog.setMessage(getString(R.string.wifiap_dialog_searchap_closeap_confirm));
				mDialog.show();
				return;
			}
			if (!mWifiUtils.mWifiManager.isWifiEnabled()) {// 如果wifi关闭着
				mWifiUtils.OpenWifi();
				mTvWifiApInfo.setVisibility(View.GONE);
			}
			mWifiApList.clear();
			refreshAdapter(mWifiApList);
			mTvWifiApTips.setVisibility(View.VISIBLE);
			mTvWifiApTips.setText(R.string.wifiap_text_searchap_searching);
			mLlCreateAP.setVisibility(View.GONE);
			mIvWifiApIcon.setVisibility(View.GONE);
			
			mBtnCreateAp.setText("创建热点");
			mWifiUtils.startScan();
			mSearchApProcess.start();
			drawableView.setVisibility(View.VISIBLE);
//			mWifisearchAnimation.startAnimation();
		} else {
			// 重新启动一下
			mSearchApProcess.stop();
			mWifiApList.clear();
			refreshAdapter(mWifiApList);
			mWifiUtils.startScan();
			mSearchApProcess.start();
		}
	}

	/** 监听 主体界面按钮 **/
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 创建热点
		case R.id.create_btn_wt_main:

			// 如果不支持热点创建
			if (mWifiUtils.getWifiApStateInt() == 4) {
				showShortToast(R.string.wifiap_dialog_createap_nonsupport);
				return;
			}

			// 如果wifi正打开着的，就提醒用户
			if (mWifiUtils.mWifiManager.isWifiEnabled()) {
				wifiapOperateEnum = WifiApConst.CREATE;
				mDialog.setMessage(getString(R.string.wifiap_dialog_createap_closewifi_confirm));
				mDialog.show();
				mTvWifiApInfo.setVisibility(View.VISIBLE);
				return;
			}

			// 如果已经存在一个其他的共享热点
			if (((mWifiUtils.getWifiApStateInt() == 3) || (mWifiUtils
					.getWifiApStateInt() == 13))
					&& (!mWifiUtils.getApSSID().startsWith(
							WifiApConst.WIFI_AP_HEADER))) {
				mTvWifiApInfo.setVisibility(View.VISIBLE);
				wifiapOperateEnum = WifiApConst.CREATE;
				mDialog.setMessage(getString(R.string.wifiap_dialog_createap_used));
				mDialog.show();
				return;
			}

			// 如果存在一个同名的共享热点
			if (((mWifiUtils.getWifiApStateInt() == 3) || (mWifiUtils
					.getWifiApStateInt() == 13))
					&& (mWifiUtils.getApSSID()
							.startsWith(WifiApConst.WIFI_AP_HEADER))) {
				wifiapOperateEnum = WifiApConst.CLOSE;
				mTvWifiApInfo.setVisibility(View.VISIBLE);
				mDialog.setMessage(getString(R.string.wifiap_dialog_closeap_confirm));
				mDialog.show();
				return;
			}

			// 如果正在搜索状态

			if (mSearchApProcess.running) {
				mSearchApProcess.stop();
				mTvWifiApInfo.setVisibility(View.VISIBLE);
				drawableView.setVisibility(View.GONE);
//				mWifisearchAnimation.stopAnimation();
			}
			mWifiUtils.closeWifi();
			wifiapOperateEnum = WifiApConst.CREATE;
			mDialog.setMessage(getString(R.string.wifiap_dialog_createap_closewifi_confirm));
			mDialog.show();
			return;

			// 返回按钮
		case R.id.wifiap_btn_back:
			WifiapActivity.this.finish();
			break;

		// 下一步按钮
		case R.id.wifiap_btn_login:
			if (mDialog.isShowing()) {
				mDialog.dismiss();
			}
			doLogin();
			break;

		}
	}

	/** 监听 提示窗口按钮 **/
	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {

		// 确定
		case 0:
			dialog.dismiss();
			switch (wifiapOperateEnum) {

			// 执行关闭热点事件
			case WifiApConst.CLOSE:
				mWifiUtils.closeWifiAp();
				mTvWifiApTips.setVisibility(View.VISIBLE);
				mTvWifiApTips.setText("");
				mLlCreateAP.setVisibility(View.GONE);
				mBtnCreateAp.setText("创建热点");
				mIvWifiApIcon.setVisibility(View.GONE);

				mTvWifiApInfo.setVisibility(View.GONE);
				localIPaddress = null;
				serverIPaddres = null;
				isClient = true;

				mWifiUtils.OpenWifi();
				mSearchApProcess.start();
				mWifiUtils.startScan();
				drawableView.setVisibility(View.VISIBLE);
//				mWifisearchAnimation.startAnimation();

				mTvWifiApTips.setVisibility(View.VISIBLE);
				mTvWifiApTips.setText(R.string.wifiap_text_searchap_searching);
				mLlCreateAP.setVisibility(View.GONE);
				mBtnCreateAp.setText("创建热点");
				break;

			// 创建热点
			case WifiApConst.CREATE:
				if (mSearchApProcess.running) {
					mSearchApProcess.stop();
					mTvWifiApInfo.setVisibility(View.VISIBLE);
					drawableView.setVisibility(View.GONE);
//					mWifisearchAnimation.stopAnimation();
				}
				mWifiUtils.startWifiAp(WifiApConst.WIFI_AP_HEADER
						+ getLocalHostName(), WifiApConst.WIFI_AP_PASSWORD,
						handler);
				mWifiApList.clear();
				refreshAdapter(mWifiApList);
				mLlCreateAP.setVisibility(View.VISIBLE);
				mBtnCreateAp.setVisibility(View.GONE);
				mTvWifiApTips.setVisibility(View.GONE);
				mTvWifiApInfo
						.setText(getString(R.string.wifiap_text_createap_creating));
				break;

			// 搜索wifi
			case WifiApConst.SEARCH:
				mTvWifiApInfo.setVisibility(View.VISIBLE);
				mTvWifiApTips.setVisibility(View.VISIBLE);
				mTvWifiApTips.setText(R.string.wifiap_text_searchap_searching);
				mLlCreateAP.setVisibility(View.GONE);
				mBtnCreateAp.setVisibility(View.VISIBLE);
				mBtnCreateAp.setText("创建热点");
				mIvWifiApIcon.setVisibility(View.GONE);

				mWifiUtils.closeWifiAp();
				mWifiUtils.OpenWifi();
				mSearchApProcess.start();
				drawableView.setVisibility(View.VISIBLE);
//				mWifisearchAnimation.startAnimation();
				break;
			}
			break;

		// 取消
		case 1:
			dialog.cancel();
			break;
		}
	}

	/** handler 异步更新UI **/
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 搜索超时
			case WifiApConst.ApSearchTimeOut:
				mSearchApProcess.stop();
				drawableView.setVisibility(View.GONE);
//				mWifisearchAnimation.stopAnimation();
				if (mWifiApList.isEmpty()) {
					mTvWifiApTips.setVisibility(View.VISIBLE);
					// mTvWifiApTips.setText(R.string.wifiap_text_searchap_empty);
					
					WifiUtils utils = WifiUtils.getInstance(getApplication());
					if (utils.isWifiConnect()) {
						String bgwan = "接入点：" + utils.getSSID() + "\nIP地址："
								+ utils.getServerIPAddress();
						mTvWifiApTips.setText("提示：Wifi已连接\n" + bgwan);
					}

				} else {
					mTvWifiApTips.setVisibility(View.GONE);
				}
				break;
			// 搜索结果
			case WifiApConst.ApScanResult:
				mTvWifiApInfo.setVisibility(View.GONE);
				WifiUtils utils = WifiUtils.getInstance(getApplication());
				int size = mWifiUtils.mWifiManager.getScanResults().size();
				if (size > 0) {
					for (int i = 0; i < size; ++i) {
						String apSSID = mWifiUtils.mWifiManager
								.getScanResults().get(i).SSID;
						if (apSSID.startsWith(WifiApConst.WIFI_AP_HEADER)
								&& !mWifiApList.contains(apSSID)) {
							mWifiApList.add(apSSID);
							refreshAdapter(mWifiApList);
						}
					}
				}
				break;
			// 连接成功
			case WifiApConst.ApConnectResult:
				mWifiUtils.setNewWifiManagerInfo();

				mTvWifiApInfo.setVisibility(View.GONE);
				if (mWifiUtils.getSSID().startsWith(WifiApConst.WIFI_AP_HEADER)) {
					mSearchApProcess.stop();
					drawableView.setVisibility(View.GONE);
//					mWifisearchAnimation.stopAnimation();
					mTvWifiApTips.setVisibility(View.GONE);
					refreshAdapter(mWifiApList);
					isClient = true;
				}

				break;

			// 热点创建结果
			case WifiApConst.ApCreateAPResult:
				if (mWifiUtils.isWifiApEnabled()
						&& mWifiUtils.getApSSID().startsWith(
								WifiApConst.WIFI_AP_HEADER)) {
					mTvWifiApTips.setVisibility(View.GONE);
					mTvWifiApInfo.setVisibility(View.VISIBLE);
					mLlCreateAP.setVisibility(View.VISIBLE);
					mBtnCreateAp.setVisibility(View.VISIBLE);
					mIvWifiApIcon.setVisibility(View.VISIBLE);
					mBtnCreateAp.setText("关闭热点");
					mTvWifiApInfo
							.setText(getString(R.string.wifiap_text_createap_succeed)
									+ getString(R.string.wifiap_text_connectap_ssid)
									+ mWifiUtils.getApSSID());
					isClient = false;
				} else {
					mTvWifiApInfo.setVisibility(View.GONE);
					mBtnCreateAp.setVisibility(View.VISIBLE);
					mBtnCreateAp.setText("创建热点");
					mTvWifiApInfo.setText(R.string.wifiap_text_createap_failue);
				}
				break;
			case WifiApConst.ApConnectting:
				mSearchApProcess.stop();
				mTvWifiApInfo.setVisibility(View.VISIBLE);
				drawableView.setVisibility(View.GONE);
//				mWifisearchAnimation.stopAnimation();
				mTvWifiApTips.setVisibility(View.GONE);
				break;
			case WifiApConst.ApConnected:
				refreshAdapter(mWifiApList);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void handleConnectChange() {
		Message msg = handler.obtainMessage(WifiApConst.ApConnectResult);
		handler.sendMessage(msg);
	}

	@Override
	public void scanResultsAvailable() {

		Message msg = handler.obtainMessage(WifiApConst.ApScanResult);
		handler.sendMessage(msg);
	}

	@Override
	public void wifiStatusNotification() {
	}

	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub
	}

}
