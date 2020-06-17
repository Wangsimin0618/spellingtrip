package com.spellingtrip.example.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.MainActivity;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.BuyVIPActivity;
import com.spellingtrip.example.activity.JieBanActivityDetailActivity;
import com.spellingtrip.example.activity.JieBaseActivity;
import com.spellingtrip.example.activity.LogineActivity;
import com.spellingtrip.example.activity.MerchantActivity;
import com.spellingtrip.example.activity.PublishPinActivity;
import com.spellingtrip.example.activity.ReportActivity;
import com.spellingtrip.example.activity.SamePublicActivity;
import com.spellingtrip.example.activity.SetInfoActivity;
import com.spellingtrip.example.activity.SplashActivity;
import com.spellingtrip.example.bean.BlackBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.DataBean;
import com.spellingtrip.example.bean.HomePiPeiBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.SignListBean;
import com.spellingtrip.example.bean.UserBean;
import com.spellingtrip.example.bean.UserInfoBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CameraUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.PayUtils;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.Rotate3dAnimation;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ShapedImageView;
import com.spellingtrip.example.viewpager.SignListAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;


/**
 */

public class CommonDialog {
    private static PopupWindow popupWindow;
    private static String TAG = "CommonDialog";
    private static boolean onclick = false;
    private static boolean onpay = false;
    /**
     * 通用提示框
     *
     * @param context       上下文
     * @param title         提示标题
     * @param tipMessage    提示信息
     * @param button1Text   按钮文本
     * @param button2Text   按钮文本
     * @param onButtonClick 回掉接口
     * @return
     */
    public static AlertDialog getDialog(final Activity context, String title, String tipMessage, String button1Text,
                                        String button2Text, final OnButtonClick onButtonClick) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.loading_dialog);
        View view = View.inflate(context, R.layout.common_dialog, null);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.rlcommon);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setText(button1Text);
        TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        tv_ok.setText(button2Text);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText(tipMessage);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick.button01ClickListener();
                dialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick.button02ClickListener();
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.8);
        dialog.getWindow().setAttributes(p);
        return dialog;
    }

    /**
     * 通用提示框
     *
     * @param context       上下文
     * @param onButtonClick 回掉接口
     * @return
     */
    public static AlertDialog getAddLableDialog(final Activity context, final OnLableButtonClick onButtonClick) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.loading_dialog);
        View view = View.inflate(context, R.layout.addlable_dialog, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        EditText etAddLableDialog = view.findViewById(R.id.etAddLableDialog);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick.button01ClickListener();
                dialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lable = etAddLableDialog.getText().toString().trim();
                if (!TextUtils.isEmpty(lable)) {
                    onButtonClick.button02ClickListener(lable);
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.8);
        dialog.getWindow().setAttributes(p);
        return dialog;
    }

    /**
     * 底部弹出框
     */
    public static void openPopupWindow(final Activity activity, View v, int userid) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(activity).inflate(R.layout.view_popupwindow, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 10);
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(activity, view, userid);
        //设置背景色
        setBackgroundAlpha(activity, 0.5f);
    }

    //设置屏幕背景透明效果
    public static void setBackgroundAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }

    //游圈更多弹框操作
    private static void setOnPopupViewClick(final Activity activity, View view, final int userid) {
        TextView tv_pick_ju, tv_pick_hei, tv_cancel;
        tv_pick_ju = view.findViewById(R.id.tv_pick_ju);
        tv_pick_hei = view.findViewById(R.id.tv_pick_hei);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_pick_ju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity, ReportActivity.class, 0, "");
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        tv_pick_hei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserTask.getInstance().isLogin()) {
                    setAddBlack(activity, userid);
                } else {
                    ActivityUtils.skipActivity(activity, LogineActivity.class, 0, "");
                }


            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
    }

    /**
     * 首页更多弹框
     */
    public static void homeitemPopupWindow(final Activity activity, View v ,int userid,final int postion) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(CustomApplication.context).inflate(R.layout.home_item_popwindow, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 10);
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        //设置PopupWindow的View点击事件
        setHomeitemmore(activity, view, userid,postion);
        //设置背景色
        setBackgroundAlpha(activity, 0.5f);
    }

    //首页更多弹框操作
    private static void setHomeitemmore(final Activity activity, View v ,int userid,final int postion) {
        TextView homeitemattention, homeitemshare, homeitemreport,homeitemcancel;
        homeitemattention = v.findViewById(R.id.home_item_attention);
        homeitemshare = v.findViewById(R.id.home_item_share);
        homeitemreport = v.findViewById(R.id.home_item_report);
        homeitemcancel = v.findViewById(R.id.home_item_cancel);
        //关注
        homeitemattention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG,"---homeitemattention.setOnClickListener---");
            }
        });
        //分享
        homeitemshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG,"---homeitemshare.setOnClickListener---");
                popupWindow.dismiss();

            }
        });
        //举报
        homeitemreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG,"---homeitemreport.setOnClickListener---");
                ActivityUtils.skipActivity(activity, ReportActivity.class, 0, "");
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        //取消
        homeitemcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG,"---homeitemcancel.setOnClickListener---");
                popupWindow.dismiss();

            }
        });
    }


    private static void setAddBlack(final Activity activity, int userid) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.ADDBLACk).addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .addParams("targetUserId", String.valueOf(userid)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    BlackBean blackBean = (BlackBean) JsonUtil.fromJson(s, BlackBean.class);
                    if (blackBean.getCode() == 0) {
                        EventBus.getDefault().post(new EventType(Constant.UrlOrigin.BlackList));
                        ToastUtil.show(blackBean.getMsg());
                        popupWindow.dismiss();
                        setBackgroundAlpha(activity, 1f);
                    } else {
                        ToastUtil.show(blackBean.getMsg());
                    }

                }
            }
        });
    }

    /**
     * 通用提示框
     *
     * @param context       上下文
     * @param tipMessage    提示信息
     * @param button1Text   按钮文本
     * @param button2Text   按钮文本
     * @param onButtonClick 回掉接口
     * @return
     */
    public static AlertDialog getUpdateDialog(Activity context, String tipMessage, String button1Text,
                                              String button2Text, final OnButtonClick onButtonClick) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.loading_dialog);
        View view = View.inflate(context, R.layout.update_dialog, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_Updatecancel);
        tv_cancel.setText(button1Text);
        TextView tv_ok = (TextView) view.findViewById(R.id.tv_Updateok);
        tv_ok.setText(button2Text);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_Updatecontent);
        tv_content.setText(tipMessage);
        final AlertDialog dialog = builder.create();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick.button01ClickListener();
                dialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick.button02ClickListener();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * 支付 提现选择
     */
