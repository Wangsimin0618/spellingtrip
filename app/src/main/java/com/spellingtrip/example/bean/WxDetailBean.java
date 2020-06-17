package com.spellingtrip.example.bean;

import com.google.gson.annotations.SerializedName;

public class WxDetailBean {

    /**
     * code : 0
     * count : 0
     * data : {"package":"Sign=WXPay","appid":"wx5b405dc55cfc282f","sign":"AE7FEF441209DEEFFDD3AA5E8C231D9C97477E75119C65D12FCB6621E0EF82F9","partnerid":"1561286641","prepayid":"wx03084902418160ba6287f2341396882200","noncestr":"1572742144379","timestamp":"1572742144"}
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
         * package : Sign=WXPay
         * appid : wx5b405dc55cfc282f
         * sign : AE7FEF441209DEEFFDD3AA5E8C231D9C97477E75119C65D12FCB6621E0EF82F9
         * partnerid : 1561286641
         * prepayid : wx03084902418160ba6287f2341396882200
         * noncestr : 1572742144379
         * timestamp : 1572742144
         */

        @SerializedName("package")
        private String packageX;
        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
