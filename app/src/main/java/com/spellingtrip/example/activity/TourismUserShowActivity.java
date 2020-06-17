package com.spellingtrip.example.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.EaseConstant;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.MyCityActivityBean;
import com.spellingtrip.example.bean.UserShowBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.DetailImgFragment;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.huanxin.ChatActivity;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.HttpRequest;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.MyLayoutManager;
import com.spellingtrip.example.view.ShapedImageView;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.CardCityAdapter;
import com.spellingtrip.example.viewpager.CardLablesAdapter;
import com.spellingtrip.example.viewpager.UserActivityAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class TourismUserShowActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    public static final String TAG = "TourismUserShowActivity";
    @BindView(R.id.tv_lantitleId)
    public TextView tv_lantitleId;
    @BindView(R.id.ivTitleRight)
    public ImageView ivTitleRight;
    @BindView(R.id.iv_Userleft)
    public ImageView iv_Userleft;
    @BindView(R.id.tv_Userlantitle)
    public TextView tv_Userlantitle;
    @BindView(R.id.userShowRecyclerView)
    public XRecyclerView xRecyclerView;
    @BindView(R.id.userShowRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.ivHeaderBg)
    ImageView ivHeaderBg;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.llUserLiao)
    RelativeLayout llUserLiao;
    @BindView(R.id.relayout)
    RelativeLayout relayout;
    private LogineDialog logineDialog;
    private UserShowBean userShowBean;
    @BindView(R.id.usershowbanner)
    public Banner usershowbanner;
    @BindView(R.id.jiebanrg)
    public RadioGroup jiebanrg;
    @BindView(R.id.ivUserShowLeft)
    public ImageView ivUserShowLeft;
    @BindView(R.id.ivHeaderMore)
    public ImageView ivHeaderMore;
    @BindView(R.id.userShowappbar)
    public AppBarLayout appBar;
    @BindView(R.id.ivHeaderEdit)
    public ImageView ivHeaderEdit;
    @BindView(R.id.userShowToolbar)
    public Toolbar toolbar;
    @BindView(R.id.rvUserLiao)
    public TextView rvUserLiao;
    @BindView(R.id.tvUserShowNick)
    public TextView tvUserShowNick;
    private int page = 1;
    private List<MyCityActivityBean.DataBean> citys;
    private UserActivityAdapter userActivityAdapter;
    private TextView qName, cAge, cNick, tvLablesNum,tvcardnonumber;
    private ImageView ivVip,cSex;
    private RecyclerView rvCardLables;
    private ShapedImageView carduserhead;
    private ActivityAABean pinYouBean;
    private CardCityAdapter aaCityAdapter;
    private List<ActivityAABean.DataBean> rows;
    List<String> images = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_usershow;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setDisableContentWhenLoading(false);
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
        View view = LayoutInflater.from(this).inflate(R.layout.card_header, null);

        carduserhead = view.findViewById(R.id.carduserhead);
        qName = view.findViewById(R.id.tvCardUserQianMing);
        ivVip = view.findViewById(R.id.ivCardUserVip);
        cAge = view.findViewById(R.id.tvCardUserAge);
        cSex = view.findViewById(R.id.tvCardUserSex);
        cNick = view.findViewById(R.id.cardUserNick);
        tvLablesNum = view.findViewById(R.id.tvCardLableNumber);
        tvcardnonumber = view.findViewById(R.id.tv_cardnonumber);
