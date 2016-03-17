package com.bgw.aw.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 作�?? staryumou@163.com:
 * @version 创建时间�?2015�?12�?21�? 下午6:14:30 类说�? :
 */
public class VaildataUtils {

	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;

		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;
	}

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}
	
	public static boolean isURL(String url) {
		String str = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(url);
		
		return m.matches();
	}

	public static String judgeText(String txt) {
		String is = "d";
		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(txt);
		if (m.matches()) {
			is = "a";// 输入的是数字
		}
		p = Pattern.compile("[a-z||A-Z]");
		m = p.matcher(txt);
		if (m.matches()) {
			is = "b";// 输入的是字母
		}
		p = Pattern.compile("[\u4e00-\u9fa5]");
		m = p.matcher(txt);
		if (m.matches()) {
			is = "c";// 输入的是汉字
		}

		return is;

	}
}
