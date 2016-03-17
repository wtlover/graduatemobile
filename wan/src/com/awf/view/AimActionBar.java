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
 * ActionBar�ؼ�
 * 
 * <p>
 * ��������ռ䣺 xmlns:app="http://schemas.android.com/apk/res/com.aim.activity"<br>
 * <strong>ע�⣺�����ռ������ô�����ʵ�ʹ��̵İ���һ��</strong>
 * </p>
 * 
 * <p>
 * Ĭ�����ã� </br> <code>
 * &#60;com.aim.view.AimActionBar</br>
 *  android:id="@+id/AimActionBar"</br>
 *  android:layout_width="match_parent"</br>
 *  android:layout_height="@dimen/aim_actionbar_height"</br>
 *  app:background_left="@drawable/aim_back_bg_selector"</br>
 *  app:function="text_title|button_left"</br>
 *  app:text_title="�����ı�"&#62;</br>
 * &#60;/com.aim.view.AimActionBar&#62;
 * </code>
 * </p>
 * 
 * <p>
 * XML���Ժ��壺 function:��������,<br>
 * �磺app:function="text_title|button_left|text_right" <br>
 * ����Ϊ��������ʾ���⣬��ʾ��ť��Ĭ�Ϸ��أ�����ʾ������
 * </p>
 * 
 * <pre>
 * function�������ÿ�������Ҫ���õĹ��ܣ�
 * <strong>text_left</strong> :��ʾ����ı������øù��ܺ���Ҫ�������ԣ�app:text_left="����ı�"
 * <strong>text_right</strong> :��ʾ�ұ��ı������øù��ܺ���Ҫ�������ԣ�app:text_right="�ұ��ı�"
 * <strong>text_title</strong> :��ʾ���⣬���øù��ܺ���Ҫ�������ԣ�app:text_title="���Ǳ���"
 * <strong>button_left</strong> :��߰�ť�����øù��ܺ���Ҫ�������ԣ�app:background_left="@drawable/aim_back_bg_selector"
 * <strong>button_right</strong> :�ұ߰�ť�����øù��ܺ���Ҫ�������ԣ�app:background_right="@drawable/aim_back_bg_selector"
 * <strong>button_title</strong> :�м�İ�ť������㳡ͼ��
 * </pre>
 * 
 * @ClassName AimActionBar
 * @Description ͨ��ActionBar
 * @author �����
 * @date 2014��11��19�� ����2:21:50
 * @version 1.0
 */

public class AimActionBar extends RelativeLayout implements OnClickListener {

    /** ��ߵ��ı��ؼ� */
    public static final int FUNCTION_TEXT_LEFT = 1;

    /** �ұߵ��ı��ؼ� */
    public static final int FUNCTION_TEXT_RIGHT = 2;

    /** �м���ı��ؼ� */
    public static final int FUNCTION_TEXT_TITLE = 4;

    /** ��ߵİ�ť */
    public static final int FUNCTION_BUTTON_LEFT = 8;

    /** �ұߵİ�ť */
    public static final int FUNCTION_BUTTON_RIGHT = 16;

    /** �м�İ�ť */
    public static final int FUNCTION_BUTTON_TITLE = 32;

    /** Ĭ���м��ı���С */
    private int defaultTitleTextSize = 18;

    /** Ĭ�ϱ����ı���С */
    private int defaultTextSize = 14;

    /** Ĭ���ı���ɫ */
    private int defaultTextColor = Color.BLACK;

    /** Ĭ�ϰ����ı���ɫ */
    private int prsssTextColor = Color.parseColor("#1186DB");

    /** Ĭ�ϴ򿪵Ĺ��� */
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

        // ��ʼ��
        Drawable background = typedArray.getDrawable(R.styleable.AimActionBar_background);
        if (background == null) {
            background = new ColorDrawable(Color.parseColor("#50B3FA"));
        }
        this.setBackground(background);

        // ���ù���
        int function = typedArray.getInt(R.styleable.AimActionBar_function, this.curremtFunction);
        setFunction(context,function);

        setTitleText(typedArray.getString(R.styleable.AimActionBar_text_title));
        setLeftText(typedArray.getString(R.styleable.AimActionBar_text_left));
        setRightText(typedArray.getString(R.styleable.AimActionBar_text_right));

        setLeftBackground(typedArray.getDrawable(R.styleable.AimActionBar_background_left));
        setRightBackground(typedArray.getDrawable(R.styleable.AimActionBar_background_right));
        setTitleBackground(typedArray.getDrawable(R.styleable.AimActionBar_background_title));

