/*
package com.spellingtrip.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.PiPeiBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.city.CityPickerActivity;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.fragment.BaseFragment;
import com.spellingtrip.example.picker.picker.OptionPicker;
import com.spellingtrip.example.picker.widget.WheelView;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.AnimationUtil;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class TourismToPinFragment extends BaseFragment {
    @BindView(R.id.tvChooseStartTime)
    public TextView tvChooseStartTime;
    @BindView(R.id.tvStartLoaction)
    public TextView tvStartLoaction;
    @BindView(R.id.tvEndLoaction)
    public TextView tvEndLoaction;
    @BindView(R.id.tvChooseSex)
    public TextView tvChooseSex;
    @BindView(R.id.tvChooseArg)
    public TextView tvChooseArg;
    @BindView(R.id.tvChooseNum)
    public TextView tvChooseNum;
    @BindView(R.id.ivTOPiPei)
    public ImageView ivTOPiPei;
    @BindView(R.id.tvNumDay)
    public TextView tvNumDay;
    @BindView(R.id.topInCheckBox)
    public CheckBox topInCheckBox;
    @BindView(R.id.etToPinDesc)
    public EditText etToPinDesc;
    @BindView(R.id.llTopPin)
    public LinearLayout llTopPin;
    private int STRAT_CITY_CODE = 101;
    private int SEND_CITY_CODE = 102;
    private String[] sexs = new String[]{"不限", "男", "女"};
    private String[] args = new String[]{"不限", "18-25", "26-30", "31-35", "36-40","40+"};
    private String[] nums = new String[]{"1", "2", "3", "4", "5"};
    private int STRAT_TIME_CODE = 103;
    private String startime;
    private String endtime;
    private LogineDialog logineDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topin;
    }

    @Override
    protected void findView(View view) {

    }



    @Override
    protected void getData() {

    }
    @Override
    protected void setData() {

    }
    private void xianshi() {
        etToPinDesc.setVisibility(View.VISIBLE);
       etToPinDesc.setAnimation(AnimationUtil.moveToViewLocation());
    }

    private void yincang(){
        etToPinDesc.setVisibility(View.GONE);
        etToPinDesc.setAnimation(AnimationUtil.moveToViewBottom());
    }
    @OnClick({ R.id.tvStartLoaction, R.id.tvEndLoaction, R.id.tvChooseSex, R.id.tvChooseArg, R.id.tvChooseNum,
            R.id.ivTOPiPei, R.id.tvChooseStartTime,R.id.topInCheckBox})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.topInCheckBox:
                if (topInCheckBox.isChecked()){
                    topInCheckBox.setButtonDrawable(R.mipmap.xingchengsel);
                    xianshi();
                }else {
                    topInCheckBox.setButtonDrawable(R.mipmap.xingchengnor);
                    yincang();
                }
                break;
            case R.id.tvChooseStartTime:
                startActivityForResult(new Intent(getActivity(), ChooseTimeActivity.class), STRAT_TIME_CODE);
                break;
            case R.id.tvStartLoaction:
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class), STRAT_CITY_CODE);
                break;
            case R.id.tvEndLoaction:
               startActivityForResult(new Intent(getActivity(), CityPickerActivity.class), SEND_CITY_CODE);
                break;
            case R.id.tvChooseSex:
                setChooseData(tvChooseSex, sexs);
                break;
            case R.id.tvChooseArg:
                setChooseData(tvChooseArg, args);
                break;
            case R.id.tvChooseNum:
                setChooseData(tvChooseNum, nums);
                break;
            case R.id.ivTOPiPei:
                String startcity = tvStartLoaction.getText().toString().trim();
                String endcity = tvEndLoaction.getText().toString().trim();
                String sex = tvChooseSex.getText().toString().trim();
                String arg = tvChooseArg.getText().toString().trim();
                String num = tvChooseNum.getText().toString().trim();
                String date = tvChooseStartTime.getText().toString().trim();
               String desc= etToPinDesc.getText().toString().trim();
                String choose="";
                if (topInCheckBox.isChecked()){
                     choose="true";
                }else {
                    choose="false";
                }
                if (TextUtils.isEmpty(date) || date.contains("请选择起始时间")) {
                    ToastUtil.show("请选择起始日期");
                } else {
                    if (logineDialog == null) {
                        logineDialog = new LogineDialog(getActivity(), "");
                    }
                    logineDialog.show();
                    setPin(startcity, endcity, sex, date, arg, num, choose, desc);
                }
                break;

            case R.id.llTopPin:
                break;

        }

    }


    */
