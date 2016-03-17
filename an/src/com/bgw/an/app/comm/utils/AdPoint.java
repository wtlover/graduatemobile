package com.bgw.an.app.comm.utils;


import java.lang.annotation.ElementType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bgw.an.R;

@SuppressLint("NewApi")
public class AdPoint extends LinearLayout{

	private ImageView imgArr[];
	private Context context;
	private TextView title;
	private LinearLayout lp;
	private LinearLayout lpL;
	public AdPoint(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	public AdPoint(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public AdPoint(Context context) {
		super(context);
		init(context, null, 0);
	}

	
	private void init(Context context, AttributeSet attrs, int defStyle){
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setGravity(Gravity.CENTER);
		this.context = context;
		this.setBackgroundColor(Color.argb(88, 0, 0, 0));
		title = new TextView(context);
		title.setLayoutParams(new LayoutParams(0,LayoutParams.WRAP_CONTENT,7f));
		title.setTextColor(Color.WHITE);
		title.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
		title.setSingleLine(true);
		title.setEllipsize(TruncateAt.MARQUEE);
		title.setTextSize(13);
		title.setMarqueeRepeatLimit(2);
		title.setFocusableInTouchMode(true);
		title.setPadding(10, 0, 0, 0);
		this.addView(title);
		
		
		lpL = new LinearLayout(context);
		lpL.setLayoutParams(new LayoutParams(0,LayoutParams.WRAP_CONTENT,2f));
		lpL.setOrientation(LinearLayout.HORIZONTAL);
		lpL.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
		this.addView(lpL);
		
		lp = new LinearLayout(context);
		lp.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,1f));
		lp.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
		lp.setPadding(0, 0, 10, 0);
		lpL.addView(lp);
	}
	
	public void setImage(int imgnum){
		imgArr = new ImageView[imgnum];
		for(int i = 0 ;i < imgnum ; i ++){
			ImageView img = new ImageView(context);
			img.setLayoutParams(new LayoutParams(30,30));
			img.setPadding(2, 5, 2, 5);
			img.setImageResource(R.drawable.ic_point_white);
			img.setAlpha(0.8f);
			imgArr[i] = img;
			lp.addView(img);
		}
	}
	
	public void setImageidx(int idx){
		for(int i = 0 ; i< imgArr.length ; i ++){
			if(i == idx){
				imgArr[i].setImageResource(R.drawable.ic_point_black);
			}else{
				imgArr[i].setImageResource(R.drawable.ic_point_white);
			}
		}
	}
	
	public void settitle(String st){
		title.setTextSize(15);
		
		title.setGravity(Gravity.CENTER);
		title.setText(st);
	}
}
