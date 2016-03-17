package com.bgw.an.app.activity.chat.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bgw.an.R;
import com.bgw.an.app.SplashActivity;
import com.nineoldandroids.view.ViewHelper;

/**
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��11��17�� ����10:19:52 ��˵�� :
 */
/**
 * TODO<>
 * 
 * @author ��˳��
 * @data: 2015��11��17�� ����10:35:15
 * @version: V1.0
 */
public class ParallaxContainer extends FrameLayout implements
		OnPageChangeListener, OnClickListener {

	private List<ParallaxFragment> fragments;
	private ParallaxPagerAdapter adapter;
	private ParallaxFragment inFragment;
	private ParallaxFragment outFragment;
	private float containerWidth;
	private ImageView iv;
	private OnCommClickListener cListener;

	public OnCommClickListener getcListener() {
		return cListener;
	}

	public void setcListener(OnCommClickListener cListener) {
		this.cListener = cListener;
	}

	public ParallaxContainer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ParallaxContainer(Context context, AttributeSet attra) {
		super(context, attra);
		// TODO Auto-generated constructor stub
	}

	// ָ�����н���
	public List<ParallaxFragment> setUp(int... childIds) {
		fragments = new ArrayList<ParallaxFragment>();
		for (int i = 0; i < childIds.length; i++) {
			ParallaxFragment f = new ParallaxFragment();
			Bundle args = new Bundle();
			// ����
			args.putInt("index", i);
			// fragment����Ҫ���صĲ����ļ�id
			args.putInt("layoutId", childIds[i]);
			f.setArguments(args);
			fragments.add(f);
		}
		// ʵ����������
		SplashActivity activity = (SplashActivity) getContext();
		adapter = new ParallaxPagerAdapter(
				activity.getSupportFragmentManager(), fragments);

		// ʵ����viewpage
		ViewPager vp = new ViewPager(getContext());
		vp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		vp.setId(R.id.parallax_pager);

		vp.setAdapter(adapter);
		addView(vp, 0);

		// �ڷ�ҳ�Ĺ����в��ϸ��ݱ�ǩ����ͼ ��Ӧ�Ķ��������ı�λ�� ���ı���ͼ��λ�� ��������͸����
		vp.setOnPageChangeListener(this);
		vp.setClickable(true);
		vp.setOnClickListener(this);
		return fragments;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.containerWidth = getWidth();
		// this.containerWidth = widthMeasureSpec;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		System.out.println("onPageScrollStateChanged");
		// TODO Auto-generated method stub
		AnimationDrawable animation = (AnimationDrawable) iv.getBackground();
		switch (arg0) {
		case ViewPager.SCROLL_STATE_DRAGGING:
			animation.start();
			break;
		case ViewPager.SCROLL_STATE_IDLE:
			animation.stop();
			break;

		default:
			break;
		}

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		System.out.println("onPageScrolled");
		// TODO Auto-generated method stub
		// �ڷ�ҳ�Ĺ����в��ϸ��ݱ�ǩ����ͼ ��Ӧ�Ķ��������ı�λ�� ���ı���ͼ��λ�� ��������͸����
		// ��ȡ�������ҳ��
		try {
			inFragment = fragments.get(arg0 - 1);
		} catch (Exception e) {
		}
		try {
			outFragment = fragments.get(arg0);
		} catch (Exception e) {
		}
		if (inFragment != null) {
			// ��ȡ���е�Fragment ���ϵ� ��ͼ ��ʵ�ֶ���Ч����
			List<View> inViews = inFragment.getParallaxViews();
			if (inViews != null) {
				for (View view : inViews) {
					// ��ȡ��ǩ�ϣ����еĶ�������
					ParallaxViewTag tag = (ParallaxViewTag) view
							.getTag(R.id.parallaxContainer);

					if (tag == null) {
						continue;
					}
					// ���Ա�֤���� /left ���λ������
					ViewHelper.setTranslationX(view, (containerWidth - arg2)
							* tag.xIn);
					// top
					ViewHelper.setTranslationX(view, (containerWidth - arg2)
							* tag.yIn);

				}
			}
		}

		if (outFragment != null) {
			// ��ȡ���е�Fragment ���ϵ� ��ͼ ��ʵ�ֶ���Ч����
			List<View> outViews = outFragment.getParallaxViews();
			if (outViews != null) {
				for (View view : outViews) {
					// ��ȡ��ǩ�ϣ����еĶ�������
					ParallaxViewTag tag = (ParallaxViewTag) view
							.getTag(R.id.parallax_tag);

					if (tag == null) {
						continue;
					}
					ViewHelper.setTranslationX(view, (0 - arg2) * tag.xOut);
					// top
					ViewHelper.setTranslationX(view, (0 - arg2) * tag.yOut);

				}
			}
		}

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

		if (arg0 == adapter.getCount() - 1) {
			iv.setVisibility(View.GONE);
			Fragment fragment = adapter.getItem(arg0);
			if (fragment == null) {
				Toast.makeText(getContext(), "fragment=null", Toast.LENGTH_LONG)
						.show();
			} else {
				View view = fragment.getView();
				Button button = (Button) view.findViewById(R.id.btn);
				if (button == null) {
					Toast.makeText(getContext(), "button=null",
							Toast.LENGTH_LONG).show();

				} else {
					button.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
						
							cListener.OnCommClick();
						}
					});
				}

			}
		} else {
			iv.setVisibility(View.VISIBLE);
		}
	}

	public void setIv(ImageView iv) {
		this.iv = iv;
	}

	@Override
	public void onClick(View v) {
		inFragment = fragments.get(0);
		Toast.makeText(getContext(), "sss", Toast.LENGTH_LONG).show();
	}

	public interface OnCommClickListener {
		public void OnCommClick();
	}
	

}
