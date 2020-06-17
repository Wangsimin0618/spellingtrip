package com.spellingtrip.example.bean;

import org.litepal.crud.LitePalSupport;

public class UserBean   extends LitePalSupport {

    /**
     * code : 0
     * count : 0
     * data : {"ImUsername":"1102871704486543361","tell":"15224204068","ImPassword":"daa28096f9e8879ab3a02b90aa0e2f83","userId":22}
     * msg : success
     * objId : 0
     */

    private int code;
    private int count;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
