package com.bg.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.bg.comm.HttpCallback;
import com.lidroid.xutils.ViewUtils;

/**
 * Activity����.
 * 
 * @author ��˳��
 * @blog bgwan.blog.163.com
 * @version 1.0 2014-11-18
 */
public abstract class BaseFragment extends Fragment implements HttpCallback,
		OnTouchListener {

	protected String TAG = null;

	protected Context mContext = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(getLayoutId(), null);
		this.mContext = inflater.getContext();

		ViewUtils.inject(this, view);
		init();

		TAG = BaseFragment.class.getSimpleName();
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		view.setOnTouchListener(this);// 拦截触摸事件，防止内存泄露下�?
		super.onViewCreated(view, savedInstanceState);
	}

	/**
	 * 拦截触摸事件，防止内存泄露下�?
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return true;
	}

	public abstract int getLayoutId();

	public abstract void init();

}
