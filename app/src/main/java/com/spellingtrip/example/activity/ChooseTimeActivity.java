package com.spellingtrip.example.activity;

import android.content.Intent;
import android.util.Log;

import com.rich.library.CalendarSelectView;
import com.rich.library.ConfirmSelectDateCallback;
import com.rich.library.DayTimeEntity;
import com.spellingtrip.example.R;
import com.spellingtrip.example.utils.CommonUtil;

import java.util.Calendar;

import butterknife.BindView;

public class ChooseTimeActivity extends BaseActivity{
    @BindView(R.id.calendar_select)
    public CalendarSelectView calendarSelectView;
     @Override
    protected int getLayoutId() {
        return R.layout.activity_choosetime;
    }

    @Override
    protected void initView() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2023, 11, 31);
        calendarSelectView.setCalendarRange(selectedDate,endDate,null,null);
        calendarSelectView.setConfirmCallback(selectDateCallback);
    }
    ConfirmSelectDateCallback selectDateCallback = new ConfirmSelectDateCallback() {
        @Override
        public void selectSingleDate(DayTimeEntity timeEntity) {
            //TODO 单选回调此方法
        }

        @Override
        public void selectMultDate(DayTimeEntity startTimeEntity, DayTimeEntity endTimeEntity) {
            //TODO 选择时间段回调些方法
           String mMonth="";
            String mDay="";
            String eMonth="";
            String eDay="";
            if (startTimeEntity.month+1<10){
                mMonth="0"+(startTimeEntity.month+1);
            }else {
                mMonth=(startTimeEntity.month+1)+"";
            }
            if (startTimeEntity.day<10){
                mDay="0"+startTimeEntity.day;
            }else {
                mDay=startTimeEntity.day+"";
            }
            if (endTimeEntity.month+1<10){
                eMonth="0"+(endTimeEntity.month+1);
            }else {
                eMonth=(endTimeEntity.month+1)+"";
            }
            if (endTimeEntity.day<10){
                eDay="0"+endTimeEntity.day;
            }else {
                eDay=endTimeEntity.day+"";
            }
            if (mDay.equals("00")&&!eDay.equals("00")){
                mDay=eDay;
            }
            if (eDay.equals("00")&&!mDay.equals("00")){
                eDay=mDay;
            }
           String startime= startTimeEntity.year+"年"+mMonth+"月"+mDay+"日";
            String endtime= endTimeEntity.year+"年"+eMonth+"月"+eDay+"日";
           Intent intent= getIntent();
            intent.putExtra("startime", startime);
           intent.putExtra("endtime", endtime);
            setResult(RESULT_OK, intent);
            finish();
        }
    };
    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("日期选择");
    }
}