//        llCardUserSexAge = view.findViewById(R.id.llCardUserSexAge);
        rvCardLables = view.findViewById(R.id.rvCardLables);
        xRecyclerView.addHeaderView(view);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setFocusableInTouchMode(false);
        xRecyclerView.setLayoutManager(layoutManager);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                setChangeImg(Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
            }
        });
    }

    private void setChangeImg(float v) {
        if (v == 1.0) {
            ivUserShowLeft.setImageResource(R.drawable.fhblack);
            tvUserShowNick.setVisibility(View.VISIBLE);
            toolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.texy6ee), 1.0f));
        } else {
            ivUserShowLeft.setImageResource(R.drawable.fhblack);
            tvUserShowNick.setVisibility(View.GONE);
            toolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.main_bg), 0.0f));
        }
    }

    @Override
    protected void getData() {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(this, "");
        }
        logineDialog.show();
        getUserInfo();
        getMyPin();
    }

    @OnClick({R.id.iv_Userleft, R.id.ivTitleRight, R.id.ivUserShowLeft, R.id.ivHeaderMore, R.id.ivHeaderEdit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_Userleft:
                finish();
                break;
            case R.id.ivHeaderMore:
                ActivityUtils.skipActivity(this, ReportActivity.class, 0, "");
                break;
            case R.id.ivUserShowLeft:
                finish();
                break;
            case R.id.ivHeaderEdit:
                ActivityUtils.skipActivity(this, MyInfoCardActivity.class, 0, "");
                break;
        }
    }

    private void getUserInfo() {
        String fid = getIntent().getStringExtra("title");
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.USERINFO + fid).build()
                .connTimeOut(10000).readTimeOut(50000).writeTimeOut(10000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    userShowBean = (UserShowBean) JsonUtil.fromJson(s, UserShowBean.class);
                    if (userShowBean.getCode() == 0 && userShowBean.getData() != null) {
                        setUserInfo(userShowBean.getData());
                    }
                }
            }
        });
    }


    private void setUserInfo(UserShowBean.DataBean data) {
        Log.v(TAG,"UserTask.getInstance().getUser().getImUsername()=="+UserTask.getInstance().getUser().getImUsername());
        Log.v(TAG,"data.getNick()=="+data.getImUsername());
        if (UserTask.getInstance().getUser().getImUsername().equals(data.getImUsername()) ){
            llUserLiao.setVisibility(View.GONE);
        }else {
            llUserLiao.setVisibility(View.VISIBLE);
        }
        String headUrl = data.getHeadUrl() + "?x-oss-process=style/320_320";
        if (!TourismUserShowActivity.this.isFinishing()) {
            Glide.with(TourismUserShowActivity.this).load(headUrl).into(carduserhead);
        }

        if (data.getBgImages() != null && data.getBgImages().size() > 0) {
//            convenientBanner.setVisibility(View.VISIBLE);
            ivHeaderBg.setVisibility(View.GONE);
            relayout.setVisibility(View.VISIBLE);
            setBanner(data.getBgImages());
        } else {
            relayout.setVisibility(View.GONE);
            ivHeaderBg.setVisibility(View.VISIBLE);
            if (!TourismUserShowActivity.this.isFinishing()) {
                Glide.with(TourismUserShowActivity.this).load(headUrl).into(ivHeaderBg);
            }
        }
        if (data.isVip()) {
            ivVip.setVisibility(View.VISIBLE);
            cNick.setTextColor(getResources().getColor(R.color.image_selector_red));
            ivVip.setBackground(getResources().getDrawable(R.drawable.vip));
        }else {
            ivVip.setVisibility(View.GONE);
            cNick.setTextColor(getResources().getColor(R.color.history_text));

        }
        cNick.setText(data.getNick());
        tvUserShowNick.setText(data.getNick());
        String age = CommonUtil.BirthdayToAge(data.getBirthday());
        if (!TextUtils.isEmpty(data.getCity())){
            cAge.setText(data.getCity()+" | "+age+"岁");
        }else {
            cAge.setText(age+"岁");

        }
        if (!TextUtils.isEmpty(data.getDescription())) {
            qName.setText(data.getDescription());
        } else {
            qName.setText("个性签名：未设置签名");
        }
        if (data.getSex().equals("男")) {
            cSex.setBackground(getResources().getDrawable(R.drawable.man));
        } else {
            cSex.setBackground(getResources().getDrawable(R.drawable.giry));
        }
        if (data.getLabels() == null || data.getLabels().size() == 0) {
            rvCardLables.setVisibility(View.GONE);
            tvcardnonumber.setVisibility(View.VISIBLE);
        } else {
            rvCardLables.setVisibility(View.VISIBLE);
        }
        if (data.getLabels() != null && data.getLabels().size() > 0) {
            final List<UserShowBean.DataBean.LabelsBean> labeLists = data.getLabels();
            MyLayoutManager layout = new MyLayoutManager();
            layout.setAutoMeasureEnabled(true);
            if (rvCardLables.getItemDecorationCount() == 0) {
                rvCardLables.addItemDecoration(new SpacesItemDecoration(14));
            }
            rvCardLables.setLayoutManager(layout);
            rvCardLables.setHasFixedSize(true);
            rvCardLables.setNestedScrollingEnabled(false);
            final CardLablesAdapter adapter = new CardLablesAdapter(activity, labeLists);
            rvCardLables.setAdapter(adapter);
        }
        String username = UserTask.getInstance().getUser().getImUsername();
        if (data.getImUsername().equals(username)) {
            ivHeaderEdit.setVisibility(View.VISIBLE);
            ivHeaderMore.setVisibility(View.GONE);
        } else {
            ivHeaderEdit.setVisibility(View.GONE);
            ivHeaderMore.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置banner
     *
     * @param bgImages
     */
    private void setBanner(List<String> bgImages) {
        images.clear();
        for (int i = 0; i < bgImages.size(); i++) {
            images.add(bgImages.get(i));
            Log.e(TAG, "bannerBeans.get(i).getFilepath()" + bgImages);
        }
        for (int i = 0; i < images.size(); i++) {
            RadioButton radioButton = new RadioButton(TourismUserShowActivity.this);
            radioButton.setBackgroundResource(R.drawable.banner_sell);
            radioButton.setButtonDrawable(null);
            jiebanrg.addView(radioButton);
        }
        usershowbanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        usershowbanner.setImageLoader(new UsershowImageLoader());
        //设置图片集合
        usershowbanner.setImages(images);
        Log.e(TAG, "-----images---" + images);
        usershowbanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e(TAG, "---setOnBannerListener--position---" + position);
            }
        });

        usershowbanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        usershowbanner.start();


        usershowbanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                DetailImgFragment fragment = new DetailImgFragment();
                fragment.setData(bgImages, position);
                fragment.show(TourismUserShowActivity.this.getFragmentManager(), "DialogFragment");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class UsershowImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            if (!TourismUserShowActivity.this.isFinishing()){
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

    private void getMyPin() {
        String userid = getIntent().getStringExtra("title");
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.MYPIN).addParams("page", String.valueOf(page))
                .addParams("userId", userid).build().readTimeOut(50000).execute(new StringCallback() {
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
                    pinYouBean = (ActivityAABean) JsonUtil.fromJson(s, ActivityAABean.class);
                    if (pinYouBean.getCode() == 0) {
                        if (pinYouBean.getData().size() > 0) {
                            if (rows == null) {
                                rows = new ArrayList<>();
                            }
                            rows.addAll(pinYouBean.getData());
                            setPinYouList(rows);
                        } else {
                            setPinYouList(rows);
                        }
                    } else {
                        ToastUtil.show(pinYouBean.getMsg());
                    }
                }
            }
        });
    }

    private void setPinYouList(List<ActivityAABean.DataBean> citys) {
        if (aaCityAdapter == null) {
            aaCityAdapter = new CardCityAdapter(this, citys, "userinfo");
            xRecyclerView.setAdapter(aaCityAdapter);
        } else {
            aaCityAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.rvUserLiao})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rvUserLiao:
                if (userShowBean != null && userShowBean.getData() != null) {
                    String username = userShowBean.getData().getImUsername();
                    String uuid = UserTask.getInstance().getUser().getImUsername();
                    if (!username.equals(UserTask.getInstance().getUser().getImUsername())) {
                        if (UserTask.getInstance().getUserInfoData().isVip()){
                            activity.startActivity(new Intent(activity, ChatActivity.class)
                                    .putExtra(EaseConstant.EXTRA_USER_ID, username)
                                    .putExtra("nick", userShowBean.getData().getNick()));
                        }else {
                            CommonDialog.getTiShiDialog(TourismUserShowActivity.this, ConstantsBean.CHATJIEBAN, new OnButtonClick() {
                                @Override
                                public void button01ClickListener() {
                                    ActivityUtils.skipActivity(TourismUserShowActivity.this, BuyVIPActivity.class, 0, "");
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
                    ToastUtil.show("用户信息缺失");
                }

                break;
//            case R.id.ivHeaderBg:
            // UserHeaderImgFragment fragment = new UserHeaderImgFragment();
            ///  fragment.setData(userShowBean.getData().getHeadUrl(),TourismUserShowActivity.this);
            // fragment.show(activity.getFragmentManager(), "DialogFragment");
               /* if (userShowBean!=null){
                    int userid= userShowBean.getData().getId();
                    int id=UserTask.getInstance().getUser().getUserId();
                    if (userid==id){
                        llUserLiao.setVisibility(View.GONE);
                        ivHeaderMore.setVisibility(View.GONE);
                    }

                }*/
//                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.GETUSER)) {
            getUserInfo();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (citys != null && citys.size() > 0) {
            citys.clear();
        }
//        Glide.with(TourismUserShowActivity.this).pauseRequests();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getData();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
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

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}
