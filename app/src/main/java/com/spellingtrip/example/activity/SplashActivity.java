package com.spellingtrip.example.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.spellingtrip.example.MainActivity;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.HotPinBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.TourisnBean;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

public class SplashActivity extends BaseActivity {
    private String type = "init";
    private String travelId="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        String guide = PreferenceUtil.getString(ConstantsBean.Spell, "boolean");
        String time=PreferenceUtil.getString(ConstantsBean.Spell,ConstantsBean.SpellTime);
        String timeDay=CommonUtil.getDayTime();
        if (TextUtils.isEmpty(time)||!time.equals(timeDay)){
            PreferenceUtil.putInt(ConstantsBean.Spell,ConstantsBean.PublishNumber,3);
            PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.SpellTime,timeDay);
        }else {
            PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.SpellTime,timeDay);
        }
        if (TextUtils.isEmpty(guide) || guide.equals("false")) {
            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            getHotPin();
            getRead(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void getRead(String writeStorage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, writeStorage) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{writeStorage}, 2);
            } else {
                jumpActivity();
            }
        } else {
            jumpActivity();
        }

    }

    @Override
    protected void getData() {
}
    private void getHotPin() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH+"/api/pin/hot").build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }
            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    HotPinBean   hotPinBean = (HotPinBean) JsonUtil.fromJson(s,HotPinBean.class);
                    if (hotPinBean.getCode() == 0) {
                        if (hotPinBean.getData().size() > 0) {
                            PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.HOTPEOPLE,s);
                        }
                    } else {
                        ToastUtil.show(hotPinBean.getMsg() + "");
                    }
                }else {
                }
            }
        });
    }
    private void getTourism(String userid) {
        OkHttpUtils.post().url("http://api.pinpinlx.cn"+ConstantsBean.TOURISM).addParams("userId",userid)
                .addParams("id",travelId).addParams("type","init").build().connTimeOut(10000) .readTimeOut(50000).writeTimeOut(10000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                //ToastUtil.show(ConstantsBean.ERROR);
            }
            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    TourisnBean tourisnBean= (TourisnBean) JsonUtil.fromJson(s,TourisnBean.class);
                    if (tourisnBean.getData() != null && tourisnBean.getCode() == 0 ) {
                        if (tourisnBean.getData().size()>0){
                            PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.TOURISMLISTS,s);
                        }else {
                        }
                    } else if (tourisnBean.getCode() == 1) {
                        ToastUtil.show(tourisnBean.getMsg() + "");
                    }
                }else {
                }
            }
        });
    }

    @Override
    protected void setData() {
    }

    private void jumpActivity() {
        if (UserTask.getInstance().isLogin()){
            if (UserTask.getInstance().getUser()!=null){
                String userid= String.valueOf(UserTask.getInstance().getUser().getUserId());
                getTourism(userid);
                String regId=JPushInterface.getRegistrationID(this);
                if (!TextUtils.isEmpty(regId)){
                    HttpRequest.setRegId(regId,userid);
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (UserTask.getInstance().getUserInfoData()!=null&&TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getSex())){
                        ActivityUtils.skipActivity(SplashActivity.this, NewUserEditInfoActivity.class,0,"");
                        finish();
                    }else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 3500);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LogineActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3500);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  jumpActivity();
                } else {
                    Toast.makeText(SplashActivity.this, "请设置保存权限", Toast.LENGTH_SHORT).show();
                    initView();
                }
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}
