package com.bgw.an.app.activity.chat.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import android.net.Uri;
import android.os.Environment;

/**
 * @fileName FileUtils.java
 * @package szu.wifichat.android.util
 * @description 鏂囦欢宸ュ叿绫�
 */
public class FileUtils {

	/**
	 * 鍒ゆ柇SD
	 * 
	 * @return
	 */
	public static boolean isSdcardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 鍒涘缓鏍圭洰褰�
	 * 
	 * @param path
	 *            鐩綍璺緞
	 */
	public static void createDirFile(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 鍒涘缓鏂囦欢
	 * 
	 * @param path
	 *            鏂囦欢璺緞
	 * @return 鍒涘缓鐨勬枃浠�
	 */
	public static File createNewFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
		}
		return file;
	}

	/**
	 * 鍒犻櫎鏂囦欢澶�
	 * 
	 * @param folderPath
	 *            鏂囦欢澶圭殑璺緞
	 */
	public static void delFolder(String folderPath) {
		delAllFile(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		myFilePath.delete();
	}

	/**
	 * 鍒犻櫎鏂囦欢
	 * 
	 * @param path
	 *            鏂囦欢鐨勮矾寰�
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		int mLength = tempList.length;
		for (int i = 0; i < mLength; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
	}

	/**
	 * 鑾峰彇鏂囦欢鐨刄ri
	 * 
	 * @param path
	 *            鏂囦欢鐨勮矾寰�
	 * @return
	 */
	public static Uri getUriFromFile(String path) {
		File file = new File(path);
		return Uri.fromFile(file);
	}

	/**
	 * 鎹㈢畻鏂囦欢澶у皬
	 * 
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "鏈煡澶у皬";
		if (size < 1024) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size < 1048576) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			fileSizeString = df.format((double) size / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) size / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 閫氳繃璺緞鑾峰緱鏂囦欢鍚嶅瓧
	 * 
	 * @param path
	 * @return
	 */
	public static String getPathByFullPath(String fullpath) {
		return fullpath.substring(0, fullpath.lastIndexOf(File.separator));
	}

	/**
	 * 閫氳繃璺緞鑾峰緱鏂囦欢鍚嶅瓧
	 * 
	 * @param path
	 * @return
	 */
	public static String getNameByPath(String path) {
		return path.substring(path.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 閫氳繃鍒ゆ柇鏂囦欢鏄惁瀛樺湪
	 * 
	 * @param path
	 * @return
	 */

	public static boolean isFileExists(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	/**
	 * 鑾峰緱SD鍗¤矾寰�
	 * 
	 * @param
	 * @return String
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 鍒ゆ柇sd鍗℃槸鍚﹀瓨鍦�
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 鑾峰彇璺熺洰褰�
			return sdDir.toString();
		}
		return null;
	}

}
