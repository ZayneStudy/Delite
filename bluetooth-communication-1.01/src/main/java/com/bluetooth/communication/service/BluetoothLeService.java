package com.bluetooth.communication.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.bluetooth.communication.actions.Action;
import com.bluetooth.communication.bean.DeviceInfo;
import com.bluetooth.communication.interfaces.OnScanDeviceListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述： 扫描指定的蓝牙设备及蓝牙服务
 * 创建人：ZZ
 * 创建时间：2016-07-14 11:43
 * 修改人：Administrator
 * 修改时间：2016-07-14 11:43
 * 修改备注：
 */

public class BluetoothLeService extends Service {
    private final String TAG = this.getClass().getSimpleName();
    private Binder mBinder = new Binder();
    private List<DeviceInfo> mDeviceInfos = new ArrayList<>();
    private static OnScanDeviceListener mOnScanDeviceListener;
    /**
     * 扫描时间
     */
    private static int SCAN_SECOND_MILLIS = 5000;
    /**
     * 每次扫描的间隔时间
     */
    private static int SCAN_SPACE_SECOND_MILLIS = 60000;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "OnCreate");

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class Binder extends android.os.Binder {
        private BluetoothManager mBluetoothManager;
        private BluetoothAdapter mBluetoothAdapter;
        private Context mContext;
        private Handler mHandler = new Handler();
        /**
         * 最多能连接多少个蓝牙设备
         */
        private int bluetoothConnectNmuber;
        /**
         * 需要匹配的蓝牙设备广播名
         */
        private List<String> mBluetoothDeviceNames;

        public void scan(OnScanDeviceListener mOnScanDeviceListener, List<String> mBluetoothDeviceNames, int
                bluetoothConnectNmuber) {
            BluetoothLeService.mOnScanDeviceListener = mOnScanDeviceListener;
            Log.e(TAG,"111111111111"+BluetoothLeService.mOnScanDeviceListener);
            this.mBluetoothDeviceNames = mBluetoothDeviceNames;
            this.bluetoothConnectNmuber = bluetoothConnectNmuber;
            mContext = getApplicationContext();
            mBluetoothManager = (BluetoothManager) getSystemService(mContext.BLUETOOTH_SERVICE);
            mBluetoothAdapter = mBluetoothManager.getAdapter();
            mHandler.post(startScanRunnable);
        }

        private final Runnable startScanRunnable = new Runnable() {
            @Override
            public void run() {
                mBluetoothAdapter.startLeScan(mLeScanCallback);
                mHandler.postDelayed(stopScanRunnable, SCAN_SECOND_MILLIS);
            }
        };
        private final Runnable stopScanRunnable = new Runnable() {
            @Override
            public void run() {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                mHandler.postDelayed(startScanRunnable, SCAN_SPACE_SECOND_MILLIS);
            }
        };
        private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                if (!isExist(device.getAddress()) && mDeviceInfos.size() < bluetoothConnectNmuber) {
                    for (String bluetoothDeviceName : mBluetoothDeviceNames) {
                        if (bluetoothDeviceName.equals(device.getName())) {
                            Log.i(TAG, "Find new device:" + device.getName());
                            DeviceInfo deviceInfo = new DeviceInfo();
                            deviceInfo.setBluetoothName(device.getName());
                            deviceInfo.setAddress(device.getAddress());
                            deviceInfo.setBluetoothGatt(device.connectGatt(getApplicationContext(), true,
                                    mBluetoothGattCallback));
                            mDeviceInfos.add(deviceInfo);
                            break;
                        }
                    }
                }
            }
        };

        /**
         * 扫描到的蓝牙设备是否已经存在
         *
         * @param address
         * @return
         */
        private boolean isExist(String address) {
            for (DeviceInfo deviceInfo : mDeviceInfos) {
                if (address.equalsIgnoreCase(deviceInfo.getAddress()))
                    return true;
            }
            return false;
        }
    }

    private final BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.i(TAG, "onConnectionStateChange" + "      newState=" + newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt.discoverServices();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.i(TAG, "onServicesDiscovered");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                displayGattServices(gatt);
            }
        }

        /**
         * 获取对应设备的服务
         *
         * @param gatt
         */
        private void displayGattServices(BluetoothGatt gatt) {
            for (DeviceInfo deviceInfo : mDeviceInfos) {
                if (gatt.equals(deviceInfo.getBluetoothGatt())) { //找到对应的设备
                    List<Integer> mUuids = new ArrayList<>();
                    List<BluetoothGattCharacteristic> mBluetoothGattCharacteristics = new ArrayList<>();
                    List<BluetoothGattService> gattServices = gatt.getServices();
                    for (BluetoothGattService gattService : gattServices) {
                        List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                        for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                            int uuid = (int) gattCharacteristic.getUuid().timestamp();
                            mUuids.add(uuid);
                            mBluetoothGattCharacteristics.add(gattCharacteristic);
                        }
                    }
                    deviceInfo.setUuids(mUuids);
                    deviceInfo.setBluetoothGattCharacteristics(mBluetoothGattCharacteristics);
                    Log.e(TAG,"222222222222222"+BluetoothLeService.mOnScanDeviceListener);
                    if (mOnScanDeviceListener != null){
                        mOnScanDeviceListener.device(deviceInfo);
                        Log.e(TAG,"333333333331");}
                    break;
                }
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int
                status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.i(TAG, "onCharacteristicRead");
            Intent intent = new Intent();
            intent.setAction(Action.ACTION_READ);
            Bundle bundle = new Bundle();
            bundle.putByteArray(Action.ACTION_CALL_BACK_VALUE, characteristic.getValue());
            bundle.putString(Action.ACTION_BLUETOOTH_ADDRESS, gatt.getDevice().getAddress());
            intent.putExtras(bundle);
            sendBroadcast(intent);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int
                status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.i(TAG, "onCharacteristicWrite");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Intent intent = new Intent();
                intent.setAction(Action.ACTION_WRITE);
                Bundle bundle = new Bundle();
                bundle.putByteArray(Action.ACTION_CALL_BACK_VALUE, characteristic.getValue());
                bundle.putString(Action.ACTION_BLUETOOTH_ADDRESS, gatt.getDevice().getAddress());
                intent.putExtras(bundle);
                sendBroadcast(intent);
            } else
                Log.i(TAG, "onCharacteristicWriteError");

        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.i(TAG, "onCharacteristicChanged");
            Intent intent = new Intent();
            intent.setAction(Action.ACTION_NOTIFY);
            Bundle bundle = new Bundle();
            bundle.putByteArray(Action.ACTION_CALL_BACK_VALUE, characteristic.getValue());
            bundle.putString(Action.ACTION_BLUETOOTH_ADDRESS, gatt.getDevice().getAddress());
            intent.putExtras(bundle);
            sendBroadcast(intent);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    };

}

