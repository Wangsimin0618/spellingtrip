package com.spellingtrip.example.bean;

import java.util.List;

public class MyCityActivityBean {

    /**
     * code : 0
     * count : 0
     * data : [{"creator":true,"cost":313,"endDate":"2019.04.24 09:00","typeName":null,"favoriteStatus":false,"joinStatus":1,"content":"uejjsks","nick":"游友","activityId":10,"datetime":"2019.04.23 09:00 - 2019.04.24 09:00","payType":1,"userCount":2,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190425/1121342963079708672.png","location":"聊城市在经开南苑新城附近","joinedCount":"1/2","startDate":"2019.04.23 09:00","status":0},{"creator":true,"cost":120,"endDate":"2019.04.24 09:00","typeName":null,"favoriteStatus":false,"joinStatus":1,"content":"hhhhhh","nick":"游友","activityId":11,"datetime":"2019.04.23 09:00 - 2019.04.24 09:00","payType":2,"userCount":1,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190425/1121343992970084352.png","location":"姜堤乐园","joinedCount":"1/1","startDate":"2019.04.23 09:00","status":0}]
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
         * creator : true
         * cost : 313
         * endDate : 2019.04.24 09:00
         * typeName : null
         * favoriteStatus : false
         * joinStatus : 1
         * content : uejjsks
         * nick : 游友
         * activityId : 10
         * datetime : 2019.04.23 09:00 - 2019.04.24 09:00
         * payType : 1
         * userCount : 2
         * coverImage : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190425/1121342963079708672.png
         * location : 聊城市在经开南苑新城附近
         * joinedCount : 1/2
         * startDate : 2019.04.23 09:00
         * status : 0
         */

        private boolean creator;
        private int cost;
        private String endDate;
        private Object typeName;
        private boolean favoriteStatus;
        private int joinStatus;
        private String content;
        private String nick;
        private int activityId;
        private String datetime;
        private int payType;
        private int userCount;
        private String coverImage;
        private String location;
        private String joinedCount;
        private String startDate;
        private int status;

        public boolean isCreator() {
            return creator;
        }

        public void setCreator(boolean creator) {
            this.creator = creator;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public Object getTypeName() {
            return typeName;
        }

        public void setTypeName(Object typeName) {
            this.typeName = typeName;
        }

        public boolean isFavoriteStatus() {
            return favoriteStatus;
        }

        public void setFavoriteStatus(boolean favoriteStatus) {
            this.favoriteStatus = favoriteStatus;
        }

        public int getJoinStatus() {
            return joinStatus;
        }

        public void setJoinStatus(int joinStatus) {
            this.joinStatus = joinStatus;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getUserCount() {
            return userCount;
        }

        public void setUserCount(int userCount) {
            this.userCount = userCount;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getJoinedCount() {
            return joinedCount;
        }

        public void setJoinedCount(String joinedCount) {
            this.joinedCount = joinedCount;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
