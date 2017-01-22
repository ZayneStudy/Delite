package com.revogi.delite.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetooth.communication.actions.Action;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.revogi.delite.R;
import com.revogi.delite.fragment.ColorFragment;
import com.revogi.delite.fragment.DevicesListFragment;
import com.revogi.delite.fragment.EffectsFragment;
import com.revogi.delite.fragment.ScenesFragment;
import com.revogi.delite.utils.StaticUtil;

import static com.bluetooth.communication.actions.Config.scanReceiver;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-26 14:18
 * 修改人：Administrator
 * 修改时间：2016-07-26 14:18
 * 修改备注：
 */

public class MyFragmentActivity extends SlidingFragmentActivity implements View.OnClickListener, SlidingMenu
        .OnClosedListener {
    private final String TAG = this.getClass().getSimpleName();
    private ColorFragment mColorFragment; // 第一个fragment对象
    private EffectsFragment mEffectsFragment; // 第二个fragment对象
    private ScenesFragment mScenesFragment; // 第三个fragment对象
    private LinearLayout mColorLy, mEffectsLy, mScenesLy; // 底部导航栏的3个布局
    private ImageView mColorIv, mEffectsIv, mScenesIv; // 底部导航栏的3个图标
    private TextView mColorTv, mEffectsTv, mScenesTv; // 底部导航栏的3个文字
    private FragmentManager mFragmentManager; // FragmentManager对象
    private FragmentTransaction mFragmentTransaction;
    private SlidingMenu mSlidingMenu;
    protected ImageButton mTitleLeftBt, mTitleRightBt;
    protected TextView mTitleNameTv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.ACTION_READ);
        intentFilter.addAction(Action.ACTION_WRITE);
        intentFilter.addAction(Action.ACTION_NOTIFY);
        this.registerReceiver(scanReceiver, intentFilter);
        initViews();
        initSlidingMenu();
        initEvents();
        inits();
    }

    private void initLayout() {
        setContentView(R.layout.activity_fragment);
    }

    private void initViews() {
        mColorLy = (LinearLayout) findViewById(R.id.activity_fragment_color);
        mEffectsLy = (LinearLayout) findViewById(R.id.activity_fragment_effects);
        mScenesLy = (LinearLayout) findViewById(R.id.activity_fragment_scenes);
        mColorIv = (ImageView) findViewById(R.id.activity_fragment_color_iv);
        mEffectsIv = (ImageView) findViewById(R.id.activity_fragment_effects_iv);
        mScenesIv = (ImageView) findViewById(R.id.activity_fragment_scenes_iv);
        mColorTv = (TextView) findViewById(R.id.activity_fragment_color_tv);
        mEffectsTv = (TextView) findViewById(R.id.activity_fragment_effects_tv);
        mScenesTv = (TextView) findViewById(R.id.activity_fragment_scenes_tv);
        mTitleLeftBt = (ImageButton) findViewById(R.id.title_left);
        mTitleRightBt = (ImageButton) findViewById(R.id.title_right);
        mTitleNameTv = (TextView) findViewById(R.id.title_name_tv);
    }

    private void initEvents() {
        mColorLy.setOnClickListener(this);
        mEffectsLy.setOnClickListener(this);
        mScenesLy.setOnClickListener(this);
        mSlidingMenu.setOnClosedListener(this);
        mTitleLeftBt.setOnClickListener(this);
        mTitleRightBt.setOnClickListener(this);
    }

    private void initSlidingMenu() {
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        setBehindContentView(R.layout.menu_frame_left);
        mSlidingMenu.setSecondaryMenu(R.layout.menu_frame_right);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_left, new DevicesListFragment())
                .commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_right, new DevicesListFragment())
                .commit();
        int screenWidth = StaticUtil.getPhoneParameter(this).get(0);
        mSlidingMenu.setBehindOffset(screenWidth / 5); // 设置左边菜单的宽度占屏幕的百分百
        mSlidingMenu.setRightBehindWidth(screenWidth / 2);// 设置右边菜单的宽度占屏幕的百分百

    }

    private void inits() {
        mFragmentManager = getSupportFragmentManager();
        setChoiceItem(0);
        isLeftMenuShow();
    }

    @Override
    public void onClick(View v) {
        if (v == mTitleLeftBt)
            isLeftMenuShow();
        else if (v == mTitleRightBt)
            isRightMenuShow();
        else if (v == mColorLy)
            setChoiceItem(0);
        else if (v == mEffectsLy)
            setChoiceItem(1);
        else if (v == mScenesLy)
            setChoiceItem(2);
    }

    @Override
    public void onClosed() {

    }

    /**
     * 选中一个item后的处理
     *
     * @param index
     */
    private void setChoiceItem(int index) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        clearChoice();
        hideFragment(mFragmentTransaction);
        switch (index) {
            case 0:
                mColorIv.setImageResource(R.drawable.ic_color_pressed);
                mColorTv.setTextColor(getResources().getColor(R.color.colorMain));
                if (mColorFragment == null) {
                    mColorFragment = new ColorFragment();
                    mFragmentTransaction.add(R.id.activity_fragment_content, mColorFragment);
                } else
                    mFragmentTransaction.show(mColorFragment);
                break;

            case 1:
                mEffectsIv.setImageResource(R.drawable.ic_effects_pressed);
                mEffectsTv.setTextColor(getResources().getColor(R.color.colorMain));
                if (mEffectsFragment == null) {
                    mEffectsFragment = new EffectsFragment();
                    mFragmentTransaction.add(R.id.activity_fragment_content, mEffectsFragment);
                } else
                    mFragmentTransaction.show(mEffectsFragment);

                break;
            case 2:
                mScenesIv.setImageResource(R.drawable.ic_scenes_pressed);
                mScenesTv.setTextColor(getResources().getColor(R.color.colorMain));
                if (mScenesFragment == null) {
                    mScenesFragment = new ScenesFragment();
                    mFragmentTransaction.add(R.id.activity_fragment_content, mScenesFragment);
                } else
                    mFragmentTransaction.show(mScenesFragment);
                break;
            default:
                break;
        }
        mFragmentTransaction.commit();
    }

    /**
     * 重置所有选项
     */
    private void clearChoice() {
        mColorIv.setImageResource(R.drawable.ic_color_normal);
        mEffectsIv.setImageResource(R.drawable.ic_effects_normal);
        mScenesIv.setImageResource(R.drawable.ic_scenes_normal);
        mColorTv.setTextColor(getResources().getColor(R.color.colorGray));
        mEffectsTv.setTextColor(getResources().getColor(R.color.colorGray));
        mScenesTv.setTextColor(getResources().getColor(R.color.colorGray));
    }

    /**
     * 隐藏所有的Fragment,避免Fragment混乱
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mColorFragment != null)
            transaction.hide(mColorFragment);
        if (mEffectsFragment != null)
            transaction.hide(mEffectsFragment);
        if (mScenesFragment != null)
            transaction.hide(mScenesFragment);
    }

    private void isRightMenuShow() {
        if (mSlidingMenu.isSecondaryMenuShowing())
            mSlidingMenu.showContent();
        else
            mSlidingMenu.showSecondaryMenu();
    }

    private void isLeftMenuShow() {
        if (mSlidingMenu.isMenuShowing())
            mSlidingMenu.showContent();
        else
            mSlidingMenu.showMenu();
    }

    private long mkeyTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                Toast.makeText(this, R.string.exit_program, Toast.LENGTH_SHORT).show();
            } else {
                finish();
                StaticUtil.exitAnimation(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
