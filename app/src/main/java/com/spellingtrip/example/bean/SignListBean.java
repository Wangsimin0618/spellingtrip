package com.spellingtrip.example.bean;

import java.util.List;

public class SignListBean {

    /**
     * code : 0
     * count : 0
     * data : [{"signDate":"2019-05-01","signType":1},{"signDate":"2019-05-02","signType":1},{"signDate":"2019-05-09","signType":2}]
     * msg : 恭喜签到成功
     * objId : 0，
     */

    private int code;
    private int count;
    private String msg;
    private String objId;
    private List<DataBean> data;

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

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * signDate : 2019-05-01
         * signType : 1
         */

        private String signDate;
        private int signType;

        public String getSignDate() {
            return signDate;
        }

        public void setSignDate(String signDate) {
            this.signDate = signDate;
        }

        public int getSignType() {
            return signType;
        }

        public void setSignType(int signType) {
            this.signType = signType;
        }
    }
}
