package com.spellingtrip.example.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.autonavi.ae.route.model.GeoPoint;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.BannerBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.HomeDataBean;
import com.spellingtrip.example.bean.LivesDetailsBean;
import com.spellingtrip.example.dialog.BannerInfoImgFragment;
import com.spellingtrip.example.dialog.GoodsInfoImgFragment;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.view.LiveNetworkImageHolderView;
import com.spellingtrip.example.view.MapContainer;
import com.spellingtrip.example.view.MyLayoutManager;
import com.spellingtrip.example.view.NetworkImageHolderView;
import com.spellingtrip.example.view.ScrollGridView;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.FurnitureAdapter;
import com.spellingtrip.example.viewpager.RecyclerAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class LiveDetailActivity extends AppCompatActivity {
    @BindView(R.id.liveBanner)
    public ConvenientBanner liveBanner;
    @BindView(R.id.tvLiveDetailTitle)
    public TextView tvLiveDetailTitle;
    @BindView(R.id.tvLivesDetailContent)
    public TextView tvLivesDetailContent;
    @BindView(R.id.tvLivesDetailAddress)
    public TextView tvLivesDetailAddress;
    @BindView(R.id.tvLivesDetailTel)
    public TextView tvLivesDetailTel;
    @BindView(R.id.rlLivesType)
    public RecyclerView rlLivesType;
    @BindView(R.id.liveDetailGridView)
    public ScrollGridView scrollGridView;
    @BindView(R.id.liveMap)
    public MapView mMapView;
    @BindView(R.id.iv_left)
    public ImageView iv_left;
    @BindView(R.id.tv_lantitle)
    public TextView tv_lantitle;
    @BindView(R.id.tvLiveDetailOldPrice)
    public TextView tvOldPrice;
    @BindView(R.id.tvLiveDetailPrice)
    public TextView tvLiveDetailPrice;
    @BindView(R.id.tvYuDing)
    public TextView tvYuDing;
    @BindView(R.id.lvScrollView)
    public ScrollView scrollView;
    @BindView(R.id.map_container)
    public MapContainer mapContainer;
    private LivesDetailsBean livesDetailsBean;
    private boolean useThemestatusBarColor=false;
    private AMap aMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedetail);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
         aMap = mMapView.getMap();
        initView();
        getDetail();
        //setData();
    }

    protected void initView() {
        MyLayoutManager layout = new MyLayoutManager();
        layout.setAutoMeasureEnabled(true);
        rlLivesType.addItemDecoration(new SpacesItemDecoration(20));
        rlLivesType.setLayoutManager(layout);
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
        mMapView.getMap().getUiSettings().setZoomControlsEnabled(false);
        mMapView.getMap().getUiSettings().setMyLocationButtonEnabled(true);
        mapContainer.setScrollView(scrollView);
        tv_lantitle.setText("民宿详情");
    }

  /*  @Override
    protected void getData() {
        getDetail();
    }
*/
    private void getDetail() {
        String id = getIntent().getStringExtra("title");
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.LIVESID).addParams("id", id).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                     livesDetailsBean = (LivesDetailsBean) JsonUtil.fromJson(s, LivesDetailsBean.class);
                    if (livesDetailsBean.getCode() == 0 && livesDetailsBean.getData() != null) {
                        setDetail(livesDetailsBean.getData());
                    } else {

                    }
                } else {

                }
            }
        });
    }

    private void setDetail(LivesDetailsBean.DataBean data) {
        if (data.getImages() != null) {
            List<BannerBean> banners = new ArrayList<>();
            for (int i = 0; i < data.getImages().size(); i++) {
                BannerBean bannerBean = new BannerBean();
                bannerBean.setFilepath(data.getImages().get(i));
                banners.add(bannerBean);
            }
            initBanner(banners);
        }
        tvLiveDetailTitle.setText(data.getTitle());
        tvLivesDetailContent.setText(data.getIntro());
        tvLivesDetailAddress.setText("位置： " + data.getLocation());
        tvLivesDetailTel.setText("联系方式： " + data.getTell());
        tvLiveDetailPrice.setText("¥ "+data.getPrice()+"/每晚");
        tvOldPrice.setText("¥ "+data.getOriginalPrice());
        if (data.getRoomType() != null && data.getRoomType().size() > 0) {
            setType(data.getRoomType());
        }
        if (data.getRoomFurniture() != null && data.getRoomFurniture().size() > 0) {
            setFurniture(data.getRoomFurniture());
        }
        //绘制marker
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(data.getLocation());
        markerOptions.visible(true);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.biaoji));
        markerOptions.icon(bitmapDescriptor);
        markerOptions.draggable(true);
        Marker marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
        LatLng latLonPoint = new LatLng(data.getLat(), data.getLng());
        mMapView.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLonPoint, 16f));
    }

    /**
     * 介绍
     *
     * @param roomFurniture
     */
    private void setFurniture(List<String> roomFurniture) {
        final FurnitureAdapter adapter = new FurnitureAdapter(this, roomFurniture);
        scrollGridView.setAdapter(adapter);
    }

    /**
     * 类型
     *
     * @param roomType
     */
    private void setType(List<String> roomType) {
        final RecyclerAdapter adapter = new RecyclerAdapter(this, roomType);
        rlLivesType.setAdapter(adapter);
    }

    /**
     * 轮播
     *
     * @param data
     */
    private void initBanner(final List<BannerBean> data) {
        liveBanner.setPages(new CBViewHolderCreator() {
            @Override
            public LiveNetworkImageHolderView createHolder() {
                return new LiveNetworkImageHolderView();
            }
        }, data);
        if (data != null && data.size() > 1) {
            liveBanner.setCanLoop(true);
            liveBanner.startTurning(5000);
            //  homeBanner.setPageIndicator(new int[]{R.mipmap.guide_nor, R.mipmap.guide_sel}).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        } else {
            liveBanner.setCanLoop(false);
        }
        liveBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               BannerInfoImgFragment fragment = new BannerInfoImgFragment();
                fragment.setData( data, position);
                fragment.show(getFragmentManager(), "DialogFragment");
            }
        });
    }
    @SuppressLint("MissingPermission")
    @OnClick({R.id.iv_left,R.id.tvYuDing})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.tvYuDing:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + livesDetailsBean.getData().getTell());
                intent.setData(data);
                startActivity(intent);
               // ActivityUtils.skipActivity(this,YuDingActivity.class,0,livesDetailsBean.getData().getTell());
                break;
        }
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
   /* @Override
    protected void setData() {
        backClick();
        setCenterTitle("民宿详情");
    }*/
   protected void setStatusBar() {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           View decorView = getWindow().getDecorView();
           int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                   | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
           decorView.setSystemUiVisibility(option);
           if (useThemestatusBarColor) {
               getWindow().setStatusBarColor(getResources().getColor(R.color.articleTitle));
           } else {
               getWindow().setStatusBarColor(Color.TRANSPARENT);
           }
       } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
           getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
       }
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
           getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                   |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
       }
   }
}
