package com.spellingtrip.example.activity;


import android.os.Bundle;
import android.view.View;

import com.spellingtrip.example.R;

import butterknife.OnClick;

/**
 * 商家发布
 */
public class MerchantActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_merchant;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.add_days, R.id.process_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_days:
                break;
            case R.id.process_left:
                break;
        }
    }


}
