package com.spellingtrip.example.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.LabelsEvent;
import com.spellingtrip.example.bean.PiPeiPublishBean;
import com.spellingtrip.example.bean.SamePinBean;
import com.spellingtrip.example.bean.TwoMessageEvent;
import com.spellingtrip.example.bean.UpDataPhotoBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.viewpager.MerchantProcessAdapter;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.MessageEvent;
import com.spellingtrip.example.bean.Proces;
import com.spellingtrip.example.city.CityPickerActivity;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.dialog.SamePublicDialog;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.view.WheelView;
import com.spellingtrip.example.viewpager.PinMediaAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 同城发布
 */
public class SamePublicActivity extends BaseActivity implements PinMediaAdapter.OnAddMediaListener {
    public static final String TAG = "SamePublicActivity";
    @BindView(R.id.start_time)
    EditText startTime;
    @BindView(R.id.time_right)
    ImageView timeRight;
    @BindView(R.id.site_location)
    EditText siteLocation;
    @BindView(R.id.site_right)
    ImageView siteRight;
    @BindView(R.id.activity_label)
    EditText activityLabel;
    @BindView(R.id.label_right)
    ImageView labelRight;
    @BindView(R.id.num_limitation)
    EditText numLimitation;
    @BindView(R.id.same_group)
    EditText sameGroup;
    @BindView(R.id.add_way)
    EditText addWay;
    @BindView(R.id.capita_cost)
    EditText capitaCost;
    @BindView(R.id.cost_right)
    ImageView costRight;
    @BindView(R.id.add_days)
    TextView addDays;
    @BindView(R.id.recy_process)
    RecyclerView recyProcess;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.image_num)
    TextView imageNum;
    @BindView(R.id.recy_image)
    RecyclerView recyImage;
    @BindView(R.id.process_left)
    ImageView processLeft;
    @BindView(R.id.relativelayout)
    RelativeLayout relativelayout;
    @BindView(R.id.merchant_read)
    TextView merchantRead;
    @BindView(R.id.merchant_public)
    TextView merchantPublic;
    private String selectText = "";


    private List<MediaEntity> result = new ArrayList<>();
    private PinMediaAdapter mMMediaAdapter;
    private int STRAT_CITY_CODE = 101;
    private int SEND_CITY_CODE = 102;
    private int STRAT_TIME_CODE = 103;
    private static final int CHOOSE_IMG = 1003;
    private String startime, mStartime, mendtime, endtime;
    private ArrayList<String> numberlist = new ArrayList<>();
    private MerchantProcessAdapter mMerchantProcessAdapter;
    List<Proces.ProcesBean> list = new ArrayList<>();
    List<Proces> Proces = new ArrayList<>();
    Proces.ProcesBean data = new Proces.ProcesBean();
    private LogineDialog logineDialog;
    private String mGroupname;
    private boolean onclick = false;
    private ArrayList<String> lists = new ArrayList<>();
    private String mSite1;
    private String mDescribe1;
    private String mTime1;
    private String mSite2;
    private String mDescribe2;
    private String mTime2;
    private String mSite3;
    private String mDescribe3;
    private String mTime3;
    private String mSite4;
    private String mDescribe4;
    private String mTime4;
    private String mSite5;
    private String mDescribe5;
    private String mTime5;
    private String mSite6;
    private String mDescribe6;
    private String mTime6;
    private String mSite7;
    private String mDescribe7;
    private String mTime7;
    private String mSite8;
    private String mDescribe8;
    private String mTime8;
    private String mSite9;
    private String mDescribe9;
    private String mTime9;
    private String mSite10;
    private String mDescribe10;
    private String mTime10;
    private String mLocation;
    private String mDescription;
    private String mActTime;
    private String mLatitude;
    private String mLongitude;
    private static PopupWindow popupWindow;
    private String mLabelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_same_public;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        SharedPreferences sp = getSharedPreferences("lat_log", Context.MODE_PRIVATE);
        Boolean value = sp.contains("latitude");
        if (value){
            mLatitude = sp.getString("latitude","latitude");
            mLongitude = sp.getString("longitude","longitude");
            Log.v(TAG,"--------latitude=="+ mLatitude +"--------longitude=="+ mLongitude);

        }
        initData();

        startTime.setFocusable(false);
        siteLocation.setFocusable(false);
        activityLabel.setFocusable(false);
        numLimitation.setFocusable(false);
        sameGroup.setFocusable(false);
        addWay.setFocusable(false);
        //人均费用输入数字类型
        capitaCost.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        recyImage.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recyImage.addItemDecoration(new SpacesItemDecoration(10));
        mMMediaAdapter = new PinMediaAdapter(this, SamePublicActivity.this, imageNum);
        recyImage.setAdapter(mMMediaAdapter);
        mMMediaAdapter.setData(result);


        //活动流程
        recyProcess.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        mMerchantProcessAdapter = new MerchantProcessAdapter(SamePublicActivity.this,list);
        recyProcess.setAdapter(mMerchantProcessAdapter);

        data.setSite("");
        data.setDescribe("");
        data.setTime("");
        data.setDay(1);
        list.add(data);

        isaccomplish();

    }

    //数字选择器
    private void initData() {
        numberlist.clear();
        for (int i = 0; i <= 100; i++) {
            numberlist.add(String.format("%d", i));
        }


    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STRAT_CITY_CODE && resultCode == RESULT_OK) {
            siteLocation.setText(data.getStringExtra("city") + "");
            isaccomplish();

        }
