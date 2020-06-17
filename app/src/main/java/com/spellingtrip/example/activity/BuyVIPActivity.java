package com.spellingtrip.example.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.OnButtonClick;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.PayUtils;
import com.spellingtrip.example.view.MaxRecyclerView;
import com.spellingtrip.example.view.SpacesItemDecoration;
import com.spellingtrip.example.viewpager.BuyVipMinSuImgAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * VIP购买
 */
public class BuyVIPActivity extends BaseActivity {
    public static final String TAG = "BuyVIPActivity";
    @BindView(R.id.iv_BuyVipLeft)
    public ImageView iv_BuyVipLeft;
    @BindView(R.id.buyvip_dredge)
    public Button buyvipdredge;
    @BindView(R.id.lvBuyVipMinSuImage)
    public MaxRecyclerView recyclerView;
    @BindView(R.id.buyvip_silver_member)
    ImageView buyvipSilverMember;
    @BindView(R.id.buyvip_gold_member)
    ImageView buyvipGoldMember;
    @BindView(R.id.buyvip_platinum_member)
    ImageView buyvipPlatinumMember;
    @BindView(R.id.buyvip_zntj)
    TextView buyvipZntj;
    @BindView(R.id.buyvip_yyzd)
    TextView buyvipYyzd;

    private int[] imgs = new int[]{R.mipmap.minsu01, R.mipmap.minsu01 ,R.mipmap.minsu01};
    private Drawable mZntjs;
    private Drawable mYyzds;
    private Drawable mZntj;
    private Drawable mYyzd;
    private boolean mVip;

    private String money = "68";
    private String cardType = "1";

    @Override
    protected int getLayoutId() {
        return R.layout.actctivity_buyvip;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initDrawable();
        initsilver();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
        recyclerView.setLayoutManager(layoutManager);

    }



    @Override
    protected void getData() {
        BuyVipMinSuImgAdapter imgAdapter = new BuyVipMinSuImgAdapter(imgs, this);
        recyclerView.setAdapter(imgAdapter);
    }

    @Override
    protected void setData() {
        if (UserTask.getInstance() != null) {
            mVip = UserTask.getInstance().getUserInfoData().isVip();
            Log.v(TAG,"-setData()------mVip=="+mVip);
            if (mVip) {
                buyvipdredge.setText("立即续费");
            } else {
                buyvipdredge.setText("立即开通    ￥98/3个月");
            }
        }
        Log.v(TAG,"-setData()------");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SendMessageData data) {
        if (data.getType().equals(ConstantsBean.ISVIP)) {
            setData();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.buyvip_dredge, R.id.iv_BuyVipLeft,R.id.buyvip_silver_member, R.id.buyvip_gold_member, R.id.buyvip_platinum_member})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_BuyVipLeft:
                finish();
                break;
            case R.id.buyvip_dredge:
                boolean vip;

                if (UserTask.getInstance() != null && UserTask.getInstance().getUserInfoData() != null) {
                     /*vip= UserTask.getInstance().getUserInfoData().isVip();
                     if (!vip){

                     }else {
                         ToastUtil.show("您已经是VIP会员");
                     }*/
//                    CommonDialog.setPaySeletor(BuyVIPActivity.this, new OnButtonClick() {
//                        @Override
//                        public void button01ClickListener() {//微信支付
//                            if (UserTask.getInstance() != null) {
//                                String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
//                                PayUtils.getWxDetail(BuyVIPActivity.this, userid, "wxapp");
//                            }
//                        }
//
//                        @Override
//                        public void button02ClickListener() {//支付宝支付
//                            if (UserTask.getInstance() != null) {
//                                String userid = String.valueOf(UserTask.getInstance().getUser().getUserId());
//                                PayUtils.getWxDetail(BuyVIPActivity.this, userid, "aliapp");
//                            }
//                        }
//                    });
                    CommonDialog.setPaySeletor(BuyVIPActivity.this, cardType);
                } else {
                    ActivityUtils.skipActivity(BuyVIPActivity.this, LogineActivity.class, 0, "");
                }
                break;

            case R.id.buyvip_silver_member://白银会员
                Log.v(TAG,"-----OnClick------buyvip_silver_member");
                money = ""+68;
                cardType = "1";
                initsilver();
                if (mVip) {
                    buyvipdredge.setText("立即续费");
                    Log.v(TAG,"-----OnClick------buyvip_silver_member-----mVip");
                } else {
                    buyvipdredge.setText("立即开通    ￥98/3个月");
                    Log.v(TAG,"-----OnClick------buyvip_silver_member-----立即开通￥68/1个月");
                }
                break;
            case R.id.buyvip_gold_member://黄金会员
                Log.v(TAG,"-----OnClick------buyvip_gold_member");
                money = ""+198;
                cardType = "2";
                initgold();
                if (mVip) {
                    buyvipdredge.setText("立即续费");
                    Log.v(TAG,"-----OnClick------buyvip_gold_member-----mVip");
                } else {
                    buyvipdredge.setText("立即开通    ￥188/6个月");
                    Log.v(TAG,"-----OnClick------buyvip_gold_member-----立即开通￥198/6个月");
                }
                break;
            case R.id.buyvip_platinum_member://铂金会员
                Log.v(TAG,"-----OnClick------buyvip_platinum_member");
                money = ""+298;
                cardType = "3";
                initplatinum();
                if (mVip) {
                    buyvipdredge.setText("立即续费");
                    Log.v(TAG,"-----OnClick------buyvip_platinum_member----mvip");
                } else {
                    buyvipdredge.setText("立即开通    ￥298/1年");
                    Log.v(TAG,"-----OnClick------buyvip_platinum_member----立即开通￥298/1年");
                }
                break;
        }
    }

    //显示铂金会员
    private void initplatinum() {
        buyvipGoldMember.setBackgroundResource(R.drawable.golds);
        buyvipSilverMember.setBackgroundResource(R.drawable.silvers);
        buyvipPlatinumMember.setBackgroundResource(R.drawable.platinum);
        buyvipZntj.setCompoundDrawablesWithIntrinsicBounds(null, mZntj, null, null);
        buyvipYyzd.setCompoundDrawablesWithIntrinsicBounds(null, mYyzd, null, null);
    }

    //显示黄金会员
    private void initgold() {
        buyvipGoldMember.setBackgroundResource(R.drawable.gold);
        buyvipSilverMember.setBackgroundResource(R.drawable.silvers);
        buyvipPlatinumMember.setBackgroundResource(R.drawable.platinums);
        buyvipZntj.setCompoundDrawablesWithIntrinsicBounds(null, mZntj, null, null);
        buyvipYyzd.setCompoundDrawablesWithIntrinsicBounds(null, mYyzds, null, null);


    }

    /**
     * 默认显示白银会员
     */
    private void initsilver() {
        buyvipSilverMember.setBackgroundResource(R.drawable.silver);
        buyvipGoldMember.setBackgroundResource(R.drawable.golds);
        buyvipPlatinumMember.setBackgroundResource(R.drawable.platinums);
        buyvipZntj.setCompoundDrawablesWithIntrinsicBounds(null, mZntjs, null, null);
        buyvipYyzd.setCompoundDrawablesWithIntrinsicBounds(null, mYyzds, null, null);
    }

    /**
     * Textview drawableTop切换图片
     */
    private void initDrawable() {
        mZntjs = getResources().getDrawable(R.drawable.zntjs);
        mZntj = getResources().getDrawable(R.drawable.zntj);
        mYyzds = getResources().getDrawable(R.drawable.yyzds);
        mYyzd = getResources().getDrawable(R.drawable.yyzd);
    }
}
