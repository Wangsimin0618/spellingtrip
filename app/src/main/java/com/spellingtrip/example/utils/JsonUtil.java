package com.spellingtrip.example.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * version 1.0
 */
public class JsonUtil {
    /**
     * 描述：将对象转化为json.
     *
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(src);
    }

    /**
     * 描述：将列表转化为json.
     *
     * @param list
     * @return
     */
    public static String toJson(List<?> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(list);
    }

    /**
     * 描述：将json转化为列表.
     *
     * @param json
     * @param //   new TypeToken<ArrayList<?>>() {};
     * @return
     */
    public static List fromJson(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        TypeToken typeToken = new TypeToken<ArrayList<?>>() {
        };
        Type type = typeToken.getType();
        return gson.fromJson(json, type);
    }


    public static <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            mList.add(gson.fromJson(elem, cls));
        }
        return mList;
    }

    /**
     * 获取list
     *
     * @param json      数据源
     * @param typeToken TypeToken typeToken = new TypeToken<ArrayList<?>>() {};
     * @return
     */
    public static List<?> fromJson(String json, TypeToken typeToken) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type type = typeToken.getType();
        return gson.fromJson(json, type);
    }


    /**
     * 描述：将json转化为对象.
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object fromJson(String json, Class clazz) {
        GsonBuilder typeToken = new GsonBuilder();
        Gson gson = typeToken.create();
        return gson.fromJson(json, clazz);
    }

}
