package com.spellingtrip.example.city;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;
import com.spellingtrip.example.R;
import com.spellingtrip.example.activity.BaseActivity;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ScrollGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableHeaderAdapter;
import me.yokeyword.indexablerv.IndexableLayout;


/**
 * Created by Administrator on 2017/11/25.搜索城市
 */

public class CityPickerActivity extends BaseActivity  {
    private ContactAdapter mAdapter;
    private BannerHeaderAdapter mBannerHeaderAdapter;
    private String[] city = {"北京", "广州", "深圳", "上海", "南京", "杭州", "武汉", "长沙", "重庆", "天津", "成都", "青岛"};
    private IndexableLayout indexableLayout;
    private CYBChangeCityGridViewAdapter cybChangeCityGridViewAdapter;
    private ArrayList<String> list;
    private Intent intent;
    @BindView(R.id.keyWordCityPick)
    public AutoCompleteTextView keyWordCityPick;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pick_contact;
    }

    @Override
    protected void initView() {

        initview();
        initAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void getData() {
        onlisten();
    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("选择城市");

    }

    public void initAdapter() {
        // 添加自定义词典
        Pinyin.init(Pinyin.newConfig()
                .with(new PinyinMapDict() {
                    @Override
                    public Map<String, String[]> mapping() {
                        HashMap<String, String[]> map = new HashMap<String, String[]>();
                        map.put("重庆",  new String[]{"CHONG", "QING"});
                        return map;
                    }
                }));
        mAdapter = new ContactAdapter(this);
        indexableLayout.setAdapter(mAdapter);
        indexableLayout.setOverlayStyle_Center();
        mAdapter.setDatas(initDatas());
        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        // 这里BannerView只有一个Item, 添加一个长度为1的任意List作为第三个参数
        List<String> bannerList = new ArrayList<>();
        bannerList.add("");
        mBannerHeaderAdapter = new BannerHeaderAdapter("↑", null, bannerList);
        indexableLayout.addHeaderAdapter(mBannerHeaderAdapter);
        //搜索框
        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactStrings);
        keyWordCityPick.setAdapter(adapter);
        keyWordCityPick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("city",  adapter.getItem(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void initview() {
        intent = getIntent();
        indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);
        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        hideSoftKey(keyWordCityPick);

    }

    private void hideSoftKey(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onlisten() {
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {
                if (originalPosition >= 0) {
                    intent.putExtra("city", entity.getNick());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtil.show("选中Header/Footer:" + entity.getNick() + "  当前位置:" + currentPosition);

                }
            }
        });
    }
    private
            /**
             * 自定义的Banner Header
             */
    class BannerHeaderAdapter extends IndexableHeaderAdapter {
        private static final int TYPE = 1;

        public BannerHeaderAdapter(String index, String indexTitle, List datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(CityPickerActivity.this).inflate(R.layout.item_city_header, parent, false);
            VH holder = new VH(view);
            return holder;
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
            final VH vh = (VH) holder;
            list = new ArrayList<>();
            for (int i = 0; i < city.length; i++) {
                list.add(city[i]);
            }
            cybChangeCityGridViewAdapter = new CYBChangeCityGridViewAdapter(CityPickerActivity.this, list);
            vh.head_home_change_city_gridview.setAdapter(cybChangeCityGridViewAdapter);
            vh.head_home_change_city_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent.putExtra("city", list.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });


        }

        private class VH extends RecyclerView.ViewHolder {
            ScrollGridView head_home_change_city_gridview;

            public VH(View itemView) {
                super(itemView);
                head_home_change_city_gridview = (ScrollGridView) itemView.findViewById(R.id.item_header_city_gridview);

            }
        }
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
}
