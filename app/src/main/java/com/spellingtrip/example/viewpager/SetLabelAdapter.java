package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.SetLabelActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.LabelBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.sticky.StickyListHeadersAdapter;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.LabelUtils;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ScrollGridView;
import com.spellingtrip.example.view.SectionPinAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

public class SetLabelAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private Activity activity;
    private List<LabelBean.DataBean> data;


    public SetLabelAdapter(Activity activity, List<LabelBean.DataBean> data) {
        this.activity=activity;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
  ;

            ViewContentHolder viewHolder=null;
            if (convertView == null) {
                viewHolder = new ViewContentHolder();
                convertView = LayoutInflater.from(activity).inflate(R.layout.item_adapter_content, parent, false);
                viewHolder.mViewContentName=convertView.findViewById(R.id.gvLabelList);
                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewContentHolder) convertView.getTag();
            }
            LabelAdapter labelAdapter=new LabelAdapter(data.get(position).getLabelList());
            viewHolder.mViewContentName.setAdapter(labelAdapter);


        return convertView;
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_adapter_title, parent, false);
            holder.tvLabelTitle = (TextView) convertView.findViewById(R.id.tvLabelTitle);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

holder.tvLabelTitle.setText(data.get(position).getGroupName());
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return data.get(position).getGroupId();
    }




    class HeaderViewHolder {
        TextView tvLabelTitle;
    }
     class ViewContentHolder {
        private ScrollGridView mViewContentName;

    }
    private class LabelAdapter extends BaseAdapter{
        private  List<LabelBean.DataBean.LabelListBean> labelList;
        public LabelAdapter(List<LabelBean.DataBean.LabelListBean> labelList) {
            this.labelList=labelList;
        }

        @Override
        public int getCount() {
            return labelList.size();
        }

        @Override
        public Object getItem(int position) {
            return labelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LabelViewHolder viewHolder=null;
            if (convertView==null){
                viewHolder=new LabelViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label, null);
                viewHolder.cbLabel=convertView.findViewById(R.id.cbLabel);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (LabelViewHolder) convertView.getTag();
            }
            viewHolder.cbLabel.setText(labelList.get(position).getLabelName()+"");
            if (SetLabelActivity.labels.size()>0){
               String labels=JsonUtil.toJson(SetLabelActivity.labels);
               if ( labels.contains(labelList.get(position).getLabelName())){
                   viewHolder.cbLabel.setBackground(activity.getResources().getDrawable(R.drawable.labelsel_shape));
               }else {
                   viewHolder.cbLabel.setBackground(activity.getResources().getDrawable(R.drawable.labelnor_shape));
               }
            }
            final LabelViewHolder finalViewHolder = viewHolder;
            viewHolder.cbLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SetLabelActivity.labels.size()<20){
                        finalViewHolder.cbLabel.setBackground(activity.getResources().getDrawable(R.drawable.labelsel_shape));
                        LabelUtils.setLabel(labelList.get(position).getLabelId(),labelList.get(position).getLabelName());
                    }else {
                        ToastUtil.show("最多可选择20个标签");
                    }
                }
            });
            return convertView;
        }



        class LabelViewHolder{
            private CheckBox cbLabel;
        }
    }

}
