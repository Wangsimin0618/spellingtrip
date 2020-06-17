package com.spellingtrip.example.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * @summary 说明：通用工具类
 */

public class CommonUtil {

    /**
     * 防止联系点击
     */
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
    private static Bitmap bitmap;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 计算两点间的距离
     */
    public static double getRange(double latitude_a, double longitude_a, double latitude_b, double longitude_b) {
        double EARTH_RADIUS = 6378137.0;
        double radLat1 = (latitude_a * Math.PI / 180.0);
        double radLat2 = (latitude_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (longitude_a - longitude_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000000;
        return s;
    }


    /**
     * 判断是否是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否有拍照权限
     */
    public final static boolean setCamera(Context context) {
        boolean camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
        return camera;
    }

    /**
     * 申请拍张权限
     */
    public final static void setRequseCamera(Context context, int code) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.CAMERA},
                code);
    }

    /**
     * 判断是否有相册权限
     */
    public final static boolean setPhoto(Context context) {
        boolean photo = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
        return photo;
    }

    /**
     * 申请拍张权限
     */
    public final static void setRequsePhoto(Context context, int code) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                code);
    }

    /**
     * url转换成bitmap
     */
    public final static Bitmap returnBitMap(String url) {
        URL myFileUrl;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 只允许输入汉字
     *
     * @param str 字符串
     * @return
     * @throws PatternSyntaxException
     */

    public static String stringFilter1(String str) throws PatternSyntaxException {
        //只允许汉字
        String regEx = "[^\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    ;

    public static boolean isBaiTian() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour = sdf.format(new Date());
        int k = Integer.parseInt(hour);
        if ((k >= 0 && k < 6) || (k >= 18 && k < 24)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * MD5加密
     *
     * @param s 加密的字符
     * @return
     */
    public final static String getMD5String(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5加密
     *
     * @param
     * @return
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        } else {

        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDayTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    //日期转换，年月日
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    //日期转换，年月日
    public static String getTimeDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd ");
        return format.format(date);
    }

    //年
    public static String getYearTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    public static String getSysTime() {
        String time = String.valueOf(System.currentTimeMillis());
        return time;
    }

    //日期转换，月
    public static String getMonTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }

    //ri
    public static String getDayTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(date);
    }

    public static String getTimeHH(Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH");
        return sDateFormat.format(new Date());
    }

    public static String getTimeMM(Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("mm");
        return sDateFormat.format(new Date());
    }

    public static String getTimeSS(Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("ss");
        return sDateFormat.format(new Date());
    }

    public static String getTimeFen(Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sDateFormat.format(date);
    }

    public static String getIsTimeFen(Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sDateFormat.format(date);
    }

    public static String getTimeHour(Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");
        return sDateFormat.format(new Date());
    }

    /**
     * 时间差
     */
    public static long getDayCha(String mstateTime, String mendTime) {
        String stateTime = mstateTime;
        String endTime = mendTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date state = sdf.parse(stateTime);
            Date end = sdf.parse(endTime);

            long stateTimeLong = state.getTime();
            long endTimeLong = end.getTime();
            // 结束时间-开始时间 = 天数
            long day = (endTimeLong - stateTimeLong) / (24 * 60 * 60 * 1000);
            return day;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 金额换算 String换算成int
     */
    public static int setChageMoney(String money) {
        if (money.contains(".")) {
            int renm = (int) (Double.valueOf(money) * 100);
           /* if (renm < 10) {
                ToastUtil.show("金额至少1元");
            }*/
            return renm;
        } else {
           /* if (Integer.valueOf(money) * 100 < 10) {
                ToastUtil.show("金额至少1元");
            }*/
            return Integer.valueOf(money) * 100;
        }

    }

    /**
     * 获取url参数
     */
    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }


    /**
     * 判断当前应用程序是否运行
     */
    public static boolean isRunning(final Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : list) {
            String processName = appProcess.processName;
            if (processName != null && processName.equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    //加载图片
    public static void loadImage(Context context, final ImageView imageView, String path, int defaultImage) {
        Glide.with(context).load(path).transform(new GlideCircleTransform(context)).placeholder(defaultImage).error(defaultImage).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (resource != null)
                            imageView.setImageDrawable(resource);
                    }
                });
    }

    public static Bitmap setHttpBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }

    }

    public static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }

    }


    public static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }

    }

    /**
     * 裁剪后路径
     */
    public static String saveFile(Context c, String fileName, Bitmap bitmap) {
        return saveFile(c, "", fileName, bitmap);
    }

    public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(c, filePath, fileName, bytes);
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String saveFile(Context c, String filePath, String fileName, byte[] bytes) {
        String fileFullName = "";
        FileOutputStream fos = null;
        String dateFolder = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)
                .format(new Date());
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                filePath = Environment.getExternalStorageDirectory() + "/LvXing/" + dateFolder + "/";
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }

    public static Bitmap getVideoThumbnail(String src) {
        try {
            Log.d("FileUtil", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            //设置固定大小
            //需要的大小
            float newWidth = 200f;
            float newHeigth = 200f;

            //图片大小
            int width = myBitmap.getWidth();
            int height = myBitmap.getHeight();

            //缩放比例
            float scaleWidth = newWidth / width;
            float scaleHeigth = newHeigth / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeigth);

            Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
            return bitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static Bitmap getVideoMap(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(file), getBitmapOption(2));
        return bitmap;
    }

    public static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    /***
     * 将指定路径的图片转uri
     * @param context
     * @param path ，指定图片(或文件)的路径
     * @return
     */
    public static Uri getMediaUriFromPath(Context context, String path) {
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(mediaUri,
                null,
                MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                new String[]{path.substring(path.lastIndexOf("/") + 1)},
                null);

        Uri uri = null;
        if (cursor.moveToFirst()) {
            uri = ContentUris.withAppendedId(mediaUri,
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
        }
        cursor.close();
        return uri;
    }


    /**
     * 照片文件路径
     *
     */
    /**
     * 根据Uri返回文件路径
     */

    public static String getFilePath(Context context, Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(context, mUri);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static String getFilePathByUri(Context context, Uri mUri) throws FileNotFoundException {
        String imgPath;
        Cursor cursor = context.getContentResolver().query(mUri, null, null, null, null);
        cursor.moveToFirst();
        imgPath = cursor.getString(1); // 图片文件路径
        return imgPath;
    }


    public static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 下载图片
     *
     * @param context 上下文
     * @param path    图片经
     */
    public static void downImage(Context context, final String path) {
        Glide.with(context).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                String p = Environment.getExternalStorageDirectory().getAbsolutePath() + "";
                File fileCache = new File(p);
                if (!fileCache.exists()) {
                    try {
                        //创建文件夹
                        fileCache.mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //保存Bitmap到SD卡
                File file = new File(fileCache, CommonUtil.md5(path) + ".jpg");
                if (!file.exists()) {
                    ToastUtil.show("开始下载");
                    FileOutputStream stream = null;
                    try {
                        stream = new FileOutputStream(file);
                        //Bitmap压缩
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        stream.flush();
                        stream.close();
                        ToastUtil.show("下载成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.show("已经下载");
                }
            }
        });
    }

    /**
     * dip转px
     */
    public static int dipToPx(Context context, float dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * px转dip
     */
    public static int pxToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 隐藏键盘
     *
     * @param context
     * @param view
     */
    public static void hideInputSoft(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 显示键盘键盘
     *
     * @param context
     * @param view
     */
    public static void showInputSoft(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isSoftShowing(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Activity context) {
        WindowManager manager = context.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Activity context) {
        WindowManager manager = context.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height2 = outMetrics.heightPixels;
        return height2;
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws ParseException
     */
    public static boolean IDCardValidate(String IDStr) {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return false;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return false;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return false;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return false;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return false;
            }
        } else {
            return true;
        }
        // =====================(end)=====================
        return true;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    private static boolean isDataFormat(String str) {
        boolean flag = false;
        // String
        // regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 发布广播信息
     */
    public static void setUpTaskType(Activity activity, String type) {
        // LocalBroadcastManager.getInstance(activity).sendBroadcast(new Intent(ConstantsBean.YDTASK).putExtra("type", type))
        ;
    }

    /**
     * 生成二维码与解析
     *
     * @param
     */
    public static Bitmap SetCreatQr(String content) {
        Bitmap bitmap = creatQr(content);
        return bitmap;
    }

    private static Bitmap creatQr(String content) {
        String newStr = content;
        try {
            String str = new String(newStr.getBytes(), "ISO-8859-1");
            Hashtable<EncodeHintType, String> hashtable = new Hashtable<>();
            hashtable.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
            BitMatrix bitMatrix = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, 400, 400, hashtable);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixels[y * width + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }

            bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, 400, 400);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void saveImageToGallery(Context context, Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "pinlvxing");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        ToastUtil.show("二维码已经保存到相册，快去分享给好友吧~");
    }

    public static File getAbsoluteImagePath(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
             Log.e("裁剪",e.getMessage());
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            Log.e("裁剪",e.getMessage());
            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps==null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }
    private static String birthday;
      private static String ageStr;
      private static int age;
      //出生年、月、日
      private static int year;
     private static int month;
     private static int day;
    public static String BirthdayToAge(String birthday1) {
                 birthday = birthday1;
                 stringToInt(birthday, "yyyy-MM-dd");
                 // 得到当前时间的年、月、日
                 Calendar cal = Calendar.getInstance();
                 int yearNow = cal.get(Calendar.YEAR);
                 int monthNow = cal.get(Calendar.MONTH) + 1;
                 int dayNow = cal.get(Calendar.DATE);
                 // 用当前年月日减去出生年月日
                 int yearMinus = yearNow - year;
                 int monthMinus = monthNow - month;
                 int dayMinus = dayNow - day;
                 age = yearMinus;// 先大致赋值
                 if (yearMinus <= 0) {
                         age = 0;
                         ageStr = String.valueOf(age);
                         return ageStr;
                     }
                 if (monthMinus < 0) {
                         age = age - 1;
                     } else if (monthMinus == 0) {
                         if (dayMinus < 0) {
                                 age = age - 1;
                             }
                     }
                 ageStr = String.valueOf(age);
                 return ageStr;
             }
    /**
     65      * String类型转换为long类型
     66      * .............................
     67      * strTime为要转换的String类型时间
     68      * formatType时间格式
     69      * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     70      * strTime的时间格式和formatType的时间格式必须相同
     71      */
     private static void stringToInt(String strTime, String formatType) {
                 try {
                         //String类型转换为date类型
                         Calendar calendar = Calendar.getInstance();
                         Date date = stringToDate(strTime, formatType);
                         calendar.setTime(date);
                         if (date == null) {
                             } else {
                                 //date类型转成long类型
                                 year = calendar.get(Calendar.YEAR);
                                 month = calendar.get(Calendar.MONTH) + 1;
                                 day = calendar.get(Calendar.DAY_OF_MONTH);
                             }
                     } catch (Exception e) {
                         Log.d("Exception：" , e+"");
                     }
             }
             /**
                     46      * String类型转换成date类型
47      * strTime: 要转换的string类型的时间，
            48      * formatType: 要转换的格式yyyy-MM-dd HH:mm:ss
49      * //yyyy年MM月dd日 HH时mm分ss秒，
        50      * strTime的时间格式必须要与formatType的时间格式相同
51      */
             private static Date stringToDate(String strTime, String formatType) {
                 try {
                         SimpleDateFormat formatter = new SimpleDateFormat(formatType);
                         Date date;
                         date = formatter.parse(strTime);
                         return date;
                     } catch (Exception e) {
                         return null;
                     }
             }


    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    public static int getDatNub(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date_start = null;
        Date date_end = null;
        try {
            date_start = sdf.parse(time);
            date_end = sdf.parse(getDayTime());
        } catch (java.text.ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
       return getGapCount(date_start,date_end);
    }
}
