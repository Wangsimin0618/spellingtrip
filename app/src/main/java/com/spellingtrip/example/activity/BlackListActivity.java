package com.spellingtrip.example.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.BlackListBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.viewpager.BlackListAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 我的：黑名单页面
 */
public class BlackListActivity extends BaseActivity{
    @BindView(R.id.lvBlackList)
    public XRecyclerView xRecyclerView;
    private int page=1;
    private int size=10;
    private BlackListAdapter blackListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_blacklist;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);

    }

    @Override
    protected void getData() {
        getBlackList();
    }

    private void getBlackList() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH+ConstantsBean.BLACKLIST).addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .addParams("page", String.valueOf(page)).addParams("size", String.valueOf(size)).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
               if (!TextUtils.isEmpty(s)){
                   BlackListBean blackListBean= (BlackListBean) JsonUtil.fromJson(s,BlackListBean.class);
                   if (blackListBean.getCode()==0&&blackListBean.getData().getRows()!=null&&blackListBean.getData().getRows().size()>0){
                       xRecyclerView.setVisibility(View.VISIBLE);
                       blackListAdapter= new BlackListAdapter(blackListBean.getData().getRows(),BlackListActivity.this);
                       xRecyclerView.setAdapter(blackListAdapter);
                       blackListAdapter.setItemClickListener(new BlackListAdapter.OnItemClickListener() {
                           @Override
                           public void onItemClick(View view, int position) {
                               setRemove(position);
                           }
                       });
                   }else {
                       xRecyclerView.setVisibility(View.GONE);
                   }
               }
            }
        });
    }

    /**
     * 移除
     * @param position
     */
    private void setRemove(int position) {
        HttpRequest.setReMove(position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventType data) {
        if (data.getMsg().equals(Constant.UrlOrigin.BlackList)){
            getBlackList();
            blackListAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void setData() {
        backClick();
        setCenterTitle("黑名单");
    }
}
