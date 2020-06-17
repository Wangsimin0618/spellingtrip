package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.PublishPinActivity;
import com.spellingtrip.example.view.ShapedImageView;

import java.util.List;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder>{
    private Activity activity;
    private List<String> lables;

    public AddAdapter(Activity activity) {
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_addlable, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int position) {
        final String labke=lables.get(position);
        viewHolder.tvItemAddLableTitle.setText(labke+"");
        viewHolder.ivItemAddLableDetel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lables.remove(position);
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return lables.size();
    }

    public void setData(List<String> mlables) {
        this.lables= mlables;
        notifyDataSetChanged();//通知更新
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemAddLableTitle;
        private ImageView ivItemAddLableDetel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemAddLableTitle=itemView.findViewById(R.id.tvItemAddLableTitle);
            ivItemAddLableDetel=itemView.findViewById(R.id.ivItemAddLableDetel);
        }
    }
}
