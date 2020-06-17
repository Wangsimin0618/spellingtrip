package com.spellingtrip.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.spellingtrip.example.MainActivity;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;

import com.spellingtrip.example.bean.SmSCodeBean;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 登录注册发送验证码页面
 */
public class PhoneLogineActivity extends BaseActivity {
    public static final String TAG = "PhoneLogineActivity";
    @BindView(R.id.etTelLogine)
    public TextView etTelLogine;
    @BindView(R.id.etTelLoginePassWrod)
    public EditText etTelLoginePassWrod;
    @BindView(R.id.tvTelSms)
    public Button tvTelSms;
    @BindView(R.id.ivPhoneLogineLeft)
    public ImageView ivPhoneLogineLeft;
    @BindView(R.id.tvPhoneLogineYinSi)
    public TextView tvPhoneLogineYinSi;
    private MyCountDownTimer myCountDownTimer;
    private LogineDialog logineDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phonelogine;
    }

    @Override
    protected void initView() {
        Log.v(TAG,"---------initView()-------");
        EventBus.getDefault().register(this);
        etTelLoginePassWrod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.v(TAG,"---------beforeTextChanged()-------");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v(TAG,"---------onTextChanged()-------");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.v(TAG,"-------initView===addTextChangedListener--------s=="+s);
                //int stratnum=etTelLoginePassWrod.getSelectionStart();
                int endnum=etTelLoginePassWrod.getSelectionEnd();
                int code=s.toString().length();
//                String sms = String.valueOf(code);
                String sms=s.toString().trim();
                Log.v(TAG,"-------initView()----endnum=="+endnum+"----------code=="+code+"---------sms=="+sms);
                if (endnum==6){
                    String phone=getIntent().getStringExtra("title");
                    String id=getIntent().getStringExtra("id");
                    Log.v(TAG,"-------initView()----phone=="+phone+"----------id=="+id);
                    if (!TextUtils.isEmpty(id)&&id.equals("8")){
                        Log.v(TAG,"---------intent----------");
                        Intent smsCode=new Intent(PhoneLogineActivity.this,EditUserPassComiActivity.class);
                        smsCode.putExtra("code",sms+"");
                        smsCode.putExtra("title",phone+"");
                        startActivity(smsCode);
                    }else {
                        setLogine(sms,phone);
                    }
                }
            }
        });
    }


    @Override
    protected void getData() {
        String phone=getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(phone)) {
            etTelLogine.setText("已发送验证码至"+phone+"");
            sendSms(phone);
        }
    }

    @Override
    protected void setData() {


    }

    @OnClick({ R.id.tvTelSms,R.id.ivPhoneLogineLeft,R.id.tvPhoneLogineYinSi})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ivPhoneLogineLeft:
                finish();
                break;
            case R.id.tvTelSms:
                String tel = etTelLogine.getText().toString().trim().replace("已发送验证码至","");
                if (!TextUtils.isEmpty(tel) && CommonUtil.isMobileNO(tel)) {
                    sendSms(tel);
                } else if (TextUtils.isEmpty(tel)) {
                    ToastUtil.show("请输入手机号");
                } else if (!CommonUtil.isMobileNO(tel)) {
                    ToastUtil.show("请查看号码是否正确");
                }
                break;
            case R.id.tvPhoneLogineYinSi://用户隐私协议进入webwiew网页
                ActivityUtils.skipActivity(this, ArgeeTextActivity.class, 0, "");
                break;
        }
    }

    /**
     * 发送验证码
     *
     * @param tel
     */
    private void sendSms(String tel) {
        if (myCountDownTimer == null) {
            myCountDownTimer = new MyCountDownTimer(60000, 1000);
        }

        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.VERIFICATION_CODE+ tel).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show("服务器开小差了");
            }

            @Override
            public void onResponse(String s, int i) {
                Log.v("aaaaaaaa","result="+s);

                if (!TextUtils.isEmpty(s)) {
                    SmSCodeBean smSCodeBean = (SmSCodeBean) JsonUtil.fromJson(s, SmSCodeBean.class);
                    if (smSCodeBean.getCode() == 0) {
                        myCountDownTimer.start();
                        ToastUtil.show(smSCodeBean.getMsg());
                    } else {
                        ToastUtil.show(smSCodeBean.getMsg());
                    }
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.IsLogine)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (logineDialog!=null&&logineDialog.isShowing()){
                        logineDialog.dismiss();
                    }
                    if (UserTask.getInstance().getUserInfoData()!=null&&UserTask.getInstance().getUserInfoData().getSex()!=null){
                        ActivityUtils.skipActivity(PhoneLogineActivity.this, MainActivity.class,0,"");
                    }else {
                        ActivityUtils.skipActivity(PhoneLogineActivity.this, NewUserEditInfoActivity.class,0,"");
                    }
                    finish();
                }
            },500);
        }
        if (data.getType().equals(Constant.UrlOrigin.ISEDITPASS)){
            if (logineDialog!=null&&logineDialog.isShowing()){
                logineDialog.dismiss();
            }
            finish();
        }
    }

    /**正在登录
     * @param sms
     * @param trim
     */
    private void setLogine(String sms, String trim) {
        Log.v(TAG,"-----------setLogine()-------------");
        if (logineDialog==null){
            Log.v(TAG,"-----------setLogine()--------logineDialog==null-----");
            logineDialog=new LogineDialog(this,"正在登录");
        }
        Log.v(TAG,"----setLogine---logineDialog.show()---");
        logineDialog.show();
        HttpRequest.getLogine(PhoneLogineActivity.this,trim, sms,"","2");
    }


    /**
     * 计时器
     */
    private class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            tvTelSms.setClickable(false);
            tvTelSms.setText(l / 1000 + "s");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tvTelSms.setText("重新发送(60s)");
            //设置可点击
            tvTelSms.setClickable(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
