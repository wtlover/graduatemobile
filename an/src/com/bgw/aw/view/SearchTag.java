package com.bgw.aw.view;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.bgw.an.R;

public class SearchTag extends LinearLayout {

	private Context context;
	private TagOnClickListener listener;
	private final static float[] BUTTON_PRESSED = new float[] { 2.0f, 0, 0, 0,
			-50, 0, 2.0f, 0, 0, -50, 0, 0, 2.0f, 0, -50, 0, 0, 0, 5, 0 };

	/** °´Å¥»Ö¸´Ô­×´ */
	private final static float[] BUTTON_RELEASED = new float[] { 1, 0, 0, 0, 0,
			0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

	public SearchTag(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	public SearchTag(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public SearchTag(Context context) {
		super(context);
		init(context, null, 0);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		this.setOrientation(LinearLayout.VERTICAL);
		this.context = context;

	}

	@SuppressLint("NewApi")
	public void setTag(List<String> ListTag, int screenwidth) {
		LinearLayout ly = null;
		int tagwidth = (screenwidth - 18) / 3;
		for (int i = 0; i < ListTag.size(); i++) {
			final int j = i;
			if (i % 3 == 0) {
				ly = new LinearLayout(context);
				ly.setOrientation(LinearLayout.HORIZONTAL);
				ly.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				this.addView(ly);
			}
			Button btn = new Button(context);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					tagwidth, LayoutParams.MATCH_PARENT);
			lp.setMargins(3, 2, 3, 2);

			btn.setLayoutParams(lp);
			btn.setTextColor(Color.GRAY);

			btn.setText(ListTag.get(i));
			btn.setTextSize(15);
//			btn.setBackgroundColor(R.anim.sst_comm_selected);
			btn.setBackgroundResource(R.anim.button_color_selector);
			btn.getBackground().setColorFilter(
					new ColorMatrixColorFilter(BUTTON_PRESSED));
			
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (listener != null) {
						listener.TagOnClick(j);
					}
				}
			});
			ly.addView(btn);
		}
	}

	public void setTagOnclick(TagOnClickListener listener) {
		this.listener = listener;
	}

	public interface TagOnClickListener {
		public void TagOnClick(int tagidx);
	}

}
