package com.revogi.delite.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.revogi.delite.R;
import com.revogi.delite.bean.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-23 13:59
 * 修改人：Administrator
 * 修改时间：2016-07-23 13:59
 * 修改备注：
 */

public class StaticUtil {
    /**
     * 进入动画(从右进)
     *
     * @param activity
     */
    public static void enterAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 退出动画(从右出)
     *
     * @param activity
     */
    public static void exitAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

    }

    /**
     * 获取手机屏幕信息
     *
     * @param activity
     * @return
     */
    public static ArrayList<Integer> getPhoneParameter(Activity activity) {
        ArrayList<Integer> parameters = new ArrayList<>();
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        parameters.add(metrics.widthPixels);
        parameters.add(metrics.heightPixels);
        parameters.add(metrics.densityDpi);
        return parameters;
    }

    /**
     * 计算checkSum
     *
     * @param data
     * @return
     */
    public static byte[] checkSum(byte[] data) {
        int checkSum = 1;
        for (int i = 2; i < data.length - 3; i++)
            checkSum += data[i];
        data[data.length - 3] = (byte) (checkSum & 0xFF);
        return data;
    }

    /**
     * 根据address查找设备
     *
     * @param address
     * @param deviceInfos
     * @return
     */
    public static DeviceInfo getDeviceInfo(String address, List<DeviceInfo> deviceInfos) {
        for (DeviceInfo deviceInfo : deviceInfos) {
            if (address.equals(deviceInfo.getAddress())) {
                return deviceInfo;
            }
        }
        return deviceInfos.get(0);
    }

    /**
     * 查找设备在列表的第几个
     *
     * @param deviceInfo
     * @param deviceInfos
     * @return
     */
    public static int flag(DeviceInfo deviceInfo, List<DeviceInfo> deviceInfos) {
        for (int i = 0; i < deviceInfos.size(); i++) {
            DeviceInfo temDeviceInfo = deviceInfos.get(i);
            if (deviceInfo == temDeviceInfo)
                return i;
        }
        return 0;
    }
}
