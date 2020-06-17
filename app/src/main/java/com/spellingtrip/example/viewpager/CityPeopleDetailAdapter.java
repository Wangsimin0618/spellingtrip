package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.CityActivityDetailActivity;
import com.spellingtrip.example.activity.TourismUserShowActivity;
import com.spellingtrip.example.bean.ActivityDetailBean;
import com.spellingtrip.example.bean.HotPinBean;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.List;

public class CityPeopleDetailAdapter extends BaseAdapter {
    private List<ActivityDetailBean.DataBean.MembersBean> hots;
    private Activity activity;
    public CityPeopleDetailAdapter(List<ActivityDetailBean.DataBean.MembersBean> hots, Activity activity) {
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
        ViewHolder viewHolder;
        if (convertView==null){
             viewHolder=new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_citys, null);
            viewHolder.ivItemCitys=convertView.findViewById(R.id.ivItemCitys);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) viewHolder.ivItemCitys.getLayoutParams();
        int px= CommonUtil.dipToPx(activity,55);
        int width=CommonUtil.getScreenWidth(activity);
        layoutParams.height=(width-px)/6;
        viewHolder.ivItemCitys.setLayoutParams(layoutParams);
        if(!hots.get(position).getHeadUrl().equals(viewHolder.ivItemCitys.getTag())){
            viewHolder.ivItemCitys.setTag(null);
            String  imgurl= hots.get(position).getHeadUrl()+"?x-oss-process=style/320_320";
            Glide.with(activity)
                    .load(imgurl)
                    .into( viewHolder.ivItemCitys);
            viewHolder.ivItemCitys.setTag(hots.get(position).getHeadUrl());
        }
        viewHolder.ivItemCitys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity, TourismUserShowActivity.class, 0, String.valueOf(hots.get(position).getUserId()));
            }
        });
        return convertView;
    }
    class ViewHolder{
        ShapedImageView ivItemCitys;
    }

}

