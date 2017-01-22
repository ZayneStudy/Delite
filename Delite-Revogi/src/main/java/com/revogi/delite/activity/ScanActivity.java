package com.revogi.delite.activity;


import android.bluetooth.BluetoothGattCharacteristic;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;

import com.bluetooth.communication.conn.BleConnector;
import com.bluetooth.communication.interfaces.ICallback;
import com.bluetooth.communication.interfaces.OnScanDeviceListener;
import com.bluetooth.communication.service.BluetoothLeService;
import com.revogi.delite.R;
import com.revogi.delite.base.BaseActivity;
import com.revogi.delite.bean.DeviceInfo;
import com.revogi.delite.utils.BluetoothSendData;
import com.revogi.delite.utils.StaticUtil;
import com.revogi.delite.view.ScanView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-14 11:43
 * 修改人：Administrator
 * 修改时间：2016-07-14 11:43
 * 修改备注：
 */
public class ScanActivity extends BaseActivity implements OnScanDeviceListener {
    private final String TAG = this.getClass().getSimpleName();
    private ScanView mScanView;
    private BluetoothLeService.Binder bluetoothLeBinder;
    private Handler mHandler = new Handler();
    public static List<DeviceInfo> mDeviceInfos = new ArrayList<>();
    private static final String bluetoothDeviceName = "Lite1100";
    /**
     * 最多能连接多少个蓝牙设备
     */
    private static final int bluetoothConnectNmuber = 10;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        mScanView = (ScanView) findViewById(R.id.scan_view);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void inits() {
        Intent intent = new Intent(this, BluetoothLeService.class);
        bindService(intent, scanServiceConnection, BIND_AUTO_CREATE);
        mHandler.postDelayed(startMainActivityRunnable, 10000);
    }

    @Override
    public void onClick(View v) {

    }

//    @Override
//    public void notify(String address, byte[] value) {
//
//    }
//
//    @Override
//    public void write(String address, byte[] value) {
//    }
//
//    @Override
//    public void read(String address, byte[] value) {
//        DeviceInfo deviceInfo = StaticUtil.getDeviceInfo(address, mDeviceInfos);
//        try {
//            deviceInfo.setName(new String(value, "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void device(com.bluetooth.communication.bean.DeviceInfo deviceInfo) {
        DeviceInfo info = new DeviceInfo();
        info.setName(deviceInfo.getBluetoothName());
        info.setAddress(deviceInfo.getAddress());
        info.setBluetoothGatt(deviceInfo.getBluetoothGatt());
        displayGattServices(deviceInfo.getUuids(), deviceInfo.getBluetoothGattCharacteristics(), info);
        mDeviceInfos.add(info);
        mScanView.addPoint();
    }

    private ServiceConnection scanServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            List<String> bluetoothDeviceNames = new ArrayList<>();
            bluetoothDeviceNames.add(bluetoothDeviceName);
            bluetoothLeBinder = (BluetoothLeService.Binder) service;
            bluetoothLeBinder.scan(ScanActivity.this, bluetoothDeviceNames, bluetoothConnectNmuber);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };


    /**
     * 获取对应设备的服务
     *
     * @param mUuid
     * @param mBluetoothGattCharacteristics
     * @param mInfo
     */
    private void displayGattServices(List<Integer> mUuid, List<BluetoothGattCharacteristic>
            mBluetoothGattCharacteristics, DeviceInfo mInfo) {
        for (int i = 0; i < mUuid.size(); i++) {
            switch (mUuid.get(i)) {
                case 0xFFF3:
                    mInfo.setWriteCharacteristic(mBluetoothGattCharacteristics.get(i));
                    break;
                case 0x2A00:
                    mInfo.setReadNameCharacteristic(mBluetoothGattCharacteristics.get(i));
                    BluetoothSendData.read(mInfo, mInfo.getReadNameCharacteristic());
                    BleConnector bleConnector = new BleConnector(this, mInfo.getBluetoothGatt(), mInfo
                            .getReadNameCharacteristic(), new ICallback() {


                        @Override
                        public void onSuccess(String address, byte[] responseValue) {
                            DeviceInfo deviceInfo = StaticUtil.getDeviceInfo(address, mDeviceInfos);
                            try {
                                deviceInfo.setName(new String(responseValue, "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onTimeOut(String address, byte[] requestValue) {

                        }
                    });
                    break;
                case 0xFFF4:
                    mInfo.setNotifyCharacteristic(mBluetoothGattCharacteristics.get(i));
                    BluetoothSendData.notify(mInfo); //打开通知功能，设备才能返回数据
                    break;
            }
        }
    }

    Runnable startMainActivityRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(ScanActivity.this, MyFragmentActivity.class);
            startActivity(intent);
            StaticUtil.enterAnimation(ScanActivity.this);
        }
    };

}
