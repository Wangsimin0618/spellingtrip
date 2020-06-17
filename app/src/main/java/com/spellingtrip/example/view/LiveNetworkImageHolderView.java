package com.spellingtrip.example.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spellingtrip.example.bean.BannerBean;
import com.spellingtrip.example.bean.HomeDataBean;
import com.spellingtrip.example.bean.LivesDetailsBean;

public class LiveNetworkImageHolderView implements Holder<BannerBean> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context,final int position, BannerBean data) {
        String path=data.getFilepath()+"?x-oss-process=style/320_320";
        Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }
}

