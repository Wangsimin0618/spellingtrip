package com.spellingtrip.example.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.MineCollectActivityBean;
import com.spellingtrip.example.bean.MyCollectBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.viewpager.MineCollectActivityAdapter;
import com.spellingtrip.example.viewpager.MineCollectAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class MineCollectActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    @BindView(R.id.citySmartRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.cityRecyclerView)
    public RecyclerView recyclerView;
    private int page=1;
    private List<MineCollectActivityBean.DataBean.RowsBean> collects=new ArrayList<>();
    private MineCollectActivityAdapter collectAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(true);
        ClassicsHeader classicsHeader= (ClassicsHeader) refreshLayout.getRefreshHeader();
        classicsHeader.setEnableLastTime(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void getData() {
        if (UserTask.getInstance().isLogin()){
            getCollect();
        }
    }

    private void getCollect() {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.MINECOLLECT+ String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .addParams("page", String.valueOf(page)).addParams("type","2").addParams("size","10")
                .build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    MineCollectActivityBean myCollectBean= (MineCollectActivityBean) JsonUtil.fromJson(s, MineCollectActivityBean.class);
                    if (myCollectBean.getCode()==0){
                        if (myCollectBean.getData().getRows()!=null&&myCollectBean.getData().getRows().size()>0){
                            collects.addAll(myCollectBean.getData().getRows());
                            setCollect(collects);
                        }else {
                            if (collects.size()>0){

                            }else {
                            }
                        }
                    }else {
                        Log.d("myCollectBean",myCollectBean.getMsg());
                    }
                }
            }
        });
    }
    private void setCollect(List<MineCollectActivityBean.DataBean.RowsBean> collects) {
        if (collectAdapter==null){
            collectAdapter = new MineCollectActivityAdapter(collects,MineCollectActivity.this);
            recyclerView.setAdapter(collectAdapter);
        } else {
            collectAdapter.notifyDataSetChanged();
        }



    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.COLLECT_ACTIVITY)) {
            onRefresh(refreshLayout);
        }
        if (data.getType().equals(Constant.UrlOrigin.IsLogine)){
            onRefresh(refreshLayout);
        }
    }
    @Override
    protected void setData() {
        backClick();
        setCenterTitle("我收藏的同城活动");
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getCollect();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page=1;
        if (collects.size()>0){
            collects.clear();
        }
        getCollect();
        refreshLayout.finishRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}