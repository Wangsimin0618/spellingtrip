package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
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
import com.spellingtrip.example.activity.TourismUserShowActivity;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.RemovePeopleBean;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.MyLayoutManager;
import com.spellingtrip.example.view.ScrollGridView;
import com.spellingtrip.example.view.ShapedImageView;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.Call;

public class CardCityAdapter extends RecyclerView.Adapter<CardCityAdapter.ViewHolder> {
    private Activity activity;
    private List<ActivityAABean.DataBean> citys;
    private String type;
    protected boolean isScrolling = false;

    public CardCityAdapter(Activity activity, List<ActivityAABean.DataBean> citys,String type) {
        this.activity = activity;
        this.citys = citys;
        this.type=type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_item, viewGroup,false);
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
            viewHolder.homeitem.setVisibility(View.VISIBLE);
            ActivityAABean.DataBean dataBean = citys.get(postion);
//            if (dataBean.getFidList() != null && dataBean.getFidList().size() > 0) {
////                String imgurl = dataBean.getFidList().get(postion)+"?x-oss-process=style/640_320";
////                Glide.with(activity).load(imgurl).into(viewHolder.ivActivityImg);
////            }
            final List<String> labeLists = dataBean.getLabelList();
            MyLayoutManager layout = new MyLayoutManager();
            layout.setAutoMeasureEnabled(true);
            if (viewHolder.homeitemlabel.getItemDecorationCount() == 0) {
                viewHolder.homeitemlabel.addItemDecoration(new SpacesItemDecoration(14));
            }
            viewHolder.homeitemlabel.setLayoutManager(layout);
            viewHolder.homeitemlabel.setHasFixedSize(true);
            viewHolder.homeitemlabel.setNestedScrollingEnabled(false);
            final LabelAdapter adapter = new LabelAdapter(activity, labeLists);
            viewHolder.homeitemlabel.setAdapter(adapter);
            viewHolder.tvstartWeiZhi.setText(dataBean.getToArea() + "");
            viewHolder.tvendWeiZhi.setText(dataBean.getFromArea() + "");
            viewHolder.homeitemlocation.setText(dataBean.getToArea() + "");
            viewHolder.tvActivityTime.setText("时间：" + dataBean.getStartDate() + "-" + dataBean.getEndDate());
            viewHolder.tvActivityContent.setText(dataBean.getDescription() + "");
            viewHolder.homeitemlike.setText(dataBean.getLickCount()+"");
            viewHolder.homeitemcomment.setText(dataBean.getCommentCount()+"");


            final List<String> fidList = dataBean.getFidList();
            int colNum;//列数
            if (fidList.size() == 1){
                colNum = 1;
            }else if (fidList.size() == 2){
                colNum = 2;
            }else {
                colNum = 3;
            }
            //设置图片
            if (dataBean.getFidList() != null &&dataBean.getFidList().size()>0) {
                viewHolder.tourismImgs.setVisibility(View.VISIBLE);
                //定义布局管理器为Grid管理器，设置一行放3个
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, colNum);
                //设置布局管理器为线性布局管理器
                viewHolder.tourismImgs.setLayoutManager(layoutManager);
                //设置适配器
                viewHolder.tourismImgs.setAdapter(new HomeImageAdapter(activity,fidList));

            }else {
                viewHolder.tourismImgs.setVisibility(View.GONE);
            }
        }else {
            viewHolder.homeitem.setVisibility(View.GONE);
        }

    }

    private void setDetel(long id, int userId) {
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
        private ImageView ivActivityImg;
        private TextView tvstartWeiZhi,tvendWeiZhi, tvActivityTime, tvActivityContent,homeitemlike,homeitemcomment,homeitemlocation;
        private RecyclerView homeitemlabel,tourismImgs;
        private LinearLayout homeitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeitem = itemView.findViewById(R.id.home_item);
            homeitemlabel = itemView.findViewById(R.id.home_item_label);
            tvstartWeiZhi = itemView.findViewById(R.id.home_item_start);
            tvendWeiZhi = itemView.findViewById(R.id.home_item_end);
            tvActivityTime = itemView.findViewById(R.id.home_item_time);
            tvActivityContent = itemView.findViewById(R.id.home_item_content);
            homeitemlocation = itemView.findViewById(R.id.home_item_location);
            homeitemlike = itemView.findViewById(R.id._home_item_like);
            homeitemcomment = itemView.findViewById(R.id.home_item_comment);
            tourismImgs = itemView.findViewById(R.id.home_item_tourismImg);


        }
    }

}

