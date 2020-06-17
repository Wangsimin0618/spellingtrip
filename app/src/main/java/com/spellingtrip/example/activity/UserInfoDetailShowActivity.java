package com.spellingtrip.example.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.UserShowBean;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.huanxin.ChatActivity;
import com.spellingtrip.example.huanxin.Constanthuan;
import com.spellingtrip.example.liaotian.UserCacheManager;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.MyLayoutManager;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.AACityAdapter;
import com.spellingtrip.example.viewpager.CardLablesAdapter;
import com.spellingtrip.example.viewpager.SearchHistoryAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.spellingtrip.example.utils.HttpRequest.getUserInfo;

public class UserInfoDetailShowActivity extends BaseActivity implements OnMultiPurposeListener {
    @BindView(R.id.parallax)
    public ImageView userheader;
    @BindView(R.id.userCardSmartRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.cardXRecyclerView)
    public XRecyclerView xRecyclerView;
    @BindView(R.id.ivCardChat)
    public ImageView ivCardChat;
    private int mOffset = 0;
    private int mScrollY = 0;
    private LogineDialog logineDialog;
    private TextView qName,cAge,cNick,tvLablesNum;
    private ImageView ivVip,cSex;
    private RecyclerView rvCardLables;
    private int page=1;
    private ActivityAABean pinYouBean;
    private List<ActivityAABean.DataBean> rows;
    private AACityAdapter aaCityAdapter;
    private UserShowBean userShowBean;

