package com.spellingtrip.example.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.baidu.platform.comapi.map.A;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.cjt2325.cameralibrary.util.DeviceUtil;
import com.cjt2325.cameralibrary.util.FileUtil;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private JCameraView jCameraView;
    private int REQUEST_CODE_CHOOSE = 104;
    private List<MediaEntity> result = new ArrayList<>();
    private Bundle bundle;
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_publishcamera);
        EventBus.getDefault().register(this);
        jCameraView = (JCameraView) findViewById(R.id.jcameraview);
        //设置视频保存路径
         bundle = getIntent().getExtras();
        if (bundle != null) {
            String type = bundle.getString("type");
            if (!TextUtils.isEmpty(type) && type.equals(ConstantsBean.XIANGce)) {
                jCameraView.setIsPaiZhao(true);
                List<MediaEntity> result1 = bundle.getParcelableArrayList("list");
                if (result != null && result.size() > 0) {
                    result.clear();
                }
                if (result1 != null) {
                    result.addAll(result1);
                }
            } else {
                jCameraView.setIsPaiZhao(false);
            }
        } else {
            jCameraView.setIsPaiZhao(false);
        }
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_BOTH);
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Log.i("CJT", "camera error");
                Intent intent = new Intent();
                setResult(103, intent);
                finish();
            }

            @Override
            public void AudioPermissionError() {
                Toast.makeText(CameraActivity.this, "给点录音权限可以?", Toast.LENGTH_SHORT).show();
            }
        });
        //JCameraView监听
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                String path = FileUtil.saveBitmap("JCamera", bitmap);
                Intent intent = new Intent(CameraActivity.this, PublishTourismActivity.class);
                Bundle bundle = getIntent().getExtras();
                bundle.putString("title", path);
                bundle.putString("type", ConstantsBean.PAIZHAO);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                //获取视频路径
                String path = FileUtil.saveBitmap("JCamera", firstFrame);
                startActivity(new Intent(CameraActivity.this, PublishVideoActivity.class).putExtra("path", path).putExtra("url", url));
            }
        });

        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                CameraActivity.this.finish();
            }
        });
        jCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                setChooseImage();

            }
        });

    }

    private void setChooseImage() {
        if (result != null) {
            Phoenix.with()
                    .theme(PhoenixOption.THEME_DEFAULT)// 主题
                    .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                    .maxPickNumber(9)// 最大选择数量
                    .minPickNumber(0)// 最小选择数量
                    .spanCount(4)// 每行显示个数
                    .enablePreview(true)// 是否开启预览
                    .enableCamera(false)// 是否开启拍照
                    .enableAnimation(false)// 选择界面图片点击效果
                    .enableCompress(false)// 是否开启压缩
                    .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                    .compressVideoFilterSize(1024)//多少kb以下的视频不压缩
                    .thumbnailHeight(160)// 选择界面图片高度
                    .thumbnailWidth(160)// 选择界面图片宽度
                    .enableClickSound(false)// 是否开启点击声音
                    .pickedMediaList(result)// 已选图片数据
                    .videoFilterTime(120)//显示多少秒以内的视频
                    .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                    .start(CameraActivity.this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE_CHOOSE);
        } else {
            Phoenix.with()
                    .theme(PhoenixOption.THEME_DEFAULT)// 主题
                    .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                    .maxPickNumber(9)// 最大选择数量
                    .minPickNumber(0)// 最小选择数量
                    .spanCount(4)// 每行显示个数
                    .enablePreview(true)// 是否开启预览
                    .enableCamera(false)// 是否开启拍照
                    .enableAnimation(false)// 选择界面图片点击效果
                    .enableCompress(false)// 是否开启压缩
                    .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                    .compressVideoFilterSize(1024)//多少kb以下的视频不压缩
                    .thumbnailHeight(160)// 选择界面图片高度
                    .thumbnailWidth(160)// 选择界面图片宽度
                    .enableClickSound(false)// 是否开启点击声音
                    .videoFilterTime(120)//显示多少秒以内的视频
                    .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                    .start(CameraActivity.this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE_CHOOSE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.publish)) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            ArrayList<MediaEntity> result = (ArrayList<MediaEntity>) Phoenix.result(data);
            Intent intent = new Intent(CameraActivity.this, PublishTourismActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("list", result);
            bundle.putString("type", ConstantsBean.XIANGce);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bundle!=null){
            bundle.clear();
        }
    }
}
