package com.spellingtrip.example.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.HotPinBean;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ConvenientGuideBanner;
import com.spellingtrip.example.view.LocalImageHolderView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Arrays;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/1/18.
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private Integer[] images = {R.drawable.group1, R.drawable.group2, R.drawable.group3, R.drawable.group4};
    private TextView button;
    private ConvenientGuideBanner banner;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        banner = (ConvenientGuideBanner) findViewById(R.id.banner_viewpager);
        button = (TextView) findViewById(R.id.bt_guilde);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRead(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        });
        banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, Arrays.asList(images));
        banner.setPageIndicator(new int[]{R.mipmap.guidesplash_nor, R.mipmap.guidesplash_sel});
        banner.setCanLoop(false);
        banner.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void getData() {
        getHotPin();
    }

    @Override
    protected void setData() {

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
    private void getHotPin() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH+"/api/pin/hot").build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }
            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    HotPinBean hotPinBean = (HotPinBean) JsonUtil.fromJson(s,HotPinBean.class);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            jumpActivity();
        } else {
            Toast.makeText(this, "请设置储存权限", Toast.LENGTH_SHORT).show();
            //jumpActivity();
        }
    }

    private void jumpActivity() {
        PreferenceUtil.putString(ConstantsBean.Spell, "boolean", "true");
        Intent intent = new Intent(GuideActivity.this, LogineActivity.class);
        startActivity(intent);
        finish();
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
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position == images.length - 1) {
            button.setVisibility(View.VISIBLE);
            banner.setPointViewVisible(false);
        } else {
            button.setVisibility(View.GONE);
            banner.setPointViewVisible(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
