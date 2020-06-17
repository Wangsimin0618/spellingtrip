package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.CityActivityDetailActivity;
import com.spellingtrip.example.activity.LogineActivity;
import com.spellingtrip.example.activity.MineCollectActivity;
import com.spellingtrip.example.bean.ActivityAddBean;
import com.spellingtrip.example.bean.ActivityCollectBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.MineCollectActivityBean;
import com.spellingtrip.example.bean.PiPeiCityBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ShapedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.Call;

public class MineCollectActivityAdapter extends RecyclerView.Adapter<MineCollectActivityAdapter.ViewHolder>{
    private Activity activity;
    private List<MineCollectActivityBean.DataBean.RowsBean> citys;

    public MineCollectActivityAdapter(List<MineCollectActivityBean.DataBean.RowsBean> citys, Activity activity) {
        this.activity=activity;
        this.citys=citys;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_aacity,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int postion) {
        MineCollectActivityBean.DataBean.RowsBean dataBean =citys.get(postion);
        String  imgurl=dataBean.getCoverImage()/*+"?x-oss-process=style/720_360"*/;
        Glide.with(activity).load(imgurl).into(viewHolder.ivActivityImg);
        viewHolder.tvActivityType.setText("活动："+dataBean.getTypeName());
        viewHolder.tvActivityWeiZhi.setText(dataBean.getLocation()+"");
        viewHolder.tvActivityTime.setText("时间："+dataBean.getStartDate());
        viewHolder.tvActivityContent.setText(dataBean.getContent());
        viewHolder.ivActivityImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserTask.getInstance().isLogin()){
                    ActivityUtils.skipActivity(activity,CityActivityDetailActivity.class,0,dataBean.getActivityId()+"");
                }else {
                    ActivityUtils.skipActivity(activity,LogineActivity.class,0,"");
                }

            }
        });
    }
    /*申请加入*/
    private void setActivityAdd(int activityId, TextView ivItemAAcityAdd) {
        String userid= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.APPLAYADD).addParams("userId",userid)
                .addParams("activityId",activityId+"").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {

                if (!TextUtils.isEmpty(s)){
                    ActivityAddBean collectBean= (ActivityAddBean) JsonUtil.fromJson(s,ActivityAddBean.class);
                    if (collectBean.getCode()==0){
                        if (collectBean.getData().isApplyStatus()){
                            ivItemAAcityAdd.setText("申请中");
                        }else {
                            ivItemAAcityAdd.setText("申请加入");
                        }
                    }else {
                        ToastUtil.show("申请失败");
                    }
                }else {
                    ToastUtil.show("申请失败");
                }
            }
        });
    }

    /*收藏*/
    private void setCollect(boolean favoriteStatus, TextView ivItemAAcityCollect, int activityId) {
        String userid= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.COLLECT_ACTIVITY).addParams("userId",userid)
                .addParams("activityId",activityId+"").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    ActivityCollectBean collectBean= (ActivityCollectBean) JsonUtil.fromJson(s,ActivityCollectBean.class);
                    if (collectBean.getCode()==0){
                        if (collectBean.getData().isFavoriteStatus()){
                            ivItemAAcityCollect.setText("已收藏");
                            EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.COLLECT_ACTIVITY));
                        }else {
                            ivItemAAcityCollect.setText("收藏");
                            EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.COLLECT_ACTIVITY));
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

    @Override
    public int getItemCount() {
        return citys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivActivityImg;
        private TextView tvActivityType,tvActivityWeiZhi,tvActivityTime,tvActivityContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivActivityImg=itemView.findViewById(R.id.ivActivityImg);
            tvActivityType=itemView.findViewById(R.id.tvActivityType);
            tvActivityWeiZhi=itemView.findViewById(R.id.tvActivityWeiZhi);
            tvActivityTime=itemView.findViewById(R.id.tvActivityTime);
            tvActivityContent=itemView.findViewById(R.id.tvActivityContent);
        }
    }

}
