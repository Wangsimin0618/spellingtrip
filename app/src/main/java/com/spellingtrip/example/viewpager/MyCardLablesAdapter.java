package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.bean.UserShowBean;

import java.util.List;

public class MyCardLablesAdapter extends RecyclerView.Adapter<MyCardLablesAdapter.MyViewHolder> {

    private Context context;
    private List<UserInfoDataBean.LabelsBean> list;

    private int defItem = -1;


    public MyCardLablesAdapter(Context context, List<UserInfoDataBean.LabelsBean> list) {
        this.context = context;
        this.list = list;
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
                context).inflate(R.layout.item_seachhotvlaue, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(list.get(position).getLabelName());
      /*  if (defItem != -1) {
            if (defItem == position) {
                holder.tv.setTextColor(context.getResources().getColor(R.color.text_color));
                holder.tv.setBackgroundResource(R.drawable.goodsarrtesel_shape);
            } else {
                holder.tv.setTextColor(context.getResources().getColor(R.color.text_14));
                holder.tv.setBackgroundResource(R.drawable.goodsarrte_shape);


            }
        }*/
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
            tv = (TextView) view.findViewById(R.id.tv_hotscolor);

        }


    }
}



