package com.spellingtrip.example.viewpager;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.TourismUserShowActivity;
import com.spellingtrip.example.bean.MyPublishBean;
import com.spellingtrip.example.bean.TourisnBean;
import com.spellingtrip.example.view.ScrollGridView;

import java.util.List;

public class UserShowAdapter extends BaseAdapter {
    private List<TourisnBean.DataBean> lists;
    private int TYPE_HEADER = 1001;
    private int TYPE_BODY = 1002;
    private FragmentActivity activity;

    public UserShowAdapter(FragmentActivity activity, List<TourisnBean.DataBean> lists) {
        this.lists = lists;
        this.activity = activity;
    }


    /**
     * 相册布局设置
     * @param
     */
    private void setItemImage(ScrollGridView gvItemImage, int position) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) gvItemImage.getLayoutParams();
        if (lists.get(position).getImages()!=null&&lists.get(position).getImages().size()>0){
            gvItemImage.setVisibility(View.VISIBLE);
            gvItemImage.setAdapter(new UserShowImageAdapter(activity, lists.get(position).getImages()));
        }else {
            gvItemImage.setVisibility(View.INVISIBLE);

        }

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_mypublish, parent, false);
            viewHolder.tvItemPublishTime=convertView.findViewById(R.id.tvItemPublishTime);
            viewHolder.tvItemPublishContent=convertView.findViewById(R.id.tvItemPublishContent);
            viewHolder.gvItemImage=convertView.findViewById(R.id.gvItemImage);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvItemPublishTime.setText(lists.get(position).getCreateTime());
        viewHolder.tvItemPublishContent.setText(lists.get(position).getContent());
        setItemImage(viewHolder.gvItemImage,position);
        return convertView;
    }


    class ViewHolder {
        private TextView tvItemPublishTime,tvItemPublishContent;
        private ScrollGridView gvItemImage;



    }}

