package com.spellingtrip.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spellingtrip.example.R;

/**
 * 热门推荐
 */
public class HottogetherFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_newest, container, false);
    }

    @Override
    protected int getLayoutId() {
        return 0;
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

}
