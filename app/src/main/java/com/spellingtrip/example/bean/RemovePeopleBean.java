package com.spellingtrip.example.bean;

import java.util.List;

public class RemovePeopleBean {

    /**
     * code : 0
     * count : 0
     * data : []
     * msg : 移除成功
     * objId : 0
     */

    private int code;
    private int count;
    private String msg;
    private int objId;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
