package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.BlackListActivity;
import com.spellingtrip.example.bean.BlackListBean;
import com.spellingtrip.example.utils.CommonUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

/**
 * 首页
 */
public class BlackListAdapter extends RecyclerView.Adapter<BlackListAdapter.ViewHolder> implements View.OnClickListener {
    private final List<BlackListBean.DataBean.RowsBean> lists;
    private Activity activity;
    public OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public BlackListAdapter(List<BlackListBean.DataBean.RowsBean> lists, Activity activity) {
        this.lists = lists;
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_black, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvItemRemove.setTag(lists.get(position).getTargetUserId());
        Glide.with(activity).load(lists.get(position).getHeadUrl()).transform(new CommonUtil.GlideCircleTransform(activity)).into(holder.tvItemBlackHeader);
        holder.tvItemBlackNick.setText(lists.get(position).getNick());
        holder.tvItemRemove.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick(v,(Integer) v.getTag());
        }
    }


    public interface OnItemClickListener{
            void onItemClick(View view,int position);
        }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView tvItemBlackHeader;
        private TextView tvItemBlackNick,tvItemRemove;


        public ViewHolder(View itemView) {
            super(itemView);
            tvItemBlackHeader = itemView.findViewById(R.id.tvItemBlackHeader);
            tvItemBlackNick = itemView.findViewById(R.id.tvItemBlackNick);
            tvItemRemove = itemView.findViewById(R.id.tvItemRemove);
        }
    }
}

