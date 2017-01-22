package com.revogi.delite.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluetooth.communication.conn.BleConnector;
import com.bluetooth.communication.interfaces.ICallback;
import com.revogi.delite.R;
import com.revogi.delite.action.Cmd;
import com.revogi.delite.activity.ScanActivity;
import com.revogi.delite.adapter.DevicesListAdapter;
import com.revogi.delite.base.BaseFragment;
import com.revogi.delite.bean.DeviceInfo;
import com.revogi.delite.interfaces.OnDevicesListClickListener;
import com.revogi.delite.interfaces.OnRecycleItemClickListener;
import com.revogi.delite.interfaces.OnRecycleItemLongClickListener;
import com.revogi.delite.utils.SendDataProtocol;
import com.revogi.delite.utils.StaticUtil;

import java.util.List;

import static com.revogi.delite.R.layout.fragment_devices_list;

/**
 * 类描述：左划出来的设备列表界面
 * 创建人：ZZ
 * 创建时间：2016-07-20 10:03
 * 修改人：Administrator
 * 修改时间：2016-07-20 10:03
 * 修改备注：
 */

public class DevicesListFragment extends BaseFragment implements OnRecycleItemClickListener,
        OnRecycleItemLongClickListener, OnDevicesListClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView devicesRv;
    private List<DeviceInfo> mDeviceInfos;
    private DevicesListAdapter mDevicesListAdapter;
    private String[] devAddress;

    @Override
    protected View getLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(fragment_devices_list, container, false);
    }

    @Override
    protected void initViews(View view) {
        initTitleLayout(view);
        devicesRv = (RecyclerView) view.findViewById(R.id.devices_list);

    }

    @Override
    protected void initEvents() {
        mDeviceInfos = ScanActivity.mDeviceInfos;
        devAddress = new String[mDeviceInfos.size()];
        for (int i = 0; i < mDeviceInfos.size(); i++) {
            devAddress[i] = mDeviceInfos.get(i).getName();
        }
        devicesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDevicesListAdapter = new DevicesListAdapter(getActivity(), mDeviceInfos);
        devicesRv.setAdapter(mDevicesListAdapter);
        mDevicesListAdapter.setOnRecycleItemClickListener(this);
        mDevicesListAdapter.setOnRecycleItemLongClickListener(this);
        mDevicesListAdapter.setOnDevicesListClickListener(this);
    }

    @Override
    protected void inits() {
        mTitleLeftBt.setVisibility(View.INVISIBLE);
        mTitleRightBt.setBackgroundResource(R.drawable.ic_setting);
        mTitleNameIv.setImageResource(R.drawable.ic_frame_left_logo);
        mTitleRl.setBackgroundColor(getResources().getColor(R.color.colorBlack));
        for (DeviceInfo deviceInfo : mDeviceInfos) {
        /*     //    BluetoothSendData.write(deviceInfo, SendDataProtocol.setTime());
            BluetoothSendData.write(deviceInfo, SendDataProtocol.getStatus());*/
            BleConnector bleConnector = new BleConnector(getActivity(), deviceInfo.getBluetoothGatt(), deviceInfo
                    .getWriteCharacteristic(), SendDataProtocol.getStatus(), (new ICallback() {


                @Override
                public void onSuccess(String address, byte[] responseValue) {
                    if (responseValue[2] == Cmd.CMD_GET_STATUS) {
                        int color = Color.rgb(responseValue[4] & 0xFF, responseValue[5] & 0xFF, responseValue[6] &
                                0xFF);
                        DeviceInfo deviceInfo = StaticUtil.getDeviceInfo(address, mDeviceInfos);
                        deviceInfo.setColor(color);
                        mDevicesListAdapter.notifyItemChanged(StaticUtil.flag(deviceInfo, mDeviceInfos));

                    }
                }

                @Override
                public void onTimeOut(String address, byte[] requestValue) {

                }
            }));
        }

    }

    @Override
    public void onClick(View v) {
     /*   if (v == mTitleRightBt) {
            for (DeviceInfo deviceInfo : mDeviceInfos) {
                BluetoothSendData.write(deviceInfo, SendDataProtocol.flicker());
            }
        }*/
/*        for (DeviceInfo deviceInfo : mDeviceInfos) {
            BleConnector mBleConnector = new BleConnector(getActivity(), deviceInfo.getBluetoothGatt(), deviceInfo
                    .getWriteCharacteristic(), SendDataProtocol.flicker(), new ICallback() {
                @Override
                public void onSuccess(byte[] date) {

                }

                @Override
                public void onFailure() {

                }
            });
        }*/
    }


    @Override
    public void onRecycleItemClick(int position) {

    }

    @Override
    public void recycleItemLongClick(int position) {

    }

    @Override
    public void onCheckClick(int position) {

    }

    @Override
    public void onDeviceClick(int position) {

    }

    @Override
    public void onUpdateClick(int position) {

    }

    @Override
    public void onSwitchClick(int position) {

    }
}
