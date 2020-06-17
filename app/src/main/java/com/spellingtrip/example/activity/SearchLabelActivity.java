package com.spellingtrip.example.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.HotCityBean;
import com.spellingtrip.example.city.CityseachActivity;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.WrapContentLinearLayoutManager;
import com.spellingtrip.example.view.MyLayoutManager;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.HotSeachAdapter;
import com.spellingtrip.example.viewpager.LabelResultAdapter;
import com.spellingtrip.example.viewpager.SearchHistoryAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class SearchLabelActivity extends BaseActivity {
    public static final String TAG = "SearchLabelActivity";

    @BindView(R.id.seach_label)
    AutoCompleteTextView seachLabel;
    @BindView(R.id.seach_cancel)
    TextView seachCancel;
    @BindView(R.id.seach_delete)
    ImageView seachDelete;
    @BindView(R.id.label_history)
    RecyclerView labelHistory;
    @BindView(R.id.city_refresh)
    ImageView cityRefresh;
    @BindView(R.id.hot_seach)
    RecyclerView hotSeach;
    private Set<String> siteno;
    private List<String> list = new ArrayList<>();
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_serachlabel;
    }

    @Override
    protected void initView() {
        initseach();
        getSeachLabel();
    }

    @Override
    protected void getData() {
        getSeachLabel();

    }

    @Override
    protected void setData() {
        setSearchHis();
        getSeachLabel();

    }


    /**
     * 搜索添加历史记录
     */
    private void initseach() {
        seachLabel.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String input = seachLabel.getText().toString().trim();
                    if (!TextUtils.isEmpty(input)) {
                        siteno = PreferenceUtil.getList(ConstantsBean.Spell, "search");
                        if (siteno.size() > 0) {
                            for (String str : siteno) {
                                siteno.add(str);
                            }
                        }
                        siteno.add(input);
                        PreferenceUtil.putList(ConstantsBean.Spell, "search", siteno);
                        mIntent = new Intent(SearchLabelActivity.this, SameDetailActivity.class);
                        mIntent.putExtra("title", input);
                        startActivity(mIntent);
                        finish();
                    }
                    return true;
                }
                return false;
            }
        });
    }

        /**
         * 设置历史记录
         */
        private void setSearchHis() {
            if (siteno == null) {
                siteno = new HashSet<>();
            }
            siteno = PreferenceUtil.getList(ConstantsBean.Spell, "search");
            if (siteno.size() > 0) {
                for (String str : siteno) {
                    list.add(str);
                }
            } else {
            }
            if (list.size() > 0) {
                labelHistory.setVisibility(View.VISIBLE);
                MyLayoutManager layout = new MyLayoutManager();
                layout.setAutoMeasureEnabled(true);
                labelHistory.addItemDecoration(new SpacesItemDecoration(49));
                labelHistory.setLayoutManager(layout);
                final SearchHistoryAdapter adapter = new SearchHistoryAdapter(this, list);
                labelHistory.setAdapter(adapter);
                adapter.setOnItemListener(new SearchHistoryAdapter.OnItemListener() {
                    @Override
                    public void onClick(View v, int pos, String projectc) {
                    mIntent = new Intent(SearchLabelActivity.this, SameDetailActivity.class);
                    mIntent.putExtra("title", projectc);
                    startActivity(mIntent);
                        finish();
                    }
                });
            } else {
                labelHistory.setVisibility(View.GONE);
            }
        }

    @OnClick({R.id.seach_cancel, R.id.seach_delete, R.id.city_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.seach_cancel:
                Log.v(TAG,"-----onViewClicked()-----seach_cancel");
                finish();
                break;
            case R.id.seach_delete:

                break;
            case R.id.city_refresh:

                break;
        }
    }

    /**
     * 获取热门城市
     */
    private void getSeachLabel() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.HOTCITY).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    HotCityBean hotCityBean = (HotCityBean) JsonUtil.fromJson(s, HotCityBean.class);
                    if (hotCityBean.getCode() == 0) {
                        setHotcity(hotCityBean.getData());
                    }
                }
            }
        });
    }

    private void setHotcity(List<HotCityBean.DataBean> data) {
        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(SearchLabelActivity.this, LinearLayoutManager.VERTICAL, false);
        LabelResultAdapter adapter = new LabelResultAdapter(SearchLabelActivity.this, data);
        hotSeach.setLayoutManager(layoutManager);
        hotSeach.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
