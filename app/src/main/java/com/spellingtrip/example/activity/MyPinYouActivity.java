package com.spellingtrip.example.activity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.MyCityActivityBean;
import com.spellingtrip.example.bean.MyPinYouBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.view.XListView;
import com.spellingtrip.example.viewpager.CityActivityAdapter;
import com.spellingtrip.example.viewpager.MyPinYouListAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MyPinYouActivity extends BaseActivity implements AdapterView.OnItemClickListener, OnRefreshLoadMoreListener {
    @BindView(R.id.mYPinRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.xlvMyPin)
    public RecyclerView xListView;
    private int page = 1;
    private String size = "10";
    private MyCityActivityBean cityActivityBean;
    private ArrayList<MyCityActivityBean.DataBean> citys;
    private CityActivityAdapter activityAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mypinyou;
    }
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        ClassicsHeader mClassicsHeader = (ClassicsHeader) refreshLayout.getRefreshHeader();
        mClassicsHeader.setEnableLastTime(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xListView.addItemDecoration(new SpacesItemDecoration(16));
        xListView.setLayoutManager(layoutManager);
    }

    @Override
    protected void getData() {
            getMyPin();
    }
    @Override
    protected void setData() {
        backClick();
        setCenterTitle("同城活动");
    }

    private void getMyPin() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.PUBLISH_ACTIVITY).addParams("page", String.valueOf(page)).addParams("size", size)
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId())).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    cityActivityBean = (MyCityActivityBean) JsonUtil.fromJson(s, MyCityActivityBean.class);
                    if (cityActivityBean.getCode() == 0) {
                        if (cityActivityBean.getData().size() > 0) {
                            if (citys == null) {
                                citys = new ArrayList<>();
                            }
                            citys.addAll(cityActivityBean.getData());
                            xListView.setVisibility(View.VISIBLE);
                            refreshLayout.setNoMoreData(false);
                            setMyCitys(citys);
                        } else {
                            if (citys!=null&&citys.size()>0){
                                xListView.setVisibility(View.VISIBLE);
                                refreshLayout.setNoMoreData(false);
                            }else {
                                xListView.setVisibility(View.GONE);
                                refreshLayout.setNoMoreData(true);
                            }
                        }
                    } else {
                        ToastUtil.show(cityActivityBean.getMsg());
                    }
                }
            }
        });
    }
    private void setMyCitys(ArrayList<MyCityActivityBean.DataBean> citys) {
        if (activityAdapter==null){
            activityAdapter = new CityActivityAdapter(citys, MyPinYouActivity.this);
            xListView.setAdapter(activityAdapter);
        }else {
            activityAdapter.notifyDataSetChanged();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventType data) {
        if (data.getMsg().equals(Constant.PUBLISH_ACTIVITY)) {
            onRefresh(refreshLayout);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // adapter.selectIndex = position;
        //adapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
       getData();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        if (citys!=null&&citys.size()>0){
            citys.clear();
        }
       getData();
        refreshLayout.finishRefresh();
    }
}
