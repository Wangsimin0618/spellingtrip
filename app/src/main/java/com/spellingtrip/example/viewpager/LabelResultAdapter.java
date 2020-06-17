package com.spellingtrip.example.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.SeachLabelresultActivity;
import com.spellingtrip.example.activity.SeachResultActivity;
import com.spellingtrip.example.bean.HotCityBean;
import com.spellingtrip.example.utils.ActivityUtils;

import java.util.List;

/**
 * date:2020/5/13
 * author:王思敏
 * function
 */
public class LabelResultAdapter extends RecyclerView.Adapter<LabelResultAdapter.ViewHolder> {
    private Context mContext;
    private List<HotCityBean.DataBean> list;

    public LabelResultAdapter(Context context, List<HotCityBean.DataBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.hotseach_item, viewGroup,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i < 3) {
            viewHolder.hotseachnumber.setTextColor(mContext.getResources().getColor(R.color.hotseach));
        }
        viewHolder.hotseachnumber.setText(i + 1 + "");
        viewHolder.mHotseachname.setText(list.get(i).getCity() + "");
        viewHolder.mHotseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.skipActivity(mContext, SeachLabelresultActivity.class, 0, list.get(i).getCity());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout mHotseach;
        private final TextView mHotseachname, hotseachnumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHotseach = itemView.findViewById(R.id.hotseach);
            hotseachnumber = itemView.findViewById(R.id.hotseachnumber);
            mHotseachname = itemView.findViewById(R.id.hotseachname);
        }
    }
}
