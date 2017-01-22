package com.bluetooth.communication.interfaces;

import com.bluetooth.communication.bean.DeviceInfo;

/**
 * 类描述：扫描到的设备
 * 创建人：ZZ
 * 创建时间：2016-07-20 15:00
 * 修改人：Administrator
 * 修改时间：2016-07-20 15:00
 * 修改备注：
 */

public interface OnScanDeviceListener {
     void device(DeviceInfo deviceInfo);
}
