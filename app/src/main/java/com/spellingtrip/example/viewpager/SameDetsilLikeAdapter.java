package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.NewActivityDetailBean;
import com.spellingtrip.example.bean.SameDetailBean;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.List;

/**
 * date:2020/5/6
 * author:王思敏
 * function同城活动详情加入人数
 */
public class SameDetsilLikeAdapter extends BaseAdapter {
    private Activity activity;
    private List<SameDetailBean.DataBean.MembersBean> tourLikeList;

    public SameDetsilLikeAdapter(Activity activity, List<SameDetailBean.DataBean.MembersBean> tourLikeList) {
        this.activity = activity;
        this.tourLikeList = tourLikeList;
    }


    @Override
    public int getCount() {
        return tourLikeList.size();
    }

    @Override
    public Object getItem(int i) {
        return tourLikeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(activity).inflate(R.layout.samedetail_like, null);
            viewHolder.mLike_head=view.findViewById(R.id.like_head);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        String url=tourLikeList.get(i).getHeadUrl()+"?x-oss-process=style/64_64";
        Glide.with(activity).load(url).into(viewHolder.mLike_head);


        return view;
    }


    class ViewHolder {
        ShapedImageView mLike_head;
    }
//
//    private Activity activity;
//    private List<SameDetailBean.DataBean.MembersBean> mMembersBeans;
//
//    public SameDetsilLikeAdapter(Activity activity, List<SameDetailBean.DataBean.MembersBean> mMembersBeans) {
//        this.activity = activity;
//        this.mMembersBeans = mMembersBeans;
//    }
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.samedetail_like, viewGroup,false);
//        SameDetsilLikeAdapter.ViewHolder viewHolder = new SameDetsilLikeAdapter.ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        String url=mMembersBeans.get(i).getHeadUrl()+"?x-oss-process=style/64_64";
//        Glide.with(activity).load(url).into(viewHolder.mLike_head);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mMembersBeans.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private final ShapedImageView mLike_head;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mLike_head = itemView.findViewById(R.id.like_head);
//        }
//    }
}
