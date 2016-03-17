package com.awf.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * @author sunshuntao
 *
 */
public class UtilWindow {
    
    /**
     * �������ֹʵ����
     */
    private UtilWindow(){
        
    }
    
    /** 
     * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
     * @param context
     * @param dpValue
     * @author �����
     * @since 2014��11��19�� V 1.0
     */
    public static int dip2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
     */
    public static int px2dip(Context context,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * ������Ļ���
     * 
     * @Title: getScreenWidth
     * @param context
     * @return int
     * @author ���쳬
     * @since 2014-11-19 V 1.0
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * ������Ļ�߶�
     * 
     * @Title: getScreenHeight
     * @param context
     * @return int
     * @author ���쳬
     * @since 2014-11-19 V 1.0
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
    
}
