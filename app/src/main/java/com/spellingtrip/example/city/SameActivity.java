package com.spellingtrip.example.city;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.BaseActivity;
import com.spellingtrip.example.activity.MerchantRZActivity;
import com.spellingtrip.example.activity.PublishPinActivity;
import com.spellingtrip.example.activity.SameDetailActivity;
import com.spellingtrip.example.activity.SamePublicActivity;
import com.spellingtrip.example.activity.SearchLabelActivity;
import com.spellingtrip.example.bean.BannerDataBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.SameBean;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.viewpager.GlideImageLoader;
import com.spellingtrip.example.viewpager.SameItemAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 同城活动
 */
public class SameActivity extends BaseActivity implements OnRefreshLoadMoreListener {

    public static final String TAG = "SameActivity";

    @BindView(R.id.same_left)
    ImageView sameleft;
    @BindView(R.id.same_seachs)
    ImageView sameSeach;
    @BindView(R.id.same_filtrate)
    TextView sameFiltrate;
    @BindView(R.id.same_xrecyclerview)
    XRecyclerView sameXrecyclerview;
    @BindView(R.id.footer_progressbar)
    ProgressBar footerProgressbar;
    @BindView(R.id.footer_hint_text)
    TextView footerHintText;
    @BindView(R.id.piCityRefreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.same_public)
    ImageView samepublic;
    @BindView(R.id.top)
    RelativeLayout top;
    @BindView(R.id.same_gr)
    ImageView sameGr;
    @BindView(R.id.same_sj)
    ImageView sameSj;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    @BindView(R.id.same_newpublic)
    TextView sameNewpublic;
    @BindView(R.id.same_recently)
    TextView sameRecently;
    @BindView(R.id.same_hotsearch)
    TextView sameHotsearch;
    @BindView(R.id.same_pop)
    LinearLayout samePop;
    @BindView(R.id.bg)
    RelativeLayout bg;
    private SameItemAdapter mSameItemAdapter;
    private Banner mBanner;
    private RadioGroup mRgzhishiqi;
    private int page = 1;
    private LogineDialog logineDialog;
    private List<SameBean.DataBean> citys = new ArrayList<>();
    private int types = 1;
    private String homePage = "1";
    private String bannerType = "4";
    List<String> images = new ArrayList<>();
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private double mLatitude;
    private double mLongitude;
    private static PopupWindow popupWindow;
    private TranslateAnimation translateAniShow, translateAniHide, leftAniShow, rightAniHide;
    private AlphaAnimation alphaAniShow, alphaAniHide;
    private boolean onclick = false;
    private boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_same);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_same;
    }

    @Override
    protected void initView() {

        initcitylocation();
        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(false);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SameActivity.this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        View view = LayoutInflater.from(SameActivity.this).inflate(R.layout.banner_public, null);
        mBanner = view.findViewById(R.id.public_banner);
        mRgzhishiqi = view.findViewById(R.id.rg_zhishiqi);

        tbAnimation();
        alphaAnimation();
        sameXrecyclerview.addHeaderView(view);
        sameXrecyclerview.setItemAnimator(null);
        sameXrecyclerview.setItemViewCacheSize(10);
        sameXrecyclerview.setPullRefreshEnabled(false);
        sameXrecyclerview.setLoadingMoreEnabled(false);
        sameXrecyclerview.setFocusableInTouchMode(false);
        sameXrecyclerview.setLayoutManager(layoutManager);

        //滑动recyclerview显示发布按钮
        sameXrecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    /*正在拖拽*/
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        //滑动时隐藏
                        bottom.setVisibility(View.GONE);
                        bottom.startAnimation(translateAniHide);
                        break;
                    /*滑动停止*/
                    case RecyclerView.SCROLL_STATE_IDLE:
                        bottom.setVisibility(View.VISIBLE);
                        bottom.startAnimation(translateAniShow);

                        break;
                    /*惯性滑动中*/
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        bottom.setVisibility(View.GONE);
                        bottom.startAnimation(translateAniHide);

                        break;
                }
            }
        });

    }

    //位移动画
    private void tbAnimation() {


        //向上位移显示动画  从自身位置的最下端向上滑动了自身的高度
        translateAniShow = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                2,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        translateAniShow.setRepeatMode(Animation.REVERSE);
        translateAniShow.setDuration(1000);

        //向下位移隐藏动画  从自身位置的最上端向下滑动了自身的高度
        translateAniHide = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                2);//fromXValue表示结束的Y轴位置
        translateAniHide.setRepeatMode(Animation.REVERSE);
        translateAniHide.setDuration(1000);


        //向左位移显示动画  从自身位置的最下端向上滑动了自身的高度
        leftAniShow = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                1,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        translateAniShow.setRepeatMode(Animation.REVERSE);
        translateAniShow.setDuration(1000);

        //向右位移隐藏动画  从自身位置的最上端向下滑动了自身的高度
        rightAniHide = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                1,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        translateAniHide.setRepeatMode(Animation.REVERSE);
        translateAniHide.setDuration(1000);
    }

    //透明度动画
    private void alphaAnimation() {
        //显示
        alphaAniShow = new AlphaAnimation(0, 1);//百分比透明度，从0%到100%显示
        alphaAniShow.setDuration(1000);//一秒

        //隐藏
        alphaAniHide = new AlphaAnimation(1, 0);
        alphaAniHide.setDuration(1000);
    }

    @Override
    protected void getData() {
        Log.v(TAG, "---------getData-------");

        if (logineDialog == null) {
            logineDialog = new LogineDialog(SameActivity.this, "正在加载");
        }
        logineDialog.show();
        getBanner(homePage, homePage, bannerType);
        initcitylocation();
        getSame(types);
    }

    @Override
    protected void setData() {
        Log.v(TAG, "---------setData-------");
        initcitylocation();
    }

    /**
     * 同城活动列表
     */
    private void getSame(int types) {
        Log.v(TAG, "---------getSame-------types=="+types);
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.SAMELIST).addParams("type", String.valueOf(types)).addParams("page", String.valueOf(page)).addParams(ConstantsBean.SIZE, "").addParams("lat", String.valueOf(mLatitude)).addParams("lng", String.valueOf(mLongitude))
                .build().connTimeOut(10000).readTimeOut(50000).writeTimeOut(10000).execute(new StringCallback() {
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
                if (page > 1) {
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishRefresh();
                }
                if (!TextUtils.isEmpty(s)) {
                    if (s.contains("接口异常")) {
                        if (mSameItemAdapter == null) {
                            mSameItemAdapter = new SameItemAdapter(SameActivity.this, citys);
                            sameXrecyclerview.setAdapter(mSameItemAdapter);
                        } else {
                            mSameItemAdapter.notifyDataSetChanged();
                        }
                    } else {
                        SameBean sameBean = (SameBean) JsonUtil.fromJson(s, SameBean.class);
                        if (sameBean.getCode() == 0) {
                            if (sameBean.getData().size() > 0) {
                                citys.addAll(sameBean.getData());
                                if (mSameItemAdapter == null) {
                                    mSameItemAdapter = new SameItemAdapter(SameActivity.this, citys);
                                    sameXrecyclerview.setAdapter(mSameItemAdapter);

                                    mSameItemAdapter.setOnSameItemClick(new SameItemAdapter.OnSameItemClick() {
                                        @Override
                                        public void onSameitem(String UserId, String ActivityId) {
                                            Intent intent = new Intent(SameActivity.this, SameDetailActivity.class);
                                            intent.putExtra("userId", UserId);
                                            intent.putExtra("activityId", ActivityId);
                                            intent.putExtra("latitude", mLatitude);
                                            intent.putExtra("longtitude", mLongitude);
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    mSameItemAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    /**
     * 同城活动轮播
     */
    private void getBanner(final String p, String size, String s1) {
        Log.v(TAG, "---------getBanner-------");
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.HOMEDATA).addParams(ConstantsBean.PAGE, p).addParams("type", s1).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                //ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    BannerDataBean homeDataBean = (BannerDataBean) JsonUtil.fromJson(s, BannerDataBean.class);
                    if (homeDataBean.getCode() == 0 && homeDataBean.getData() != null && homeDataBean.getData().getRows().size() > 0) {
                        BannerDataBean.DataBean data = homeDataBean.getData();
                        mBanner.clearDisappearingChildren();
                        images.clear();
                        mRgzhishiqi.clearDisappearingChildren();
                        initBanner(data.getRows());
                    } else if (homeDataBean.getCode() == 1) {
                        ToastUtil.show(homeDataBean.getMsg() + "");
                    }
                }
            }
        });
    }

    /**
     * 首页轮播
     *
     * @param bannerBeans
     */
    private void initBanner(final List<BannerDataBean.DataBean.RowsBean> bannerBeans) {
        Log.v(TAG, "bannerBeans.size()==" + bannerBeans.size() + " ------images.size==" + images.size());
        for (int i = 0; i < bannerBeans.size(); i++) {
            images.add(bannerBeans.get(i).getFilepath());
            Log.e(TAG, "bannerBeans.get(i).getFilepath()" + bannerBeans.get(i).getFilepath());
        }
        for (int i = 0; i < bannerBeans.size(); i++) {
            RadioButton radioButton = new RadioButton(SameActivity.this);
            radioButton.setBackgroundResource(R.drawable.banner_sell);
            radioButton.setButtonDrawable(null);
            mRgzhishiqi.addView(radioButton);
        }
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        Log.e(TAG, "-----images---" + images);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e(TAG, "---setOnBannerListener--position---" + position);
            }
        });

        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mRgzhishiqi.check(position + 1);
                for (int i = 0; i < mRgzhishiqi.getChildCount(); i++) {
                    View childAt = mRgzhishiqi.getChildAt(i);
                    LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)
                            childAt.getLayoutParams(); //取控件textView当前的布局参数
                    linearParams.height = dip2px(3);
                    linearParams.width = dip2px(4);
                    linearParams.setMargins(dip2px(2), dip2px(2), dip2px(2), dip2px(2));
                    childAt.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
                }
                View childAt = mRgzhishiqi.getChildAt(position);
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
        mBanner.start();

//        homeBanner.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Log.v(TAG, "--homeBanner.setOnItemClickListener--" + position);
//                ActivityUtils.skipActivity(CustomApplication.context, WebShowActivity.class, 0, "");
////                ActivityUtils.skipActivity(CustomApplication.context, WebShowActivity.class,
////                        bannerBeans.get(position).getId(), bannerBeans.get(position).getSummary());
//            }
//        });
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

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK) {
            refreshLayout.finishLoadMore();
        } else {
            page++;
            getBanner(homePage, homePage, bannerType);
            getSame(types);
        }
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK) {
            refreshLayout.finishRefresh();
        }
        page = 1;
        getBanner(homePage, homePage, bannerType);
        getSame(types);
    }

    @OnClick({R.id.same_left, R.id.same_seachs, R.id.same_filtrate,R.id.same_newpublic,R.id.same_recently,R.id.same_hotsearch, R.id.same_public, R.id.same_gr, R.id.same_sj,R.id.bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.same_left:
                //返回首页
                finish();
                break;
             case R.id.same_newpublic:
                //最新发布
                 ToastUtil.show("最新发布");
                 samePop.setVisibility(View.GONE);
                 bg.setVisibility(View.GONE);
                 isVisible = false;
                 types = 2;
                 onRefresh(refreshLayout);
                 getSame(types);

                break;
             case R.id.same_recently:
                //离我最近
                 ToastUtil.show("离我最近");
                 samePop.setVisibility(View.GONE);
                 bg.setVisibility(View.GONE);
                 isVisible = false;
                 types = 3;
                 onRefresh(refreshLayout);
                 getSame(types);

                break;
             case R.id.same_hotsearch:
                //热门搜索
                 ToastUtil.show("热门搜索");
                 samePop.setVisibility(View.GONE);
                 bg.setVisibility(View.GONE);
                 isVisible = false;
                 types = 1;
                 onRefresh(refreshLayout);
                 getSame(types);

                break;
            case R.id.same_seachs://搜索标签
                Log.v(TAG, "----onViewClicked()---------same_seach");
                ActivityUtils.skipActivity(SameActivity.this, SearchLabelActivity.class, 0, "");

                break;
            case R.id.same_filtrate://筛选选择
                Log.v(TAG, "----onViewClicked()---------same_filtrate");
                if (isVisible ==false){
                    //显示
                    samePop.setVisibility(View.VISIBLE);
                    bg.setVisibility(View.VISIBLE);
                    bg.getBackground().setAlpha(200);//0~255透明度值
                    isVisible = true;
                }else {
                    //隐藏
                    isVisible = false;
                    samePop.setVisibility(View.GONE);
                    bg.setVisibility(View.GONE);
                }

                break;
                case R.id.bg:
                     samePop.setVisibility(View.GONE);
                     bg.setVisibility(View.GONE);
                    isVisible = false;

                break;

            case R.id.same_public://发布按钮
                Log.v(TAG, "----onViewClicked()---------same_hotsearch");
                if (onclick == false) {
                    Log.v("1212232", "正旋转");
                    samepublic.animate().rotation(45).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            samepublic.setRotation(45);
                            samepublic.setEnabled(true);
                        }

                    });
                    bottom.setBackgroundResource(R.drawable.same_bottombg);
                    bottom.getBackground().setAlpha(100);//0~255透明度值
                    sameGr.startAnimation(leftAniShow);
                    sameSj.startAnimation(rightAniHide);
                    sameGr.setVisibility(View.VISIBLE);
                    sameSj.setVisibility(View.VISIBLE);

                    onclick = true;
                } else {
                    Log.v("1212232", "反旋转");
                    samepublic.animate().rotation(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            samepublic.setRotation(0);
                            samepublic.setEnabled(true);
                        }
                    });
                    bottom.setBackground(null);
                    sameGr.startAnimation(rightAniHide);
                    sameSj.startAnimation(leftAniShow);
                    sameGr.setVisibility(View.GONE);
                    sameSj.setVisibility(View.GONE);
                    onclick = false;
                }
                break;
            case R.id.same_gr://个人发布按钮
                Log.v(TAG, "----onViewClicked()---------same_hotsearch");
                ActivityUtils.skipActivity(SameActivity.this, SamePublicActivity.class, 0, "");

                break;
            case R.id.same_sj://商家发布按钮
                Log.v(TAG, "----onViewClicked()---------same_sj");
                //查询登陆用户是否是VIP，不是则不能进行同城发布
//                if (UserTask.getInstance().getUserInfoData().isVip()) {
                ActivityUtils.skipActivity(SameActivity.this, MerchantRZActivity.class, 0, "");
//                } else {
//                    CommonDialog.getTiShiDialog(SameActivity.this, ConstantsBean.CHATJIEBAN, new OnButtonClick() {
//                        @Override
//                        public void button01ClickListener() {
//                            ActivityUtils.skipActivity(SameActivity.this, BuyVIPActivity.class, 0, "");
//                        }
//
//                        @Override
//                        public void button02ClickListener() {
//
//                        }
//                    });
//                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "----onDestroy()-----");
    }

    private void initcitylocation() {
        mLocationClient = new LocationClient(CustomApplication.context);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            //获取位置描述信息
            //获取纬度信息
            mLatitude = location.getLatitude();
            //获取经度信息
            mLongitude = location.getLongitude();
            Log.v(TAG, "-----latitude==" + mLatitude + "------longitude==" + mLongitude);
        }
    }

}
