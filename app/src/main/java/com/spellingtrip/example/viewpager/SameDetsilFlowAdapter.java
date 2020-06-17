package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.SameDetailBean;
import com.spellingtrip.example.view.ShapedImageView;
import com.spellingtrip.example.view.VerticalDashView;

import java.util.List;

/**
 * date:2020/5/6
 * author:王思敏
 * function同城详情页活动流程
 */
public class SameDetsilFlowAdapter extends RecyclerView.Adapter<SameDetsilFlowAdapter.ViewHolder> {

    private Activity activity;
    private List<SameDetailBean.DataBean.MembersBean> mMembersBeans;

    public SameDetsilFlowAdapter(Activity activity, List<SameDetailBean.DataBean.MembersBean> mMembersBeans) {
        this.activity = activity;
        this.mMembersBeans = mMembersBeans;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.samedetail_flow, viewGroup,false);
        SameDetsilFlowAdapter.ViewHolder viewHolder = new SameDetsilFlowAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.v("samedetailflowadapter","-----mMembersBeans.size()=="+mMembersBeans.size());


    }

    @Override
    public int getItemCount() {
        return mMembersBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView mFlowname,flowcontent,flowdata;
        private final VerticalDashView mFlowxx;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFlowname = itemView.findViewById(R.id.flowname);
            flowcontent = itemView.findViewById(R.id.flowcontent);
            flowdata = itemView.findViewById(R.id.flowdata);
            mFlowxx = itemView.findViewById(R.id.flow_xx);
        }
    }
}
