package com.spellingtrip.example.viewpager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.utils.SplitString;

import java.util.List;

/**
 * date:2020/4/24
 * author:王思敏
 * function
 */
public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;

    private int defItem = -1;
    private OnItemListener onItemListener;

    public LabelAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public interface OnItemListener {
        void onClick(View v, int pos, String projectc);
    }

    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.label_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.tv.setText(SplitString.getImg("#"+list.get(position)));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * ViewHolder的类，用于缓存控件
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.label_txt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemListener != null) {
                        onItemListener.onClick(v, getLayoutPosition(), list.get(getLayoutPosition()));
                    }
                }
            });
        }


    }
}
