package com.spellingtrip.example.fragment;

import android.view.View;
import android.widget.TextView;

import com.spellingtrip.example.R;

import butterknife.BindView;

public class MessageFragment extends BaseFragment {
    @BindView(R.id.tvTitleFragment)
    public TextView tvTitleFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void findView(View view) {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        tvTitleFragment.setText("消息");
    }
}
