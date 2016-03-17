package com.bgw.an.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/** 
 * TODO<> 
 * @author  孙顺涛 
 * @data:  2015年9月3日 下午2:55:32 
 * @version:  V1.0 
 */ 
public abstract class AbstractAdapter<T> extends BaseAdapter {
	protected Context context;
	protected List<T> objectList;

	public AbstractAdapter(Context context) {
		this.context = context;
		objectList = new ArrayList<T>();
	}

	@Override
	public int getCount() {
		return objectList.size();
	}

	@Override
	public Object getItem(int position) {
		return objectList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public T getObject(int position) {
		if (position >= objectList.size()) {
			return null;
		}
		return objectList.get(position);
	}

	public void notifyData(List<T> objectList) {
		this.objectList.clear();
		this.objectList.addAll(objectList);
		notifyDataSetChanged();
	}

	public void add(T object) {
		this.objectList.add(object);
	}

	// public <T> void addData(T object, int index) {
	// this.objectList.add(index, object);
	// notifyDataSetChanged();
	// }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AbstractViewHolder viewHolder = null;
		if (null == convertView) {
			convertView = createView();
			viewHolder = createViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (AbstractViewHolder) convertView.getTag();
		}
		viewHolder.init(position);
		return convertView;
	}

	protected abstract class AbstractViewHolder {
		public abstract void init(final int positon);
	}

	protected abstract View createView();

	protected abstract AbstractViewHolder createViewHolder(View view);
}
