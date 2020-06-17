package com.spellingtrip.example.city;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapView;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.BaseActivity;
import com.spellingtrip.example.bean.MessageEvent;
import com.spellingtrip.example.fragment.HomeFragment;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * 城市定位
 */
public class CityAddressActivity extends BaseActivity {
    public static final String TAG = "CityAddressActivity";
    @BindView(R.id.city_cancel)
    TextView cityCancel;
    @BindView(R.id.city_location)
    TextView cityLocation;
    private ContactAdapter mAdapter;
    private IndexableLayout indexableLayout;
    private Intent intent;
    @BindView(R.id.keyWordCityPick)
    public AutoCompleteTextView keyWordCityPick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_address;
    }

    @Override
    protected void initView() {
        initview();
        initAdapter();
        initmap();
        initData();
    }

    //软键盘回车变搜索按钮
    private void initData() {
        keyWordCityPick.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String input=keyWordCityPick.getText().toString();
                    if(!TextUtils.isEmpty(input)){
                        finish();
                        EventBus.getDefault().post(new MessageEvent(input));

                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initmap() {//获取定位值
      String addr = intent.getStringExtra("addr");
      if (addr == null){
          cityLocation.setText("未定位");
          return;
      }else {
          cityLocation.setText(addr);
      }
    }

    private void initAdapter() {
        // 添加自定义词典
        Pinyin.init(Pinyin.newConfig()
                .with(new PinyinMapDict() {
                    @Override
                    public Map<String, String[]> mapping() {
                        HashMap<String, String[]> map = new HashMap<String, String[]>();
                        map.put("重庆", new String[]{"CHONG", "QING"});
                        return map;
                    }
                }));
        mAdapter = new ContactAdapter(this);
        indexableLayout.setAdapter(mAdapter);
        indexableLayout.setOverlayStyle_Center();
        mAdapter.setDatas(initDatas());
        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        //搜索框
        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactStrings);
        keyWordCityPick.setAdapter(adapter);
        //搜索结果
        keyWordCityPick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG,"---onItemClick()---city=="+adapter.getItem(position));
//                intent.putExtra("position", adapter.getItem(position));
//                setResult(RESULT_OK, intent);
                EventBus.getDefault().post(new MessageEvent(adapter.getItem(position)));
                finish();
            }
        });
    }

    private void initview() {
        intent = getIntent();
        indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);
        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        hideSoftKey(keyWordCityPick);
    }

    @Override
    protected void getData() {
        onlisten();


    }

    @Override
    protected void setData() {
    }

    private List<UserEntity> initDatas() {
        List<UserEntity> list = new ArrayList<>();
        // 初始化数据
        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        List<String> mobileStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        for (int i = 0; i < contactStrings.size(); i++) {
            UserEntity contactEntity = new UserEntity(contactStrings.get(i), mobileStrings.get(i));
            list.add(contactEntity);
        }
        return list;
    }

    public void onlisten() {//列表城市item
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {
                if (originalPosition >= 0) {
                    Log.v(TAG,"---onlisten()---city=="+entity.getNick());
//                    intent.putExtra("position", entity.getNick());
//                    setResult(RESULT_OK, intent);
                    EventBus.getDefault().post(new MessageEvent(entity.getNick()));
                    finish();
                } else {
                    ToastUtil.show("选中Header/Footer:" + entity.getNick() + "  当前位置:" + currentPosition);

                }
            }
        });
    }


    private void hideSoftKey(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @OnClick({R.id.city_cancel, R.id.city_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.city_cancel:
                finish();
                break;
            case R.id.city_location:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
