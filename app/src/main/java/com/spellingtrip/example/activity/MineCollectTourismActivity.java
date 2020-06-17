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
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.MyCollectBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
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

public class MineCollectTourismActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    @BindView(R.id.collectSmartRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.collectRecyclerView)
    public RecyclerView recyclerView;
    private int page=1;
    private List<MyCollectBean.DataBean.RowsBean> collects=new ArrayList<>();
    private MineCollectAdapter collectAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_collecttourism;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        ClassicsHeader classicsHeader= (ClassicsHeader) refreshLayout.getRefreshHeader();
        classicsHeader.setEnableLastTime(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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
                .addParams("page", String.valueOf(page)).addParams("type","1").addParams("size","10")
                .build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                // ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    MyCollectBean myCollectBean= (MyCollectBean) JsonUtil.fromJson(s, MyCollectBean.class);
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

    private void setCollect(List<MyCollectBean.DataBean.RowsBean> collects) {
        if (collectAdapter==null){
            collectAdapter = new MineCollectAdapter(collects,MineCollectTourismActivity.this);
            recyclerView.setAdapter(collectAdapter);
        } else {
            collectAdapter.notifyDataSetChanged();
        }



    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.getMineCollect)) {
            onRefresh(refreshLayout);
        }
        if (data.getType().equals(Constant.UrlOrigin.IsLogine)){
            onRefresh(refreshLayout);
        }
    }
    @Override
    protected void setData() {
        backClick();
        setCenterTitle("我收藏的游圈");
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
