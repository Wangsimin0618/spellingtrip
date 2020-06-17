package com.spellingtrip.example.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.spellingtrip.example.MainActivity;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.UpDataPhotoBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ShapedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.spellingtrip.example.activity.UserHeaderActivity.CODE_RESULT_REQUEST;

public class NewUserEditInfoActivity extends BaseActivity {
    private static final int PHOTO = 1002;
    private static final int CODE_REQUEST = 1003;
    @BindView(R.id.tvEditUserInfoNext)
    public TextView tvEditUserInfoNext;
    @BindView(R.id.etEditUserInfoNick)
    public EditText etEditUserInfoNick;
    @BindView(R.id.tvEditUserInfoSex)
    public TextView tvEditUserInfoSex;
    @BindView(R.id.tvEditUserInfoBirth)
    public TextView tvEditUserInfoBirth;
    @BindView(R.id.ivEditUserInfoChooseHeader)
    public ShapedImageView ivEditUserInfoChooseHeader;
    private PopupWindow popupWindow;
    private String sex;
    private String fid;

    @Override
    protected int getLayoutId() {
        return R.layout.actctivity_edituserinfo;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
    }

    @OnClick({R.id.ivEditUserInfoChooseHeader, R.id.tvEditUserInfoSex, R.id.tvEditUserInfoBirth, R.id.tvEditUserInfoNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivEditUserInfoChooseHeader:
                getPhotoPrams();
                break;
            case R.id.tvEditUserInfoSex:
                openPopupWindow(tvEditUserInfoSex);
                break;
            case R.id.tvEditUserInfoBirth:
                setDirthDay();
                break;
            case R.id.tvEditUserInfoNext:
                String nick= etEditUserInfoNick.getText().toString().trim();
                String birth=tvEditUserInfoBirth.getText().toString().trim();
                String sex=tvEditUserInfoSex.getText().toString().trim();
                if (TextUtils.isEmpty(fid)){
                    ToastUtil.show("请选择头像");
                }else if (TextUtils.isEmpty(nick)){
                    ToastUtil.show("请填写昵称");
                }else if (sex.contains("请选择性别")){
                    ToastUtil.show("请选择性别");
                }else if (birth.contains("请选择生日")){
                    ToastUtil.show("请选择生日");
                }else {
                    setUserInfo(fid,sex,birth,nick);
                }
                break;
        }
    }

    private void setUserInfo(String fid, String sex, String birth, String nick) {
        int userid=0;
        if (UserTask.getInstance().getUser()!=null){
            userid= UserTask.getInstance().getUser().getUserId();
        }
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPDATAINFO + userid).addParams("fid",fid)
                .addParams("nick", nick).addParams("birthday",birth).addParams("sex",sex)
                .build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }
            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {
                        //ToastUtil.show(reMoveBean.getMsg());
                        ActivityUtils.skipActivity(NewUserEditInfoActivity.this, MainActivity.class,0,"");
                        finish();
                    } else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }

    private void getPhotoPrams() {
        if (CommonUtil.setPhoto(this)) {
            CommonUtil.setRequsePhoto(this, PHOTO);
        } else {
            takePhoto();
        }
    }

    /**
     * 相册
     */
    private void takePhoto() {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        startActivityForResult(intent, PHOTO);
//       Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PHOTO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    ToastUtil.show("设置权限");
                }
                break;
            case 102:

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (!CommonUtil.isSDAvailable()) {
                return;
            }
            switch (requestCode) {
                case PHOTO:
                    Uri uri = data.getData();
                    cropRawPhoto(uri);
                    break;
                case CODE_REQUEST:
//                    Bundle bundle = data.getExtras();
//                    if (bundle != null) {
//                        Uri crop = data.getData();
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
//                          Bitmap photo = data.getExtras().getParcelable("data");
//                        // File file = new File(CommonUtil.saveFile(UserHeaderActivity.this,CommonUtil.getFilePath(UserHeaderActivity.this,cropUri),"header.png",photo));
                        File file=CommonUtil.getAbsoluteImagePath(bitmap);
                        uploadImageFile(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                    }
                    break;
                default:
                    break;
            }
        }
    }
    private Uri uritempFile;
    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故只保存图片Uri，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         * // intent.putExtra("return-data", true);
         */
        // 上述方法中，裁剪后的图片通过Intent的putExtra("return-data",true)方法进行传递，miui系统问题就出在这里，return-data的方式只适用于小图，
        // miui系统默认的裁剪图片可能裁剪得过大，或对return-data分配的资源不足，造成return-data失败。
        // 解决思路是：裁剪后，intent保存图片的资源路径Uri，在onActivityResult()方法中，再提取对应的Uri图片资源转换为Bitmap使用。
        // 裁剪后的图片Uri路径，uritempFile为Uri类变量
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CODE_REQUEST);
    }

    private void uploadImageFile(File file) {
        String time = CommonUtil.getSysTime();
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPLOAD_PATH).addFile("file", time + ".png", file)
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    UpDataPhotoBean upDataPhotoBean = (UpDataPhotoBean) JsonUtil.fromJson(s, UpDataPhotoBean.class);
                    if (upDataPhotoBean.getCode() == 0) {
                        String url = upDataPhotoBean.getData().getUrl();
                        fid=upDataPhotoBean.getData().getFid();
                        Glide.with(NewUserEditInfoActivity.this).load(url).into(ivEditUserInfoChooseHeader);
                    } else {
                        ToastUtil.show(upDataPhotoBean.getMsg());
                    }
                }

            }
        });

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
                tvEditUserInfoBirth.setText(CommonUtil.getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "").isCenterLabel(false)
                .setRangDate(startDate, endDate).setBackgroundId(0x50333333)
                .setSubmitColor(R.color.textSelColor)//确定按钮文字颜色
                .setCancelColor(R.color.logineNor_text)//取消按钮文字颜色
                .setDecorView(null).build();
        pvTime.show();
    }
    /**
     * 底部弹出框
     */
    @SuppressLint("WrongConstant")
    public  void openPopupWindow(final TextView textView) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.popview_setsex, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        TextView tvSetSexCannel=view.findViewById(R.id.tvSetSexCannel);
        TextView tvSetSexYes=view.findViewById(R.id.tvSetSexYes);
        TextView tvChooseNv=view.findViewById(R.id.tvChooseNv);
        TextView tvChooseBoy=view.findViewById(R.id.tvChooseBoy);
        tvSetSexCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        tvSetSexYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(sex)){
                    textView.setText(sex+"");
                    popupWindow.dismiss();
                    setBackgroundAlpha(activity, 1f);
                }
            }
        });
        tvChooseNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex="女";
                tvChooseNv.setTextColor(getResources().getColor(R.color.text68));
                tvChooseBoy.setTextColor(getResources().getColor(R.color.history_text));
            }
        });
        tvChooseBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvChooseBoy.setTextColor(getResources().getColor(R.color.text68));
                tvChooseNv.setTextColor(getResources().getColor(R.color.history_text));
                sex="男";
            }
        });
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        // popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(textView, Gravity.BOTTOM, 0, 10);
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        //设置PopupWindow的View点击事件
        //setOnPopupViewClick(activity,view,userid);
        //设置背景色
        setBackgroundAlpha(activity, 0.5f);
    }
    //设置屏幕背景透明效果
    public static void setBackgroundAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }
}
