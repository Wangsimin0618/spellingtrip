package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.NocticeDetailActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.NoticeMeassageBean;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.view.ShapedImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

public class NoticeMeassageAdapter extends RecyclerView.Adapter<NoticeMeassageAdapter.ViewHolder> {
    private List<NoticeMeassageBean.DataBean>meassages;
    private Activity activity;
    public NoticeMeassageAdapter(List<NoticeMeassageBean.DataBean> meassages, Activity activity) {
    this.activity=activity;
    this.meassages=meassages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notice_meassage,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int postion) {
        NoticeMeassageBean.DataBean dataBean=meassages.get(postion);
        Glide.with(activity).load(dataBean.getSenderAvatar()).into(viewHolder.ivItemMeassageImge);
        viewHolder.tvItemMeassageContent.setText(dataBean.getContent());
        viewHolder.tvItemMeassageTitle.setText(dataBean.getSenderNick());
        viewHolder.tvItemMeassagetime.setText(dataBean.getTime());
       /* viewHolder.rlItemMeassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity,NocticeDetailActivity.class,0,dataBean.getMessageId()+"");
            }
        });*/
    }



    @Override
    public int getItemCount() {
        return meassages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShapedImageView ivItemMeassageImge;
        private RelativeLayout rlItemMeassage;
        private TextView tvItemMeassageTitle,tvItemMeassageContent,tvItemMeassagetime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemMeassageImge=itemView.findViewById(R.id.ivItemMeassageImge);
            tvItemMeassageTitle=itemView.findViewById(R.id.tvItemMeassageTitle);
            tvItemMeassageContent=itemView.findViewById(R.id.tvItemMeassageContent);
            tvItemMeassagetime=itemView.findViewById(R.id.tvItemMeassagetime);
            rlItemMeassage=itemView.findViewById(R.id.rlItemMeassage);
        }
    }
}
