package com.bgw.an.app.home.entity;

import java.io.Serializable;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��9��3�� ����7:33:27 ��˵�� :�������
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
