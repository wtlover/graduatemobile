package com.bgw.an.app.home.entity;

import java.io.Serializable;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年9月3日 下午7:33:27 类说明 :组合内容
 */
public class GroupChildMode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupTitle;
	private String content;

	@Override
	public String toString() {
		return "GroupChildMode [groupTitle=" + groupTitle + ", content="
				+ content + "]";
	}

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
