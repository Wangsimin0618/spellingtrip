package com.spellingtrip.example.activity;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
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
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class RenZhengActivity extends BaseActivity {
    @BindView(R.id.tvRenFinish)
    public TextView tvRenFinish;
    @BindView(R.id.etShenNumber)
    public EditText etShenNumber;
    @BindView(R.id.etName)
    public EditText etName;
    @BindView(R.id.ivFanPhoto)
    public ImageView ivFanPhoto;
    @BindView(R.id.ivZhengPhoto)
    public ImageView ivZhengPhoto;
    @BindView(R.id.tvShening)
    public TextView tvShening;
    @BindView(R.id.llAddRenZheng)
    public LinearLayout llAddRenZheng;
    private int REQUEST_CODE_FANCHOOSE = 501;
    private int REQUEST_CODE_ZHENGCHOOSE = 502;
    private String fanid = "";
    private String zhengid="";
    private LogineDialog logineDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_renzheng;
    }

    @Override
    protected void initView() {
        etShenNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    @Override
    protected void getData() {

    }

    @OnClick({R.id.tvRenFinish, R.id.ivZhengPhoto, R.id.ivFanPhoto})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRenFinish:
                String number = etShenNumber.getText().toString().trim();
                String name = etName.getText().toString().trim();
                if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(name)&&!TextUtils.isEmpty(fanid)&&!TextUtils.isEmpty(zhengid)) {
                    setRenZheng(number,name);
                } else if (TextUtils.isEmpty(number)) {
                    ToastUtil.show("请输入身份证号");
                } else if (TextUtils.isEmpty(name)) {
                    ToastUtil.show("请输入姓名");
                } else if (TextUtils.isEmpty(zhengid)) {
                    ToastUtil.show("请上传身份证正面照片");
                }else if (TextUtils.isEmpty(fanid)) {
                    ToastUtil.show("请上传身份证反面照片");
                }
                break;
            case R.id.ivFanPhoto:
                onaddMedia(REQUEST_CODE_FANCHOOSE);
                break;
            case R.id.ivZhengPhoto:
                onaddMedia(REQUEST_CODE_ZHENGCHOOSE);
                break;

        }

    }

    /**
     * 提交认证
     * @param number
     * @param name
     */
    private void setRenZheng(String number, String name) {
        if (logineDialog==null){
            logineDialog=new LogineDialog(this,ConstantsBean.UPDATA);
        }
        logineDialog.show();
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.RENZHENG).addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .addParams("realName",name).addParams("idcardNumber",number).addParams("frontImage",zhengid)
                .addParams("reverseImage",fanid).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
                logineDialog.dismiss();
            }

            @Override
            public void onResponse(String s, int i) {
                logineDialog.dismiss();
                if (!TextUtils.isEmpty(s)){
                    ReMoveBean reMoveBean= (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode()==0){
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.USERINFO));
                        ToastUtil.show(reMoveBean.getMsg());
                        tvRenFinish.setVisibility(View.GONE);
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },1000);

                    }else {
                        tvRenFinish.setVisibility(View.VISIBLE);
                        ToastUtil.show(reMoveBean.getMsg());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<MediaEntity> result = Phoenix.result(data);
        if (requestCode == REQUEST_CODE_FANCHOOSE && resultCode == RESULT_OK) {
            addImage(result.get(0).getLocalPath(), REQUEST_CODE_FANCHOOSE);
        } else if (requestCode == REQUEST_CODE_ZHENGCHOOSE && resultCode == RESULT_OK) {
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
            logineDialog=new LogineDialog(this,ConstantsBean.UPDATA);
        }
        logineDialog.show();
        File tempFile = new File(path);
        String time=CommonUtil.getSysTime();
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
                    UpDataPhotoBean upDataPhotoBean = (UpDataPhotoBean) JsonUtil.fromJson(s, UpDataPhotoBean.class);
                    if (upDataPhotoBean.getCode() == 0) {
                        if (code == REQUEST_CODE_FANCHOOSE) {
                            fanid = upDataPhotoBean.getData().getFid();
                            Glide.with(RenZhengActivity.this).load(upDataPhotoBean.getData().getUrl()).into(ivFanPhoto);
                        } else if (code == REQUEST_CODE_ZHENGCHOOSE) {
                            zhengid = upDataPhotoBean.getData().getFid();
                            Glide.with(RenZhengActivity.this).load(upDataPhotoBean.getData().getUrl()).into(ivZhengPhoto);
                        }
                    } else {
                        ToastUtil.show(upDataPhotoBean.getMsg());
                    }
                }

            }
        });
    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("实名认证");
        if (UserTask.getInstance().getUserInfoData()!=null&&UserTask.getInstance().getUserInfoData().isIdcardAuth()){
            tvShening.setVisibility(View.VISIBLE);
            llAddRenZheng.setVisibility(View.GONE);
        }else {
            tvShening.setVisibility(View.GONE);
            llAddRenZheng.setVisibility(View.VISIBLE);
        }
    }
}
