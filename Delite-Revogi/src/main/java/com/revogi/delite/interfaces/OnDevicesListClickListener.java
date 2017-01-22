package com.revogi.delite.interfaces;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-28 20:15
 * 修改人：Administrator
 * 修改时间：2016-07-28 20:15
 * 修改备注：
 */

public interface OnDevicesListClickListener {
    void onCheckClick(int position);

    void onDeviceClick(int position);

    void onUpdateClick(int position);

    void onSwitchClick(int position);
}