/**
     * 匹配
     *
     * @param startcity
     * @param endcity
     * @param sex
     * @param arg
     * @param num
     * @param
     *//*

    private void setPin(String startcity, String endcity, String sex, String date, String arg, String num, String ispublish, String desc) {
        String fromAge = null;
        String toAge = null;
        if (arg.contains("不限")) {
            fromAge = "10";
            toAge = "60";
        } else {
            fromAge = arg.substring(0, 2);
            toAge = arg.substring(3, 5);
        }
        String mStartime= startime.replace("年","-").replace("月","-").replace("日","");
        String mendtime= endtime.replace("年","-").replace("月","-").replace("日","");
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.PINSEARCH).addParams("fromArea", startcity).addParams("toArea", endcity)
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .addParams("startDate", mStartime).addParams("endDate", mendtime).addParams("expectedSex", sex)
                .addParams("fromAge", fromAge).addParams("toAge", toAge).addParams("totalCount", num)
                .addParams("publishSchedule", ispublish).addParams("description", desc)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
            }
            @Override
            public void onResponse(String s, int i) {
                if (logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                if (!TextUtils.isEmpty(s)) {
                    String tops=JsonUtil.toJson(s);
                    if (tops.contains("请完善性别")){
                        ToastUtil.show("请完善性别");
                    }else if (tops.contains("接口异常")){
                        ToastUtil.show("匹配失败");
                    }else {
                        PiPeiBean piPeiBean = (PiPeiBean) JsonUtil.fromJson(s, PiPeiBean.class);
                        if (piPeiBean.getCode() == 0) {
                            if (piPeiBean.getData().size() > 0) {
                                EventBus.getDefault().post(new EventType(ConstantsBean.AUTOPI));
                                ActivityUtils.skipActivity(getActivity(), MyJieBanActivity.class, 0, "");
                                getActivity().finish();
                            } else {
                                setDialog(piPeiBean.getObjId());
                            }
                        } else if (piPeiBean.getMsg().contains("性别")||piPeiBean.getMsg().contains("接口")){
                            setContent();
                        }else {
                            ToastUtil.show(piPeiBean.getMsg());
                        }
                    }
                }
            }
        });
    }
    private void setDialog(final int objId) {
        CommonDialog.getDialog(getActivity(), "提示", ConstantsBean.PIPEITEXT,
                "否", "是", new OnButtonClick() {
                    @Override
                    public void button01ClickListener() {
                    }
                    @Override
                    public void button02ClickListener() {
                        setAuto(objId);
                    }
                });
    }
    private void setContent() {
        CommonDialog.getDialog(getActivity(), "提示","请完善个人信息",
                "否", "是", new OnButtonClick() {
                    @Override
                    public void button01ClickListener() {
                    }
                    @Override
                    public void button02ClickListener() {
                        ActivityUtils.skipActivity(getActivity(),UserInfoActivity.class,0,"");
                    }
                });
    }
    */
/**
     * 后台挂起
     *
     * @param objId
     *//*

    private void setAuto(int objId) {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(getActivity(), "");
        }
        logineDialog.show();
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.PYAUTO).addParams("pinId", String.valueOf(objId))
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId())).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }

            }
            @Override
            public void onResponse(String s, int i) {
                if (logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {
                        ToastUtil.show(reMoveBean.getMsg());
                        EventBus.getDefault().post(new EventType(ConstantsBean.AUTOPI));
                        ActivityUtils.skipActivity(getActivity(), MyJieBanActivity.class, 0, "");
                        getActivity().finish();
                    } else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }

    private void setChooseData(final TextView mTextView, String[] contents) {
        OptionPicker picker = new OptionPicker(getActivity(), contents);
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.WHITE, 1);
        picker.setSubmitTextColor(getResources().getColor(R.color.articleTitle));
        picker.setCancelTextColor(getResources().getColor(R.color.articleTitle));
        picker.setTextColor(getResources().getColor(R.color.textb3));
        picker.setSelectedIndex(1);
        picker.setTopLineVisible(false);
        picker.setCycleDisable(true);
        picker.setLineVisible(false);
        picker.setPressedTextColor(getResources().getColor(R.color.articleTitle));
        picker.setTextSize(20);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                mTextView.setText(item);
            }
        });
        picker.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STRAT_CITY_CODE && resultCode == getActivity().RESULT_OK) {
            tvStartLoaction.setText(data.getStringExtra("city") + "");
        }
        if (requestCode == SEND_CITY_CODE && resultCode == getActivity().RESULT_OK) {
            tvEndLoaction.setText(data.getStringExtra("city") + "");
        }
        if (requestCode == STRAT_TIME_CODE && resultCode ==getActivity().RESULT_OK) {
            startime = data.getStringExtra("startime");
            endtime = data.getStringExtra("endtime");
            String mStartime= startime.replace("年","-").replace("月","-").replace("日","");
            String mendtime= endtime.replace("年","-").replace("月","-").replace("日","");
             long day=CommonUtil.getDayCha(mStartime,mendtime);
            tvNumDay.setText("共计" + day + "天");
           tvChooseStartTime.setText(startime + "-" + endtime);
        }

    }
}
*/
