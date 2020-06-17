package com.spellingtrip.example;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.view.View;
import android.widget.ImageView;


import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;

import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.spellingtrip.example.bean.ConstantsBean;
import com.spellingtrip.example.huanxin.DemoHelper;
import com.spellingtrip.example.retrofit.bean.SendMessageData;
import com.spellingtrip.example.utils.ToastUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class CustomApplication extends Application {
    public static Context context;
    private static CustomApplication instance;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        instance = this;
        context = this;
        //init demo helper

        SDKInitializer.initialize(getApplicationContext());
        IWXAPI api  = WXAPIFactory.createWXAPI(this, null,false);
        api.registerApp(ConstantsBean.APP_ID);

        DemoHelper.getInstance().init(context);
        LitePal.initialize(this);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        Phoenix.config()
                .imageLoader(new ImageLoader() {
                    @Override
                    public void loadImage(Context mContext, ImageView imageView
                            , String imagePath, int type) {
                        Glide.with(mContext)
                                .load(imagePath)
                                .into(imageView);
                    }
                });
        initUniversalImageLoader();
        JCVideoPlayer.setThumbImageViewScalType(ImageView.ScaleType.FIT_XY);

    }

    public static CustomApplication getInstance() {
        return instance;
    }

    public String getAppVersion() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.13";
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    private void initUniversalImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(new ColorDrawable(Color.parseColor("#f0f0f0")))
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
        int memCacheSize = 1024 * 1024 * memClass / 8;

        File cacheDir = new File(Environment.getExternalStorageDirectory().getPath() + "/jiecao/cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(new UsingFreqLimitedMemoryCache(memCacheSize))
                .memoryCacheSize(memCacheSize)
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                .defaultDisplayImageOptions(options)
                .build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
    }
}
