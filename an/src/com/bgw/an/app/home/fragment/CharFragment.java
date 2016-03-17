package com.bgw.an.app.home.fragment;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseApplication;
import com.bgw.an.app.activity.chat.activity.WifiapActivity;
import com.bgw.an.app.activity.chat.adapter.SimpleListDialogAdapter;
import com.bgw.an.app.activity.chat.dialog.FlippingLoadingDialog;
import com.bgw.an.app.activity.chat.dialog.SimpleListDialog;
import com.bgw.an.app.activity.chat.dialog.SimpleListDialog.onSimpleListItemClickListener;
import com.bgw.an.app.activity.chat.entity.Users;
import com.bgw.an.app.activity.chat.util.DateUtils;
import com.bgw.an.app.activity.chat.util.ImageUtils;
import com.bgw.an.app.activity.chat.util.SessionUtils;
import com.bgw.an.app.activity.chat.util.TextUtils;
import com.bgw.an.app.activity.chat.view.HandyTextView;
import com.bgw.an.app.activity.chat.view.HeaderLayout;
import com.bgw.an.app.activity.chat.view.PagerScrollView;
import com.bgw.aw.base.BaseBgwanFragment;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @fileName CharFragment.java
 * @description 用户登陆类
 */
public class CharFragment extends BaseBgwanFragment implements OnClickListener,
		onSimpleListItemClickListener, OnDateChangedListener {
	// 登陆年龄限制
	private static final int MAX_AGE = 80;
	private static final int MIN_AGE = 12;
	private static final String DEFAULT_DATA = "19600101";

	@ViewInject(R.id.login_header)
	private HeaderLayout mHeaderLayout;
	@ViewInject(R.id.login_linearlayout_main)
	private PagerScrollView mLlayoutMain; // 首次登陆主界面
	@ViewInject(R.id.login_htv_onlinestate)
	private HandyTextView mHtvSelectOnlineState;
	@ViewInject(R.id.login_et_nickname)
	private EditText mEtNickname;

	@ViewInject(R.id.login_birthday_htv_constellation)
	private HandyTextView mHtvConstellation;
	@ViewInject(R.id.login_birthday_htv_age)
	private HandyTextView mHtvAge;
	@ViewInject(R.id.login_birthday_dp_birthday)
	private DatePicker mDpBirthday;
	private Calendar mCalendar;
	private Date mMinDate;
	private Date mMaxDate;
	private Date mSelectDate;

	@ViewInject(R.id.login_linearlayout_existmain)
	private LinearLayout mLlayoutExMain; // 二次登陆页面
	@ViewInject(R.id.llinearlayoutbgcolor)
	private LinearLayout llinearlayoutbgcolor; // 二次登陆页面
	@ViewInject(R.id.login_img_existImg)
	private ImageView mImgExAvatar;
	@ViewInject(R.id.login_tv_existName)
	private TextView mTvExNickmame;
	@ViewInject(R.id.login_layout_gender)
	private LinearLayout mLayoutExGender; // 性别根布局
	@ViewInject(R.id.login_iv_gender)
	private ImageView mIvExGender;
	@ViewInject(R.id.login_htv_age)
	private HandyTextView mHtvExAge;
	@ViewInject(R.id.login_tv_constellation)
	private TextView mTvExConstellation;// 星座
	@ViewInject(R.id.login_tv_lastlogintime)
	private TextView mTvExLogintime; // 上次登录时间

	@ViewInject(R.id.login_btn_next)
	private Button mBtnNext;
	@ViewInject(R.id.login_btn_changeUser)
	private Button mBtnChangeUser;
	@ViewInject(R.id.login_baseinfo_rg_gender)
	private RadioGroup mRgGender;
	private TelephonyManager mTelephonyManager;
	private SimpleListDialog mSimpleListDialog;

	private int mAge;
	private int mAvatar;
	private String mBirthday;
	private String mGender;
	private String mIMEI;
	private String mConstellation; // 星座
	private String mLastLogintime; // 上次登录时间
	private String mNickname = "";
	private String mOnlineStateStr = "在线"; // 默认登录状态
	private int mOnlineStateInt = 0; // 默认登录状态编号
	private String[] mOnlineStateType;

	@Override
	public RequestParams onParams(int paramFlag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSuccess(String result, int callbackflag) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initViews() {

		// showToast("首次使用该功能还是先注册吧");
		/*
		 * // LayoutInflater inflater = getActivity().getLayoutInflater(); //
		 * LayoutInflater inflater = LayoutInflater.from(getActivity()); String
		 * inflaterContext = Context.LAYOUT_INFLATER_SERVICE;
		 * 
		 * LayoutInflater inflater = (LayoutInflater) getActivity()
		 * .getSystemService(inflaterContext); View view =
		 * inflater.inflate(R.layout.fragment_char, null);
		 */
		mLlayoutMain.setBackgroundColor(0xff42ab82);
		llinearlayoutbgcolor.setBackgroundColor(0xff42ab82);
		SharedPreferences mSharedPreferences = getActivity()
				.getSharedPreferences(GlobalSharedName, Context.MODE_PRIVATE);
		mNickname = mSharedPreferences.getString(Users.NICKNAME, "");

		// 若mNickname有内容，则读取本地存储的用户信息
		if (mNickname.length() != 0) {
			mLlayoutMain.setVisibility(View.GONE);
			mLlayoutExMain.setBackgroundColor(0xff42ab82);
			mLlayoutExMain.setVisibility(View.VISIBLE);

			mAvatar = mSharedPreferences.getInt(Users.AVATAR, 0);
			mBirthday = mSharedPreferences.getString(Users.BIRTHDAY, "000000");
			mOnlineStateInt = mSharedPreferences
					.getInt(Users.ONLINESTATEINT, 0);
			mGender = mSharedPreferences.getString(Users.GENDER, "获取失败");
			mAge = mSharedPreferences.getInt(Users.AGE, -1);

			mConstellation = mSharedPreferences.getString(Users.CONSTELLATION,
					"获取失败");
			mLastLogintime = mSharedPreferences.getString(Users.LOGINTIME,
					"获取失败");
			mApplication = BaseApplication.getInstance();
			mImgExAvatar.setImageBitmap(ImageUtils.getAvatar(mApplication,
					getActivity(), Users.AVATAR + mAvatar));
			mTvExNickmame.setText(mNickname);
			mTvExConstellation.setText(mConstellation);
			mHtvExAge.setText(mAge + "");
			mTvExLogintime.setText(DateUtils.getBetweentime(mLastLogintime));
			if ("女".equals(mGender)) {
				mIvExGender.setBackgroundResource(R.drawable.ic_user_famale);
				mLayoutExGender
						.setBackgroundResource(R.drawable.bg_gender_famal);
			} else {
				mIvExGender.setBackgroundResource(R.drawable.ic_user_male);
				mLayoutExGender
						.setBackgroundResource(R.drawable.bg_gender_male);
			}
		}

	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		// System.out.println("aq ++++++++++initEvents");
		mHtvSelectOnlineState.setOnClickListener(this);

		mBtnNext.setOnClickListener(this);
		mBtnChangeUser.setOnClickListener(this);

	}

	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_char;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		// System.out.println("aq+++++++++++ init");
		mTelephonyManager = (TelephonyManager) getActivity().getSystemService(
				Context.TELEPHONY_SERVICE);
		initViews();
		initEvents();
		initData();

	}

	private void initData() {
		if (android.text.TextUtils.isEmpty(mBirthday)) {
			mSelectDate = DateUtils.getDate(DEFAULT_DATA);
			mBirthday = DEFAULT_DATA;
		} else {
			mSelectDate = DateUtils.getDate(mBirthday);
		}

		Calendar mMinCalendar = Calendar.getInstance();
		Calendar mMaxCalendar = Calendar.getInstance();

		mMinCalendar.set(Calendar.YEAR, mMinCalendar.get(Calendar.YEAR)
				- MIN_AGE);
		mMinDate = mMinCalendar.getTime();
		mMaxCalendar.set(Calendar.YEAR, mMaxCalendar.get(Calendar.YEAR)
				- MAX_AGE);
		mMaxDate = mMaxCalendar.getTime();

		mCalendar = Calendar.getInstance();
		mCalendar.setTime(mSelectDate);
		flushBirthday(mCalendar);
		mDpBirthday.init(mCalendar.get(Calendar.YEAR),
				mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH), this);

	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mBirthday = String.valueOf(year) + String.format("%02d", monthOfYear)
				+ String.format("%02d", dayOfMonth);
		mCalendar = Calendar.getInstance();
		mCalendar.set(year, monthOfYear, dayOfMonth);
		if (mCalendar.getTime().after(mMinDate)
				|| mCalendar.getTime().before(mMaxDate)) {
			mCalendar.setTime(mSelectDate);
			mDpBirthday.init(mCalendar.get(Calendar.YEAR),
					mCalendar.get(Calendar.MONTH),
					mCalendar.get(Calendar.DAY_OF_MONTH), this);
		} else {
			flushBirthday(mCalendar);
		}
	}

	private void flushBirthday(Calendar calendar) {
		String constellation = TextUtils.getConstellation(
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		mSelectDate = calendar.getTime();
		mHtvConstellation.setText(constellation);
		int age = TextUtils.getAge(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		mHtvAge.setText(age + "");
	}

	@Override
	public void onItemClick(int position) {
		mOnlineStateStr = mOnlineStateType[position];
		mOnlineStateInt = position; // 获取在线状态编号
		mHtvSelectOnlineState.requestFocus();
		mHtvSelectOnlineState.setText(mOnlineStateStr);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_htv_onlinestate:
			mOnlineStateType = getResources().getStringArray(
					R.array.onlinestate_type);
			mSimpleListDialog = new SimpleListDialog(getActivity());
			mSimpleListDialog.setTitle("选择在线状态");
			mSimpleListDialog.setTitleLineVisibility(View.GONE);
			mSimpleListDialog.setAdapter(new SimpleListDialogAdapter(
					getActivity(), mOnlineStateType));
			mSimpleListDialog.setOnSimpleListItemClickListener(this);
			mSimpleListDialog.show();
			break;

		// 更换用户,清空数据
		case R.id.login_btn_changeUser:
			mNickname = "";
			mAge = -1;
			mGender = null;
			mIMEI = null;
			mOnlineStateStr = "在线"; // 默认登录状态
			mAvatar = 0;
			mConstellation = null;
			mOnlineStateInt = 0; // 默认登录状态编号
			SessionUtils.clearSession(); // 清空Session数据
			mLlayoutMain.setVisibility(View.VISIBLE);
			mLlayoutExMain.setVisibility(View.GONE);
			break;

		case R.id.login_btn_next:
			doLoginNext();
			break;
		}

	}

	/**
	 * 登录资料完整性验证，不完整则无法登陆，完整则记录输入的信息。
	 * 
	 * @return boolean 返回是否为完整， 完整(true),不完整(false)
	 */
	private boolean isValidated() {
		mNickname = "";
		mGender = null;
		if (TextUtils.isNull(mEtNickname)) {
			showToast("请输入您的昵称");
			// showToast("昵称不能为空" + R.string.login_toast_nickname);
			mEtNickname.requestFocus();
			return false;
		} else {
			if (mEtNickname.getText().toString().length() < 2) {
				// mLoadingDialog = new FlippingLoadingDialog(getActivity(),
				// "请求提交中");
				// showLoadingDialog("昵称的长度必须在4个字符以上");
				showToast("昵称的长度必须在2个字符以上");
				return false;
			}
		}

		switch (mRgGender.getCheckedRadioButtonId()) {
		case R.id.login_baseinfo_rb_female:
			mGender = "女";
			break;
		case R.id.login_baseinfo_rb_male:
			mGender = "男";
			break;
		default:
			showToast("请选择性别");
			return false;
		}

		mNickname = mEtNickname.getText().toString().trim(); // 获取昵称
		// System.out.println("aq 测试名字" + mNickname);
		mAvatar = (int) (Math.random() * 12 + 1); // 获取头像编号
		mConstellation = mHtvConstellation.getText().toString().trim(); // 获取星座
		mAge = Integer.parseInt(mHtvAge.getText().toString().trim()); // 获取年龄
		return true;
	}

	/**
	 * 执行下一步跳转
	 * <p>
	 * 同时获取客户端的IMIE信息
	 */
	private void doLoginNext() {
		if (mNickname.length() == 0) {
			if ((!isValidated())) {
				return;
			}
		}
		putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();

				mLoadingDialog = new FlippingLoadingDialog(getActivity(),
						"请求提交中");

				showLoadingDialog(getString(R.string.login_dialog_saveInfo));
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					mIMEI = mTelephonyManager.getDeviceId(); // 获取IMEI

					// 设置用户Session信息
					SessionUtils.setIMEI(mIMEI);
					SessionUtils.setNickname(mNickname);
					SessionUtils.setAge(mAge);
					SessionUtils.setBirthday(mBirthday);
					SessionUtils.setGender(mGender);
					SessionUtils.setAvatar(mAvatar);
					SessionUtils.setOnlineStateInt(mOnlineStateInt);
					SessionUtils.setConstellation(mConstellation);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (result) {
					mContext = getActivity().getApplicationContext();
					startActivity(WifiapActivity.class);
					// getActivity().finish();
					/*
					 * Intent intent = new
					 * Intent(getActivity(),WifiapActivity.class);
					 * startActivity(intent); getActivity().finish();
					 */
				} else {
					showToast("" + R.string.login_toast_loginfailue);
				}
			}
		});
	}

}
