package com.spellingtrip.example.wxapi;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.BaseActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2017/12/11 0011.
 */

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static long lastClickTime;
    private IWXAPI api;
    private static final int MIN_CLICK_DELAY_TIME = 1000;


    @Override
    protected int getLayoutId() {
        return R.layout.pay_result;
    }

    @Override
    protected void initView() {
        //解决不回调的问题
        api = WXAPIFactory.createWXAPI(this, ConstantsBean.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("微信支付");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }


    @Override
    public void onResp(BaseResp resp) {
        int code = resp.errCode;
        switch (code) {
            case 0://支付成功后的界面 ;
                UserInfoDataBean userInfoDataBean = UserTask.getInstance().getUserInfoData();
                userInfoDataBean.setVip(true);
                userInfoDataBean.save();
                EventBus.getDefault().post(new SendMessageData(ConstantsBean.ISVIP));
                ToastUtil.show("支付成功");
                break;
            case -1:
                Log.e("wxpayfail", "签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、您的微信账号异常等。");
                break;
            case -2://用户取消支付后的界面
                ToastUtil.show("支付失败");
                break;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}