//    public static AlertDialog setPaySeletor(Activity activity, final OnButtonClick onButtonClick) {
    public static AlertDialog setPaySeletor(Activity activity, String cardType) {
        View myView = LayoutInflater.from(activity).inflate(R.layout.selet_pay, null);
        ImageView wachaselect = myView.findViewById(R.id.wacha_select);
        ImageView alipayselect = myView.findViewById(R.id.alipay_select);
        Button btnpay = myView.findViewById(R.id.btn_pay);
        final AlertDialog dialog = new AlertDialog.Builder(activity, R.style.loading_dialog).setView(myView).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        myView.findViewById(R.id.WachalineCz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick = true;
                onpay = true;
                wachaselect.setBackgroundResource(R.drawable.selected);
                alipayselect.setBackgroundResource(R.drawable.unselected);
            }
        });
        myView.findViewById(R.id.lineCzAlipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick = true;
                alipayselect.setBackgroundResource(R.drawable.selected);
                wachaselect.setBackgroundResource(R.drawable.unselected);
            }
        });
        myView.findViewById(R.id.pay_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                onclick = false;
                onpay = false;
            }
        });
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onclick){
                    //是否有选中支付方式
                    if (onpay){//微信选中
                        Log.v(TAG,"----onClick()-------------微信支付--------");
                        if (UserTask.getInstance() != null) {
                            String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
                            PayUtils.getWxDetail(activity, userid, cardType,"wxapp");
                        }
                    }else {//支付宝选中
                        Log.v(TAG,"----onClick()-------------支付宝支付--------");
                        if (UserTask.getInstance() != null) {
                            String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
                            PayUtils.getWxDetail(activity, userid, cardType,"aliapp");
                        }
                    }
                    onclick = false;
                    onpay = false;
                    dialog.dismiss();

                }else {
                    Log.v(TAG,"----onClick()-------------微信支付--------");
                    if (UserTask.getInstance() != null) {
                        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
                        PayUtils.getWxDetail(activity, userid, cardType,"wxapp");
                    }
                }

            }
        });

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = d.getWidth();
        dialog.getWindow().setAttributes(p);
        return dialog;
    }

    public static AlertDialog getPiPeiTiShi(Activity activity, HomePiPeiBean.DataBean data) {
        View myView = LayoutInflater.from(activity).inflate(R.layout.home_pipei, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity, R.style.loading_dialog).setView(myView).create();
        ShapedImageView userHeader = myView.findViewById(R.id.ivPiPeiUserHeader);
        TextView tvHomePiPeiCity = myView.findViewById(R.id.tvHomePiPeiCity);
        TextView arg = myView.findViewById(R.id.tvHomePiPeiArg);
        ImageView sex = myView.findViewById(R.id.ivHomePiPeiSex);
        TextView nick = myView.findViewById(R.id.tvHomePiPeiNick);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        if (data.getUserInfo().getHeadUrl() != null && !TextUtils.isEmpty(data.getUserInfo().getHeadUrl())) {
            Glide.with(activity).load(data.getUserInfo().getHeadUrl()).into(userHeader);
        }
        nick.setText(data.getUserInfo().getNick() + "");
        tvHomePiPeiCity.setText(data.getTour().getToArea() + "");
        arg.setText(data.getUserInfo().getAge() + "");
        if (data.getUserInfo().getSex() != null) {
            if (data.getUserInfo().getSex().equals(ConstantsBean.BOY)) {
                sex.setImageDrawable(activity.getResources().getDrawable(R.mipmap.boy));
            } else {
                sex.setImageDrawable(activity.getResources().getDrawable(R.mipmap.gril));
            }
        }
        myView.findViewById(R.id.ivHomePiPeiCannel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        myView.findViewById(R.id.ivHomePiPeiToLook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity, JieBanActivityDetailActivity.class, 0, data.getTour().getId() + "");
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = d.getWidth();
        p.height = CommonUtil.dipToPx(activity, 361);
        dialog.getWindow().setAttributes(p);
        return dialog;
    }

    /**
     * 签到
     *
     * @param context 上下文
     * @return
     */
    public static AlertDialog getQianDaoDialog(final Activity context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.loading_dialog);
        View view = View.inflate(context, R.layout.qiandao_dialog, null);
        ImageView tishi = view.findViewById(R.id.ivQianDaoTiShi);
        TextView tvQianDaoNumber = view.findViewById(R.id.tvQianDaoNumber);
        //GridView qianGridView=view.findViewById(R.id.qiandaoGridView);
        ImageView ivSignOne = view.findViewById(R.id.ivSignOne);
        ImageView ivSignTwo = view.findViewById(R.id.ivSignTwo);
        ImageView ivSignSan = view.findViewById(R.id.ivSignSan);
        ImageView ivSignFor = view.findViewById(R.id.ivSignFor);
        ImageView ivSignFive = view.findViewById(R.id.ivSignFive);
        ImageView ivSignSex = view.findViewById(R.id.ivSignSex);
        ImageView ivSignSeven = view.findViewById(R.id.ivSignSeven);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        ivSignOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddSign(context, ivSignOne, ivSignTwo, ivSignSan, ivSignFor, ivSignFive, ivSignSex, ivSignSeven, tvQianDaoNumber);
            }
        });
        ivSignTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddSign(context, ivSignOne, ivSignTwo, ivSignSan, ivSignFor, ivSignFive, ivSignSex, ivSignSeven, tvQianDaoNumber);
            }
        });
        ivSignSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddSign(context, ivSignOne, ivSignTwo, ivSignSan, ivSignFor, ivSignFive, ivSignSex, ivSignSeven, tvQianDaoNumber);
            }
        });
        ivSignFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddSign(context, ivSignOne, ivSignTwo, ivSignSan, ivSignFor, ivSignFive, ivSignSex, ivSignSeven, tvQianDaoNumber);
            }
        });
        ivSignFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddSign(context, ivSignOne, ivSignTwo, ivSignSan, ivSignFor, ivSignFive, ivSignSex, ivSignSeven, tvQianDaoNumber);
            }
        });
        ivSignSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddSign(context, ivSignOne, ivSignTwo, ivSignSan, ivSignFor, ivSignFive, ivSignSex, ivSignSeven, tvQianDaoNumber);
            }
        });
        ivSignSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddSign(context, ivSignOne, ivSignTwo, ivSignSan, ivSignFor, ivSignFive, ivSignSex, ivSignSeven, tvQianDaoNumber);
            }
        });
        tishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showLayout(context);
            }
        });
        dialog.show();
        getQinDao(context, ivSignOne, ivSignTwo, ivSignSan, ivSignFor, ivSignFive, ivSignSex, ivSignSeven, tvQianDaoNumber);
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.95);
        dialog.getWindow().setAttributes(p);
        return dialog;
    }

    private static void setAddSign(Activity activity, ImageView ivSignOne, ImageView ivSignTwo, ImageView ivSignSan,
                                   ImageView ivSignFor, ImageView ivSignFive, ImageView ivSignSex, ImageView ivSignSeven, TextView tvQianDaoNumber) {
        int userid = 0;
        if (UserTask.getInstance().getUser() != null) {
            userid = UserTask.getInstance().getUser().getUserId();
        }
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.ADDSIGN).addParams(ConstantsBean.USERID, userid + "")
                .addParams("signType", "1").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {
                        int jifen = PreferenceUtil.getInt(ConstantsBean.Spell, ConstantsBean.JIFEN);
                        int mJifen = jifen + 10;
                        PreferenceUtil.putInt(ConstantsBean.Spell, ConstantsBean.JIFEN, mJifen);
                        tvQianDaoNumber.setText(mJifen + "");
                        setSignJifen(activity);
                        getQinDao(activity, ivSignOne, ivSignTwo, ivSignSan,
                                ivSignFor, ivSignFive, ivSignSex, ivSignSeven, tvQianDaoNumber);
                    } else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }
    private static boolean isToday=false;
    private static void getQinDao(Activity activity, ImageView ivSignOne, ImageView ivSignTwo, ImageView ivSignSan,
                                  ImageView ivSignFor, ImageView ivSignFive, ImageView ivSignSex, ImageView ivSignSeven, TextView tvQianDaoNumber) {
        int userid = 0;
        if (UserTask.getInstance().getUser() != null) {
            userid = UserTask.getInstance().getUser().getUserId();
        }
        String time = CommonUtil.getDayTime();
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.SIGNLIST).addParams(ConstantsBean.USERID, userid + "")
                .addParams("date", time).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    SignListBean signListBean = (SignListBean) JsonUtil.fromJson(s, SignListBean.class);
                    if (signListBean.getCode() == 0 && signListBean.getData().size() > 0) {
                        int jifen = PreferenceUtil.getInt(ConstantsBean.Spell, ConstantsBean.JIFEN);
                        tvQianDaoNumber.setText(jifen + "");
                        for (int j = 0; j < signListBean.getData().size(); j++) {
                            int mDay = CommonUtil.getDatNub(signListBean.getData().get(j).getSignDate());
                            if (mDay == j) {
                                isToday=true;
                                setSignSel(activity, ivSignOne, ivSignTwo, ivSignSan,
                                        ivSignFor, ivSignFive, ivSignSex, ivSignSeven, mDay, true);
                            } else if (mDay == j + 1) {
                                if (isToday){
                                    isToday=false;
                                    break;
                                }
                                setSignSel(activity, ivSignOne, ivSignTwo, ivSignSan,
                                        ivSignFor, ivSignFive, ivSignSex, ivSignSeven, mDay, false);
                            } else if (mDay>1&&mDay > j) {
                                setSignSel(activity, ivSignOne, ivSignTwo, ivSignSan,
                                        ivSignFor, ivSignFive, ivSignSex, ivSignSeven, 10, true);
                            }
                            if (j >6) {
                                break;
                            }
                        }
                    } else {
                        tvQianDaoNumber.setText("0");
                        setSignSel(activity, ivSignOne, ivSignTwo, ivSignSan,
                                ivSignFor, ivSignFive, ivSignSex, ivSignSeven, 38, false);
                    }
                }
            }
        });
    }

    private static void setSignSel(final Activity activity, final ImageView ivSignOne, final ImageView ivSignTwo, final ImageView ivSignSan,
                                   final ImageView ivSignFor, final ImageView ivSignFive, final ImageView ivSignSex, final ImageView ivSignSeven, int num, boolean isToday) {
        if (isToday) {
            if (num == 0) {
                ivSignOne.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 1) {
                ivSignTwo.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 2) {
                ivSignSan.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 3) {
                ivSignFor.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 4) {
                ivSignFive.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 5) {
                ivSignSex.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 6) {
                ivSignSeven.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            }

        } else {
            if (num == 1) {
                ivSignOne.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 2) {
                ivSignTwo.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 3) {
                ivSignSan.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 4) {
                ivSignFor.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 5) {
                ivSignFive.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 6) {
                ivSignSex.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaosel));
            } else if (num == 7) {
                ivSignSeven.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaonor));
            } else {
                if (num > 10) {
                    Drawable.ConstantState res = activity.getResources().getDrawable(R.mipmap.qiandaosel).getConstantState();
                    if (!ivSignOne.getDrawable().getConstantState().equals(res)) {
                        ivSignOne.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaonor));
                    }
                    if (!ivSignTwo.getDrawable().getConstantState().equals(res)) {
                        ivSignTwo.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaonor));
                    }
                    if (!ivSignSan.getDrawable().getConstantState().equals(res)) {
                        ivSignSan.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaonor));
                    }
                    if (!ivSignFor.getDrawable().getConstantState().equals(res)) {
                        ivSignFor.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaonor));
                    }
                    if (!ivSignFive.getDrawable().getConstantState().equals(res)) {
                        ivSignFive.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaonor));
                    }
                    if (!ivSignSex.getDrawable().getConstantState().equals(res)) {
                        ivSignSex.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaonor));
                    }
                    if (!ivSignSeven.getDrawable().getConstantState().equals(res)) {
                        ivSignSeven.setImageDrawable(activity.getResources().getDrawable(R.mipmap.qiandaonor));
                    }


                }
            }
        }

    }

    private static AlertDialog setSignJifen(Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.loading_dialog);
        View view = View.inflate(activity, R.layout.signfinish_dialog, null);
        ImageView ivSignFinish = view.findViewById(R.id.ivSignFinish);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        Animation operatingAnim = AnimationUtils.loadAnimation(activity, R.anim.tip);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        ivSignFinish.startAnimation(operatingAnim);
        ivSignFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.8);
        dialog.getWindow().setAttributes(p);
        return dialog;
    }
    /**
     * 次数提示框
     *
     * @param context       上下文
     * @param tipMessage    提示信息
     * @return
     */
    public static AlertDialog getTiShiDialog(final Activity context, String tipMessage,   final OnButtonClick onButtonClick) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.loading_dialog);
        View view = View.inflate(context, R.layout.tishidialog, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tvTishicancel);
        TextView tv_ok = (TextView) view.findViewById(R.id.tvTishiOK);
        TextView tv_content = (TextView) view.findViewById(R.id.tvTishiContent);
        tv_content.setText(tipMessage);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        //点击ok进入聊天
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick.button01ClickListener();
                dialog.dismiss();
            }
        });
        //取消聊天
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.8);
        dialog.getWindow().setAttributes(p);
        return dialog;
    }
    /**
     * 次数提示框
     *
     * @param context       上下文
     * @param tipMessage    提示信息
     * @return
     */
    public static AlertDialog getJIeBAnDialog(final Activity context, String tipMessage) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.loading_dialog);
        View view = View.inflate(context, R.layout.jiebantishidialog, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tvJieBanTishicancel);
        TextView tv_content = (TextView) view.findViewById(R.id.tvJieBanTishiContent);
        TextView tvJieBanTishiOk=view.findViewById(R.id.tvJieBanTishiOk);
        tv_content.setText(tipMessage);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvJieBanTishiOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(context, BuyVIPActivity.class,0,"");
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.8);
        dialog.getWindow().setAttributes(p);
        return dialog;
    }
    /**
     * 次数提示框
     *
     * @param context       上下文
     * @return
     */
    public static AlertDialog getOutLoginDialog(final Activity context) {
        Log.v(TAG,"---getOutLoginDialog---");
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.loading_dialog);
        View view = View.inflate(context, R.layout.outlogindialog, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tvOutTishicancel);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示帐号在其他设备登录
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        setOutLogin();
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog!=null&&dialog.isShowing()){
                                    dialog.dismiss();
                                }
                                Intent intent = new Intent(context, SplashActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                context.finish();
                            }
                        });
                    }
                    @Override
                    public void onError(int i, String s) {
                       setOutLogin();
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog!=null&&dialog.isShowing()){
                                    dialog.dismiss();
                                }
                                Intent intent = new Intent(context, SplashActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                context.finish();
                            }
                        });
                    }
                    @Override
                    public void onProgress(int i, String s) {
                        Log.e("onProgress",s+"");
                    }
                });

            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.8);
        dialog.getWindow().setAttributes(p);
        return dialog;
    }

    private static void setOutLogin() {
        LitePal.deleteAll(UserBean.class);
        LitePal.deleteAll(DataBean.class);
        LitePal.deleteAll(UserInfoBean.class);
        LitePal.deleteAll(UserInfoDataBean.class);
        PreferenceUtil.putString(ConstantsBean.Spell, ConstantsBean.USER_PHONE, "");
        PreferenceUtil.putString(ConstantsBean.Spell, ConstantsBean.USER_PASS, "");
        EventBus.getDefault().post(new SendMessageData("outlogine"));
    }


    /**
     * 发布按钮弹框
     */
    public static void publicPin(final Activity activity ,String name) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(CustomApplication.context).inflate(R.layout.public_pin, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        //设置PopupWindow的View点击事件
        setPublicPin(activity, view,name);
        //设置背景色
//        setBackgroundAlpha(activity, 0.5f);
    }

    private static void setPublicPin(Activity activity, View view,String name) {

        ImageView imgGrfb = view.findViewById(R.id.img_grfb);
        ImageView imgSjfb = view.findViewById(R.id.img_sjfb);
        ImageView publicGb = view.findViewById(R.id.public_gb);
        TextView txtsjfb = view.findViewById(R.id.txt_sjfb);
        if (name !=null){
            if (name.equals("fb")){
                txtsjfb.setText("商家发布");
            }else if (name.equals("tc")){
                txtsjfb.setText("同城发布");
            }
        }
        imgGrfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //个人发布
                ActivityUtils.skipActivity(activity, PublishPinActivity.class, 0, "");
                popupWindow.dismiss();
            }
        });
        //商家发布
        imgSjfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name !=null) {
                    if (name.equals("tc")) {
                        ActivityUtils.skipActivity(activity, SamePublicActivity.class, 0, "");
                    } else {
                        ToastUtil.show("暂未开发");
//                ActivityUtils.skipActivity(activity, MerchantActivity.class, 0, "");

                    }
                }
            }
        });
        publicGb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }
}
