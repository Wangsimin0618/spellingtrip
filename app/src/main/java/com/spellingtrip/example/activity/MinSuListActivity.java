package com.spellingtrip.example.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.autonavi.rtbt.IFrameForRTBT;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.LiveListBean;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.utils.WrapContentLinearLayoutManager;
import com.spellingtrip.example.viewpager.LiveListAdapter;
import com.spellingtrip.example.viewpager.MineCollectActivityAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MinSuListActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, OnRefreshLoadMoreListener, AMapLocationListener {
    @BindView(R.id.ivMinSuleft)
    public ImageView ivMinSuleft;
    @BindView(R.id.rgMinSu)
    public RadioGroup radioGroup;
    @BindView(R.id.rbHotList)
    public RadioButton rbHotList;
    @BindView(R.id.rbNearbyList)
    public RadioButton rbNearbyList;
    @BindView(R.id.tvNearLink)
    public TextView tvNearLink;
    @BindView(R.id.tvHotLink)
    public TextView tvHotLink;
    @BindView(R.id.minSuMinSu)
    public XRecyclerView recyclerView;
    @BindView(R.id.minSuSmart)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.tvMinSuNull)
    public TextView tvMinSuNull;
    private int page=1;
    private String type="2";
    private List<LiveListBean.DataBean> lives=new ArrayList<>();
    private LiveListAdapter adapter;
    private String lat="";
    private String lng="";
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_minsu;
    }

    @Override
    protected void initView() {
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(true);
        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setPullRefreshEnabled(false);
        ClassicsHeader mClassicsHeader = (ClassicsHeader)refreshLayout.getRefreshHeader();
        mClassicsHeader.setEnableLastTime(false);
        radioGroup.setOnCheckedChangeListener(this);
        initLocation();
        View minsu= LayoutInflater.from(MinSuListActivity.this).inflate(R.layout.item_minsutop,null);
        ImageView ivItemMinSuZhu=minsu.findViewById(R.id.ivItemMinSuZhu);
        ivItemMinSuZhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        recyclerView.addHeaderView(minsu);
    }

    private void initLocation() {
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);//定位一次
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }

    @Override
    protected void getData() {
        getLive();
    }

    private void getLive() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH+ConstantsBean.LIVE).addParams(ConstantsBean.TYPE,type).addParams(ConstantsBean.PAGE, String.valueOf(page))
                .addParams(ConstantsBean.SIZE,"10").addParams("lat",lat).addParams("lng",lng).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    LiveListBean liveListBean= (LiveListBean) JsonUtil.fromJson(s,LiveListBean.class);
                    if (liveListBean.getCode()==0){
                        if (liveListBean.getData()!=null&&liveListBean.getData().size()>0){
                            tvMinSuNull.setVisibility(View.GONE);
                            refreshLayout.setVisibility(View.VISIBLE);
                            lives.addAll(liveListBean.getData());
                            setLives(lives);
                        }else {
                            if (lives.size()>0){
                                tvMinSuNull.setVisibility(View.GONE);
                                refreshLayout.setVisibility(View.VISIBLE);
                            }else {
                                tvMinSuNull.setVisibility(View.VISIBLE);
                                refreshLayout.setVisibility(View.GONE);
                            }

                        }
                    }else {
                        tvMinSuNull.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 列表数据
     * @param lives
     */
    private void setLives(List<LiveListBean.DataBean> lives) {
        if (adapter==null){
            adapter = new LiveListAdapter(lives,MinSuListActivity.this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setData() {

    }
    @OnClick({R.id.ivMinSuleft})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ivMinSuleft:
                finish();
                break;
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rbHotList:
                rbHotList.setChecked(true);
                tvHotLink.setVisibility(View.VISIBLE);
                tvNearLink.setVisibility(View.INVISIBLE);
                type="2";
                lat="";
                lng="";
                onRefresh(refreshLayout);
                break;
            case R.id.rbNearbyList:
                rbNearbyList.setChecked(true);
                tvHotLink.setVisibility(View.INVISIBLE);
                tvNearLink.setVisibility(View.VISIBLE);
                type="1";
                lat= PreferenceUtil.getString(ConstantsBean.Spell,ConstantsBean.LAT);
                lng= PreferenceUtil.getString(ConstantsBean.Spell,ConstantsBean.LNG);
                onRefresh(refreshLayout);
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getLive();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (lives.size()>0){
            lives.clear();
        }
        page=1;
        getLive();
        refreshLayout.finishRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lives.size()>0){
            lives.clear();
        }
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                String mLatitude = String.valueOf(amapLocation.getLatitude());//获取纬度
                String mLongitude = String.valueOf(amapLocation.getLongitude());//获取经度
                amapLocation.getAccuracy();//获取精度信息
                PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.LAT,mLatitude);
                PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.LNG,mLongitude);
            } else {

            }
        }
    }

}
