package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.List;

class PeopleHeaderAdaper extends BaseAdapter {
    private List<String> userIcons;
    private Activity activity;
    public PeopleHeaderAdaper(List<String> userIcons, Activity activity) {
        this.userIcons=userIcons;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return userIcons.size();
    }

    @Override
    public Object getItem(int position) {
        return userIcons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
             viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(activity).inflate(R.layout.item_peoplehwader,null);
           viewHolder.ivItemPeopleHeader= convertView.findViewById(R.id.ivItemPeopleHeader);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        String  imgurl=userIcons.get(position)+"?x-oss-process=style/320_320";
        Glide.with(activity).load(imgurl).into(viewHolder.ivItemPeopleHeader);
        return convertView;
    }
    class ViewHolder{
        ShapedImageView ivItemPeopleHeader;
    }
}
