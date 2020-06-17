package com.spellingtrip.example.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.MyPublishBean;
import com.spellingtrip.example.fragment.BaseFragment;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.MinePublishRecyclerAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MinePublishActivity extends BaseFragment implements OnRefreshLoadMoreListener {
    @BindView(R.id.publishSmartRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.publishRecyclerView)
    public RecyclerView recyclerView;
    private String type = "init";
    private String travelId = "";
    private List<MyPublishBean.DataBean> travels = new ArrayList<>();
    private MinePublishRecyclerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_minepublish;
    }

    @Override
    protected void findView(View view) {
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(true);
        ClassicsHeader mClassicsHeader = (ClassicsHeader) refreshLayout.getRefreshHeader();
        mClassicsHeader.setEnableLastTime(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void getData() {
        if (UserTask.getInstance().isLogin()) {
            getMinePublish(String.valueOf(UserTask.getInstance().getUser().getUserId()), type, travelId);
        }
    }
    private void getMinePublish(String userid, String type, String id) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.MYPUBLISH).addParams("userId", userid)
                .addParams("id", id).addParams("type", type).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                // ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    MyPublishBean tourisnBean = (MyPublishBean) JsonUtil.fromJson(s, MyPublishBean.class);
                    if (tourisnBean.getCode() == 0) {
                        if (type.equals("down") && tourisnBean.getData() != null && tourisnBean.getData().size() > 0) {
                            travelId = String.valueOf(tourisnBean.getData().get(0).getId());
                        }
                        travels.addAll(tourisnBean.getData());
                        setToutism(travels);
                    } else {

                    }
                } else {

                }
            }
        });
    }
    private void setToutism(List<MyPublishBean.DataBean> travels) {
        if (adapter==null){
            adapter = new MinePublishRecyclerAdapter(getActivity(), travels);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }
    @Override
    protected void setData() {
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        type = "down";
        getData();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK){
            refreshLayout.finishRefresh();
        }
        type = "init";
        travelId = "";
        if (travels != null && travels.size() > 0) {
            travels.clear();
        }
        getData();
        refreshLayout.finishRefresh();
    }
}
