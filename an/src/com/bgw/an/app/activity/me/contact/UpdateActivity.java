package com.bgw.an.app.activity.me.contact;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bgw.an.R;
import com.bgw.an.app.activity.me.contact.entity.DataBean;
import com.bgw.an.app.activity.me.contact.utils.DBUtil;

public class UpdateActivity extends Activity {
	private EditText name_ett, tel_ett, address_ett, email_ett, photo_ett,
			remark;
	private TextView updataTextView, showInfoView;
	private Button updateBtn;
	private DataBean dataBean;
	int _id;

	private static final String DB_DIR = "databases";
	private static final String DB_NAME = "contacts11";
	private ApplicationInfo applicationInfo;
	// 存放目标路径的（注意区分原文件）
	String databasePath;
	DBUtil dbUtil;
	DataBean[] dataBeans;
	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.insert_main);

		initView(this);

		this.databasePath = getDatabasePath(this);
		dbUtil = DBUtil.getInstance(databasePath);
		dataBean = new DataBean();
		dbUtil.openDB();
		bundle = this.getIntent().getExtras();
		initAllData();

		updateBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				initAllDataAndUpdate();

				if (dbUtil.updateOneData(bundle.getInt("_id"), dataBean)) {
					System.out.println("updata+++> =" + _id);
					showInfoView.setText("修改成功");
					Intent intent = new Intent();
					intent.setClass(UpdateActivity.this, MainActivity.class);
					startActivity(intent);
				} else {
					showInfoView.setText("修改失败");
				}
			}
		});
	}

	private void initAllDataAndUpdate() {
		String str_name_ett = name_ett.getText().toString();
		String str_tel_ett = tel_ett.getText().toString();
		String str_address_ett = address_ett.getText().toString();
		String str_email_ett = email_ett.getText().toString();
		String str_photo_ett = photo_ett.getText().toString();
		String str_remark = remark.getText().toString();

		dataBean.setName(str_name_ett);
		dataBean.setTel(str_tel_ett);
		dataBean.setAddress(str_address_ett);
		dataBean.setEmail(str_email_ett);
		dataBean.setPhoto(str_photo_ett);
		dataBean.setRemark(str_remark);

	}

	private void initAllData() {
		dataBean = new DataBean();
		Bundle bundle = this.getIntent().getExtras();
		this._id = bundle.getInt("_id");
		dbUtil = DBUtil.getInstance(databasePath);
		dbUtil.openDB();
		dataBean.set_id(_id);
		dataBeans = dbUtil.getOneDataByContactId(dataBean);
		System.out.println("获取数据位置ID2 =" + _id);

		name_ett.setText(dataBeans[0].getName());
		tel_ett.setText(dataBeans[0].getTel());
		address_ett.setText(dataBeans[0].getAddress());
		email_ett.setText(dataBeans[0].getEmail());
		photo_ett.setText(dataBeans[0].getPhoto());
		remark.setText(dataBeans[0].getRemark());
	}

	private void initView(Context context) {
		name_ett = (EditText) findViewById(R.id.name_ett);
		tel_ett = (EditText) findViewById(R.id.tel_ett);
		address_ett = (EditText) findViewById(R.id.address_ett);
		email_ett = (EditText) findViewById(R.id.email_ett);
		photo_ett = (EditText) findViewById(R.id.photo_ett);
		remark = (EditText) findViewById(R.id.remark);
		showInfoView = (TextView) findViewById(R.id.iTextView);

		updateBtn = (Button) findViewById(R.id.iButton);
		updataTextView = (TextView) findViewById(R.id.updata_id);

		updataTextView.setText("修改通讯录界面");
		updateBtn.setText("确定修改");

	}

	public String getDatabasePath(Context context) {
		String path = "";
		String packageName = context.getPackageName();
		try {
			applicationInfo = context.getPackageManager().getApplicationInfo(
					packageName, PackageManager.GET_META_DATA);
			String dbDir = applicationInfo.dataDir + File.separator + DB_DIR;
			File file = new File(dbDir);
			if (!file.exists()) {
				file.mkdir();
			}

			path = applicationInfo.dataDir + File.separator + DB_DIR
					+ File.separator + DB_NAME;
		} catch (NameNotFoundException e) {
		}
		return path;
	}
}
