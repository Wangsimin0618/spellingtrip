package com.spellingtrip.example.bean;

public class UpdateBean {

    /**
     * code : 0
     * count : 0
     * data : {"appVersion":"1.0.1","datetime":"2018-12-08 23:12:42","forceUpdate":false,"logInfo":"[\"1.添加自动匹配功能\",\"2.添加自动匹配功能\",\"3.添加自动匹配功能\"]"}
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
         * appVersion : 1.0.1
         * datetime : 2018-12-08 23:12:42
         * forceUpdate : false
         * logInfo : ["1.添加自动匹配功能","2.添加自动匹配功能","3.添加自动匹配功能"]
         */

        private String appVersion;
        private String datetime;
        private boolean forceUpdate;
        private String logInfo;

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public boolean isForceUpdate() {
            return forceUpdate;
        }

        public void setForceUpdate(boolean forceUpdate) {
            this.forceUpdate = forceUpdate;
        }

        public String getLogInfo() {
            return logInfo;
        }

        public void setLogInfo(String logInfo) {
            this.logInfo = logInfo;
        }
    }
}
