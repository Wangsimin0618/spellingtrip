package com.spellingtrip.example.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;

import com.spellingtrip.example.utils.CameraUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class PublishActivity extends BaseActivity implements SurfaceHolder.Callback {
    @BindView(R.id.surfaceView)
    public SurfaceView mSurfaceview;
    @BindView(R.id.ivPublishBack)
    public ImageView ivPublishBack;
    @BindView(R.id.ivCarmar)
    public ImageView ivCarmar;
    @BindView(R.id.publish_paishe)
    public TextView publish_paishe;
    @BindView(R.id.ivPublishPhoto)
    public ImageView ivPublishPhoto;
    @BindView(R.id.llPandenPai)
    public LinearLayout llPandenPai;
    @BindView(R.id.tvPandenPai)
    public TextView tvPandenPai;
    @BindView(R.id.tvPhotoOk)
    public TextView tvPhotoOk;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private String TAG = "Carmra";
    private byte[] mdata;
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 9;
    private ArrayList<String> defaultDataArray=new ArrayList<>();
    private int REQUEST_CODE=102;
    private ArrayList<String> selected=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.view_publish;
    }

    @Override
    protected void initView() {
        mSurfaceHolder = mSurfaceview.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.ivPublishBack, R.id.publish_paishe, R.id.ivCarmar,R.id.ivPublishPhoto,R.id.tvPhotoOk,R.id.tvPandenPai})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ivPublishBack:
                finish();
                break;
            case R.id.publish_paishe:
                startTakephoto();
                break;
            case R.id.ivCarmar:
                CameraUtils.setCameraDisplayOrientation(this, Camera.open());
                break;
            case R.id.ivPublishPhoto:
                choosePhoto();
                break;
            case R.id.tvPandenPai:
                publish_paishe.setVisibility(View.VISIBLE);
                llPandenPai.setVisibility(View.GONE);
                ivCarmar.setVisibility(View.VISIBLE);
                refreshCamera();
                break;
            case R.id.tvPhotoOk:
                dealWithCameraData(mdata);

                break;
        }
    }

    /**
     * 选择照片
     */
    private void choosePhoto() {
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {



        }
    }
    private void showContent(Intent data) {


    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = getCameraInstance();
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera(); // 这一步是否多余？在以后复杂的使用场景下，此步骤是必须的。
        int rotation = getDisplayOrientation(); //获取当前窗口方向
        mCamera.setDisplayOrientation(rotation); //设定相机显示方向
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceHolder.removeCallback(this);
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            //停止预览
            mCamera.stopPreview();
            //释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }


    // === 以下是各种辅助函数 ===

    // 获取camera实例
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            Log.d("TAG", "camera is not available");
        }
        return c;
    }

    // 获取当前窗口管理器显示方向
    private int getDisplayOrientation() {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int rotation = display.getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        android.hardware.Camera.CameraInfo camInfo =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, camInfo);

        // 这里其实还是不太懂：为什么要获取camInfo的方向呢？相当于相机标定？？
        int result = (camInfo.orientation - degrees + 360) % 360;

        return result;
    }

    // 刷新相机
    private void refreshCamera() {
        if (mSurfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
        }
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {

        }
    }

    @Override
    // apk暂停时执行的动作：把相机关闭，避免占用导致其他应用无法使用相机
    protected void onPause() {
        super.onPause();
        if (mCamera!=null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

    }

    @Override
    // 恢复apk时执行的动作
    protected void onResume() {
        super.onResume();
        if (null != mCamera) {
            mCamera = getCameraInstance();
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d(TAG, "Error setting camera preview: " + e.getMessage());
            }
        }else {
            initView();
        }
    }


    private void startTakephoto() {
        //获取到相机参数
        Camera.Parameters parameters = mCamera.getParameters();
        //设置图片保存格式
        parameters.setPictureFormat(ImageFormat.JPEG);
        //设置图片大小
        parameters.setPreviewSize(480, 720);
        //设置对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        //设置自动对焦
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    mCamera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            mdata=data;
                            publish_paishe.setVisibility(View.GONE);
                            ivCarmar.setVisibility(View.GONE);
                            llPandenPai.setVisibility(View.VISIBLE);
                           // dealWithCameraData(data);
                        }
                    });
                }
            }
        });
    }

    //保存拍照数据
    private void dealWithCameraData(byte[] data) {

      /*  String fileName = System.currentTimeMillis() + ".jpg";
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(data);
            Intent intent = new Intent(PublishActivity.this, PublishTourismActivity.class);
            intent.putExtra("path", fileName);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
    /**
     * 单张图片上传
     * @param path
     */
    private void updataPhoto(File path) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPLOAD_PATH).addFile("file","",path )
                .build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show("数据解析失败");
                Log.e("onError", JsonUtil.toJson(e));

            }

            @Override
            public void onResponse(String s, int i) {
                Log.e("updataPhoto",JsonUtil.toJson(s));
              /*  Intent intent = new Intent(PublishActivity.this, PublishTourismActivity.class);
                intent.putExtra("path", tempFile);
                startActivity(intent);
                finish();*/
            }
        });
    }
}
