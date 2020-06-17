package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.SetLabelActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.LabelBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.LabelUtils;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ChooseLabelAdapter extends BaseAdapter{
    private List<UserInfoDataBean.LabelsBean> labels;
    private Activity activity;
    public ChooseLabelAdapter(List<UserInfoDataBean.LabelsBean> labels, Activity activity) {
        this.labels=labels;
        this.activity=activity;
    }


    @Override
    public int getCount() {
        return labels.size();
    }

    @Override
    public Object getItem(int position) {
        return labels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       convertView= LayoutInflater.from(activity).inflate(R.layout.item_chooselabel,null);
       TextView tvChooseLabelTitle=convertView.findViewById(R.id.tvChooseLabelTitle);
       ImageView ivDetelLabel=convertView.findViewById(R.id.ivDetelLabel);
       tvChooseLabelTitle.setText(labels.get(position).getLabelName());
       ivDetelLabel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               LabelUtils.remove(labels.get(position).getLabelId());
               EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.IsLABEL));
               notifyDataSetChanged();
               //detelLabel(String.valueOf(labels.get(position).getLabelId()));
           }
       });
        return convertView;
    }

    private void detelLabel(final String labelId) {
        List<String>ids=new ArrayList<>();
        ids.add(labelId);
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.DETELLABEL).addParams("labelIds", JsonUtil.toJson(ids))
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId())).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                   ReMoveBean reMoveBean= (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                   if (reMoveBean.getCode()==0){
                       LabelUtils.remove(Integer.parseInt(labelId));
                       EventBus.getDefault().post(new EventType(Constant.UrlOrigin.IsLABEL));
                       notifyDataSetChanged();
                   }else {
                       ToastUtil.show(reMoveBean.getMsg());
                   }
                }
            }
        });
    }
}
