/*
package com.spellingtrip.example.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;

import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.exceptions.HyphenateException;
import com.spellingtrip.example.MainActivity;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.ChooseLocationActivity;
import com.spellingtrip.example.activity.ChoosePinActivity;
import com.spellingtrip.example.activity.UserInfoActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.PinCityCallBack;
import com.spellingtrip.example.bean.UpDataPhotoBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.picker.picker.OptionPicker;
import com.spellingtrip.example.picker.widget.WheelView;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.PinMediaAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class TourismPinFragment extends BaseFragment implements PinMediaAdapter.OnAddMediaListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.pinRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.rlPinTime)
    public TextView rlPinTime;
    @BindView(R.id.rlPinLocation)
    public TextView rlPinLocation;
    @BindView(R.id.rlPinActivity)
    public TextView rlPinActivity;
    @BindView(R.id.rlPinPeople)
    public TextView rlPinPeople;
    @BindView(R.id.etPinMoney)
    public EditText etPinMoney;
    @BindView(R.id.etPinDesc)
    public EditText etPinDesc;
    @BindView(R.id.ivPinPublish)
    public ImageView ivPinPublish;
    @BindView(R.id.pinRadioGroup)
    public RadioGroup radioGroup;
    @BindView(R.id.pinRadioButtonAA)
    public RadioButton radioGroupAA;
    @BindView(R.id.pinRadioButtonPles)
    public RadioButton ples;
    @BindView(R.id.tvZongtime)
    public TextView tvZongtime;
    @BindView(R.id.tvChooseEndTime)
    public TextView tvChooseEndTime;
    private PinMediaAdapter mMediaAdapter;
    private String[] nums = new String[]{"1", "2", "3", "4", "5", "6", "7+"};
    private static final int CHOOSE_ACTIVITY = 1001;
    private String lng = "";
    private String lat = "";
    private String typeid;
    private static final int CHOOSE_LOCATION = 1002;
    private String payType = "1";
    private static final int CHOOSE_IMG = 1003;
    private List<MediaEntity> result = new ArrayList<>();
    //private List<String> result=new ArrayList<>();
    private LogineDialog logineDialog;
    private List<String> lists = new ArrayList<>();
    private String endTime = "";
    private String startTime = "";
    private InputMethodManager inputManager;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pinactivity;
    }

    @Override
    protected void findView(View view) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        mMediaAdapter = new PinMediaAdapter(this, getActivity(),);
        recyclerView.setAdapter(mMediaAdapter);
        mMediaAdapter.setData(result);
        radioGroup.setOnCheckedChangeListener(this);
        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void getData() {

    }

    @OnClick({R.id.rlPinTime, R.id.rlPinLocation, R.id.rlPinActivity, R.id.rlPinPeople, R.id.ivPinPublish, R.id.tvChooseEndTime})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlPinTime:
                getTime("start");
                break;
            case R.id.tvChooseEndTime:
                getTime("end");
                break;
            case R.id.rlPinLocation:
                hideKeyboard();
                startActivityForResult(new Intent(getActivity(), ChooseLocationActivity.class), CHOOSE_LOCATION);
                break;
            case R.id.rlPinActivity:
                startActivityForResult(new Intent(getActivity(), ChoosePinActivity.class), CHOOSE_ACTIVITY);
                break;
            case R.id.rlPinPeople:
                setChooseData(rlPinPeople, nums);
                break;
            case R.id.ivPinPublish:
                String sTime = rlPinTime.getText().toString().trim();
                String eTime = tvChooseEndTime.getText().toString().trim();
                String pinLocation = rlPinLocation.getText().toString().trim();
                String pinActivity = rlPinActivity.getText().toString().trim();
                String pinPeople = rlPinPeople.getText().toString().trim();
                String pinMoney = etPinMoney.getText().toString().trim();
                String pinDesc = etPinDesc.getText().toString().trim();
                Date sDate=null;
                Date eDate=null;
                if (!TextUtils.isEmpty(sTime)&&!TextUtils.isEmpty(eTime)){
                    SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                         sDate = dateFormat.parse(startTime);//开始时间
                         eDate = dateFormat.parse(endTime);//结束时间
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (!TextUtils.isEmpty(sTime) && sTime.equals("选择起始时间")) {
                    ToastUtil.show("选择起始时间");
                } else if (!TextUtils.isEmpty(eTime) && eTime.equals("选择结束时间")) {
                    ToastUtil.show("选择结束时间");
                } else if (sDate.getTime()-eDate.getTime() > 0) {
                    ToastUtil.show("结束时间不能小于当前时间");
                } else if (!TextUtils.isEmpty(pinLocation) && pinLocation.equals("选择地点")) {
                    ToastUtil.show("请选择地点");
                } else if (!TextUtils.isEmpty(pinActivity) && pinActivity.equals("选择活动")) {
                    ToastUtil.show("请选择活动类型");
                } else if (!TextUtils.isEmpty(pinPeople) && pinPeople.equals("选择人数")) {
                    ToastUtil.show("请选择人数");
                } else if (!TextUtils.isEmpty(pinMoney) && pinMoney.equals("设置花费")) {
                    ToastUtil.show("请设置花费");
                } else if (TextUtils.isEmpty(pinDesc)) {
                    ToastUtil.show("请填写活动描述");
                } else if (result.size() == 0) {
                    ToastUtil.show("请选择图片");
                } else {
                    setUpLoadImg(eTime, pinLocation, typeid, pinPeople, pinMoney, pinDesc);
                }
                break;
        }
    }
    */
