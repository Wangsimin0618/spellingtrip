package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.CityActivityDetailActivity;
import com.spellingtrip.example.activity.LogineActivity;
import com.spellingtrip.example.activity.TourismUserShowActivity;
import com.spellingtrip.example.bean.DataBean;
import com.spellingtrip.example.bean.MyCityActivityBean;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;

import java.util.List;

public class UserActivityAdapter extends RecyclerView.Adapter<UserActivityAdapter.ViewHolder>{
    private List<MyCityActivityBean.DataBean> citys;
    private Activity activity;
    public UserActivityAdapter(List<MyCityActivityBean.DataBean> citys, Activity activity) {
        this.citys=citys;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_useractivity,null);
       ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        MyCityActivityBean.DataBean dataBean=citys.get(i);
        Glide.with(activity).load(dataBean.getCoverImage()).into(viewHolder.ivItemUserActivityImg);
        viewHolder.tvItemUserActivityType.setText("活动："+dataBean.getTypeName());
        viewHolder.tvItemUserActivityWeiZhi.setText("地点："+dataBean.getLocation());
        viewHolder.ivItemUserActivityImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserTask.getInstance().isLogin()){
                    ActivityUtils.skipActivity(activity,CityActivityDetailActivity.class,dataBean.getActivityId(),"");
                }else {
                    ActivityUtils.skipActivity(activity,LogineActivity.class,0,"");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return citys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItemUserActivityImg;
        private TextView tvItemUserActivityType,tvItemUserActivityWeiZhi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemUserActivityImg= itemView.findViewById(R.id.ivItemUserActivityImg);
             tvItemUserActivityType= itemView.findViewById(R.id.tvItemUserActivityType);
             tvItemUserActivityWeiZhi= itemView.findViewById(R.id.tvItemUserActivityWeiZhi);
        }
    }
}
