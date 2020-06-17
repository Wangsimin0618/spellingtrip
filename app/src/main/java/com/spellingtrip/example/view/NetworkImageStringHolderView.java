package com.spellingtrip.example.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spellingtrip.example.bean.BannerDataBean;


/**
 * Created by Administrator on 2017/9/19 0019.
 */

public class NetworkImageStringHolderView implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        String imgurl =data+"?x-oss-process=style/640_640";
        Glide.with(context).load(imgurl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

    }
}
