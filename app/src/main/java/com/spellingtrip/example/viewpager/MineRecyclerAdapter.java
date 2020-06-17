package com.spellingtrip.example.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.MyPublishBean;
import com.spellingtrip.example.view.ScrollGridView;


import java.util.List;


import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MineRecyclerAdapter extends RecyclerView.Adapter<MineRecyclerAdapter.ViewHolder> {
    private List<MyPublishBean.DataBean> lists;
    private FragmentActivity activity;

    public MineRecyclerAdapter(FragmentActivity activity, List<MyPublishBean.DataBean> lists) {
        this.lists = lists;
        this.activity = activity;
    }


    /**
     * 相册布局设置
     * @param
     * @param
     */
    private void setItemImage(ScrollGridView gvItemImage, List<MyPublishBean.DataBean.ImagesBean> images) {
        if (images!=null&&images.size()>0){
            gvItemImage.setVisibility(View.VISIBLE);
            gvItemImage.setAdapter(new PublishImageAdapter(activity, images));
        }else {
            gvItemImage.setVisibility(View.GONE);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_mypublish, null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        MyPublishBean.DataBean dataBean=lists.get(position);
      /*  LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) viewHolder.itemVideoView.getLayoutParams();
        int width=CommonUtil.getScreenWidth(activity);
        int spec=CommonUtil.dipToPx(activity,24);
        layoutParams.width=(width-spec)/3;
        layoutParams.height=(width-spec)/3;
        viewHolder.itemVideoView.setLayoutParams(layoutParams);*/
        viewHolder.tvItemPublishTime.setText(dataBean.getCreateTime());
        viewHolder.tvItemPublishContent.setText(dataBean.getContent());
        if (dataBean.getVideo()!=null&&dataBean.getVideo().getSize()>0){
            viewHolder.itemPublishCard.setVisibility(View.VISIBLE);
            String imageurl=dataBean.getVideo().getUrl()+"?x-oss-process=video/snapshot,t_0,f_jpg,w_800,h_600,m_fast";
            viewHolder.itemVideoView.setUp(
                    dataBean.getVideo().getUrl(),imageurl,
                    "",null);

        }else {
            viewHolder.itemPublishCard.setVisibility(View.GONE);
        }
        setItemImage(viewHolder.gvItemImage,dataBean.getImages());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView tvItemPublishTime,tvItemPublishContent;
        private ScrollGridView gvItemImage;
        private JCVideoPlayer itemVideoView;
        private CardView itemPublishCard;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvItemPublishTime=itemView.findViewById(R.id.tvItemPublishTime);
        tvItemPublishContent=itemView.findViewById(R.id.tvItemPublishContent);
        gvItemImage=itemView.findViewById(R.id.gvItemImage);
        itemVideoView= itemView.findViewById(R.id.itemVideoView);
        itemPublishCard=itemView.findViewById(R.id.itemPublishCard);
    }
}}
