package com.revogi.delite.utils;

import java.util.Calendar;

/**
 * 类描述：发送数据包
 * 创建人：ZZ
 * 创建时间：2016-07-28 14:25
 * 修改人：Administrator
 * 修改时间：2016-07-28 14:25
 * 修改备注：
 */

public class SendDataProtocol {
    /**
     * 万年历校正时间
     *
     * @return
     */
    public static byte[] setTime() {
        Calendar cal = Calendar.getInstance();
        byte time[] = {0x0F, 0x0C, 0x01, 0x00,
                (byte) cal.get(Calendar.SECOND),
                (byte) cal.get(Calendar.MINUTE),
                (byte) cal.get(Calendar.HOUR_OF_DAY),
                (byte) cal.get(Calendar.DAY_OF_MONTH),
                (byte) (cal.get(Calendar.MONTH) + 1),
                (byte) (cal.get(Calendar.YEAR) / 0x100),
                (byte) (cal.get(Calendar.YEAR) % 0x100),
                0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF};
        return StaticUtil.checkSum(time);
    }

    /**
     * 获取设备状态(颜色，亮度，模式,NFC......)
     *
     * @return
     */
    public static byte[] getStatus() {
        byte status[] = {0x0F, 0x05, 0x04, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF};
        return StaticUtil.checkSum(status);
    }

    /**
     * 闪烁提醒
     *
     * @return
     */
    public static byte[] flicker() {
        byte flicker[] = {0x0F, 0x0C, 0x0C, 0x00, 0x06, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)
                0xFF, (byte) 0xFF};
        return StaticUtil.checkSum(flicker);
    }
}
