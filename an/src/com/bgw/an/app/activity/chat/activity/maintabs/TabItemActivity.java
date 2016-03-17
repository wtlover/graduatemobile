package com.bgw.an.app.activity.chat.activity.maintabs;

import android.os.Message;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseActivity;
import com.bgw.an.app.activity.chat.util.ActivityCollectorUtils;

public class TabItemActivity extends BaseActivity {

    protected Long exitTime = (long) 0;

    protected void init() {
    }

    @Override
    protected void initViews() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub

    }

    @Override
    public void processMessage(Message msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBackPressed() { // ·µ»Ø×ÀÃæ
        if (MainTabActivity.getIsTabActive()) {
            System.out.println(System.currentTimeMillis() - exitTime);
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showShortToast(R.string.tabitem_toast_logout);
                exitTime = System.currentTimeMillis();
            }
            else {
                ActivityCollectorUtils.finishAllActivities(mApplication, getApplicationContext());
            }
//            ActivityCollectorUtils.finishAllActivities(mApplication, getApplicationContext());
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

}
