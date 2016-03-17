package com.bgw.an.app.activity.chat.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseApplication;
import com.bgw.an.app.activity.chat.BaseObjectListAdapter;
import com.bgw.an.app.activity.chat.entity.Entity;
import com.bgw.an.app.activity.chat.entity.Users;
import com.bgw.an.app.activity.chat.util.DateUtils;
import com.bgw.an.app.activity.chat.util.ImageUtils;
import com.bgw.an.app.activity.chat.view.HandyTextView;

public class NearByPeopleAdapter extends BaseObjectListAdapter {

	public NearByPeopleAdapter(BaseApplication application, Context context,
			List<? extends Entity> datas) {
		super(application, context, datas);
	}

	public void setData(List<? extends Entity> datas) {
		super.setData(datas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_user, null);
			holder = new ViewHolder();

			holder.mIvAvatar = (ImageView) convertView
					.findViewById(R.id.user_item_iv_avatar);
			holder.mIvDevice = (ImageView) convertView
					.findViewById(R.id.user_item_iv_icon_device);
			holder.mHtvName = (HandyTextView) convertView
					.findViewById(R.id.user_item_htv_name);
			holder.mLayoutGender = (LinearLayout) convertView
					.findViewById(R.id.user_item_layout_gender);
			holder.mIvGender = (ImageView) convertView
					.findViewById(R.id.user_item_iv_gender);
			holder.mHtvAge = (HandyTextView) convertView
					.findViewById(R.id.user_item_htv_age);
			holder.mHtvTime = (HandyTextView) convertView
					.findViewById(R.id.user_item_htv_time);
			holder.mHtvLastMsg = (HandyTextView) convertView
					.findViewById(R.id.user_item_htv_lastmsg);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Users people = (Users) getItem(position);
		holder.mIvAvatar.setImageBitmap(ImageUtils.getAvatar(mApplication,
				mContext, Users.AVATAR + people.getAvatar()));
		holder.mHtvName.setText(people.getNickname());
		holder.mLayoutGender.setBackgroundResource(people.getGenderBgId());
		holder.mIvGender.setImageResource(people.getGenderId());
		holder.mHtvAge.setText(people.getAge() + "");
		holder.mHtvTime
				.setText(DateUtils.getBetweentime(people.getLogintime()));
		holder.mHtvLastMsg.setText(mApplication.getLastMsgCache(people
				.getIMEI()));
		holder.mIvDevice.setImageResource(R.drawable.mylike);
		return convertView;
	}

	class ViewHolder {
		ImageView mIvAvatar;
		ImageView mIvDevice;
		HandyTextView mHtvName;
		LinearLayout mLayoutGender;
		ImageView mIvGender;
		HandyTextView mHtvAge;
		HandyTextView mHtvTime;
		HandyTextView mHtvLastMsg;
	}
}