    @Override
    protected int getLayoutId() {
        return R.layout.actctivity_userinfocard;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        refreshLayout.setOnMultiPurposeListener(this);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setDisableContentWhenLoading(true);
        refreshLayout.setEnableOverScrollDrag(true);
        refreshLayout.setReboundDuration(450);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setEnableClipHeaderWhenFixedBehind(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        View view= LayoutInflater.from(this).inflate(R.layout.card_header,null);
        qName=view.findViewById(R.id.tvCardUserQianMing);
        ivVip=view.findViewById(R.id.ivCardUserVip);
        cAge=view.findViewById(R.id.tvCardUserAge);
        cSex=view.findViewById(R.id.tvCardUserSex);
        cNick=view.findViewById(R.id.cardUserNick);
        tvLablesNum=view.findViewById(R.id.tvCardLableNumber);
        rvCardLables=view.findViewById(R.id.rvCardLables);
        xRecyclerView.addHeaderView(view);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setFocusableInTouchMode(false);
        xRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void getData() {
        if (logineDialog==null){
            logineDialog=new LogineDialog(this,"");
        }
        logineDialog.show();
        getUserInfo();
        getMyPin();
    }

    @Override
    protected void setData() {

    }

    private void getUserInfo() {

        String fid = getIntent().getStringExtra("title");
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.USERINFO + fid).build().readTimeOut(50000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtil.show(ConstantsBean.ERROR);
                    }
                    @Override
                    public void onResponse(String s, int i) {

                        if (!TextUtils.isEmpty(s)) {
                              userShowBean= (UserShowBean) JsonUtil.fromJson(s,UserShowBean.class);
                            if (userShowBean.getCode()==0&&userShowBean.getData()!=null){
                                setUserInfo(userShowBean.getData());
                            }else {
                            }
                        } else {
                        }
                    }
                });
    }

    private void setUserInfo(UserShowBean.DataBean data) {
        if (data.getBgImages()!=null&&data.getBgImages().size()>0){
            String headUrl=data.getBgImages().get(0)+"?x-oss-process=style/320_320";
            Glide.with(this).load(headUrl).into(userheader);
        }else {
            String headUrl=data.getHeadUrl()+"?x-oss-process=style/320_320";
            Glide.with(this).load(headUrl).into(userheader);
        }
        cNick.setText(data.getNick());
        cAge.setText(data.getBirthday());
        if (!TextUtils.isEmpty(data.getDescription())){
            qName.setText("个性签名："+data.getDescription());
        }else {
            qName.setText("个性签名：未设置签名");
        }
        if (data.getSex().equals("男")){
//            cSex.setText("♂");
        }else {
//            cSex.setText("♀");
        }
        if (data.isVip()){
            ivVip.setVisibility(View.VISIBLE);
        }else {
            ivVip.setVisibility(View.GONE);
        }
        if (data.getLabels()==null||data.getLabels().size()==0){
            tvLablesNum.setText("爱好标签·0");
        }else {
           int num= data.getLabels().size();
            tvLablesNum.setText("爱好标签·"+num);
        }
        if (data.getLabels()!=null&&data.getLabels().size()>0){
            final List<UserShowBean.DataBean.LabelsBean> labeLists = data.getLabels();
            MyLayoutManager layout = new MyLayoutManager();
            layout.setAutoMeasureEnabled(true);
            if (rvCardLables.getItemDecorationCount() == 0) {
                rvCardLables.addItemDecoration(new SpacesItemDecoration(14));
            }
            rvCardLables.setLayoutManager(layout);
            rvCardLables.setHasFixedSize(true);
            rvCardLables.setNestedScrollingEnabled(false);
            final CardLablesAdapter adapter = new CardLablesAdapter(activity, labeLists);
            rvCardLables.setAdapter(adapter);
        }
    }

    private void getMyPin() {
        String userid = getIntent().getStringExtra("title");
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.MYPIN).addParams("page", String.valueOf(page))
                .addParams("userId",userid ).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
               if (logineDialog!=null&&logineDialog.isShowing()){
                   logineDialog.dismiss();
               }
            }

            @Override
            public void onResponse(String s, int i) {
                if (logineDialog!=null&&logineDialog.isShowing()){
                    logineDialog.dismiss();
                }
                if (!TextUtils.isEmpty(s)) {
                    pinYouBean = (ActivityAABean) JsonUtil.fromJson(s, ActivityAABean.class);
                    if (pinYouBean.getCode() == 0) {
                        if (pinYouBean.getData().size() > 0) {
                            if (rows == null) {
                                rows = new ArrayList<>();
                            }
                            rows.addAll(pinYouBean.getData());
                            setPinYouList(rows);
                        } else {
                        }
                    } else {
                        ToastUtil.show(pinYouBean.getMsg());
                    }
                }
            }
        });
    }
    private void setPinYouList(List<ActivityAABean.DataBean> citys) {
        if (aaCityAdapter==null){
            aaCityAdapter=new AACityAdapter(this,citys,"home");
            xRecyclerView.setAdapter(aaCityAdapter);
        }else {
            aaCityAdapter.notifyDataSetChanged();
        }

    }
    @OnClick({R.id.ivCardChat})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ivCardChat:
                if (userShowBean!=null){
                    PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.USER_NICK,userShowBean.getData().getNick());
                    String userId= userShowBean.getData().getImUsername();
                    if (!userId.equals(UserTask.getInstance().getUser().getImUsername())){
                        UserCacheManager.save(userId
                                ,userShowBean.getData().getNick(),userShowBean.getData().getHeadUrl());
                        startActivity(new Intent(this, ChatActivity.class)
                                .putExtra(Constanthuan.EXTRA_USER_ID, userId).putExtra("nick",userShowBean.getData().getNick()));
                    }else {

                    }
                }
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.USERINFO)) {
            getUserInfo();
            getMyPin();
        }
    }
        @Override
    public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
        mOffset = offset / 2;
        Log.e("onHeaderMoving",mOffset+"/"+mScrollY);
       // userheader.setTranslationY(mOffset - mScrollY);

    }


    @Override
    public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {
        mOffset = headerHeight / 2;
        Log.e("onHeaderReleased",mOffset+"/"+mScrollY);
       // userheader.setTranslationY(mOffset - mScrollY);

    }

    @Override
    public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

    }

    @Override
    public void onHeaderFinish(RefreshHeader header, boolean success) {

    }

    @Override
    public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

    }

    @Override
    public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

    }

    @Override
    public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

    }

    @Override
    public void onFooterFinish(RefreshFooter footer, boolean success) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getData();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
