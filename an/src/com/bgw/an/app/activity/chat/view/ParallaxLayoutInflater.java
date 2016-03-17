package com.bgw.an.app.activity.chat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.bgw.an.R;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��11��17�� ����12:47:36 ��˵�� :
 */
public class ParallaxLayoutInflater extends LayoutInflater {

	private ParallaxFragment fragment;

	protected ParallaxLayoutInflater(LayoutInflater original,
			Context newContext, ParallaxFragment fragment) {
		super(original, newContext);
		// TODO �ı䲼�ּ������Ĺ���
		// �������ڴ��������ļ������е���ͼ��
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

		// �Զ�����ͼ�����Ĺ���
		@Override
		public View onCreateView(String name, Context context,
				AttributeSet attrs) {
			View view = null;
			// 1.�Զ���ؼ���ͼ ����Ҫǰ׺ ��
			if (view == null) {
				view = createViewOrFailQuietly(name, context, attrs);
			}
			// 2.ϵͳ��ͼ ��Ҫ����ǰ׺��
			// inflater.createView(name, prefix, attrs)
			// ʵ�������
			if (view != null) {
				// ��ȡ��ͼ�����е��Զ������ԣ�ͨ����ǩ��������ͼ��
				setViewTag(view, context, attrs);
				fragment.getParallaxViews().add(view);
			}
			return view;
		}

		private void setViewTag(View view, Context context, AttributeSet attrs) {
			// TODO Auto-generated method stub
			// �����Զ�������
			int[] attrIds = { R.attr.a_in, R.attr.a_out, R.attr.x_in,
					R.attr.x_out, R.attr.y_in, R.attr.y_out, };
			// ��ȡ
			TypedArray a = context.obtainStyledAttributes(attrs, attrIds);
			if (a != null && a.length() > 0) {
				ParallaxViewTag tag = new ParallaxViewTag();
				tag.alphaIn = a.getFloat(0, 0f);
				tag.alphaOut = a.getFloat(1, 0f);
				tag.xIn = a.getFloat(2, 0f);
				tag.xOut = a.getFloat(3, 0f);
				tag.yIn = a.getFloat(4, 0f);
				tag.yOut = a.getFloat(5, 0f);
				// index��ֵ
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
