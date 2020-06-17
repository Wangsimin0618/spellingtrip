package com.spellingtrip.example.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.MyPinYouBean;
import com.spellingtrip.example.fragment.BaseFragment;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.AACityAdapter;
import com.spellingtrip.example.viewpager.MyPinYouListAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class MyJieBanActivity extends BaseFragment implements OnRefreshLoadMoreListener {
    @BindView(R.id.jieBanRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.jieBanRecyclerView)
    public XRecyclerView recyclerView;
    @BindView(R.id.rlNODataPublishPin)
    public RelativeLayout rlNODataPublishPin;
    private int page=1;
    private ActivityAABean pinYouBean;
    private List<ActivityAABean.DataBean> rows;
    private AACityAdapter aaCityAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_jieban;
    }

    @Override
    protected void findView(View view) {
        EventBus.getDefault().register(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(true);
        ClassicsHeader mClassicsHeader = (ClassicsHeader) refreshLayout.getRefreshHeader();
        mClassicsHeader.setEnableLastTime(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setPullRefreshEnabled(false);
    }


    @Override
    protected void getData() {
        //智能结伴
        getMyPin();
    }
    private void getMyPin() {
       String userid= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.MYPIN).addParams("page", String.valueOf(page))
                .addParams("userId",userid ).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    pinYouBean = (ActivityAABean) JsonUtil.fromJson(s, ActivityAABean.class);
                    if (pinYouBean.getCode() == 0) {
                        if (pinYouBean.getData().size() > 0) {
                            if (rows == null) {
                                rows = new ArrayList<>();
                            }
                            rows.addAll(pinYouBean.getData());
                            rlNODataPublishPin.setVisibility(View.GONE);
                            refreshLayout.setVisibility(View.VISIBLE);
                            setPinYouList(rows);
                        } else {
                            if (rows!=null&&rows.size()>0){
                                rlNODataPublishPin.setVisibility(View.GONE);
                                refreshLayout.setVisibility(View.VISIBLE);
                            }else {
                                rlNODataPublishPin.setVisibility(View.VISIBLE);
                                refreshLayout.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        rlNODataPublishPin.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                        ToastUtil.show(pinYouBean.getMsg());

                    }
                }
            }
        });
    }
    private void setPinYouList(List<ActivityAABean.DataBean> citys) {
        if (aaCityAdapter==null){
            aaCityAdapter=new AACityAdapter(getActivity(),citys,"only");
            recyclerView.setAdapter(aaCityAdapter);
        }else {
            aaCityAdapter.notifyDataSetChanged();
        }

    }
    @Override
    protected void setData() {
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventType data) {
        if (data.getMsg().equals(ConstantsBean.AUTOPI)) {
            onRefresh(refreshLayout);
        }
    }
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getData();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK){
            refreshLayout.finishRefresh();
        }
        page = 1;
        if (rows != null && rows.size() > 0) {
            rows.clear();
        }
        getData();
        refreshLayout.finishRefresh();
    }

}
