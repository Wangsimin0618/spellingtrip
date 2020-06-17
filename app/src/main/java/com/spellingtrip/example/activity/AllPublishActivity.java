package com.spellingtrip.example.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.spellingtrip.example.R;
import com.spellingtrip.example.viewpager.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AllPublishActivity extends BaseActivity{
    @BindView(R.id.allTabLayout)
    public TabLayout tableLayout;
    @BindView(R.id.allViewPager)
    public ViewPager viewPager;
    @BindView(R.id.ivAllPublish)
    public ImageView ivAllPublish;
    private String titles[] = new String[]{"我的结伴","我的游圈"};
    private List<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_allpublish;
    }

    @Override
    protected void initView() {
        MyJieBanActivity jeibanFragment = new MyJieBanActivity();
        MinePublishActivity publishFragment = new MinePublishActivity();
        fragmentList.add(jeibanFragment);
        fragmentList.add(publishFragment);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,titles));
        viewPager.setOffscreenPageLimit(2);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabIndicatorFullWidth(false);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {

    }
    @OnClick({R.id.ivAllPublish})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ivAllPublish:
                finish();
                break;
        }
    }
}
