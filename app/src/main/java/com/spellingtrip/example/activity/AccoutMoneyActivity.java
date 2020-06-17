package com.spellingtrip.example.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AccoutMoneyActivity extends BaseActivity{
    @BindView(R.id.tvAccountOrderList)
    public TextView tvAccountOrderList;
    @BindView(R.id.tvAccountMoneyNumber)
    public TextView tvAccountMoneyNumber;
    @BindView(R.id.ivAccoutMoneyChong)
    public ImageView ivAccoutMoneyChong;
    @BindView(R.id.ivAccoutMoneyTiXian)
    public ImageView ivAccoutMoneyTiXian;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_accoutmoney;
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
        setCenterTitle("余额");
    }
    @OnClick({R.id.tvAccountOrderList,R.id.ivAccoutMoneyTiXian,R.id.ivAccoutMoneyChong})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tvAccountOrderList:
                ToastUtil.show("暂无数据");
                break;
            case R.id.ivAccoutMoneyChong:
                ToastUtil.show("暂未开放");
                break;
            case R.id.ivAccoutMoneyTiXian:
                ToastUtil.show("暂未开放");
                break;
        }
    }
}
