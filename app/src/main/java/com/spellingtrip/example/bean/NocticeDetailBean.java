package com.spellingtrip.example.bean;

public class NocticeDetailBean {

    /**
     * code : 0
     * count : 0
     * data : {"content":"yyt申请加入【姜堤乐园-约拍】","createTime":"2019-04-28 14:54:23","id":1,"notifyType":1,"readStatus":false,"sender":"1111501051195817984","senderAvatar":"","senderId":0,"senderNick":"","target":"1121343995440529408","targetType":1,"title":"yyt申请加入【姜堤乐园-约拍】","uuid":"1122393606741557248"}
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
         * content : yyt申请加入【姜堤乐园-约拍】
         * createTime : 2019-04-28 14:54:23
         * id : 1
         * notifyType : 1
         * readStatus : false
         * sender : 1111501051195817984
         * senderAvatar :
         * senderId : 0
         * senderNick :
         * target : 1121343995440529408
         * targetType : 1
         * title : yyt申请加入【姜堤乐园-约拍】
         * uuid : 1122393606741557248
         */

        private String content;
        private String createTime;
        private int id;
        private int notifyType;
        private boolean readStatus;
        private String sender;
        private String senderAvatar;
        private int senderId;
        private String senderNick;
        private String target;
        private int targetType;
        private String title;
        private String uuid;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNotifyType() {
            return notifyType;
        }

        public void setNotifyType(int notifyType) {
            this.notifyType = notifyType;
        }

        public boolean isReadStatus() {
            return readStatus;
        }

        public void setReadStatus(boolean readStatus) {
            this.readStatus = readStatus;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getSenderAvatar() {
            return senderAvatar;
        }

        public void setSenderAvatar(String senderAvatar) {
            this.senderAvatar = senderAvatar;
        }

        public int getSenderId() {
            return senderId;
        }

        public void setSenderId(int senderId) {
            this.senderId = senderId;
        }

        public String getSenderNick() {
            return senderNick;
        }

        public void setSenderNick(String senderNick) {
            this.senderNick = senderNick;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public int getTargetType() {
            return targetType;
        }

        public void setTargetType(int targetType) {
            this.targetType = targetType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
