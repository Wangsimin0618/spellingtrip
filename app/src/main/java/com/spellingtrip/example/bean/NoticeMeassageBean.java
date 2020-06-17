package com.spellingtrip.example.bean;

import java.util.List;

public class NoticeMeassageBean {

    /**
     * code : 0
     * count : 0
     * data : [{"readStatus":false,"senderId":3,"senderAvatar":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190418/1118759081478193152.png","messageId":1,"time":"47秒前","senderNick":"yyt","title":"yyt申请加入【姜堤乐园-约拍】","content":"yyt申请加入【姜堤乐园-约拍】"}]
     * msg : success
     * objId : 0
     */

    private int code;
    private int count;
    private String msg;
    private int objId;
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

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
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
         * readStatus : false
         * senderId : 3
         * senderAvatar : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190418/1118759081478193152.png
         * messageId : 1
         * time : 47秒前
         * senderNick : yyt
         * title : yyt申请加入【姜堤乐园-约拍】
         * content : yyt申请加入【姜堤乐园-约拍】
         */

        private boolean readStatus;
        private int senderId;
        private String senderAvatar;
        private int messageId;
        private String time;
        private String senderNick;
        private String title;
        private String content;

        public boolean isReadStatus() {
            return readStatus;
        }

        public void setReadStatus(boolean readStatus) {
            this.readStatus = readStatus;
        }

        public int getSenderId() {
            return senderId;
        }

        public void setSenderId(int senderId) {
            this.senderId = senderId;
        }

        public String getSenderAvatar() {
            return senderAvatar;
        }

        public void setSenderAvatar(String senderAvatar) {
            this.senderAvatar = senderAvatar;
        }

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSenderNick() {
            return senderNick;
        }

        public void setSenderNick(String senderNick) {
            this.senderNick = senderNick;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
