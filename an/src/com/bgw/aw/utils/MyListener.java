package com.bgw.aw.utils;

import android.os.Handler;
import android.os.Message;

import com.bgw.aw.utils.impl.Macro_PullToRefreshLayout;
import com.bgw.aw.utils.impl.Macro_PullToRefreshLayout.OnRefreshListener;

public class MyListener implements OnRefreshListener
{

	@Override
	public void onRefresh(final Macro_PullToRefreshLayout pullToRefreshLayout)
	{
		// ����ˢ�²���
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// ǧ������˸��߿ؼ�ˢ�������Ŷ��
				pullToRefreshLayout.refreshFinish(Macro_PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

	@Override
	public void onLoadMore(final Macro_PullToRefreshLayout pullToRefreshLayout)
	{
		// ���ز���
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// ǧ������˸��߿ؼ����������Ŷ��
				pullToRefreshLayout.loadmoreFinish(Macro_PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

}
