package com.bluetooth.communication.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;

import com.bluetooth.communication.R;

/**
 * 类描述：判断手机支不支持蓝牙4.0
 * 创建人：ZZ
 * 创建时间：2016-07-25 17:51
 * 修改人：Administrator
 * 修改时间：2016-07-25 17:51
 * 修改备注：
 */

public class BluetoothInit {
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;

    public void initBle(Context context) {
        mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (mBluetoothManager == null) {
            StaticUtil.showToast(context, R.string.bluetooth_not_available);
            return;
        }
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            StaticUtil.showToast(context, R.string.bluetooth_not_available);
            return;
        }
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            StaticUtil.showToast(context, R.string.not_support_bluetooth);
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
            StaticUtil.showToast(context, R.string.bluetooth_enable);
            return;
        }

    }


}
