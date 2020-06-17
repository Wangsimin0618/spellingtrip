package com.spellingtrip.example.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.CommentAddBean;
import com.spellingtrip.example.bean.CommentContentBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.NewActivityDetailBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.DetailImgFragment;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.huanxin.ChatActivity;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.SoftKeyBroadManager;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.utils.Util;
import com.spellingtrip.example.utils.WeiBoConstants;
import com.spellingtrip.example.view.MyLayoutManager;
import com.spellingtrip.example.view.ScrollGridView;
import com.spellingtrip.example.view.ShapedImageView;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.CommentAdapter;
import com.spellingtrip.example.viewpager.JieBanLiveAdapter;
import com.spellingtrip.example.viewpager.LabelAdapter;
import com.tencent.connect.common.Constants;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class JieBanActivityDetailActivity extends JieBaseActivity implements WbShareCallback, OnRefreshLoadMoreListener {
    public static final String TAG = "JieBanActivityDetail";
    private static final String TYPE_WECHAT = "session";
    private static final String TYPE_WECHAT_MOMENT = "timeline";
    public static final String KEY_SHARE_TYPE = "key_share_type";
    @BindView(R.id.jiebanfh)
    ImageView jiebanfh;
    @BindView(R.id.jiebanmore)
    ImageView jiebanmore;
    @BindView(R.id.sil)
    TextView sil;
    @BindView(R.id.jiebanbanner)
    Banner jiebanbanner;
    @BindView(R.id.jiebanrg)
    RadioGroup jiebanrg;
    @BindView(R.id.share)
    TextView share;
    @BindView(R.id.likenum)
    TextView likenum;

    private LogineDialog logineDialog;
    private NewActivityDetailBean activityDetailBean;
    private NewActivityDetailBean.DataBean detailBean;
    public ShapedImageView ivJieBanUserHeader;
    public ImageView ivJieBanSex;
    public RecyclerView recyclerView;
    @BindView(R.id.tvJieBanToChat)
    public ImageView tvJieBanToChat;
    @BindView(R.id.rlJieBanShare)
    public ImageView rlJieBanShare;
    public TextView tvJiebanTime, tvJieBanSexArg, tvJieBanSex, tvJiebanContent, tvJiebanToArea, tvJieBanUserNick, tvCommentTitle, tvJiebanStartArea, tvJieBanLiveNum;
    private LinearLayout llJieBanLive;
    private RelativeLayout llJieBanArgSex;
    @BindView(R.id.jieBanRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.lvJiebanDetailList)
    public XRecyclerView xRecyclerView;
    @BindView(R.id.tvJiebanComment)
    public TextView tvJiebanComment;
    @BindView(R.id.etJieBanContent)
    public EditText etJieBanContent;
    @BindView(R.id.tvJiebanSend)
    public TextView tvJiebanSend;
    @BindView(R.id.rlJieBanLive)
    public ImageView rlJieBanLive;
    @BindView(R.id.llJieBanBottonSend)
    public RelativeLayout llJieBanBottonSend;
    @BindView(R.id.llJieBanBotton)
    public LinearLayout llJieBanBotton;
    private IWXAPI api;
    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private int mTargetTimeine = SendMessageToWX.Req.WXSceneTimeline;
    private PopupWindow popupWindow;
    private WbShareHandler shareHandler;
    public static final int SHARE_CLIENT = 1;
    private int mShareType = SHARE_CLIENT;
    private int page = 1;
    private CommentAdapter commentAdapter;
    private List<CommentContentBean.DataBean> comments = new ArrayList<>();
    private ScrollGridView gvJieBanLives;
    private SoftKeyBroadManager softKeyBroadManager;
    private Tencent mTencent;
    private IUiListener mIUiListener;
    List<String> images = new ArrayList<>();
    private ImageView jiebansex;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected int getLayoutId() {
        return R.layout.activity_jiebandetail;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(true);
        refreshLayout.setEnableOverScrollDrag(true);
        refreshLayout.setReboundDuration(450);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setEnableClipHeaderWhenFixedBehind(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        View view = LayoutInflater.from(this).inflate(R.layout.jieban_header, null);
        ivJieBanUserHeader = view.findViewById(R.id.ivJieBanUserHeader);
        jiebansex = view.findViewById(R.id.jiebansex);
        tvJieBanUserNick = view.findViewById(R.id.tvJieBanUserNick);
        tvJiebanStartArea = view.findViewById(R.id.tvJiebanStartArea);
        ivJieBanSex = view.findViewById(R.id.ivJieBanSex);
        gvJieBanLives = view.findViewById(R.id.gvJieBanLives);
        tvJiebanToArea = view.findViewById(R.id.tvJiebanToArea);
        llJieBanArgSex = view.findViewById(R.id.llJieBanArgSex);
        recyclerView = view.findViewById(R.id.jiebanTypes);
        tvJieBanLiveNum = view.findViewById(R.id.tvJieBanLiveNum);
        llJieBanLive = view.findViewById(R.id.llJieBanLive);
        tvJiebanContent = view.findViewById(R.id.tvJiebanContent);
        tvJiebanTime = view.findViewById(R.id.tvJiebanTime);
        tvJieBanSexArg = view.findViewById(R.id.tvJieBanSexArg);
        tvCommentTitle = view.findViewById(R.id.tvJIeBanHeaderCommentTitle);
        xRecyclerView.addHeaderView(view);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setFocusableInTouchMode(false);
        xRecyclerView.setLayoutManager(layoutManager);
        softKeyBroadManager = new SoftKeyBroadManager(tvJiebanComment, true);
        softKeyBroadManager.addSoftKeyboardStateListener(softKeyboardStateListener);

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

    SoftKeyBroadManager.SoftKeyboardStateListener softKeyboardStateListener = new SoftKeyBroadManager.SoftKeyboardStateListener() {
        @Override
        public void onSoftKeyboardOpened(int keyboardHeightInPx) {
            llJieBanBottonSend.requestLayout();
        }

        @Override
        public void onSoftKeyboardClosed() {
            llJieBanBottonSend.setVisibility(View.GONE);

        }
    };

    @Override
    protected void getData() {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(JieBanActivityDetailActivity.this, "正在加载");
        }
        logineDialog.show();
        String activityid = getIntent().getStringExtra("title");
        getActivityData(activityid);
        getPinLun();
    }

    private void getPinLun() {
        String activityid = getIntent().getStringExtra("title");
        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.PINGLUN + activityid).addParams(ConstantsBean.USERID, userid)
                .addParams(ConstantsBean.PAGE, String.valueOf(page)).build().readTimeOut(50000)
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
                    CommentContentBean commentContentBean = (CommentContentBean) JsonUtil.fromJson(s, CommentContentBean.class);
                    if (commentContentBean.getCode() == 0) {
                        if (commentContentBean.getData() != null && commentContentBean.getData().size() > 0) {
                            if (commentContentBean.getData().size() > 9) {
                                refreshLayout.setEnableLoadMore(true);
                            } else {
                                refreshLayout.setEnableLoadMore(false);
                            }
                            comments.addAll(commentContentBean.getData());
                            setComment(comments);
                        } else {
                            refreshLayout.setEnableLoadMore(false);
                            setComment(comments);
                        }
                    } else {

                    }
                }
            }
        });
    }

    private void setComment(List<CommentContentBean.DataBean> data) {
        if (commentAdapter == null) {
            commentAdapter = new CommentAdapter(JieBanActivityDetailActivity.this, data);
            xRecyclerView.setAdapter(commentAdapter);
        } else {
            commentAdapter.notifyDataSetChanged();
        }
    }

    /*获取结伴详情*/
    private void getActivityData(String activityid) {
        String userid = null;
        if (UserTask.getInstance().getUser() != null) {
            userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        }
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.CITY_DETAIL + activityid + "?userId=" + userid)
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
                activityDetailBean = (NewActivityDetailBean) JsonUtil.fromJson(s, NewActivityDetailBean.class);
                if (activityDetailBean.getCode() == 0) {
                    detailBean = activityDetailBean.getData();
                    setDetailData(activityDetailBean.getData());
                } else {
                }
            }
        });
    }

    /**
     * 设置结伴详情
     *
     * @param data
     */
    private void setDetailData(NewActivityDetailBean.DataBean data) {
        initBanner(data.getTour().getFidList());
        String imgurl = data.getUserInfo().getHeadUrl() + "?x-oss-process=style/64_64";
        if (!JieBanActivityDetailActivity.this.isFinishing()) {
            Glide.with(JieBanActivityDetailActivity.this).load(imgurl).into(ivJieBanUserHeader);
        }
        if (data.getUserInfo().isVip()) {
            ivJieBanSex.setVisibility(View.VISIBLE);
            tvJieBanUserNick.setTextColor(getResources().getColor(R.color.image_selector_red));
        } else {
            ivJieBanSex.setVisibility(View.GONE);
            tvJieBanUserNick.setTextColor(getResources().getColor(R.color.history_text));
        }
        tvJieBanUserNick.setText(data.getUserInfo().getNick());
        if (data.getUserInfo().getSex().equals("女")) {
            jiebansex.setBackgroundResource(R.drawable.giry);
        } else {
            jiebansex.setBackgroundResource(R.drawable.man);
        }
        if (data.getTour().getTourLikeList() != null && data.getTour().getTourLikeList().size() > 0) {
            likenum.setVisibility(View.VISIBLE);
            llJieBanLive.setVisibility(View.VISIBLE);
            tvJieBanLiveNum.setText("喜欢·" + data.getTour().getLikeCount());
            likenum.setText(data.getTour().getLikeCount()+"");
            setLives(data.getTour().getTourLikeList());
        } else {
            likenum.setVisibility(View.GONE);
            llJieBanLive.setVisibility(View.GONE);
        }
        String age = CommonUtil.BirthdayToAge(data.getUserInfo().getBirthday());
        tvJieBanSexArg.setText(age + " | 刚刚活跃");
        tvJiebanToArea.setText(data.getTour().getToArea());
        tvJiebanStartArea.setText(data.getTour().getFromArea());
        tvJiebanContent.setText(data.getTour().getDescription());
        tvJiebanTime.setText(data.getTour().getStartDate() + "-" + data.getTour().getEndDate());
        if (data.getTour().isLikeState()) {
            likenum.setText(data.getTour().getLikeCount()+"");

            rlJieBanLive.setImageDrawable(getResources().getDrawable(R.drawable.likes));
        } else {
            likenum.setText(data.getTour().getLikeCount()+"");

            rlJieBanLive.setImageDrawable(getResources().getDrawable(R.drawable.likeblack));
        }
        if (data.getTour().getLabelList() != null && data.getTour().getLabelList().size() > 0) {
            final List<String> labeLists = data.getTour().getLabelList();
            MyLayoutManager layout = new MyLayoutManager();
            layout.setAutoMeasureEnabled(true);
            if (recyclerView.getItemDecorationCount() == 0) {
                recyclerView.addItemDecoration(new SpacesItemDecoration(14));
            }
            recyclerView.setLayoutManager(layout);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            final LabelAdapter adapter = new LabelAdapter(JieBanActivityDetailActivity.this, labeLists);
            recyclerView.setAdapter(adapter);
        }
        ivJieBanUserHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.skipActivity(JieBanActivityDetailActivity.this, TourismUserShowActivity.class, 0, String.valueOf(data.getUserInfo().getId()));

            }
        });
