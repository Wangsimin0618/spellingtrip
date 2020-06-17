package com.spellingtrip.example.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spellingtrip.example.R;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.bean.ReMoveBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.dialog.LogineDialog;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.retrofit.http.Constant;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.EventType;
import com.spellingtrip.example.utils.JsonUtil;
import com.spellingtrip.example.utils.PreferenceUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class EditInfoActivity extends BaseActivity {
    @BindView(R.id.tvEditInfoFinish)
    public TextView tvEditInfoFinish;
    @BindView(R.id.ivInfoDeltel)
    public ImageView ivInfoDeltel;
    @BindView(R.id.etInfoNick)
    public EditText etInfoNick;
    @BindView(R.id.rlEditInfoQianName)
    public RelativeLayout rlEditInfoQianName;
    @BindView(R.id.rlEditInfoNick)
    public RelativeLayout rlEditInfoNick;
    @BindView(R.id.tvTextNumber)
    public TextView tvTextNumber;
    @BindView(R.id.etInfoQianName)
    public EditText etInfoQianName;
    @BindView(R.id.tv_lantitle)
    public TextView tv_lantitle;
    private LogineDialog logineDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editinfo;
    }

    @Override
    protected void initView() {
        etInfoQianName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    tvTextNumber.setText(35+"");
                }else {
                    tvTextNumber.setText((35-s.length())+"");
                }

            }
        });
        etInfoNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    ivInfoDeltel.setVisibility(View.VISIBLE);
                } else {
                    ivInfoDeltel.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void getData() {

    }

    @OnClick({R.id.tvEditInfoFinish, R.id.ivInfoDeltel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvEditInfoFinish:
                String nick = etInfoNick.getText().toString().trim();
                String qianming=etInfoQianName.getText().toString().trim();
                String title=tv_lantitle.getText().toString().trim();
                if (title.equals("设置昵称")&&!TextUtils.isEmpty(nick)){
                    setNick("nick", nick);
                }else if (title.equals("设置签名")&&!TextUtils.isEmpty(qianming)){
                    setNick("description", qianming);
                } else {
                    ToastUtil.show("修改内容不能为空");
                }
                break;
            case R.id.ivInfoDeltel:
                etInfoNick.setText("");
                break;
            default:
                break;
        }
    }

    private void setNick(final String type, final String nick) {
        if (logineDialog == null) {
            logineDialog = new LogineDialog(this, "");
        }
        logineDialog.show();
        OkHttpUtils.post().url(ConstantsBean.BASE_PATH + ConstantsBean.UPDATAINFO + UserTask.getInstance().getUser().getUserId())
                .addParams(type, nick).addParams("v","1").build().readTimeOut(50000)
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.show(ConstantsBean.ERROR);
                logineDialog.dismiss();
            }

            @Override
            public void onResponse(String s, int i) {
                logineDialog.dismiss();
                if (!TextUtils.isEmpty(s)) {
                    ReMoveBean reMoveBean = (ReMoveBean) JsonUtil.fromJson(s, ReMoveBean.class);
                    if (reMoveBean.getCode() == 0) {
                        ToastUtil.show(reMoveBean.getMsg());
                            EventBus.getDefault().post(new SendMessageData(Constant.UrlOrigin.USERINFO));
                        finish();
                    } else {
                        ToastUtil.show(reMoveBean.getMsg());
                    }
                }
            }
        });
    }

    @Override
    protected void setData() {
        backClick();
        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            setCenterTitle(title);
            if (title.equals("设置昵称")){
                rlEditInfoQianName.setVisibility(View.GONE);
                rlEditInfoNick.setVisibility(View.VISIBLE);
                etInfoNick.setText(UserTask.getInstance().getUserInfoData().getNick());
                etInfoNick.setSelection(UserTask.getInstance().getUserInfoData().getNick().length());
            }else {
                rlEditInfoNick.setVisibility(View.GONE);
                rlEditInfoQianName.setVisibility(View.VISIBLE);
                etInfoQianName.setText(UserTask.getInstance().getUserInfoData().getDescription());
                if (UserTask.getInstance().getUserInfoData()!=null
                        &&!TextUtils.isEmpty(UserTask.getInstance().getUserInfoData().getDescription())){
                    etInfoQianName.setSelection(UserTask.getInstance().getUserInfoData().getDescription().length());
                }

            }
        }


    }
}
