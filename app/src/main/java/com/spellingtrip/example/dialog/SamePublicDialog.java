package com.spellingtrip.example.dialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.MainActivity;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.PublishPinActivity;
import com.spellingtrip.example.activity.SamePublicActivity;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.MapUtil;

/**
 * date:2020/5/7
 * author:王思敏
 * function同城活动页弹框
 */
public class SamePublicDialog {
    private static PopupWindow popupWindow;
    private static String TAG = "CommonDialog";


    /**
     * 是否建立群聊/申请加入方式按钮弹框
     */
    public static void groupandway(final Activity activity , String name,final OnButtonClick onButtonClick) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(activity).inflate(R.layout.group_way, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 10);
//        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        //设置PopupWindow的View点击事件
        setPublicPin(activity, view,name,onButtonClick);
        //设置背景色
        setBackgroundAlpha(activity, 0.5f);
    }

    private static void setPublicPin(Activity activity, View view,String name,final OnButtonClick onButtonClick) {

        TextView gw_one = view.findViewById(R.id.gw_one);
        TextView gw_two = view.findViewById(R.id.gw_two);
        TextView cancel = view.findViewById(R.id.cancel);
        if (name !=null){
            if (name.equals("group")){
                gw_one.setText("是");
                gw_two.setText("否");
            }else if (name.equals("way")){
                gw_one.setText("申请加入");
                gw_two.setText("审核加入");
            }
        }
        gw_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //个人发布
                onButtonClick.button01ClickListener();
                popupWindow.dismiss();
            }
        });
        //商家发布
        gw_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick.button02ClickListener();
                popupWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }


    /**
     * 同城发布阅读
     */
    public static void publicread(final Activity activity) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(CustomApplication.context).inflate(R.layout.public_read, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 10);

        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        //设置背景色
        setBackgroundAlpha(activity, 0.5f);
    }

    /**
     * 同城发布阅读
     */
    public static void publiNg(final Activity activity ,String Latitude1,String Longtitude1,String Location) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(CustomApplication.context).inflate(R.layout.public_navigation, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 10);

        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        setMapButton(activity,view, Latitude1,Longtitude1,Location);
        //设置背景色
        setBackgroundAlpha(activity, 0.5f);
    }

    private static void setMapButton(Activity activity,View view , String latitude1, String longtitude1, String location) {
        TextView gaode_map = view.findViewById(R.id.gaode_map);
        TextView baidu_map = view.findViewById(R.id.baidu_map);
        TextView tencent_map = view.findViewById(R.id.tencent_map);
        gaode_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MapUtil.isGdMapInstalled()) {
                    MapUtil.openGaoDeNavi(activity, 0, 0, null, latitude1, longtitude1, location);
                } else {
                    //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                    Toast.makeText(activity, "尚未安装高德地图", Toast.LENGTH_SHORT).show();
                }
            }
        });
        baidu_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MapUtil.isBaiduMapInstalled()){
                    MapUtil.openBaiDuNavi(activity, 0, 0, null, latitude1, longtitude1, location);
                } else {
                    Toast.makeText(activity, "尚未安装百度地图", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tencent_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MapUtil.isTencentMapInstalled()){
                    MapUtil.openTencentMap(activity, 0, 0, null, latitude1, longtitude1, location);
                } else {
                    Toast.makeText(activity, "尚未安装腾讯地图", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


















    //设置屏幕背景透明效果
    public static void setBackgroundAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }
}
