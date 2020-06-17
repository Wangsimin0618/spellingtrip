package com.spellingtrip.example.activity;

import android.view.View;
import android.widget.TextView;

import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class UsetAboutUsActivity extends BaseActivity{
    @BindView(R.id.tvaboutusv)
    public TextView tvaboutusv;
    @BindView(R.id.tvContactWay)
    public TextView tvContactWay;
    @BindView(R.id.tvConceal)
    public TextView tvConcel;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutus;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {

    }
@OnClick({R.id.tvContactWay,R.id.tvConceal})
public void onClick(View view){
        switch (view.getId()){
            case R.id.tvConceal:
                ActivityUtils.skipActivity(this,ConcealActivity.class,0,"");
                break;
            case R.id.tvContactWay:
                ActivityUtils.skipActivity(this,ContactWayActivity.class,0,"");
                break;
        }
}
    @Override
    protected void setData() {
        backClick();
        setCenterTitle("关于我们");
        tvaboutusv.setText("版本:V"+CustomApplication.getInstance().getAppVersion());
    }
}
