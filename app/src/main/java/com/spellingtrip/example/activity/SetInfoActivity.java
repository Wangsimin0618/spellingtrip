package com.spellingtrip.example.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.DataBean;
import com.spellingtrip.example.bean.UserBean;
import com.spellingtrip.example.bean.UserInfoBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.DataCleanManager;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;


import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.OnClick;

public class SetInfoActivity extends BaseActivity{
    @BindView(R.id.rlSetInfo)
    public RelativeLayout rlSetInfo;
    @BindView(R.id.rlNumberSecurity)
    public RelativeLayout rlNumberSecurity;
    @BindView(R.id.rlClearCrach)
    public RelativeLayout rlClearCrach;
    @BindView(R.id.rlAboutWe)
    public RelativeLayout rlAboutWe;
    @BindView(R.id.rlBlackDan)
    public RelativeLayout rlBlackDan;
    @BindView(R.id.rlIdea)
    public RelativeLayout rlIdea;
    @BindView(R.id.rlOutLogine)
    public RelativeLayout rlOutLogine;
    @BindView(R.id.tvCrachNum)
    public TextView tvCrachNum;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setinfo;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {

    }
    @OnClick({R.id.rlOutLogine,R.id.rlClearCrach,R.id.rlNumberSecurity,R.id.rlAboutWe,R.id.rlBlackDan,R.id.rlIdea,R.id.rlSetInfo})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rlOutLogine:
                if (UserTask.getInstance().isLogin()){
                    setOutLogine();
                }else {
                    ToastUtil.show(ConstantsBean.NOLOGINE);
                }


                break;
            case R.id.rlClearCrach:
                CommonDialog.getDialog(this, "提示", "是否清除缓存？", "取消", "确认", new OnButtonClick() {
                    @Override
                    public void button01ClickListener() {

                    }

                    @Override
                    public void button02ClickListener() {
                        LogineDialog logineDialog=new LogineDialog(SetInfoActivity.this,"");
                        logineDialog.show();
                        DataCleanManager.clearAllCache(SetInfoActivity.this);
                        logineDialog.dismiss();
                        tvCrachNum.setText("已清除");
                    }
                });
                break;
            case R.id.rlNumberSecurity:
                if (UserTask.getInstance().isLogin()){
                    ActivityUtils.skipActivity(this, SecurityNumberActivity.class,0,"");
                }else {
                    ActivityUtils.skipActivity(this,LogineActivity.class,0,"");
                }

                break;
            case R.id.rlAboutWe:
                ActivityUtils.skipActivity(this, UsetAboutUsActivity.class,0,"");
                break;
            case R.id.rlBlackDan:
                if (!UserTask.getInstance().isLogin()){
                    ActivityUtils.skipActivity(this,LogineActivity.class,0,"");
                }else {
                    ActivityUtils.skipActivity(this,BlackListActivity.class,0,"");
                }

                break;
            case R.id.rlIdea:
                ActivityUtils.skipActivity(this,IdeaBackActivity.class,21,ConstantsBean.IDEABACK);
                break;
            case R.id.rlSetInfo:
                if (UserTask.getInstance().isLogin()){
                    ActivityUtils.skipActivity(this,UserInfoActivity.class,0,"");
                }else {
                    ActivityUtils.skipActivity(this,LogineActivity.class,0,"");
                }

                break;
        }
    }

    private void setOutLogine() {
        CommonDialog.getDialog(this, "提示", "确定要退出当前账号吗？", "取消", "确认", new OnButtonClick() {
            @Override
            public void button01ClickListener() {

            }

            @Override
            public void button02ClickListener() {
                LogineDialog logineDialog=new LogineDialog(SetInfoActivity.this,"正在退出");
                logineDialog.show();
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        LitePal.deleteAll(UserBean.class);
                        LitePal.deleteAll(DataBean.class);
                        LitePal.deleteAll(UserInfoBean.class);
                        LitePal.deleteAll(UserInfoDataBean.class);
                        PreferenceUtil.putString(ConstantsBean.Spell, ConstantsBean.USER_PHONE, "");
                        PreferenceUtil.putString(ConstantsBean.Spell, ConstantsBean.USER_PASS, "");
                        EventBus.getDefault().post(new SendMessageData("outlogine"));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.show("退出成功");
                                if (logineDialog!=null){
                                    logineDialog.dismiss();
                                }
                                Intent intent = new Intent(SetInfoActivity.this, SplashActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onError(int code, String message) {
                        // TODO Auto-generated method stub

                    }
                });

                //  finish();
            }
        });
    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("设置");
        try {
            tvCrachNum.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
