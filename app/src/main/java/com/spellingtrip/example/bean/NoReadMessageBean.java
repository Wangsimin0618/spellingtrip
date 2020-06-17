package com.spellingtrip.example.bean;

public class NoReadMessageBean {

    /**
     * code : 0
     * count : 0
     * data : {"announceCount":0,"messageCount":0,"remindCount":0}
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
         * announceCount : 0
         * messageCount : 0
         * remindCount : 0
         */

        private int announceCount;
        private int messageCount;
        private int remindCount;

        public int getAnnounceCount() {
            return announceCount;
        }

        public void setAnnounceCount(int announceCount) {
            this.announceCount = announceCount;
        }

        public int getMessageCount() {
            return messageCount;
        }

        public void setMessageCount(int messageCount) {
            this.messageCount = messageCount;
        }

        public int getRemindCount() {
            return remindCount;
        }

        public void setRemindCount(int remindCount) {
            this.remindCount = remindCount;
        }
    }
}