        // ������Դ
        typedArray.recycle();
    }

    /** ��ʼ���ؼ� */
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
     * ͬʱ��������ܣ��ú���������{@link AimActionBar#addFunction(int)}
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

        // ���title
        if (isAddFunction(FUNCTION_TEXT_TITLE)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            addView(mTitleTextView, params);
        }

        // ���leftTextView
        if (isAddFunction(FUNCTION_TEXT_LEFT)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.leftMargin = UtilWindow.dip2px(context,8);
            addView(mLeftTextView, params);
        }

        // ���rightTextView
        if (isAddFunction(FUNCTION_TEXT_RIGHT)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//            if (isInEditMode()) { return; }
            params.rightMargin = UtilWindow.dip2px(context,8);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            addView(mRightTextView, params);
        }

        // ���titleButton
        if (isAddFunction(FUNCTION_BUTTON_TITLE)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            addView(mTitleButton, params);
        }

        // ���leftButton
        if (isAddFunction(FUNCTION_BUTTON_LEFT)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            if (isInEditMode()) { return; }
            params.leftMargin = UtilWindow.dip2px(context,8);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            addView(mLeftButton, params);
        }

        // ���RightButton
        if (isAddFunction(FUNCTION_BUTTON_RIGHT)) {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.rightMargin = UtilWindow.dip2px(context,8);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            addView(mRightButton, params);
        }
    }

    /**
     * ���ñ������ı�
     * 
     * @param titleString
     */
    public void setTitleText(CharSequence titleString) {
        mTitleTextView.setText(titleString);
    }

    /**
     * ���ñ������ı�
     * 
     * @param resId
     *            ��ԴID
     */
    public void setTitleText(int resId) {
        setTitleText(getResources().getString(resId));
    }

    /**
     * �������ı�
     * 
     * @param titleString
     */
    public void setLeftText(String titleString) {
        mLeftTextView.setText(titleString);
    }

    /**
     * �������ı�
     * 
     * @param resId
     *            ��ԴID
     */
    public void setLeftText(int resId) {
        setLeftText(getResources().getString(resId));
    }

    /**
     * �������ı�
     * 
     * @param titleString
     */
    public void setRightText(String titleString) {
        mRightTextView.setText(titleString);
    }

    /**
     * �������ı�
     * 
     * @param resId
     *            ��ԴID
     */
    public void setRightText(int resId) {
        setRightText(getResources().getString(resId));
    }

    /**
     * ������ť�ı���
     * 
     * @param background
     */
    @SuppressWarnings("deprecation")
    public void setLeftBackground(Drawable background) {
        mLeftButton.setBackgroundDrawable(background);
    }

    /**
     * �����Ұ�ť�ı���
     * 
     * @param background
     */
    @SuppressWarnings("deprecation")
    public void setRightBackground(Drawable background) {
        mRightButton.setBackgroundDrawable(background);
    }

    /**
     * �����м�ͼƬ�ı���
     * 
     * @param background
     */
    @SuppressWarnings("deprecation")
    public void setTitleBackground(Drawable background) {
        mTitleButton.setBackgroundDrawable(background);
    }

    /**
     * ���ñ���������
     * 
     * @param background
     */
    @Override
    @SuppressWarnings("deprecation")
    public void setBackground(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    /**
     * ��鹦���Ƿ����������У�</br> {@link AimActionBar#FUNCTION_TEXT_LEFT}�����ı�</br>
     * {@link AimActionBar#FUNCTION_TEXT_RIGHT} �����ı�</br>
     * {@link AimActionBar#FUNCTION_TEXT_TITLE} ���м��ı���title</br>
     * {@link AimActionBar#FUNCTION_BUTTON_LEFT}����ť</br>
     * {@link AimActionBar#FUNCTION_BUTTON_RIGHT} ���Ұ�ť</br>
     * {@link AimActionBar#FUNCTION_BUTTON_TITLE} ���м䰴ť
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

        // ���ݿؼ�ID�������¼�
        if (isAddFunction(function) && function != 0) {
            boolean result = true;
            if (onActionBarClickListerner != null) {
                result = onActionBarClickListerner.onActionBarClickListener(function);
            }
            if (result && (function == FUNCTION_BUTTON_LEFT)) {
                // �������¼�
                Context context = getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        }
    }

    /**
     * @ClassName OnActionBarClickListerner
     * @Description ActionBar�ؼ����������
     * @author �����
     * @date 2014��11��19�� ����3:04:25
     * @version 1.0
     */
    public static interface OnActionBarClickListerner {
        /**
         * ��ť������Ļص�
         * 
         * @param function
         *            ��ť����¼����: </br> {@link AimActionBar#FUNCTION_TEXT_LEFT}
         *            �����ı������</br> {@link AimActionBar#FUNCTION_TEXT_RIGHT}
         *            �����ı������</br> {@link AimActionBar#FUNCTION_TEXT_TITLE}
         *            ���м��ı��������title</br>
         *            {@link AimActionBar#FUNCTION_BUTTON_LEFT}����ť�����</br>
         *            {@link AimActionBar#FUNCTION_BUTTON_RIGHT}���Ұ�ť�����</br>
         *            {@link AimActionBar#FUNCTION_BUTTON_TITLE}���м䰴ť�����
         * 
         * @return ActionBar�ؼ��Ƿ���Ҫ�����ؼ��¼�: </br> <b>true</b>:��Ҫ�������¼�;</br>
         *         <b>false</b>������Ҫ�ٴ������¼�</br>
         */
        public boolean onActionBarClickListener(int function);
    }

    /**
     * ���ð�ť��������¼�
     * 
     * @param onActionBarClickListerner
     */
    public void setOnActionBarClickListerner(OnActionBarClickListerner onActionBarClickListerner) {
        this.onActionBarClickListerner = onActionBarClickListerner;
    }

    /**
     * @ClassName AimTextView
     * @Description
     * @author �����
     * @date 2014��11��19�� ����3:33:31
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
