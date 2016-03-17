package com.bgw.an.app.comm.utils;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.bgw.an.R;

public class AdImageScroll extends LinearLayout {

	private Context context;
	private ViewPager viewpager;
	private AdPoint mypoint;
	private List<AdProcessImageView> pimglist;
	private Handler handler;
	private boolean isScrolled = false;

	public AdImageScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	public AdImageScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public AdImageScroll(Context context) {
		super(context);
		init(context, null, 0);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.home_viewpager, this, true);

		viewpager = (ViewPager) findViewById(R.id.viewpager);
		mypoint = (AdPoint) findViewById(R.id.pointview);
	}

	public void setViewPager(List<AdProcessImageView> pl, final List<String> lt) {
		pimglist = pl;
		mypoint.setImage(pl.size());
		AdViewPagerAdapter adapter = new AdViewPagerAdapter(pimglist);
		viewpager.setAdapter(adapter);
		viewpager.setCurrentItem(0);
		mypoint.setImageidx(0);
		mypoint.settitle(lt.get(0));

		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				mypoint.setImageidx(position);
				mypoint.settitle(lt.get(position));

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				switch (arg0) {
				case 1:
					isScrolled = false;
					break;
				case 2:
					isScrolled = true;
					break;
				case 0:
					if (viewpager.getCurrentItem() == viewpager.getAdapter()
							.getCount() - 1 && !isScrolled) {
						viewpager.setCurrentItem(0, false);
					} else if (viewpager.getCurrentItem() == 0 && !isScrolled) {
						viewpager.setCurrentItem(viewpager.getAdapter()
								.getCount() - 1, false);
					}
					break;

				default:
					break;
				}
			}
		});

		setSelect();
	}

	private void setSelect() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (viewpager.getCurrentItem() + 1 >= pimglist.size()) {
					viewpager.setCurrentItem(0);
				} else {
					viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
				}
				handler.sendEmptyMessageDelayed(0, 5000);
				super.handleMessage(msg);
			}
		};
		handler.sendEmptyMessageDelayed(0, 5000);
	}
}
