package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.LiveDetailActivity;
import com.spellingtrip.example.activity.MinSuListActivity;
import com.spellingtrip.example.bean.DataBean;
import com.spellingtrip.example.bean.LiveListBean;
import com.spellingtrip.example.bean.MineCollectActivityBean;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.List;

public class LiveListAdapter extends RecyclerView.Adapter<LiveListAdapter.ViewHolder>{
    private List<LiveListBean.DataBean> lives;
    private Activity activity;
    public LiveListAdapter(List<LiveListBean.DataBean> lives, Activity activity) {
        this.activity=activity;
        this.lives=lives;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lives,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
       LiveListBean.DataBean dataBean =lives.get(i);
        String  imgurl=dataBean.getCoverImage()/*+"?x-oss-process=style/680_360"*/;
        Glide.with(activity).load(imgurl).into(viewHolder.ivItemLives);
        viewHolder.tvItemLivestitle.setText(dataBean.getTitle());
        viewHolder.tvItemLivesType.setText(dataBean.getRoomType().toString()+"");
        viewHolder.tvItemTypeDetail.setText(dataBean.getIntro());
        viewHolder.tvItemLivesPrice.setText("¥"+dataBean.getPrice());
        viewHolder.tvItemLivesOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
        viewHolder.tvItemLivesOldPrice.setText("¥"+dataBean.getOriginalPrice()+"每晚");
        viewHolder.llItemLives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity,LiveDetailActivity.class,0, String.valueOf(dataBean.getHotelId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return lives.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShapedImageView ivItemLives;//tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
        private TextView tvItemLivestitle,tvItemLivesType,tvItemTypeDetail,tvItemLivesPrice,tvItemLivesOldPrice;
        private LinearLayout llItemLives;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llItemLives=itemView.findViewById(R.id.llItemLives);
            ivItemLives=itemView.findViewById(R.id.ivItemLives);
            tvItemLivestitle=itemView.findViewById(R.id.tvItemLivestitle);
            tvItemLivesType=itemView.findViewById(R.id.tvItemLivesType);
            tvItemTypeDetail=itemView.findViewById(R.id.tvItemTypeDetail);
            tvItemLivesPrice=itemView.findViewById(R.id.tvItemLivesPrice);
            tvItemLivesOldPrice=itemView.findViewById(R.id.tvItemLivesOldPrice);

        }
    }
}

