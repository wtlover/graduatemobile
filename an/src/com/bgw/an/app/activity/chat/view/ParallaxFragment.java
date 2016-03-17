package com.bgw.an.app.activity.chat.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bgw.an.R;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��11��17�� ����10:49:37 ��˵�� :
 */
public class ParallaxFragment extends Fragment {

	// ��Fragment��������Ҫʵ���Ӳ������ͼ
	private List<View> parallaxViews = new ArrayList<View>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater original, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle args = getArguments();
		int layoutId = args.getInt("layoutId");
		int index = args.getInt("index");
		// 1.buju�����������ּ��ؽ�����
		// 2.���������Ĳ��������е���ͼ��
		// 3.�Լ��㶨������ͼ�Ĺ��̡�
		// 4.��ȡ��ͼ��ص��Զ������Ե�ֵ��

		ParallaxLayoutInflater inflater = new ParallaxLayoutInflater(original,
				getActivity(), this);
//		View view = inflater.inflate(R.layout.zly_shicha_mejoin, null);
		return inflater.inflate(layoutId, null);
	}

	public List<View> getParallaxViews() {
		return parallaxViews;

	}
}
