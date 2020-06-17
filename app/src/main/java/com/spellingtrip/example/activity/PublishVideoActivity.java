package com.spellingtrip.example.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.cjt2325.cameralibrary.CameraInterface;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.state.CameraMachine;
import com.cjt2325.cameralibrary.util.LogUtil;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.iceteck.silicompressorr.VideoCompress;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.PublishBean;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import okhttp3.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PublishVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlPublishBottom;
    private TextView tvPublishRight, tvChongpai, tvVideoPublishLocation;
    private JCVideoPlayer mVideoView;
    private float screenProp = 0f;
    private MediaPlayer mMediaPlayer;
    private CameraMachine machine;
    private EditText etVideoContent;
    private LogineDialog logineDialog;
    private int CITY_CODE = 102;
    private String outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_publishvideo);
        mVideoView = findViewById(R.id.publishVideoView);
        rlPublishBottom = findViewById(R.id.rlPublishBottom);
        tvChongpai = findViewById(R.id.tvChongpai);
        tvVideoPublishLocation = findViewById(R.id.tvVideoPublishLocation);
        tvPublishRight = findViewById(R.id.tvPublishRight);
        etVideoContent = findViewById(R.id.etVideoContent);
        String path = getIntent().getStringExtra("path");
        String url = getIntent().getStringExtra("url");
        Bitmap bitmap = CommonUtil.getVideoMap(new File(path));
        mVideoView.setUp(url, "", "", bitmap);
        mVideoView.setYinCang(true);
        tvVideoPublishLocation.setOnClickListener(this);
        tvChongpai.setOnClickListener(this);
        tvPublishRight.setOnClickListener(this);
        if (logineDialog == null) {
            logineDialog = new LogineDialog(this, "正在发布");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvChongpai:
                finish();
                break;
            case R.id.tvPublishRight:
                String content = etVideoContent.getText().toString().trim();
                String path = getIntent().getStringExtra("path");
                String url = getIntent().getStringExtra("url");
                String location = tvVideoPublishLocation.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.show("请填写标题内容");
                } else if (TextUtils.isEmpty(location)) {
                    ToastUtil.show("请选择位置");
                } else {
                    updataPhoto(url, content, location, "3");
                }

                break;
            case R.id.tvVideoPublishLocation:
                setRequestedPress();
                break;
        }
    }

    private Locale getLocale() {
        Configuration config = getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }
        return sysLocale;
    }
    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config){
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config){
        return config.getLocales().get(0);
    }
    private void updataPhoto(String url, String content, String location, String type) {
        if (logineDialog != null) {
            logineDialog.show();
        }
        final String destPath = outputDir + File.separator + "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss", getLocale()).format(new Date()) + ".mp4";
        VideoCompress.compressVideoLow(url,destPath, new VideoCompress.CompressListener() {
            @Override
            public void onStart() {
            }
            @Override
            public void onSuccess() {
               File file=new File(destPath);
                setUpdataFile(file, content, location, "3");
            }
            @Override
            public void onFail() {
            }
            @Override
            public void onProgress(float percent) {

            }
        });
    /*   File file=new File(url);
        setUpdataFile(file, content, location, "3");
        Luban.with(PublishVideoActivity.this)
                .load(url)
                .ignoreBy(100)
               .setTargetDir(PublishVideoActivity.this.getExternalCacheDir().getPath())
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        setUpdataFile(file, content, location, "3");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();*/


    }

    private void setUpdataFile(File file,String content, String location, String type) {
        String time=CommonUtil.getSysTime();
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPLOAD_PATH).addFile("file", time+".mp4", file)
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
                        onPublish(upDataPhotoBean.getData().getFid()
                                , type, content, location);

                    } else {
                        ToastUtil.show(upDataPhotoBean.getMsg());
                    }
                }
            }
        });
    }

    private void onPublish(String fid, String type, String content, String location) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.PUBLISH).addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .addParams("travelType", type).addParams("content", content).addParams("city", location).addParams("coverVideo", "")
                .addParams("fid", fid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog != null) {
                    logineDialog.dismiss();
                }
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (logineDialog != null) {
                    logineDialog.dismiss();
                }
                if (!TextUtils.isEmpty(s)) {
                    PublishBean publishBean = (PublishBean) JsonUtil.fromJson(s, PublishBean.class);
                    if (publishBean.getCode() == 0) {
                        ToastUtil.show("发布成功");
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.publish));
                        finish();
                    } else {
                        ToastUtil.show("发布失败");
                    }
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CITY_CODE && resultCode == RESULT_OK) {
            tvVideoPublishLocation.setText(data.getStringExtra("city") + "");
        }
    }

    /**
     * 判断请求权限
     */
    private void setRequestedPress() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (Build.VERSION.SDK_INT >= 23) {
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            if (check == PackageManager.PERMISSION_GRANTED) {
                //写入你需要权限才能使用的方法
                jumpActivity();
            } else {
                //手动去请求用户打开权限(可以在数组中添加多个权限) 1 为请求码 一般设置为final静态变量
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            //写入你需要权限才能使用的方法
            jumpActivity();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CITY_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            jumpActivity();
        } else {
            Toast.makeText(this, "请打开定位权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void jumpActivity() {
        startActivityForResult(new Intent(this, GaoDeLoactionActivity.class), CITY_CODE);
    }
}
