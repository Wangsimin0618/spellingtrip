package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.HotCityListActivity;
import com.spellingtrip.example.activity.SeachResultActivity;
import com.spellingtrip.example.bean.HotCityBean;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.List;

public class HotCityAdapter extends BaseAdapter {
    private Activity activity;
    private List<HotCityBean.DataBean> data;

    public HotCityAdapter(Activity activity, List<HotCityBean.DataBean> data) {
        this.activity=activity;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
             convertView= LayoutInflater.from(activity).inflate(R.layout.item_hotcity,null);
            viewHolder.tvCityName= convertView.findViewById(R.id.tvItemCityName);
            viewHolder.cityLogo=convertView.findViewById(R.id.ivItemCityLogo);
            viewHolder.llItemHotCity=convertView.findViewById(R.id.llItemHotCity);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        HotCityBean.DataBean dataBean=data.get(position);
        String url=dataBean.getCityIcon()+"?x-oss-process=style/128_128";
        Glide.with(activity).load(url).into(viewHolder.cityLogo);
        viewHolder.tvCityName.setText(dataBean.getCity());
        viewHolder.llItemHotCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity, SeachResultActivity.class,0,dataBean.getCity());
            }
        });
        return convertView;
    }
    class ViewHolder{
        ShapedImageView cityLogo;
        TextView tvCityName;
        LinearLayout llItemHotCity;
    }
}
