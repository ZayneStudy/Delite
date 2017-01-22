package com.bluetooth.communication.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 类描述：
 * 创建人：ZZ
 * 创建时间：2016-07-25 18:01
 * 修改人：Administrator
 * 修改时间：2016-07-25 18:01
 * 修改备注：
 */

public class StaticUtil {
    public static void showToast(Context context, int string) {
        Toast.makeText(context, context.getResources().getString(string), Toast.LENGTH_SHORT).show();
    }
}
