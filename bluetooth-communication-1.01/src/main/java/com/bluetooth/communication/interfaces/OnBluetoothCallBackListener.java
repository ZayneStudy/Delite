package com.bluetooth.communication.interfaces;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-22 16:59
 * 修改人：Administrator
 * 修改时间：2016-07-22 16:59
 * 修改备注：
 */

public interface OnBluetoothCallBackListener {
    /**
     * 写返回的值
     *
     * @param address
     * @param responseValue
     */
    void onNotify(String address, byte[] responseValue);

    /**
     * 写成功发送的值
     *
     * @param address
     * @param responseValue
     */
    void onWrite(String address, byte[] responseValue);

    /**
     * 读返回的值
     *
     * @param address
     * @param responseValue
     */
    void onRead(String address, byte[] responseValue);

    void onTimeOut(String address, byte[] requestValue);
}
