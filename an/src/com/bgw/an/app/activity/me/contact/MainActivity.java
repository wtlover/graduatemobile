package com.bgw.an.app.activity.me.contact;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bgw.an.R;
import com.bgw.an.app.activity.me.contact.adapter.DataAdapter;
import com.bgw.an.app.activity.me.contact.entity.DataBean;
import com.bgw.an.app.activity.me.contact.utils.DBUtil;

public class MainActivity extends Activity implements OnItemClickListener,
		OnItemLongClickListener {
	private static final String DB_DIR = "databases";
	private static final String DB_NAME = "contacts11";
	private ListView listView;
	private ApplicationInfo applicationInfo;
	private Button insertBtn;
	private TextView shopInfoView;
	private DataAdapter adapter = null;
	// 存放目标路径的（注意区分原文件）
	String databasePath;
	DBUtil dbUtil;
	DataBean[] dataBeans;
	int position;
	int _id;

	private void android18UIStandard() {
		// Android 4.0后主线程默认只能运行UI
		StrictMode.setThreadPolicy(new

		StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites

		().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects

				().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_contact);
		android18UIStandard();
		initView(this);
		if (!fileIsExists()) {
			MyTask mtask = new MyTask();
			mtask.execute();
		} else {
			
		}

		buttonListener();
		loadLocalData();
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
	}

	private void loadLocalData() {
		dbUtil = DBUtil.getInstance(databasePath);
		dbUtil.openDB();
		shopInfoView.setText("数据库拷贝成功");
		dataBeans = dbUtil.getAllDataBean();

		adapter = new DataAdapter(getApplicationContext(), dataBeans);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
		
	}

	private void buttonListener() {
		insertBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, InsertActivity.class);
				startActivity(intent);
			}
		});

	}

	private void initView(Context context) {
		listView = (ListView) findViewById(R.id.contactListView);
		shopInfoView = (TextView) findViewById(R.id.shopInfoView);
		insertBtn = (Button) findViewById(R.id.contacts_call);

		this.databasePath = getDatabasePath(this);
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

	// 异步处理
	public class MyTask extends AsyncTask<String, Integer, Integer> {
		@Override
		protected Integer doInBackground(String... params) {
			Integer res = 0;
			try {
				InputStream inputStream = getAssets().open("contacts11.db3");
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
				shopInfoView.setText("数据库拷贝成功");
				dataBeans = dbUtil.getAllDataBean();
				adapter = new DataAdapter(getApplicationContext(), dataBeans);
				adapter.notifyDataSetChanged();
				listView.setAdapter(adapter);
			} else {
				shopInfoView.setText("数据库拷贝不成功！");
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

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			final int position, long id) {

		LinearLayout loginLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.operator_contacts, null);
		final Button mDeleteBtn = (Button) loginLayout
				.findViewById(R.id.deleteBtn);
		final Button mUpdataBtn = (Button) loginLayout
				.findViewById(R.id.updataBtn);
		new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.dianxin)
				.setTitle("操作通讯录").setView(loginLayout)
				.setPositiveButton("", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).setNegativeButton("", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
		mDeleteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				_id = dataBeans[position].get_id();
				long m = dbUtil.deleteOneData(_id);
				if (m > 0) {
					shopInfoView.setText("删除通讯录成功");
					Toast.makeText(getApplicationContext(), "操作成功",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, MainActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
				} else {
					shopInfoView.setText("删除通讯录失败");
				}
			}
		});
		mUpdataBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				_id = dataBeans[position].get_id();
				if (dbUtil != null) {
					DataBean dataBean = new DataBean();
					dataBean.set_id(_id);
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, UpdateActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("_id", _id);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		// Toast.makeText(getApplicationContext(), "按钮测试1",
		// Toast.LENGTH_LONG).show();
		_id = dataBeans[position].get_id();
		

		if (dbUtil != null) {
			System.out.println("MainActivity-->:" + _id);
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, DataDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("_id", _id);
			intent.putExtras(bundle);
			startActivity(intent);
		}

	}
}
