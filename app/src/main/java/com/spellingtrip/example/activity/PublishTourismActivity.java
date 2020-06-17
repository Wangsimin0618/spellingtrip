package com.spellingtrip.example.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.PublishBean;
import com.spellingtrip.example.bean.UpDataPhotoBean;
import com.spellingtrip.example.city.CityPickerActivity;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.phoenix.MediaAdapter;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class PublishTourismActivity extends BaseActivity implements MediaAdapter.OnAddMediaListener {
    private static final int CITY_CODE = 105;
    @BindView(R.id.etPublishContent)
    public EditText etPublishContent;
    @BindView(R.id.gvPublishImage)
    public RecyclerView recyclerView;
    @BindView(R.id.tvPublishLocation)
    public TextView tvPublishLocation;
    @BindView(R.id.tvPublishTourism)
    public TextView tvPublishTourism;
    private int REQUEST_CODE_CHOOSE = 101;
    private MediaAdapter mMediaAdapter;
    private List<MediaEntity> result = new ArrayList<>();
    private LogineDialog logineDialog;
    private StringBuffer stringBuffer;
    private List<String>lists=new ArrayList<>();
    private String type="";
    private static final int GET_LOCATION=502;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publishtourism;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        type=bundle.getString("type");
        if (!TextUtils.isEmpty(type)&&type.equals(ConstantsBean.XIANGce)) {//判断上层点击那个类型 进入此页面  并携带的参数   相册
           List<MediaEntity> xresult = bundle.getParcelableArrayList("list");
           result.addAll(xresult);
        }else if (!TextUtils.isEmpty(type)&&type.equals(ConstantsBean.PAIZHAO)){//判断上层点击那个类型 进入此页面  并携带的参数  拍照
            String path = getIntent().getStringExtra("title");
            MediaEntity entity = new MediaEntity();
            entity.setLocalPath(path);
            entity.setMimeType("image/png");
            result.add(entity);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        type=bundle.getString("type");
        if (!TextUtils.isEmpty(type)&&type.equals(ConstantsBean.XIANGce)) {//判断上层点击那个类型 进入此页面  并携带的参数   相册
            List<MediaEntity> xresult = bundle.getParcelableArrayList("list");
            if (result!=null&&result.size()>0){
                result.clear();
            }
            result.addAll(xresult);
            if (mMediaAdapter!=null){
                mMediaAdapter.setData(result);
                mMediaAdapter.notifyDataSetChanged();
            }
        }else if (!TextUtils.isEmpty(type)&&type.equals(ConstantsBean.PAIZHAO)){//判断上层点击那个类型 进入此页面  并携带的参数  拍照
            String path = intent.getStringExtra("title");
            MediaEntity entity = new MediaEntity();
            entity.setLocalPath(path);
            entity.setMimeType("image/png");
            if (mMediaAdapter!=null){
                mMediaAdapter.getData().add(entity);
                mMediaAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void getData() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        mMediaAdapter = new MediaAdapter(this,PublishTourismActivity.this);
        recyclerView.setAdapter(mMediaAdapter);
        mMediaAdapter.setData(result);
    }

    /**
     * 图片上传
     *
     * @param path
     */
    private void updataPhoto(List<MediaEntity> path, final String content, final String type,String location) {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(this, ConstantsBean.FABU);
        }
        logineDialog.show();
        stringBuffer = new StringBuffer();
        List<String>paths=new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            paths.add(path.get(i).getLocalPath());
        }
        if (lists!=null&&lists.size()>0){
            lists.clear();
        }
        Luban.with(this)
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
                        setUpdataFile(file,content,type,location,path.size());

                    }
                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();

    }

    private void setUpdataFile(File file,String content,String type,String location,int size) {
       String time= CommonUtil.getSysTime();
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPLOAD_PATH).addFile("file", time+".png", file)
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog!=null&&logineDialog.isShowing()){
                    logineDialog.dismiss();
                }
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    UpDataPhotoBean upDataPhotoBean = (UpDataPhotoBean) JsonUtil.fromJson(s, UpDataPhotoBean.class);
                    if (upDataPhotoBean.getCode() == 0&&upDataPhotoBean.getData().isSuccess()) {
                        lists.add(upDataPhotoBean.getData().getFid());
                        if (lists.size()==size){
                            setPublish(content, type,lists ,location);
                        }
                        stringBuffer.append(upDataPhotoBean.getData().getFid() + ",");

                    } else {
                        ToastUtil.show(upDataPhotoBean.getMsg());
                    }
                }
            }
        });
    }

    @OnClick({R.id.tvPublishLocation, R.id.tvPublishTourism})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvPublishLocation:
                getPerssim();
                break;
            case R.id.tvPublishTourism:
                String content = etPublishContent.getText().toString().trim();
                String location = tvPublishLocation.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.show("请填写标题内容");
                } else if (TextUtils.isEmpty(location)||location.contains("选择位置")) {
                    ToastUtil.show("请选择位置");
                } else if (mMediaAdapter!=null && mMediaAdapter.getData().size() > 0) {
                    if (logineDialog == null) {
                        logineDialog = new LogineDialog(this, ConstantsBean.FABU);
                    }
                    if (!logineDialog.isShowing()) {
                        logineDialog.show();
                    }
                    if (mMediaAdapter!=null){
                        updataPhoto(mMediaAdapter.getData(), content, "2",location);
                    }

                } else if (!TextUtils.isEmpty(content)){
                    ToastUtil.show("请添加图片");
                }
                break;
        }
    }

    private void getPerssim() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (Build.VERSION.SDK_INT >= 23) {
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            if (check == PackageManager.PERMISSION_GRANTED) {
                //写入你需要权限才能使用的方法
                jumpActivity();
            } else {
                //手动去请求用户打开权限(可以在数组中添加多个权限) 1 为请求码 一般设置为final静态变量
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, GET_LOCATION);
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
        if (requestCode == GET_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            jumpActivity();
        } else {
            Toast.makeText(this, "请打开定位权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void jumpActivity() {
        startActivityForResult(new Intent(this, GaoDeLoactionActivity.class), CITY_CODE);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            //返回的数据
            result = Phoenix.result(data);
            mMediaAdapter.setData(result);
        }
        if (requestCode == CITY_CODE && resultCode == RESULT_OK) {
            tvPublishLocation.setText(data.getStringExtra("city") + "");
        }
    }


    /**
     * 发布游圈请求逻辑
     *
     * @param content
     */
    private void setPublish(String content, String type, List<String> fid, String city) {
     String fids=JsonUtil.toJson(fid).replace("[","").replace("]","")
             .replace("\"","");
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.PUBLISH)
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .addParams("travelType", type).addParams("content", content).addParams("address", city)
                .addParams("coverVideo", "").addParams("fid", fids)
                .build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                if (!TextUtils.isEmpty(s)) {
                    PublishBean publishBean = (PublishBean) JsonUtil.fromJson(s, PublishBean.class);
                    if (publishBean.getCode() == 0) {
                        ToastUtil.show("发布成功");
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.publish));
                        finish();
                    } else {
                        Log.e("发布失败",publishBean.getMsg()+"/"+publishBean.getCode());
                        ToastUtil.show("发布失败");
                    }
                }
            }
        });
    }


    public void onBack(View view) {
        finish();
    }


    @Override
    public void onaddMedia() {
        if (mMediaAdapter!=null&&mMediaAdapter.getData().size()>0&&!mMediaAdapter.getData().get(0).getLocalPath().contains(".mp4")){
            Intent intent=new Intent(PublishTourismActivity.this,CameraActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("list",(ArrayList<? extends Parcelable>) mMediaAdapter.getData());
            bundle.putString("type",ConstantsBean.XIANGce);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if (mMediaAdapter!=null&&mMediaAdapter.getData().size()>0){
            Intent intent=new Intent(PublishTourismActivity.this,CameraActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) mMediaAdapter.getData());
            bundle.putString("type",ConstantsBean.VIDEO);
            intent.putExtras(bundle);
            startActivity(intent);
        }else {
            Intent intent=new Intent(PublishTourismActivity.this,CameraActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("type","nomal");
            intent.putExtras(bundle);
            startActivity(intent);
        }

       /* Phoenix.with()
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
                .pickedMediaList(mMediaAdapter.getData())// 已选图片数据
                .videoFilterTime(120)//显示多少秒以内的视频
                .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                .start(this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE_CHOOSE);*/
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
