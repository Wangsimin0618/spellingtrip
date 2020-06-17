package com.spellingtrip.example.bean;

public class PiPeiPublishBean {

    /**
     * code : 1
     * count : 0
     * data : {"tourLimit":true}
     * msg : 非会员用户可免费发布【3】次免费拼游,您的免费次数已经用完了,开通会员可无限次数发布拼游
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
         * tourLimit : true
         */

        private boolean tourLimit;

        public boolean isTourLimit() {
            return tourLimit;
        }

        public void setTourLimit(boolean tourLimit) {
            this.tourLimit = tourLimit;
        }
    }
}
