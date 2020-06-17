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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.JieBanActivityDetailActivity;
import com.spellingtrip.example.activity.LogineActivity;
import com.spellingtrip.example.activity.TourismUserShowActivity;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.CommentContentBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.RemovePeopleBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.MyLayoutManager;
import com.spellingtrip.example.view.ShapedImageView;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Activity activity;
    private List<CommentContentBean.DataBean> data;

    public CommentAdapter(Activity activity, List<CommentContentBean.DataBean> data) {
        this.activity = activity;
        this.data = data;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int postion) {
        if (data != null && data.size() > 0) {
            viewHolder.rlItemCommentNodata.setVisibility(View.GONE);
            final CommentContentBean.DataBean dataBean = data.get(postion);
            String userHeader = dataBean.getHeadUrl() + "?x-oss-process=style/128_128";
            Glide.with(activity).load(userHeader).into(viewHolder.sivItemCommentHeader);
            viewHolder.tvItemCommentContent.setText(dataBean.getContent());
            viewHolder.tvItemCommentNick.setText(dataBean.getNick());
            viewHolder.tvItemCommentTime.setText(dataBean.getCreateTime());
            if (dataBean.getVoteCount()!=0){
                viewHolder.tvItemCommentLiveNum.setText(dataBean.getVoteCount() + "");
            }else {
                viewHolder.tvItemCommentLiveNum.setText("");
            }
            if (dataBean.isVoteStatus()){
                viewHolder.ivItemCommentLive.setImageDrawable(activity.getResources().getDrawable(R.drawable.likered));
            }else {
                viewHolder.ivItemCommentLive.setImageDrawable(activity.getResources().getDrawable(R.drawable.likegray));
            }
            int userid = 0;
            if (UserTask.getInstance().getUser() != null) {
                userid = UserTask.getInstance().getUser().getUserId();
            }
            if (dataBean.getUserId() == userid) {
                viewHolder.tvItemCommentDetel.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvItemCommentDetel.setVisibility(View.GONE);
            }
            viewHolder.sivItemCommentHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.skipActivity(activity, TourismUserShowActivity.class, 0, String.valueOf(dataBean.getUserId()));
                }
            });
            viewHolder.tvItemCommentDetel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detel(dataBean.getId(), dataBean.getUserId(), postion);
                }
            });
            viewHolder.ivItemCommentLive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentLive(dataBean.getId());
                }
            });
        } else {
            viewHolder.rlItemCommentNodata.setVisibility(View.VISIBLE);
        }

    }

    private void commentLive(long id) {
        String userid=UserTask.getInstance().getUser().getUserId()+"";
        String tid= id+"";
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.COMMENTVOTE).addParams(ConstantsBean.USERID, userid)
                .addParams("id",tid).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Log.e("commentLiveEeor",JsonUtil.toJson(e));
            }

            @Override
            public void onResponse(String s, int i) {
               if (!TextUtils.isEmpty(s)){
                   ReMoveBean reMoveBean= (ReMoveBean) JsonUtil.fromJson(s,ReMoveBean.class);
                   if (reMoveBean.getCode()==0){
                     EventBus.getDefault().post(new SendMessageData(ConstantsBean.DELCOMMENT));
                   }else {
                       ToastUtil.show(reMoveBean.getMsg());
                   }
               }
            }
        });
    }

    private void detel(long id, int userId, int pos) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils.postString().url(ConstantsBean.BASE_PATH + ConstantsBean.COMMENTDEL).content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json; charset=utf-8")).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Log.e("onError", JsonUtil.toJson(e));
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {
                        EventBus.getDefault().post(new SendMessageData(ConstantsBean.DELCOMMENT));
                        ToastUtil.show(reMoveBean.getMsg());

                    } else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null || data.size() == 0 ? 1 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShapedImageView sivItemCommentHeader;
        private RelativeLayout rlItemCommentNodata;
        private ImageView ivItemCommentLive;
        private TextView tvItemCommentNick, tvItemCommentContent, tvItemCommentTime, tvItemCommentDetel, tvItemCommentLiveNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sivItemCommentHeader = itemView.findViewById(R.id.sivItemCommentHeader);
            tvItemCommentNick = itemView.findViewById(R.id.tvItemCommentNick);
            tvItemCommentContent = itemView.findViewById(R.id.tvItemCommentContent);
            tvItemCommentTime = itemView.findViewById(R.id.tvItemCommentTime);
            tvItemCommentDetel = itemView.findViewById(R.id.tvItemCommentDetel);
            rlItemCommentNodata = itemView.findViewById(R.id.rlItemCommentNodata);
            ivItemCommentLive = itemView.findViewById(R.id.ivItemCommentLive);
            tvItemCommentLiveNum = itemView.findViewById(R.id.tvItemCommentLiveNum);
        }
    }

}
