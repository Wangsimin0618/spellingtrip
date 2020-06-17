package com.spellingtrip.example.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.CityPeopleBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.viewpager.CityPeopleAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class CityPeoPleActvity extends BaseActivity{
    @BindView(R.id.lvCityPeoPle)
    public RecyclerView recyclerView;
    private CityPeopleAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_citypeople;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void getData() {
       String activityID= getIntent().getStringExtra("title");
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.CITY_PEOPEL).addParams("activityId",activityID)
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId())).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i){
                if (!TextUtils.isEmpty(s)){
                  CityPeopleBean cityPeopleBean= (CityPeopleBean) JsonUtil.fromJson(s,CityPeopleBean.class);
                  if (cityPeopleBean.getCode()==0&&cityPeopleBean.getData().size()>0){
                      setPeople(cityPeopleBean.getData());
                  }else {

                  }
                }
            }
        });
    }

    private void setPeople(List<CityPeopleBean.DataBean> data) {
         adapter=new CityPeopleAdapter(data,CityPeoPleActvity.this);
        recyclerView.setAdapter(adapter);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.REMOVE_PEOPLE)){
            getData();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("成员管理");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
