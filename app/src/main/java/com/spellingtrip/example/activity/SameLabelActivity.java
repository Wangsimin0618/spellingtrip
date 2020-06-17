package com.spellingtrip.example.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.Label;
import com.spellingtrip.example.bean.LabelBean;
import com.spellingtrip.example.bean.LabelsEvent;
import com.spellingtrip.example.bean.MessageEvent;
import com.spellingtrip.example.bean.TwoMessageEvent;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.sticky.StickyListHeadersListView;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.viewpager.LabelsAdapter;
import com.spellingtrip.example.viewpager.SameLabelAdapter;
import com.spellingtrip.example.viewpager.SetLabelAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class SameLabelActivity extends BaseActivity {

    public static final String TAG = "SameLabelActivity";

    @BindView(R.id.sameSetLabel)
    RecyclerView sameSetLabel;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_lantitle)
    TextView tvLantitle;
    @BindView(R.id.tvSetLabelFinish)
    TextView tvSetLabelFinish;

    private Label labelBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_samelabel;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void getData() {
        getLaBel();
    }

    @Override
    protected void setData() {

    }

    private void getLaBel() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + "/api/activity/typelist").build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    labelBean = (Label) JsonUtil.fromJson(s, Label.class);
                    if (labelBean.getCode() == 0 && labelBean.getData().size() > 0) {
                        initDatas(labelBean.getData());
                    } else {
                        ToastUtil.show(labelBean.getMsg());
                    }
                }
            }
        });
    }

    private void initDatas(List<Label.DataBean> data) {
        sameSetLabel.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        LabelsAdapter adapter = new LabelsAdapter(data);
        sameSetLabel.setAdapter(adapter);
        adapter.setOnItemClickListener(new LabelsAdapter.onItemClickListener() {
            @Override
            public void onclick(String name,String id) {
                EventBus.getDefault().post(new LabelsEvent(id,name));
                finish();
            }
        });

    }

    @OnClick({R.id.iv_left, R.id.tvSetLabelFinish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tvSetLabelFinish:
                break;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

