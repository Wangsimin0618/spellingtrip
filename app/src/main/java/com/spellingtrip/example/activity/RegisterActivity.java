package com.spellingtrip.example.activity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.spellingtrip.example.MainActivity;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.SmSCodeBean;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.PhoneCode;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.phoneCode)
    public PhoneCode phoneCode;
    @BindView(R.id.cbAgree)
    public CheckBox cbAgree;
    @BindView(R.id.tvArgeeText)
    public TextView tvArgeeText;
    @BindView(R.id.tvRegisterSel)
    public TextView tvRegisterSel;
    @BindView(R.id.tvRegisterTelSms)
    public Button tvTelSms;
    @BindView(R.id.etRegisterPhone)
    public EditText etRegisterPhone;
    @BindView(R.id.etRegisterPassWrod)
    public EditText etRegisterPassWrod;
    @BindView(R.id.etRegisterTwoPassWrod)
    public EditText etRegisterTwoPassWrod;
    @BindView(R.id.tvRegisterPass)
    public TextView tvRegisterPass;
    private MyCountDownTimer myCountDownTimer;

    private String smsCode;
    private LogineDialog logineDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        phoneCode.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                smsCode=code;
            }

            @Override
            public void onInput() {

            }
        });
    }

    @Override
    protected void getData() {
    }

    @Override
    protected void setData() {
        backClick();
       String title= getIntent().getStringExtra("title");
       if (!TextUtils.isEmpty(title)){
           tvRegisterPass.setText(title);
       }
      String phone= getIntent().getStringExtra("phone");
        if (!TextUtils.isEmpty(phone)) {
            etRegisterPhone.setText(phone + "");
        }

    }

    @OnClick({R.id.tvRegisterSel, R.id.tvArgeeText, R.id.tvRegisterTelSms})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tvRegisterSel:
                String tell= etRegisterPhone.getText().toString().trim();
                String passone= etRegisterPassWrod.getText().toString().trim();
                String passtwo=etRegisterTwoPassWrod.getText().toString().trim();
                String title=tvRegisterPass.getText().toString().trim();
                if (!cbAgree.isChecked()) {
                    ToastUtil.show("请先阅读用户使用协议");
                } else if (!TextUtils.isEmpty(tell)&&!TextUtils.isEmpty(passone)&&!TextUtils.isEmpty(passtwo)&&!TextUtils.isEmpty(smsCode)
                        &&passone.equals(passtwo)){
                    if (title.equals("注册")){
                        if (logineDialog==null){
                            logineDialog=new LogineDialog(this,"正在注册");
                        }
                        logineDialog.show();
                       String password= CommonUtil.md5(passone);
                       setlogineDialog();
                        HttpRequest.getRegister(RegisterActivity.this,ConstantsBean.Register,tell,smsCode,password);
                    }else if (title.equals("修改密码")){
                        if (logineDialog==null){
                            logineDialog=new LogineDialog(this,"修改");
                        }
                        logineDialog.show();
                        String password1= CommonUtil.md5(passone);
                        setlogineDialog();
                        HttpRequest.getRegister(RegisterActivity.this,ConstantsBean.UPDATAPASS,tell,smsCode,password1);
                    }
                }else if (!TextUtils.isEmpty(passone)&&!TextUtils.isEmpty(passtwo)&&!passone.equals(passtwo)){
                    ToastUtil.show("两次输入的密码不相同");
                }else {
                    ToastUtil.show("请检查是否遗漏");
                }
                break;
            case R.id.tvArgeeText:
                ActivityUtils.skipActivity(this, ArgeeTextActivity.class, 0, "");
                break;
            case R.id.tvRegisterTelSms:
                String tel = etRegisterPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(tel) && CommonUtil.isMobileNO(tel)) {
                    sendSms(tel);
                } else if (TextUtils.isEmpty(tel)) {
                    ToastUtil.show("请输入手机号");
                } else if (!CommonUtil.isMobileNO(tel)) {
                    ToastUtil.show("请查看号码是否正确");
                }
                break;
        }
    }

    private void setlogineDialog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (logineDialog!=null&&logineDialog.isShowing()){
                    logineDialog.dismiss();
                }
            }
        },3000);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.IsLogine)){
            if (logineDialog!=null&&logineDialog.isShowing()){
                logineDialog.dismiss();
            }
           /* ActivityUtils.skipActivity(this, MainActivity.class,0,"");
            finish();*/
        }
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
            tvTelSms.setText(l / 1000 + "秒");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tvTelSms.setText("发送");
            //设置可点击
            tvTelSms.setClickable(true);
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

        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.VERIFICATION_CODE + tel).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show("服务器开小差了");
            }

            @Override
            public void onResponse(String s, int i) {
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



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
