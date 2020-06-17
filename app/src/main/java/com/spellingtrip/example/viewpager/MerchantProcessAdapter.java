package com.spellingtrip.example.viewpager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.BaiduMapActivity;
import com.spellingtrip.example.activity.JieBanActivityDetailActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.Proces;
import com.spellingtrip.example.bean.TwoMessageEvent;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * date:2020/5/7
 * author:王思敏
 * function同城活动发布活动流程
 */
public class MerchantProcessAdapter extends RecyclerView.Adapter<MerchantProcessAdapter.ViewHolder> {

    private Activity mActivity;
    private List<Proces.ProcesBean> dataList;
    TimePickerView pvTime;
    private String mMessage;
    private String mContent;
    private EditText mProcess_site;


    public MerchantProcessAdapter(Activity activity,List<Proces.ProcesBean> list) {
        EventBus.getDefault().register(this);
        this.mActivity = activity;
        this.dataList = list;
    }
    public void setData() {
        if (dataList.size()>=10){
            ToastUtil.show("最多可添加十天");
            return;
        }
        //添加天数
        Proces.ProcesBean data = new Proces.ProcesBean();
        data.setSite("");
        data.setDescribe("");
        data.setTime("");
        data.setDay(dataList.size()+1);
        dataList.add(data);
        notifyDataSetChanged();
        notifyItemRemoved( dataList.size() );
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.process_item, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.v("merchantprocessadapter","dataList.size()=="+dataList.size());
        viewHolder.mProcessday.setText("第"+dataList.get(i).getDay()+"天");
        if( viewHolder.mProcess_time.getTag()!= null && viewHolder.mProcess_time.getTag() instanceof TextWatcher){
            viewHolder.mProcess_time.removeTextChangedListener(((TextWatcher) viewHolder.mProcess_time.getTag()));
        }
        if( viewHolder.mProcess_describe.getTag() != null && viewHolder.mProcess_describe.getTag() instanceof TextWatcher){
            viewHolder.mProcess_describe.removeTextChangedListener(((TextWatcher) viewHolder.mProcess_describe.getTag()));
        }
        if( mProcess_site.getTag() != null && mProcess_site.getTag() instanceof TextWatcher){
             mProcess_site.removeTextChangedListener(((TextWatcher)mProcess_site.getTag()));
        }

        //输入框不可输入
        viewHolder.mProcess_time.setFocusable(false);
        mProcess_site.setFocusable(false);
        //选择时间
        viewHolder.mProcess_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击组件的点击事件
                pvTime.show(viewHolder.mProcess_time);
            }
        });
        //选择地点
        mProcess_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedPress();
            }
        });


//        viewHolder.mProcess_time.setText(dataList.get(i).getTime());
        Log.v("dataList","-----------dataList.get(i).getSite()=="+dataList.get(i).getSite());

        viewHolder.mProcess_describe.setText( dataList.get(i).getDescribe() );

        viewHolder.mProcess_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.size()<=1){
                    return;
                }
                dataList.remove( i );
                notifyDataSetChanged();
                notifyItemRemoved( i );

            }
        });

        TextWatcher SiteWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    dataList.get(i).setSite(s.toString());
                    Log.v("111111111111111111","----setSite=="+s.toString());

            }
        };

        mProcess_site.addTextChangedListener( SiteWatcher );
        mProcess_site.setTag( SiteWatcher );

        TextWatcher DescribeWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.v("111111111111111111","----setDescribe=="+s.toString());
                dataList.get(i).setDescribe(s.toString());

            }
        };
        viewHolder.mProcess_describe.addTextChangedListener( DescribeWatcher );
        viewHolder.mProcess_describe.setTag( DescribeWatcher );

        TextWatcher TimeWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.v("111111111111111111","----setTime=="+s.toString());
                dataList.get(i).setTime(s.toString());
            }
        };
        viewHolder.mProcess_time.addTextChangedListener( TimeWatcher );
        viewHolder.mProcess_time.setTag( TimeWatcher );



        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2029, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(mActivity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                viewHolder.mProcess_time.setText(getTimes(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(18)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();





    }
    private String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 判断请求权限
     */
    private void setRequestedPress() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (Build.VERSION.SDK_INT >= 23) {
            int check = ContextCompat.checkSelfPermission(mActivity, permissions[0]);
            if (check == PackageManager.PERMISSION_GRANTED) {
                //写入你需要权限才能使用的方法
                Intent intent = new Intent(mActivity, BaiduMapActivity.class);
                mActivity.startActivity(intent);
            } else {
                //手动去请求用户打开权限(可以在数组中添加多个权限) 1 为请求码 一般设置为final静态变量
                mActivity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
            }
        } else {
            //写入你需要权限才能使用的方法
            Intent intent = new Intent(mActivity, BaiduMapActivity.class);
            mActivity.startActivity(intent);
        }

    }





    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mProcess_delete;
        private final LinearLayout layout;
        private final TextView mProcessday;
        private final EditText mProcess_time,mProcess_describe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mProcessday = itemView.findViewById(R.id.process_day);
            mProcess_delete = itemView.findViewById(R.id.process_delete);
            mProcess_site = itemView.findViewById(R.id.process_site);
            mProcess_time = itemView.findViewById(R.id.process_time);
            mProcess_describe = itemView.findViewById(R.id.process_describe);
            layout = itemView.findViewById(R.id.layout);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TwoMessageEvent data) {
        Log.v("onMessageEvent","--------data.getMessage()=="+data.getMessage()+"-----data.getContent()=="+data.getContent());
        if (!TextUtils.isEmpty(data.getMessage()) && !TextUtils.isEmpty(data.getContent())){

            mMessage = data.getMessage();
            mContent = data.getContent();
            mProcess_site.setText(mContent+"."+mMessage);

            Log.v("onMessageEvent","--------mMessage=="+mMessage+"-----mContent=="+mContent);


        }
    }



}
