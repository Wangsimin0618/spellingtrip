package com.spellingtrip.example.viewpager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.R;
import com.spellingtrip.example.utils.GlideRoundTransform;
import com.youth.banner.loader.ImageLoader;

/**
 * date:2020/4/18
 * author:王思敏
 * function首页轮播
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        int i = dip2px(10);
//        RequestOptions requestOptions = RequestOptions.bitmapTransform(new RoundedCorners(i));
//        Glide.with(context).load(path).into(imageView);
        Glide.with(context)
                .load(path)
                .transform(new CenterCrop(context),new GlideRoundTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .into(imageView);

    }
    /**
     * dp转px
     * 16dp - 48px
     * 17dp - 51px*/
    public static int dip2px(float dpValue) {
        float scale = CustomApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int)((dpValue * scale) + 0.5f);
    }

}
