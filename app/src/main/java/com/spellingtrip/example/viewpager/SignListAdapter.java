package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.SignListBean;
import com.spellingtrip.example.utils.CommonUtil;

import java.util.List;

public class SignListAdapter extends BaseAdapter {
    private Activity activity;
    private List<SignListBean.DataBean> data;

    public SignListAdapter(Activity activity, List<SignListBean.DataBean> data) {
        this.activity=activity;
        this.data=data;
    }

    @Override
    public int getCount() {
        return 7;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
             viewHolder=new ViewHolder();
             convertView= LayoutInflater.from(activity).inflate(R.layout.item_sign,null);
            viewHolder.ivItemSign= convertView.findViewById(R.id.ivItemSign);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        ImageView ivItemSign;
    }
}
