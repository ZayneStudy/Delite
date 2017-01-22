package com.revogi.delite.base;

import com.bluetooth.communication.utils.BluetoothInit;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-25 17:49
 * 修改人：Administrator
 * 修改时间：2016-07-25 17:49
 * 修改备注：
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BluetoothInit bluetoothInit = new BluetoothInit();
        bluetoothInit.initBle(this);
    }
}
