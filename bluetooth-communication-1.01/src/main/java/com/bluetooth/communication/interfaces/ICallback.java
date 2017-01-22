package com.bluetooth.communication.interfaces;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-08-01 8:46
 * 修改人：Administrator
 * 修改时间：2016-08-01 8:46
 * 修改备注：
 */

public interface ICallback {
    public void onSuccess(String address,byte[] responseValue);

    public void onTimeOut(String address,byte[] requestValue);
}
