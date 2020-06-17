package com.spellingtrip.example.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.spellingtrip.example.bean.AliDetailBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.bean.WxDetailBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.task.UserTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import okhttp3.Call;

public class PayUtils {
    public static final String TAG = "PayUtils";
    private static final int SDK_PAY_FLAG = 1;
    private static String RSA_PRIVATE="";
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
                           EventBus.getDefault().post(new SendMessageData(ConstantsBean.ISVIP));
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
    public static void getWxDetail(Activity activity,String userid,String cardType ,String type){
        Log.v(TAG,"---userid=="+userid+"----cardType=="+cardType+"-------type=="+type);
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.WXPAY).addParams(ConstantsBean.USERID,userid)
                .addParams("cardType",cardType)
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
      /*  req.sign = signNum(ConstantsBean.PARTNERID, "WX1217752501201407033233368018", "Sign=WXPay",
                CommonUtil.getRandomStringByLength(32)
                , String.valueOf(StringUtils.genTimeStamp()), ConstantsBean.WXKEY);*/
        ToastUtil.show("正在跳转微信支付...");
        api.sendReq(req);

    }



    //签名
    public static String signNum(String partnerId, String prepayId, String packageValue, String nonceStr, String timeStamp, String key) {
        String stringA =
                "appid=" + ConstantsBean.APP_ID
                        + "&noncestr=" + nonceStr
                        + "&package=" + packageValue
                        + "&partnerid=" + partnerId
                        + "&prepayid=" + prepayId
                        + "&timestamp=" + timeStamp;
        String stringSignTemp = stringA + "&key=" + key;
        String sign = CommonUtil.getMD5String(stringSignTemp);
        return sign;

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
