package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.AddressBean;
import com.spellingtrip.example.bean.HotCityBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * date:2020/5/11
 * author:王思敏
 * function搜索地图列表展示
 */
public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {
    private Activity mActivity;
    private List<AddressBean> list;

    public MapAdapter(Activity activity) {
        this.mActivity = activity;
    }



    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String name,String content);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.map_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (list.size() !=0){
            viewHolder.mMapname.setText(list.get(i).getTitle());
            viewHolder.mMapcontent.setText(list.get(i).getText());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener !=null){
                    mItemClickListener.onItemClick(list.get(i).getTitle(),list.get(i).getText());
                }
            }
        });
    }
    /**
     * 设置数据集
     * @param data
     */
    public void setData(List<AddressBean> data){
        this.list = data;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mMapname,mMapcontent;
        private final RelativeLayout relay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relay = itemView.findViewById(R.id.relay);
            mMapname = itemView.findViewById(R.id.map_title);
            mMapcontent = itemView.findViewById(R.id.map_content);
        }
    }
}
