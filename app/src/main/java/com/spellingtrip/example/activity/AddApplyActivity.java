package com.spellingtrip.example.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.AddApplayBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.viewpager.AddApplyAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 申请加入列表
 */
public class AddApplyActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    @BindView(R.id.addApplyRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.addApplyRecycler)
    public RecyclerView recyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_addapply;
    }
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ClassicsHeader classicsHeader= (ClassicsHeader) refreshLayout.getRefreshHeader();
        classicsHeader.setEnableLastTime(false);
        refreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void getData() {
        getApplyList();
    }

    private void getApplyList() {
      String userId= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.APPLYADD_LIST).addParams("userId",userId)
               .build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
            }

            @Override
            public void onResponse(String s, int i) {
             if (!TextUtils.isEmpty(s)){
                 AddApplayBean addApplayBean= (AddApplayBean) JsonUtil.fromJson(s,AddApplayBean.class);
                 if (addApplayBean.getCode()==0){
                     if (addApplayBean.getData().size()>0){
                         setAddApply(addApplayBean.getData());
                     }else {
                     }
                 }else {
                 }
             }
            }
        });
    }

    private void setAddApply(List<AddApplayBean.DataBean> applays) {
        AddApplyAdapter addApplyAdapter=new AddApplyAdapter(AddApplyActivity.this,applays);
        recyclerView.setAdapter(addApplyAdapter);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.DETELE_ADDAPPLY)) {
            onRefresh(refreshLayout);
        }

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("系统通知");
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore();

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getApplyList();
        refreshLayout.finishRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
