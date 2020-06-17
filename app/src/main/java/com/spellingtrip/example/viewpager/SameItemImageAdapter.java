package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.SameBean;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.List;

/**
 * date:2020/5/12
 * author:王思敏
 * function同城活动首页图片叠加
 */
public class SameItemImageAdapter extends RecyclerView.Adapter<SameItemImageAdapter.ViewHolder> {
    private Activity mActivity;
    private List<String> list;
    public SameItemImageAdapter(Activity activity, List<String> list) {
        this.mActivity = activity;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.same_image, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mActivity).load(list.get(i)).placeholder(R.mipmap.repalec).diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(false).into(viewHolder.mLikehead);
        if(i==0){
            setMargins(viewHolder.mRelayout,0,0,0,0);
        }

    }
    public void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ShapedImageView mLikehead;
        private final RelativeLayout mRelayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRelayout = itemView.findViewById(R.id.relayout);
            mLikehead = itemView.findViewById(R.id.sameitem_head);
        }
    }
}
