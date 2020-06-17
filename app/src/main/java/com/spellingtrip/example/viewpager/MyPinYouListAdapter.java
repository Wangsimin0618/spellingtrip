package com.spellingtrip.example.viewpager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;

import com.spellingtrip.example.activity.DetailListActivity;
import com.spellingtrip.example.activity.PinYouInfoActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.MyPinYouBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ScrollGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.Call;

public class MyPinYouListAdapter extends RecyclerView.Adapter<MyPinYouListAdapter.ViewHolder> {
    private List<MyPinYouBean.DataBean.RowsBean> rows;
    private Activity activity;
    public int selectIndex;
    private int total;
    private LogineDialog logineDialog;

    public MyPinYouListAdapter(List<MyPinYouBean.DataBean.RowsBean> rows, Activity activity, int total) {
        this.activity = activity;
        this.rows = rows;
        this.total = total;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mypinyoulist, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.tvItemPYLocation.setText(rows.get(position).getFromArea() + " - " + rows.get(position).getToArea());
        viewHolder.tvItemPyTime.setText(rows.get(position).getStartDate() + " - " + rows.get(position).getEndDate());
        MyPinYouBean.DataBean.RowsBean rowsBean=  rows.get(position);
        int status = rows.get(position).getStatus();
        if (status == 0) {
            setStatus(false, ConstantsBean.STATUS0, total, viewHolder);
        } else if (status == 1) {
            setStatus(false, ConstantsBean.STATUS1, total, viewHolder);
        } else if (status == 2) {
            setStatus(false, ConstantsBean.STATUS2, total, viewHolder);
        } else if (status == 3) {
            setStatus(true, ConstantsBean.STATUS3, total, viewHolder);
        } else if (status == 4) {
            setStatus(false, ConstantsBean.STATUS4, total, viewHolder);
        } else if (status == 5) {
            setStatus(true, ConstantsBean.STATUS5, total, viewHolder);
        }
        //删除
        viewHolder.ivItemDetelPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(rowsBean.getPinId());
            }
        });
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.slvItemPYPeople.getLayoutParams();
        if (rowsBean.getUserIcons() != null &&rowsBean.getUserIcons().size() > 0) {
            viewHolder.slvItemPYPeople.setVisibility(View.VISIBLE);
            viewHolder.ivUserHeaderMyPin.setVisibility(View.GONE);
            layoutParams.width = CommonUtil.dipToPx(activity, 40) * rowsBean.getUserIcons().size();
            viewHolder.slvItemPYPeople.setLayoutParams(layoutParams);
            viewHolder.slvItemPYPeople.setNumColumns(rowsBean.getUserIcons().size());
            PeopleHeaderAdaper peopleHeaderAdaper = new PeopleHeaderAdaper(rowsBean.getUserIcons(), activity);
            viewHolder.slvItemPYPeople.setAdapter(peopleHeaderAdaper);
            viewHolder.tvItemPYNumber.setText(rows.get(position).getUserIcons().size() + "/" + (rows.get(position).getMatchCount()));
        } else {
            viewHolder.slvItemPYPeople.setVisibility(View.GONE);
            viewHolder.ivUserHeaderMyPin.setVisibility(View.VISIBLE);
            Glide.with(activity).load(rowsBean.getHeadUrl()).transform(new CommonUtil.GlideCircleTransform(activity))
                    .into(viewHolder.ivUserHeaderMyPin);
            viewHolder.tvItemPYNumber.setText("");
        }
        viewHolder.rlItemPinList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity, DetailListActivity.class,rowsBean.getPinId(), "");
            }
        });
        viewHolder.tvItemPYLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity, PinYouInfoActivity.class, rows.get(position).getPinId(), "");
            }
        });
        viewHolder.tvItemPyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(activity, PinYouInfoActivity.class, rows.get(position).getPinId(), "");
            }
        });
    }


    @Override
    public int getItemCount() {
        return rows.size();
    }

    private void setDialog(final int pinId) {
        CommonDialog.getDialog(activity, "提示", "是否删除匹配信息?", "取消", "删除", new OnButtonClick() {
            @Override
            public void button01ClickListener() {

            }

            @Override
            public void button02ClickListener() {
                if (logineDialog == null) {
                    logineDialog = new LogineDialog(activity, "正在删除");
                }
                logineDialog.show();
                setDelete(pinId);
            }
        });
    }

    private void setDelete(int pinId) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.DETELPIN).addParams("pinId", String.valueOf(pinId))
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId())).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {
                        ToastUtil.show("删除成功");
                        EventBus.getDefault().post(new EventType(ConstantsBean.AUTOPI));
                    } else {
                        ToastUtil.show(reMoveBean.getMsg());

                    }
                }
            }
        });
    }

    private void setStatus(boolean b, String status, int total, ViewHolder viewHolder) {
        viewHolder.cbItemPYStatus.setChecked(b);
        viewHolder.tvItemPYStatus.setText(status);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemPYLocation, tvItemPyTime, tvItemPYStatus, tvItemPYNumber, tvItemIsOpen;
        CheckBox cbItemPYStatus;
        ScrollGridView slvItemPYPeople;
        ImageView ivItemDetelPin, ivUserHeaderMyPin;
        RelativeLayout llItemTop;
        RelativeLayout rlItemPinList;

        public ViewHolder(View itemView) {
            super(itemView);
            slvItemPYPeople = itemView.findViewById(R.id.slvItemPYPeople);
            rlItemPinList=itemView.findViewById(R.id.rlItemPinList);
            tvItemPYLocation = itemView.findViewById(R.id.tvItemPYLocation);
            tvItemPyTime = itemView.findViewById(R.id.tvItemPyTime);
            tvItemPYStatus = itemView.findViewById(R.id.tvItemPYStatus);
            tvItemPYNumber = itemView.findViewById(R.id.tvItemPYNumber);
            tvItemIsOpen = itemView.findViewById(R.id.tvItemIsOpen);
            cbItemPYStatus = itemView.findViewById(R.id.cbItemPYStatus);
            ivItemDetelPin = itemView.findViewById(R.id.ivItemDetelPin);
            llItemTop = itemView.findViewById(R.id.llItemTop);
            ivUserHeaderMyPin = itemView.findViewById(R.id.ivUserHeaderMyPin);
        }
    }


}
