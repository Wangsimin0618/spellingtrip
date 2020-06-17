package com.spellingtrip.example.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.cjt2325.cameralibrary.JCameraView;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ChooseActivityBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.ChooseActivityAdapter;
import com.spellingtrip.example.viewpager.PinMediaAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.spellingtrip.example.utils.JsonUtil.fromJson;

public class ChoosePinActivity extends BaseActivity{
    @BindView(R.id.activityRecyclerView)
    public ListView recyclerView;
    @BindView(R.id.tvChooseFinish)
    public TextView tvChooseFinish;
    @BindView(R.id.etHomeSearch)
    public EditText etHomeSearch;
    private ChooseActivityAdapter activityAdapter;
    private ChooseActivityBean activityBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choosepin;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void getData() {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.CHOOSE_ACTIVITY).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
             if (!TextUtils.isEmpty(s)){
                  activityBean= (ChooseActivityBean) fromJson(s,ChooseActivityBean.class);
                 if (activityBean.getCode()==0){
                     if (activityBean.getData().size()>0){
                         if(activityBean.getData().size()<10){
                             etHomeSearch.setVisibility(View.GONE);
                         }else {
                             etHomeSearch.setVisibility(View.VISIBLE);
                         }
                         setActivity(activityBean.getData());
                     }else {
                         etHomeSearch.setVisibility(View.GONE);
                     }
                 }else {

                 }
             }
            }
        });
    }

    private void setActivity(List<ChooseActivityBean.DataBean> data) {
         activityAdapter=  new ChooseActivityAdapter(data,ChoosePinActivity.this);
        recyclerView.setAdapter(activityAdapter);
        recyclerView.setOnItemClickListener(onItemClickListener);
    }

    private String title;
    private String typeid;
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position != activityAdapter.getSelectedPosition()) {
                ChooseActivityBean.DataBean poiItem = ( ChooseActivityBean.DataBean) activityAdapter.getItem(position);
                 title=poiItem.getTypeName();
                 typeid= String.valueOf(poiItem.getTypeId());
                activityAdapter.setSelectedPosition(position);
                activityAdapter.notifyDataSetChanged();
            }
        }
    };
    @OnClick({R.id.tvChooseFinish})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tvChooseFinish:
                if (TextUtils.isEmpty(title)&&activityBean.getData()!=null&&activityBean.getData().size()>0){
                    title=activityBean.getData().get(0).getTypeName();
                    typeid= String.valueOf(activityBean.getData().get(0).getTypeId());
                }
                setResult(RESULT_OK, new Intent().putExtra("title", title).putExtra("typeid",typeid));
                finish();
                break;
        }
    }
    @Override
    protected void setData() {
        backClick();
        setCenterTitle("选择活动");
    }


}
