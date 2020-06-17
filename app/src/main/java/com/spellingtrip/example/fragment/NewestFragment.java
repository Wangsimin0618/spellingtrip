package com.spellingtrip.example.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.viewpager.HomeitemAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 最新推荐
 */
public class NewestFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    public static final String TAG = "NewestFragment";
    @BindView(R.id.home_recy)
    XRecyclerView homeRecy;
    Unbinder unbinder;
    @BindView(R.id.footer_progressbar)
    ProgressBar footerProgressbar;
    @BindView(R.id.footer_hint_text)
    TextView footerHintText;
    @BindView(R.id.piCityRefreshLayout)
    SmartRefreshLayout piCityRefreshLayout;

    private List<ActivityAABean.DataBean> citys = new ArrayList<>();

    private int page = 1;

    private HomeitemAdapter aaCityAdapter;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_newest, container, false);
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newest;
    }

    @Override
    protected void findView(View view) {

        piCityRefreshLayout.setOnRefreshLoadMoreListener(this);
        piCityRefreshLayout.setDisableContentWhenRefresh(true);
        piCityRefreshLayout.setDisableContentWhenLoading(false);
        piCityRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

//        homeRecy.addHeaderView(view);
//        homeRecy.setItemAnimator(null);
//        homeRecy.setItemViewCacheSize(10);
//        homeRecy.setPullRefreshEnabled(false);
//        homeRecy.setLoadingMoreEnabled(false);
//        homeRecy.setFocusableInTouchMode(false);
        homeRecy.setLayoutManager(layoutManager);
    }

    @Override
    protected void getData() {
        if (citys != null && citys.size() > 0) {
            citys.clear();
        }
        getNewsest();
    }

    @Override
    protected void setData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getNewsest() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.AALIST).addParams("city", "").addParams(ConstantsBean.PAGE, String.valueOf(page))
                .build().connTimeOut(10000).readTimeOut(50000).writeTimeOut(10000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
            }

            @Override
            public void onResponse(String s, int i) {
                if (page > 1) {
                    piCityRefreshLayout.finishLoadMore();
                } else {
                    piCityRefreshLayout.finishRefresh();
                }
                if (!TextUtils.isEmpty(s)) {
                    if (s.contains("接口异常")) {
                        setAA(citys);
                    } else {
                        ActivityAABean messageBean = (ActivityAABean) JsonUtil.fromJson(s, ActivityAABean.class);
                        if (messageBean.getCode() == 0) {
                            if (messageBean.getData().size() > 0) {
                                citys.addAll(messageBean.getData());
                                setAA(citys);
                            } else {
                            }
                        } else {
                        }
                    }
                }
            }
        });
    }

    private void setAA(List<ActivityAABean.DataBean> citys) {
        if (aaCityAdapter == null) {
            aaCityAdapter = new HomeitemAdapter(getActivity(), citys, "home");
            homeRecy.setAdapter(aaCityAdapter);
        } else {
            aaCityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK) {
            refreshLayout.finishLoadMore();
        } else {
            page++;
            getNewsest();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK) {
            refreshLayout.finishRefresh();
        } else {
            page = 1;
            getData();
//            getTourMach();
        }
    }
}
