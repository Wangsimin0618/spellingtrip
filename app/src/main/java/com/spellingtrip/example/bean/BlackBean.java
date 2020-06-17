package com.spellingtrip.example.bean;

import com.spellingtrip.example.retrofit.bean.BaseBean;

public class BlackBean extends BaseBean{

    /**
     * code : 0
     * count : 0
     * data : null
     * msg :
     * objId : 0
     */

    private int code;
    private int count;
    private Object data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
