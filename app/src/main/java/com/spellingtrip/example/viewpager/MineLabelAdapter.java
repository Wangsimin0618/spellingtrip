package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.SetLabelActivity;

import com.spellingtrip.example.bean.UserInfoDataBean;

import com.spellingtrip.example.utils.ActivityUtils;

import java.util.List;

public class MineLabelAdapter extends RecyclerView.Adapter<MineLabelAdapter.ViewHolder> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_MEDIA = 2;
    private Activity activity;
    private List<UserInfoDataBean.LabelsBean> labels;
    private int[] images = new int[]{R.mipmap.biaoji01, R.mipmap.tab_zong, R.mipmap.tab_hong, R.mipmap.tab_lv, R.mipmap.tab_lan
    };

    public MineLabelAdapter(Activity activity, List<UserInfoDataBean.LabelsBean> labels) {
        this.activity = activity;
        this.labels = labels;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlAdapterLabel;

        TextView tvAdapterLabelTitle;

        ViewHolder(View view) {
            super(view);
            rlAdapterLabel = view.findViewById(R.id.rlAdapterLabel);
            tvAdapterLabelTitle = view.findViewById(R.id.tvAdapterLabelTitle);

        }
    }

    @Override
    public int getItemCount() {
        if (labels.size()>5){
            return labels.size() == 0 ? 1 : labels.size()-(labels.size()-5) + 1;
        }else {
            return labels.size() == 0 ? 1 : labels.size() + 1;
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position == labels.size() || labels.size() == 0||position==labels.size()-(labels.size()-5)) {
            return TYPE_ADD;
        } else {
            return TYPE_MEDIA;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_label, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == TYPE_ADD) {
            viewHolder.tvAdapterLabelTitle.setText("更多");
            viewHolder.rlAdapterLabel.setBackgroundResource(R.mipmap.tab_hong);
            viewHolder.tvAdapterLabelTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.skipActivity(activity, SetLabelActivity.class, 0, "");
                }
            });
        } else {
            if (position<5){
                viewHolder.rlAdapterLabel.setBackgroundResource(images[position]);
                viewHolder.tvAdapterLabelTitle.setText(labels.get(position).getLabelName());
            }

        }
    }

}
