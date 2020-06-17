package com.spellingtrip.example.activity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.fragment.InformationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserWalletActivity extends BaseActivity {


    @BindView(R.id.money_left)
    ImageView moneyLeft;
    @BindView(R.id.txt_ye)
    TextView txtYe;
    @BindView(R.id.txt_jf)
    TextView txtJf;
    @BindView(R.id.moneyviewframe)
    FrameLayout moneyviewframe;

    private Fragment[] fragments = new Fragment[2];
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Drawable mbottom;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userwallet;
    }

    @Override
    protected void initView() {
        mbottom = getResources().getDrawable(R.drawable.money_bot);
        BalanceActivity moneyActivity = new BalanceActivity();
        fragments[0] = moneyActivity;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.moneyviewframe, moneyActivity);
        ft.show(moneyActivity).commit();

        txtJf.setTextSize(TypedValue.COMPLEX_UNIT_SP,16); //22SP
        txtYe.setTextSize(TypedValue.COMPLEX_UNIT_SP,22); //22SP
        txtYe.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
        txtJf.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//取消加粗
        txtYe.setCompoundDrawables(null, null, null, mbottom);
        txtJf.setCompoundDrawables(null, null, null, null);

    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.money_left, R.id.txt_ye, R.id.txt_jf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.money_left:
                finish();
                break;
            case R.id.txt_ye:
                txtJf.setTextSize(TypedValue.COMPLEX_UNIT_SP,16); //22SP
                txtYe.setTextSize(TypedValue.COMPLEX_UNIT_SP,22); //22SP
                txtYe.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                txtJf.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//取消加粗
//                txtYe.setCompoundDrawables(null, null, null, mbottom);
//                txtJf.setCompoundDrawables(null, null, null, null);
                //余额
                BalanceActivity moneyActivity = new BalanceActivity();
                fragments[0] = moneyActivity;
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.add(R.id.moneyviewframe, moneyActivity);
                ft.show(moneyActivity).commit();
                break;
            case R.id.txt_jf:
                //积分
                txtYe.setTextSize(TypedValue.COMPLEX_UNIT_SP,16); //22SP
                txtJf.setTextSize(TypedValue.COMPLEX_UNIT_SP,22); //22SP
                txtJf.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗
                txtYe.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//取消加粗
//                txtJf.setCompoundDrawables(null, null, null, mbottom);
//                txtYe.setCompoundDrawables(null, null, null, null);

                IntegralActivity integralActivity = new IntegralActivity();
                fragments[1] = integralActivity;
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.add(R.id.moneyviewframe, integralActivity);
                ft.show(integralActivity).commit();
                break;
        }
    }
}
