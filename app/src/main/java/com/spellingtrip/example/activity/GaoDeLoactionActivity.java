package com.spellingtrip.example.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.spellingtrip.example.R;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.viewpager.GaoDeAdapter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GaoDeLoactionActivity extends AppCompatActivity implements LocationSource,AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener, View.OnClickListener { // Inputtips.InputtipsListener
    private ListView listView;
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private String[] items = {"", "住宅区", "商场", "学校"};
    private LogineDialog progDialog = null;
    private GeocodeSearch geocoderSearch;
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private List<PoiItem> poiItems;// poi数据
    private String searchType = items[0];
    private String searchKey = "";
    private LatLonPoint searchLatlonPoint;
    private List<PoiItem> resultData;
    private GaoDeAdapter searchResultAdapter;
    private boolean isItemClickAction;
    // private PoiItem firstItem;
    private TextView tvGetAddress, tv_lantitle;
    private String title;
    private boolean useThemestatusBarColor=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        setContentView(R.layout.activity_location);

        if (progDialog==null){
            progDialog = new LogineDialog(this,"正在加载");
        }
        progDialog.show();
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        tvGetAddress = (TextView) findViewById(R.id.tvGetAddress);
        tv_lantitle = (TextView) findViewById(R.id.tv_lantitle);
        tv_lantitle.setText("选择位置");
        findViewById(R.id.iv_left).setOnClickListener(this);
        init();
        initView();
        resultData = new ArrayList<>();
    }

    protected void initView() {
        tvGetAddress.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview);
        searchResultAdapter = new GaoDeAdapter(this);
        listView.setAdapter(searchResultAdapter);
        listView.setOnItemClickListener(onItemClickListener);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position != searchResultAdapter.getSelectedPosition()) {
                PoiItem poiItem = (PoiItem) searchResultAdapter.getItem(position);
                taskaddress = poiItem.getSnippet();
                latitude = poiItem.getLatLonPoint().getLatitude() + "";
                longitube = poiItem.getLatLonPoint().getLongitude() + "";
                province = poiItem.getProvinceName();
                city = poiItem.getCityName();
                county = poiItem.getAdName();
                title=poiItem.getTitle();
                LatLng curLatlng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
                isItemClickAction = true;
                //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
                searchResultAdapter.setSelectedPosition(position);
                searchResultAdapter.notifyDataSetChanged();
            }
        }
    };

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (!isItemClickAction){
                    geoAddress();
                }
                searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                isItemClickAction = false;
            }
        });
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
              //  addMarkerInScreenCenter(null);
            }
        });
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
       // mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);
                LatLng curLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                latitude = curLatlng.latitude + "";
                longitube = curLatlng.longitude + "";
                searchLatlonPoint = new LatLonPoint(curLatlng.latitude, curLatlng.longitude);
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
                isInputKeySearch = false;
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
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
              //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位间隔,单位毫秒,默认为2000ms
            //mLocationOption.setInterval(2000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
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
     * 响应逆地理编码
     */
    public void geoAddress() {
        if (searchLatlonPoint != null) {
            RegeocodeQuery query = new RegeocodeQuery(searchLatlonPoint, 1000, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            geocoderSearch.getFromLocationAsyn(query);
        }
    }

    /**
     * 开始进行poi搜索
     */
    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        currentPage = 0;
        query = new PoiSearch.Query(searchKey, searchType, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setCityLimit(true);
        query.setPageSize(20);
        query.setPageNum(currentPage);
        if (searchLatlonPoint != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 1000, true));//
            poiSearch.searchPOIAsyn();
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                doSearchQuery();
            }
        } else {
            Toast.makeText(GaoDeLoactionActivity.this, "error code is " + rCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * POI搜索结果回调
     *
     * @param poiResult  搜索结果
     * @param resultCode 错误码
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode) {
        if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (poiResult.getQuery().equals(query)) {
                    poiItems = poiResult.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        updateListview(poiItems);
                    } else {
                        Toast.makeText(GaoDeLoactionActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(GaoDeLoactionActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 更新列表中的item
     *
     * @param poiItems
     */
    private void updateListview(List<PoiItem> poiItems) {
        resultData.clear();
        searchResultAdapter.setSelectedPosition(0);
        //   resultData.add(firstItem);
        resultData.addAll(poiItems);
        searchResultAdapter.setData(resultData);
        searchResultAdapter.notifyDataSetChanged();
        city=poiItems.get(0).getCityName();
        county=poiItems.get(0).getAdName();
        title=poiItems.get(0).getTitle();
       if (progDialog.isShowing()){
            progDialog.dismiss();
       }

    }


    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private String taskaddress, latitude, longitube, province, city, county, detail;

    private boolean isInputKeySearch;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGetAddress:
                setResult(RESULT_OK, new Intent().putExtra("city", city+county+title));
                finish();
                break;
            case R.id.iv_left:
                finish();
                break;
        }
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
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
