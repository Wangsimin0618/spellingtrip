package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.TourismUserShowActivity;
import com.spellingtrip.example.bean.HotPinBean;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.view.ShapedImageView;
import java.util.List;

public class HotPinAdapter extends BaseAdapter {
    private List<HotPinBean.DataBean> hots;
    private Activity activity;
    public HotPinAdapter( List<HotPinBean.DataBean> hots,Activity activity) {
        this.hots=hots;
        this.activity=activity;

    }

    @Override
    public int getCount() {
        return hots.size();
    }

    @Override
    public Object getItem(int position) {
        return hots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PinViewHolder viewHolder=null;
        if (convertView==null){
            if (convertView == null) {
                viewHolder = new PinViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotpin, null);
                viewHolder.ivHotPin= convertView.findViewById(R.id.ivHotPin);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (PinViewHolder) convertView.getTag();
            }
        }else {
           viewHolder= (PinViewHolder) convertView.getTag();
        }
     String  imgurl= hots.get(position).getHeadUrl()+"?x-oss-process=style/320_320";
        Glide.with(activity).load(imgurl).into(viewHolder.ivHotPin);
        viewHolder. ivHotPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity, TourismUserShowActivity.class, 0, String.valueOf(hots.get(position).getUserId()));
                 }
        });
        return convertView;
    }
    class PinViewHolder{
        private ShapedImageView ivHotPin;
    }
}
