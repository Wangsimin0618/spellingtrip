package com.spellingtrip.example.utils;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class EventType {
    private String Tsg;
    private String mMsg;
    private String mType;

    public EventType(String type, String msg) {
        // TODO Auto-generated constructor stub
        mType = type;
        mMsg = msg;
    }

    public EventType(String msg) {
        // TODO Auto-generated constructor stub
        Tsg = msg;
    }

    public String getTypeMsg() {
        return mType + mMsg;
    }

    public String getMsg() {
        return Tsg;
    }
}
