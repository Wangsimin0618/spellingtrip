package com.spellingtrip.example.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.AllPublishActivity;
import com.spellingtrip.example.activity.BaiduMapActivity;
import com.spellingtrip.example.activity.PublishPinActivity;
import com.spellingtrip.example.activity.RenZhengActivity;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.FhBean;
import com.spellingtrip.example.bean.MessageEvent;
import com.spellingtrip.example.bean.PiPeiPublishBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.UpDataPhotoBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.IDCard;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 商家认证填写资料页
 */
public class InformationFragment extends BaseFragment {
    public static final String TAG = "InformationFragment";
    @BindView(R.id.txt_gr)
    TextView txtGr;
    @BindView(R.id.txt_sj)
    TextView txtSj;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_identity)
    EditText editIdentity;
    @BindView(R.id.edit_birthday)
    EditText editBirthday;
    @BindView(R.id.edit_phone1)
    EditText editPhone1;
    @BindView(R.id.edit_phone2)
    EditText editPhone2;
    @BindView(R.id.edit_emils)
    EditText editEmils;
    @BindView(R.id.edit_wx)
    EditText editWx;
    @BindView(R.id.img_rz)
    ImageView imgRz;
    @BindView(R.id.txt_yd)
    TextView txtYd;
    @BindView(R.id.txt_next)
    TextView txtNext;
    Unbinder unbinder;
    @BindView(R.id.certificate)
    TextView certificate;
    private Drawable mLeft;
    private Drawable mUnLeft;
    TimePickerView pvTime;

    private int grandsjonclick = 0;
    private int REQUEST_CODE_ZHENGCHOOSE = 502;
    private int RESULT_OK = -1;
    private LogineDialog logineDialog;
    private String mZhengid = "";
    private static PopupWindow popupWindow;
    private static boolean mYdclick = false;
    private UpDataPhotoBean mUpDataPhotoBean;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    protected void findView(View view) {
        editBirthday.setFocusable(false);
        mLeft = getResources().getDrawable(R.drawable.lxselect);
        mUnLeft = getResources().getDrawable(R.drawable.lxunselect);
        editPhone1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editPhone2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editIdentity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        //人均费用输入数字类型
        editIdentity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editPhone1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editPhone2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editWx.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.txt_gr, R.id.txt_sj, R.id.edit_birthday, R.id.img_rz, R.id.txt_yd, R.id.txt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_gr:
                grandsjonclick = 1;
                txtgr();

                break;
            case R.id.txt_sj:
                grandsjonclick = 2;
                txtsj();

                break;
            case R.id.edit_birthday:
                //关闭软键盘
                closeKeybord(getActivity());
                if (pvTime != null) {
                    pvTime.show(editBirthday);
                }
                //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
                //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
                Calendar selectedDate = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                startDate.set(2013, 0, 23);
                Calendar endDate = Calendar.getInstance();
                endDate.set(2029, 11, 28);
                //时间选择器
                pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                        editBirthday.setText(getTimes(date));
                    }
                })
                        //年月日时分秒 的显示与否，不设置则默认全部显示
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setLabel("年", "月", "日", "", "", "")
                        .isCenterLabel(true)
                        .setDividerColor(Color.DKGRAY)
                        .setContentSize(18)
                        .setDate(selectedDate)
                        .setRangDate(startDate, endDate)
                        .setDecorView(null)
                        .build();
                break;
            case R.id.img_rz:
                onaddMedia(REQUEST_CODE_ZHENGCHOOSE);
                break;
            case R.id.txt_yd:
                RZread(getActivity(),txtYd);
                break;
            case R.id.txt_next:
                String name = editName.getText().toString().trim();
                String Identity = editIdentity.getText().toString().trim();
                String Birthday = editBirthday.getText().toString().trim();
                String phone1 = editPhone1.getText().toString().trim();
                String phone2 = editPhone2.getText().toString().trim();
                String emils = editEmils.getText().toString().trim();
                String wx = editWx.getText().toString().trim();

                if (grandsjonclick !=1 && grandsjonclick !=2){
                    ToastUtil.show("请选择类型");
                    return;
                }
                try {
                    if (!IDCard.IDCardValidate(Identity)) {
                        ToastUtil.show("请输入正确的身份证号码");
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (!IDCard.isPhoneNumber(phone1) || !IDCard.isPhoneNumber(phone2)) {
                    ToastUtil.show("请输入正确的手机号码");
                    return;
                }
                if (phone1.equals(phone2)){
                    ToastUtil.show("手机号不能输入一样");
                    return;
                }
                if (!IDCard.isEmail(emils)){
                    ToastUtil.show("邮箱格式不正确");
                    return;
                }
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(Identity)|| TextUtils.isEmpty(Birthday) ||TextUtils.isEmpty(phone1) ||TextUtils.isEmpty(phone2)
                        || TextUtils.isEmpty(emils)  || TextUtils.isEmpty(wx)  || TextUtils.isEmpty(mZhengid)){
                    Log.v(TAG,"----name=="+name+"-------Identity=="+Identity+"-----phone1=="+phone1+"-------mZhengid=="+mZhengid);
                    ToastUtil.show("请填写全部内容");
                    return;
                }
                if (mYdclick == false){
                    ToastUtil.show("请阅读活动个人安全免责协议书");
                    return;
                }
                setRenZheng(name,Identity,Birthday,phone1,phone2,emils,wx);

                break;
        }
    }

    /**
     * 提交认证
     * @param name
     * @param identity
     * @param birthday
     * @param phone1
     * @param phone2
     * @param emils
     * @param wx
     */
    private void setRenZheng(String name, String identity, String birthday, String phone1, String phone2, String emils, String wx) {
        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        if (logineDialog==null){
            logineDialog=new LogineDialog(getActivity(),ConstantsBean.UPDATA);
        }
        logineDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userid);
            jsonObject.put("authType", grandsjonclick);
            jsonObject.put("realName", name);
            jsonObject.put("idcard", identity);
            jsonObject.put("tell", phone1);
            jsonObject.put("tell2", phone2);
            jsonObject.put("email", emils);
            jsonObject.put("wechat", wx);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .postString()
                .url(ConstantsBean.BASE_PATH + ConstantsBean.MERCHANTRZ)
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
                            FhBean fhBean= (FhBean) JsonUtil.fromJson(s, FhBean.class);
                            if (fhBean.getCode()==0){
                                ToastUtil.show("认证成功");
                                EventBus.getDefault().post(new MessageEvent("认证成功"));
                            }
                        }
                    }
                });
    }
    public void onaddMedia(int code) {
        Phoenix.with()
                .theme(PhoenixOption.THEME_DEFAULT)// 主题
                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(1)// 最大选择数量
                .minPickNumber(0)// 最小选择数量
                .spanCount(4)// 每行显示个数
                .enablePreview(true)// 是否开启预览
                .enableCamera(true)// 是否开启拍照
                .enableAnimation(false)// 选择界面图片点击效果
                .enableCompress(false)// 是否开启压缩
                .compressPictureFilterSize(300)//多少kb以下的图片不压缩
                .compressVideoFilterSize(2018)//多少kb以下的视频不压缩
                .thumbnailHeight(160)// 选择界面图片高度
                .thumbnailWidth(160)// 选择界面图片宽度
                .enableClickSound(false)// 是否开启点击声音
                .videoFilterTime(0)//显示多少秒以内的视频
                .mediaFilterSize(0)//显示多少kb以下的图片/视频，默认为0，表示不限制
                .start(this, PhoenixOption.TYPE_PICK_MEDIA, code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<MediaEntity> result = Phoenix.result(data);
        if (requestCode == REQUEST_CODE_ZHENGCHOOSE && resultCode == RESULT_OK) {
            addImage(result.get(0).getLocalPath(), REQUEST_CODE_ZHENGCHOOSE);
        }
    }
    /**
     * 上传照片
     *
     * @param path
     * @param code
     */
    private void addImage(String path, final int code) {
        if (logineDialog==null){
            logineDialog=new LogineDialog(getActivity(), ConstantsBean.UPDATA);
        }
        logineDialog.show();
        File tempFile = new File(path);
        String time= CommonUtil.getSysTime();
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPLOAD_PATH).addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .addFile("file", time+".png", tempFile)
                .build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show("数据解析失败");
                logineDialog.dismiss();
            }

            @Override
            public void onResponse(String s, int i) {
                logineDialog.dismiss();
                if (!TextUtils.isEmpty(s)) {
                    mUpDataPhotoBean = (UpDataPhotoBean) JsonUtil.fromJson(s, UpDataPhotoBean.class);
                    if (mUpDataPhotoBean.getCode() == 0) {
                       if (code == REQUEST_CODE_ZHENGCHOOSE) {
                           mZhengid = mUpDataPhotoBean.getData().getFid();
                           Glide.with(getActivity()).load(mUpDataPhotoBean.getData().getUrl()).into(imgRz);
                        }
                    } else {
                        ToastUtil.show(mUpDataPhotoBean.getMsg());
                    }
                }

            }
        });
    }
    private String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 点击个人变化状态
     */
    private void txtgr() {
        certificate.setText("上传身份证正面照");
        txtGr.setTextColor(getResources().getColor(R.color.hotseach));
        txtGr.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); //22SP
        txtGr.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        txtSj.setTextColor(getResources().getColor(R.color.buyvip));
        txtSj.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14); //22SP
        txtSj.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//不加粗
        txtGr.setCompoundDrawablesWithIntrinsicBounds(mLeft, null, null, null);
        txtSj.setCompoundDrawablesWithIntrinsicBounds(mUnLeft, null, null, null);
    }

    /**
     * 商家状态改变
     */
    private void txtsj() {
        certificate.setText("上传商家资质照片");
        txtSj.setTextColor(getResources().getColor(R.color.hotseach));
        txtSj.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); //22SP
        txtSj.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        txtGr.setTextColor(getResources().getColor(R.color.buyvip));
        txtGr.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14); //22SP
        txtGr.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//不加粗
        txtSj.setCompoundDrawablesWithIntrinsicBounds(mLeft, null, null, null);
        txtGr.setCompoundDrawablesWithIntrinsicBounds(mUnLeft, null, null, null);
    }

    /**
     * 同城发布阅读
     */
    public static void RZread(final Activity activity, TextView txtyd) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(CustomApplication.context).inflate(R.layout.rz_read, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 10);
        Button btnTy = view.findViewById(R.id.btn_ty);
        Button btnQx = view.findViewById(R.id.btn_qx);
        Drawable yd = activity.getResources().getDrawable(R.drawable.ydselect);
        Drawable ydun = activity.getResources().getDrawable(R.drawable.ydunselect);
        //同意
        btnTy.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                mYdclick = true;
                popupWindow.dismiss();
                txtyd.setCompoundDrawablesWithIntrinsicBounds(yd, null, null, null);
            }
        });
        //取消
        btnQx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mYdclick = false;
                popupWindow.dismiss();
                txtyd.setCompoundDrawablesWithIntrinsicBounds(ydun, null, null, null);

            }
        });
        //设置背景色
    }


    /**
     * 自动关闭软键盘
     * @param activity
     */
    public static void closeKeybord(Activity activity) {
        InputMethodManager imm =  (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
