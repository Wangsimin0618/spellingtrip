package com.spellingtrip.example.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.city.CityPickerActivity;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.ivUserinfoHeader)
    public ImageView ivUserinfoHeader;
    @BindView(R.id.tvUserInfoNick)
    public TextView tvUserInfoNick;
    @BindView(R.id.rlUserInfoSex)
    public RelativeLayout rlUserInfoSex;
    @BindView(R.id.rlUserInfoNick)
    public RelativeLayout rlUserInfoNick;
    @BindView(R.id.rlUserInfoDirthDay)
    public RelativeLayout rlUserInfoDirthDay;
    @BindView(R.id.rlUserInfoCity)
    public RelativeLayout rlUserInfoCity;
    @BindView(R.id.rlUserInfoQianName)
    public RelativeLayout rlUserInfoQianName;
    @BindView(R.id.tvUserInfoSex)
    public TextView tvUserInfoSex;
    @BindView(R.id.tvUserInfoDirthDay)
    public TextView tvUserInfoDirthDay;
    @BindView(R.id.tvUserInfoCity)
    public TextView tvUserInfoCity;
    @BindView(R.id.tvUserInfoQianName)
    public TextView tvUserInfoQianName;
    private int CITY_CODE=202;
    private String[] sexArry=new String[]{"男","女"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void getData() {

    }

    @OnClick({R.id.ivUserinfoHeader,R.id.rlUserInfoNick,R.id.rlUserInfoSex,R.id.rlUserInfoCity,R.id.rlUserInfoDirthDay,R.id.rlUserInfoQianName})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ivUserinfoHeader:
                if (!TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getHeadUrl())){
                    ActivityUtils.skipActivity(this,UserHeaderActivity.class,0,UserTask.getInstance().getUserInfoData().getHeadUrl());
                }
                break;
            case R.id.rlUserInfoNick:
                ActivityUtils.skipActivity(this,EditInfoActivity.class,0,"设置昵称");
                break;
            case R.id.rlUserInfoSex:
                setSex();
                break;
            case R.id.rlUserInfoCity:
                startActivityForResult(new Intent(this, CityPickerActivity.class), CITY_CODE);
                break;
            case R.id.rlUserInfoDirthDay:
                setDirthDay();
                break;
            case R.id.rlUserInfoQianName:
                ActivityUtils.skipActivity(this,EditInfoActivity.class,0,"设置签名");
                break;
        }
    }

    private void setSex() {
        String sex=tvUserInfoSex.getText().toString().trim();
        int sexnum=0;
        if (sex.equals("男")){
            sexnum=0;
        }else if (sex.equals("女")){
            sexnum=1;
        }else {
            sexnum=0;
        }
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, sexnum, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                setUserInfo("sex",sexArry[which]);
                dialog.dismiss();
            }
        });
        builder3.show();

    }

    /**
     * 设置生日
     */
    private void setDirthDay() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(1970, 0, 1);
        Calendar endDate = Calendar.getInstance();
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(final Date date, View v) {
                tvUserInfoDirthDay.setText(CommonUtil.getTime(date));
                setUserInfo("birthday",CommonUtil.getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "").isCenterLabel(false)
                .setRangDate(startDate, endDate).setBackgroundId(0x50333333)
                .setSubmitColor(R.color.textSelColor)//确定按钮文字颜色
                .setCancelColor(R.color.logineNor_text)//取消按钮文字颜色
                .setDecorView(null).build();
        pvTime.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CITY_CODE && resultCode == RESULT_OK) {
            tvUserInfoCity.setText(data.getStringExtra("city") + "");
            setUserInfo("city",data.getStringExtra("city"));
        }
    }

    private void setUserInfo(String type,String content) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPDATAINFO + UserTask.getInstance().getUser().getUserId())
                .addParams(type, content).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {
                        ToastUtil.show(reMoveBean.getMsg());
                        tvUserInfoSex.setText(content);
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.USERINFO));
                    } else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.IsLogine)){
            HttpRequest.getUserInfo();
        }
        if (data.getType().equals(Constant.UrlOrigin.GETUSER)){
            setInfo();
        }
    }

    private void setInfo() {
        if (UserTask.getInstance().getUserInfoData()!=null){
            if (UserTask.getInstance().getUserInfoData().getHeadUrl()!=null
                    &&!TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getHeadUrl())){
                String  imgurl=UserTask.getInstance().getUserInfoData().getHeadUrl()+"?x-oss-process=style/320_320";
                Glide.with(this).load(imgurl).transform(new CommonUtil.GlideCircleTransform(this))
                        .into(ivUserinfoHeader);
            }
            tvUserInfoNick.setText(UserTask.getInstance().getUserInfoData().getNick());
            if (!TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getDescription())){
                tvUserInfoQianName.setText(UserTask.getInstance().getUserInfoData().getDescription()+"");
            }else {
                tvUserInfoQianName.setText("您还未设置个性签名");
            }
            if (!TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getCity())){
                tvUserInfoCity.setText(UserTask.getInstance().getUserInfoData().getCity());
            }

            if (!TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getBirthday())){
                tvUserInfoDirthDay.setText(UserTask.getInstance().getUserInfoData().getBirthday());
            }
            if (!TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getSex())&&UserTask.getInstance().getUserInfoData().getSex()!=null){
                tvUserInfoSex.setText(UserTask.getInstance().getUserInfoData().getSex());
            }else {
                tvUserInfoSex.setText("选择性别");
            }

        }

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("个人资料");
        setInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
