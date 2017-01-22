package com.revogi.delite.interfaces;

import com.bluetooth.communication.bean.DeviceInfo;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-20 11:40
 * 修改人：Administrator
 * 修改时间：2016-07-20 11:40
 * 修改备注：
 */

public interface OnDeviceListener {
    void device(DeviceInfo deviceInfo);
}
