package com.spellingtrip.example.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.UpDataPhotoBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.bean.UserShowBean;
import com.spellingtrip.example.dialog.CityDetailInfoImgFragment;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.MyLayoutManager;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.CardLablesAdapter;
import com.spellingtrip.example.viewpager.CardMediaAdapter;
import com.spellingtrip.example.viewpager.MyCardLablesAdapter;
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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 照片墙
 */
public class MyInfoCardActivity extends BaseActivity implements CardMediaAdapter.OnAddMediaListener, CardMediaAdapter.OnItemClickListener {
    private PopupWindow popupWindow;
    @BindView(R.id.photoRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.ivCardPhotoNick)
    public TextView ivCardPhotoNick;
    @BindView(R.id.ivCardPhotoQianName)
    public TextView ivCardPhotoQianName;
    private CardMediaAdapter mMediaAdapter;
    @BindView(R.id.rvCardLiveLables)
    public RecyclerView rvCardLiveLables;
    @BindView(R.id.rlCardLive)
    public RelativeLayout rlCardLive;
    @BindView(R.id.rlCardInfoQianName)
    public RelativeLayout rlCardInfoQianName;
    @BindView(R.id.rlCardInfoNick)
    public RelativeLayout rlCardInfoNick;
    private static final int CHOOSE_IMG = 2003;
    private List<MediaEntity> result = new ArrayList<>();
    private LogineDialog logineDialog;
    private ArrayList<String> httpPaths = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.actctivity_infocardphoto;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SpacesItemDecoration(16));
    }

    @Override
    protected void getData() {
        if (UserTask.getInstance().getUserInfoData()!=null&&UserTask.getInstance().getUserInfoData().getBgImages()!= null
                && UserTask.getInstance().getUserInfoData().getBgImages().size() > 0) {
            mMediaAdapter = new CardMediaAdapter(this, MyInfoCardActivity.this);
            recyclerView.setAdapter(mMediaAdapter);
            getImages();
        } else {
            mMediaAdapter = new CardMediaAdapter(this, MyInfoCardActivity.this);
            recyclerView.setAdapter(mMediaAdapter);
        }
        mMediaAdapter.setOnItemClickListener(this);
        setUserNick();
    }

    private void setUserNick() {
        if (UserTask.getInstance().getUserInfoData() != null) {
            ivCardPhotoNick.setText(UserTask.getInstance().getUserInfoData().getNick());
            if (TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getDescription())) {
                ivCardPhotoQianName.setText("未设置个性签名");
            } else {
                ivCardPhotoQianName.setText(UserTask.getInstance().getUserInfoData().getDescription());
            }
           List<UserInfoDataBean.LabelsBean> labelsBean= PreferenceUtil.getLabels(ConstantsBean.Spell,ConstantsBean.LABELS);
            if (labelsBean!=null&&labelsBean.size()>0){
                MyLayoutManager layout = new MyLayoutManager();
                layout.setAutoMeasureEnabled(true);
                if (rvCardLiveLables.getItemDecorationCount() == 0) {
                    rvCardLiveLables.addItemDecoration(new SpacesItemDecoration(14));
                }
                rvCardLiveLables.setLayoutManager(layout);
                rvCardLiveLables.setHasFixedSize(true);
                rvCardLiveLables.setNestedScrollingEnabled(false);
                 MyCardLablesAdapter adapter = new MyCardLablesAdapter(activity, labelsBean);
                rvCardLiveLables.setAdapter(adapter);
            }

        }

    }

    private void getImages() {
        if (httpPaths.size() > 0) {
            httpPaths.clear();
        }
        List<String> images = UserTask.getInstance().getUserInfoData().getBgImages();
        httpPaths.addAll(images);
        mMediaAdapter.setData(httpPaths);
    }

    @OnClick({R.id.rlCardInfoNick, R.id.rlCardInfoQianName,R.id.rlCardLive})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlCardInfoNick:
                ActivityUtils.skipActivity(this,EditInfoActivity.class,0,"设置昵称");
                break;
            case R.id.rlCardInfoQianName:
                ActivityUtils.skipActivity(this,EditInfoActivity.class,0,"设置签名");
                break;
            case R.id.rlCardLive:
                ActivityUtils.skipActivity(this, SetLabelActivity.class, 0, "");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.GETUSER)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setUserNick();
                }
            },500);

        }
    }
    @Override
    protected void setData() {
        backClick();
        setCenterTitle("个人资料");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMG && resultCode == RESULT_OK) {
            //返回的数据
            // result = Matisse.obtainPathResult(data);
            result = Phoenix.result(data);
            // mMediaAdapter.setData(result);
            uploadPhoto(result);
        }
    }

    private void uploadPhoto(List<MediaEntity> result) {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(MyInfoCardActivity.this, "正在更新");
        }
        logineDialog.show();
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            if (!result.get(i).getFinalPath().contains("http")) {
                paths.add(result.get(i).getFinalPath());
            } else {
                httpPaths.add(result.get(i).getFinalPath());
            }
        }
        Luban.with(MyInfoCardActivity.this)
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
                        setUpdataFile(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();

    }

    private void setUpdataFile(File file) {
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
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    UpDataPhotoBean upDataPhotoBean = (UpDataPhotoBean) JsonUtil.fromJson(s, UpDataPhotoBean.class);
                    if (upDataPhotoBean.getCode() == 0 && upDataPhotoBean.getData().isSuccess()) {
                        //lists.add(upDataPhotoBean.getData().getUrl());
                        // titles= Arrays.copyOf(titles, titles.length+1);
                        httpPaths.add(upDataPhotoBean.getData().getUrl());
                        if (httpPaths.size() == httpPaths.size()) {
                            setCardImages(httpPaths);
                        }
                    } else {
                        ToastUtil.show(upDataPhotoBean.getMsg());
                    }
                }
            }
        });

    }

    private void setCardImages(ArrayList<String> lists) {
        JSONArray bgImages = new JSONArray();
        if (lists.size() > 0) {
            for (int a = 0; a < lists.size(); a++) {
                bgImages.put(lists.get(a));
            }
        }
        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userid);
            jsonObject.put("bgImages", bgImages);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpUtils
                .postString()
                .url(ConstantsBean.BASE_PATH + ConstantsBean.SETIMAGE)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        if (logineDialog != null && logineDialog.isShowing()) {
                            logineDialog.dismiss();
                        }
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (logineDialog != null && logineDialog.isShowing()) {
                            logineDialog.dismiss();
                        }
                        if (!TextUtils.isEmpty(s)) {
                            ReMoveBean moveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                            if (moveBean.getCode() == 0) {
                                EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.USERINFO));
                                mMediaAdapter.setData(lists);
                            } else {

                            }
                        }
                    }
                });
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
                // .pickedMediaList(mMediaAdapter.getData())// 已选图片数据
                .videoFilterTime(120)//显示多少秒以内的视频
                .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                .start(MyInfoCardActivity.this, PhoenixOption.TYPE_PICK_MEDIA, CHOOSE_IMG);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (httpPaths != null && httpPaths.size() > 0) {
            httpPaths.clear();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(final int position, View v) {
        openPopupWindow(MyInfoCardActivity.this, v, position);
    }

    /**
     * 底部弹出框
     */
    public void openPopupWindow(final Activity activity, View v, int position) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(activity).inflate(R.layout.view_cardpopupwindow, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 10);
        //设置消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        setOnPopupViewClick(MyInfoCardActivity.this, view, position);
        //设置背景色
        setBackgroundAlpha(activity, 0.5f);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }

    private void setOnPopupViewClick(Activity activity, View view, final int position) {
        TextView tv_pick_look, tv_pick_detail, tv_cancel_card;
        tv_pick_look = view.findViewById(R.id.tv_pick_look);
        tv_pick_detail = view.findViewById(R.id.tv_pick_detail);
        tv_cancel_card = view.findViewById(R.id.tv_cancel_card);
        tv_pick_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityDetailInfoImgFragment fragment = new CityDetailInfoImgFragment();
                fragment.setData(httpPaths, position);
                fragment.show(activity.getFragmentManager(), "DialogFragment");
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        tv_pick_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != RecyclerView.NO_POSITION) {
                    httpPaths.remove(position);
                    setCardImages(httpPaths);
                } else {

                }
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
        tv_cancel_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setBackgroundAlpha(activity, 1f);
            }
        });
    }

}
