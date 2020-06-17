package com.spellingtrip.example.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;


/**
 * @summary 吐司工具类
 */
public class ToastUtil {

    private static Toast toast = null;

    /**
     * 弹出吐司
     *
     * @param tip 提示的字符串
     */
    public static void show(String tip) {
        if (toast == null) {
            toast = Toast.makeText(CustomApplication.context, tip, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();
            toast = Toast.makeText(CustomApplication.context, tip, Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    /**
     * 弹出吐司
     *
     * @param resId 提示的资源id
     */
    public static void show(int resId) {
        if (toast == null) {
            toast = Toast.makeText(CustomApplication.context, resId, Toast.LENGTH_LONG);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }
    /**
     * 弹出吐司
     *
     * @param
     */
    public static void showLayout(Activity activity) {
        View toastRoot =activity.getLayoutInflater().inflate(R.layout.my_toast, null);
        TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
        if (toast==null){
             toast = new Toast(activity);
        }else {
            toast.cancel();
            toast = new Toast(activity);
        }
        toast.setView(toastRoot);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}
