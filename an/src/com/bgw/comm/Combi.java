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
 * combi������
 * 
 */
public class Combi {
	public final static String TAG = "Combi";
	
//	**********************************************
//	****************	����ȼ�	   ***************
//	**********************************************
	public final static int LOG_VERBOSE	=	1;
	public final static int LOG_DEBUG 		= 	2;
	public final static int LOG_INFO 		= 	3;
	public final static int LOG_WARN		= 	4;
	public final static int LOG_ERROR		= 	5;
//	**********************************************
//	****************	�������	   ***************
//	**********************************************
	public final static String ERROR_SYSTEM_NoLog 				=	"����־�����";
	public final static String ERROR_SYSTEM_IllegalAccess 		=	"�Ƿ�����";
	public final static String ERROR_SYSTEM_IllegalArgument 	=	"������Ĳ���";
	public final static String ERROR_SYSTEM_JSONDecodeFailed 	=	"Json����ʧ��";
	public final static String ERROR_SYSTEM_XMLDecodeFailed 	=	"xml����ʧ��";
	public final static String ERROR_SYSTEM_InvocationTarget 	=	"ʵ����Ŀ��������";
	public final static String ERROR_SYSTEM_Instantiation 		=	"��ʼ������";
	public final static String ERROR_SYSTEM_IO 					=	"I/O����";
	public final static String ERROR_SYSTEM_NumberFormat 		=	"�������ַ���ת������";
	public final static String ERROR_SYSTEM_JSON				=	"JSON��������";
	
//	
	/**
	 * ��������
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
//	**************	ϵͳ���ֱ���	   ***************
//	**********************************************	
	/**
	 * ��ǰ���еĿ����� sch liuh sst
	 */
	public String 	configDeveloper;
	/**
	 * ������ģʽ
	 */
	public Boolean 	configDevelopmode;
	
//	**********************************************
//	**************	���粿�ֱ���	   ***************
//	**********************************************
	private int configTimeout 		= 10000;		// ���ó�ʱʱ��
	private int configRetrycount 	= 1;	// ���ô������Դ��� 
	private String dynamicJSession 	= null;	// �Ự����
//	**********************************************
//	**************	���粿�ַ���	   ***************
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
	 * ��־��ӡ����
	 * @param tag	��ǩ
	 * @param msg	��Ϣ
	 * @param tr		����
	 * @param level ����ȼ�
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
	 * ��ӡ��־
	 * @param tag		��־��ǩ
	 * @param msg		��־��Ϣ
	 * @param level		��־�ȼ�
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
