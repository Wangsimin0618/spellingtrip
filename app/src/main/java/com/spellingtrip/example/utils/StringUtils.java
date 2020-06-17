package com.spellingtrip.example.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.spellingtrip.example.bean.ConstantsBean;

import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Random;

public class StringUtils {

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @Title: int2ip
     * @Description: TODO(将ip的整数形式转换成ip形式 )
     * @param   @param ipInt
     * @param   @return
     * @return 返回类型  String
     * @throws
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     *
     * @Title: getLocalIpAddress
     * @Description: TODO(获取当前ip地址 )
     * @param   @param context
     * @param   @return
     * @return 返回类型  String
     * @throws
     */
    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
        // return null;
    }

    /**
     *
     * @Title: genTimeStamp
     * @Description: TODO(生成时间戳)
     * @param   @return
     * @return 返回类型  long
     * @throws
     */
    public static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     *
     * @Title: genAppSign
     * @Description: TODO(生成签名)
     * @param   @param params
     * @param   @return
     * @return 返回类型  String
     * @throws
     */
    public static String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(ConstantsBean.WXKEY);

        String appSign = CommonUtil.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }


    /**
     *
     * @Title: genPackageSign
     * @Description: TODO(生成签名)
     * @param   @param params
     * @param   @return
     * @return 返回类型  String
     * @throws
     */
    public static String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(ConstantsBean.WXKEY);
        String packageSign = CommonUtil.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }




    /**
     *
     * @Title: genNonceStr
     * @Description: TODO(随机字符串)
     * @param   @return
     * @return 返回类型  String
     * @throws
     */
    public static  String genNonceStr() {
        Random random = new Random();
        return CommonUtil.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

}
