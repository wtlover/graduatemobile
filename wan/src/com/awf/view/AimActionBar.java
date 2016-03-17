package com.awf.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awf.utils.UtilWindow;
import com.bg.wan.R;

/**
 * ActionBar控件
 * 
 * <p>
 * 添加命名空间： xmlns:app="http://schemas.android.com/apk/res/com.aim.activity"<br>
 * <strong>注意：命名空间以引用此类库的实际工程的包名一致</strong>
 * </p>
 * 
 * <p>
 * 默认设置： </br> <code>
 * &#60;com.aim.view.AimActionBar</br>
 *  android:id="@+id/AimActionBar"</br>
 *  android:layout_width="match_parent"</br>
 *  android:layout_height="@dimen/aim_actionbar_height"</br>
 *  app:background_left="@drawable/aim_back_bg_selector"</br>
 *  app:function="text_title|button_left"</br>
 *  app:text_title="标题文本"&#62;</br>
 * &#60;/com.aim.view.AimActionBar&#62;
 * </code>
 * </p>
 * 
 * <p>
 * XML属性含义： function:开启功能,<br>
 * 如：app:function="text_title|button_left|text_right" <br>
 * 含义为：开启显示标题，显示左按钮（默认返回），显示右文字
 * </p>
 * 
 * <pre>
 * function属性设置开启后需要设置的功能：
 * <strong>text_left</strong> :显示左边文本，设置该功能后需要设置属性：app:text_left="左边文本"
 * <strong>text_right</strong> :显示右边文本，设置该功能后需要设置属性：app:text_right="右边文本"
 * <strong>text_title</strong> :显示标题，设置该功能后需要设置属性：app:text_title="这是标题"
 * <strong>button_left</strong> :左边按钮，设置该功能后需要设置属性：app:background_left="@drawable/aim_back_bg_selector"
 * <strong>button_right</strong> :右边按钮，设置该功能后需要设置属性：app:background_right="@drawable/aim_back_bg_selector"
 * <strong>button_title</strong> :中间的按钮：比如广场图标
 * </pre>
 * 
 * @ClassName AimActionBar
 * @Description 通用ActionBar
 * @author 李君波
 * @date 2014年11月19日 下午2:21:50
 * @version 1.0
 */

public class AimActionBar extends RelativeLayout implements OnClickListener {

    /** 左边的文本控件 */
    public static final int FUNCTION_TEXT_LEFT = 1;

    /** 右边的文本控件 */
    public static final int FUNCTION_TEXT_RIGHT = 2;

    /** 中间的文本控件 */
    public static final int FUNCTION_TEXT_TITLE = 4;

    /** 左边的按钮 */
    public static final int FUNCTION_BUTTON_LEFT = 8;

    /** 右边的按钮 */
    public static final int FUNCTION_BUTTON_RIGHT = 16;

    /** 中间的按钮 */
    public static final int FUNCTION_BUTTON_TITLE = 32;

    /** 默认中间文本大小 */
    private int defaultTitleTextSize = 18;

    /** 默认边上文本大小 */
    private int defaultTextSize = 14;

    /** 默认文本颜色 */
    private int defaultTextColor = Color.BLACK;

    /** 默认按下文本颜色 */
    private int prsssTextColor = Color.parseColor("#1186DB");

    /** 默认打开的功能 */
    private int curremtFunction = 0;

    private OnActionBarClickListerner onActionBarClickListerner;

    private TextView mLeftTextView;
    private TextView mRightTextView;
    private TextView mTitleTextView;

    private View mLeftButton;
    private View mRightButton;
    private View mTitleButton;

    public AimActionBar(Context context) {
        this(context, null, 0);
    }

