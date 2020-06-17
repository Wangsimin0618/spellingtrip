package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
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
import com.spellingtrip.example.utils.ToastUtil;

import java.util.List;

public class DetailListAdapter extends BaseAdapter {
    private List<PiPeiRecordBean.DataBean> lists;
    private Activity activity;

    public DetailListAdapter(List<PiPeiRecordBean.DataBean> lists, Activity activity) {
        this.lists=lists;

        this.activity=activity;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PiPeiRecordBean.DataBean dataBean=lists.get(position);
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(activity).inflate(R.layout.item_detail,null);
            viewHolder.etItemDetailRecordNick=convertView.findViewById(R.id.etItemDetailRecordNick);
            viewHolder.tvItemLocationRecord=convertView.findViewById(R.id.tvItemLocationRecord);
            viewHolder.tvItemDetailRecordTime=convertView.findViewById(R.id.tvItemDetailRecordTime);
            viewHolder.tvDetailSimilarity=convertView.findViewById(R.id.tvDetailSimilarity);
            viewHolder.tvDetailLike=convertView.findViewById(R.id.tvDetailLike);
            viewHolder.ivItemDetailRecordHeader=convertView.findViewById(R.id.ivItemDetailRecordHeader);
            viewHolder.rlToChat=convertView.findViewById(R.id.rlToChat);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(dataBean.getHeadUrl())){
            String  imgurl=dataBean.getHeadUrl()+"?x-oss-process=style/320_320";
            Glide.with(activity).load(imgurl).transform(new CommonUtil.GlideCircleTransform(activity))
                    .into(viewHolder.ivItemDetailRecordHeader);
        }

        viewHolder.etItemDetailRecordNick.setText(dataBean.getNick());
        viewHolder.tvItemLocationRecord.setText(dataBean.getFromArea()+"-"+dataBean.getToArea());
        viewHolder.tvItemDetailRecordTime.setText(dataBean.getStartDate()+"-"+dataBean.getEndDate());
        viewHolder.tvDetailSimilarity.setText(dataBean.getTripScore()+"");
        viewHolder.tvDetailLike.setText(dataBean.getLabelScore()+"");
        viewHolder.rlToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.getUserId()!= UserTask.getInstance().getUser().getUserId()){
                    UserCacheManager.save(dataBean.getImUserName()
                            ,dataBean.getNick(),dataBean.getHeadUrl());
                    PreferenceUtil.putString(ConstantsBean.Spell,ConstantsBean.USER_NICK,dataBean.getImUserName());
                    activity.startActivity(new Intent(activity, ChatActivity.class).putExtra(Constanthuan.EXTRA_USER_ID, dataBean.getImUserName())
                            .putExtra("nick", dataBean.getNick()));
                }else {
                    ToastUtil.show("请于他人聊天哦");
                }

            }
        });
        viewHolder.ivItemDetailRecordHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { ActivityUtils.skipActivity(activity,TourismUserShowActivity.class,0, String.valueOf(dataBean.getUserId()));
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView etItemDetailRecordNick,tvItemLocationRecord,tvItemDetailRecordTime,tvDetailSimilarity,tvDetailLike;
        ImageView ivItemDetailRecordHeader;
        RelativeLayout rlToChat;
    }


}
