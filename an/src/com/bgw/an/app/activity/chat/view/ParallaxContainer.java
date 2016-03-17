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
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年11月17日 上午10:19:52 类说明 :
 */
/**
 * TODO<>
 * 
 * @author 孙顺涛
 * @data: 2015年11月17日 上午10:35:15
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

	// 指定所有界面
	public List<ParallaxFragment> setUp(int... childIds) {
		fragments = new ArrayList<ParallaxFragment>();
		for (int i = 0; i < childIds.length; i++) {
			ParallaxFragment f = new ParallaxFragment();
			Bundle args = new Bundle();
			// 索引
			args.putInt("index", i);
			// fragment中需要加载的布局文件id
			args.putInt("layoutId", childIds[i]);
			f.setArguments(args);
			fragments.add(f);
		}
		// 实例化适配器
		SplashActivity activity = (SplashActivity) getContext();
		adapter = new ParallaxPagerAdapter(
				activity.getSupportFragmentManager(), fragments);

		// 实例化viewpage
		ViewPager vp = new ViewPager(getContext());
		vp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		vp.setId(R.id.parallax_pager);

		vp.setAdapter(adapter);
		addView(vp, 0);

		// 在翻页的过程中不断根据标签中试图 对应的动画参数改变位置 ，改变视图的位置 ，或者是透明度
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
		// 在翻页的过程中不断根据标签中试图 对应的动画参数改变位置 ，改变视图的位置 ，或者是透明度
		// 获取到进入的页面
		try {
			inFragment = fragments.get(arg0 - 1);
		} catch (Exception e) {
		}
		try {
			outFragment = fragments.get(arg0);
		} catch (Exception e) {
		}
		if (inFragment != null) {
			// 获取所有的Fragment 。上的 试图 ，实现动画效果。
			List<View> inViews = inFragment.getParallaxViews();
			if (inViews != null) {
				for (View view : inViews) {
					// 获取标签上，所有的动画参数
					ParallaxViewTag tag = (ParallaxViewTag) view
							.getTag(R.id.parallaxContainer);

					if (tag == null) {
						continue;
					}
					// 可以保证兼容 /left 相对位置设置
					ViewHelper.setTranslationX(view, (containerWidth - arg2)
							* tag.xIn);
					// top
					ViewHelper.setTranslationX(view, (containerWidth - arg2)
							* tag.yIn);

				}
			}
		}

		if (outFragment != null) {
			// 获取所有的Fragment 。上的 试图 ，实现动画效果。
			List<View> outViews = outFragment.getParallaxViews();
			if (outViews != null) {
				for (View view : outViews) {
					// 获取标签上，所有的动画参数
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
