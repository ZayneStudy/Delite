package com.bluetooth.communication.conn;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.IntentFilter;

import com.bluetooth.communication.actions.Action;
import com.bluetooth.communication.actions.Config;
import com.bluetooth.communication.interfaces.ICallback;
import com.bluetooth.communication.interfaces.OnBluetoothCallBackListener;
import com.bluetooth.communication.receiver.ScanReceiver;
import com.bluetooth.communication.utils.BluetoothTransport;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-08-01 8:45
 * 修改人：Administrator
 * 修改时间：2016-08-01 8:45
 * 修改备注：
 */

public class BleConnector implements OnBluetoothCallBackListener {
    private static final String TAG = "BleConnector";
    private ICallback mICallback;
    private ScanReceiver scanReceiver;

    public BleConnector(Context mContext, BluetoothGatt mBluetoothGatt, BluetoothGattCharacteristic
            writeCharacteristic, byte[] data, ICallback mICallback) {
        scanReceiver = Config.scanReceiver;
        this.mICallback = mICallback;
        if (scanReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Action.ACTION_READ);
            intentFilter.addAction(Action.ACTION_WRITE);
            intentFilter.addAction(Action.ACTION_NOTIFY);
            mContext.registerReceiver(scanReceiver, intentFilter);
            scanReceiver.setOnBluetoothCallBackListener(this);
        }
        send(mBluetoothGatt, writeCharacteristic, data);
    }

    public BleConnector(Context mContext, BluetoothGatt mBluetoothGatt, BluetoothGattCharacteristic
            readCharacteristic, ICallback mICallback) {
        scanReceiver = Config.scanReceiver;
        this.mICallback = mICallback;
        if (scanReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Action.ACTION_READ);
            intentFilter.addAction(Action.ACTION_WRITE);
            intentFilter.addAction(Action.ACTION_NOTIFY);
            mContext.registerReceiver(scanReceiver, intentFilter);
            scanReceiver.setOnBluetoothCallBackListener(this);
        }
        send(mBluetoothGatt, readCharacteristic);
    }

    private void send(BluetoothGatt mBluetoothGatt, BluetoothGattCharacteristic
            writeCharacteristic, byte[] data) {
        BluetoothTransport.writeCharacteristic(mBluetoothGatt, writeCharacteristic, data);
    }

    private void send(BluetoothGatt mBluetoothGatt, BluetoothGattCharacteristic
            writeCharacteristic) {
        BluetoothTransport.readCharacteristic(mBluetoothGatt, writeCharacteristic);
    }

    @Override
    public void onNotify(String address, byte[] responseValue) {
        mICallback.onSuccess(address, responseValue);
    }

    @Override
    public void onWrite(String address, byte[] requestValue) {

    }

    @Override
    public void onRead(String address, byte[] responseValue) {
        mICallback.onSuccess(address, responseValue);
    }

    @Override
    public void onTimeOut(String address, byte[] requestValue) {
        mICallback.onTimeOut(address, requestValue);
    }
}
