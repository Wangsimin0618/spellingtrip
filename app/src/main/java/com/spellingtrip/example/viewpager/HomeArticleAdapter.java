package com.spellingtrip.example.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.WebShowActivity;
import com.spellingtrip.example.bean.HomeDataBean;
import com.spellingtrip.example.utils.ActivityUtils;

import java.util.List;

public class HomeArticleAdapter extends BaseAdapter {
    private List<HomeDataBean.DataBean.RowsBean> rows;

    public HomeArticleAdapter(List<HomeDataBean.DataBean.RowsBean> rows) {
        super();
        this.rows = rows;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, null);
            viewHolder. tvArticleTitle = convertView.findViewById(R.id.tvArticleTitle);
            viewHolder.tvArticleContent = convertView.findViewById(R.id.tvArticleContent);
            viewHolder.ivArticlePhoto = convertView.findViewById(R.id.ivArticlePhoto);
            viewHolder. llArticle = convertView.findViewById(R.id.llArticle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvArticleTitle.setText(rows.get(position).getTitle() + "");
        viewHolder.tvArticleContent.setText(rows.get(position).getSummary() + "");
        Glide.with(parent.getContext())
                .load(rows.get(position).getFilepath())
                .into(viewHolder.ivArticlePhoto);
        viewHolder. llArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(parent.getContext(), WebShowActivity.class,rows.get(position).getId(),rows.get(position).getTitle());
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvArticleTitle, tvArticleContent;
        LinearLayout llArticle;
        ImageView ivArticlePhoto;
    }
}
