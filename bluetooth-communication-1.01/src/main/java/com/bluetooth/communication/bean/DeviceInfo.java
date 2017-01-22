package com.bluetooth.communication.bean;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：设备信息
 * 创建人：ZZ
 * 创建时间：2016-07-19 16:36
 * 修改人：Administrator
 * 修改时间：2016-07-19 16:36
 * 修改备注：
 */

public class DeviceInfo implements Serializable {
    private String bluetoothName;
    private String address;
    private BluetoothGatt mBluetoothGatt;
    private List<BluetoothGattCharacteristic> mBluetoothGattCharacteristics;
    private List<Integer> mUuids;


    public String getBluetoothName() {
        return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BluetoothGatt getBluetoothGatt() {
        return mBluetoothGatt;
    }

    public void setBluetoothGatt(BluetoothGatt bluetoothGatt) {
        mBluetoothGatt = bluetoothGatt;
    }

    public List<BluetoothGattCharacteristic> getBluetoothGattCharacteristics() {
        return mBluetoothGattCharacteristics;
    }

    public void setBluetoothGattCharacteristics(List<BluetoothGattCharacteristic> bluetoothGattCharacteristics) {
        mBluetoothGattCharacteristics = bluetoothGattCharacteristics;
    }

    public List<Integer> getUuids() {
        return mUuids;
    }

    public void setUuids(List<Integer> uuids) {
        mUuids = uuids;
    }
}
