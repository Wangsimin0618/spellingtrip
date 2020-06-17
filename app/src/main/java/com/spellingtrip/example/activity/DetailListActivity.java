package com.spellingtrip.example.activity;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.PiPeiRecordBean;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.viewpager.DetailListAdapter;
import com.spellingtrip.example.viewpager.RecordAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 匹配详情列表
 */
public class DetailListActivity extends BaseActivity{
    @BindView(R.id.lvDetailList)
    public ListView listView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_detaillist;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {
      String infoid=  getIntent().getStringExtra("id");
      setInfo(infoid);
    }

    private void setInfo(String infoid) {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH+ConstantsBean.RECORD).addParams("pinId",infoid)
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId()))
                .build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    PiPeiRecordBean recordBean= (PiPeiRecordBean) JsonUtil.fromJson(s, PiPeiRecordBean.class);
                    if (recordBean.getCode()==0){
                        DetailListAdapter adapter=new DetailListAdapter(recordBean.getData(),DetailListActivity.this);
                        listView.setAdapter(adapter);
                    }else {
                        ToastUtil.show(recordBean.getMsg());
                    }
                }
            }
        });
    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("匹配详情");
    }
}
