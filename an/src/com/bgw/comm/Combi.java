package com.bgw.comm;

import java.io.IOException;
import java.util.Properties;

import net.tsz.afinal.FinalHttp;
import android.content.Context;
import android.util.Log;

import com.bgw.comm.network.HttpRequest;


/**
 * 
 * @author acmersch
 * combi公共类
 * 
 */
public class Combi {
	public final static String TAG = "Combi";
	
//	**********************************************
//	****************	错误等级	   ***************
//	**********************************************
	public final static int LOG_VERBOSE	=	1;
	public final static int LOG_DEBUG 		= 	2;
	public final static int LOG_INFO 		= 	3;
	public final static int LOG_WARN		= 	4;
	public final static int LOG_ERROR		= 	5;
//	**********************************************
//	****************	常规错误	   ***************
//	**********************************************
	public final static String ERROR_SYSTEM_NoLog 				=	"无日志能输出";
	public final static String ERROR_SYSTEM_IllegalAccess 		=	"非法访问";
	public final static String ERROR_SYSTEM_IllegalArgument 	=	"不合理的参数";
	public final static String ERROR_SYSTEM_JSONDecodeFailed 	=	"Json解析失败";
	public final static String ERROR_SYSTEM_XMLDecodeFailed 	=	"xml解析失败";
	public final static String ERROR_SYSTEM_InvocationTarget 	=	"实例化目标对象出错";
	public final static String ERROR_SYSTEM_Instantiation 		=	"初始化出错";
	public final static String ERROR_SYSTEM_IO 					=	"I/O出错";
	public final static String ERROR_SYSTEM_NumberFormat 		=	"非数字字符串转换出错";
	public final static String ERROR_SYSTEM_JSON				=	"JSON解析出错";
	
//	
	/**
	 * 单例对象
	 */
	private static Combi instance;
	
	public static Combi newInstance() {
		if (instance == null) {
			instance = new Combi();
		}
		
		return instance;
	}
	
	private Properties config;
	
	public void loadProperties(Context context) {
		config = new Properties();
		try {
			config.load(context.getAssets().open("combi.properties"));
			
			configTimeout = Integer.parseInt(config.getProperty("configtimeout", "10000"));
			configRetrycount = Integer.parseInt(config.getProperty("configretrycount", "1"));
			
			configDeveloper = config.getProperty("configDeveloper", "sst");
			configDevelopmode = Boolean.parseBoolean(config.getProperty("configDevelopmode", "false"));
		} catch (IOException e) {
			log(TAG, ERROR_SYSTEM_IO, e, LOG_ERROR);
		} catch (NumberFormatException e) {
			log(TAG, ERROR_SYSTEM_IO, e, LOG_ERROR);
		}
	}
//	**********************************************
//	**************	系统部分变量	   ***************
//	**********************************************	
	/**
	 * 当前运行的开发者 sch liuh sst
	 */
	public String 	configDeveloper;
	/**
	 * 开发者模式
	 */
	public Boolean 	configDevelopmode;
	
//	**********************************************
//	**************	网络部分变量	   ***************
//	**********************************************
	private int configTimeout 		= 10000;		// 配置超时时间
	private int configRetrycount 	= 1;	// 配置错误重试次数 
	private String dynamicJSession 	= null;	// 会话代码
//	**********************************************
//	**************	网络部分方法	   ***************
//	**********************************************
	public static HttpRequest httpRequest() {
		Combi instance = newInstance();
		
		HttpRequest req = new HttpRequest(
				instance.getConfigTimeout(), 
				instance.getConfigRetrycount(),
				instance.getDynamicJSession()
				);
		
		return req;
	}
	
	public static FinalHttp httpClient() {
		return httpRequest().buildHTTP();
	}
	
	
	/**
	 * 日志打印错误
	 * @param tag	标签
	 * @param msg	信息
	 * @param tr		错误
	 * @param level 错误等级
	 */	
	public static void log(String tag, String msg, Throwable tr, int level) {
		tag = TAG + '#' + tag;
		switch (level) {
		case LOG_VERBOSE:
			Log.v(tag, msg, tr);
			break;
		case LOG_DEBUG:
			Log.d(tag, msg, tr);
			break;
		case LOG_INFO:
			Log.i(tag, msg, tr);
			break;
		case LOG_WARN:
			Log.w(tag, msg, tr);
			break;
		case LOG_ERROR:
			Log.e(tag, msg, tr);
			break;
		default:
			Log.e(tag, ERROR_SYSTEM_NoLog);
			break;
		}
	}
	
	/**
	 * 打印日志
	 * @param tag		日志标签
	 * @param msg		日志信息
	 * @param level		日志等级
	 */
	public static void log(String tag, String msg, int level) {
		log(tag, msg, null, level);
	}
	
//	**********************************************
//	**************	Getter/Setter	   ***********
//	**********************************************	
	
	public int getConfigTimeout() {
		return configTimeout;
	}
	public int getConfigRetrycount() {
		return configRetrycount;
	}
	public String getDynamicJSession() {
		return dynamicJSession;
	}
	public void setDynamicJSession(String jsession) {
		dynamicJSession = jsession;
	}
	
}
