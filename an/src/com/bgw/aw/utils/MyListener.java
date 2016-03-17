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
		// 下拉刷新操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件刷新完毕了哦！
				pullToRefreshLayout.refreshFinish(Macro_PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

	@Override
	public void onLoadMore(final Macro_PullToRefreshLayout pullToRefreshLayout)
	{
		// 加载操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件加载完毕了哦！
				pullToRefreshLayout.loadmoreFinish(Macro_PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

}
