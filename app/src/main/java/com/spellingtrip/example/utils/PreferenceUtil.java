package com.spellingtrip.example.utils;

import android.content.SharedPreferences;

import com.spellingtrip.example.CustomApplication;
import com.spellingtrip.example.bean.ChatUserBean;
import com.spellingtrip.example.bean.LabelBean;
import com.spellingtrip.example.bean.UserInfoDataBean;
import com.spellingtrip.example.bean.UserShowBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static android.content.Context.MODE_PRIVATE;

/**
 * @summary 偏好设置工具类
 */
public class PreferenceUtil {
    /**
     * 存放布尔值
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String name, String key, boolean value) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    /**
     * 获取布尔值
     *
     * @param key      键
     * @param defValue 默认值
     */
    public static boolean getBoolean(String name, String key, boolean defValue) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }


    /**
     * 存放字符串
     *
     * @param key
     * @param value
     */
    public static void putString(String name, String key, String value) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * 获取字符串
     *
     * @param key 键
     */
    public static String getString(String name, String key) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        return sp.getString(key, null);
    }
    /**
     * 存放标签列表字符串
     *
     * @param key
     * @param value
     */
    public static void putLabels(String name, String key, List<UserInfoDataBean.LabelsBean> value) {
        String labels=JsonUtil.toJson(value);
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, labels);
        edit.apply();
    }
    /**
     * 存放标签列表字符串
     *
     * @param key
     * @param value
     */
    public static void putCardLabels(String name, String key, List<UserShowBean.DataBean.LabelsBean> value) {
        String labels=JsonUtil.toJson(value);
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, labels);
        edit.apply();
    }
    /**
     * 获取标签字符串
     *
     * @param key 键
     */
    public static List<UserShowBean.DataBean.LabelsBean> getCardLabels(String name, String key) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        List<UserShowBean.DataBean.LabelsBean>labelsBeans=JsonUtil.fromJsonList(sp.getString(key,null),UserShowBean.DataBean.LabelsBean.class);
        return labelsBeans;
    }
    /**
     * 获取标签字符串
     *
     * @param key 键
     */
    public static List<UserInfoDataBean.LabelsBean> getLabels(String name, String key) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        List<UserInfoDataBean.LabelsBean>labelsBeans=JsonUtil.fromJsonList(sp.getString(key,null),UserInfoDataBean.LabelsBean.class);
        return labelsBeans;
    }
    /**
     * 存放字符串
     *
     * @param key
     * @param value
     */
    public static void putInt(String name, String key, int value) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    /**
     * 获取字符串
     *
     * @param key 键
     */
    public static int getInt(String name, String key) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    /**
     * 清空偏好设置
     */
    public static void clearPreference(String name) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    /**
     * 设置列表数据
     *
     * @param key   键
     * @param value
     */
    public static void putList(String name, String key, Set<String> value) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        //关键操作 需要在新的集合添加值 然后再提交修改
        SharedPreferences.Editor edit = sp.edit();
        edit.putStringSet(key, value);
        edit.apply();
    }

    /**
     * 获取列表数据
     *
     * @param key 键
     */
    public static Set<String> getList(String name, String key) {
        SharedPreferences sp = CustomApplication.context.getSharedPreferences(name, MODE_PRIVATE);
        Set<String> changeData = new HashSet<String>();
        return sp.getStringSet(key, changeData);
    }


}
