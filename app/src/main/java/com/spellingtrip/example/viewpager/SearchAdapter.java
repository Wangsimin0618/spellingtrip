package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.WebShowActivity;
import com.spellingtrip.example.bean.HomeDataBean;
import com.spellingtrip.example.retrofit.bean.SearchBean;
import com.spellingtrip.example.utils.ActivityUtils;

import java.util.List;

public class SearchAdapter extends BaseAdapter{
    private Activity activity;
    private List<SearchBean.DataBean.RowsBean> rows;
    public SearchAdapter(Activity activity, List<SearchBean.DataBean.RowsBean> rows) {
        this.activity=activity;
        this.rows=rows;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
             viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(activity).inflate(R.layout.item_search,null);
            viewHolder.tvArticleTitle = convertView.findViewById(R.id.tvItemArticleTitle);
            viewHolder.tvArticleContent = convertView.findViewById(R.id.tvItemArticleContent);
            viewHolder.ivArticlePhoto = convertView.findViewById(R.id.ivItemArticlePhoto);
            viewHolder.llArticle = convertView.findViewById(R.id.llItemArticle);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final SearchBean.DataBean.RowsBean rowsBean= rows.get(position);
        viewHolder.tvArticleTitle.setText(rowsBean.getTitle()+"");
        viewHolder.tvArticleContent.setText(rowsBean.getSummary()+ "");
        Glide.with(CustomApplication.context)
                .load(rowsBean.getFilepath()+"")
                .into(viewHolder.ivArticlePhoto);
        viewHolder.llArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity, WebShowActivity.class, rowsBean.getId(), rowsBean.getTitle());
            }
        });
        return convertView;
    }
    class ViewHolder{
        private TextView tvArticleTitle;
        private ImageView ivArticlePhoto;
        private LinearLayout llArticle;
        private EditText tvArticleContent;
    }
}
