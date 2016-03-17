package com.bgw.an.app.activity.chat.activity;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.bgw.an.app.activity.chat.BaseActivity;
import com.bgw.an.app.activity.chat.adapter.SimpleListDialogAdapter;
import com.bgw.an.app.activity.chat.dialog.SimpleListDialog;
import com.bgw.an.app.activity.chat.dialog.SimpleListDialog.onSimpleListItemClickListener;
import com.bgw.an.app.activity.chat.entity.Users;
import com.bgw.an.app.activity.chat.util.DateUtils;
import com.bgw.an.app.activity.chat.util.ImageUtils;
import com.bgw.an.app.activity.chat.util.SessionUtils;
import com.bgw.an.app.activity.chat.util.TextUtils;
import com.bgw.an.app.activity.chat.view.HandyTextView;
import com.bgw.an.app.activity.chat.view.HeaderLayout;
import com.bgw.an.app.activity.chat.view.HeaderLayout.HeaderStyle;
import com.bgw.an.app.activity.chat.view.PagerScrollView;

public class LoginActivity extends BaseActivity implements OnClickListener,
		onSimpleListItemClickListener, OnDateChangedListener {

	private static final int MAX_AGE = 80;
	private static final int MIN_AGE = 12;
	private static final String DEFAULT_DATA = "19920101";

	private HeaderLayout mHeaderLayout;
	private PagerScrollView mLlayoutMain;
	private HandyTextView mHtvSelectOnlineState;
	private EditText mEtNickname;

	private HandyTextView mHtvConstellation;
	private HandyTextView mHtvAge;
	private DatePicker mDpBirthday;
	private Calendar mCalendar;
	private Date mMinDate;
	private Date mMaxDate;
	private Date mSelectDate;

	private LinearLayout mLlayoutExMain; // 浜屾鐧婚檰椤甸潰
	private ImageView mImgExAvatar;
	private TextView mTvExNickmame;
	private LinearLayout mLayoutExGender; // 鎬у埆鏍瑰竷灞�
	private ImageView mIvExGender;
	private HandyTextView mHtvExAge;
	private TextView mTvExConstellation;// 鏄熷骇
	private TextView mTvExLogintime; // 涓婃鐧诲綍鏃堕棿

	private Button mBtnBack;
	private Button mBtnNext;
	private Button mBtnChangeUser;
	private RadioGroup mRgGender;
	private TelephonyManager mTelephonyManager;
	private SimpleListDialog mSimpleListDialog;

	private int mAge;
	private int mAvatar;
	private String mBirthday;
	private String mGender;
	private String mIMEI;
	private String mConstellation; 
	private String mLastLogintime; 
	private String mNickname = "";
	private String mOnlineStateStr = "鍦ㄧ嚎";
	private int mOnlineStateInt = 0;
	private String[] mOnlineStateType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mTelephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		initViews();
		initData();
		initEvents();
	}

	@Override
	protected void initViews() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.login_header);
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
		mHeaderLayout.setDefaultTitle("鐧诲綍", null);

		mEtNickname = (EditText) findViewById(R.id.login_et_nickname);
		mHtvSelectOnlineState = (HandyTextView) findViewById(R.id.login_htv_onlinestate);
		mRgGender = (RadioGroup) findViewById(R.id.login_baseinfo_rg_gender);
		mHtvConstellation = (HandyTextView) findViewById(R.id.login_birthday_htv_constellation);
		mHtvAge = (HandyTextView) findViewById(R.id.login_birthday_htv_age);
		mDpBirthday = (DatePicker) findViewById(R.id.login_birthday_dp_birthday);

		mBtnBack = (Button) findViewById(R.id.login_btn_back);
		mBtnNext = (Button) findViewById(R.id.login_btn_next);
		mBtnChangeUser = (Button) findViewById(R.id.login_btn_changeUser);

		SharedPreferences mSharedPreferences = getSharedPreferences(
				GlobalSharedName, Context.MODE_PRIVATE);
		mNickname = mSharedPreferences.getString(Users.NICKNAME, "");

		// 鑻Nickname鏈夊唴瀹癸紝鍒欒鍙栨湰鍦板瓨鍌ㄧ殑鐢ㄦ埛淇℃伅
		if (mNickname.length() != 0) {
			mTvExNickmame = (TextView) findViewById(R.id.login_tv_existName);
			mImgExAvatar = (ImageView) findViewById(R.id.login_img_existImg);
			mLayoutExGender = (LinearLayout) findViewById(R.id.login_layout_gender);
			mIvExGender = (ImageView) findViewById(R.id.login_iv_gender);
			mHtvExAge = (HandyTextView) findViewById(R.id.login_htv_age);
			mTvExConstellation = (TextView) findViewById(R.id.login_tv_constellation);
			mTvExLogintime = (TextView) findViewById(R.id.login_tv_lastlogintime);
			mLlayoutExMain = (LinearLayout) findViewById(R.id.login_linearlayout_existmain);
			mLlayoutMain = (PagerScrollView) findViewById(R.id.login_linearlayout_main);
			mLlayoutMain.setVisibility(View.GONE);
			mLlayoutExMain.setVisibility(View.VISIBLE);

			mAvatar = mSharedPreferences.getInt(Users.AVATAR, 0);
			mBirthday = mSharedPreferences.getString(Users.BIRTHDAY, "000000");
			mOnlineStateInt = mSharedPreferences
					.getInt(Users.ONLINESTATEINT, 0);
			mGender = mSharedPreferences.getString(Users.GENDER, "鑾峰彇澶辫触");
			mAge = mSharedPreferences.getInt(Users.AGE, -1);

			mConstellation = mSharedPreferences.getString(Users.CONSTELLATION,
					"鑾峰彇澶辫触");
			mLastLogintime = mSharedPreferences.getString(Users.LOGINTIME,
					"鑾峰彇澶辫触");

			mImgExAvatar.setImageBitmap(ImageUtils.getAvatar(mApplication,
					this, Users.AVATAR + mAvatar));
			mTvExNickmame.setText(mNickname);
			mTvExConstellation.setText(mConstellation);
			mHtvExAge.setText(mAge + "");
			mTvExLogintime.setText(DateUtils.getBetweentime(mLastLogintime));
			if ("濂�".equals(mGender)) {
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
		mHtvSelectOnlineState.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);
		mBtnNext.setOnClickListener(this);
		mBtnChangeUser.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_htv_onlinestate:
			mOnlineStateType = getResources().getStringArray(
					R.array.onlinestate_type);
			mSimpleListDialog = new SimpleListDialog(LoginActivity.this);
			mSimpleListDialog.setTitle("閫夋嫨鍦ㄧ嚎鐘舵��");
			mSimpleListDialog.setTitleLineVisibility(View.GONE);
			mSimpleListDialog.setAdapter(new SimpleListDialogAdapter(
					LoginActivity.this, mOnlineStateType));
			mSimpleListDialog
					.setOnSimpleListItemClickListener(LoginActivity.this);
			mSimpleListDialog.show();
			break;

		// 鏇存崲鐢ㄦ埛,娓呯┖鏁版嵁
		case R.id.login_btn_changeUser:
			mNickname = "";
			mAge = -1;
			mGender = null;
			mIMEI = null;
			mOnlineStateStr = "鍦ㄧ嚎"; // 榛樿鐧诲綍鐘舵��
			mAvatar = 0;
			mConstellation = null;
			mOnlineStateInt = 0; // 榛樿鐧诲綍鐘舵�佺紪鍙�
			SessionUtils.clearSession(); // 娓呯┖Session鏁版嵁
			mLlayoutMain.setVisibility(View.VISIBLE);
			mLlayoutExMain.setVisibility(View.GONE);
			break;

		case R.id.login_btn_back:
			finish();
			break;

		case R.id.login_btn_next:
			doLoginNext();
			break;
		}
	}

	@Override
	public void onItemClick(int position) {
		mOnlineStateStr = mOnlineStateType[position];
		mOnlineStateInt = position; // 鑾峰彇鍦ㄧ嚎鐘舵�佺紪鍙�
		mHtvSelectOnlineState.requestFocus();
		mHtvSelectOnlineState.setText(mOnlineStateStr);
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

	/**
	 * 鐧诲綍璧勬枡瀹屾暣鎬ч獙璇侊紝涓嶅畬鏁村垯鏃犳硶鐧婚檰锛屽畬鏁村垯璁板綍杈撳叆鐨勪俊鎭��
	 * 
	 * @return boolean 杩斿洖鏄惁涓哄畬鏁达紝 瀹屾暣(true),涓嶅畬鏁�(false)
	 */
	private boolean isValidated() {
		mNickname = "";
		mGender = null;
		if (TextUtils.isNull(mEtNickname)) {
			showShortToast(R.string.login_toast_nickname);
			mEtNickname.requestFocus();
			return false;
		}

		switch (mRgGender.getCheckedRadioButtonId()) {
		case R.id.login_baseinfo_rb_female:
			mGender = "濂�";
			break;
		case R.id.login_baseinfo_rb_male:
			mGender = "鐢�";
			break;
		default:
			showShortToast(R.string.login_toast_sex);
			return false;
		}

		mNickname = mEtNickname.getText().toString().trim(); // 鑾峰彇鏄电О
		mAvatar = (int) (Math.random() * 12 + 1); // 鑾峰彇澶村儚缂栧彿
		mConstellation = mHtvConstellation.getText().toString().trim(); // 鑾峰彇鏄熷骇
		mAge = Integer.parseInt(mHtvAge.getText().toString().trim()); // 鑾峰彇骞撮緞
		return true;
	}

	/**
	 * 鎵ц涓嬩竴姝ヨ烦杞�
	 * <p>
	 * 鍚屾椂鑾峰彇瀹㈡埛绔殑IMIE淇℃伅
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
				showLoadingDialog(getString(R.string.login_dialog_saveInfo));
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					mIMEI = mTelephonyManager.getDeviceId(); // 鑾峰彇IMEI

					// 璁剧疆鐢ㄦ埛Session淇℃伅
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
					startActivity(WifiapActivity.class);
					finish();
				} else {
					showShortToast(R.string.login_toast_loginfailue);
				}
			}
		});
	}

	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
