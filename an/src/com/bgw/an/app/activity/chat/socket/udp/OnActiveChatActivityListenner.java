package com.bgw.an.app.activity.chat.socket.udp;

import com.bgw.an.app.activity.chat.entity.Message;

/** 接收消息监听的listener接口 **/
public interface OnActiveChatActivityListenner {

	/**
	 * 判断收到的消息是否匹配已打开的聊天窗口
	 * 
	 * @param paramMsg
	 *            收到的消息对象
	 * @return
	 */
	public boolean isThisActivityMsg(Message paramMsg);
}