package com.spellingtrip.example.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.AddressBean;
import com.spellingtrip.example.bean.HotCityBean;
import com.spellingtrip.example.bean.MessageEvent;
import com.spellingtrip.example.bean.Proces;
import com.spellingtrip.example.bean.TwoMessageEvent;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.viewpager.MapAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BaiduMapActivity extends Activity implements AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener {
    @BindView(R.id.seach_name)
    AutoCompleteTextView seachName;
    @BindView(R.id.seach_cancel)
    TextView seachCancel;
    @BindView(R.id.recy_name)
    RecyclerView recyName;
    @BindView(R.id.seach_sure)
    TextView seachSure;
    private AMap mAmap;
    /**
     * 地图对象
     */
    private AMapLocationClient mlocationClient;
    private MapView mRouteMapView;
    private Marker mStartMarker;
    private Marker mEndMarker;


    /**
     * 途径点坐标集合
     */
    /**
     * 终点坐标集合［建议就一个终点］
     */
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private boolean useThemestatusBarColor = false;
    private Double startLat, startLon;
    private TextView tvJuLi;
    private LogineDialog progDialog = null;
    private MapAdapter mMapAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actctivity_baidumap);
        ButterKnife.bind(this);
        setStatusBar();
        initData();
        mRouteMapView = findViewById(R.id.baidu_map);
        tvJuLi = findViewById(R.id.tvBaiduJuli);
        mRouteMapView.onCreate(savedInstanceState);
        mAmap = mRouteMapView.getMap();
        // 初始化Marker添加到地图
        mStartMarker = mAmap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.start))));
        mEndMarker = mAmap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.end))));
        init();
        mMapAdapter = new MapAdapter(BaiduMapActivity.this);
        mMapAdapter.setOnItemClickListener(new MapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name, String content) {

                finish();
                EventBus.getDefault().post(new TwoMessageEvent(name,content));
            }
        });

    }

    private void initData() {
        //软键盘回车变搜索按钮
            seachName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH){
                        String input=seachName.getText().toString();
                        if(!TextUtils.isEmpty(input)){
                            PoiSearch.Query query = new PoiSearch.Query(input, "", "");
                            query.setPageSize(30);
                            query.setPageNum(0);
                            PoiSearch poiSearch = new PoiSearch(BaiduMapActivity.this, query);
                            poiSearch.setOnPoiSearchListener(BaiduMapActivity.this);
                            poiSearch.searchPOIAsyn();
                            //关闭软键盘
                            closeKeybord(BaiduMapActivity.this);

                        }
                        return true;
                    }
                    return false;
                }
            });
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mRouteMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mRouteMapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRouteMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mRouteMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        EventBus.getDefault().unregister(this);
    }


    /**
     * 初始化
     */
    private void init() {
        if (mAmap == null) {
            mAmap = mRouteMapView.getMap();
            setUpMap();
        } else {
            setUpMap();
        }

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        mAmap.getUiSettings().setLogoBottomMargin(-50);
        mAmap.getUiSettings().setZoomControlsEnabled(false);
        mAmap.setLocationSource(this);// 设置定位监听
        mAmap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        mAmap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAmap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);
                startLat = amapLocation.getLatitude();
                startLon = amapLocation.getLongitude();
                mStartMarker.setPosition(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
            } else {
                if (progDialog != null) {
                    progDialog.dismiss();
                }
            }
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void getLatlon(String cityName) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(BaiduMapActivity.this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i == 1000) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null &&
                            geocodeResult.getGeocodeAddressList().size() > 0) {
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                        LatLng llA = new LatLng(latitude, longititude);
                        mEndMarker.setPosition(new LatLng(latitude, longititude));

                        mAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(llA, 16f));
                        if (progDialog != null) {
                            progDialog.dismiss();
                        }
                        if (startLat != null && startLon != null) {
                            double latlon = CommonUtil.getRange(startLat, startLon, latitude, longititude);
//                                    String city=getIntent().getStringExtra("address");
//                                    if (latlon<1.0){
//                                        tvJuLi.setText("相距 <1km");
//                                    }else {
//                                        tvJuLi.setText("距"+city+latlon+"km");
//                                    }
                        }


                    } else {
                        if (progDialog != null) {
                            progDialog.dismiss();
                        }
                    }
                } else {
                    if (progDialog != null) {
                        progDialog.dismiss();
                    }
                }
            }
        });
        GeocodeQuery geocodeQuery = new GeocodeQuery(cityName.trim(), "29");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }

    @OnClick({R.id.seach_sure, R.id.seach_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.seach_sure:
                String keyword = seachName.getText().toString().trim();
                PoiSearch.Query query = new PoiSearch.Query(keyword, "", "");
                query.setPageSize(30);
                query.setPageNum(0);
                PoiSearch poiSearch = new PoiSearch(BaiduMapActivity.this, query);
                poiSearch.setOnPoiSearchListener(this);
                poiSearch.searchPOIAsyn();
                closeKeybord(BaiduMapActivity.this);

                break;
            case R.id.seach_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            ArrayList<AddressBean> data = new ArrayList<AddressBean>();
            ArrayList<PoiItem> items = poiResult.getPois();
            for(PoiItem item : items){
                //获取经纬度对象
                LatLonPoint llp = item.getLatLonPoint();
                double lon = llp.getLongitude();
                double lat = llp.getLatitude();
                //获取标题
                String title = item.getTitle();
                //获取内容
                String text = item.getSnippet();
                data.add(new AddressBean(lon, lat, title, text));
            }
            recyName.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
            recyName.setAdapter(mMapAdapter);
            mMapAdapter.setData(data);
            mMapAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 自动关闭软键盘
     * @param activity
     */
    public static void closeKeybord(Activity activity) {
        InputMethodManager imm =  (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}

