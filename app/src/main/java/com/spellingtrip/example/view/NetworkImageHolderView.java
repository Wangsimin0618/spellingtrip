package com.spellingtrip.example.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.spellingtrip.example.bean.BannerBean;
import com.spellingtrip.example.bean.BannerDataBean;
import com.spellingtrip.example.bean.HomeDataBean;


/**
 * Created by Administrator on 2017/9/19 0019.
 */

public class NetworkImageHolderView implements Holder<BannerDataBean.DataBean.RowsBean> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerDataBean.DataBean.RowsBean data) {
        Glide.with(context).load(data.getFilepath())
                .override(350, 300).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

    }
}
