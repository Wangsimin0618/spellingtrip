package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.content.Intent;
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

import com.spellingtrip.example.activity.LogineActivity;
import com.spellingtrip.example.activity.PinYouInfoActivity;
import com.spellingtrip.example.activity.TourismUserShowActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.PiPeiRecordBean;
import com.spellingtrip.example.huanxin.ChatActivity;
import com.spellingtrip.example.huanxin.Constanthuan;
import com.spellingtrip.example.liaotian.UserCacheManager;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.PreferenceUtil;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{
    private List<PiPeiRecordBean.DataBean> lists;
    private Activity activity;
    private String type;
    public RecordAdapter(List<PiPeiRecordBean.DataBean> lists, Activity activity,String type) {
        this.lists=lists;
        this.type=type;
        this.activity=activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_record, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final PiPeiRecordBean.DataBean dataBean=lists.get(position);
        if (!TextUtils.isEmpty(dataBean.getHeadUrl())){
            String  imgurl=dataBean.getHeadUrl()+"?x-oss-process=style/320_320";
            Glide.with(activity).load(imgurl).transform(new CommonUtil.GlideCircleTransform(activity))
                    .into(viewHolder.ivItemRecordHeader);
        }
        viewHolder.tvItemRecordSex.setText(dataBean.getSex());
        viewHolder.etItemRecordNick.setText(dataBean.getNick());
        viewHolder.tvItemRene.setText("人数："+dataBean.getTotalCount());
        if (type.equals(ConstantsBean.DETAIL)){
            viewHolder.tvSimilarity.setText(dataBean.getTripScore()+"");
        }else {
            viewHolder.tvSimilarity.setText("相似度  "+dataBean.getLabelScore());
        }

        viewHolder.tvItemRecordStartArge.setText("出发地："+dataBean.getFromArea());
        viewHolder.tvItemRecordEndArge.setText("目的地："+dataBean.getToArea());
        viewHolder.tvItemRecordTime.setText(dataBean.getStartDate()+"  "+dataBean.getEndDate());
        viewHolder.ivItemChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserTask.getInstance().isLogin()&&!dataBean.getImUserName().equals(UserTask.getInstance().getUser().getImUsername())){
                    UserCacheManager.save(dataBean.getImUserName()
                            ,dataBean.getNick(),dataBean.getHeadUrl());
                    PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.USER_NICK,dataBean.getImUserName());
                    activity.startActivity(new Intent(activity, ChatActivity.class)
                            .putExtra(Constanthuan.EXTRA_USER_ID, dataBean.getImUserName()).putExtra("nick", dataBean.getNick()));
                }else if (!UserTask.getInstance().isLogin()){
                   ActivityUtils.skipActivity(activity, LogineActivity.class,0,"");
                }

            }
        });
        viewHolder.ivItemRecordHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserTask.getInstance().isLogin()){
                    if (type.equals(ConstantsBean.PIPEI)){
                        ActivityUtils.skipActivity(activity,PinYouInfoActivity.class,dataBean.getInfoId(),"");
                    }else {
                        ActivityUtils.skipActivity(activity,TourismUserShowActivity.class,0, String.valueOf(dataBean.getUserId()));
                    }
                }else {
                    ActivityUtils.skipActivity(activity, LogineActivity.class,0,"");
                }


            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSimilarity,tvItemRecordEndArge,tvItemRecordStartArge,tvItemRecordTime,tvItemRecordSex,etItemRecordNick,tvItemRene;
        ImageView ivItemRecordHeader,ivItemChat;


        public ViewHolder(View itemView) {
            super(itemView);
            tvSimilarity=itemView.findViewById(R.id.tvSimilarity);
            tvItemRecordEndArge=itemView.findViewById(R.id.tvItemRecordEndArge);
            tvItemRecordStartArge=itemView.findViewById(R.id.tvItemRecordStartArge);
            tvItemRecordTime=itemView.findViewById(R.id.tvItemRecordTime);
            tvItemRecordSex=itemView.findViewById(R.id.tvItemRecordSex);
            etItemRecordNick=itemView.findViewById(R.id.etItemRecordNick);
            ivItemRecordHeader=itemView.findViewById(R.id.ivItemRecordHeader);
            tvItemRene=itemView.findViewById(R.id.tvItemRene);
            ivItemChat=itemView.findViewById(R.id.ivItemChat);
        }
    }

}
