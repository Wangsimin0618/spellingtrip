package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.JieBanActivityDetailActivity;
import com.spellingtrip.example.activity.SameDetailActivity;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.SameBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.view.MyLayoutManager;
import com.spellingtrip.example.view.ShapedImageView;
import com.spellingtrip.example.view.SpacesItemDecoration;

import java.util.List;

/**
 * date:2020/4/29
 * author:王思敏
 * function同城item
 */
public class SameItemAdapter extends RecyclerView.Adapter<SameItemAdapter.ViewHolder> {
    private Activity mActivity;
    private List<SameBean.DataBean> list;
    private Bitmap bitmap;
    private HomeImageAdapter mHomeImageAdapter;

    public SameItemAdapter(Activity activity, List<SameBean.DataBean> list) {
        this.mActivity = activity;
        this.list = list;
    }

    public interface OnSameItemClick{
        void onSameitem(String UserId, String ActivityId);
    }
    private OnSameItemClick mOnSameItemClick;

    public void setOnSameItemClick(OnSameItemClick onSameItemClick){
        mOnSameItemClick = onSameItemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                mActivity).inflate(R.layout.same_item, viewGroup,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        String headUrl = dataBean.getHeadUrl();
        SameBean.DataBean dataBean = list.get(i);
        Glide.with(mActivity).load(dataBean.getHeadUrl()).into(viewHolder.mSameitemhead);
        if (dataBean.getSex()!=null) {
            if (dataBean.getSex().equals("男")) {
                viewHolder.sameitemsex.setBackgroundResource(R.drawable.man);
            }else {
                viewHolder.sameitemsex.setBackgroundResource(R.drawable.giry);
            }
        }

//        if (dataBean.isVip()){
            if (dataBean.getPayType()==3){
                viewHolder.mSameitemvip.setVisibility(View.VISIBLE);
                viewHolder.mSameitemvip.setBackgroundResource(R.drawable.vipyear);
                viewHolder.mSameitemname.setTextColor(mActivity.getResources().getColor(R.color.image_selector_red));
            }else {
                viewHolder.mSameitemvip.setVisibility(View.VISIBLE);
                viewHolder.mSameitemvip.setBackgroundResource(R.drawable.vip);
                viewHolder.mSameitemname.setTextColor(mActivity.getResources().getColor(R.color.image_selector_red));
            }
//        }
//        else {
//            viewHolder.mSameitemname.setTextColor(mActivity.getResources().getColor(R.color.history_text));
//            viewHolder.mSameitemvip.setVisibility(View.GONE);
//        }

        viewHolder.mSameitemname.setText(dataBean.getNick());

        if (dataBean.isActive()){
            viewHolder.mSameitemactive.setVisibility(View.VISIBLE);
        }else {
            viewHolder.view.setVisibility(View.GONE);
            viewHolder.mSameitemactive.setVisibility(View.GONE);

        }
        viewHolder.mSameitemage.setText(dataBean.getAge()+"");
        viewHolder.mSameitemaddress.setText("地点： "+dataBean.getLocation());
        viewHolder.mSameitemtime.setText("时间："+dataBean.getStartDate()+" - "+dataBean.getEndDate());
        viewHolder.mSameitemcapita.setText("人均：￥ "+dataBean.getCost());

        viewHolder.mSameitemlabel.setText("#"+dataBean.getTypeName()+"");
        viewHolder.mSameitemcontent.setText(dataBean.getContent());
        //设置文本最高显示两排最低一排
        viewHolder.mSameitemcontent.setLines(1);
        viewHolder.mSameitemcontent.setMaxLines(2);

        Glide.with(mActivity).load(dataBean.getCoverImage()).into(viewHolder.mSameitemtourismimg);
        viewHolder.mSameitemnum.setText("已有"+dataBean.getJoinedCount()+"人加入");

        if (list.get(i).getJoinUserList() !=null){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,true);
            linearLayoutManager.setStackFromEnd(true);
            viewHolder.mSameitempersion.setLayoutManager(linearLayoutManager);
            SameItemImageAdapter imageAdapter = new SameItemImageAdapter(mActivity,list.get(i).getJoinUserList());
            viewHolder.mSameitempersion.setAdapter(imageAdapter);
        }







        //点击条目进入详情页
        viewHolder.mSameitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("11111111111111111111","-----userid=="+dataBean.getUserId());
                if (mOnSameItemClick !=null){
                    mOnSameItemClick.onSameitem(dataBean.getUserId()+"",dataBean.getActivityId()+"");
                }
            }
        });

        viewHolder.mSameitemmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonDialog.homeitemPopupWindow(mActivity, view,dataBean.getUserId(), i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ShapedImageView mSameitemhead;
        private final TextView mSameitemname,mSameitemage,mSameitemactive,mSameitemaddress,mSameitemtime,mSameitemcapita,mSameitemcontent,view,mSameitemnum,mSameitemlabel;
        private final ImageView sameitemsex,mSameitemvip;
        private final RelativeLayout mSameitemmore;
        private final RecyclerView mSameitempersion;
        private final ShapedImageView mSameitemtourismimg;
        private final LinearLayout mSameitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSameitem = itemView.findViewById(R.id.same_item);
            mSameitemhead = itemView.findViewById(R.id.same_item_head);
            sameitemsex = itemView.findViewById(R.id.same_item_sex);
            mSameitemname = itemView.findViewById(R.id.same_item_name);
            mSameitemvip = itemView.findViewById(R.id.same_item_vip);
            mSameitemage = itemView.findViewById(R.id.same_item_age);
            mSameitemactive = itemView.findViewById(R.id.same_item_active);
            mSameitemmore= itemView.findViewById(R.id.same_item_more);
            mSameitemaddress= itemView.findViewById(R.id.same_item_address);
            mSameitemtime= itemView.findViewById(R.id.same_item_time);
            mSameitemcapita= itemView.findViewById(R.id.same_item_capita);
            mSameitemlabel= itemView.findViewById(R.id.same_item_label);
            mSameitemtourismimg= itemView.findViewById(R.id.same_item_tourismImg);
            mSameitemcontent= itemView.findViewById(R.id.same_item_content);
            view= itemView.findViewById(R.id.view);
            mSameitempersion= itemView.findViewById(R.id.same_item_persion);
            mSameitemnum= itemView.findViewById(R.id.same_item_num);
        }
    }
}
