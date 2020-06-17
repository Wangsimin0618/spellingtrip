package com.spellingtrip.example.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.autonavi.rtbt.IFrameForRTBT;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.NoticeMeassageBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.WrapContentLinearLayoutManager;
import com.spellingtrip.example.viewpager.NoticeMeassageAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import okhttp3.Call;

public class NoticeMeassageFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    @BindView(R.id.noticeMeassageRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.noticeMeassageRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.rlNODataMessage)
    public RelativeLayout rlNODataMessage;
    private int page=1;
    private String size="10";
    private List<NoticeMeassageBean.DataBean> meassages=new ArrayList<>();
    private NoticeMeassageAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notice_meassage;
    }

    @Override
    protected void findView(View view) {
        EventBus.getDefault().register(this);
       ClassicsHeader classicsHeader= (ClassicsHeader) refreshLayout.getRefreshHeader();
       classicsHeader.setEnableLastTime(false);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(true);
        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    protected void getData() {
        getMeassage();
    }

    /**
     * 获取信息列表
     */
    private void getMeassage() {
       String userId= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.NOTICE_MEASSAGE).addParams("userId",userId)
                .addParams("type","1").addParams("page", String.valueOf(page)).addParams("size",size)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    NoticeMeassageBean meassageBean= (NoticeMeassageBean) JsonUtil.fromJson(s,NoticeMeassageBean.class);
                    if (meassageBean.getCode()==0){
                        if (meassageBean.getData().size()>0){
                            meassages.addAll(meassageBean.getData());
                            setMeassage(meassages);
                        }else {

                        }
                    }else {

                    }
                }
            }
        });
    }

    private void setMeassage(List<NoticeMeassageBean.DataBean> meassages) {
        if (adapter==null){
             adapter=new NoticeMeassageAdapter(meassages,getActivity());
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.DETELE_ADDAPPLY)) {
            onRefresh(refreshLayout);
        }

    }
    @Override
    protected void setData() {
    }
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getMeassage();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page=1;
        if (meassages.size()>0){
            meassages.clear();
        }
        getMeassage();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
