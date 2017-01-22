package com.revogi.delite.base;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluetooth.communication.actions.Action;
import com.revogi.delite.R;
import com.revogi.delite.receiver.ScanReceiver;
import com.revogi.delite.utils.StaticUtil;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-22 15:01
 * 修改人：Administrator
 * 修改时间：2016-07-22 15:01
 * 修改备注：
 */

public abstract class BaseActivity extends FragmentActivity implements  View
        .OnClickListener {
    private ScanReceiver scanReceiver;

    /**
     * 加载布局文件
     */
    protected abstract void initLayout();

    /**
     * 获取布局文件里面的控件
     */
    protected abstract void initViews();

    /**
     * 控件注册监听事件
     */
    protected abstract void initEvents();

    /**
     * 其他事件
     */
    protected abstract void inits();

    /**
     * 标题布局
     */
    protected RelativeLayout mTitleRl;
    /**
     * 标题左按钮
     */
    protected ImageButton mTitleLeftBt;
    /**
     * 标题名字(文字)
     */
    protected TextView mTitleNameTv;
    /**
     * 标题名字(图片)
     */
    protected ImageView mTitleNameIv;
    /**
     * 标题栏右边按钮
     */
    protected ImageButton mTitleRightBt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initViews();
        initEvents();
        inits();
        initReceiver();
    }

    private void initReceiver() {
        scanReceiver = new ScanReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.ACTION_READ);
        intentFilter.addAction(Action.ACTION_WRITE);
        intentFilter.addAction(Action.ACTION_NOTIFY);
        registerReceiver(scanReceiver, intentFilter);
      //  scanReceiver.setOnBluetoothCallBackListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(scanReceiver);
    }

    /**
     * 初始化标题栏布局
     */
    protected void initTitleLayout() {
        mTitleRl = (RelativeLayout) findViewById(R.id.title_rl);
        mTitleNameTv = (TextView) findViewById(R.id.title_name_tv);
        mTitleNameIv = (ImageView) findViewById(R.id.title_name_Iv);
        mTitleLeftBt = (ImageButton) findViewById(R.id.title_left);
        mTitleRightBt = (ImageButton) findViewById(R.id.title_right);
        mTitleLeftBt.setOnClickListener(mTitleLeftBtOnClickListener);
        mTitleRightBt.setOnClickListener(this);
    }

    protected View.OnClickListener mTitleLeftBtOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            defaultFinish();
        }
    };

    /**
     * 通过Class跳转
     **/
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
        StaticUtil.enterAnimation(this);
    }

    /**
     * 默认退出
     **/
    protected void defaultFinish() {
        super.finish();
        StaticUtil.exitAnimation(this);
    }
}