    public AimActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0); 
    }

    public AimActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createView(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AimActionBar);

        // 初始化
        Drawable background = typedArray.getDrawable(R.styleable.AimActionBar_background);
        if (background == null) {
            background = new ColorDrawable(Color.parseColor("#50B3FA"));
        }
        this.setBackground(background);

        // 设置功能
        int function = typedArray.getInt(R.styleable.AimActionBar_function, this.curremtFunction);
        setFunction(context,function);

        setTitleText(typedArray.getString(R.styleable.AimActionBar_text_title));
        setLeftText(typedArray.getString(R.styleable.AimActionBar_text_left));
        setRightText(typedArray.getString(R.styleable.AimActionBar_text_right));

        setLeftBackground(typedArray.getDrawable(R.styleable.AimActionBar_background_left));
        setRightBackground(typedArray.getDrawable(R.styleable.AimActionBar_background_right));
        setTitleBackground(typedArray.getDrawable(R.styleable.AimActionBar_background_title));

        // 回收资源
        typedArray.recycle();
    }

    /** 初始化控件 */
    private void createView(Context context) {
        mTitleButton = new ImageView(context);
        mTitleButton.setOnClickListener(this);

        mLeftButton = new ImageView(context);
        mLeftButton.setOnClickListener(this);

        mRightButton = new ImageView(context);
        mRightButton.setOnClickListener(this);

        mTitleTextView = new TextView(context);
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, defaultTitleTextSize);
        mTitleTextView.setTextColor(defaultTextColor);
        mTitleTextView.setGravity(android.view.Gravity.CENTER);
        mTitleTextView.setOnClickListener(this);

        mLeftTextView = new AimTextView(context);
        mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, defaultTextSize);
        mLeftTextView.setTextColor(defaultTextColor);
        mLeftTextView.setGravity(android.view.Gravity.CENTER);
        mLeftTextView.setOnClickListener(this);

        mRightTextView = new AimTextView(context);
        mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, defaultTextSize);
        mRightTextView.setTextColor(defaultTextColor);
        mRightTextView.setGravity(android.view.Gravity.CENTER);
        mRightTextView.setOnClickListener(this);
    }

    /**
     * 同时开启多项功能，该函数区别于{@link AimActionBar#addFunction(int)}
     * 
     * <pre>
     * setFunction({@link AimActionBar#FUNCTION_TEXT_LEFT} | {@link AimActionBar#FUNCTION_TEXT_RIGHT});
     * </pre>
     * @param context 
     * 
     * @param function
     */
    public void setFunction(Context context, int function) {
        if (this.curremtFunction == function) {
            return;
        }

        this.curremtFunction = function;
        this.removeAllViews();

        // 添加title
        if (isAddFunction(FUNCTION_TEXT_TITLE)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            addView(mTitleTextView, params);
        }

        // 添加leftTextView
        if (isAddFunction(FUNCTION_TEXT_LEFT)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.leftMargin = UtilWindow.dip2px(context,8);
            addView(mLeftTextView, params);
        }

        // 添加rightTextView
        if (isAddFunction(FUNCTION_TEXT_RIGHT)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//            if (isInEditMode()) { return; }
            params.rightMargin = UtilWindow.dip2px(context,8);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            addView(mRightTextView, params);
        }

        // 添加titleButton
        if (isAddFunction(FUNCTION_BUTTON_TITLE)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            addView(mTitleButton, params);
        }

        // 添加leftButton
        if (isAddFunction(FUNCTION_BUTTON_LEFT)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            if (isInEditMode()) { return; }
            params.leftMargin = UtilWindow.dip2px(context,8);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            addView(mLeftButton, params);
        }

        // 添加RightButton
        if (isAddFunction(FUNCTION_BUTTON_RIGHT)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.rightMargin = UtilWindow.dip2px(context,8);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            addView(mRightButton, params);
        }
    }

    /**
     * 设置标题栏文本
     * 
     * @param titleString
     */
    public void setTitleText(CharSequence titleString) {
        mTitleTextView.setText(titleString);
    }

    /**
     * 设置标题栏文本
     * 
     * @param resId
     *            资源ID
     */
    public void setTitleText(int resId) {
        setTitleText(getResources().getString(resId));
    }

    /**
     * 设置左文本
     * 
     * @param titleString
     */
    public void setLeftText(String titleString) {
        mLeftTextView.setText(titleString);
    }

    /**
     * 设置左文本
     * 
     * @param resId
     *            资源ID
     */
    public void setLeftText(int resId) {
        setLeftText(getResources().getString(resId));
    }

    /**
     * 设置右文本
     * 
     * @param titleString
     */
    public void setRightText(String titleString) {
        mRightTextView.setText(titleString);
    }

    /**
     * 设置右文本
     * 
     * @param resId
     *            资源ID
     */
    public void setRightText(int resId) {
        setRightText(getResources().getString(resId));
    }

    /**
     * 设置左按钮的背景
     * 
     * @param background
     */
    @SuppressWarnings("deprecation")
    public void setLeftBackground(Drawable background) {
        mLeftButton.setBackgroundDrawable(background);
    }

    /**
     * 设置右按钮的背景
     * 
     * @param background
     */
    @SuppressWarnings("deprecation")
    public void setRightBackground(Drawable background) {
        mRightButton.setBackgroundDrawable(background);
    }

    /**
     * 设置中间图片的背景
     * 
     * @param background
     */
    @SuppressWarnings("deprecation")
    public void setTitleBackground(Drawable background) {
        mTitleButton.setBackgroundDrawable(background);
    }

    /**
     * 设置标题栏背景
     * 
     * @param background
     */
    @Override
    @SuppressWarnings("deprecation")
    public void setBackground(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    /**
     * 检查功能是否开启，参数有：</br> {@link AimActionBar#FUNCTION_TEXT_LEFT}：左文本</br>
     * {@link AimActionBar#FUNCTION_TEXT_RIGHT} ：右文本</br>
     * {@link AimActionBar#FUNCTION_TEXT_TITLE} ：中间文本：title</br>
     * {@link AimActionBar#FUNCTION_BUTTON_LEFT}：左按钮</br>
     * {@link AimActionBar#FUNCTION_BUTTON_RIGHT} ：右按钮</br>
     * {@link AimActionBar#FUNCTION_BUTTON_TITLE} ：中间按钮
     * 
     * @param function
     */
    public boolean isAddFunction(int function) {
        return (this.curremtFunction & function) == function;
    }

    @Override
    public void onClick(View v) {
        int function = 0;
        if (v == mLeftButton) {
            function = FUNCTION_BUTTON_LEFT;
        } else if (v == mLeftTextView) {
            function = FUNCTION_TEXT_LEFT;
        } else if (v == mRightButton) {
            function = FUNCTION_BUTTON_RIGHT;
        } else if (v == mRightTextView) {
            function = FUNCTION_TEXT_RIGHT;
        } else if (v == mTitleButton) {
            function = FUNCTION_BUTTON_TITLE;
        } else if (v == mTitleTextView) {
            function = FUNCTION_TEXT_TITLE;
        }

        // 根据控件ID处理点击事件
        if (isAddFunction(function) && function != 0) {
            boolean result = true;
            if (onActionBarClickListerner != null) {
                result = onActionBarClickListerner.onActionBarClickListener(function);
            }
            if (result && (function == FUNCTION_BUTTON_LEFT)) {
                // 处理返回事件
                Context context = getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        }
    }

    /**
     * @ClassName OnActionBarClickListerner
     * @Description ActionBar控件点击监听器
     * @author 李君波
     * @date 2014年11月19日 下午3:04:25
     * @version 1.0
     */
    public static interface OnActionBarClickListerner {
        /**
         * 按钮被点击的回调
         * 
         * @param function
         *            按钮点击事件编号: </br> {@link AimActionBar#FUNCTION_TEXT_LEFT}
         *            ：左文本被点击</br> {@link AimActionBar#FUNCTION_TEXT_RIGHT}
         *            ：右文本被点击</br> {@link AimActionBar#FUNCTION_TEXT_TITLE}
         *            ：中间文本被点击：title</br>
         *            {@link AimActionBar#FUNCTION_BUTTON_LEFT}：左按钮被点击</br>
         *            {@link AimActionBar#FUNCTION_BUTTON_RIGHT}：右按钮被点击</br>
         *            {@link AimActionBar#FUNCTION_BUTTON_TITLE}：中间按钮被点击
         * 
         * @return ActionBar控件是否需要处理返回键事件: </br> <b>true</b>:需要处理返回事件;</br>
         *         <b>false</b>：不需要再处理返回事件</br>
         */
        public boolean onActionBarClickListener(int function);
    }

    /**
     * 设置按钮被点击的事件
     * 
     * @param onActionBarClickListerner
     */
    public void setOnActionBarClickListerner(OnActionBarClickListerner onActionBarClickListerner) {
        this.onActionBarClickListerner = onActionBarClickListerner;
    }

    /**
     * @ClassName AimTextView
     * @Description
     * @author 李君波
     * @date 2014年11月19日 下午3:33:31
     * @version 1.0
     */
    private class AimTextView extends TextView {

        public AimTextView(Context context) {
            super(context);
        }

        @SuppressLint("ClickableViewAccessibility")
		@Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    setTextColor(defaultTextColor);
                    break;

                case MotionEvent.ACTION_DOWN:
                    setTextColor(prsssTextColor);
                    break;

                default:
                    break;
            }
            return super.onTouchEvent(event);
        }
    }

    public View getmRightButton() {
        return mRightButton;
    }

    public TextView getmTitleTextView() {
        return mTitleTextView;
    }

    public void setmTitleTextView(TextView mTitleTextView) {
        this.mTitleTextView = mTitleTextView;
    }

}
