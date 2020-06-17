package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;

import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;


import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.LogineActivity;

import com.spellingtrip.example.activity.TourismUserShowActivity;
import com.spellingtrip.example.activity.UserInfoDetailShowActivity;
import com.spellingtrip.example.bean.CollectBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.DianZan;
import com.spellingtrip.example.bean.TourisnBean;
import com.spellingtrip.example.dialog.CommonDialog;

import com.spellingtrip.example.fragment.TourismFragment;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;

import com.spellingtrip.example.utils.ToastUtil;

import com.spellingtrip.example.view.ScrollGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import okhttp3.Call;

public class TourismListAdapter extends RecyclerView.Adapter<TourismListAdapter.ViewHolder> {
    private Activity activity;
    private String TAG = "TourismListAdapter";
    private Bitmap bitmap;
    private List<TourisnBean.DataBean> lists;

    public TourismListAdapter(Activity activity) {
        this.activity = activity;
    }
    protected boolean isScrolling = false;
    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tourism, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        TourisnBean.DataBean dataBean = lists.get(position);
        //设置头像
        String  imgurl=dataBean.getHeadUrl()+"?x-oss-process=style/128_128";
        Glide.with(activity).load(imgurl)
                .transform(new CommonUtil.GlideCircleTransform(activity)).into(holder.tourismHeader);
        holder.tvUserTitle.setText(dataBean.getNick() + "");
        holder.tvUserSendTime.setText(dataBean.getCreateTime() + "");
        if (dataBean.getPosition().contains("选择位置")||TextUtils.isEmpty(dataBean.getPosition())){
            holder.tvBiaoJi.setText( "未标记");
        }else {
            holder.tvBiaoJi.setText(dataBean.getPosition() + "");
        }
        holder.tvXinNum.setText(dataBean.getLikeCount() + "");
        holder.tvXingXingNum.setText(dataBean.getFavoriteCount() + "");
        holder.tvSendContent.setText(dataBean.getContent() + "");
        if (dataBean.isLiked()) {
            holder.cbXin.setButtonDrawable(R.mipmap.xinsel);
        } else {
            holder.cbXin.setButtonDrawable(R.mipmap.xinnor);
        }
        if (dataBean.isFavorited()) {
            holder.cbXingXing.setButtonDrawable(R.mipmap.xingxingsel);
        } else {
            holder.cbXingXing.setButtonDrawable(R.mipmap.xingxingnor);
        }
        if (dataBean.getVideo() != null && !TextUtils.isEmpty(dataBean.getVideo().getUrl())) {
            holder.videoCardView.setVisibility(View.VISIBLE);
            String imageurl=dataBean.getVideo().getUrl()+"?x-oss-process=video/snapshot,t_0,f_jpg,w_800,h_600,m_fast";
            holder.jzvdStd.setUp(lists.get(position).getVideo().getUrl()
                    , imageurl, "",null);
            holder.jzvdStd.setYinCang(false);
        } else {
            holder.videoCardView.setVisibility(View.GONE);
        }
        //设置图片
        if (dataBean.getImages() != null&&dataBean.getImages().size()>0) {
            holder.tourismImg.setVisibility(View.VISIBLE);
            if (!isScrolling){
                holder.tourismImg.setAdapter(new TourismImageAdapter(activity, dataBean.getImages(),false));
            }else{
                holder.tourismImg.setAdapter(new TourismImageAdapter(activity, dataBean.getImages(),true));

            }

        }else {
            holder.tourismImg.setVisibility(View.GONE);
        }
        holder.ivDetelContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog.openPopupWindow(activity, v, dataBean.getUserId());
            }
        });
        holder.tourismHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.skipActivity(activity, TourismUserShowActivity.class, 0, String.valueOf(dataBean.getUserId()));
            }
        });
        final ViewHolder finalHolder = holder;
        holder.cbXingXing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserTask.getInstance().isLogin()) {
                    setCollect(dataBean.getId(), position, finalHolder);
                } else {
                    ActivityUtils.skipActivity(activity, LogineActivity.class, 0, "");
                }
            }
        });
        //点赞
        final ViewHolder finalHolder1 = holder;
        holder.cbXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserTask.getInstance().isLogin()) {
                    setDianZan(dataBean.getId(), position, finalHolder1);
                } else {
                    ActivityUtils.skipActivity(activity, LogineActivity.class, 0, "");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    /**
     * 收藏
     *
     * @param
     * @param id
     * @param
     */
    private void setCollect(int id, final int postion, ViewHolder viewHolder) {
        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.COLLECT).addParams("userId", userid)
                .addParams("travelId", String.valueOf(id)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    CollectBean collectBean = (CollectBean) JsonUtil.fromJson(s, CollectBean.class);
                    if (collectBean.getCode() == 0) {
                        ToastUtil.show(collectBean.getData().getMsg());
                        String num = viewHolder.tvXingXingNum.getText().toString();
                        if (collectBean.getData().isStatus()) {
                            lists.get(postion).setFavorited(true);
                            lists.get(postion).setFavoriteCount(Integer.valueOf(num) + 1);
                            viewHolder.cbXingXing.setButtonDrawable(R.mipmap.xingxingsel);
                            viewHolder.tvXingXingNum.setText(Integer.valueOf(num) + 1 + "");
                        } else {
                            lists.get(postion).setFavorited(false);
                            lists.get(postion).setFavoriteCount(Integer.valueOf(num));
                            viewHolder.cbXingXing.setButtonDrawable(R.mipmap.xingxingnor);
                            viewHolder.tvXingXingNum.setText(Integer.valueOf(num) - 1 + "");
                        }
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.getMineCollect));
                    } else {
                        ToastUtil.show(collectBean.getMsg());
                    }
                }
            }
        });
    }

    /**
     * 点赞
     *
     * @param
     * @param id
     * @param
     */
    private void setDianZan(int id, final int postion, ViewHolder viewHolder) {
        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.DIANZAN).addParams("userId", userid)
                .addParams("travelId", String.valueOf(id)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    DianZan dianZan = (DianZan) JsonUtil.fromJson(s, DianZan.class);
                    if (dianZan.getCode() == 0) {
                        String num = viewHolder.tvXinNum.getText().toString();
                        if (dianZan.getData().isLiked()) {
                            lists.get(postion).setLiked(true);
                            lists.get(postion).setLikeCount(Integer.valueOf(num) + 1);
                            viewHolder.cbXin.setButtonDrawable(R.mipmap.xinsel);
                            viewHolder.tvXinNum.setText(Integer.valueOf(num) + 1 + "");
                        } else {
                            lists.get(postion).setLiked(false);
                            lists.get(postion).setLikeCount(Integer.valueOf(num) - 1);
                            viewHolder.cbXin.setButtonDrawable(R.mipmap.xinnor);
                            viewHolder.tvXinNum.setText(Integer.valueOf(num) - 1 + "");
                        }

                    } else {
                        ToastUtil.show(dianZan.getMsg());
                    }
                }
            }
        });
    }

    public void setData(List<TourisnBean.DataBean> travel) {
        this.lists=travel;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tourismHeader, ivDetelContent, ivPlay;
        TextView tvUserTitle, tvUserSendTime, tvBiaoJi, tvXinNum, tvXingXingNum, tvSendContent;
        CheckBox cbXin, cbXingXing;
        ScrollGridView tourismImg;
        JCVideoPlayer jzvdStd;
        CardView videoCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tourismHeader = itemView.findViewById(R.id.tourismHeader);
            tvUserTitle = itemView.findViewById(R.id.tvUserTitle);
            tvUserSendTime = itemView.findViewById(R.id.tvUserSendTime);
            tvSendContent = itemView.findViewById(R.id.tvSendContent);
            tvBiaoJi = itemView.findViewById(R.id.tvBiaoJi);
            tvXinNum = itemView.findViewById(R.id.tvXinNum);
            tvXingXingNum = itemView.findViewById(R.id.tvXingXingNum);
            cbXin = itemView.findViewById(R.id.cbXin);
            cbXingXing = itemView.findViewById(R.id.cbXingXing);
            tourismImg = itemView.findViewById(R.id.tourismImg);
            ivDetelContent = itemView.findViewById(R.id.ivDetelContent);
            jzvdStd = itemView.findViewById(R.id.item_videoview);
            videoCardView=itemView.findViewById(R.id.videoCardView);
        }

    }

}

