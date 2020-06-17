package com.spellingtrip.example.activity;

import android.widget.TextView;

import com.spellingtrip.example.R;

import butterknife.BindView;

public class SpellingActivity extends BaseActivity{
    @BindView(R.id.tvTitleFragment)
    public TextView tvTitleFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_spelling;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        tvTitleFragment.setText("拼游");
    }
}
