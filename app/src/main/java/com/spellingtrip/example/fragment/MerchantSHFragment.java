package com.spellingtrip.example.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 商家认证审核
 */
public class MerchantSHFragment extends BaseFragment {
    @BindView(R.id.btn_fh)
    Button btnFh;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchantsh;
    }

    @Override
    protected void findView(View view) {

    }

    @Override
    protected void getData() {

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
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_fh)
    public void onViewClicked() {
        EventBus.getDefault().post(new MessageEvent("返回首页"));
    }
}
