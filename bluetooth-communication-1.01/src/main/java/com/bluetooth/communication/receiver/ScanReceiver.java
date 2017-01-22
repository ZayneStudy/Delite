package com.bluetooth.communication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bluetooth.communication.actions.Action;
import com.bluetooth.communication.interfaces.OnBluetoothCallBackListener;

import java.util.Arrays;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-22 10:26
 * 修改人：Administrator
 * 修改时间：2016-07-22 10:26
 * 修改备注：
 */

public class ScanReceiver extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();
    private OnBluetoothCallBackListener mOnBluetoothCallBackListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String address = bundle.getString(Action.ACTION_BLUETOOTH_ADDRESS);
        byte[] value = bundle.getByteArray(Action.ACTION_CALL_BACK_VALUE);
        Log.e(TAG, mOnBluetoothCallBackListener + "");
        if (mOnBluetoothCallBackListener != null) {
            if (Action.ACTION_READ.equals(intent.getAction())) {
                mOnBluetoothCallBackListener.onRead(address, value);
                Log.i(TAG, "readCallBackValue=" + printBundle(address, value));
            } else if (Action.ACTION_WRITE.equals(intent.getAction())) {
                mOnBluetoothCallBackListener.onWrite(address, value);
                Log.i(TAG, "writeSuccess=" + printBundle(address, value));
            } else if (Action.ACTION_NOTIFY.equals(intent.getAction())) {
                mOnBluetoothCallBackListener.onNotify(address, value);
                Log.i(TAG, "notifyCallBackValue=" + printBundle(address, value));
            }
        }
    }

    public void setOnBluetoothCallBackListener(OnBluetoothCallBackListener mOnBluetoothCallBackListener) {
        this.mOnBluetoothCallBackListener = mOnBluetoothCallBackListener;
    }

    private String printBundle(String address, byte[] value) {
        return "[address=" + address + ";value=" + Arrays.toString(value) + "]";
    }
}
