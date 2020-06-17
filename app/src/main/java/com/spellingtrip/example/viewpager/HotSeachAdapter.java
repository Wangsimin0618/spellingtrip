package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.SeachResultActivity;
import com.spellingtrip.example.bean.HotCityBean;
import com.spellingtrip.example.utils.ActivityUtils;

import java.util.List;

/**
 * date:2020/4/26
 * author:王思敏
 * function热门搜索
 */
public class HotSeachAdapter extends RecyclerView.Adapter<HotSeachAdapter.ViewHolder> {
    private Context mContext;
    private List<HotCityBean.DataBean> list;

    public HotSeachAdapter(Context context, List<HotCityBean.DataBean> list) {
        this. mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.hotseach_item, viewGroup,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       if (i<3){
           viewHolder.hotseachnumber.setTextColor(mContext.getResources().getColor(R.color.hotseach));
       }
     viewHolder.hotseachnumber.setText(i+1+"");
    viewHolder.mHotseachname.setText(list.get(i).getCity()+"");
    viewHolder.mHotseach.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ActivityUtils.skipActivity(mContext, SeachResultActivity.class,0,list.get(i).getCity());
        }
    });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout mHotseach;
        private final TextView mHotseachname,hotseachnumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHotseach = itemView.findViewById(R.id.hotseach);
            hotseachnumber = itemView.findViewById(R.id.hotseachnumber);
            mHotseachname = itemView.findViewById(R.id.hotseachname);
        }
    }
}
