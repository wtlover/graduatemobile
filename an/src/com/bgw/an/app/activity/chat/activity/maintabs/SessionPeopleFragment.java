package com.bgw.an.app.activity.chat.activity.maintabs;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseApplication;
import com.bgw.an.app.activity.chat.BaseFragment;
import com.bgw.an.app.activity.chat.activity.message.ChatActivity;
import com.bgw.an.app.activity.chat.adapter.NearByPeopleAdapter;
import com.bgw.an.app.activity.chat.entity.Users;
import com.bgw.an.app.activity.chat.view.MoMoRefreshListView;

public class SessionPeopleFragment extends BaseFragment implements
		OnItemClickListener {

	private static List<Users> mSessionPeoples; // δ����Ϣ�û��б�

	private MoMoRefreshListView mMmrlvList;
	private NearByPeopleAdapter mAdapter;
	private TextView mTvListEmpty;

	public SessionPeopleFragment() {
		super();
	}

	public SessionPeopleFragment(BaseApplication application,
			Activity activity, Context context) {
		super(application, activity, context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_nearbypeople, container,
				false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected void initViews() {
		mMmrlvList = (MoMoRefreshListView) findViewById(R.id.nearby_people_mmrlv_list);
		mTvListEmpty = (TextView) findViewById(R.id.nearby_people_mmrlv_empty);
	}

	@Override
	protected void initEvents() {
		mMmrlvList.setOnItemClickListener(this);
		mMmrlvList.setEmptyView(mTvListEmpty);
	}

	@Override
	protected void init() {
		mSessionPeoples = mApplication.getUnReadPeopleList();
		mAdapter = new NearByPeopleAdapter(mApplication, mContext,
				mSessionPeoples);
		mMmrlvList.setAdapter(mAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		int position = (int) arg3;
		Users people = mSessionPeoples.get(position);
		mApplication.removeUnReadPeople(people); // �Ƴ�δ���û�
		Intent intent = new Intent(mContext, ChatActivity.class);
		intent.putExtra(Users.ENTITY_PEOPLE, people);
		startActivity(intent);
	}

	/** ˢ���û������б�UI **/
	public void refreshAdapter() {
		mSessionPeoples = mApplication.getUnReadPeopleList();
		mAdapter.setData(mSessionPeoples);
		mAdapter.notifyDataSetChanged();
		mMmrlvList.setSelection(0);
	}

	/** ������ʾ��ʼλ�� **/
	public void setLvSelection(int position) {
		mMmrlvList.setSelection(position);
	}
}
