package com.revogi.delite.utils;

import android.bluetooth.BluetoothGattCharacteristic;

import com.bluetooth.communication.utils.BluetoothTransport;
import com.revogi.delite.bean.DeviceInfo;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-20 11:46
 * 修改人：Administrator
 * 修改时间：2016-07-20 11:46
 * 修改备注：
 */

public class BluetoothSendData {
    private static final String TAG = "BluetoothSendData";

    /**
     * 向蓝牙设备写数据
     *
     * @param deviceInfo
     * @param data
     */
    public static void write(DeviceInfo deviceInfo, byte[] data) {
        BluetoothTransport.writeCharacteristic(deviceInfo.getBluetoothGatt(), deviceInfo.getWriteCharacteristic(),
                data);
    }

    /**
     * 向蓝牙设备读数据
     *
     * @param deviceInfo
     * @param readCharacteristic
     */
    public static void read(DeviceInfo deviceInfo, BluetoothGattCharacteristic
            readCharacteristic) {
        BluetoothTransport.readCharacteristic(deviceInfo.getBluetoothGatt(), readCharacteristic);
    }

    /**
     * 打开通知
     *
     * @param deviceInfo
     */
    public static void notify(DeviceInfo deviceInfo) {
        BluetoothTransport.notifyCharacteristic(deviceInfo.getBluetoothGatt(), deviceInfo.getNotifyCharacteristic(),
                true);
    }
}
