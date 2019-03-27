package com.tj.graduation.travel.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * Created by wangsong on 2019/3/9.
 */

public class ToastUtil {

    private static Toast toast;

    public static void showToastText(Context context, String text) {

        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);

        } else {
            toast.setText(text);
        }
        toast.show();

    }
    public static void showToastTextLong(Context context, String text) {

        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);

        } else {
            toast.setText(text);
        }
        toast.show();

    }

}
