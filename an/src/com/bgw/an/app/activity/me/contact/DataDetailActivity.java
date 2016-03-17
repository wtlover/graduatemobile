package com.bgw.an.app.activity.me.contact;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bgw.an.R;
import com.bgw.an.app.activity.me.contact.adapter.DataDetailAdapter;
import com.bgw.an.app.activity.me.contact.entity.DataBean;
import com.bgw.an.app.activity.me.contact.utils.DBUtil;

public class DataDetailActivity extends Activity {
	DBUtil dbUtil;
	private ApplicationInfo applicationInfo;
	private String databasePath;
	static final String DB_DIR = "database";
	static final String DB_NAME = "contacts11";
	int _id;
	String number;
	long int_number;
	String str_email;
	
	private ListView dataListView;
	private ImageView imageView;
	DataBean[] dataBeans;
	DataDetailAdapter adapter;
	private Button contacts_call, contacts_email, contacts_duanxin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.data_main);

		dataListView = (ListView) findViewById(R.id.dataListView);
		imageView = (ImageView) findViewById(R.id.imgeJiantou2);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DataDetailActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});

		init(this);
		

		if (!fileIsExists()) {
			MyTask mtask = new MyTask();
			mtask.execute();
		} else {

		}
		Bundle bundle = this.getIntent().getExtras();
		_id = bundle.getInt("_id");
		System.out.println("DataDetailActivity-->:"+_id);
		dbUtil = DBUtil.getInstance(databasePath);
		dbUtil.openDB();
		DataBean dataBean = new DataBean();
		dataBean.set_id(_id);
		System.out.println("DataDetailActivity-->:to String:"+dataBean.toString());

		dataBeans = dbUtil.getOneDataByContactId(dataBean);
		
		
		this.number = dataBeans[0].getTel();
		this.int_number = Long.parseLong(number);
		this.str_email =dataBeans[0].getEmail();
		
		adapter = new DataDetailAdapter(getApplicationContext(), dataBeans);
		dataListView.setAdapter(adapter);
		contacts_call.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+int_number));
				startActivity(callIntent);
//				Toast.makeText(getApplicationContext(), "我的电话："+number, Toast.LENGTH_LONG).show();
			}
		});
		contacts_duanxin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri smsToUri = Uri.parse("smsto:"+int_number);// 联系人地址 	  
				Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO,smsToUri);  		  
				mIntent.putExtra("sms_body", "输入短信内容");// 短信的内容  				  
				startActivity(mIntent);  			
			}
		});
		contacts_email.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				//设置文本格式
				emailIntent.setType("text/plain");
				//设置对方邮件地址
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "str_email");
				//设置标题内容
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,getString(R.string.setting_recommend_words));
				//设置邮件文本内容
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,getString(R.string.setting_recommend_words));
				startActivity(Intent.createChooser(emailIntent,"Choose Email Client"));
				
			}
		});
	}

	private void init(Context context) {
		contacts_call = (Button) findViewById(R.id.contacts_call);
		contacts_duanxin = (Button) findViewById(R.id.contacts_duanxin);
		contacts_email = (Button) findViewById(R.id.contacts_email);

		this.databasePath = getDatabasePath(context);

	}

	private String getDatabasePath(Context context) {
		String packageName = context.getPackageName();
		String path = "";
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

	// 异步处理
	public class MyTask extends AsyncTask<String, Integer, Integer> {
		@Override
		protected Integer doInBackground(String... params) {
			Integer res = 0;
			try {
				InputStream inputStream = getAssets().open("contacts.db3");
				if (databasePath != null) {
					File file = new File(databasePath);
					if (!file.exists()) {
						file.createNewFile();
					}
					FileOutputStream outputStream = new FileOutputStream(file);
					byte[] buffer = new byte[1024 * 4];
					int count = 0;
					while ((count = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, count);
					}
					outputStream.close();
				}
				inputStream.close();
				res = 1;// 代表成功

			} catch (IOException e) {
				e.printStackTrace();
			}
			return res;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// 成功了
			if (result == 1) {
				dbUtil = DBUtil.getInstance(databasePath);
				dbUtil.openDB();
				DataBean dataBean = new DataBean();
				dataBeans = dbUtil.getAllDataBean();
				adapter = new DataDetailAdapter(getApplicationContext(),
						dataBeans);
				dataListView.setAdapter(adapter);
			} else {
			}
		}
	}

	public boolean fileIsExists() {
		try {
			File f = new File(databasePath);
			if (!f.exists()) {
				// 如果不存在返回false
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
}
