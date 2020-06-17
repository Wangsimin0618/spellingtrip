package com.spellingtrip.example.activity;

import android.widget.TextView;

import com.spellingtrip.example.R;

import butterknife.BindView;

public class YuDingActivity extends BaseActivity{
    @BindView(R.id.tvYuDingTell)
    public TextView tvYuDingTell;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_yuding;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        String tel=getIntent().getStringExtra("title");
        tvYuDingTell.setText("联系电话："+tel);
        backClick();
        setCenterTitle("民宿预定");
    }
}
