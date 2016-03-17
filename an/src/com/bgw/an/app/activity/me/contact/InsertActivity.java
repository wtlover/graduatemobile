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
import android.widget.ListView;
import android.widget.TextView;

import com.bgw.an.R;
import com.bgw.an.app.activity.me.contact.adapter.DataAdapter;
import com.bgw.an.app.activity.me.contact.entity.DataBean;
import com.bgw.an.app.activity.me.contact.utils.DBUtil;

public class InsertActivity extends Activity {
	private EditText name_ett, tel_ett, address_ett, email_ett, photo_ett,
			remark;
	private TextView iTextView;
	private Button iButton;
	private DataAdapter adapter;
	private DataBean dataBean;

	private static final String DB_DIR = "databases";
	private static final String DB_NAME = "contacts11";
	private ListView listView;
	private ApplicationInfo applicationInfo;
	private Button insertBtn;
	private TextView shopInfoView, contactTextView;
	// 存放目标路径的（注意区分原文件）
	String databasePath;
	DBUtil dbUtil;
	DataBean[] dataBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.insert_main);
		initView(this);

		iButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbUtil.openDB();

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

				if (dbUtil.insert(dataBean)) {
					Intent intent = new Intent();
					intent.setClass(InsertActivity.this, MainActivity.class);
					startActivity(intent);
				} else {
					iTextView.setText("主键不唯一,插入失败！");
				}
			}

		});
	}

	private void initView(Context context) {
		name_ett = (EditText) findViewById(R.id.name_ett);
		tel_ett = (EditText) findViewById(R.id.tel_ett);
		address_ett = (EditText) findViewById(R.id.address_ett);
		email_ett = (EditText) findViewById(R.id.email_ett);
		photo_ett = (EditText) findViewById(R.id.photo_ett);
		remark = (EditText) findViewById(R.id.remark);
		iTextView = (TextView) findViewById(R.id.iTextView);

		iButton = (Button) findViewById(R.id.iButton);
		dataBean = new DataBean();

		this.databasePath = getDatabasePath(this);
		dbUtil = DBUtil.getInstance(databasePath);
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
