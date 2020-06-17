package com.spellingtrip.example.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.spellingtrip.example.MainActivity;
import com.spellingtrip.example.R;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class EditUserPassActivity extends BaseActivity {
    @BindView(R.id.tvEditUserPassLogineYinSi)
    public TextView tvEditUserPassLogineYinSi;
    @BindView(R.id.ivEditUserPassBack)
    public ImageView ivEditUserPassBack;
    @BindView(R.id.etEditUserPassPhone)
    public EditText etEditUserPassPhone;
    @BindView(R.id.tvEditUserPassLogineSel)
    public TextView tvEditUserPassLogineSel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edituserpass;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        etEditUserPassPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                if (length == 11) {
                    tvEditUserPassLogineSel.setBackground(getResources().getDrawable(R.mipmap.loginsel_bg));
                    tvEditUserPassLogineSel.setTextColor(getResources().getColor(R.color.main_bg));
                } else {
                    tvEditUserPassLogineSel.setBackground(getResources().getDrawable(R.drawable.loginnor_shape));
                    tvEditUserPassLogineSel.setTextColor(getResources().getColor(R.color.textabc0c));
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.ISEDITPASS)){
            finish();
        }
    }
    @OnClick({R.id.tvEditUserPassLogineYinSi, R.id.ivEditUserPassBack,R.id.tvEditUserPassLogineSel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvEditUserPassLogineYinSi:
                ActivityUtils.skipActivity(this, ArgeeTextActivity.class, 0, "");
                break;
            case R.id.ivEditUserPassBack:
                finish();
                break;
            case R.id.tvEditUserPassLogineSel:
                String phone=etEditUserPassPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)&&phone.length()==11){
                    ActivityUtils.skipActivity(this,PhoneLogineActivity.class,8,phone);
                }else {

                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
