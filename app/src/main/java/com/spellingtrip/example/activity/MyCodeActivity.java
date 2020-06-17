package com.spellingtrip.example.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spellingtrip.example.R;
import com.spellingtrip.example.task.UserTask;
import com.spellingtrip.example.utils.CommonUtil;
import com.spellingtrip.example.utils.ToastUtil;
import com.spellingtrip.example.view.ShapedImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCodeActivity extends BaseActivity{

    @BindView(R.id.ivMyErCode)
    public ImageView ivMyErCode;
    @BindView(R.id.iv_left)
    public ImageView iv_left;
    @BindView(R.id.tv_lantitle)
    public TextView tv_lantitle;
    @BindView(R.id.sivMyCodeUserHeader)
    public ImageView sivMyCodeUserHeader;
    @BindView(R.id.tvMyCodeCopy)
    public TextView tvMyCodeCopy;
    @BindView(R.id.rlMyCodeSave)
    public RelativeLayout rlMyCodeSave;
    @Override
    protected int getLayoutId() {
        return R.layout.actctivity_mycode;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {
        if (UserTask.getInstance()!=null&&UserTask.getInstance().getUserInfoData()!=null){
           String url= UserTask.getInstance().getUserInfoData().getHeadUrl();
           CommonUtil.loadImage(MyCodeActivity.this,sivMyCodeUserHeader,url,R.mipmap.icon_40);
          //  Glide.with(this).load(url).into(sivMyCodeUserHeader);
        }
    }
    @OnClick({R.id.iv_left,R.id.tvMyCodeCopy})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.tvMyCodeCopy:
                rlMyCodeSave.setDrawingCacheEnabled(true);
                rlMyCodeSave.buildDrawingCache();
                final Bitmap bmp = rlMyCodeSave.getDrawingCache(); // 获取图片
               // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.myercode, null);
              CommonUtil.saveImageToGallery(MyCodeActivity.this,bmp);
                break;
        }
    }
    @Override
    protected void setData() {
        tv_lantitle.setText("我的二维码");
    }
}
