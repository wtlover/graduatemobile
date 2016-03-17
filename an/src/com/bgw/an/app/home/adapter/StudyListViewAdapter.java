package com.bgw.an.app.home.adapter;

import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgw.an.R;
import com.bgw.an.app.model.ResponseStudyMode;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年12月4日 下午2:47:29 类说明 :
 */
public class StudyListViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mLayoutInflater;
	private List<ResponseStudyMode> list;

	public StudyListViewAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudyListViewAdapter(Context context, List<ResponseStudyMode> list) {
		this.context = context;
		mLayoutInflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();

		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.sst_home_listview,
					null);
			holder.textView = (TextView) convertView
					.findViewById(R.id.textView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(list.get(position).getName() + "");
		String url = list.get(position).getUrl();
		System.out.println(url);
		return convertView;
	}

	class ViewHolder {
		public TextView textView;
	}

}
