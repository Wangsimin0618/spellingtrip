package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.LiveDetailActivity;

import java.util.List;

public class FurnitureAdapter extends BaseAdapter{
    private Activity activity;
    private List<String> lists;
    public FurnitureAdapter(Activity activity, List<String> lists) {
        this.activity=activity;
        this.lists=lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
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
            convertView= LayoutInflater.from(activity).inflate(R.layout.item_furniture,null);
            viewHolder.mTextView=convertView.findViewById(R.id.tvItemFurniture);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        String title=lists.get(position);
        viewHolder.mTextView.setText(title+"");
        return convertView;
    }

    class ViewHolder{
        TextView mTextView;
    }
}
