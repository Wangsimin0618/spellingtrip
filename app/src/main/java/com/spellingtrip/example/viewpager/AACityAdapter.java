package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.RemovePeopleBean;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.MyLayoutManager;
import com.spellingtrip.example.view.ShapedImageView;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.Call;

public class AACityAdapter extends RecyclerView.Adapter<AACityAdapter.ViewHolder> {
    private Activity activity;
    private List<ActivityAABean.DataBean> citys;
    private String type;

    public AACityAdapter(Activity activity, List<ActivityAABean.DataBean> citys,String type) {
        this.activity = activity;
        this.citys = citys;
        this.type=type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_aacity, viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int postion) {
        if (citys!=null&&citys.size()>0){
            ActivityAABean.DataBean dataBean = citys.get(postion);
            if (dataBean.getFidList() != null && dataBean.getFidList().size() > 0) {
                String imgurl = dataBean.getFidList().get(0)+"?x-oss-process=style/640_320";
                Glide.with(activity).load(imgurl).into(viewHolder.ivActivityImg);
            }
            String headUrl = dataBean.getHeadUrl();
            Glide.with(activity).load(headUrl).into(viewHolder.ivItemAAUserHeader);
            if (dataBean.getSex()!=null){
                if (dataBean.getSex().equals(ConstantsBean.BOY)) {
                    viewHolder.tvItemAASex.setText("♂");
                    viewHolder.ivItemAASex.setBackground(activity.getResources().getDrawable(R.drawable.argsexjiebanboy_shape));
                } else {
                    viewHolder.tvItemAASex.setText("♀");
                    viewHolder.ivItemAASex.setBackground(activity.getResources().getDrawable(R.drawable.argsexjieban_shape));
                }
            }
            final List<String> labeLists = dataBean.getLabelList();
            MyLayoutManager layout = new MyLayoutManager();
            layout.setAutoMeasureEnabled(true);
            if (viewHolder.tvActivityType.getItemDecorationCount() == 0) {
                viewHolder.tvActivityType.addItemDecoration(new SpacesItemDecoration(14));
            }
            viewHolder.tvActivityType.setLayoutManager(layout);
            viewHolder.tvActivityType.setHasFixedSize(true);
            viewHolder.tvActivityType.setNestedScrollingEnabled(false);
            final SearchHistoryAdapter adapter = new SearchHistoryAdapter(activity, labeLists);
            viewHolder.tvActivityType.setAdapter(adapter);
            if (!TextUtils.isEmpty(dataBean.getAge()+"")){
                viewHolder.tvItemAAArg.setVisibility(View.VISIBLE);
                viewHolder.tvItemAAArg.setText(dataBean.getAge()+"");
            }else {
                viewHolder.tvItemAAArg.setVisibility(View.GONE);
            }
            if (dataBean.isIdentityAuth()){
                viewHolder.tvItemAAUserShiMing.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tvItemAAUserShiMing.setVisibility(View.GONE);
            }
            if (dataBean.isActive()){
                viewHolder.tvItemAAUserActivity.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tvItemAAUserActivity.setVisibility(View.GONE);
            }
            if (dataBean.isVip()){
                viewHolder.tvItemAAUserNick.setTextColor(activity.getResources().getColor(R.color.image_selector_red));
                viewHolder.tvItemAAUserVIP.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tvItemAAUserNick.setTextColor(activity.getResources().getColor(R.color.history_text));
                viewHolder.tvItemAAUserVIP.setVisibility(View.GONE);
            }
            viewHolder.tvItemAAUserNick.setText(dataBean.getNick());
            viewHolder.tvActivityWeiZhi.setText("目的地：" + dataBean.getToArea() + "");
            viewHolder.tvActivityTime.setText("起止时间：" + dataBean.getStartDate() + "-" + dataBean.getEndDate());
            viewHolder.tvActivityContent.setText(dataBean.getDescription() + "");
            viewHolder.llItemAA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UserTask.getInstance().isLogin()) {
                        ActivityUtils.skipActivity(activity, JieBanActivityDetailActivity.class, 0, dataBean.getId() + "");
                    } else {
                        ActivityUtils.skipActivity(activity, LogineActivity.class, 0, "");
                    }
                }
            });
            if (type.equals("home")){
                viewHolder.ivItemAAcityDetel.setVisibility(View.GONE);
            }else {
                viewHolder.ivItemAAcityDetel.setVisibility(View.VISIBLE);
                viewHolder.ivItemAAcityDetel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (UserTask.getInstance()!=null){
                            int userid=UserTask.getInstance().getUser().getUserId();
                            setDetel(dataBean.getId(),userid);
                        }
                    }
                });
            }
        }

    }

    private void setDetel(String id, int userId) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.DETAILJIEBAN).addParams(ConstantsBean.tourId, String.valueOf(id))
                .addParams(ConstantsBean.USERID, String.valueOf(userId)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
            }

            @Override
            public void onResponse(String s, int i) {
             if (!TextUtils.isEmpty(s)){
                 RemovePeopleBean removePeopleBean= (RemovePeopleBean) JsonUtil.fromJson(s, RemovePeopleBean.class);
                 if (removePeopleBean.getCode()==0){
                     EventBus.getDefault().post(new EventType(ConstantsBean.AUTOPI));
                     ToastUtil.show(removePeopleBean.getMsg());
                 }else {
                     ToastUtil.show(removePeopleBean.getMsg());
                 }
             }
            }
        });
    }

    @Override
    public int getItemCount() {
        return citys==null||citys.size()==0?1:citys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivActivityImg,ivItemAAcityDetel,tvItemAAUserShiMing,tvItemAAUserActivity,tvItemAAUserVIP;
        private ShapedImageView ivItemAAUserHeader;
        private TextView tvActivityWeiZhi, tvActivityTime, tvActivityContent, tvItemAAUserNick,tvItemAAArg,tvItemAASex;
        private RecyclerView tvActivityType;
        private LinearLayout llItemAA;
        private RelativeLayout ivItemAASex;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llItemAA=itemView.findViewById(R.id.llItemAA);
            ivItemAAcityDetel=itemView.findViewById(R.id.ivItemAAcityDetel);
            ivItemAAUserHeader = itemView.findViewById(R.id.ivItemAAUserHeader);
            ivActivityImg = itemView.findViewById(R.id.ivActivityImg);
            tvActivityType = itemView.findViewById(R.id.tvActivityType);
            tvActivityWeiZhi = itemView.findViewById(R.id.tvActivityWeiZhi);
            tvActivityTime = itemView.findViewById(R.id.tvActivityTime);
            tvActivityContent = itemView.findViewById(R.id.tvActivityContent);
            tvItemAAUserNick = itemView.findViewById(R.id.tvItemAAUserNick);
            ivItemAASex = itemView.findViewById(R.id.ivItemAASex);
            tvItemAAArg=itemView.findViewById(R.id.tvItemAAArg);
            tvItemAASex=itemView.findViewById(R.id.tvItemAASex);
            tvItemAAUserVIP=itemView.findViewById(R.id.tvItemAAUserVIP);
            tvItemAAUserShiMing=itemView.findViewById(R.id.tvItemAAUserShiMing);
            tvItemAAUserActivity=itemView.findViewById(R.id.tvItemAAUserActivity);

        }
    }

}
