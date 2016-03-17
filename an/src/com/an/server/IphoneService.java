package com.an.server;

import java.util.List;

import com.an.entity.ExpandableListViewBean;


public interface IphoneService{
	public String getJsonStrData();
	public List<ExpandableListViewBean> getJsonListData();
	public ExpandableListViewBean getJsonBean();
}
