package com.spellingtrip.example.task;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.spellingtrip.example.bean.UpdateBean;

import java.io.File;



/**
 *
 */

public class UpdateTask {
    private String AppDesc;
    private Activity activity;
    private long mDownloadId;
    private static int REQUESTPERMISSION = 110;
    private UpdateBean updateBean;


    private static class UpdateHolder {
        private static final UpdateTask updateUtil = new UpdateTask();
    }

    public static UpdateTask getInstance() {
        return UpdateHolder.updateUtil;
    }

    private String fullPath;

    public void startUpdate(Activity context, UpdateBean updateBean) {
        activity = context;
        initReceiver();
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        //String url = updateBean.getData().getUrl();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(""));
        // 把id保存好，在接收者里面要用
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("拼旅行升级新版本");
        request.setDescription("版本：" + updateBean.getData().getAppVersion());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        fullPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator + "拼旅行" + updateBean.getData().getAppVersion() + ".apk";
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "拼旅行" + updateBean.getData().getAppVersion() + ".apk");
        File file = new File(fullPath);
        if (file.exists()) {
            installApk(file);
        } else {
            mDownloadId = manager.enqueue(request);
        }
    }

    private BroadcastReceiver mReceiver;

    private void initReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                    long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    if (fullPath != null) {
                        File file = new File(fullPath);
                        if (file.exists()) {
                            installApk(file);
                        } else {
                            Log.i("upadate", "onReceive: file not exist");
                        }
                    } else {
                        Log.i("upadatesw", "onReceive: path is null");
                    }
                }
            }
        };
        activity.registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private void installApk(File file) {
       /* String command = file.getAbsolutePath();
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //在AndroidManifest中的android:authorities值
            Uri apkUri = FileProvider.getUriForFile(activity,
                    "com.spellingtrip.example.provider", file);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            activity.startActivity(install);
        } else {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(install);
        }
    }

}