//        if (requestCode == SEND_CITY_CODE && resultCode == RESULT_OK) {
//            tvEndLoaction.setText(data.getStringExtra("city") + "");
//        }
        if (requestCode == STRAT_TIME_CODE && resultCode == RESULT_OK) {
            startime = data.getStringExtra("startime");
            endtime = data.getStringExtra("endtime");
            mStartime = startime.replace("年", "-").replace("月", "-").replace("日", "");
            mendtime = endtime.replace("年", "-").replace("月", "-").replace("日", "");
            long day = CommonUtil.getDayCha(mStartime, mendtime);
            startTime.setText(mStartime);
            isaccomplish();

        }
        if (requestCode == CHOOSE_IMG && resultCode == RESULT_OK) {
            //返回的数据
            // result = Matisse.obtainPathResult(data);
            result = Phoenix.result(data);
            mMMediaAdapter.setData(result);
            if (result.size() > 0) {
                imageNum.setText(result.size() + "/6");
            } else {
                imageNum.setText("0/6");
            }
            isaccomplish();

        }
    }

    /**
     * 图片上传回调
     */
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
                .pickedMediaList(mMMediaAdapter.getData())// 已选图片数据
                .videoFilterTime(120)//显示多少秒以内的视频
                .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                .start(SamePublicActivity.this, PhoenixOption.TYPE_PICK_MEDIA, CHOOSE_IMG);
    }


    @OnClick({R.id.process_left, R.id.merchant_read, R.id.merchant_public, R.id.start_time, R.id.site_location, R.id.activity_label, R.id.num_limitation, R.id.same_group, R.id.add_way, R.id.add_days})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.process_left:
                //关闭当前页面
                finish();
                break;
            case R.id.add_days:
                mMerchantProcessAdapter.setData();
                break;
            case R.id.merchant_read:
                //阅读
                onclick = true;
                Drawable drawableLeft = getResources().getDrawable(R.drawable.check);
                merchantRead.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                        null, null, null);
                SamePublicDialog.publicread(SamePublicActivity.this);
                isaccomplish();

                break;

            case R.id.start_time:
                //时间
                startActivityForResult(new Intent(SamePublicActivity.this, ChooseTimeActivity.class), STRAT_TIME_CODE);
                break;
            case R.id.site_location:
                //地点
                startActivityForResult(new Intent(SamePublicActivity.this, CityPickerActivity.class), STRAT_CITY_CODE);
                break;
            case R.id.activity_label:
                //标签
                ActivityUtils.skipActivity(SamePublicActivity.this, SameLabelActivity.class, 0, "");
                break;
            case R.id.num_limitation:
                //人数限制
                showDialog(numLimitation, numberlist, 8);
                break;
            case R.id.same_group:
                //是否建立群聊
                groupandway(SamePublicActivity.this);


                break;
            case R.id.add_way:
                //申请加入方式
                SamePublicDialog.groupandway(SamePublicActivity.this, "way", new OnButtonClick() {
                    @Override
                    public void button01ClickListener() {
                        addWay.setText("直接加入");
                    }

                    @Override
                    public void button02ClickListener() {
                        addWay.setText("申请加入");
                    }
                });
                isaccomplish();

                break;
            case R.id.merchant_public:
                listsize();
                //发布
                String startTimes = startTime.getText().toString().trim();
                String siteLocations = siteLocation.getText().toString().trim();
                String activityLabels = activityLabel.getText().toString().trim();
                String numLimitations = numLimitation.getText().toString().trim();
                String sameGroups = sameGroup.getText().toString().trim();
                String addWays = addWay.getText().toString().trim();
                String capitaCosts = capitaCost.getText().toString().trim();
                String editContents = editContent.getText().toString().trim();
                if (TextUtils.isEmpty(startTimes) || startTimes.equals("开始时间")) {
                    ToastUtil.show("请选择时间");
                } else if (TextUtils.isEmpty(siteLocations) || siteLocations.equals("活动地点")) {
                    ToastUtil.show("请选择活动地点");
                } else if (TextUtils.isEmpty(activityLabels) || activityLabels.equals("活动类型")) {
                    ToastUtil.show("请选择活动类型");
                } else if (TextUtils.isEmpty(numLimitations) || numLimitations.equals("人数限制")) {
                    ToastUtil.show("请选择人数限制");
                } else if (TextUtils.isEmpty(sameGroups) || sameGroups.equals("是否建立群聊")) {
                    ToastUtil.show("是否建立群聊");
                } else if (TextUtils.isEmpty(addWays) || addWays.equals("申请加入方式")) {
                    ToastUtil.show("请选择加入方式");
                } else if (TextUtils.isEmpty(capitaCosts) || capitaCosts.equals("人均费用")) {
                    ToastUtil.show("请输入人均费用");
                } else if (TextUtils.isEmpty(editContents)) {
                    ToastUtil.show("请填写活动描述");
                }else if (result == null || result.size() == 0) {
                    ToastUtil.show("请选择图片");
                }else if (list.size() ==0){
                    ToastUtil.show("请填写活动流程");
                }else if (onclick==false){
                    ToastUtil.show("请阅读并同意平台发布规则");
                }else {
                    Log.v("TAG","发布内容"+ startTimes + siteLocations + activityLabels +numLimitations+sameGroups+addWays+capitaCosts+editContents+mLocation+mActTime+mDescription);
                setUpLoadImg(startTimes, siteLocations, activityLabels,numLimitations,sameGroups,addWays,capitaCosts,editContents,mLocation,mActTime,mDescription);

                }

                break;
        }
    }




    /**
     * 上传图片
     * @param startTimes
     * @param siteLocations
     * @param activityLabels
     * @param numLimitations
     * @param sameGroups
     * @param addWays
     * @param capitaCosts
     * @param editContents
     * @param location
     * @param actTime
     * @param description
     */
    private void setUpLoadImg(String startTimes, String siteLocations, String activityLabels, String numLimitations, String sameGroups, String addWays, String capitaCosts, String editContents, String location, String actTime, String description) {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(this, ConstantsBean.FABU);
        }
        logineDialog.show();
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            paths.add(result.get(i).getFinalPath());
        }
        if (lists != null && lists.size() > 0) {
            lists.clear();
        }
        Luban.with(SamePublicActivity.this)
                .load(paths)
                .ignoreBy(100)
                .setTargetDir(getExternalCacheDir().getPath())
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
                        setUpdataFile(file, startTimes,siteLocations,activityLabels,numLimitations,sameGroups,addWays,capitaCosts,editContents,mLocation,mActTime,mDescription);
                    }



                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    private void setUpdataFile(File file, String startTimes, String siteLocations, String activityLabels, String numLimitations, String sameGroups, String addWays, String capitaCosts, String editContents, String location, String actTime, String description) {
    //发布计划请求
        String time = CommonUtil.getSysTime();

        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPLOAD_PATH).addFile("file", time + ".png", file)
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .build().readTimeOut(50000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (logineDialog != null && logineDialog.isShowing()) {
                            logineDialog.dismiss();
                        }
                        Log.v(TAG, "--setUpdataFile--onError()----call==" + call + "------e==" + e + "----i-----" + i);
                        ToastUtil.show(ConstantsBean.ERROR);
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (!TextUtils.isEmpty(s)) {
                            UpDataPhotoBean upDataPhotoBean = (UpDataPhotoBean) JsonUtil.fromJson(s, UpDataPhotoBean.class);
                            Log.v(TAG, "--setUpdataFile--onResponse()----upDataPhotoBean=="  + upDataPhotoBean.getData());
                            if (upDataPhotoBean.getCode() == 0 && upDataPhotoBean.getData().isSuccess()) {
                                lists.add(upDataPhotoBean.getData().getFid());
                                // titles= Arrays.copyOf(titles, titles.length+1);
                                if (lists.size() == result.size()) {
                                    setPin(startTimes,siteLocations,activityLabels,numLimitations,sameGroups,addWays,capitaCosts,editContents,mLocation,mActTime,mDescription,lists);
                                }
                            } else {
                                ToastUtil.show(upDataPhotoBean.getMsg());
                            }
                        }
                    }


                });
    }

    /**
     * 匹配
     * @param startTimes
     * @param siteLocations
     * @param activityLabels
     * @param numLimitations
     * @param sameGroups
     * @param addWays
     * @param capitaCosts
     * @param editContents
     * @param location
     * @param actTime
     * @param description
     */
    private void setPin(String startTimes, String siteLocations, String activityLabels, String numLimitations, String sameGroups, String addWays, String capitaCosts, String editContents, String location, String actTime, String description,ArrayList<String> lists) {
        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        JSONArray array = new JSONArray();
        for (int i = 0; i < lists.size(); i++) {
            array.put(lists.get(i));
        }
        JSONArray labs = new JSONArray();
        Log.v("TAG","---Proces.size() =="+Proces.size() );
        if (Proces.size() > 0) {
            for (int a = 0; a < Proces.size(); a++) {
                labs.put(Proces.get(a).getBeanList());
            }
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userid);
            jsonObject.put("startTime", startTimes);
            jsonObject.put("location", siteLocations);
            jsonObject.put("lng", mLongitude);
            jsonObject.put("lat", mLatitude);
            jsonObject.put("category", mLabelId);
            jsonObject.put("userCount", numLimitations);
            jsonObject.put("content", editContents);
            jsonObject.put("cost", capitaCosts);
            jsonObject.put("days", addWays);
            jsonObject.put("payType", 1);
            jsonObject.put("groupName", sameGroups);
            jsonObject.put("isGroup", true);
            jsonObject.put("imageList", array);
            jsonObject.put("location", location);
            jsonObject.put("steps", labs);
            jsonObject.put("actTime", actTime);
            jsonObject.put("description", description);
            jsonObject.put("days", String.valueOf(list.size()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .postString()
                .url(ConstantsBean.BASE_PATH + ConstantsBean.PUBLISHACTIVITY)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (logineDialog.isShowing()) {
                            logineDialog.dismiss();
                        }
                        if (!TextUtils.isEmpty(s)) {
                            String tops = JsonUtil.toJson(s);
                            if (tops.contains("接口异常")) {
                                ToastUtil.show("发布失败");
                                Log.v(TAG,"-----tops=="+tops+"--jsonObject.toString()=="+jsonObject.toString());
                            } else {
                                SamePinBean pinBean = (SamePinBean) JsonUtil.fromJson(s, SamePinBean.class);
                                if (pinBean.getCode() == 0) {
//                                    EventBus.getDefault().post(new EventType(ConstantsBean.AUTOPI));
//                                    EventBus.getDefault().post(new SendMessageData(ConstantsBean.AUTOPI));
//                                    ActivityUtils.skipActivity(SamePublicActivity.this, AllPublishActivity.class, 0, "");
                                    finish();
                                }
                                Log.v(TAG,"-------onResponse=="+pinBean.getData());
                                        ToastUtil.show(pinBean.getMsg());
                            }
                        }
                    }
                });
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LabelsEvent event) {
        Log.v(TAG, "------enent.getTypeId==" + event.getTypeId()+"------enent.getTypeName==" + event.getTypeName());
        if (event.getTypeId() == null && event.getTypeName() == null) {
            return;
        }
        mLabelId = event.getTypeId();
        activityLabel.setText("#" + event.getTypeName());
    }

    //人数选择器弹框
    private void showDialog(TextView textView, ArrayList<String> list, int selected) {
        showChoiceDialog(list, textView, selected,
                new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        selectText = item;
                    }
                });

    }

    private void showChoiceDialog(ArrayList<String> dataList, final TextView textView, int selected,
                                  WheelView.OnWheelViewListener listener) {
        selectText = "";
        View outerView = LayoutInflater.from(this).inflate(R.layout.dialog_wheelview, null);
        final WheelView wheelView = outerView.findViewById(R.id.wheel_view);
        wheelView.setOffset(2);// 对话框中当前项上面和下面的项数
        wheelView.setItems(dataList);// 设置数据源
        wheelView.setSeletion(selected);// 默认选中第三项
        wheelView.setOnWheelViewListener(listener);

        // 显示对话框，点击确认后将所选项的值显示到Button上
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(outerView)
                .setPositiveButton("确认",
                        (dialogInterface, i) -> {
                            numLimitation.setText(selectText);
                            textView.setTextColor(this.getResources().getColor(R.color.buyvip));
                        })
                .setNegativeButton("取消", null).create();
        alertDialog.show();
        int green = this.getResources().getColor(R.color.buyvip);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(green);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(green);
        isaccomplish();

    }

    /**
     * 获取活动流程内容
     */
    private void listsize() {
        Log.v(TAG,"list.size()=="+list.size());
        if (list.size()==1){
            mLocation = list.get(0).getSite();
            mDescription = list.get(0).getDescribe();
            mActTime = list.get(0).getTime();

        }else if (list.size()==2){
            mSite1 = list.get(0).getSite();
            mDescribe1 = list.get(0).getDescribe();
            mTime1 = list.get(0).getTime();
            mSite2 = list.get(1).getSite();
            mDescribe2 = list.get(1).getDescribe();
            mTime2 = list.get(1).getTime();
            mLocation = new StringBuilder().append(mSite1).append(mSite2).toString();
            mDescription = new StringBuilder().append(mDescribe1).append(mDescribe2).toString();
            mActTime = new StringBuilder().append(mTime1).append(mTime2).toString();

        }else if (list.size()==3){
            mSite1 = list.get(0).getSite();
            mDescribe1 = list.get(0).getDescribe();
            mTime1 = list.get(0).getTime();
            mSite2 = list.get(1).getSite();
            mDescribe2 = list.get(1).getDescribe();
            mTime2 = list.get(1).getTime();
            mSite3 = list.get(2).getSite();
            mDescribe3 = list.get(2).getDescribe();
            mTime3 = list.get(2).getTime();
            mLocation = new StringBuilder().append(mSite1).append(mSite2).append(mSite3).toString();
            mDescription = new StringBuilder().append(mDescribe1).append(mDescribe2).append(mDescribe3).toString();
            mActTime = new StringBuilder().append(mTime1).append(mTime2).append(mTime3).toString();

        }else if (list.size()==4){
            mSite1 = list.get(0).getSite();
            mDescribe1 = list.get(0).getDescribe();
            mTime1 = list.get(0).getTime();
            mSite2 = list.get(1).getSite();
            mDescribe2 = list.get(1).getDescribe();
            mTime2 = list.get(1).getTime();
            mSite3 = list.get(2).getSite();
            mDescribe3 = list.get(2).getDescribe();
            mTime3 = list.get(2).getTime();
            mSite4 = list.get(3).getSite();
            mDescribe4 = list.get(3).getDescribe();
            mTime4 = list.get(3).getTime();
            mLocation = new StringBuilder().append(mSite1).append(mSite2).append(mSite3).append(mSite4).toString();
            mDescription = new StringBuilder().append(mDescribe1).append(mDescribe2).append(mDescribe3).append(mDescribe4).toString();
            mActTime = new StringBuilder().append(mTime1).append(mTime2).append(mTime3).append(mTime4).toString();

        }else if (list.size()==5){
            mSite1 = list.get(0).getSite();
            mDescribe1 = list.get(0).getDescribe();
            mTime1 = list.get(0).getTime();
            mSite2 = list.get(1).getSite();
            mDescribe2 = list.get(1).getDescribe();
            mTime2 = list.get(1).getTime();
            mSite3 = list.get(2).getSite();
            mDescribe3 = list.get(2).getDescribe();
            mTime3 = list.get(2).getTime();
            mSite4 = list.get(3).getSite();
            mDescribe4 = list.get(3).getDescribe();
            mTime4 = list.get(3).getTime();
            mSite5 = list.get(4).getSite();
            mDescribe5 = list.get(4).getDescribe();
            mTime5 = list.get(4).getTime();
            mLocation = new StringBuilder().append(mSite1).append(mSite2).append(mSite3).append(mSite4).append(mSite5).toString();
            mDescription = new StringBuilder().append(mDescribe1).append(mDescribe2).append(mDescribe3).append(mDescribe4).append(mDescribe5).toString();
            mActTime = new StringBuilder().append(mTime1).append(mTime2).append(mTime3).append(mTime4).append(mTime5).toString();

        }else if (list.size()==6){
            mSite1 = list.get(0).getSite();
            mDescribe1 = list.get(0).getDescribe();
            mTime1 = list.get(0).getTime();
            mSite2 = list.get(1).getSite();
            mDescribe2 = list.get(1).getDescribe();
            mTime2 = list.get(1).getTime();
            mSite3 = list.get(2).getSite();
            mDescribe3 = list.get(2).getDescribe();
            mTime3 = list.get(2).getTime();
            mSite4 = list.get(3).getSite();
            mDescribe4 = list.get(3).getDescribe();
            mTime4 = list.get(3).getTime();
            mSite5 = list.get(4).getSite();
            mDescribe5 = list.get(4).getDescribe();
            mTime5 = list.get(4).getTime();
            mSite6 = list.get(5).getSite();
            mDescribe6 = list.get(5).getDescribe();
            mTime6 = list.get(5).getTime();
            mLocation = new StringBuilder().append(mSite1).append(mSite2).append(mSite3).append(mSite4).append(mSite5).append(mSite6).toString();
            mDescription = new StringBuilder().append(mDescribe1).append(mDescribe2).append(mDescribe3).append(mDescribe4).append(mDescribe5).append(mDescribe6).toString();
            mActTime = new StringBuilder().append(mTime1).append(mTime2).append(mTime3).append(mTime4).append(mTime5).append(mTime6).toString();
        }else if (list.size()==7){
            mSite1 = list.get(0).getSite();
            mDescribe1 = list.get(0).getDescribe();
            mTime1 = list.get(0).getTime();
            mSite2 = list.get(1).getSite();
            mDescribe2 = list.get(1).getDescribe();
            mTime2 = list.get(1).getTime();
            mSite3 = list.get(2).getSite();
            mDescribe3 = list.get(2).getDescribe();
            mTime3 = list.get(2).getTime();
            mSite4 = list.get(3).getSite();
            mDescribe4 = list.get(3).getDescribe();
            mTime4 = list.get(3).getTime();
            mSite5 = list.get(4).getSite();
            mDescribe5 = list.get(4).getDescribe();
            mTime5 = list.get(4).getTime();
            mSite6 = list.get(5).getSite();
            mDescribe6 = list.get(5).getDescribe();
            mTime6 = list.get(5).getTime();
            mSite7 = list.get(6).getSite();
            mDescribe7 = list.get(6).getDescribe();
            mTime7 = list.get(6).getTime();
            mLocation = new StringBuilder().append(mSite1).append(mSite2).append(mSite3).append(mSite4).append(mSite5).append(mSite6).append(mSite7).toString();
            mDescription = new StringBuilder().append(mDescribe1).append(mDescribe2).append(mDescribe3).append(mDescribe4).append(mDescribe5).append(mDescribe6).append(mDescribe7).toString();
            mActTime = new StringBuilder().append(mTime1).append(mTime2).append(mTime3).append(mTime4).append(mTime5).append(mTime6).append(mTime7).toString();
        }else if (list.size()==8){
            mSite1 = list.get(0).getSite();
            mDescribe1 = list.get(0).getDescribe();
            mTime1 = list.get(0).getTime();
            mSite2 = list.get(1).getSite();
            mDescribe2 = list.get(1).getDescribe();
            mTime2 = list.get(1).getTime();
            mSite3 = list.get(2).getSite();
            mDescribe3 = list.get(2).getDescribe();
            mTime3 = list.get(2).getTime();
            mSite4 = list.get(3).getSite();
            mDescribe4 = list.get(3).getDescribe();
            mTime4 = list.get(3).getTime();
            mSite5 = list.get(4).getSite();
            mDescribe5 = list.get(4).getDescribe();
            mTime5 = list.get(4).getTime();
            mSite6 = list.get(5).getSite();
            mDescribe6 = list.get(5).getDescribe();
            mTime6 = list.get(5).getTime();
            mSite7 = list.get(6).getSite();
            mDescribe7 = list.get(6).getDescribe();
            mTime7 = list.get(6).getTime();
            mSite8 = list.get(7).getSite();
            mDescribe8 = list.get(7).getDescribe();
            mTime8 = list.get(7).getTime();
            mLocation = new StringBuilder().append(mSite1).append(mSite2).append(mSite3).append(mSite4).append(mSite5).append(mSite6).append(mSite7).append(mSite8).toString();
            mDescription = new StringBuilder().append(mDescribe1).append(mDescribe2).append(mDescribe3).append(mDescribe4).append(mDescribe5).append(mDescribe6).append(mDescribe7).append(mDescribe8).toString();
            mActTime = new StringBuilder().append(mTime1).append(mTime2).append(mTime3).append(mTime4).append(mTime5).append(mTime6).append(mTime7).append(mTime8).toString();
        }else if (list.size()==9){
            mSite1 = list.get(0).getSite();
            mDescribe1 = list.get(0).getDescribe();
            mTime1 = list.get(0).getTime();
            mSite2 = list.get(1).getSite();
            mDescribe2 = list.get(1).getDescribe();
            mTime2 = list.get(1).getTime();
            mSite3 = list.get(2).getSite();
            mDescribe3 = list.get(2).getDescribe();
            mTime3 = list.get(2).getTime();
            mSite4 = list.get(3).getSite();
            mDescribe4 = list.get(3).getDescribe();
            mTime4 = list.get(3).getTime();
            mSite5 = list.get(4).getSite();
            mDescribe5 = list.get(4).getDescribe();
            mTime5 = list.get(4).getTime();
            mSite6 = list.get(5).getSite();
            mDescribe6 = list.get(5).getDescribe();
            mTime6 = list.get(5).getTime();
            mSite7 = list.get(6).getSite();
            mDescribe7 = list.get(6).getDescribe();
            mTime7 = list.get(6).getTime();
            mSite8 = list.get(7).getSite();
            mDescribe8 = list.get(7).getDescribe();
            mTime8 = list.get(7).getTime();
            mSite9 = list.get(8).getSite();
            mDescribe9 = list.get(8).getDescribe();
            mTime9 = list.get(8).getTime();
            mLocation = new StringBuilder().append(mSite1).append(mSite2).append(mSite3).append(mSite4).append(mSite5).append(mSite6).append(mSite7).append(mSite8).append(mSite9).toString();
            mDescription = new StringBuilder().append(mDescribe1).append(mDescribe2).append(mDescribe3).append(mDescribe4).append(mDescribe5).append(mDescribe6).append(mDescribe7).append(mDescribe8).append(mDescribe9).toString();
            mActTime = new StringBuilder().append(mTime1).append(mTime2).append(mTime3).append(mTime4).append(mTime5).append(mTime6).append(mTime7).append(mTime8).append(mTime9).toString();
        }else if (list.size()==10){
            mSite1 = list.get(0).getSite();
            mDescribe1 = list.get(0).getDescribe();
            mTime1 = list.get(0).getTime();
            mSite2 = list.get(1).getSite();
            mDescribe2 = list.get(1).getDescribe();
            mTime2 = list.get(1).getTime();
            mSite3 = list.get(2).getSite();
            mDescribe3 = list.get(2).getDescribe();
            mTime3 = list.get(2).getTime();
            mSite4 = list.get(3).getSite();
            mDescribe4 = list.get(3).getDescribe();
            mTime4 = list.get(3).getTime();
            mSite5 = list.get(4).getSite();
            mDescribe5 = list.get(4).getDescribe();
            mTime5 = list.get(4).getTime();
            mSite6 = list.get(5).getSite();
            mDescribe6 = list.get(5).getDescribe();
            mTime6 = list.get(5).getTime();
            mSite7 = list.get(6).getSite();
            mDescribe7 = list.get(6).getDescribe();
            mTime7 = list.get(6).getTime();
            mSite8 = list.get(7).getSite();
            mDescribe8 = list.get(7).getDescribe();
            mTime8 = list.get(7).getTime();
            mSite9 = list.get(8).getSite();
            mDescribe9 = list.get(8).getDescribe();
            mTime9 = list.get(8).getTime();
            mSite10 = list.get(9).getSite();
            mDescribe10 = list.get(9).getDescribe();
            mTime10 = list.get(9).getTime();
            mLocation = new StringBuilder().append(mSite1).append(mSite2).append(mSite3).append(mSite4).append(mSite5).append(mSite6).append(mSite7).append(mSite8).append(mSite9).append(mSite10).toString();
            mDescription = new StringBuilder().append(mDescribe1).append(mDescribe2).append(mDescribe3).append(mDescribe4).append(mDescribe5).append(mDescribe6).append(mDescribe7).append(mDescribe8).append(mDescribe9).append(mDescribe10).toString();
            mActTime = new StringBuilder().append(mTime1).append(mTime2).append(mTime3).append(mTime4).append(mTime5).append(mTime6).append(mTime7).append(mTime8).append(mTime9).append(mTime10).toString();
        }
    }

    /**
     * 建立群聊输入名称弹框
     */
    public void samegroups() {
            //防止重复按按钮
            if (popupWindow != null && popupWindow.isShowing()) {
                return;
            }
            //设置PopupWindow的View
            View view = LayoutInflater.from(CustomApplication.context).inflate(R.layout.samegroups, null);
           EditText editname =  view.findViewById(R.id.edit_groupname);
           Button sure = view.findViewById(R.id.sure);
           ImageView close = view.findViewById(R.id.close);
            popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            //设置点击弹窗外隐藏自身
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            //设置动画
            popupWindow.setAnimationStyle(R.style.PopupWindow);
            //设置位置
            popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 10);

            //设置消失监听
