package com.revogi.delite.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.revogi.delite.R;
import com.revogi.delite.base.BaseActivity;

/**
 * 类描述：启动页
 * 创建人：ZZ
 * 创建时间：2016-07-25 19:44
 * 修改人：Administrator
 * 修改时间：2016-07-25 19:44
 * 修改备注：
 */

public class CoverActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();
    /**
     * 关闭启动页的延迟时间
     */
    private static final int SPLASH_DELAY_MILLIS = 2000;
    /**
     * 进入引导页面
     */
    private static final int ENTER_GUIDE = 0X01;
    /**
     * 进入主页面
     */
    private static final int ENTER_SCAN = 0X02;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_cover);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void inits() {
        mHandler.sendEmptyMessageDelayed(ENTER_SCAN, SPLASH_DELAY_MILLIS);
    }

    @Override
    public void onClick(View v) {

    }
    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case ENTER_SCAN:
                    startActivity(ScanActivity.class);
                    break;
            }
            return false;
        }
    });

}
