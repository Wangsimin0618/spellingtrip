package com.spellingtrip.example.fragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spellingtrip.example.utils.NetworkChangeReceiver;

import butterknife.ButterKnife;
public abstract class BaseFragment extends Fragment {
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), null);
        }
        ButterKnife.bind(this, view);
        findView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //注册网络状态监听广播
        getData();
        setData();
    }

    protected abstract int getLayoutId();

    protected abstract void findView(View view);

    protected abstract void getData();

    protected abstract void setData();



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }


}
