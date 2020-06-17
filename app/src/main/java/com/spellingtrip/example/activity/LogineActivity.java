package com.spellingtrip.example.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.j256.ormlite.stmt.query.In;
import com.spellingtrip.example.MainActivity;
import com.spellingtrip.example.R;

import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import butterknife.BindView;
import butterknife.OnClick;

public class LogineActivity extends BaseActivity {
    @BindView(R.id.tvYanPhone)
    public TextView tvYanPhone;
    @BindView(R.id.etPassWrod)
    public EditText etPassWrod;
    @BindView(R.id.etUserPhone)
    public EditText etUserPhone;
    @BindView(R.id.tvLogineSel)
    public TextView tvLogineSel;
    @BindView(R.id.tvRegister)
    public TextView tvRegister;
    @BindView(R.id.tvLogineYinSi)
    public TextView tvLogineYinSi;
    @BindView(R.id.tvZhangPassWord)
    public TextView tvZhangPassWord;
    @BindView(R.id.tvLogineWangPass)
    public TextView tvLogineWangPass;
    private LogineDialog logineDialog;
    private String type = "tel";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logine;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        etPassWrod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                if (length >0) {
                    String passleng = etUserPhone.getText().toString().trim();
                    if (passleng.length()==11) {
                        tvLogineSel.setBackground(getResources().getDrawable(R.mipmap.loginsel_bg));
                        tvLogineSel.setTextColor(getResources().getColor(R.color.main_bg));
                    } else {
                        tvLogineSel.setBackground(getResources().getDrawable(R.drawable.loginnor_shape));
                        tvLogineSel.setTextColor(getResources().getColor(R.color.textabc0c));
                    }
                } else {
                    tvLogineSel.setBackground(getResources().getDrawable(R.drawable.loginnor_shape));
                    tvLogineSel.setTextColor(getResources().getColor(R.color.textabc0c));
                }
            }
        });
        etUserPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                if (type.equals("tel")) {
                    if (length == 11) {
                        tvLogineSel.setBackground(getResources().getDrawable(R.mipmap.loginsel_bg));
                        tvLogineSel.setTextColor(getResources().getColor(R.color.main_bg));
                    } else {
                        tvLogineSel.setBackground(getResources().getDrawable(R.drawable.loginnor_shape));
                        tvLogineSel.setTextColor(getResources().getColor(R.color.textabc0c));
                    }
                } else if (type.equals("pass")) {
                    if (length == 11) {
                        String passleng = etPassWrod.getText().toString().trim();
                        if (TextUtils.isEmpty(passleng)) {
                            tvLogineSel.setBackground(getResources().getDrawable(R.drawable.loginnor_shape));
                            tvLogineSel.setTextColor(getResources().getColor(R.color.textabc0c));
                        } else {
                            tvLogineSel.setBackground(getResources().getDrawable(R.mipmap.loginsel_bg));
                            tvLogineSel.setTextColor(getResources().getColor(R.color.main_bg));
                        }
                    } else {
                        tvLogineSel.setBackground(getResources().getDrawable(R.drawable.loginnor_shape));
                        tvLogineSel.setTextColor(getResources().getColor(R.color.textabc0c));
                    }
                }
            }
        });
    }

    @Override
    protected void getData() {
    }

    @Override
    protected void setData() {
    }

    @OnClick({R.id.tvLogineSel, R.id.tvYanPhone, R.id.tvRegister, R.id.tvLogineYinSi, R.id.tvLogineWangPass, R.id.tvZhangPassWord})
    public void OnClick(View view) {
        switch (view.getId()) {
            /*下一步或登录*/
            case R.id.tvLogineSel:
                String phone = etUserPhone.getText().toString().trim();
                String pass = etPassWrod.getText().toString().trim();
                if (type.equals("pass")) {
                    if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pass) && phone.length() == 11) {
                        setLogine(phone, pass);
                    } else if (!TextUtils.isEmpty(phone) && phone.length() == 11 && TextUtils.isEmpty(pass)) {
                        ToastUtil.show("请输入密码");
                    }
                } else if (type.equals("tel")) {
                    if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
                        ActivityUtils.skipActivity(this, PhoneLogineActivity.class, 0, phone);
                    } else {

                    }

                }

                break;
            /*账号密码登录*/
            case R.id.tvZhangPassWord:
                type = "pass";
                tvZhangPassWord.setVisibility(View.GONE);
                etPassWrod.setVisibility(View.VISIBLE);
                tvYanPhone.setVisibility(View.VISIBLE);
                tvLogineWangPass.setVisibility(View.VISIBLE);
                tvLogineSel.setText("登录");
                break;
            /*验证码登录*/
            case R.id.tvYanPhone:
                type = "tel";
                tvLogineSel.setText("下一步");
                etPassWrod.setVisibility(View.GONE);
                tvYanPhone.setVisibility(View.GONE);
                tvLogineWangPass.setVisibility(View.GONE);
                tvZhangPassWord.setVisibility(View.VISIBLE);
                break;
            case R.id.tvRegister:
                String rPhone = etUserPhone.getText().toString().trim();
                Intent intent = new Intent(LogineActivity.this, RegisterActivity.class);
                intent.putExtra("phone", rPhone);
                intent.putExtra("title", "注册");
                startActivity(intent);
                break;
            /*协议*/
            case R.id.tvLogineYinSi:
                ActivityUtils.skipActivity(this, ArgeeTextActivity.class, 0, "");
                break;
            /*忘记密码*/
            case R.id.tvLogineWangPass:
                ActivityUtils.skipActivity(this, EditUserPassActivity.class, 0, "");
                break;
        }
    }

    /**
     * 登录
     *
     * @param phone
     * @param pass
     */
    private void setLogine(String phone, String pass) {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(this, "正在登录");
        }
        logineDialog.show();
        String password = CommonUtil.md5(pass);
        HttpRequest.getLogine(LogineActivity.this,phone, "", password, "1");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.IsLogine)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (logineDialog != null && logineDialog.isShowing()) {
                        logineDialog.dismiss();
                    }
                    if (UserTask.getInstance().getUserInfoData() != null && UserTask.getInstance().getUserInfoData().getSex() != null) {
                        ActivityUtils.skipActivity(LogineActivity.this, MainActivity.class, 0, "");
                    } else {
                        ActivityUtils.skipActivity(LogineActivity.this, NewUserEditInfoActivity.class, 0, "");
                    }
                    finish();
                }
            }, 500);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
