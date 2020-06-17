package com.spellingtrip.example.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.MyPublishBean;
import com.spellingtrip.example.view.ScrollGridView;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MinePublishRecyclerAdapter extends RecyclerView.Adapter<MinePublishRecyclerAdapter.ViewHolder> {
    private List<MyPublishBean.DataBean> lists;
    private FragmentActivity activity;

    public MinePublishRecyclerAdapter(FragmentActivity activity, List<MyPublishBean.DataBean> lists) {
        this.lists = lists;
        this.activity = activity;
    }


    /**
     * 相册布局设置
     *
     * @param
     */
    private void setItemImage(ScrollGridView gvItemImage, int position) {
        if (lists.get(position).getImages() != null && lists.get(position).getImages().size() > 0) {
            gvItemImage.setVisibility(View.VISIBLE);
            gvItemImage.setAdapter(new PublishImageAdapter(activity, lists.get(position).getImages()));
        } else {
            gvItemImage.setVisibility(View.GONE);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mypublish, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.tvItemPublishTime.setText(lists.get(position).getCreateTime());
        viewHolder.tvItemPublishContent.setText(lists.get(position).getContent());
        if (lists.get(position).getVideo() != null && lists.get(position).getVideo().getSize() > 0) {
            viewHolder.itemVideoView.setVisibility(View.VISIBLE);
            String imageurl=lists.get(position).getVideo().getUrl()+"?x-oss-process=video/snapshot,t_0,f_jpg,w_800,h_600,m_fast";
            viewHolder.itemVideoView.setUp(lists.get(position).getVideo().getUrl(), imageurl, "",null);
        } else {
            viewHolder.itemVideoView.setVisibility(View.GONE);
        }
        setItemImage(viewHolder.gvItemImage, position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemPublishTime, tvItemPublishContent;
        private ScrollGridView gvItemImage;
        private JCVideoPlayer itemVideoView;


        public ViewHolder(View itemView) {
            super(itemView);
            tvItemPublishTime = itemView.findViewById(R.id.tvItemNewPublishTime);
            tvItemPublishContent = itemView.findViewById(R.id.tvItemNewPublishContent);
            gvItemImage = itemView.findViewById(R.id.gvItemNewImage);
            itemVideoView = itemView.findViewById(R.id.itemNewVideoView);
        }
    }
}
