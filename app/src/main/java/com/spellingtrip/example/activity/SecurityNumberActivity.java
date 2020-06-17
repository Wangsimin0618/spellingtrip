package com.spellingtrip.example.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class SecurityNumberActivity extends BaseActivity {
    @BindView(R.id.rlUpDataPass)
    public RelativeLayout rlUpDataPass;
    @BindView(R.id.rlRenZheng)
    public RelativeLayout rlRenZheng;
    @BindView(R.id.tvTishi)
    public TextView tvTiShi;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_security;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void getData() {

    }
@OnClick({R.id.rlUpDataPass,R.id.rlRenZheng})
public void onClick(View view){
        switch (view.getId()){
            case R.id.rlUpDataPass:
                ActivityUtils.skipActivity(this,RegisterActivity.class,0,"修改密码");
                break;
            case R.id.rlRenZheng:
                if (UserTask.getInstance().getUserInfoData()!=null){
                    if (UserTask.getInstance().getUserInfoData().isIdcardAuth()){
                        ToastUtil.show("已认证");
                    }else if (UserTask.getInstance().getUserInfoData().getAuthStatus()==1){
                        ToastUtil.show("正在审核中");
                    }else if (!UserTask.getInstance().getUserInfoData().isIdcardAuth()){
                        ActivityUtils.skipActivity(this,RenZhengActivity.class,0,"");
                    }

                }else {
                    ActivityUtils.skipActivity(this,RenZhengActivity.class,0,"");
                }


                break;
        }
}
    @Override
    protected void setData() {
        backClick();
        setCenterTitle("账号与安全");
        if (UserTask.getInstance().getUserInfoData()!=null) {
            if (UserTask.getInstance().getUserInfoData().isIdcardAuth()) {
                tvTiShi.setText("已认证");
            } else if (UserTask.getInstance().getUserInfoData().getAuthStatus() == 1) {
                tvTiShi.setText("正在审核中");
            } else if (!UserTask.getInstance().getUserInfoData().isIdcardAuth()) {
                tvTiShi.setText("未认证");
            }
        }else {
            tvTiShi.setText("未认证");
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.USERINFO)){
            finish();
           /* if (UserTask.getInstance().getUserInfoData()!=null) {
                if (UserTask.getInstance().getUserInfoData().isIdcardAuth()) {
                    tvTiShi.setText("已认证");
                } else if (UserTask.getInstance().getUserInfoData().getAuthStatus() == 1) {
                    tvTiShi.setText("正在审核中");
                } else if (!UserTask.getInstance().getUserInfoData().isIdcardAuth()) {
                    tvTiShi.setText("未认证");
                }
            }else {
                tvTiShi.setText("未认证");
            }*/
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().unregister(this);
    }
}
