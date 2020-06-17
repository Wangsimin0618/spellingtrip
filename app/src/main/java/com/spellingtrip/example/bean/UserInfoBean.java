package com.spellingtrip.example.bean;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public class UserInfoBean extends LitePalSupport {

    private int code;
    private int count;
    private UserInfoDataBean data;
    private String msg;
    private int objId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UserInfoDataBean getData() {
        return data;
    }

    public void setData(UserInfoDataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        this.objId = objId;
    }


}
