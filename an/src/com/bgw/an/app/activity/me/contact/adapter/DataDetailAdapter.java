package com.bgw.an.app.activity.me.contact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bgw.an.R;
import com.bgw.an.app.activity.me.contact.entity.DataBean;

public class DataDetailAdapter extends BaseAdapter {
	private DataBean[] data;
	private LayoutInflater inflater;
	private Context context;

	public DataDetailAdapter(Context context, DataBean[] data) {
		super();
		this.data = data;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public int getCount() {

		return data.length;
	}

	@Override
	public Object getItem(int postion) {

		return data[postion];
	}

	@Override
	public long getItemId(int postion) {

		return postion;
	}

	public View getView(int postion, View convertView, ViewGroup parent) {
		final Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.data_list_item, null);

			holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
			holder.tel_tv = (TextView) convertView.findViewById(R.id.tel_tv);
			holder.address_tv = (TextView) convertView
					.findViewById(R.id.address_tv);
			holder.email_tv = (TextView) convertView
					.findViewById(R.id.email_tv);
			holder.photo_tv = (TextView) convertView
					.findViewById(R.id.photo_tv);
			holder.remark_tv = (TextView) convertView
					.findViewById(R.id.remark_tv);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.name_tv.setText("姓名:" + " " + data[postion].getName());
		holder.tel_tv.setText("电话号码:" + " " + data[postion].getTel());
		holder.address_tv.setText("地址:" + " " + data[postion].getAddress());
		holder.email_tv.setText("邮箱:" + " " + data[postion].getEmail());
		holder.photo_tv.setText("图片ID:" + " " + data[postion].getPhoto());
		holder.remark_tv.setText("备注:" + " " + data[postion].getRemark());

		return convertView;
	}

	class Holder {
		TextView _id_tv, name_tv, tel_tv, address_tv, email_tv, photo_tv,
				remark_tv;
	}

}
