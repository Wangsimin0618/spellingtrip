package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.ChoosePinActivity;
import com.spellingtrip.example.bean.ChooseActivityBean;
import com.spellingtrip.example.bean.MyCollectBean;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.view.ScrollGridView;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class ChooseActivityAdapter extends BaseAdapter{
    private List<ChooseActivityBean.DataBean> lists;
    private Activity activity;
    private int selectedPosition=0;

    public ChooseActivityAdapter(List<ChooseActivityBean.DataBean> collects, Activity activity) {
        this.lists=collects;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ChooseActivityBean.DataBean dataBean=lists.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_chooseactivity, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindView(position);
        return convertView;
    }
    class ViewHolder {
        TextView textTitle;
        ImageView imageCheck, image_checknor;

        public ViewHolder(View view) {
            textTitle = (TextView) view.findViewById(R.id.text_activitytitle);
            imageCheck = (ImageView) view.findViewById(R.id.image_activitycheck);
            image_checknor = (ImageView) view.findViewById(R.id.image_activitychecknor);
        }

        public void bindView(int position) {
            if (position >= lists.size())
                return;
            ChooseActivityBean.DataBean poiItem = lists.get(position);
            textTitle.setText(poiItem.getTypeName());
            if (position == selectedPosition) {
                imageCheck.setVisibility(View.VISIBLE);
                image_checknor.setVisibility(View.GONE);
            } else {
                image_checknor.setVisibility(View.VISIBLE);
                imageCheck.setVisibility(View.GONE);
            }

        }
    }
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}

