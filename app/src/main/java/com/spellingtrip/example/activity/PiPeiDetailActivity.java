package com.spellingtrip.example.activity;

import android.icu.text.AlphabeticIndex;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.PiPeiRecordBean;

import com.spellingtrip.example.fragment.BaseFragment;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.XListView;
import com.spellingtrip.example.viewpager.RecordAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 匹配详情
 */
public class PiPeiDetailActivity extends BaseFragment implements  OnRefreshLoadMoreListener, View.OnClickListener {
    @BindView(R.id.lvPiPeiDetailList)
    public XRecyclerView xRecyclerView;
    @BindView(R.id.piPeiRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    public ImageView ivPiMinSu;
    private String infoId = "";
    private String type = "";
    private RecordAdapter adapter;
    private ArrayList<PiPeiRecordBean.DataBean> lists = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_peipeidetail;
    }

    @Override
    protected void findView(View v) {
        EventBus.getDefault().register(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setEnableRefresh(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.pipei_header,null);
        ivPiMinSu=view.findViewById(R.id.ivPiMinSu);
        ivPiMinSu.setOnClickListener(this);
        xRecyclerView.addHeaderView(view);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setFocusableInTouchMode(false);
        xRecyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void getData() {
        getDetail();
    }


    private void getDetail() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.NEWPULISH).addParams("userId", "").addParams("infoId", infoId)
                .addParams("type", type).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    PiPeiRecordBean recordBean = (PiPeiRecordBean) JsonUtil.fromJson(s, PiPeiRecordBean.class);
                    if (recordBean.getCode() == 0) {
                        if (recordBean.getData().size() > 0) {
                            if (type.equals("up")) {
                                infoId = String.valueOf(recordBean.getData().get(recordBean.getData().size()).getInfoId());
                            }
                            lists.addAll(recordBean.getData());
                            setInfo(recordBean.getData());
                        } else {
                        }
                    } else {
                        ToastUtil.show(recordBean.getMsg());
                    }
                } else {

                }
            }
        });
    }



    private void setInfo(List<PiPeiRecordBean.DataBean> data) {
        adapter = new RecordAdapter(data, getActivity(), ConstantsBean.PIPEI);
        xRecyclerView.setAdapter(adapter);
    }
   @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ivPiMinSu:
                ActivityUtils.skipActivity(getActivity(),MinSuListActivity.class,0,"");
                break;
        }
    }
    @Override
    protected void setData() {
     /*   backClick();
        setCenterTitle("最新结伴");*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventType data) {
        if (data.getMsg().equals(ConstantsBean.AUTOPI)) {
            getDetail();
        }

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        type = "up";
        getData();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        type = "";
        infoId = "";
        if (lists.size() > 0) {
            lists.clear();
        }
        getData();
        refreshLayout.finishRefresh();
    }
}
