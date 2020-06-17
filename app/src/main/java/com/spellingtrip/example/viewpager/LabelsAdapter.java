package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.Label;
import com.spellingtrip.example.bean.LabelBean;

import java.util.List;

/**
 * date:2020/5/16
 * author:王思敏
 * function同城发布选择标签
 */
public class LabelsAdapter extends RecyclerView.Adapter<LabelsAdapter.ViewHolder> {
    private List<Label.DataBean> labelList;
    public LabelsAdapter( List<Label.DataBean> data) {
        this.labelList=data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.label, viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
     viewHolder.mTypename.setText(labelList.get(i).getTypeName()+"");
        viewHolder.mTypename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener !=null){
                    Log.v("samelabeladapter","-----cbLabel------name=="+labelList.get(i).getTypeName());
                    mOnItemClickListener.onclick(labelList.get(i).getTypeName()+"",labelList.get(i).getTypeId()+"");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return labelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTypename;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTypename = itemView.findViewById(R.id.typename);
        }
    }


    public interface onItemClickListener{
        void onclick(String name,String id);
    }
    private onItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
