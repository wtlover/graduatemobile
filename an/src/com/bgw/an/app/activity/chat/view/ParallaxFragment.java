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
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年11月17日 上午10:49:37 类说明 :
 */
public class ParallaxFragment extends Fragment {

	// 此Fragment上所有需要实现视差动画的视图
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
		// 1.buju加载器将布局加载进来。
		// 2.解析创建的布局上所有的试图。
		// 3.自己搞定创建视图的过程。
		// 4.获取试图相关的自定义属性的值。

		ParallaxLayoutInflater inflater = new ParallaxLayoutInflater(original,
				getActivity(), this);
//		View view = inflater.inflate(R.layout.zly_shicha_mejoin, null);
		return inflater.inflate(layoutId, null);
	}

	public List<View> getParallaxViews() {
		return parallaxViews;

	}
}
