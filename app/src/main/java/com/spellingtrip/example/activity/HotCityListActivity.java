package com.spellingtrip.example.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.viewpager.AACityAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 热门城市
 */
public class HotCityListActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    @BindView(R.id.hotCityRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.rvHotCityDetailList)
    public XRecyclerView xRecyclerView;
    @BindView(R.id.ivHotCityPublish)
    public ImageView ivHotCityPublish;
    private LogineDialog logineDialog;
    private int page=1;
    private List<ActivityAABean.DataBean> citys=new ArrayList<>();
    private AACityAdapter aaCityAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.actctivity_hotcity;
    }

    @Override
    protected void initView() {
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        View view=LayoutInflater.from(this).inflate(R.layout.hotcity_header,null);
        xRecyclerView.addHeaderView(view);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setFocusableInTouchMode(false);
        xRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void getData() {
        if (logineDialog==null){
            logineDialog=new LogineDialog(this,"正在加载");
        }
        logineDialog.show();
        setCity();
    }

    private void setCity() {
        String city=getIntent().getStringExtra("title");
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH+ConstantsBean.AALIST).addParams("page", String.valueOf(page))
                .addParams("city",city).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (  logineDialog!=null&&logineDialog.isShowing()){
                    logineDialog.dismiss();
                }
            }

            @Override
            public void onResponse(String s, int i) {
                if (  logineDialog!=null&&logineDialog.isShowing()){
                    logineDialog.dismiss();
                }
                if (!TextUtils.isEmpty(s)){
                    if (s.contains("接口异常")){
                    }else {
                        ActivityAABean messageBean= (ActivityAABean) JsonUtil.fromJson(s,ActivityAABean.class);
                        if (messageBean.getCode()==0){
                            if (messageBean.getData().size()>0){
                                citys.addAll(messageBean.getData());
                                setAA(citys);
                            }else {
                            }
                        }else {
                        }
                    }
                }
            }
        });
    }
    private void setAA(List<ActivityAABean.DataBean> citys) {
        if (aaCityAdapter==null){
            aaCityAdapter=new AACityAdapter(HotCityListActivity.this,citys,"home");
            xRecyclerView.setAdapter(aaCityAdapter);
        }else {
            aaCityAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void setData() {
        backClick();
        String city=getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(city)){
            setCenterTitle(city);
        }else {
            setCenterTitle("热门城市");
        }

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
       setCity();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK){
            refreshLayout.finishRefresh();
        }
        page=1;
        if (citys!=null&&citys.size()>0){
            citys.clear();
        }
        setCity();
        refreshLayout.finishRefresh();
    }
    @OnClick({R.id.ivHotCityPublish})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ivHotCityPublish:
                ActivityUtils.skipActivity(HotCityListActivity.this,PublishPinActivity.class,0,"");
                break;
        }
    }
}
