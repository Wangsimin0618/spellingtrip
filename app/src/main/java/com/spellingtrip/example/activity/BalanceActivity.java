package com.spellingtrip.example.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.fragment.BaseFragment;
import com.spellingtrip.example.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的钱包余额
 */
public class BalanceActivity extends BaseFragment {
    public static final String TAG = "BalanceActivity";


    @BindView(R.id.integral)
    TextView integral;
    @BindView(R.id.btn_cz)
    Button btnCz;
    @BindView(R.id.btn_tx)
    Button btnTx;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_balance;
    }

    @Override
    protected void findView(View view) {

    }

    @Override
    protected void getData() {
        integral.setText("0.00");
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

    @OnClick({R.id.btn_cz, R.id.btn_tx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cz:
                ToastUtil.show("暂未开发");
                Log.v(TAG,"---onViewClicked-----btn_cz");
                break;
            case R.id.btn_tx:
                ToastUtil.show("暂未开发");
                Log.v(TAG,"---onViewClicked-----btn_tx");

                break;
        }
    }
}
