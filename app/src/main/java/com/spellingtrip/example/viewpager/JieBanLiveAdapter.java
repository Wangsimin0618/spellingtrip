package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.JieBanActivityDetailActivity;
import com.spellingtrip.example.bean.NewActivityDetailBean;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.List;

public class JieBanLiveAdapter extends BaseAdapter {
    private Activity activity;
    private List<NewActivityDetailBean.DataBean.TourBean.TourLikeListBean> tourLikeList;

    public JieBanLiveAdapter(Activity activity, List<NewActivityDetailBean.DataBean.TourBean.TourLikeListBean> tourLikeList) {
        this.activity = activity;
        this.tourLikeList = tourLikeList;
    }

    @Override
    public int getCount() {
        return tourLikeList.size();
    }

    @Override
    public Object getItem(int position) {
        return tourLikeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
             viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_live, null);
            viewHolder.liveHeader=convertView.findViewById(R.id.sivItemLiveheader);
            viewHolder.tvItemLiveYuNumber=convertView.findViewById(R.id.tvItemLiveYuNumber);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
       String url=tourLikeList.get(position).getHeadUrl()+"?x-oss-process=style/64_64";
        Glide.with(activity).load(url).into(viewHolder.liveHeader);
        if (position==8){
            viewHolder.tvItemLiveYuNumber.setVisibility(View.VISIBLE);
            if (tourLikeList.size()>1000){
                viewHolder.tvItemLiveYuNumber.setText("+"+tourLikeList.size()/1000+"k");
            }else {
                viewHolder.tvItemLiveYuNumber.setText("+"+tourLikeList.size());
            }
        }else {
            viewHolder.tvItemLiveYuNumber.setVisibility(View.GONE);
        }


        return convertView;
    }

    class ViewHolder {
        ShapedImageView liveHeader;
        TextView tvItemLiveYuNumber;
    }
}
