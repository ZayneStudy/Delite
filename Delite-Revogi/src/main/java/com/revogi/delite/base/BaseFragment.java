package com.revogi.delite.base;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluetooth.communication.actions.Action;
import com.revogi.delite.R;
import com.revogi.delite.receiver.ScanReceiver;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-26 14:03
 * 修改人：Administrator
 * 修改时间：2016-07-26 14:03
 * 修改备注：
 */

public abstract class BaseFragment extends Fragment implements  View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private ScanReceiver scanReceiver;

    /**
     * 加载布局文件
     *
     * @param inflater
     * @param container
     * @return
     */
    protected abstract View getLayout(LayoutInflater inflater, ViewGroup container);

    /**
     * 获取布局文件里面的控件
     *
     * @param view
     */
    protected abstract void initViews(View view);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayout(inflater, container);
        initViews(view);
        initEvents();
        inits();
        scanReceiver = new ScanReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.ACTION_READ);
        intentFilter.addAction(Action.ACTION_WRITE);
        intentFilter.addAction(Action.ACTION_NOTIFY);
        getActivity().registerReceiver(scanReceiver, intentFilter);
  //      scanReceiver.setOnBluetoothCallBackListener(this);
        Log.e(TAG,"onCreateView");
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
         Log.e(TAG,"onHiddenChanged="+hidden);
    }

    /**
     * 初始化标题栏布局
     */
    protected void initTitleLayout(View view) {
        mTitleRl = (RelativeLayout) view.findViewById(R.id.title_rl);
        mTitleNameTv = (TextView) view.findViewById(R.id.title_name_tv);
        mTitleNameIv = (ImageView) view.findViewById(R.id.title_name_Iv);
        mTitleLeftBt = (ImageButton) view.findViewById(R.id.title_left);
        mTitleRightBt = (ImageButton) view.findViewById(R.id.title_right);
        mTitleLeftBt.setOnClickListener(this);
        mTitleRightBt.setOnClickListener(this);
    }

    /**
     * 通过Class跳转
     **/
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class跳转
     **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }
}
