package com.bgw.an.app.activity.chat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.bgw.an.R;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年11月17日 下午12:47:36 类说明 :
 */
public class ParallaxLayoutInflater extends LayoutInflater {

	private ParallaxFragment fragment;

	protected ParallaxLayoutInflater(LayoutInflater original,
			Context newContext, ParallaxFragment fragment) {
		super(original, newContext);
		// TODO 改变布局加载器的工厂
		// 工厂用于创建布局文件中所有的试图。
		this.fragment = fragment;
		setFactory(new ParallaxFactory(this));
	}

	@Override
	public LayoutInflater cloneInContext(Context newContext) {
		// TODO Auto-generated method stub
		return new ParallaxLayoutInflater(this, newContext, fragment);
	}

	class ParallaxFactory implements LayoutInflater.Factory {

		private LayoutInflater inflater;
		private final String[] sClassprefix = { "android.widget.",
				"android.view.", };

		public ParallaxFactory(LayoutInflater inflater) {
			this.inflater = inflater;
		}

		// 自定义视图创建的过程
		@Override
		public View onCreateView(String name, Context context,
				AttributeSet attrs) {
			View view = null;
			// 1.自定义控件视图 不需要前缀 。
			if (view == null) {
				view = createViewOrFailQuietly(name, context, attrs);
			}
			// 2.系统试图 需要加上前缀。
			// inflater.createView(name, prefix, attrs)
			// 实例化完成
			if (view != null) {
				// 读取视图下所有的自定义属性，通过标签关联到视图上
				setViewTag(view, context, attrs);
				fragment.getParallaxViews().add(view);
			}
			return view;
		}

		private void setViewTag(View view, Context context, AttributeSet attrs) {
			// TODO Auto-generated method stub
			// 所有自定义属性
			int[] attrIds = { R.attr.a_in, R.attr.a_out, R.attr.x_in,
					R.attr.x_out, R.attr.y_in, R.attr.y_out, };
			// 获取
			TypedArray a = context.obtainStyledAttributes(attrs, attrIds);
			if (a != null && a.length() > 0) {
				ParallaxViewTag tag = new ParallaxViewTag();
				tag.alphaIn = a.getFloat(0, 0f);
				tag.alphaOut = a.getFloat(1, 0f);
				tag.xIn = a.getFloat(2, 0f);
				tag.xOut = a.getFloat(3, 0f);
				tag.yIn = a.getFloat(4, 0f);
				tag.yOut = a.getFloat(5, 0f);
				// index的值
				view.setTag(R.id.parallax_tag, tag);
			}
			a.recycle();
		}

		private View createViewOrFailQuietly(String name, String prefix,
				Context context, AttributeSet attrs) {

			try {
				inflater.createView(name, prefix, attrs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return null;
			}
			return null;
		}

		private View createViewOrFailQuietly(String name, Context context,
				AttributeSet attrs) {
			// TODO Auto-generated method stub
			if (name.contains(".")) {

				createViewOrFailQuietly(name, null, context, attrs);
			}
			for (String prefix : sClassprefix) {
				View view = createViewOrFailQuietly(name, context, attrs);
				if (view != null) {
					return view;
				}
			}
			return null;
		}

	}
}
