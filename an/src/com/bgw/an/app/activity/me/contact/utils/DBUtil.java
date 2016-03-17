package com.bgw.an.app.activity.me.contact.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bgw.an.app.activity.me.contact.entity.DataBean;

public class DBUtil {

	private final static String TABLE_CONTACTS = "contact11";

	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_TEL = "tel";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_PHOTO = "photo";
	public static final String DATA_EMAIL = "email";
	public static final String DATA_REMARK = "remark";

	private String databasePath;
	private SQLiteDatabase database;

	private static DBUtil dbUtil;

	private DBUtil(String databasePath) {

	}

	public static DBUtil getInstance(String databasePath) {
		if (dbUtil == null) {
			dbUtil = new DBUtil(databasePath);
		}
		dbUtil.databasePath = databasePath;
		return dbUtil;
	}

	public int openDB() {
		try {
			if (database == null || !database.isOpen()) {
				database = SQLiteDatabase.openDatabase(this.databasePath, null,
						SQLiteDatabase.OPEN_READWRITE);
				System.out.println("sunshuntao---Open");
			}
		} catch (SQLiteException e) {
			return -1;
		}
		return 0;
	}

	public void closeDB() {

		if (database != null && database.isOpen()) {
			System.out.println("sunshuntao---close");
			database.close();
			database = null;
		}
	}

	public DataBean[] getAllDataBean() {
		Cursor results = null;
		try {
			results = database.query(TABLE_CONTACTS, null, null, null, null,
					null, null);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ConvertToDataBean(results);
	}

	private DataBean[] ConvertToDataBean(Cursor cursor) {
		int resultCounts = 0;
		try {
			resultCounts = cursor.getCount();
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		DataBean[] dataBeans = new DataBean[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			dataBeans[i] = new DataBean();

			dataBeans[i]._id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
			dataBeans[i].name = cursor.getString(cursor
					.getColumnIndex(KEY_NAME));
			dataBeans[i].tel = cursor.getString(cursor.getColumnIndex(KEY_TEL));
			dataBeans[i].address = cursor.getString(cursor
					.getColumnIndex(KEY_ADDRESS));
			dataBeans[i].email = cursor.getString(cursor
					.getColumnIndex(DATA_EMAIL));
			dataBeans[i].photo = cursor.getString(cursor
					.getColumnIndex(KEY_PHOTO));
			dataBeans[i].remark = cursor.getString(cursor
					.getColumnIndex(DATA_REMARK));

			cursor.moveToNext();
		}
		return dataBeans;
	}

	public DataBean[] getOneDataByContactId(DataBean data) {
		String id = String.valueOf(data.get_id());
		Cursor results = null;
		try {
			if (id != null) {
				results = database.query(TABLE_CONTACTS, new String[] { "name",
						"tel", "address", "email", "photo", "remark" }, "_id" + "="
						+ String.valueOf(data.get_id()), null, null, null, null);
			} else {

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		

		return ConvertToOneData(results);
	}

	private DataBean[] ConvertToOneData(Cursor results) {
		try {
			int resultCounts = results.getCount();
		} catch (Exception e) {
			// TODO: handle exception
		}
		int resultCounts = results.getCount();
		if (resultCounts == 0 || !results.moveToFirst()) {
			return null;
		}
		DataBean[] datas = new DataBean[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			datas[i] = new DataBean();

			datas[i].setName(results.getString(results.getColumnIndex(KEY_NAME)));
			datas[i].setTel(results.getString(results.getColumnIndex(KEY_TEL)));
			datas[i].setAddress(results.getString(results
					.getColumnIndex(KEY_ADDRESS)));
			datas[i].setEmail(results.getString(results
					.getColumnIndex(DATA_EMAIL)));
			datas[i].setRemark(results.getString(results
					.getColumnIndex(DATA_REMARK)));
			datas[i].setPhoto(results.getString(results
					.getColumnIndex(KEY_PHOTO)));

			results.moveToNext();
		}
		return datas;
	}

	public boolean insert(DataBean dataBean) {

		ContentValues newDataValues = new ContentValues();

		newDataValues.put(KEY_NAME, dataBean.name);
		newDataValues.put(KEY_TEL, dataBean.tel);
		newDataValues.put(KEY_ADDRESS, dataBean.address);
		newDataValues.put(DATA_EMAIL, dataBean.email);
		newDataValues.put(KEY_PHOTO, dataBean.remark);
		newDataValues.put(DATA_REMARK, dataBean.remark);

		long m = 0;
		m = database.insert(TABLE_CONTACTS, null, newDataValues);
		if (m > 0)
			return true;
		else
			return false;
	}

	public long deleteAllData() {

		return database.delete(TABLE_CONTACTS, null, null);
	}

	public long deleteOneData(long id) {
		return database.delete(TABLE_CONTACTS, KEY_ID + "=" + id, null);
	}

	//
	// public People[] queryAllData() {
	// }
	//
	// public People[] queryOneData(long id) {
	// }
	//
	public boolean updateOneData(long id, DataBean dataBean) {
		ContentValues newDataValues = new ContentValues();

		newDataValues.put(KEY_NAME, dataBean.name);
		newDataValues.put(KEY_TEL, dataBean.tel);
		newDataValues.put(KEY_ADDRESS, dataBean.address);
		newDataValues.put(DATA_EMAIL, dataBean.email);
		newDataValues.put(KEY_PHOTO, dataBean.remark);
		newDataValues.put(DATA_REMARK, dataBean.remark);
		long m = 0;
		m = database.update(TABLE_CONTACTS, newDataValues, KEY_ID + "=" + id,
				null);
		if (m > 0)
			return true;
		else
			return false;
	}
	// private People[] ConvertToPeople(Cursor cursor) {
	// }

}
