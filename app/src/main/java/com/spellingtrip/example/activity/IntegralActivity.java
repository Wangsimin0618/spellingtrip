package com.spellingtrip.example.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的钱包积分
 */
public class IntegralActivity extends BaseFragment {

    @BindView(R.id.integral)
    TextView integral;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral;
    }

    @Override
    protected void findView(View view) {

    }

    @Override
    protected void getData() {
        integral.setText("暂无积分");

    }

    @Override
    protected void setData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
