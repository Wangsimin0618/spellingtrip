package com.spellingtrip.example.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.WrapContentLinearLayoutManager;
import com.spellingtrip.example.viewpager.NoticeMeassageAdapter;
import com.spellingtrip.example.viewpager.NoticeRemindAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class NoticeRemindFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    @BindView(R.id.noticeRemindRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.noticeRemindRecyclerView)
    public RecyclerView recyclerView;
    private int page=1;
    private String size="10";
    private NoticeRemindAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notice_remind;
    }

    @Override
    protected void findView(View view) {
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
        getRemind();
    }

    private void getRemind() {
        String userId= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.NOTICE_MEASSAGE).addParams("userId",userId)
                .addParams("type","2").addParams("page", String.valueOf(page)).addParams("size",size)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
              if (!TextUtils.isEmpty(s)){
                  setReminds();
              }
            }
        });
    }

    private void setReminds() {
        if (adapter==null){
            adapter=new NoticeRemindAdapter(getActivity());
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setData() {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
refreshLayout.finishRefresh();
    }
}