//        tvJiebanToArea.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String lat = String.valueOf(activityDetailBean.getData().getUserInfo().getLat());
//                String lng = String.valueOf(activityDetailBean.getData().getUserInfo().getLng());
//                Double latitude = null;
//                Double longitude = null;
//                if (!TextUtils.isEmpty(lat) && lat.length() > 5) {
//                    latitude = Double.valueOf(String.valueOf(activityDetailBean.getData().getUserInfo().getLat()).substring(0, 5));
//                }
//                if (!TextUtils.isEmpty(lng) && lng.length() > 6) {
//                    longitude = Double.valueOf(String.valueOf(activityDetailBean.getData().getUserInfo().getLng()).substring(0, 6));
//                }
//                setRequestedPress(activityDetailBean.getData().getTour().getToArea());
//            }
//        });
    }

    private void setLives(List<NewActivityDetailBean.DataBean.TourBean.TourLikeListBean> tourLikeList) {
        JieBanLiveAdapter liveAdapter = new JieBanLiveAdapter(this, tourLikeList);
        gvJieBanLives.setAdapter(liveAdapter);
    }

    //顶部轮播
    private void initBanner(List<String> fidList) {
        images.clear();
        for (int i = 0; i < fidList.size(); i++) {
            images.add(fidList.get(i));
            Log.e("", "bannerBeans.get(i).getFilepath()" + fidList);
        }
        for (int i = 0; i < images.size(); i++) {
            RadioButton radioButton = new RadioButton(JieBanActivityDetailActivity.this);
            radioButton.setBackgroundResource(R.drawable.banner_sell);
            radioButton.setButtonDrawable(null);
            jiebanrg.addView(radioButton);
        }
        jiebanbanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        jiebanbanner.setImageLoader(new JieBanImageLoader());
        //设置图片集合
        jiebanbanner.setImages(images);
        Log.e(TAG, "-----images---" + images);
        jiebanbanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e(TAG, "---setOnBannerListener--position---" + position);
            }
        });

        jiebanbanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                jiebanrg.check(position + 1);
                for (int i = 0; i < jiebanrg.getChildCount(); i++) {
                    View childAt = jiebanrg.getChildAt(i);
                    LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)
                            childAt.getLayoutParams(); //取控件textView当前的布局参数
                    linearParams.height = dip2px(3);
                    linearParams.width = dip2px(4);
                    linearParams.setMargins(dip2px(2), dip2px(2), dip2px(2), dip2px(2));
                    childAt.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
                }
                View childAt = jiebanrg.getChildAt(position);
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
        jiebanbanner.start();


        jiebanbanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                DetailImgFragment fragment = new DetailImgFragment();
                fragment.setData(fidList, position);
                fragment.show(JieBanActivityDetailActivity.this.getFragmentManager(), "DialogFragment");
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class JieBanImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            int i = dip2px(10);
//        RequestOptions requestOptions = RequestOptions.bitmapTransform(new RoundedCorners(i));
            Glide.with(context).load(path).into(imageView);

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

    @OnClick({R.id.tvJieBanToChat, R.id.rlJieBanShare, R.id.tvJiebanComment, R.id.tvJiebanSend, R.id.rlJieBanLive, R.id.jiebanfh, R.id.jiebanmore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvJieBanToChat:
                if (UserTask.getInstance() != null && UserTask.getInstance().getUser() != null) {
                    String uuid = UserTask.getInstance().getUser().getImUsername();
                    int userid = UserTask.getInstance().getUser().getUserId();
                    if (detailBean != null && !uuid.equals(detailBean.getUserInfo().getUuid())) {
                        if (UserTask.getInstance().getUserInfoData().isVip()) {
                            activity.startActivity(new Intent(activity, ChatActivity.class)
                                    .putExtra(EaseConstant.EXTRA_USER_ID, detailBean.getUserInfo().getUuid())
                                    .putExtra("nick", detailBean.getUserInfo().getNick()));
                        } else {
                            CommonDialog.getTiShiDialog(JieBanActivityDetailActivity.this, ConstantsBean.CHATJIEBAN, new OnButtonClick() {
                                @Override
                                public void button01ClickListener() {
                                    ActivityUtils.skipActivity(JieBanActivityDetailActivity.this, BuyVIPActivity.class, 0, "");
                                }

                                @Override
                                public void button02ClickListener() {

                                }
                            });
                        }
                    } else {
                        ToastUtil.show("去找别人聊聊吧");
                    }
                } else {
                    ActivityUtils.skipActivity(JieBanActivityDetailActivity.this, LogineActivity.class, 0, "");
                }

                break;
            case R.id.rlJieBanShare:
                openPopupWindow(rlJieBanShare);
                break;
            case R.id.tvJiebanComment:
                llJieBanBottonSend.setVisibility(View.VISIBLE);
                showSoftInputFromWindow(etJieBanContent);
                break;
            case R.id.tvJiebanSend:
                String content = etJieBanContent.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    setContent(content);
                } else {
                    ToastUtil.show("请输入内容");
                }
                break;
            //点赞
            case R.id.rlJieBanLive:
                String activityid = getIntent().getStringExtra("title");
                setTourZan(activityid);
                break;
            case R.id.jiebanfh:
                Log.v(TAG, "----onclick---jiebanfh");
                finish();
                break;
            case R.id.jiebanmore:
                Log.v(TAG, "----onclick---jiebanmore");
                int userid = UserTask.getInstance().getUser().getUserId();
                CommonDialog.homeitemPopupWindow(activity, view, userid, 0);
                break;
        }
    }

    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    /**
     * 拼游点赞
     *
     * @param activityid
     */
    private void setTourZan(String activityid) {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(JieBanActivityDetailActivity.this, "加载数据");
        }
        logineDialog.show();
        String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.TOURLIKE).addParams(ConstantsBean.tourId, activityid)
                .addParams(ConstantsBean.USERID, userid).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                if (logineDialog != null && logineDialog.isShowing()) {
                    logineDialog.dismiss();
                }
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {
                        getActivityData(activityid);
                    } else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }

    /**
     * 发送信息
     *
     * @param content
     */
    private void setContent(String content) {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(JieBanActivityDetailActivity.this, "正在加载");
        }
        logineDialog.show();
        String activityid = getIntent().getStringExtra("title");
        String usrid = String.valueOf(UserTask.getInstance().getUser().getUserId());
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.COOMENTADD).addParams("userId", usrid + "")
                .addParams(ConstantsBean.tourId, activityid).addParams("content", content).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Log.e("onError", JsonUtil.toJson(e));
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    CommentAddBean commentAddBean = (CommentAddBean) JsonUtil.fromJson(s, CommentAddBean.class);
                    if (commentAddBean.getCode() == 0) {
                        //  etJieBanContent.setFocusable(false);
                        etJieBanContent.setText("");
                        llJieBanBottonSend.setVisibility(View.GONE);
                        onRefresh(refreshLayout);
                    } else {

                    }
                }
            }
        });
    }

    /**
     * 判断请求权限
     */
    private void setRequestedPress(String city) {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (Build.VERSION.SDK_INT >= 23) {
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            if (check == PackageManager.PERMISSION_GRANTED) {
                //写入你需要权限才能使用的方法
                jumpActivity(city);
            } else {
                //手动去请求用户打开权限(可以在数组中添加多个权限) 1 为请求码 一般设置为final静态变量
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
            }
        } else {
            //写入你需要权限才能使用的方法
            jumpActivity(city);
        }

    }

    private void jumpActivity(String city) {
        Intent intent = new Intent(JieBanActivityDetailActivity.this, BaiduMapActivity.class);
        //  intent.putExtra("longitude", longitude);
        intent.putExtra("address", city);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            jumpActivity(activityDetailBean.getData().getTour().getToArea());
        } else {
            Toast.makeText(this, "请打开定位权限", Toast.LENGTH_SHORT).show();
        }
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
        if (activityDetailBean != null && activityDetailBean.getData() != null && activityDetailBean.getData().getTour() != null) {
            msg.title = activityDetailBean.getData().getTour().getDescription();
            msg.description = activityDetailBean.getData().getTour().getDescription();
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

    @Override
    protected void setData() {
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 底部弹出框
     */
    @SuppressLint("WrongConstant")
    public void openPopupWindow(final ImageView v) {
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
                setBackgroundAlpha(JieBanActivityDetailActivity.this, 1f);
            }
        });
        setBackgroundAlpha(JieBanActivityDetailActivity.this, 0.9f);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(ConstantsBean.DELCOMMENT)) {
            onRefresh(refreshLayout);
        }
    }

    //qq
    private void setQQShare(String type) {
        mTencent = Tencent.createInstance(ConstantsBean.QQAPPID, getApplicationContext());
        mIUiListener = new BaseUiListener();
        Bundle params = new Bundle();
        if (activityDetailBean != null && activityDetailBean.getData() != null && activityDetailBean.getData().getTour() != null) {
            if (type.equals("QQ")) {
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, activityDetailBean.getData().getTour().getDescription());// 标题
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, activityDetailBean.getData().getTour().getDescription());// 摘要
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, ConstantsBean.PAGE_URL);// 内容地址
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, activityDetailBean.getData().getTour().getFidList().get(0));// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
                //params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其它附加功能");
            } else {
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, activityDetailBean.getData().getTour().getDescription());// 标题
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, activityDetailBean.getData().getTour().getDescription());// 摘要
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, ConstantsBean.PAGE_URL);// 内容地址
                // ArrayList<String> imgUrlList = new ArrayList<>();
                // imgUrlList.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=6f05c5f929738bd4db21b531918a876c/6a600c338744ebf8affdde1bdef9d72a6059a702.jpg");
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, (ArrayList<String>) activityDetailBean.getData().getTour().getFidList());// 图片地址
            }
        }

        // 分享操作要在主线程中完成
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type.equals("QQ")) {
                    mTencent.shareToQQ(JieBanActivityDetailActivity.this, params, mIUiListener);
                } else {
                    mTencent.shareToQzone(JieBanActivityDetailActivity.this, params, mIUiListener);
                }

            }
        });

    }

    //微博
    private void setWeiShare() {
        WbSdk.install(JieBanActivityDetailActivity.this, new AuthInfo(JieBanActivityDetailActivity.this,
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
        if (activityDetailBean != null && activityDetailBean.getData() != null && activityDetailBean.getData().getTour() != null) {
            mediaObject.title = activityDetailBean.getData().getTour().getDescription();
            mediaObject.description = activityDetailBean.getData().getTour().getDescription();
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.desktop_icon);
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = ConstantsBean.PAGE_URL;
        mediaObject.defaultText = "拼旅行";
        return mediaObject;
    }

    //设置屏幕背景透明效果
    public static void setBackgroundAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, mIUiListener);
            }
        }
        if (shareHandler != null) {
            shareHandler.doResultIntent(data, this);
        }

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


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getPinLun();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK) {
            refreshLayout.finishRefresh();
        }
        page = 1;
        if (comments != null && comments.size() > 0) {
            comments.clear();
        }
        getPinLun();
        refreshLayout.finishRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Glide.with(getApplicationContext()).pauseRequests();
    }

}
