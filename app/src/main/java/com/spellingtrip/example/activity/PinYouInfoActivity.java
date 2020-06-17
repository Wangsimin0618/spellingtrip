package com.spellingtrip.example.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.PinYouInfoBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.huanxin.ChatActivity;
import com.spellingtrip.example.huanxin.Constanthuan;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class PinYouInfoActivity extends BaseActivity {
    @BindView(R.id.tvInfoDesc)
    public EditText tvInfoDesc;
    @BindView(R.id.ivInfoHeader)
    public ImageView ivInfoHeader;
    @BindView(R.id.etInfoName)
    public TextView etInfoName;
    @BindView(R.id.tvPinYouInfoSex)
    public TextView tvPinYouInfoSex;
    @BindView(R.id.ivPinYouInfoRenzheng)
    public ImageView ivPinYouInfoRenzheng;
    @BindView(R.id.tvInfoTime)
    public TextView tvInfoTime;
    @BindView(R.id.tvInfoStartLocation)
    public TextView tvInfoStartLocation;
    @BindView(R.id.tvInfoEndLocation)
    public TextView tvInfoEndLocation;
    @BindView(R.id.tvInfoPeopleNumber)
    public TextView tvInfoPeopleNumber;
    @BindView(R.id.ivInfoChat)
    public ImageView ivInfoChat;
    @BindView(R.id.tvInfoTotalDay)
    public TextView tvInfoTotalDay;
    @BindView(R.id.tvUpdataDesc)
    public TextView tvUpdataDesc;
    @BindView(R.id.tvInfoPeopleSex)
    public TextView tvInfoPeopleSex;
    @BindView(R.id.tvInfoPeopleArg)
    public TextView tvInfoPeopleArg;
    private PinYouInfoBean pinYouInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pinyouinfo;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {
        String infoid = getIntent().getStringExtra("id");
        setInfo(infoid);
    }

    @OnClick({R.id.ivInfoChat, R.id.tvUpdataDesc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivInfoChat:
                if (pinYouInfoBean != null&&!pinYouInfoBean.getData().getImUserName().equals(UserTask.getInstance().getUser().getImUsername())) {
                    activity.startActivity(new Intent(activity, ChatActivity.class)
                            .putExtra(Constanthuan.EXTRA_USER_ID, pinYouInfoBean.getData().getImUserName())
                            .putExtra("nick",pinYouInfoBean.getData().getNick()));
                }
                break;
            case R.id.tvUpdataDesc:
                String desc = tvInfoDesc.getText().toString().trim();
                if (pinYouInfoBean != null && !desc.equals(pinYouInfoBean.getData().getDescription())) {
                    setUpdata(desc, pinYouInfoBean.getData().getInfoId());
                } else {
                    ToastUtil.show("详情没有修改哦");
                }
                break;
        }
    }

    private void setUpdata(String desc, int infoId) {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.UPDATADESC).addParams("pinId", String.valueOf(infoId))
                .addParams("description", desc).build().readTimeOut(50000).execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {
                        ToastUtil.show(reMoveBean.getMsg());
                        finish();
                    } else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }

    /**
     * 获取详情
     *
     * @param infoid
     */
    private void setInfo(String infoid) {
        OkHttpUtils.get().url(ConstantsBean.BASE_PATH + ConstantsBean.PININFO).addParams("pinId", infoid).build().readTimeOut(50000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtil.show(ConstantsBean.ERROR);
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        if (!TextUtils.isEmpty(s)) {
                            pinYouInfoBean = (PinYouInfoBean) JsonUtil.fromJson(s, PinYouInfoBean.class);
                            if (pinYouInfoBean.getCode() == 0) {
                                setInfo(pinYouInfoBean.getData());
                            } else {
                                ToastUtil.show(pinYouInfoBean.getMsg());
                            }
                        }
                    }
                });
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private void setInfo(PinYouInfoBean.DataBean data) {
        if (data.getUserId() == UserTask.getInstance().getUser().getUserId()) {
            tvInfoDesc.setFocusable(true);
            tvUpdataDesc.setVisibility(View.VISIBLE);
            ivInfoChat.setVisibility(View.GONE);
        } else {
            tvUpdataDesc.setVisibility(View.GONE);
            ivInfoChat.setVisibility(View.VISIBLE);
            tvInfoDesc.setFocusable(false);
        }
        if (!TextUtils.isEmpty(data.getHeadUrl())) {
            String  imgurl=data.getHeadUrl()+"?x-oss-process=style/320_320";
            Glide.with(this).load(imgurl).transform(new CommonUtil.GlideCircleTransform(this)).into(ivInfoHeader);
        }
        tvInfoPeopleSex.setText(data.getExpectedSex());
       if (data.getExpectedAge().contains("10-60")){
           tvInfoPeopleArg.setText("不限");
       }else {
           tvInfoPeopleArg.setText(data.getExpectedAge());
       }

        etInfoName.setText(data.getNick());
        tvPinYouInfoSex.setText(data.getSex());
        if (data.isIdcardAuth()) {
            ivPinYouInfoRenzheng.setImageResource(R.mipmap.renzhengsel);
        } else {
            ivPinYouInfoRenzheng.setImageResource(R.mipmap.renzhengnor);
        }
        tvInfoTime.setText(data.getStartDate() + " - " + data.getEndDate());
        tvInfoStartLocation.setText(data.getFromArea());
        tvInfoEndLocation.setText(data.getToArea());
        tvInfoPeopleNumber.setText(data.getTotalCount() + "");
        if (!TextUtils.isEmpty(data.getDescription())) {
            tvInfoDesc.setText(data.getDescription());
        }
        tvInfoTotalDay.setText("共计" + data.getDiffDays() + "天");

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("详情");
    }
}
