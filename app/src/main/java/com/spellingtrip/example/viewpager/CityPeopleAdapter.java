package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.CityPeoPleActvity;
import com.spellingtrip.example.bean.ActivityCallBack;
import com.spellingtrip.example.bean.CityPeopleBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.RemovePeopleBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.Call;

public class CityPeopleAdapter extends RecyclerView.Adapter<CityPeopleAdapter.ViewHolder>{
    private List<CityPeopleBean.DataBean> data;
    private Activity activity;
    public CityPeopleAdapter(List<CityPeopleBean.DataBean> data, Activity activity) {
        this.data=data;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_citypeople,null);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        CityPeopleBean.DataBean dataBean=data.get(i);
        String headerurl=dataBean.getHeadUrl()+"?x-oss-process=style/320_320";
        Glide.with(activity).load(headerurl).transform(new CommonUtil.GlideCircleTransform(activity)).into(viewHolder.tvItemCityPeopleHeader);
        viewHolder.tvItemCityPeopleNick.setText(dataBean.getNick());
        if (UserTask.getInstance()!=null&&UserTask.getInstance().getUser()!=null&&UserTask.getInstance().getUser().getUserId()
                ==dataBean.getUserId()){
            viewHolder.tvItemCityPeopleRemove.setVisibility(View.GONE);
        }else {
            viewHolder.tvItemCityPeopleRemove.setVisibility(View.VISIBLE);
        }
        viewHolder.tvItemCityPeopleRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    CommonDialog.getDialog(activity, "提示", "是否将" + dataBean.getNick() + "移除活动?", "取消"
                            , "确认", new OnButtonClick() {
                                @Override
                                public void button01ClickListener() {

                                }

                                @Override
                                public void button02ClickListener() {
                                    setRemove(dataBean.getMemberId());
                                }
                            });



            }
        });
    }

    private void setRemove(int memberId) {
       String userId= String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.REMOVE_PEOPLE).addParams("memberId",memberId+"")
                .addParams("userId",userId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    RemovePeopleBean peopleBean= (RemovePeopleBean) JsonUtil.fromJson(s,RemovePeopleBean.class);
                    if (peopleBean.getCode()==0){
                        ToastUtil.show(peopleBean.getMsg());
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.REMOVE_PEOPLE));
                    }else {

                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView tvItemCityPeopleHeader;
        private TextView tvItemCityPeopleNick,tvItemCityPeopleRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             tvItemCityPeopleHeader=itemView.findViewById(R.id.tvItemCityPeopleHeader);
             tvItemCityPeopleNick=itemView.findViewById(R.id.tvItemCityPeopleNick);
             tvItemCityPeopleRemove=itemView.findViewById(R.id.tvItemCityPeopleRemove);
        }
    }
}
