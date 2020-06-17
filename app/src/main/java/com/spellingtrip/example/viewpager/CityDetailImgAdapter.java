package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.CityActivityDetailActivity;
import com.spellingtrip.example.dialog.CityDetailInfoImgFragment;
import com.spellingtrip.example.dialog.GoodsInfoImgFragment;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.List;

public class CityDetailImgAdapter extends RecyclerView.Adapter<CityDetailImgAdapter.ViewHolder>{
    private List<String> coverImage;
    private Activity activity;
    public CityDetailImgAdapter(List<String> coverImage, Activity activity) {
        this.coverImage=coverImage;
        this.activity=activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detailimg,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) viewHolder.ivItemDetailImg.getLayoutParams();
        int mWidth=CommonUtil.dipToPx(activity,210);
        layoutParams.width=mWidth;
        viewHolder.ivItemDetailImg.setLayoutParams(layoutParams);
        Glide.with(activity).load(coverImage.get(i)).into(viewHolder.ivItemDetailImg);
        viewHolder.ivItemDetailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityDetailInfoImgFragment fragment = new CityDetailInfoImgFragment();
                fragment.setData(coverImage, i);
                fragment.show(activity.getFragmentManager(), "DialogFragment");
            }
        });
    }
    @Override
    public int getItemCount() {
        return coverImage.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView ivItemDetailImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemDetailImg=itemView.findViewById(R.id.ivItemDetailImg);
        }
    }
}
