package com.revogi.delite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.revogi.delite.R;
import com.revogi.delite.bean.DeviceInfo;
import com.revogi.delite.interfaces.OnDevicesListClickListener;
import com.revogi.delite.interfaces.OnRecycleItemClickListener;
import com.revogi.delite.interfaces.OnRecycleItemLongClickListener;

import java.util.List;


/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-27 9:15
 * 修改人：Administrator
 * 修改时间：2016-07-27 9:15
 * 修改备注：
 */

public class DevicesListAdapter extends RecyclerView.Adapter<DevicesListAdapter.DevicesViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private List<DeviceInfo> mDeviceInfos;
    private OnRecycleItemClickListener mOnRecycleItemClickListener;
    private OnRecycleItemLongClickListener mOnRecycleItemLongClickListener;
    private OnDevicesListClickListener mOnDevicesListClickListener;

    public DevicesListAdapter(Context context, List<DeviceInfo> deviceInfos) {
        this.mContext = context;
        this.mDeviceInfos = deviceInfos;
    }

    @Override
    public DevicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DevicesViewHolder holder = new DevicesViewHolder(LayoutInflater.from(mContext).inflate(R.layout
                .item_devices_list, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(DevicesViewHolder holder, int position) {
        holder.position = position;
        DeviceInfo deviceInfo = mDeviceInfos.get(position);
        holder.nameTv.setText(deviceInfo.getName());
        holder.deviceIv.setBackgroundColor(deviceInfo.getColor());
    }

    @Override
    public int getItemCount() {
        return mDeviceInfos.size();
    }


    class DevicesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private RelativeLayout devicesRl;
        private CheckBox checkCb, switchCb;
        private ImageView deviceIv;
        private TextView nameTv;
        private int position;

        private DevicesViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
            initEvents(itemView);
        }

        private void initViews(View itemView) {
            devicesRl = (RelativeLayout) itemView.findViewById(R.id.item_devices);
            checkCb = (CheckBox) itemView.findViewById(R.id.item_devices_check);
            deviceIv = (ImageView) itemView.findViewById(R.id.item_devices_device);
            switchCb = (CheckBox) itemView.findViewById(R.id.item_devices_switch);
            nameTv = (TextView) itemView.findViewById(R.id.item_devices_name);
        }

        private void initEvents(View itemView) {
            devicesRl.setOnClickListener(this);
            devicesRl.setOnLongClickListener(this);
            deviceIv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == devicesRl && mOnRecycleItemClickListener != null) {
                mOnRecycleItemClickListener.onRecycleItemClick(position);
            } else if (mOnDevicesListClickListener != null) {
                if (v == deviceIv)
                    mOnDevicesListClickListener.onDeviceClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    }

    public void setOnRecycleItemClickListener(OnRecycleItemClickListener onRecycleItemClickListener) {
        this.mOnRecycleItemClickListener = onRecycleItemClickListener;
    }

    public void setOnRecycleItemLongClickListener(OnRecycleItemLongClickListener onRecycleItemLongClickListener) {
        this.mOnRecycleItemLongClickListener = onRecycleItemLongClickListener;
    }

    public void setOnDevicesListClickListener(OnDevicesListClickListener onDevicesListClickListener) {
        this.mOnDevicesListClickListener = onDevicesListClickListener;
    }
}
