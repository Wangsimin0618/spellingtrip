package com.spellingtrip.example.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.PiPeiPublishBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.UpDataPhotoBean;
import com.spellingtrip.example.city.CityPickerActivity;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.dialog.OnLableButtonClick;
import com.spellingtrip.example.picker.picker.OptionPicker;
import com.spellingtrip.example.picker.widget.WheelView;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.AddAdapter;
import com.spellingtrip.example.viewpager.PinMediaAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PublishPinActivity extends BaseActivity implements PinMediaAdapter.OnAddMediaListener {
    public static final String TAG = "PublishPinActivity";
    @BindView(R.id.tvChooseStartTime)
    public EditText tvChooseStartTime;
    @BindView(R.id.tvStartLoaction)
    public EditText tvStartLoaction;
    @BindView(R.id.tvEndLoaction)
    public EditText tvEndLoaction;
    @BindView(R.id.ivTOPiPei)
    public ImageView ivTOPiPei;
    @BindView(R.id.etToPinDesc)
    public EditText etToPinDesc;
    @BindView(R.id.llTopPin)
    public LinearLayout llTopPin;
    @BindView(R.id.pinRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.gvPinLables)
    public RecyclerView gvPinLables;
    @BindView(R.id.ivAddPublishLable)
    public ImageView ivAddPublishLable;
    @BindView(R.id.rlChooseStartTime)
    public RelativeLayout rlChooseStartTime;
    @BindView(R.id.rlStartLoaction)
    public RelativeLayout rlStartLoaction;
    @BindView(R.id.rlEndLoaction)
    public RelativeLayout rlEndLoaction;
    @BindView(R.id.addLable)
    RelativeLayout addLable;
    private int STRAT_CITY_CODE = 101;
    private int SEND_CITY_CODE = 102;
    private int STRAT_TIME_CODE = 103;
    private String startime, mStartime, mendtime;
    private String endtime;
    private LogineDialog logineDialog;
    private InputMethodManager inputManager;
    private PinMediaAdapter mMediaAdapter;
    private List<MediaEntity> result = new ArrayList<>();
    private static final int CHOOSE_IMG = 1003;
    @BindView(R.id.tvPublishChooseNumber)
    public TextView tvPublishChooseNumber;
    private ArrayList<String> lists = new ArrayList<>();
    private List<String> lables = new ArrayList<>();
    private AddAdapter addAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publishpin;
    }

    @Override
    protected void initView() {
        tvChooseStartTime.setFocusable(false);
        tvEndLoaction.setFocusable(false);
        tvStartLoaction.setFocusable(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        LinearLayoutManager mPerfectCourse = new LinearLayoutManager(PublishPinActivity.this);
        mPerfectCourse.setOrientation(LinearLayoutManager.HORIZONTAL);
        gvPinLables.setLayoutManager(mPerfectCourse);
        gvPinLables.addItemDecoration(new SpacesItemDecoration(20));
        mMediaAdapter = new PinMediaAdapter(this, PublishPinActivity.this, tvPublishChooseNumber);
        recyclerView.setAdapter(mMediaAdapter);
        mMediaAdapter.setData(result);
    }

    @Override
    protected void getData() {
    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("填写结伴信息");
    }

    @OnClick({R.id.tvChooseStartTime, R.id.tvStartLoaction, R.id.addLable,
            R.id.ivTOPiPei, R.id.tvEndLoaction})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tvChooseStartTime:
                startActivityForResult(new Intent(PublishPinActivity.this, ChooseTimeActivity.class), STRAT_TIME_CODE);
                break;
            case R.id.tvStartLoaction:
                startActivityForResult(new Intent(PublishPinActivity.this, CityPickerActivity.class), STRAT_CITY_CODE);
                break;
            case R.id.tvEndLoaction:
                startActivityForResult(new Intent(PublishPinActivity.this, CityPickerActivity.class), SEND_CITY_CODE);
                break;
            case R.id.ivTOPiPei://发布计划
                String startcity = tvStartLoaction.getText().toString().trim();
                String endcity = tvEndLoaction.getText().toString().trim();
                String startime = tvChooseStartTime.getText().toString().trim();
                String desc = etToPinDesc.getText().toString().trim();
                if (TextUtils.isEmpty(startcity) || startcity.equals("添加出发地")) {
                    ToastUtil.show("添加出发地");
                } else if (TextUtils.isEmpty(endcity) || endcity.equals("添加目的地")) {
                    ToastUtil.show("添加目的地");
                } else if (TextUtils.isEmpty(startime) || TextUtils.isEmpty(mStartime) || TextUtils.isEmpty(mendtime)) {
                    ToastUtil.show("请选择时间");
                } else if (TextUtils.isEmpty(desc)) {
                    ToastUtil.show("请填写活动描述");
                } else if (desc.length() < 15) {
                    ToastUtil.show("活动描述至少15个字");
                } else if (result == null || result.size() == 0) {
                    ToastUtil.show("请选择图片");
                } else if (lables == null || lables.size() == 0) {
                    ToastUtil.show("请添加标签");
                } else {
                    CommonDialog.getTiShiDialog(PublishPinActivity.this, ConstantsBean.PINYOUTS, new OnButtonClick() {
                        @Override
                        public void button01ClickListener() {
                            setUpLoadImg(startcity, endcity, mStartime, mendtime, desc);
                        }

                        @Override
                        public void button02ClickListener() {

                        }
                    });
                }

                break;
            case R.id.llTopPin:
                break;
            case R.id.addLable:
                setAddlable();
                break;

        }

    }

    /**
     * 添加标签
     */
    private void setAddlable() {
        CommonDialog.getAddLableDialog(PublishPinActivity.this, new OnLableButtonClick() {
            @Override
            public void button01ClickListener() {

            }

            @Override
            public void button02ClickListener(String lable) {
                lables.add(lable);
                if (addAdapter == null) {
                    addAdapter = new AddAdapter(PublishPinActivity.this);
                    addAdapter.setData(lables);
                    gvPinLables.setAdapter(addAdapter);
                } else {
                    addAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    /**
     * 上传图片
     *
     * @param startcity
     * @param endcity
     * @param startime
     * @param endTime
     * @param desc
     */
    private void setUpLoadImg(String startcity, String endcity, String startime, String endTime, String desc) {
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
        Luban.with(PublishPinActivity.this)
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
                        setUpdataFile(file, startcity, endcity, startime, endTime, desc);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    //发布计划请求
    private void setUpdataFile(File file, String startcity, String endcity, String startime, String endTime, String desc) {
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
                                    setPin(startcity, endcity, startime, endTime, desc, lists);
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
     *
     * @param
     * @param startcity
     * @param endcity
     * @param lists
     */
    private void setPin(String startcity, String endcity, String startime, String endtime, String desc, ArrayList<String> lists) {
        //String startTime=startime.replace("年","-").replace("月","-").replace("日","");
        // String endTime=endtime.replace("年","-").replace("月","-").replace("日","");
        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        JSONArray array = new JSONArray();
        for (int i = 0; i < lists.size(); i++) {
            array.put(lists.get(i));
        }
        JSONArray labs = new JSONArray();
        if (lables.size() > 0) {
            for (int a = 0; a < lables.size(); a++) {
                labs.put(lables.get(a));
            }
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fromArea", startcity);
            jsonObject.put("toArea", endcity);
            jsonObject.put("userId", userid);
            jsonObject.put("fidList", array);
            jsonObject.put("labelList", labs);
            jsonObject.put("startDate", startime);
            jsonObject.put("endDate", endtime);
            jsonObject.put("description", desc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .postString()
                .url(ConstantsBean.BASE_PATH + ConstantsBean.PINYOU)
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
                            if (tops.contains("请完善性别")) {
                                ToastUtil.show("请完善性别");
                            } else if (tops.contains("接口异常")) {
                                ToastUtil.show("发布失败");
                            } else {
                                PiPeiPublishBean piPeiBean = (PiPeiPublishBean) JsonUtil.fromJson(s, PiPeiPublishBean.class);
                                if (piPeiBean.getCode() == 0) {
                                    EventBus.getDefault().post(new EventType(ConstantsBean.AUTOPI));
                                    EventBus.getDefault().post(new SendMessageData(ConstantsBean.AUTOPI));
                                    ActivityUtils.skipActivity(PublishPinActivity.this, AllPublishActivity.class, 0, "");
                                    finish();
                                } else if (piPeiBean.getMsg().contains("性别") || piPeiBean.getMsg().contains("接口")) {
                                    // setContent();
                                } else {
                                    if (piPeiBean.getData() != null && piPeiBean.getData().isTourLimit()) {
                                        CommonDialog.getJIeBAnDialog(activity, ConstantsBean.FABUTISHI);
                                    } else {
                                        ToastUtil.show(piPeiBean.getMsg());
                                    }

                                }
                            }
                        }
                    }
                });
    }

    private void setDialog(final int objId) {
        CommonDialog.getDialog(PublishPinActivity.this, "提示", ConstantsBean.PIPEITEXT,
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
        CommonDialog.getDialog(PublishPinActivity.this, "提示", "请完善个人信息",
                "否", "是", new OnButtonClick() {
                    @Override
                    public void button01ClickListener() {
                    }

                    @Override
                    public void button02ClickListener() {
                        ActivityUtils.skipActivity(PublishPinActivity.this, UserInfoActivity.class, 0, "");
                    }
                });
    }

    /**
     * 后台挂起
     *
     * @param objId
     */
    private void setAuto(int objId) {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(this, "");
        }
        logineDialog.show();
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.PYAUTO).addParams("pinId", String.valueOf(objId))
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId())).build().readTimeOut(50000).execute(new StringCallback() {
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
                        ActivityUtils.skipActivity(PublishPinActivity.this, AllPublishActivity.class, 0, "");
                        finish();
                    } else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STRAT_CITY_CODE && resultCode == RESULT_OK) {
            tvStartLoaction.setText(data.getStringExtra("city") + "");
        }
        if (requestCode == SEND_CITY_CODE && resultCode == RESULT_OK) {
            tvEndLoaction.setText(data.getStringExtra("city") + "");
        }
        if (requestCode == STRAT_TIME_CODE && resultCode == RESULT_OK) {
            startime = data.getStringExtra("startime");
            endtime = data.getStringExtra("endtime");
            mStartime = startime.replace("年", "-").replace("月", "-").replace("日", "");
            mendtime = endtime.replace("年", "-").replace("月", "-").replace("日", "");
            long day = CommonUtil.getDayCha(mStartime, mendtime);
            tvChooseStartTime.setText(mStartime + " - " + mendtime);
        }
        if (requestCode == CHOOSE_IMG && resultCode == RESULT_OK) {
            //返回的数据
            // result = Matisse.obtainPathResult(data);
            result = Phoenix.result(data);
            mMediaAdapter.setData(result);
            if (result.size() > 0) {
                tvPublishChooseNumber.setText(result.size() + "/9");
            } else {
                tvPublishChooseNumber.setText("0/9");
            }
        }
    }

    @Override
    public void onaddMedia() {
        Phoenix.with()
                .theme(PhoenixOption.THEME_DEFAULT)// 主题
                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(9)// 最大选择数量
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
                .start(PublishPinActivity.this, PhoenixOption.TYPE_PICK_MEDIA, CHOOSE_IMG);
    }

    private void setChooseData(final TextView mTextView, String[] contents) {
        OptionPicker picker = new OptionPicker(this, contents);
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
    protected void onDestroy() {
        super.onDestroy();
        if (lists != null && lists.size() > 0) {
            lists.clear();
        }
        if (lables != null && lables.size() > 0) {
            lables.clear();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
