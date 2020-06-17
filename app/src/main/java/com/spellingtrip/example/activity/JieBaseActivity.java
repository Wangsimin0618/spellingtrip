package com.spellingtrip.example.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;

import butterknife.ButterKnife;


/**
 * Created by yongdaA-B2 on 2017/7/28.
 */

public abstract class JieBaseActivity extends AppCompatActivity {
    public Activity activity;
    public String tag;
    protected boolean useThemestatusBarColor = false;
    protected boolean useStatusBarColor = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        //加载布局
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setStatusBar();
        //初始化控件
        initView();
        //获取数据
        getData();
        //设置数据
        setData();

    }
    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
             getWindow().setStatusBarColor(getResources().getColor(R.color.main_bg));
            if (isLightColor(getResources().getColor(R.color.main_bg))) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void onShow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.articleTitle));
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                       /* | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION*/

        );
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void getData();

    protected abstract void setData();

    //左边按钮
    public ImageView backClick() {
        ImageView button = (ImageView) findViewById(R.id.iv_left);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return button;
    }

    /**
     * 设置中心标题
     *
     * @param title 标题
     * @return
     */
    public TextView setCenterTitle(String title) {
        TextView textView = (TextView) findViewById(R.id.tv_lantitle);
        textView.setText(title);
        return textView;
    }

    /**
     * 设置标题背景颜色
     */
    public RelativeLayout setTitleBackgroud() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.tv_toplan);
       // relativeLayout.setBackgroundColor(getResources().getColor(R.color.searchblue));
        return relativeLayout;
    }
}
