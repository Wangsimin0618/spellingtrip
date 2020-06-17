package com.spellingtrip.example.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.AliDetailBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.MessageEvent;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.bean.WxDetailBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.OrderInfoUtil;
import com.spellingtrip.example.utils.PayResult;
import com.spellingtrip.example.utils.PayUtils;
import com.spellingtrip.example.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 商家认证支付页面
 */
public class MerchantPayFragment extends BaseFragment {
    public static final String TAG = "MerchantPayFragment";
    @BindView(R.id.btn_pay)
    Button btnPay;
    Unbinder unbinder;
    private static final int SDK_PAY_FLAG = 1;
    private static String RSA_PRIVATE="";

    private static PopupWindow popupWindow;
    private static boolean onclick = false;
    private static boolean onpay = false;
    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (resultStatus.equals("9000")) {
                        UserInfoDataBean userInfoDataBean = UserTask.getInstance().getUserInfoData();
                        userInfoDataBean.setVip(true);
                        boolean seccus= userInfoDataBean.save();
                        if (seccus){
                            EventBus.getDefault().post(new MessageEvent("支付成功"));
                            ToastUtil.show("支付成功");
                        }
                    } else {
                        // EventBus.getDefault().post("faile");
                        ToastUtil.show("支付失败");
                    }
                default:
                    break;
            }
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchantpay;
    }

    @Override
    protected void findView(View view) {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_pay)
    public void onViewClicked() {
        payDialog(getActivity());
    }
    /**
     * 支付弹框
     */
    public static AlertDialog payDialog(final Activity activity) {
        //防止重复按按钮
        //设置PopupWindow的View
        View view = LayoutInflater.from(CustomApplication.context).inflate(R.layout.selet_pay, null);
        ImageView wachaselect = view.findViewById(R.id.wacha_select);
        ImageView alipayselect = view.findViewById(R.id.alipay_select);
        ImageView payclose = view.findViewById(R.id.pay_close);
        Button btnpay = view.findViewById(R.id.btn_pay);
        final AlertDialog dialog = new AlertDialog.Builder(activity, R.style.loading_dialog).setView(view).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //关闭弹框
        payclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        //微信支付
        view.findViewById(R.id.WachalineCz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick = true;
                onpay = true;
                wachaselect.setBackgroundResource(R.drawable.selected);
                alipayselect.setBackgroundResource(R.drawable.unselected);
            }
        });
        //支付宝支付
        view.findViewById(R.id.lineCzAlipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick = true;
                alipayselect.setBackgroundResource(R.drawable.selected);
                wachaselect.setBackgroundResource(R.drawable.unselected);

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
                            getWxDetail(activity, userid,"wxapp");
                        }
                    }else {//支付宝选中
                        Log.v(TAG,"----onClick()-------------支付宝支付--------");
                        if (UserTask.getInstance() != null) {
                            String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
                            getWxDetail(activity, userid,"aliapp");
                        }
                    }
                    onclick = false;
                    onpay = false;
                    dialog.dismiss();

                }else {
                    Log.v(TAG,"----onClick()-------------微信支付--------");
                    if (UserTask.getInstance() != null) {
                        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
                       getWxDetail(activity, userid,"wxapp");
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

    /**
     * 商家认证支付请求接口
     * @param activity
     * @param userid
     * @param type
     */
    public static void getWxDetail(Activity activity,String userid,String type){
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.MERCHANTPAY).addParams(ConstantsBean.USERID,userid)
                .addParams("payType",type)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Log.e("getWxDetailonError",JsonUtil.toJson(e));
            }

            @Override
            public void onResponse(String s, int i) {
                if (type.equals("aliapp")){
                    if (!TextUtils.isEmpty(s)){
                        AliDetailBean aliDetailBean= (AliDetailBean) JsonUtil.fromJson(s,AliDetailBean.class);
                        if (aliDetailBean.getCode()==0){
                            //支付宝支付
                            getAilpay(activity,aliDetailBean.getData());
                        }else {

                        }
                    }
                }else {//微信支付
                    if (!TextUtils.isEmpty(s)){
                        WxDetailBean wxDetailBean= (WxDetailBean) JsonUtil.fromJson(s,WxDetailBean.class);
                        if (wxDetailBean.getCode()==0){
                            getWxPay(activity,wxDetailBean.getData());
                        }else {

                        }
                    }
                }

            }
        });

    };

    /**
     * 微信支付
     * @param activity
     * @param dataBean
     */
    public static void getWxPay(Activity activity, WxDetailBean.DataBean dataBean) {
        IWXAPI api = WXAPIFactory.createWXAPI(activity, ConstantsBean.APP_ID,true);
        if (!api.isWXAppInstalled()){
            ToastUtil.show("您还未安装微信客户端");
            return;
        }
        api.registerApp(ConstantsBean.APP_ID);
        PayReq req = new PayReq();
        req.appId = dataBean.getAppid();
        req.partnerId =dataBean.getPartnerid();
        req.prepayId = dataBean.getPrepayid();
        req.nonceStr = dataBean.getNoncestr();
        req.timeStamp =dataBean.getTimestamp(); //"1412000000";
        req.packageValue = dataBean.getPackageX();
        req.sign = dataBean.getSign();
        ToastUtil.show("正在跳转微信支付...");
        api.sendReq(req);

    }

    /**
     * 支付宝支付
     * @param
     * @param
     * @param activity
     * @param data
     */
    public static void getAilpay(final Activity activity, AliDetailBean.DataBean data) {
        boolean rsa2 = (ConstantsBean.RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil.buildOrderParamMap(ConstantsBean.ALIPAYADDID, rsa2,data);
        String orderParam = OrderInfoUtil.buildOrderParam(params);
        String privateKey = rsa2 ? ConstantsBean.RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
