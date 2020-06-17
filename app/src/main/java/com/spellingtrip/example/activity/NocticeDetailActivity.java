package com.spellingtrip.example.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ActivityCallBack;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.NocticeDetailBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ShapedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class NocticeDetailActivity extends BaseActivity{
    @BindView(R.id.ivItemNoticeDetailHeader)
    public ShapedImageView shapedImageView;
    @BindView(R.id.tvItemNoticeDetailNick)
    public TextView tvItemNoticeDetailNick;
    @BindView(R.id.tvItemNoticeDetailTime)
    public TextView tvItemNoticeDetailTime;
    @BindView(R.id.tvItemNoticeDetailLocation)
    public TextView tvItemNoticeDetailLocation;
    @BindView(R.id.tvItemNoticeDetailTongYi)
    public TextView tvItemNoticeDetailTongYi;
    @BindView(R.id.tvItemNoticeDetailJuJue)
    public TextView tvItemNoticeDetailJuJue;
    private NocticeDetailBean nocticeDetailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.actctivity_noticedetail;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {
        String id=getIntent().getStringExtra("title");
        getDetail(id);
    }

    private void getDetail(String id) {
        String userid= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.NOCTICE_DETAIL).addParams("userId",userid)
                .addParams("id",id).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
              if (!TextUtils.isEmpty(s)){
                   nocticeDetailBean= (NocticeDetailBean) JsonUtil.fromJson(s,NocticeDetailBean.class);
                  if (nocticeDetailBean.getCode()==0){
                      String url=nocticeDetailBean.getData().getSenderAvatar()+"?x-oss-process=style/320_320";
                      Glide.with(NocticeDetailActivity.this).load(url)
                              .into(shapedImageView);
                      tvItemNoticeDetailNick.setText(nocticeDetailBean.getData().getTitle());
                      tvItemNoticeDetailTime.setText(nocticeDetailBean.getData().getCreateTime());
                  }else {

                  }
              }
            }
        });
    }
    @OnClick({R.id.tvItemNoticeDetailJuJue,R.id.tvItemNoticeDetailTongYi})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tvItemNoticeDetailJuJue:
                if (nocticeDetailBean!=null){
                   String applyId= String.valueOf(nocticeDetailBean.getData().getId());
                    String url=ConstantsBean.JUJUE_ADDAPPLY;
                    setJuJue(applyId,url);
                }

                break;
            case R.id.tvItemNoticeDetailTongYi:
                if (nocticeDetailBean!=null){
                    String applyId= String.valueOf(nocticeDetailBean.getData().getId());
                    String url=ConstantsBean.TONGYI_ADDAPPLY;
                    setJuJue(applyId,url);
                }
                break;
        }
    }
    private void setJuJue(String applyId,String url) {
        String userId= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+url).addParams("userId",userId).addParams("applyId",applyId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    ActivityCallBack activityCallBack= (ActivityCallBack) JsonUtil.fromJson(s, ActivityCallBack.class);
                    if (activityCallBack.getCode()==0){
                        ToastUtil.show(activityCallBack.getMsg());
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.DETELE_ADDAPPLY));
                        finish();
                    }else {
                        ToastUtil.show(activityCallBack.getMsg());
                    }
                }
            }
        });
    }
    @Override
    protected void setData() {
        backClick();
        setCenterTitle("详情");
    }
}
