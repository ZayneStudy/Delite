package com.revogi.delite.bean;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;


/**
 * 类描述：设备信息
 * 创建人：ZZ
 * 创建时间：2016-07-19 16:36
 * 修改人：Administrator
 * 修改时间：2016-07-19 16:36
 * 修改备注：
 */

public class DeviceInfo {
    private String bluetoothName;
    private String address;
    private BluetoothGattCharacteristic writeCharacteristic;
    private BluetoothGattCharacteristic readNameCharacteristic;
    private BluetoothGattCharacteristic notifyCharacteristic;
    private BluetoothGatt mBluetoothGatt;
    private String name;
    private int color;
    /**
     * 0：彩色模式 1：色温模式2：万花筒模式 3狂闪模式4:蜡烛模式 5:太阳光模式 6：disco模式 7:show模式 8:shake模式
     */
    private int mode;
    /**
     * 0彩色灯 1色温灯 2蜡烛灯 3灯带
     */
    private int style;

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

    public BluetoothGattCharacteristic getWriteCharacteristic() {
        return writeCharacteristic;
    }

    public void setWriteCharacteristic(BluetoothGattCharacteristic writeCharacteristic) {
        this.writeCharacteristic = writeCharacteristic;
    }

    public BluetoothGattCharacteristic getReadNameCharacteristic() {
        return readNameCharacteristic;
    }

    public void setReadNameCharacteristic(BluetoothGattCharacteristic readNameCharacteristic) {
        this.readNameCharacteristic = readNameCharacteristic;
    }

    public BluetoothGattCharacteristic getNotifyCharacteristic() {
        return notifyCharacteristic;
    }

    public void setNotifyCharacteristic(BluetoothGattCharacteristic notifyCharacteristic) {
        this.notifyCharacteristic = notifyCharacteristic;
    }

    public BluetoothGatt getBluetoothGatt() {
        return mBluetoothGatt;
    }

    public void setBluetoothGatt(BluetoothGatt bluetoothGatt) {
        mBluetoothGatt = bluetoothGatt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", mode=" + mode +
                ", style=" + style +
                '}';
    }
}
