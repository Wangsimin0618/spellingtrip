package com.spellingtrip.example.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.viewpager.HomeitemAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class SeachResultActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    public static final String TAG = "SeachResultActivity";

    @BindView(R.id.result_fh)
    ImageView resultFh;
    @BindView(R.id.result_xrecy)
    XRecyclerView resultXrecy;
    @BindView(R.id.footer_progressbar)
    ProgressBar footerProgressbar;
    @BindView(R.id.footer_hint_text)
    TextView footerHintText;
    @BindView(R.id.result_refresh)
    SmartRefreshLayout resultRefresh;
    private LogineDialog logineDialog;
    private List<ActivityAABean.DataBean> citys = new ArrayList<>();
    private HomeitemAdapter aaCityAdapter;
    private String mKeyword;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seachresult;

    }

    @Override
    protected void initView() {
        mKeyword = getIntent().getStringExtra("title");
        if (mKeyword==null){
            return;
        }
        resultRefresh.setOnRefreshLoadMoreListener(this);
        resultRefresh.setDisableContentWhenRefresh(true);
        resultRefresh.setDisableContentWhenLoading(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        View view= LayoutInflater.from(this).inflate(R.layout.hotcity_header,null);
        TextView txt = view.findViewById(R.id.txt);
        txt.setVisibility(View.GONE);
        resultXrecy.addHeaderView(view);
        resultXrecy.setPullRefreshEnabled(false);
        resultXrecy.setLoadingMoreEnabled(false);
        resultXrecy.setFocusableInTouchMode(false);
        resultXrecy.setLayoutManager(layoutManager);

    }

    @Override
    protected void getData() {
        Log.v(TAG, "---------getData-------");

        if (logineDialog == null) {
            logineDialog = new LogineDialog(SeachResultActivity.this, "正在加载");
        }
        logineDialog.show();

        getSeach(mKeyword);
    }

    private void getSeach(String keyword) {
        Log.v(TAG,"-----getSeach()----keyword=="+keyword);
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH+ConstantsBean.AALIST).addParams("page", String.valueOf(page))
                .addParams("city",keyword).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog != null && logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
            }

            @Override
            public void onResponse(String s, int i) {
                if (logineDialog != null && logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                if (page > 1) {
                    resultRefresh.finishLoadMore();
                } else {
                    resultRefresh.finishRefresh();
                }
                if (!TextUtils.isEmpty(s)) {
                    if (s.contains("接口异常")) {
                        setSeachResult(citys);
                    } else {
                        ActivityAABean messageBean = (ActivityAABean) JsonUtil.fromJson(s, ActivityAABean.class);
                        if (messageBean.getCode() == 0) {
                            if (messageBean.getData().size() > 0) {
                                citys.addAll(messageBean.getData());
                                setSeachResult(citys);
                            } else {
                            }
                        } else {
                        }
                    }
                }
            }
        });
    }

    private void setSeachResult(List<ActivityAABean.DataBean> citys) {
        if (aaCityAdapter == null) {
            aaCityAdapter = new HomeitemAdapter(SeachResultActivity.this, citys, "home");
            resultXrecy.setAdapter(aaCityAdapter);
        } else {
            aaCityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setData() {

    }

    @OnClick(R.id.result_fh)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
       getSeach(mKeyword);
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
        getSeach(mKeyword);
        refreshLayout.finishRefresh();
    }

    @Override
    protected void onDestroy() {
        //处理dialog异常
        if (logineDialog != null && logineDialog.isShowing()) {
            logineDialog.dismiss();
        }
        super.onDestroy();
    }
}
