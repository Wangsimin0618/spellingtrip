package com.spellingtrip.example.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ActivityAddBean;
import com.spellingtrip.example.bean.ActivityCallBack;
import com.spellingtrip.example.bean.ActivityCollectBean;
import com.spellingtrip.example.bean.ActivityDetailBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.huanxin.ChatActivity;
import com.spellingtrip.example.huanxin.ChatRoomDetailsActivity;
import com.spellingtrip.example.liaotian.UserCacheManager;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.MaxRecyclerView;
import com.spellingtrip.example.view.ScrollGridView;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.CityDetailImgAdapter;
import com.spellingtrip.example.viewpager.CityPeopleDetailAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/***
 * 老版本结伴详情
 */
public class CityActivityDetailActivity extends BaseActivity {
    @BindView(R.id.gvCityPeople)
    public ScrollGridView scrollGridView;
    @BindView(R.id.rlCityDetailTime)
    public TextView rlCityDetailTime;
    @BindView(R.id.rlDetailType)
    public TextView rlDetailType;
    @BindView(R.id.rlCityDetailLocation)
    public TextView rlCityDetailLocation;
    @BindView(R.id.rlDetailMoney)
    public TextView rlDetailMoney;
    @BindView(R.id.tvCityPayType)
    public TextView tvCityPayType;
    @BindView(R.id.rlDetailPeople)
    public TextView rlDetailPeople;
    @BindView(R.id.tvCityActivityDesc)
    public TextView tvCityActivityDesc;
    @BindView(R.id.lvCityImage)
    public MaxRecyclerView recyclerView;
    @BindView(R.id.tvCityStartActivity)
    public TextView tvCityStartActivity;
    @BindView(R.id.tvCityInChat)
    public TextView tvCityInChat;
    @BindView(R.id.tvCityEndActivity)
    public TextView tvCityEndActivity;
    @BindView(R.id.tvCityCollect)
    public TextView tvCityCollect;
    @BindView(R.id.tvCityQunZhuChat)
    public TextView tvCityQunZhuChat;
    @BindView(R.id.tvCityAdd)
    public TextView tvCityAdd;
    @BindView(R.id.tvCityOutActivity)
    public TextView tvCityOutActivity;
    @BindView(R.id.tvCityLoading)
    public TextView tvCityLoading;
    @BindView(R.id.tvCityGuan)
    public TextView tvCityGuan;
    @BindView(R.id.tvCityFinish)
    public TextView tvCityFinish;
    private ActivityDetailBean.DataBean detailBean;
    private LogineDialog logineDialog;
    private ActivityDetailBean activityDetailBean;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_citydetail;
    }

    @SuppressLint("WrongCall")
    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void getData() {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(this, "正在加载");
        }
        logineDialog.show();
        String activityid = getIntent().getStringExtra("title");
        getActivityData(activityid);
    }
    /*获取拼团详情*/
    private void getActivityData(String activityid) {
        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.CITY_DETAIL+activityid).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog != null && logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (logineDialog != null && logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                Log.e("getActivityData",JsonUtil.toJson(s));
               /* activityDetailBean = (ActivityDetailBean) JsonUtil.fromJson(s, ActivityDetailBean.class);
                if (activityDetailBean.getCode() == 0) {
                    detailBean = activityDetailBean.getData();
                    setDetailData(activityDetailBean.getData());
                } else {
                }*/
            }
        });
    }

    private void setDetailData(ActivityDetailBean.DataBean data) {
        rlCityDetailTime.setText(data.getDatetime() + "");
        rlDetailType.setText(data.getTypeName() + "");
        rlCityDetailLocation.setText(data.getLocation() + "");
        rlDetailPeople.setText(data.getMembers().size() + "/" + data.getUserCount() + "");
        rlDetailMoney.setText(data.getCost() + "");
        tvCityActivityDesc.setText(data.getContent());
        if (data.getMembers() != null && data.getMembers().size() > 0) {
            setPeople(data.getMembers());
        }
        if (data.getCoverImage() != null && data.getCoverImage().size() > 0) {
            setDetailImg(data.getCoverImage());
        }
        //团主本人
        if (data.isOriginator()) {
            tvCityAdd.setText("已加入");
            tvCityGuan.setVisibility(View.VISIBLE);
            tvCityCollect.setVisibility(View.GONE);//收藏
            tvCityQunZhuChat.setVisibility(View.GONE);//联系群主
           // tvCityOutActivity.setVisibility(View.GONE);//退出活动
            //未开始
            if (data.getStatus() == 0) {
                tvCityStartActivity.setVisibility(View.VISIBLE);//开始活动
                tvCityInChat.setVisibility(View.GONE);//进入群聊
                tvCityEndActivity.setVisibility(View.VISIBLE);//解散活动
                tvCityLoading.setVisibility(View.GONE);//进行中
                tvCityFinish.setVisibility(View.GONE);//已完成
            } else if (data.getStatus() == 1) {//进行中
                tvCityStartActivity.setVisibility(View.GONE);
                tvCityEndActivity.setVisibility(View.VISIBLE);
                tvCityInChat.setVisibility(View.VISIBLE);//进入群聊
                tvCityLoading.setVisibility(View.VISIBLE);
                tvCityFinish.setVisibility(View.GONE);//已完成
            } else if (data.getStatus() == 2){
                setActivityFinish();
            }
        } else {//成员
            if (data.getJoinStatus()==100){
                tvCityAdd.setText("申请加入");
                tvCityQunZhuChat.setVisibility(View.VISIBLE);//联系群主
                tvCityInChat.setVisibility(View.GONE);//进入群聊
            }else if (data.getJoinStatus()==0){
                tvCityAdd.setText("审核中");
                tvCityQunZhuChat.setVisibility(View.VISIBLE);//联系群主
                tvCityInChat.setVisibility(View.GONE);//进入群聊
            }else if (data.getJoinStatus()==1){
                tvCityAdd.setText("加入成功");
                tvCityQunZhuChat.setVisibility(View.GONE);//联系群主
                tvCityInChat.setVisibility(View.VISIBLE);//进入群聊
            }else if (data.getJoinStatus()==2){
                tvCityAdd.setText("拒绝加入");
                tvCityQunZhuChat.setVisibility(View.VISIBLE);//联系群主
                tvCityInChat.setVisibility(View.GONE);//进入群聊
            }
            tvCityGuan.setVisibility(View.GONE);
            tvCityStartActivity.setVisibility(View.GONE);//开始活动
            tvCityEndActivity.setVisibility(View.GONE);//解散活动
            //收藏
            if (data.isFavoriteStatus()) {
                tvCityCollect.setVisibility(View.VISIBLE);//收藏
                tvCityCollect.setText("取消收藏");
            } else {
                tvCityCollect.setVisibility(View.VISIBLE);
                tvCityCollect.setText("收藏活动");
            }
            //活动开始前
            if (data.getStatus() == 0) {
                tvCityLoading.setVisibility(View.GONE);//进行中
                tvCityFinish.setVisibility(View.GONE);//已完成
                //申请加入前
                if (data.getJoinStatus() == -1) {
                    tvCityQunZhuChat.setVisibility(View.VISIBLE);//联系群主
                   // tvCityOutActivity.setVisibility(View.GONE);//退出活动
                } else if (data.getJoinStatus() == 2) {  //加入后
                  //  tvCityOutActivity.setVisibility(View.VISIBLE);//退出活动
                    tvCityQunZhuChat.setVisibility(View.GONE);//联系群主
                }else {
                    tvCityQunZhuChat.setVisibility(View.VISIBLE);//联系群主
                }
            } else if (data.getStatus() == 1) { //活动进行中
                tvCityFinish.setVisibility(View.GONE);
                tvCityLoading.setVisibility(View.GONE);//进行中
            } else {
                tvCityQunZhuChat.setVisibility(View.GONE);//联系群主
                tvCityLoading.setVisibility(View.GONE);
                tvCityFinish.setVisibility(View.VISIBLE);//已完成
            }
        }
    }

    private void setDetailImg(List<String> coverImage) {
        CityDetailImgAdapter imgAdapter = new CityDetailImgAdapter(coverImage, this);
        recyclerView.setAdapter(imgAdapter);
    }

    private void setPeople(List<ActivityDetailBean.DataBean.MembersBean> members) {
        CityPeopleDetailAdapter hotPinAdapter = new CityPeopleDetailAdapter(members, this);
        scrollGridView.setAdapter(hotPinAdapter);
    }

    private void setActivityFinish() {
        tvCityStartActivity.setVisibility(View.GONE);
        tvCityEndActivity.setVisibility(View.GONE);
        tvCityLoading.setVisibility(View.GONE);
        tvCityFinish.setVisibility(View.VISIBLE);//已完成
        tvCityInChat.setVisibility(View.GONE);//进入群聊
    }

    @OnClick({R.id.tvCityStartActivity, R.id.tvCityEndActivity, R.id.tvCityInChat,
            R.id.tvCityQunZhuChat, R.id.tvCityOutActivity, R.id.tvCityCollect, R.id.tvCityAdd, R.id.tvCityGuan})
    public void onClick(View view) {
        switch (view.getId()) {
            //开始活动
            case R.id.tvCityStartActivity:
                if (detailBean != null) {
                    CommonDialog.getDialog(CityActivityDetailActivity.this, "开始活动", "是否开启活动?", "取消", "确认"
                            , new OnButtonClick() {
                                @Override
                                public void button01ClickListener() {

                                }

                                @Override
                                public void button02ClickListener() {
                                    String activityid = getIntent().getStringExtra("title");
                                    String url = ConstantsBean.ACTIVITY_START;
                                    openActivity(activityid, url);
                                }
                            });
                }
                break;
            //解散活动(结束活动)
            case R.id.tvCityEndActivity:
                if (detailBean != null) {
                    CommonDialog.getDialog(CityActivityDetailActivity.this, "结束活动", "是否结束活动?", "取消", "确认"
                            , new OnButtonClick() {
                                @Override
                                public void button01ClickListener() {

                                }

                                @Override
                                public void button02ClickListener() {
                                    String activityid = getIntent().getStringExtra("title");
                                    String url = ConstantsBean.ACTIVITY_END;
                                    openActivity(activityid, url);
                                }
                            });

                }
                break;
            //进入群聊
            case R.id.tvCityInChat:
                if (activityDetailBean != null) {
                    String title = activityDetailBean.getData().getLocation() +"～"+activityDetailBean.getData().getTypeName();
                  //  List<EMGroup> grouplist = EMClient.getInstance().groupManager().getAllGroups();
                    inChatRoom(title,"in");
                }
                break;
            //联系团主
            case R.id.tvCityQunZhuChat:
                if (detailBean != null && UserTask.getInstance().getUser().getImUsername() != detailBean.getCreatorImUsername()) {
                    UserCacheManager.save(detailBean.getCreatorImUsername()
                            , detailBean.getNick(), detailBean.getMembers().get(0).getHeadUrl());
                    startActivity(new Intent(this, ChatActivity.class)
                            .putExtra(EaseConstant.EXTRA_USER_ID, detailBean.getCreatorImUsername()).putExtra("nick", detailBean.getNick()));
                }
                break;
            //退出活动
            case R.id.tvCityOutActivity:
                if (activityDetailBean != null) {
                    String title = activityDetailBean.getData().getLocation() +"～"+activityDetailBean.getData().getTypeName();
                  //  inChatRoom(title,"out");
                }
                break;
            //收藏
            case R.id.tvCityCollect:
                if (detailBean != null) {
                    String activityid = getIntent().getStringExtra("title");
                    setCollect(activityid);
                }
                break;
            //申请加入
            case R.id.tvCityAdd:
                if (detailBean != null && detailBean.getJoinStatus()==100) {
                    String activityid = getIntent().getStringExtra("title");
                    setActivityAdd(activityid);
                } else {
                   // ToastUtil.show("人数已满");
                }
                break;
            case R.id.tvCityGuan:
                String activityid = getIntent().getStringExtra("title");
                ActivityUtils.skipActivity(this, CityPeoPleActvity.class, 0, activityid);
                break;
        }
    }

    private void inChatRoom(String title,String type) {
        new Thread(new Runnable() {
            public void run() {
                List<EMGroup> grouplist = null;
                try {
                    grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                if (grouplist != null && grouplist.size() > 0) {
                    for (int i = 0; i < grouplist.size(); i++) {
                        String id = grouplist.get(i).getGroupId();
                        EMGroup group = EMClient.getInstance().groupManager().getGroup(id);
                        if (group.getGroupName().contains(title)) {
                            if (type.equals("in")){
                                Intent intent = new Intent(CityActivityDetailActivity.this, ChatActivity.class);
                                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                                intent.putExtra(EaseConstant.EXTRA_USER_ID, id);
                                intent.putExtra("nick", group.getGroupName());
                                startActivity(intent);
                            }else {
                                exitGrop(id);
                            }

                            break;
                        }
                    }
                }
            }
        }).start();

    }

    /**
     * 退出群组
     *
     */
    private void exitGrop(String id) {
        Log.e("exitGropid",id+"");
        new Thread(new Runnable() {
            public void run() {
                try {
                    Log.e("exitGroprun",id+"");
                    EMClient.getInstance().groupManager().leaveGroup(id);
                    Log.e("exitGropOne",id+"");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            finish();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Exit_the_group_chat_failure) + " " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
    /**
     * 开始活动
     *
     * @param activityid
     */
    private void openActivity(String activityid, String url) {
        String userId = String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + url).addParams("userId", userId)
                .addParams("activityId", String.valueOf(activityid)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    ActivityCallBack callBack = (ActivityCallBack) JsonUtil.fromJson(s, ActivityCallBack.class);
                    if (callBack.getCode()==0){
                        ToastUtil.show(callBack.getMsg());
                        getActivityData(activityid);
                    }else {
                        ToastUtil.show(callBack.getMsg());
                    }

                }
            }
        });
    }

    /*申请加入*/
    private void setActivityAdd(String activityId) {
        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.APPLAYADD).addParams("userId", userid)
                .addParams("activityId", activityId + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    ActivityAddBean collectBean = (ActivityAddBean) JsonUtil.fromJson(s, ActivityAddBean.class);
                    if (collectBean.getCode() == 0) {
                        if (collectBean.getData().isApplyStatus()) {
                            ToastUtil.show(collectBean.getData().getText());
                            getActivityData(activityId);
                        } else {
                            ToastUtil.show(collectBean.getData().getText());
                        }
                    } else {
                        ToastUtil.show("申请失败");
                    }
                } else {
                    ToastUtil.show("申请失败");
                }
            }
        });
    }
    /*收藏*/
    private void setCollect(String activityId) {
        String userid= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.COLLECT_ACTIVITY).addParams("userId",userid)
                .addParams("activityId",activityId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    ActivityCollectBean collectBean= (ActivityCollectBean) JsonUtil.fromJson(s,ActivityCollectBean.class);
                    if (collectBean.getCode()==0){
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.COLLECT_ACTIVITY));
                        EventBus.getDefault().post(new SendMessageData(Constant.PUBLISH_ACTIVITY));
                        if (collectBean.getData().isFavoriteStatus()) {
                            tvCityCollect.setVisibility(View.VISIBLE);//收藏
                            tvCityCollect.setText("取消收藏");
                        } else {
                            tvCityCollect.setVisibility(View.VISIBLE);
                            tvCityCollect.setText("收藏活动");
                        }
                    }else {
                        ToastUtil.show("收藏失败");
                    }
                }else {
                    ToastUtil.show("收藏失败");
                }
            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.REMOVE_PEOPLE)){
           String id= getIntent().getStringExtra("title");
           getActivityData(id);

        }
    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("活动详情");
    }
}
