package com.bluetooth.communication.utils;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import java.util.Arrays;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-20 11:46
 * 修改人：Administrator
 * 修改时间：2016-07-20 11:46
 * 修改备注：
 */

public class BluetoothTransport {
    private static final String TAG = "BluetoothTransport";

    /**
     * 向蓝牙设备写数据
     *
     * @param mBluetoothGatt
     * @param writeCharacteristic
     * @param data
     */
    public static void writeCharacteristic(BluetoothGatt mBluetoothGatt, BluetoothGattCharacteristic
            writeCharacteristic, byte[] data) {
        if (writeCharacteristic != null) {
            writeCharacteristic.setValue(data);
            mBluetoothGatt.writeCharacteristic(writeCharacteristic);
            Log.i(TAG, "write=" + Arrays.toString(data));
        }
    }

    /**
     * 向蓝牙设备读数据
     *
     * @param mBluetoothGatt
     * @param readCharacteristic
     */
    public static void readCharacteristic(BluetoothGatt mBluetoothGatt, BluetoothGattCharacteristic
            readCharacteristic) {
        mBluetoothGatt.readCharacteristic(readCharacteristic);
    }

    /**
     * 打开/关闭通知
     *
     * @param mBluetoothGatt
     * @param notifyCharacteristic
     * @param enable
     */
    public static void notifyCharacteristic(BluetoothGatt mBluetoothGatt, BluetoothGattCharacteristic
            notifyCharacteristic, boolean enable) {
        mBluetoothGatt.setCharacteristicNotification(notifyCharacteristic, enable);
    }
}
