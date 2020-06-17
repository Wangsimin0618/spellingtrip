package com.spellingtrip.example.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.spellingtrip.example.R;
import com.spellingtrip.example.fragment.HomeFragment;
import com.spellingtrip.example.fragment.NoticeMeassageFragment;

import butterknife.BindView;

public class DianZanListActivity extends BaseActivity{
    @BindView(R.id.fragmeDianZan)
    public FrameLayout fragmeDianZan;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dianzanlist;
    }

    @Override
    protected void initView() {
        NoticeMeassageFragment meassageFragment = new NoticeMeassageFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fragmeDianZan, meassageFragment);
        ft.show(meassageFragment).commit();
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("系统通知");
    }
}
