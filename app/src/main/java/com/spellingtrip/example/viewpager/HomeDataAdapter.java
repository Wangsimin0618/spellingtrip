package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.platform.comapi.map.A;
import com.bumptech.glide.Glide;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.WebShowActivity;
import com.spellingtrip.example.bean.HomeDataBean;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.view.ScrollGridView;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class HomeDataAdapter extends  RecyclerView.Adapter<HomeDataAdapter.ViewHolder> {
    private Activity activity;
    private List<HomeDataBean.DataBean.RowsBean> rows;
    private View mHeaderView;


    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public HomeDataAdapter(Activity activity, List<HomeDataBean.DataBean.RowsBean> rows) {
        this.activity=activity;
        this.rows=rows;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_article, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final HomeDataBean.DataBean.RowsBean rowsBean= rows.get(position);
        viewHolder.tvArticleTitle.setText(rowsBean.getTitle()+"");
        viewHolder.tvArticleContent.setText(rowsBean.getSummary()+ "");
        Glide.with(activity)
                .load(rowsBean.getFilepath())
                .into(viewHolder.ivArticlePhoto);
        viewHolder.llArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity, WebShowActivity.class, rowsBean.getId(), rowsBean.getTitle());
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return rows == null ? 0 : rows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvArticleTitle;
        private ImageView ivArticlePhoto;
        private LinearLayout llArticle;
        private TextView tvArticleContent;
        public ViewHolder(View itemView) {
            super(itemView);
            tvArticleTitle = itemView.findViewById(R.id.tvArticleTitle);
            tvArticleContent = itemView.findViewById(R.id.tvArticleContent);
            ivArticlePhoto = itemView.findViewById(R.id.ivArticlePhoto);
            llArticle = itemView.findViewById(R.id.llArticle);
        }
    }

}
