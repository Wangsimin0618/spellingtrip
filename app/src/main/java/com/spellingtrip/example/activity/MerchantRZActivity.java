package com.spellingtrip.example.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.MessageEvent;
import com.spellingtrip.example.fragment.InformationFragment;
import com.spellingtrip.example.fragment.MerchantPayFragment;
import com.spellingtrip.example.fragment.MerchantSHFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商家认证
 */
public class MerchantRZActivity extends BaseActivity {
    public static final String TAG = "MerchantRZActivity";
    @BindView(R.id.rz_fh)
    ImageView rzFh;
    @BindView(R.id.lc_pay)
    TextView lcPay;
    @BindView(R.id.lc_sh)
    TextView lcSh;
    @BindView(R.id.lc_edit)
    TextView lcEdit;
    @BindView(R.id.edit_view)
    ImageView editView;
    @BindView(R.id.pay_view)
    ImageView payView;
    private Fragment[] fragments = new Fragment[3];
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Drawable mLeft;
    private String mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_merchantrz;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mLeft = getResources().getDrawable(R.drawable.lcstart);
    }

    @Override
    protected void getData() {

        InformationFragment informationActivity = new InformationFragment();
        fragments[0] = informationActivity;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.frame_Layout, informationActivity);
        ft.show(informationActivity).commit();
    }

    @Override
    protected void setData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {
        Log.v(TAG, "------enent.message==" + event.message);
        if (event.message == null) {
            return;
        }
        if (event.message.equals("认证成功")) {
            MerchantPayFragment payFragment = new MerchantPayFragment();
            fragments[1] = payFragment;
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            ft.add(R.id.frame_Layout, payFragment);
            ft.show(payFragment).commit();
            editView.setBackgroundColor(MerchantRZActivity.this.getResources().getColor(R.color.hotseach));
            lcPay.setCompoundDrawablesWithIntrinsicBounds(null, mLeft, null, null);
        } else if (event.message.equals("支付成功")) {
            MerchantSHFragment shFragment = new MerchantSHFragment();
            fragments[2] = shFragment;
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            ft.add(R.id.frame_Layout, shFragment);
            ft.show(shFragment).commit();
            payView.setBackgroundColor(MerchantRZActivity.this.getResources().getColor(R.color.hotseach));
            lcSh.setCompoundDrawablesWithIntrinsicBounds(null, mLeft, null, null);

        }else if (event.message.equals("返回首页")){
            finish();
        }
    }

    @OnClick(R.id.rz_fh)
    public void onViewClicked() {
        //关闭页面
        finish();
    }
}
