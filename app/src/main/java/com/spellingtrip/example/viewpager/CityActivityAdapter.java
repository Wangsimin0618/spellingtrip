package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.CityActivityDetailActivity;
import com.spellingtrip.example.bean.ActivityCallBack;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.MyCityActivityBean;
import com.spellingtrip.example.bean.MyPinYouBean;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ScrollGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class CityActivityAdapter extends RecyclerView.Adapter<CityActivityAdapter.ViewHolder> {
    private Activity activity;
    private List<MyCityActivityBean.DataBean> citys;
    public CityActivityAdapter(List<MyCityActivityBean.DataBean> citys, Activity activity) {
        this.activity = activity;
        this.citys=citys;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cityactivity, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
       MyCityActivityBean.DataBean dataBean= citys.get(position);
       viewHolder.tvItemActivityNick.setText(dataBean.getNick());
       String header=UserTask.getInstance().getUserInfoData().getHeadUrl();
        Glide.with(activity).load(header).transform(new CommonUtil.GlideCircleTransform(activity)).into(viewHolder.ivItemActivityImg);
        viewHolder.tvItemActivityTime.setText("时间："+dataBean.getDatetime());
        viewHolder.tvItemActivityLocation.setText(dataBean.getLocation());
        viewHolder.tvItemActivityType.setText("活动："+dataBean.getTypeName());
        viewHolder.tvItemActivityPeople.setText("团队成员："+dataBean.getJoinedCount());
        if (dataBean.getStatus()==0){
            viewHolder. tvItemActivityStartApplay.setText("开始活动");
        }else if (dataBean.getStatus()==1){
            viewHolder. tvItemActivityStartApplay.setText("进行中");
        }else if (dataBean.getStatus()==2){
            viewHolder. tvItemActivityStartApplay.setText("已结束");
        }
        if (dataBean.getPayType()==1){
            viewHolder.tvItemActivityPayType.setText("线下AA");
        }else {
            viewHolder.tvItemActivityPayType.setText("我请客");
        }
        viewHolder.tvItemActivityStartTime.setText("距开始："+dataBean.getEndDate());
        viewHolder.rlItemAACity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity,CityActivityDetailActivity.class,0,dataBean.getActivityId()+"");
            }
        });
        viewHolder.tvItemActivityStartApplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.getStatus()==0){
                    String url= ConstantsBean.ACTIVITY_START;
                    setActivity(url,dataBean.getActivityId());
                }

            }
        });
    }

    private void setActivity(String url,int id) {
        String userId= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+url).addParams("userId",userId).addParams("activityId", String.valueOf(id))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    ActivityCallBack callBack= (ActivityCallBack) JsonUtil.fromJson(s,ActivityCallBack.class);
                    if (callBack.getCode()==0){
                        EventBus.getDefault().post(new EventType(ConstantsBean.PUBLISH_ACTIVITY));
                        ToastUtil.show(callBack.getMsg());
                    }else {
                        ToastUtil.show(callBack.getMsg());
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return citys.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemActivityNick, tvItemActivityTime, tvItemActivityLocation, tvItemActivityType, tvItemActivityPeople
                ,tvItemActivityPayType,tvItemActivityStartTime,tvItemActivityStartApplay,tvItemActivityJIeSan;
        ImageView ivItemActivityImg, ivUserHeaderMyPin;
        RelativeLayout rlItemAACity;
        public ViewHolder(View itemView) {
            super(itemView);
            tvItemActivityNick = itemView.findViewById(R.id.tvItemActivityNick);
            tvItemActivityTime = itemView.findViewById(R.id.tvItemActivityTime);
            tvItemActivityLocation = itemView.findViewById(R.id.tvItemActivityLocation);
            tvItemActivityType = itemView.findViewById(R.id.tvItemActivityType);
            tvItemActivityPeople = itemView.findViewById(R.id.tvItemActivityPeople);
            tvItemActivityPayType = itemView.findViewById(R.id.tvItemActivityPayType);
            tvItemActivityStartTime=itemView.findViewById(R.id.tvItemActivityStartTime);
            tvItemActivityStartApplay=itemView.findViewById(R.id.tvItemActivityStartApplay);
            tvItemActivityJIeSan=itemView.findViewById(R.id.tvItemActivityJIeSan);
            ivItemActivityImg = itemView.findViewById(R.id.ivItemActivityImg);
            rlItemAACity=itemView.findViewById(R.id.rlItemAACity);
        }
    }


}

