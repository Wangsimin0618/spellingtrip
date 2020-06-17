package com.spellingtrip.example.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.guoxiaoxing.phoenix.picker.widget.videoview.InternalVideoView;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.LabelBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.city.ContactAdapter;
import com.spellingtrip.example.city.UserEntity;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.sticky.StickyListHeadersListView;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.SectionPinListView;
import com.spellingtrip.example.viewpager.ChooseLabelAdapter;
import com.spellingtrip.example.viewpager.SetLabelAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.indexablerv.IndexableLayout;
import okhttp3.Call;

public class SetLabelActivity extends BaseActivity  {
    @BindView(R.id.lvSetLabel)
    public StickyListHeadersListView lvSetLabel;
    @BindView(R.id.gvChooseLabel)
    public GridView gvChooseLabel;
    @BindView(R.id.tvSetLabelFinish)
    public TextView tvSetLabelFinish;
    private LabelBean labelBean;
    public static List<UserInfoDataBean.LabelsBean> labels = new ArrayList<>();

    private StringBuffer stringBuffer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setlabel;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

    }

    @Override
    protected void getData() {
        getLaBel();
        setLabel();
    }


    private void getLaBel() {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + "/api/label/options/" + UserTask.getInstance().getUser().getUserId()).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    labelBean = (LabelBean) JsonUtil.fromJson(s, LabelBean.class);
                    if (labelBean.getCode() == 0 && labelBean.getData().size() > 0) {
                        initDatas(labelBean.getData());
                    } else {
                        ToastUtil.show(labelBean.getMsg());
                    }
                }
            }
        });
    }

    private void setLabel() {
      if (labels.size()==0&&PreferenceUtil.getLabels(ConstantsBean.Spell,ConstantsBean.LABELS)!=null){
          labels.addAll(PreferenceUtil.getLabels(ConstantsBean.Spell,ConstantsBean.LABELS));
      }
        if (labels.size() > 0) {
            ChooseLabelAdapter labelAdapter = new ChooseLabelAdapter(labels, SetLabelActivity.this);
            gvChooseLabel.setAdapter(labelAdapter);
        } else {

        }
    }

    private void initDatas(List<LabelBean.DataBean> data) {
        SetLabelAdapter adapter = new SetLabelAdapter(SetLabelActivity.this, data);
        lvSetLabel.setAdapter(adapter);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(Constant.UrlOrigin.IsLABEL)) {
            getLaBel();
        }
        if (data.getType().equals(Constant.UrlOrigin.ADDLABEL)){
            setLabel();
        }
    }

    @OnClick(R.id.tvSetLabelFinish)
    public void onClick(View view) {
        if (stringBuffer==null){
            stringBuffer  =new StringBuffer();
        }
        if (labels.size()>0){
            for (int i = 0; i < labels.size(); i++) {
                if (i==labels.size()-1){
                    stringBuffer.append(labels.get(i).getLabelId());
                }else {
                    stringBuffer.append(labels.get(i).getLabelId()+",");
                }
            }
            savelabel(stringBuffer.toString());
        }else {

        }
        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.USERINFO));
        finish();
    }
    private void savelabel( String labelname) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.SAVELABEL).addParams("ids", labelname)
                .addParams("userId", String.valueOf(UserTask.getInstance().getUser().getUserId())).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    ReMoveBean reMoveBean= (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode()==0){
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.USERINFO));
                        finish();
                    }else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }
    @Override
    protected void setData() {
        backClick();
        setCenterTitle("设置标签");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



}