/**
     * hide
     *//*

    protected void hideKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null){
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private void getTime(String type) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2023, 11, 31);
        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (type.equals("end")) {
                    endTime = CommonUtil.getTimeFen(date);
                    tvChooseEndTime.setText(endTime);
                } else
                    startTime = CommonUtil.getTimeFen(date);
                    rlPinTime.setText(startTime);
            }
        }).setType(new boolean[]{true, true, true, true, true, true}).gravity(Gravity.BOTTOM)// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(17)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(getActivity().getResources().getColor(R.color.articleTitle))//标题文字颜色
                .setSubmitColor(getActivity().getResources().getColor(R.color.textb3))//确定按钮文字颜色
                .setCancelColor(getActivity().getResources().getColor(R.color.textb3))//取消按钮文字颜色
                .setTitleBgColor(getActivity().getResources().getColor(R.color.main_bg))//标题背景颜色 Night mode
                .setBgColor(getActivity().getResources().getColor(R.color.main_bg))//滚轮背景颜色 Night mode
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    private void setUpLoadImg(String pintime, String pinLocation, String pinActivity, String pinPeople, String pinMoney, String pinDesc) {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(getActivity(), ConstantsBean.FABU);
        }
        logineDialog.show();
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            paths.add(result.get(i).getFinalPath());
        }
        if (lists != null && lists.size() > 0) {
            lists.clear();
        }
        Luban.with(getActivity())
                .load(paths)
                .ignoreBy(100)
                .setTargetDir(getActivity().getExternalCacheDir().getPath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        setUpdataFile(file, pintime, pinLocation, pinActivity, pinPeople, pinMoney, pinDesc);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();

    }

    private void setUpdataFile(File file, String pintime, String pinLocation, String pinActivity, String pinPeople, String pinMoney, String pinDesc) {
        String time = CommonUtil.getSysTime();
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPLOAD_PATH).addFile("file", time + ".png", file)
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog != null && logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    UpDataPhotoBean upDataPhotoBean = (UpDataPhotoBean) JsonUtil.fromJson(s, UpDataPhotoBean.class);
                    if (upDataPhotoBean.getCode() == 0 && upDataPhotoBean.getData().isSuccess()) {
                        lists.add(upDataPhotoBean.getData().getFid());
                        if (lists.size() == result.size()) {
                            setPublish(pintime, pinLocation, pinActivity, pinPeople, pinMoney, pinDesc, lists);
                        }
                    } else {
                        ToastUtil.show(upDataPhotoBean.getMsg());
                    }
                }
            }
        });
    }

    private void setPublish(String pintime, String pinLocation, String pinActivity, String pinPeople, String pinMoney, String pinDesc, List<String> lists) {
        String fids = JsonUtil.toJson(lists).replace("[", "").replace("]", "")
                .replace("\"", "");
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.PUBLISHACTIVITY).addParams("startTime", startTime)
                .addParams("endTime", endTime).addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .addParams("location", pinLocation).addParams("lng", lng).addParams("lat", lat)
                .addParams("category", pinActivity).addParams("userCount", pinPeople).addParams("payType", payType)
                .addParams("content", pinDesc).addParams("cost", pinMoney).addParams("fid", fids)
                .addParams("coverImage", fids).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog != null && logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
            }

            @Override
            public void onResponse(String s, int i) {
                if (logineDialog != null && logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                if (!TextUtils.isEmpty(s)) {
                    String pulishs=JsonUtil.toJson(s);
                   if (pulishs.contains("完善性别")){
                        ToastUtil.show("请先完善性别");
                    }else if (pulishs.contains("接口异常")){
                       CommonDialog.getDialog(getActivity(), "提示", "请先去完善个人资料(性别/年龄)", "取消", "去完善", new OnButtonClick() {
                           @Override
                           public void button01ClickListener() {

                           }

                           @Override
                           public void button02ClickListener() {
                               ActivityUtils.skipActivity(getActivity(), UserInfoActivity.class, 0, "");
                           }
                       });

                   }else {
                        PinCityCallBack cityCallBack = (PinCityCallBack) JsonUtil.fromJson(s, PinCityCallBack.class);
                        if (cityCallBack.getCode() == 0) {
                            ToastUtil.show("发布成功");
                            EventBus.getDefault().post(new SendMessageData(Constant.PUBLISH_ACTIVITY));
                            getActivity().finish();
                        } else {
                            ToastUtil.show(cityCallBack.getMsg());
                        }
                    }

                } else {

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    String title = data.getStringExtra("title");
                    rlPinActivity.setText(title + "");
                    typeid = data.getStringExtra("typeid");
                }
                break;
            case CHOOSE_LOCATION:
                if (resultCode == Activity.RESULT_OK) {
                    String address = data.getStringExtra("address");
                    lat = data.getStringExtra("latitude");
                    lng = data.getStringExtra("longitube");
                    rlPinLocation.setText(address);
                }
                break;
            case CHOOSE_IMG:
                if (resultCode == Activity.RESULT_OK) {
                    //返回的数据
                    // result = Matisse.obtainPathResult(data);
                    result = Phoenix.result(data);
                    mMediaAdapter.setData(result);
                }
                break;
        }
    }

    @Override
    protected void setData() {

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
    public void onaddMedia() {
        Phoenix.with()
                .theme(PhoenixOption.THEME_DEFAULT)// 主题
                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(6)// 最大选择数量
                .minPickNumber(0)// 最小选择数量
                .spanCount(4)// 每行显示个数
                .enablePreview(false)// 是否开启预览
                .enableCamera(false)// 是否开启拍照
                .enableAnimation(false)// 选择界面图片点击效果
                .enableCompress(false)// 是否开启压缩
                .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                .compressVideoFilterSize(1024)//多少kb以下的视频不压缩
                .thumbnailHeight(160)// 选择界面图片高度
                .thumbnailWidth(160)// 选择界面图片宽度
                .enableClickSound(false)// 是否开启点击声音
                .pickedMediaList(mMediaAdapter.getData())// 已选图片数据
                .videoFilterTime(120)//显示多少秒以内的视频
                .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                .start(getActivity(), PhoenixOption.TYPE_PICK_MEDIA, CHOOSE_IMG);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.pinRadioButtonAA:
                payType = "1";
                break;
            case R.id.pinRadioButtonPles:
                payType = "2";
                break;
        }
    }

    public void setPhoto(List<MediaEntity> mResult) {
        result = mResult;
        mMediaAdapter.setData(mResult);
    }

    protected void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }
        if (chatFragmentHelper != null) {
            //set extension
            chatFragmentHelper.onSetMessageAttributes(message);
        }
        message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    protected EaseChatFragmentHelper chatFragmentHelper;

    public void setChatFragmentHelper(EaseChatFragmentHelper chatFragmentHelper) {
        this.chatFragmentHelper = chatFragmentHelper;
    }


    public interface EaseChatFragmentHelper {
        */
/**
         * set message attribute
         *//*

        void onSetMessageAttributes(EMMessage message);

        */
/**
         * enter to chat detail
         *//*

        void onEnterToChatDetails();

        */
/**
         * on avatar clicked
         *
         * @param username
         *//*

        void onAvatarClick(String username);

        */
/**
         * on avatar long pressed
         *
         * @param username
         *//*

        void onAvatarLongClick(String username);

        */
/**
         * on message bubble clicked
         *//*

        boolean onMessageBubbleClick(EMMessage message);

        */
/**
         * on message bubble long pressed
         *//*

        void onMessageBubbleLongClick(EMMessage message);

        */
/**
         * on extend menu item clicked, return true if you want to override
         *
         * @param view
         * @param itemId
         * @return
         *//*

        boolean onExtendMenuItemClick(int itemId, View view);

        */
/**
         * on set custom chat row provider
         *
         * @return
         *//*

        EaseCustomChatRowProvider onSetCustomChatRowProvider();
    }
}
*/
