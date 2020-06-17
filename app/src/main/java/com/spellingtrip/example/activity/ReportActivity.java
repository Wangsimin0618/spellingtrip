package com.spellingtrip.example.activity;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.spellingtrip.example.R;
import com.spellingtrip.example.dialog.CommonDialog;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.dialog.OnButtonClick;

import butterknife.BindView;
import butterknife.OnClick;

//举报
public class ReportActivity extends BaseActivity{
    @BindView(R.id.rlSeQing)
    public RelativeLayout rlSeQing;
    @BindView(R.id.rlLaji)
    public RelativeLayout rlLaji;
    @BindView(R.id.rlQiZha)
    public RelativeLayout rlQiZha;
    @BindView(R.id.rlZhenzhi)
    public RelativeLayout rlZhenzhi;
    @BindView(R.id.rlContentNo)
    public RelativeLayout rlContentNo;
    @BindView(R.id.rlMa)
    public RelativeLayout rlMa;
    private LogineDialog logineDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void initView() {

    }
    @OnClick({R.id.rlSeQing,R.id.rlLaji,R.id.rlQiZha,R.id.rlZhenzhi,R.id.rlContentNo,R.id.rlMa})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rlSeQing:
                setJuBao();
                break;
            case R.id.rlLaji:
                setJuBao();
                break;
            case R.id.rlQiZha:
                setJuBao();
                break;
            case R.id.rlZhenzhi:
                setJuBao();
                break;
            case R.id.rlContentNo:
                setJuBao();
                break;
            case R.id.rlMa:
                setJuBao();
                break;
        }
    }

    private void setJuBao() {
        CommonDialog.getDialog(this, "", "确定要投诉么", "取消", "确定", new OnButtonClick() {
            @Override
            public void button01ClickListener() {

            }

            @Override
            public void button02ClickListener() {
                if (logineDialog==null){
                    logineDialog=   new LogineDialog(ReportActivity.this,"");
                }
                 logineDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (logineDialog.isShowing()){
                            logineDialog.dismiss();
                        }
                    }
                },1000);
            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
        backClick();
        setCenterTitle("投诉举报");
    }
}
