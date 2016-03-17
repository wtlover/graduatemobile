package com.bgw.aw.utils.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.bgw.aw.utils.Pullable;
import com.bgw.aw.utils.impl.Macro_PullToRefreshLayout.OnRefreshListener;

public class PullableListView extends ListView implements Pullable ,OnRefreshListener{

	public PullableListView(Context context) {
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		if (getCount() == 0) {
			// û��item��ʱ��Ҳ��������ˢ��
			return true;
		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0).getTop() >= 0) {
			// ����ListView�Ķ�����
			return true;
		} else
			return false;
	}

	@Override
	public boolean canPullUp() {
		if (getCount() == 0) {
			// û��item��ʱ��Ҳ������������
			return true;
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			// �����ײ���
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(
							getLastVisiblePosition()
									- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}
		return false;
	}

	@Override
	public void onRefresh(Macro_PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore(Macro_PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		
	}
}
