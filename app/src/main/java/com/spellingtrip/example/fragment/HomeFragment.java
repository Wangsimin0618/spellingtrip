package com.spellingtrip.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.spellingtrip.example.activity.HotCitysActivity;
import com.spellingtrip.example.activity.WebShowActivity;
import com.spellingtrip.example.bean.ActivityAABean;
import com.spellingtrip.example.bean.BannerDataBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.HomeDataBean;
import com.spellingtrip.example.bean.HomePiPeiBean;
import com.spellingtrip.example.bean.MessageEvent;
import com.spellingtrip.example.city.CityAddressActivity;
import com.spellingtrip.example.city.CityseachActivity;
import com.spellingtrip.example.city.SameActivity;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.viewpager.GlideImageLoader;
import com.spellingtrip.example.viewpager.HomeitemAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
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
import butterknife.Unbinder;
import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends BaseFragment implements View.OnClickListener, OnRefreshLoadMoreListener {
    public static final String TAG = "HomeFragment";
    @BindView(R.id.home_txt_location)
    TextView homeTxtLocation;
    @BindView(R.id.home_img_pull)
    ImageView homeImgPull;
    @BindView(R.id.home_img_seach)
    ImageView homeImgSeach;
    Unbinder unbinder;
    private List<HomeDataBean.DataBean.RowsBean> lists;
    public Banner homeBanner;
    @BindView(R.id.lvPiCityDetailList)
    public XRecyclerView xRecyclerView;
    @BindView(R.id.piCityRefreshLayout)
    public SmartRefreshLayout refreshLayout;
    private List<ActivityAABean.DataBean> citys = new ArrayList<>();
    private HomeitemAdapter aaCityAdapter;
    private int page = 1;
    private String homePage = "1";
    private String bannerType = "4";
    private LogineDialog logineDialog;
    private RadioButton mTabHotcity;
    private RadioButton mTabSameactvity;
    private RadioButton mTabHotpath;
    private RadioButton mTabMerchantsalliance;
    private int STRAT_CITY_CODE = 101;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private String addr = null;
    private RadioGroup mRg_zhishiqi;
    List<String> images = new ArrayList<>();
    private TabLayout hometableyout;
    private int type = 1;
    private TextView mNavigationNewest;
    private TextView mNavigationHottogether;
    private TextView mNavigationVIPscreen;
    private Drawable mDrawable;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void findView(View fromhear) {
        EventBus.getDefault().register(this);

        refreshLayout.setOnRefreshLoadMoreListener(this);
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(false);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.minsu_header, null);
        homeBanner = view.findViewById(R.id.homeBanner);
        mRg_zhishiqi = view.findViewById(R.id.mRg_zhishiqi);

        //导航栏
        mTabHotcity = view.findViewById(R.id.tab_hotcity);
        mTabSameactvity = view.findViewById(R.id.tab_sameactvity);
        mTabHotpath = view.findViewById(R.id.tab_hotpath);
        mTabMerchantsalliance = view.findViewById(R.id.tab_merchantsalliance);

        mNavigationNewest = view.findViewById(R.id.navigation_newest);
        mNavigationHottogether = view.findViewById(R.id.navigation_Hottogether);
        mNavigationVIPscreen = view.findViewById(R.id.navigation_VIPscreen);
        initnavi();//导航栏下标线条的显示和影藏
        initlistener();//点击监听
        initcitylocation();

        xRecyclerView.addHeaderView(view);
        xRecyclerView.setItemAnimator(null);
        xRecyclerView.setItemViewCacheSize(10);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setFocusableInTouchMode(false);
        xRecyclerView.setLayoutManager(layoutManager);
    }
    private void initnavi() {
        //默认最新推荐字体加粗
        mNavigationNewest.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        mNavigationHottogether.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//取消加粗
        mNavigationVIPscreen.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//取消加粗
        //默认显示最新推荐列表下线条
        mDrawable = getResources().getDrawable(R.mipmap.blue_link);
        mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
        mNavigationNewest.setCompoundDrawables(null, null, null, mDrawable);
        mNavigationHottogether.setCompoundDrawables(null, null, null, null);
        mNavigationVIPscreen.setCompoundDrawables(null, null, null, null);
        mNavigationNewest.setTextColor(getResources().getColor(R.color.buyvip));
        mNavigationHottogether.setTextColor(getResources().getColor(R.color.buyvips));
        mNavigationVIPscreen.setTextColor(getResources().getColor(R.color.buyvips));
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

    private void initlistener() {//首页点击监听
        mTabHotcity.setOnClickListener(this);
        mTabSameactvity.setOnClickListener(this);
        mTabHotpath.setOnClickListener(this);
        mTabMerchantsalliance.setOnClickListener(this);

        mNavigationNewest.setOnClickListener(this);
        mNavigationHottogether.setOnClickListener(this);
        mNavigationVIPscreen.setOnClickListener(this);

    }


    @Override
    protected void getData() {
        Log.v(TAG, "---------getData-------");

        if (logineDialog == null) {
            logineDialog = new LogineDialog(getActivity(), "正在加载");
        }
        logineDialog.show();
        if (citys != null && citys.size() > 0) {
            citys.clear();
        }
        initcitylocation();//获取定位传递过来的值
        mRg_zhishiqi.clearDisappearingChildren();

        getHomeData(homePage, homePage, bannerType);

        getAA(type);
    }

    //首页轮播数据请求
    private void getHomeData(final String p, String size, String s1) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.HOMEDATA).addParams(ConstantsBean.PAGE, p).addParams("type", s1).build().readTimeOut(50000).execute(new StringCallback() {
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
                        homeBanner.clearDisappearingChildren();
                        images.clear();
                        mRg_zhishiqi.clearDisappearingChildren();
                        initBanner(data.getRows());
                    } else if (homeDataBean.getCode() == 1) {
                        ToastUtil.show(homeDataBean.getMsg() + "");
                    }
                }
            }
        });
    }

    @Override
    protected void setData() {
        Log.v(TAG, "---------setData-------");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getTourMach();
            }
        }, 1000);
        initcitylocation();
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
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setBackgroundResource(R.drawable.banner_sell);
            radioButton.setButtonDrawable(null);
            mRg_zhishiqi.addView(radioButton);
        }
        homeBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        homeBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        homeBanner.setImages(images);
        Log.e(TAG, "-----images---" + images);
        homeBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e(TAG, "---setOnBannerListener--position---" + position);
            }
        });

        homeBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mRg_zhishiqi.check(position + 1);
                for (int i = 0; i < mRg_zhishiqi.getChildCount(); i++) {
                    View childAt = mRg_zhishiqi.getChildAt(i);
                    LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)
                            childAt.getLayoutParams(); //取控件textView当前的布局参数
                    linearParams.height = dip2px(3);
                    linearParams.width = dip2px(4);
                    linearParams.setMargins(dip2px(2), dip2px(2), dip2px(2), dip2px(2));
                    childAt.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
                }
                View childAt = mRg_zhishiqi.getChildAt(position);
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
        homeBanner.start();

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
    public void onStart() {
        Log.v(TAG, "---------onStart-------");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.v(TAG, "---------onResume-------");
        super.onResume();
    }

    @OnClick({R.id.home_img_pull, R.id.home_img_seach})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_img_pull://选择城市
                Intent intent = new Intent(CustomApplication.context, CityAddressActivity.class);
                intent.putExtra("addr", addr);
                startActivity(intent);

                break;
            case R.id.home_img_seach://顶部搜索框
                startActivityForResult(new Intent(CustomApplication.context, CityseachActivity.class), STRAT_CITY_CODE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_hotcity://热门城市
                Log.v(TAG, "------onClick()---------tab_hotcity");
                Intent intentHotCitys = new Intent(CustomApplication.context, HotCitysActivity.class);
                startActivity(intentHotCitys);
                break;
            case R.id.tab_sameactvity://同城活动
                Log.v(TAG, "------onClick()---------tab_sameactvity");
                Intent intentsameactvity = new Intent(CustomApplication.context, SameActivity.class);
                startActivity(intentsameactvity);
                break;
            case R.id.tab_hotpath://热门路线
                Log.v(TAG, "------onClick()---------tab_hotpath");
                ToastUtil.show("暂未开发");

                break;
            case R.id.tab_merchantsalliance://商家联盟
                Log.v(TAG, "------onClick()---------tab_merchantsalliance");
                ToastUtil.show("暂未开发");

                break;
            case R.id.navigation_newest://最新推荐
                Log.v(TAG, "------onClick()---------navigation_newest");
                type = 1;
               onRefresh(refreshLayout);
                 getAA(type);
                initnew();

                break;

            case R.id.navigation_Hottogether://热门结伴
                Log.v(TAG, "------onClick()---------navigation_Hottogether");
                type = 2;
                onRefresh(refreshLayout);
                getAA(type);
                inithot();


                break;

            case R.id.navigation_VIPscreen://VIP筛选
                Log.v(TAG, "------onClick()---------navigation_VIPscreen");
                type = 3;
                onRefresh(refreshLayout);
                getAA(type);
                initVIP();

        }
    }


    private void getAA(int type) {
        Log.v(TAG, "-----getAA()----type==" + type);
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.AALIST).addParams("city", "").addParams(ConstantsBean.TYPE, String.valueOf(type)).addParams(ConstantsBean.PAGE, String.valueOf(page))
                .build().readTimeOut(50000).execute(new StringCallback() {
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
                        setAA(citys);
                    } else {
                        ActivityAABean messageBean = (ActivityAABean) JsonUtil.fromJson(s, ActivityAABean.class);
                        if (messageBean.getCode() == 0) {
                            if (messageBean.getData().size() > 0) {
                                citys.addAll(messageBean.getData());
                                setAA(citys);
                            }
                        }
                    }
                }
            }
        });
    }

    private void setAA(List<ActivityAABean.DataBean> citys) {
        if (aaCityAdapter == null) {
            aaCityAdapter = new HomeitemAdapter(getActivity(), citys, "home");
            xRecyclerView.setAdapter(aaCityAdapter);
        } else {
            aaCityAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.PUBLISH_ACTIVITY)) {
            onRefresh(refreshLayout);
        }
        if (data.getType().equals(ConstantsBean.AUTOPI)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getTourMach();
                }
            }, 1200);
        }
        if (data.getType().equals(ConstantsBean.HAVENETWORK)) {
            page = 1;
            getData();
        }
        if (data.getType().equals(ConstantsBean.NONETWORK)) {
            if(refreshLayout !=null){
                refreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK) {
            refreshLayout.finishLoadMore();
        } else {
            page++;
            getAA(type);
            initcitylocation();

        }
        // refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (!ConstantsBean.ISHANENETWORK) {
            refreshLayout.finishRefresh();
        } else {
            page = 1;
            getData();
            getTourMach();
            initcitylocation();

        }
    }

    /**
     * 首页弹框
     */
    private void getTourMach() {
        String userId = null;
        if (UserTask.getInstance() != null && UserTask.getInstance().getUser() != null) {
            userId = String.valueOf(UserTask.getInstance().getUser().getUserId());
        }
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.PIPEIING + userId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                Log.e("getTourMachonError", JsonUtil.toJson(e));
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    HomePiPeiBean homePiPeiBean = (HomePiPeiBean) JsonUtil.fromJson(s, HomePiPeiBean.class);
                    if (homePiPeiBean.getCode() == 0 && homePiPeiBean.getData() != null && homePiPeiBean.getData().getUserInfo() != null
                            && homePiPeiBean.getData().getTour() != null) {
                        setPiPeiPop(homePiPeiBean.getData());
                    }
                }
            }
        });
    }

    private void setPiPeiPop(HomePiPeiBean.DataBean data) {
        CommonDialog.getPiPeiTiShi(getActivity(), data);
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "---------onDestroy-------");
        //处理dialog异常
        if (logineDialog != null && logineDialog.isShowing()) {
            logineDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        Log.v(TAG, "---------onDestroyView-------");
        super.onDestroyView();
        unbinder.unbind();
    }

    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            //获取位置描述信息
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            String locationDescribe = location.getLocationDescribe();
            //获取详细地址信息
            addr = location.getAddrStr();
            String city = location.getCity();//城市
            Log.v(TAG, "---MyLocationListener---locationDescribe==" + locationDescribe + "-----------addr==" + addr+"---latitude=="+latitude+"----longitude=="+longitude);

            if (addr == null || city == null) {
                Log.v(TAG, "---MyLocationListener---------addr == null || city == null");
                homeTxtLocation.setText("未定位");
                return;
            }
            homeTxtLocation.setText(city);
            SharedPreferences sp = getActivity().getSharedPreferences("lat_log", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("latitude",latitude+"");
            editor.putString("longitude",longitude+"");
            editor.commit();


        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {
        Log.v(TAG, "------enent.message==" + event.message);
        if (event.message == null) {
            return;
        }
        homeTxtLocation.setText(event.message);
    }

    //最新推荐字号，粗细，下标线条
    private void initnew(){
        //显示最新推荐下标
        mNavigationNewest.setTextSize(TypedValue.COMPLEX_UNIT_SP,22); //22SP
        mNavigationHottogether.setTextSize(TypedValue.COMPLEX_UNIT_SP,16); //22SP
        mNavigationVIPscreen.setTextSize(TypedValue.COMPLEX_UNIT_SP,16); //22SP
        mNavigationNewest.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        mNavigationHottogether.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//取消加粗
        mNavigationVIPscreen.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//取消加粗
        mNavigationNewest.setCompoundDrawables(null, null, null, mDrawable);
        mNavigationHottogether.setCompoundDrawables(null, null, null, null);
        mNavigationVIPscreen.setCompoundDrawables(null, null, null, null);
        mNavigationNewest.setTextColor(getResources().getColor(R.color.buyvip));
        mNavigationHottogether.setTextColor(getResources().getColor(R.color.buyvips));
        mNavigationVIPscreen.setTextColor(getResources().getColor(R.color.buyvips));

    }
    //最新推荐字号，粗细，下标线条
    private void inithot(){
        mNavigationNewest.setTextSize(TypedValue.COMPLEX_UNIT_SP,16); //22SP
        mNavigationHottogether.setTextSize(TypedValue.COMPLEX_UNIT_SP,22); //22SP
        mNavigationVIPscreen.setTextSize(TypedValue.COMPLEX_UNIT_SP,16); //22SP
        mNavigationNewest.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
        mNavigationHottogether.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//取消加粗
        mNavigationVIPscreen.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//取消加粗
        //显示热门结伴下标
        mNavigationHottogether.setCompoundDrawables(null, null, null, mDrawable);
        mNavigationNewest.setCompoundDrawables(null, null, null, null);
        mNavigationVIPscreen.setCompoundDrawables(null, null, null, null);
        mNavigationNewest.setTextColor(getResources().getColor(R.color.buyvips));
        mNavigationHottogether.setTextColor(getResources().getColor(R.color.buyvip));
        mNavigationVIPscreen.setTextColor(getResources().getColor(R.color.buyvips));
    }
    //VIP字号，粗细，下标线条
    private void initVIP() {
        mNavigationNewest.setTextSize(TypedValue.COMPLEX_UNIT_SP,16); //22SP
        mNavigationHottogether.setTextSize(TypedValue.COMPLEX_UNIT_SP,16); //22SP
        mNavigationVIPscreen.setTextSize(TypedValue.COMPLEX_UNIT_SP,22); //22SP
        mNavigationNewest.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
        mNavigationHottogether.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//取消加粗
        mNavigationVIPscreen.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//取消加粗
        //显示VIP筛选下标
        mNavigationVIPscreen.setCompoundDrawables(null, null, null, mDrawable);
        mNavigationNewest.setCompoundDrawables(null, null, null, null);
        mNavigationHottogether.setCompoundDrawables(null, null, null, null);
        mNavigationNewest.setTextColor(getResources().getColor(R.color.buyvips));
        mNavigationHottogether.setTextColor(getResources().getColor(R.color.buyvips));
        mNavigationVIPscreen.setTextColor(getResources().getColor(R.color.buyvip));
    }
}