//            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    popupWindow.dismiss();
//                    setBackgroundAlpha(activity, 1f);
//                }
//            });
            //关闭弹框
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sameGroup.setText("否");
                    popupWindow.dismiss();
                    setBackgroundAlpha(activity, 1f);
                }
            });
            sure.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    mGroupname = editname.getText().toString().trim();
                    popupWindow.dismiss();
                    setBackgroundAlpha(activity, 1f);
                }
            });
            //设置背景色
            setBackgroundAlpha(activity, 0.5f);
    }




    //设置屏幕背景透明效果
    public static void setBackgroundAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }




    /**
     * 发布页内容是否都填写完成
     */
    public void isaccomplish(){

        String startTimes = startTime.getText().toString().trim();
        String siteLocations = siteLocation.getText().toString().trim();
        String activityLabels = activityLabel.getText().toString().trim();
        String numLimitations = numLimitation.getText().toString().trim();
        String sameGroups = sameGroup.getText().toString().trim();
        String addWays = addWay.getText().toString().trim();
        String capitaCosts = capitaCost.getText().toString().trim();
        String editContents = editContent.getText().toString().trim();
        if (!TextUtils.isEmpty(startTimes) && !TextUtils.isEmpty(siteLocations) && !TextUtils.isEmpty(activityLabels) && !TextUtils.isEmpty(numLimitations)
                && !TextUtils.isEmpty(sameGroups) && !TextUtils.isEmpty(addWays) && !TextUtils.isEmpty(capitaCosts) && !TextUtils.isEmpty(editContents)
                && result!=null && list.size()!=0 && onclick==true){
            if (sameGroup.equals("是") && !TextUtils.isEmpty(mGroupname) ){
                merchantPublic.setBackgroundResource(R.drawable.surebutton_bg);
            }else if (sameGroup.equals("否")){
                merchantPublic.setBackgroundResource(R.drawable.surebutton_bg);
            }
        }else {
            Log.v(TAG,"---startTimes--"+startTimes);
            Log.v(TAG,"---siteLocations--"+siteLocations);
            Log.v(TAG,"---activityLabels--"+activityLabels);
            Log.v(TAG,"---numLimitations--"+numLimitations);
            Log.v(TAG,"---sameGroups--"+sameGroups);
            Log.v(TAG,"---addWays--"+addWays);
            Log.v(TAG,"---capitaCosts--"+capitaCosts);
            Log.v(TAG,"---editContents--"+editContents);
            Log.v(TAG,"---mGroupname--"+mGroupname);
            Log.v(TAG,"---result--"+result.size());
            Log.v(TAG,"---list--"+list.size());
            Log.v(TAG,"---onclick--"+onclick);
        }

    }

    /**
     * 是否建立群聊/申请加入方式按钮弹框
     */
    public  void groupandway(final Activity activity ) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(activity).inflate(R.layout.group_way, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 10);
//        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        //设置PopupWindow的View点击事件
        setPublicPin(activity, view);
        //设置背景色
        setBackgroundAlpha(activity, 0.5f);
    }

    private  void setPublicPin(Activity activity, View view) {

        TextView gw_one = view.findViewById(R.id.gw_one);
        TextView gw_two = view.findViewById(R.id.gw_two);
        TextView cancel = view.findViewById(R.id.cancel);
        gw_one.setText("是");
        gw_two.setText("否");
        gw_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sameGroup.setText("是");
                popupWindow.dismiss();
                samegroups();
                isaccomplish();
            }
        });
        //商家发布
        gw_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sameGroup.setText("否");
                popupWindow.dismiss();
                isaccomplish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }


}
