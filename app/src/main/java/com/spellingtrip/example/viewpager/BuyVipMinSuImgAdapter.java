package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.BuyVIPActivity;
import com.spellingtrip.example.dialog.CityDetailInfoImgFragment;
import com.spellingtrip.example.utils.CommonUtil;

import java.util.List;

public class BuyVipMinSuImgAdapter extends RecyclerView.Adapter<BuyVipMinSuImgAdapter.ViewHolder>{
    private int[]  coverImage;
    private Activity activity;
    public BuyVipMinSuImgAdapter(int[] coverImage, Activity activity) {
        this.coverImage=coverImage;
        this.activity=activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detailimg,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(activity).load(coverImage[i]).into(viewHolder.ivItemDetailImg);

    }
    @Override
    public int getItemCount() {
        return coverImage.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemDetailImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemDetailImg=itemView.findViewById(R.id.ivItemDetailImg);
        }
    }
}

