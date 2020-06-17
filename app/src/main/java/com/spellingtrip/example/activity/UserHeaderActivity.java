package com.spellingtrip.example.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.UpDataPhotoBean;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UserHeaderActivity extends BaseActivity {
    @BindView(R.id.tvChoosePhoto)
    public ImageView tvChoosePhoto;
    @BindView(R.id.ivUserHeaderUrl)
    public ImageView ivUserHeaderUrl;
    private static final int PHOTO=302;
    public static final int CODE_RESULT_REQUEST=303;
    private Uri uritempFile;
    private LogineDialog logineDialog;
    private Uri cropUri;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_userheader;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.tvChoosePhoto)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvChoosePhoto:
                getPhotoPrams();
                break;
        }

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
       /* Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");*/
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        startActivityForResult(intent, 1);
    }

    @Override
    protected void getData() {

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
                case 1:
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        cropRawPhoto(uri);
                    }

                    break;
                case CODE_RESULT_REQUEST:
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Uri crop = data.getData();
                        //在这里获得了剪裁后的Bitmap对象，可以用于上传
                        Bitmap photo = data.getExtras().getParcelable("data");
                       // File file = new File(CommonUtil.saveFile(UserHeaderActivity.this,CommonUtil.getFilePath(UserHeaderActivity.this,
                              //  cropUri),"header.png",photo));
                        File file=CommonUtil.getAbsoluteImagePath(photo);
                        uploadImageFile(file);

                    }

                    break;
                default:
                    break;
            }
        }
    }


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
        intent.putExtra("return-data", true);
        //设置剪切的图片保存位置
         cropUri = Uri.fromFile(new File(
                Environment.getExternalStorageDirectory().getPath() + "crop.png"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT,cropUri);
        startActivityForResult(intent, CODE_RESULT_REQUEST);

    }

    private void uploadImageFile(File file) {
        String time=CommonUtil.getSysTime();
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPLOAD_PATH).addFile("file", time+".png",file)
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
                      setHeader(upDataPhotoBean.getData().getFid());
                    } else {
                        ToastUtil.show(upDataPhotoBean.getMsg());
                    }
                }

            }
        });

    }

    private void setHeader(String fid) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPDATAINFO + UserTask.getInstance().getUser().getUserId())
                .addParams("fid", fid).build().readTimeOut(50000).execute(new StringCallback() {
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
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.USERINFO));
                        finish();
                    } else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }

    @Override
    protected void setData() {
        backClick();
        String url = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(url)) {
            Glide.with(this).load(url).into(ivUserHeaderUrl);
        }
    }
}
