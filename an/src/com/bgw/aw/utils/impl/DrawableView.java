package com.bgw.aw.utils.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.bgw.an.R;

public class DrawableView extends View {

	private int w;
	private int h;

	// huabi1
	private Paint mPaint;
	private Paint mPainCircle;
	private Matrix mMatrix;
	private int start;
	private Handler mHandler = new Handler();
	private Runnable run = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			start += 4;
			mMatrix = new Matrix();
			mMatrix.setRotate(start, w / 2, h / 2);
			DrawableView.this.invalidate();
			mHandler.postDelayed(run, 20);

		}
	};

	public DrawableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
//		setBackgroundResource(R.drawable.bgxx);
		this.w = context.getResources().getDisplayMetrics().widthPixels;
		this.h = context.getResources().getDisplayMetrics().heightPixels;
		initPaint();
		mHandler.post(run);

	}

	private void initPaint() {
		// TODO Auto-generated method stub
		mPaint = new Paint();
		mPainCircle = new Paint();

		mPaint.setColor(Color.parseColor("#A1A1A1"));
		mPaint.setStrokeWidth(3);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);

		mPainCircle.setColor(0x9D00ff00);
		mPainCircle.setAntiAlias(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(w, h);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawCircle(w / 2, h / 2, w / 6, mPaint);
		canvas.drawCircle(w / 2, h / 2, 2 * w / 6, mPaint);
		canvas.drawCircle(w / 2, h / 2, 11 * w / 20, mPaint);
		canvas.drawCircle(w / 2, h / 2, 7 * h / 16, mPaint);

		Shader shader = new SweepGradient(w / 2, h / 2, Color.TRANSPARENT,
				Color.parseColor("#FFE4C4"));
		mPainCircle.setShader(shader);
		canvas.setMatrix(mMatrix);
		canvas.drawCircle(w / 2, h / 2, 7 * h / 16, mPainCircle);
	}
}
