package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;

import com.spellingtrip.example.bean.MyCollectBean;

import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.view.ScrollGridView;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
;


public class MineCollectAdapter extends RecyclerView.Adapter<MineCollectAdapter.ViewHolder> {
    private List<MyCollectBean.DataBean.RowsBean> collects;
    private Activity activity;
    public MineCollectAdapter(List<MyCollectBean.DataBean.RowsBean> collects, Activity activity) {
        this.collects=collects;
        this.activity=activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_minecollect, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMineCollectUserTitle.setText(collects.get(position).getNick() + "");
        holder.tvMineCollectUserSendTime.setText(collects.get(position).getCreateTime() + "");
        holder.tvMineCollectSendContent.setText(collects.get(position).getContent());
        holder.gvMineCollecttourismImg.setTag(collects.get(position).getImages());
        if (holder.gvMineCollecttourismImg.getTag() != null && holder.gvMineCollecttourismImg.getTag().equals(collects.get(position).getImages())) {
            holder.gvMineCollecttourismImg.setVisibility(View.VISIBLE);
            holder.gvMineCollecttourismImg.setAdapter(new MineCollectImagesAdapter(activity, collects.get(position).getImages()));
        }else {
            holder.gvMineCollecttourismImg.setVisibility(View.GONE);
        }
        Glide.with(activity).load(collects.get(position).getHeadUrl())
                .transform(new CommonUtil.GlideCircleTransform(activity)).into(holder.ivMineCollectTourismHeader);
        if (collects.get(position).getVideo()!=null&&!TextUtils.isEmpty(collects.get(position).getVideo().getUrl())){
            holder.itemCollectVideoView.setVisibility(View.VISIBLE);
            String imageurl=collects.get(position).getVideo().getUrl()+"?x-oss-process=video/snapshot,t_0,f_jpg,w_800,h_600,m_fast";
            holder.itemCollectVideoView.setUp(
                    collects.get(position).getVideo().getUrl(),imageurl,
                    "",null);

        }else {
            holder.itemCollectVideoView.setVisibility(View.GONE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return collects.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMineCollectTourismHeader;
        private TextView tvMineCollectUserTitle, tvMineCollectUserSendTime,tvMineCollectSendContent;
        private ScrollGridView gvMineCollecttourismImg;
        private JCVideoPlayer itemCollectVideoView;

        public ViewHolder(View itemView) {
            super(itemView);

           ivMineCollectTourismHeader = itemView.findViewById(R.id.ivMineCollectTourismHeader);
            tvMineCollectUserTitle= itemView.findViewById(R.id.tvMineCollectUserTitle);
             tvMineCollectUserSendTime= itemView.findViewById(R.id.tvMineCollectUserSendTime);
            gvMineCollecttourismImg = itemView.findViewById(R.id.gvMineCollecttourismImg);
            tvMineCollectSendContent = itemView.findViewById(R.id.tvMineCollectSendContent);
            itemCollectVideoView=itemView.findViewById(R.id.itemCollectVideoView);
        }
    }

}
