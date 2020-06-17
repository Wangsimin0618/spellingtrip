package com.spellingtrip.example.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.BlackBean;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.SmSCodeBean;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.utils.ActivityUtils;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/***
 * 设置密码
 */
public class EditUserPassComiActivity extends BaseActivity{
    @BindView(R.id.tvComiEditUserPassLogineYinSi)
    public TextView tvComiEditUserPassLogineYinSi;
    @BindView(R.id.ivComiEditUserPassBack)
    public ImageView ivComiEditUserPassBack;
    @BindView(R.id.etComiOneEditUserPassPhone)
    public EditText etComiOneEditUserPassPhone;
    @BindView(R.id.etComiTwoEditUserPassPhone)
    public EditText etComiTwoEditUserPassPhone;
    @BindView(R.id.tvComiEditUserPassLogineSel)
    public TextView tvComiEditUserPassLogineSel;
    @Override
    protected int getLayoutId() {
        return R.layout.actctivity_comiuserpass;
    }

    @Override
    protected void initView() {
        etComiOneEditUserPassPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                if (length >6) {
                    String passOne=etComiTwoEditUserPassPhone.getText().toString().trim();
                    if (passOne.length()>6){
                        tvComiEditUserPassLogineSel.setBackground(getResources().getDrawable(R.mipmap.loginsel_bg));
                        tvComiEditUserPassLogineSel.setTextColor(getResources().getColor(R.color.main_bg));
                    }else {
                        tvComiEditUserPassLogineSel.setBackground(getResources().getDrawable(R.drawable.loginnor_shape));
                        tvComiEditUserPassLogineSel.setTextColor(getResources().getColor(R.color.textabc0c));
                    }
                } else {
                    tvComiEditUserPassLogineSel.setBackground(getResources().getDrawable(R.drawable.loginnor_shape));
                    tvComiEditUserPassLogineSel.setTextColor(getResources().getColor(R.color.textabc0c));
                }
            }
        });
        etComiTwoEditUserPassPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                if (length >6) {
                    String passOne=etComiOneEditUserPassPhone.getText().toString().trim();
                    if (passOne.length()>6){
                        tvComiEditUserPassLogineSel.setBackground(getResources().getDrawable(R.mipmap.loginsel_bg));
                        tvComiEditUserPassLogineSel.setTextColor(getResources().getColor(R.color.main_bg));
                    }else {
                        tvComiEditUserPassLogineSel.setBackground(getResources().getDrawable(R.drawable.loginnor_shape));
                        tvComiEditUserPassLogineSel.setTextColor(getResources().getColor(R.color.textabc0c));
                    }

                } else {
                    tvComiEditUserPassLogineSel.setBackground(getResources().getDrawable(R.drawable.loginnor_shape));
                    tvComiEditUserPassLogineSel.setTextColor(getResources().getColor(R.color.textabc0c));
                }

            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setData() {
    }
    @OnClick({R.id.tvComiEditUserPassLogineYinSi,R.id.ivComiEditUserPassBack,R.id.tvComiEditUserPassLogineSel})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tvComiEditUserPassLogineYinSi:
                ActivityUtils.skipActivity(this, ArgeeTextActivity.class, 0, "");
                break;
            case R.id.ivComiEditUserPassBack:
                finish();
                break;
            case R.id.tvComiEditUserPassLogineSel:
               String onePass= etComiOneEditUserPassPhone.getText().toString().trim();
                String twoPass= etComiTwoEditUserPassPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(onePass)&&onePass.length()>=6&&!TextUtils.isEmpty(twoPass)&&twoPass.length()>=6){
                    if (onePass.equals(twoPass)){
                        String phone=getIntent().getStringExtra("title");
                        String code=getIntent().getStringExtra("code");
                       String pass= CommonUtil.md5(onePass);
                        setPass(pass,phone,code);
                    }else {
                        ToastUtil.show("两次密码不一致");
                    }

                }else {
                }
                break;
        }
    }

    private void setPass(String pass, String phone,String code) {
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH+ConstantsBean.UPDATAPASS).addParams("tell",phone).addParams("password",pass)
                .addParams("code",code).build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show("修改失败");
            }

            @Override
            public void onResponse(String s, int i) {
                if (!TextUtils.isEmpty(s)){
                    BlackBean smSCodeBean= (BlackBean) JsonUtil.fromJson(s, BlackBean.class);
                    if (smSCodeBean.getCode()==0){
                        EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.ISEDITPASS));
                        ToastUtil.show(smSCodeBean.getMsg());
                      finish();
                    }else {
                        ToastUtil.show(smSCodeBean.getMsg());
                    }
                }else {

                }
            }
        });
    }
}
