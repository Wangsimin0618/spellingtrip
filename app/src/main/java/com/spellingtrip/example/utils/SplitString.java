package com.spellingtrip.example.utils;

/**
 * date:2020/4/27
 * author:王思敏
 * function
 */
public class SplitString {

    public static String getImg(String images) {
        String[] a = images.split("，");
        return a[0];
    }
}
