package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.JieBanActivityDetailActivity;
import com.spellingtrip.example.activity.LogineActivity;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2020/4/18
 * author:王思敏
 * function首页条目图片
 */
public class HomeImageAdapter extends RecyclerView.Adapter<HomeImageAdapter.ViewHolder> {
    public static final String TAG = "HomeImageAdapter";
    private List<String> list;
    private Activity activity;
    private boolean isVisible = true;
    public HomeImageAdapter(Activity activity,  List<String> list){
        this.list=list;
        this.activity=activity;
    }

    public interface ImageOnclick{
        void onItemClick();
    }
    private ImageOnclick mImageOnclick;
    public void setImageOnclick(ImageOnclick imageOnclick){
        mImageOnclick = imageOnclick;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup,false);
        HomeImageAdapter.ViewHolder viewHolder = new HomeImageAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) viewHolder.itemImage.getLayoutParams(); //取控件textView当前的布局参数
        if (list!=null){
            if (list.size()==1){
                linearParams.height=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180,CustomApplication.getInstance().getResources().getDisplayMetrics()));
                viewHolder.itemImage.setVisibility(View.VISIBLE);
                Glide.with(activity).load(list.get(i)).placeholder(R.mipmap.repalec)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(false).into(viewHolder.itemImage);
                initclick(viewHolder.imageclick);
                return;
            } if (list.size()==2){
                linearParams.height=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 154,CustomApplication.getInstance().getResources().getDisplayMetrics()));
                viewHolder.itemImage.setVisibility(View.VISIBLE);
                Glide.with(activity).load(list.get(i)).placeholder(R.mipmap.repalec)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(false).into(viewHolder.itemImage);
                initclick(viewHolder.imageclick);
                return;
            }
            if (i>5){
                linearParams.height=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,CustomApplication.getInstance().getResources().getDisplayMetrics()));
                viewHolder.itemImage.setVisibility(View.GONE);
                initclick(viewHolder.imageclick);
                Glide.with(activity).load(list.get(i)).placeholder(R.mipmap.repalec)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(false).into(viewHolder.itemImage);
                return;
            }
            else {
                linearParams.height=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,CustomApplication.getInstance().getResources().getDisplayMetrics()));
                Log.v(TAG,"---------list.size=="+list.size()+"---------i=="+i);
                viewHolder.itemImage.setVisibility(View.VISIBLE);
                Glide.with(activity).load(list.get(i)).placeholder(R.mipmap.repalec)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(false).into(viewHolder.itemImage);
            }
            viewHolder.itemImage.setLayoutParams(linearParams);

        }
        initclick(viewHolder.imageclick);

    }

    private void initclick(LinearLayout imageclick) {
        imageclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageOnclick !=null){
                    mImageOnclick.onItemClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ShapedImageView itemImage;
        private final LinearLayout imageclick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImages);
            imageclick = itemView.findViewById(R.id.imageclick);
        }
    }
}
