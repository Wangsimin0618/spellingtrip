package com.spellingtrip.example.viewpager;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spellingtrip.example.R;

public class NoticeRemindAdapter extends RecyclerView.Adapter<NoticeRemindAdapter.ViewHolder>{
    private Activity activity;
    public NoticeRemindAdapter(Activity activity) {
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notice_remind,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvItemRemindTitle.setText("爬山大队活动");
        viewHolder.tvItemRemindContent.setText("还有两个小时就要开始活动了,不要迟到o~");
        viewHolder.tvItemRemindtime.setText("1小时前");
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemRemindTitle,tvItemRemindContent,tvItemRemindtime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemRemindTitle= itemView.findViewById(R.id.tvItemRemindTitle);
            tvItemRemindContent= itemView.findViewById(R.id.tvItemRemindContent);
            tvItemRemindtime= itemView.findViewById(R.id.tvItemRemindtime);
        }
    }
}
