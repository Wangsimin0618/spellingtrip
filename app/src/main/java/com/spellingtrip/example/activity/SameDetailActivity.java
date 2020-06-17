package com.spellingtrip.example.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.EaseConstant;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.SameDetailBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.dialog.SamePublicDialog;
import com.spellingtrip.example.huanxin.ChatActivity;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.utils.Util;
import com.spellingtrip.example.utils.WeiBoConstants;
import com.spellingtrip.example.view.ObservableScrollView;
import com.spellingtrip.example.view.ScrollGridView;
import com.spellingtrip.example.view.ShapedImageView;
import com.spellingtrip.example.viewpager.SameDetsilFlowAdapter;
import com.spellingtrip.example.viewpager.SameDetsilLikeAdapter;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 同城活动详情页
 */
public class SameDetailActivity extends BaseActivity implements WbShareCallback {
    public static final String TAG = "SameDetailActivity";
    private static final String TYPE_WECHAT = "session";
    private static final String TYPE_WECHAT_MOMENT = "timeline";
    public static final String KEY_SHARE_TYPE = "key_share_type";

    @BindView(R.id.samebanner)
    Banner samebanner;
    @BindView(R.id.samerg)
    RadioGroup samerg;
    @BindView(R.id.relayout)
    RelativeLayout relayout;
    @BindView(R.id.same_detailHeader)
    ShapedImageView sameDetailHeader;
    @BindView(R.id.same_dateilsex)
    ImageView sameDateilsex;
    @BindView(R.id.same_head)
    RelativeLayout sameHead;
    @BindView(R.id.same_detailNick)
    TextView sameDetailNick;
    @BindView(R.id.same_detailvip)
    ImageView sameDetailvip;
    @BindView(R.id.same_detailage)
    TextView sameDetailage;
    @BindView(R.id.same_detail_address)
    TextView sameDetailAddress;
    @BindView(R.id.same_detailnavigation)
    TextView sameDetailnavigation;
    @BindView(R.id.same_detailTime)
    TextView sameDetailTime;
    @BindView(R.id.same_detailcapita)
    TextView sameDetailcapita;
    @BindView(R.id.same_detailtype)
    TextView sameDetailtype;
    @BindView(R.id.same_detailContent)
    TextView sameDetailContent;
    @BindView(R.id.same_detail_flow)
    RecyclerView sameDetailFlow;
    @BindView(R.id.llJieBanLive)
    LinearLayout llJieBanLive;
    @BindView(R.id.same_detailsaddnums)
    TextView sameDetailsaddnums;
    @BindView(R.id.same_add)
    TextView sameAdd;
    @BindView(R.id.same_private)
    TextView samePrivate;
    @BindView(R.id.same_share)
    TextView sameShare;
    @BindView(R.id.sameLeft)
    ImageView sameLeft;
    @BindView(R.id.sameNick)
    TextView sameNick;
    @BindView(R.id.sameMore)
    ImageView sameMore;
    @BindView(R.id.samebannerbg)
    ImageView samebannerbg;
    @BindView(R.id.same_detaillikenum)
    TextView sameDetaillikenum;
    @BindView(R.id.same_detail_like)
    ScrollGridView sameDetailLike;
    @BindView(R.id.top_title)
    RelativeLayout topTitle;
    @BindView(R.id.sc)
    ObservableScrollView sc;
    private LogineDialog logineDialog;
    private int page = 1;
    List<String> images = new ArrayList<>();
    private PopupWindow popupWindow;
    private IWXAPI api;
    private SameDetailBean mSameDetailBean;
    public static final int SHARE_CLIENT = 1;
    private int mShareType = SHARE_CLIENT;
    private WbShareHandler shareHandler;
    private Tencent mTencent;
    private IUiListener mIUiListener;
    private String mUserId;
    private String mActivityId;
    private int imageHeight;
    private String mLatitude1;
    private String mLongtitude1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_samedetail;
    }

    @Override
    protected void initView() {
        SharedPreferences sp = getSharedPreferences("lat_log", Context.MODE_PRIVATE);
        Boolean value = sp.contains("latitude");
        if (value){
            mLatitude1 = sp.getString("latitude","latitude");
            mLongtitude1 = sp.getString("longitude","longitude");
            Log.v(TAG,"--------latitude=="+ mLatitude1 +"--------longitude=="+ mLongtitude1);

        }
        Intent intent = getIntent();
        mUserId = intent.getStringExtra("userId");
        mActivityId = intent.getStringExtra("activityId");
        Log.v(TAG,"-------------------mUserId=="+mUserId+"------mActivityId=="+mActivityId);
        initListeners();

    }

    @Override
    protected void getData() {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(SameDetailActivity.this, "正在加载");
        }
        logineDialog.show();
        getSameDetail(mUserId, mActivityId);
        getSamelike();
    }


    /**
     * 同城活动详情
     *
     * @param activityid
     */
    private void getSameDetail(String userid, String activityid) {
        Log.v(TAG, "---------getSameDetail------userid==" + userid + "-------activityid==" + activityid);
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.SAMEDETAIL).addParams("userId", userid).addParams("activityId", activityid)
                .build().connTimeOut(10000).readTimeOut(50000).writeTimeOut(10000).execute(new StringCallback() {


            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
                if (logineDialog != null && logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
            }

            @Override
            public void onResponse(String s, int i) {
                if (logineDialog != null && logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
                mSameDetailBean = (SameDetailBean) JsonUtil.fromJson(s, SameDetailBean.class);
                if (mSameDetailBean.getCode() == 0 && mSameDetailBean.getData() != null) {
                    setDetailData(mSameDetailBean.getData());
                }
            }
        });
    }

    /**
     * 设置同城结伴详情
     *
     * @param data
     */
    private void setDetailData(SameDetailBean.DataBean data) {
        String imgurl = data.getHeadUrl() + "?x-oss-process=style/64_64";
        if (!SameDetailActivity.this.isFinishing()) {
            Glide.with(SameDetailActivity.this).load(imgurl).into(sameDetailHeader);
        }
        if (data.getPayType() == 1) {
            //月vip
            sameDetailvip.setVisibility(View.VISIBLE);
            sameDetailvip.setImageDrawable(getResources().getDrawable(R.drawable.vip));
            sameDetailNick.setTextColor(getResources().getColor(R.color.image_selector_red));
        } else if (data.getPayType() == 2) {
            //季vip
            sameDetailvip.setVisibility(View.VISIBLE);
            sameDetailvip.setImageDrawable(getResources().getDrawable(R.drawable.vip));
            sameDetailNick.setTextColor(getResources().getColor(R.color.image_selector_red));
        } else if (data.getPayType() == 3) {
            //年vip
            sameDetailvip.setVisibility(View.VISIBLE);
            sameDetailvip.setImageDrawable(getResources().getDrawable(R.drawable.vipyear));
            sameDetailNick.setTextColor(getResources().getColor(R.color.image_selector_red));
        } else {
            //无vip
            sameDetailvip.setVisibility(View.GONE);
            sameDetailNick.setTextColor(getResources().getColor(R.color.history_text));
        }
        sameDetailNick.setText(data.getNick() + "");
        sameDetailAddress.setText(data.getLocation() + "");
        sameDetailTime.setText(data.getDatetime() + "");
        sameDetailcapita.setText("￥ " + data.getCost());
        sameDetailtype.setText("#" + data.getTypeName());
        sameDetailContent.setText(data.getContent() + "");
        sameDetaillikenum.setText("已加入人数." + data.getMembers().size() + "");
        //轮播
        String headUrl = data.getHeadUrl() + "?x-oss-process=style/320_320";
        if (data.getCoverImage() != null && data.getCoverImage().size() > 0) {
            samebanner.setVisibility(View.VISIBLE);
            samerg.setVisibility(View.VISIBLE);
            samebannerbg.setVisibility(View.GONE);
            setBanner(data.getCoverImage());
        } else {
            samebanner.setVisibility(View.GONE);
            samerg.setVisibility(View.GONE);
            samebannerbg.setVisibility(View.VISIBLE);
            Glide.with(SameDetailActivity.this).load(headUrl).into(samebannerbg);
        }

        if (data.getMembers() != null && data.getMembers().size() > 0) {
            setLikes(data.getMembers());
        }


        //活动流程
        if (data.getMembers() != null && data.getMembers().size() > 0) {
            setFlow(data.getMembers());
        }


    }

    /**
     * 活动流程
     *
     * @param members
     */
    private void setFlow(List<SameDetailBean.DataBean.MembersBean> members) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 1);
        //设置布局管理器为线性布局管理器
        sameDetailFlow.setLayoutManager(layoutManager);
        SameDetsilFlowAdapter flowAdapter = new SameDetsilFlowAdapter(this, members);
        sameDetailFlow.setAdapter(flowAdapter);
    }

    /**
     * 喜欢列表
     *
     * @param members
     */
    private void setLikes(List<SameDetailBean.DataBean.MembersBean> members) {
        SameDetsilLikeAdapter likeAdapter = new SameDetsilLikeAdapter(this, members);
        sameDetailLike.setAdapter(likeAdapter);

    }

    /**
     * 商家结伴顶部轮播
     *
     * @param coverimage
     */
    private void setBanner(List<String> coverimage) {
        images.clear();
        for (int i = 0; i < coverimage.size(); i++) {
            images.add(coverimage.get(i));
            Log.e(TAG, "members" + coverimage);
        }
        for (int i = 0; i < images.size(); i++) {
            RadioButton radioButton = new RadioButton(SameDetailActivity.this);
            radioButton.setBackgroundResource(R.drawable.banner_sell);
            radioButton.setButtonDrawable(null);
            samerg.addView(radioButton);
        }
        samebanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        samebanner.setImageLoader(new UsershowImageLoader());
        //设置图片集合
        samebanner.setImages(images);
        Log.e(TAG, "-----images---" + images);
        samebanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e(TAG, "---setOnBannerListener--position---" + position);
            }
        });

        samebanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                samerg.check(position + 1);
                for (int i = 0; i < samerg.getChildCount(); i++) {
                    View childAt = samerg.getChildAt(i);
                    LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)
                            childAt.getLayoutParams(); //取控件textView当前的布局参数
                    linearParams.height = dip2px(3);
                    linearParams.width = dip2px(4);
                    linearParams.setMargins(dip2px(2), dip2px(2), dip2px(2), dip2px(2));
                    childAt.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
                }
                View childAt = samerg.getChildAt(position);
                if (childAt != null) {
                    LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)
                            childAt.getLayoutParams(); //取控件textView当前的布局参数
                    linearParams.width = dip2px(11);
                    childAt.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        samebanner.start();


        samebanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                DetailImgFragment fragment = new DetailImgFragment();
