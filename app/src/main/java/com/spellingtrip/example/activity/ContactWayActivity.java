package com.spellingtrip.example.activity;

import com.spellingtrip.example.R;

public class ContactWayActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_contactway;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("联系方式");
    }
}
