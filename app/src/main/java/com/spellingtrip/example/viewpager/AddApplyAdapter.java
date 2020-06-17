package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.AddApplyActivity;
import com.spellingtrip.example.bean.ActivityCallBack;
import com.spellingtrip.example.bean.AddApplayBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ShapedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.Call;

public class AddApplyAdapter extends RecyclerView.Adapter<AddApplyAdapter.ViewHolder>{
    private Activity activity;
    private List<AddApplayBean.DataBean> applays;
    public AddApplyAdapter(Activity activity, List<AddApplayBean.DataBean> applays) {
        this.applays=applays;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_addapply,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
       AddApplayBean.DataBean dataBean= applays.get(i);
        viewHolder.tvItemAddNick.setText(dataBean.getNick());
        viewHolder.tvItemAddTime.setText(dataBean.getApplyTime());
        viewHolder.tvItemLocation.setText(dataBean.getActivityName());
        String url=dataBean.getHeadUrl()+"?x-oss-process=style/320_320";
        Glide.with(activity).load(url).into(viewHolder.ivItemAddApplyHeader);
        viewHolder.tvItemAddJuJue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=ConstantsBean.JUJUE_ADDAPPLY;
                setJuJue(dataBean.getApplyId(),url);
            }
        });
        viewHolder.tvItemAddTongYi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=ConstantsBean.TONGYI_ADDAPPLY;
                setJuJue(dataBean.getApplyId(),url);
            }
        });
    }

    private void setJuJue(int applyId,String url) {
        String userId= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+url).addParams("userId",userId)
                .addParams("applyId", String.valueOf(applyId))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    ActivityCallBack activityCallBack= (ActivityCallBack) JsonUtil.fromJson(s, ActivityCallBack.class);
                    if (activityCallBack.getCode()==0){
                        ToastUtil.show(activityCallBack.getMsg());
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.DETELE_ADDAPPLY));
                    }else {
                        ToastUtil.show(activityCallBack.getMsg());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return applays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShapedImageView ivItemAddApplyHeader;
        private TextView tvItemAddJuJue,tvItemAddTongYi,tvItemLocation,tvItemAddTime,tvItemAddNick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemAddApplyHeader= itemView.findViewById(R.id.ivItemAddApplyHeader);
             tvItemAddJuJue= itemView.findViewById(R.id.tvItemAddJuJue);
             tvItemAddTongYi= itemView.findViewById(R.id.tvItemAddTongYi);
            tvItemLocation=itemView.findViewById(R.id.tvItemLocation);
            tvItemAddTime=itemView.findViewById(R.id.tvItemAddTime);
            tvItemAddNick=itemView.findViewById(R.id.tvItemAddNick);
        }
    }
}
