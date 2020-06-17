package com.spellingtrip.example.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.CameraActivity;
import com.spellingtrip.example.activity.LogineActivity;
import com.spellingtrip.example.activity.PublishTourismActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.HotPinBean;
import com.spellingtrip.example.bean.TourisnBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;

import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;

import com.spellingtrip.example.utils.WrapContentLinearLayoutManager;
import com.spellingtrip.example.view.CommItemDecoration;
import com.spellingtrip.example.viewpager.TourismListAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import okhttp3.Call;

/**
 * 游圈
 */
public class TourismFragment extends BaseFragment implements   OnRefreshLoadMoreListener {
    @BindView(R.id.tvTitleFragment)
    public TextView tvTitleFragment;
    @BindView(R.id.ivFragmentRight)
    public ImageView ivFragmentRight;
    @BindView(R.id.refreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.xrvTourism)
    public RecyclerView xListView;
    @BindView(R.id.llNOWifi)
    public LinearLayout llNOWifi;
    @BindView(R.id.ivFragmentQianDao)
    public ImageView ivFragmentQianDao;
    private TourismListAdapter adapter;
    private String type = "init";
    private String travelId="";
    private List<TourisnBean.DataBean> travels = new ArrayList<>();
    private int CAMERA = 102;
    private LogineDialog logineDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tourism;
    }

    @Override
    protected void findView(View view) {
        EventBus.getDefault().register(this);
        LinearLayoutManager layoutManager = new  WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        xListView.setLayoutManager(layoutManager);
        xListView.setItemAnimator(null);
        xListView.setItemViewCacheSize(10);
        xListView.setHasFixedSize(true);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setDisableContentWhenRefresh(false);
        refreshLayout.setDisableContentWhenLoading(false);

    }

    @Override
    protected void getData() {
        if (logineDialog==null){
            logineDialog=new LogineDialog(getActivity(),"正在加载");
        }
        logineDialog.show();
        setLists();
    }

    private void setLists() {
        if (travels!=null&&travels.size()>0){
            travels.clear();
        }
        String lists= PreferenceUtil.getString(ConstantsBean.Spell,ConstantsBean.TOURISMLISTS);
        if (lists!=null&&!TextUtils.isEmpty(lists)){
            TourisnBean  tourisnBean= (TourisnBean) JsonUtil.fromJson(lists,TourisnBean.class);
            if (tourisnBean.getData() != null && tourisnBean.getCode() == 0 ) {
                if (  logineDialog!=null&&logineDialog.isShowing()){
                    logineDialog.dismiss();
                }
                if (tourisnBean.getData().size()>0){
                    int mSize=tourisnBean.getData().size()-1;
                    travelId = String.valueOf(tourisnBean.getData().get(mSize).getId());
                    travels.addAll(tourisnBean.getData());
                    setToutism(travels);
                }else {
                }
            } else if (tourisnBean.getCode() == 1) {
                ToastUtil.show(tourisnBean.getMsg() + "");
            }
        }else {
            getLists("init");
        }
    }
    private void getLists(String type) {
        if (UserTask.getInstance().isLogin()){
            getTourism(String.valueOf(UserTask.getInstance().getUser().getUserId()),type);
        }else {
            getTourism("",type);
        }
    }

    private void getNetWork(String type) {
        if (type.equals(ConstantsBean.HAVENETWORK)) {
            refreshLayout.setVisibility(View.VISIBLE);
            llNOWifi.setVisibility(View.GONE);
            setLists();
        } else {
            ToastUtil.show("当前网络不可用");
            refreshLayout.finishLoadMore();
            refreshLayout.setVisibility(View.GONE);
            llNOWifi.setVisibility(View.VISIBLE);
        }
    }

    private void getTourism(String userid,String type) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.TOURISM).addParams("userId",userid)
                .addParams("id",travelId).addParams("type",type).build().connTimeOut(10000) .readTimeOut(10000).writeTimeOut(10000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                //ToastUtil.show(ConstantsBean.ERROR);
                if (  logineDialog!=null&&logineDialog.isShowing()){
                    logineDialog.dismiss();
                }
                if (type.equals("init")){
                    refreshLayout.finishRefresh();
                }
            }
            @Override
            public void onResponse(String s, int i) {
                if (  logineDialog!=null&&logineDialog.isShowing()){
                    logineDialog.dismiss();
                }
                if (type.equals("down")){
                    refreshLayout.finishLoadMore();
                }
                if (type.equals("init")){
                    if (travels!=null&&travels.size()>0){
                        travels.clear();
                    }
                    refreshLayout.finishRefresh();
                }
                if (!TextUtils.isEmpty(s)){
                    TourisnBean  tourisnBean= (TourisnBean) JsonUtil.fromJson(s,TourisnBean.class);
                    if (tourisnBean.getData() != null && tourisnBean.getCode() == 0 ) {
                        if (tourisnBean.getData().size()>0){
                            int mSize=tourisnBean.getData().size()-1;
                            travelId = String.valueOf(tourisnBean.getData().get(mSize).getId());
                            travels.addAll(tourisnBean.getData());
                            setToutism(travels);
                        }else {
                        }
                    } else if (tourisnBean.getCode() == 1) {
                        ToastUtil.show(tourisnBean.getMsg() + "");
                    }
                }else {
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.publish)) {
            onRefresh(refreshLayout);
        }
        if (data.getType().equals(ConstantsBean.HAVENETWORK)){
            getNetWork(ConstantsBean.HAVENETWORK);
        }
        if (data.getType().equals(ConstantsBean.NONETWORK)){
            getNetWork(ConstantsBean.NONETWORK);
        }
    }
    @OnClick({R.id.ivFragmentRight,R.id.ivFragmentQianDao,R.id.llNOWifi})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ivFragmentRight:
                if (UserTask.getInstance().isLogin()) {
                    setRequstAuth();
                } else {
                    ActivityUtils.skipActivity(getActivity(), LogineActivity.class, 0, "");
                }
                break;
            case R.id.ivFragmentQianDao:
                CommonDialog.getQianDaoDialog(getActivity());
                break;
            case R.id.llNOWifi:
               /* if (isRegistered){
                    getNetWork(ConstantsBean.HAVENETWORK);
                }else {
                    getNetWork(ConstantsBean.NONETWORK);
                }*/
                break;
        }
    }
    private void setRequstAuth() {
        //获取相机
        if (CommonUtil.setCamera(getActivity())) {
            CommonUtil.setRequseCamera(getActivity(), CAMERA);
        } else {
            ActivityUtils.skipActivity(getActivity(),CameraActivity.class, 0, "");
        }
    }
    @Override
    protected void setData() {
        tvTitleFragment.setText("游圈");
        ivFragmentRight.setVisibility(View.VISIBLE);
        setToutism(travels);
    }

    private void setToutism(List<TourisnBean.DataBean> travels) {
        if (adapter==null){
            adapter = new TourismListAdapter(getActivity());
            adapter.setData(travels);
            xListView.setAdapter(adapter);
        }else {
            adapter.setData(travels);
            // adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 102:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ActivityUtils.skipActivity(getActivity(),CameraActivity.class, 0, "");
                } else {
                    ToastUtil.show("设置相机权限");
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        travelId = "";
        type = "";
        if (travels!=null&&travels.size()>0){
            travels.clear();
        }
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK){
            refreshLayout.finishRefresh();
        }
        travelId = "";
        getLists("init");
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK){
            refreshLayout.finishLoadMore();
        }else {
            getLists("down");
        }

    }
}