//                fragment.setData(members, position);
//                fragment.show(SameDetailActivity.this.getFragmentManager(), "DialogFragment");
            }
        });
    }


    public class UsershowImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (!SameDetailActivity.this.isFinishing()) {
                Glide.with(context).load(path).into(imageView);
            }
        }
    }

    /**
     * dp转px
     * 16dp - 48px
     * 17dp - 51px
     */
    public static int dip2px(float dpValue) {
        float scale = CustomApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) ((dpValue * scale) + 0.5f);
    }


    /**
     * 喜欢人数
     */
    private void getSamelike() {

    }


    @Override
    protected void setData() {

    }

    @OnClick({R.id.sameLeft, R.id.sameMore, R.id.same_add, R.id.same_private, R.id.same_share, R.id.same_detailnavigation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sameLeft:
                //左侧返回
                finish();
                break;
            case R.id.sameMore:
                //右侧更多
                 CommonDialog.homeitemPopupWindow(activity, view,1, 0);
                break;
            case R.id.same_detailnavigation:
                //导航
                SamePublicDialog.publiNg(SameDetailActivity.this,mLatitude1, mLongtitude1, mSameDetailBean.getData().getLocation());
                break;
            case R.id.same_add:
                //申请加入
                break;
            case R.id.same_private:
                //私聊
                if (UserTask.getInstance() != null && UserTask.getInstance().getUser() != null) {
                    String uuid = UserTask.getInstance().getUser().getImUsername();
                    if (mSameDetailBean.getData() != null && !uuid.equals(mSameDetailBean.getData().getCreatorImUsername())) {
//                        if (UserTask.getInstance().getUserInfoData().isVip()) {
                            activity.startActivity(new Intent(activity, ChatActivity.class)
                                    .putExtra(EaseConstant.EXTRA_USER_ID, mSameDetailBean.getData().getCreatorImUsername())
                                    .putExtra("nick", mSameDetailBean.getData().getNick()));
//                        } else {
//                            CommonDialog.getTiShiDialog(SameDetailActivity.this, ConstantsBean.CHATJIEBAN, new OnButtonClick() {
//                                @Override
//                                public void button01ClickListener() {
//                                    ActivityUtils.skipActivity(SameDetailActivity.this, BuyVIPActivity.class, 0, "");
//                                }
//
//                                @Override
//                                public void button02ClickListener() {
//
//                                }
//                            });
//                        }
                    } else {
                        ToastUtil.show("去找别人聊聊吧");
                    }
                } else {
                    ActivityUtils.skipActivity(SameDetailActivity.this, LogineActivity.class, 0, "");
                }

                break;
            case R.id.same_share:
                //分享
                openPopupWindow(sameShare);
                break;
        }
    }

    /**
     * 判断手机中是否有导航app
     *
     * @param activity
     * @param packagename 包名
     */
    public static boolean isNavigationApk(Activity activity, String packagename) {
        List<PackageInfo> packages = activity.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equals(packagename)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    /**
     * 底部弹出框
     */
    @SuppressLint("WrongConstant")
    public void openPopupWindow(final TextView v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.popview_share, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        TextView tvShareTime = view.findViewById(R.id.tvShareTime);
        TextView tvShareSeen = view.findViewById(R.id.tvShareSeen);
        TextView tvShareWeiBo = view.findViewById(R.id.tvShareWeiBo);
        TextView tvShareZQne = view.findViewById(R.id.tvShareZQne);
        TextView tvShareQQ = view.findViewById(R.id.tvShareQQ);
        //微信好友分享
        tvShareSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShare(TYPE_WECHAT);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        //朋友圈分享
        tvShareTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShare(TYPE_WECHAT_MOMENT);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        //微博分享
        tvShareWeiBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWeiShare();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        //QQ分享
        tvShareQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQQShare("QQ");
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        //QQ空间分享
        tvShareZQne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQQShare("QQZQne");
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 10);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                setBackgroundAlpha(SameDetailActivity.this, 1f);
            }
        });
        setBackgroundAlpha(SameDetailActivity.this, 0.9f);
    }

    //设置屏幕背景透明效果
    public static void setBackgroundAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * wx分享
     *
     * @param type
     */
    private void setShare(String type) {
        api = WXAPIFactory.createWXAPI(this, ConstantsBean.APP_ID);
        api.registerApp(ConstantsBean.APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ConstantsBean.PAGE_URL;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if (mSameDetailBean != null && mSameDetailBean.getData() != null) {
            msg.title = mSameDetailBean.getData().getContent();
            msg.description = mSameDetailBean.getData().getContent();
        }
        Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.drawable.desktop_icon);
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        //根据type设置分享情景
        switch (type) {
            case TYPE_WECHAT:
                //分享到微信好友
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case TYPE_WECHAT_MOMENT:
                //分享到微信朋友圈
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
        }
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    //微博
    private void setWeiShare() {
        WbSdk.install(SameDetailActivity.this, new AuthInfo(SameDetailActivity.this,
                WeiBoConstants.APP_KEY, WeiBoConstants.REDIRECT_URL, WeiBoConstants.SCOPE));
        mShareType = getIntent().getIntExtra(KEY_SHARE_TYPE, SHARE_CLIENT);
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
        shareHandler.setProgressColor(0xff33b5e5);
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.mediaObject = getWebpageObj();
        shareHandler.shareMessage(weiboMessage, mShareType == SHARE_CLIENT);
    }

    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        if (mSameDetailBean != null && mSameDetailBean.getData() != null) {
            mediaObject.title = mSameDetailBean.getData().getContent();
            mediaObject.description = mSameDetailBean.getData().getContent();
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.desktop_icon);
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = ConstantsBean.PAGE_URL;
        mediaObject.defaultText = "拼旅行";
        return mediaObject;
    }


    //qq
    private void setQQShare(String type) {
        mTencent = Tencent.createInstance(ConstantsBean.QQAPPID, getApplicationContext());
        mIUiListener = new BaseUiListener();
        Bundle params = new Bundle();
        if (mSameDetailBean != null && mSameDetailBean.getData() != null) {
            if (type.equals("QQ")) {
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, mSameDetailBean.getData().getContent());// 标题
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mSameDetailBean.getData().getContent());// 摘要
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, ConstantsBean.PAGE_URL);// 内容地址
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mSameDetailBean.getData().getCoverImage().get(0));// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
                //params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其它附加功能");
            } else {
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, mSameDetailBean.getData().getContent());// 标题
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, mSameDetailBean.getData().getContent());// 摘要
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, ConstantsBean.PAGE_URL);// 内容地址
                // ArrayList<String> imgUrlList = new ArrayList<>();
                // imgUrlList.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=6f05c5f929738bd4db21b531918a876c/6a600c338744ebf8affdde1bdef9d72a6059a702.jpg");
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, (ArrayList<String>) mSameDetailBean.getData().getCoverImage());// 图片地址
            }
        }

        // 分享操作要在主线程中完成
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type.equals("QQ")) {
                    mTencent.shareToQQ(SameDetailActivity.this, params, mIUiListener);
                } else {
                    mTencent.shareToQzone(SameDetailActivity.this, params, mIUiListener);
                }

            }
        });

    }


    @Override
    public void onWbShareSuccess() {
        ToastUtil.show("分享成功");
    }

    @Override
    public void onWbShareFail() {
        ToastUtil.show("分享失败");
    }

    @Override
    public void onWbShareCancel() {
        ToastUtil.show("取消分享");
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            ToastUtil.show("分享成功");
        }

        protected void doComplete(JSONObject values) {
        }

        @Override
        public void onError(UiError e) {
            ToastUtil.show("分享失败");
        }

        @Override
        public void onCancel() {
            ToastUtil.show("分享成功");
        }
    }


    /**
     * 滑动改变状态栏背景
     */
    private void initListeners() {
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver vto = topTitle.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                topTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = topTitle.getHeight();
                sc.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                        // TODO Auto-generated method stub
                        if (y <= 0) {
//                          设置文字背景颜色，白色
                            topTitle.setBackgroundColor(changeAlpha(getResources().getColor(R.color.main_bg), 0.0f));
                            sameNick.setTextColor(getResources().getColor(R.color.main_bg));
                            sameLeft.setImageResource(R.drawable.fhwhite);
                            sameMore.setImageResource(R.drawable.morewhite);
//                          设置文字颜色，黑色
                        } else if (y > 0 && y <= imageHeight) {
                            float scale = (float) y / imageHeight;
                            float alpha = (255 * scale);
                            sameLeft.setImageResource(R.drawable.fhblack);
                            sameMore.setImageResource(R.drawable.moreblack);
                            sameNick.setTextColor(getResources().getColor(R.color.buyvip));

                            topTitle.setBackgroundColor(changeAlpha(getResources().getColor(R.color.texy6ee), 1.0f));
                        } else {
                            sameLeft.setImageResource(R.drawable.fhblack);
                            sameMore.setImageResource(R.drawable.moreblack);
                            sameNick.setTextColor(getResources().getColor(R.color.buyvip));
                            topTitle.setBackgroundColor(changeAlpha(getResources().getColor(R.color.texy6ee), 1.0f));
                        }
                    }
                });

            }
        });

    }
    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }
}
