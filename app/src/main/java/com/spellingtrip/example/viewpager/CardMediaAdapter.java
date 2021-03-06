package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.MyInfoCardActivity;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.ArrayList;
import java.util.List;

public class CardMediaAdapter extends RecyclerView.Adapter<CardMediaAdapter.ViewHolder> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_MEDIA = 2;

    private List<String> mMediaList = new ArrayList<>();
    private OnAddMediaListener mOnAddMediaListener;
    private Activity activity;

    public interface OnAddMediaListener {
        void onaddMedia();
    }

    public CardMediaAdapter(OnAddMediaListener mOnAddMediaListener, Activity activity) {
        this.mOnAddMediaListener = mOnAddMediaListener;
        this.activity=activity;

    }

    public void setData(List<String> mediaList) {
        mMediaList.clear();
        mMediaList.addAll(mediaList);
        notifyDataSetChanged();
    }

    public List<String> getData() {
        return mMediaList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ShapedImageView ivPicture;
        RelativeLayout rlItemImage;
        ViewHolder(View view) {
            super(view);
            ivPicture = (ShapedImageView) view.findViewById(R.id.ivCardPicture);
            rlItemImage=view.findViewById(R.id.rlCardItemImage);

        }
    }

    @Override
    public int getItemCount() {
        return mMediaList.size() == 0 ? 1 : mMediaList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mMediaList.size() || mMediaList.size() == 0) {
            return TYPE_ADD;
        } else {
            return TYPE_MEDIA;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_cardmedia, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) viewHolder.rlItemImage.getLayoutParams();
        int spex= CommonUtil.dipToPx(activity,70);
        int width=CommonUtil.getScreenWidth(activity);
        layoutParams.width=(width-spex)/3;
        layoutParams.height=(width-spex)/3;
        viewHolder.rlItemImage.setLayoutParams(layoutParams);
        if (getItemViewType(position) == TYPE_ADD) {
            if (mMediaList.size()==9){
                viewHolder.rlItemImage.setVisibility(View.GONE);
            }else {
                viewHolder.rlItemImage.setVisibility(View.VISIBLE);
                viewHolder.ivPicture.setImageResource(R.mipmap.card_add);
            }
            viewHolder.ivPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddMediaListener.onaddMedia();
                }
            });
        } else {
           /* viewHolder.llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = viewHolder.getAdapterPosition();
                    if (index != RecyclerView.NO_POSITION) {
                        mMediaList.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, mMediaList.size());
                    }
                }
            });*/
            String path = mMediaList.get(position);
            Glide.with(viewHolder.itemView.getContext())
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.ivPicture);
            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        mItemClickListener.onItemClick(adapterPosition, v);
                    }
                });
            }
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}
