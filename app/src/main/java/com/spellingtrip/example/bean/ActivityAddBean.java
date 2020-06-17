package com.spellingtrip.example.bean;

public class ActivityAddBean {

    /**
     * code : 0
     * count : 0
     * data : {"text":"已申请加入，等待活动发起人通过","applyStatus":true}
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

    public static class DataBean {
        /**
         * text : 已申请加入，等待活动发起人通过
         * applyStatus : true
         */

        private String text;
        private boolean applyStatus;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(boolean applyStatus) {
            this.applyStatus = applyStatus;
        }
    }
}
