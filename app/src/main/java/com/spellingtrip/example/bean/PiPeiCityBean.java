package com.spellingtrip.example.bean;

import java.util.List;

public class PiPeiCityBean {

    /**
     * code : 0
     * count : 0
     * data : [{"cost":3423,"endDate":"2019.08.09","typeName":"登山","favoriteStatus":false,"joinStatus":-1,"userId":null,"content":"请尽量丰富描述你的活动内容及行程2222222222","activityId":2,"datetime":"2019.08.09 19:50 - 2019.08.09 19:50","payType":1,"userCount":17,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190421/1119899800179834880.jpg","location":"北京","startDate":"2019.08.09","status":0},{"cost":0,"endDate":"2019.09.09","typeName":"逛街","favoriteStatus":false,"joinStatus":-1,"userId":null,"content":"89382938298392","activityId":3,"datetime":"2019.08.09 19:46 - 2019.09.09 19:45","payType":1,"userCount":11,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190421/1119904904480030720.jpg","location":"北京","startDate":"2019.08.09","status":0},{"cost":5555,"endDate":"2019.08.10","typeName":"跑步","favoriteStatus":false,"joinStatus":-1,"userId":null,"content":"请尽量丰富描述你的活动内容及行程43434343","activityId":4,"datetime":"2019.07.09 19:50 - 2019.08.10 19:50","payType":0,"userCount":5,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190421/1119909360827367424.jpg","location":"北京","startDate":"2019.07.09","status":0},{"cost":43,"endDate":"2019.08.10","typeName":"逛街","favoriteStatus":false,"joinStatus":-1,"userId":null,"content":"请尽量丰富描述你的活动内容及行程22222","activityId":5,"datetime":"2019.08.09 19:50 - 2019.08.10 19:50","payType":0,"userCount":7,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190421/1119910681877938176.jpg","location":"北京","startDate":"2019.08.09","status":0},{"cost":1000,"endDate":"2019.08.09","typeName":"骑单车","favoriteStatus":false,"joinStatus":-1,"userId":null,"content":"一起去玩啊","activityId":6,"datetime":"2019.08.09 19:50 - 2019.08.09 20:50","payType":0,"userCount":13,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190422/1120011443148685312.jpg","location":"北京","startDate":"2019.08.09","status":0},{"cost":100,"endDate":"2019.08.09","typeName":"唱歌","favoriteStatus":false,"joinStatus":-1,"userId":null,"content":"测试测试","activityId":7,"datetime":"2019.08.09 19:50 - 2019.08.09 19:50","payType":0,"userCount":5,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190422/1120231834144210944.jpg","location":"北京","startDate":"2019.08.09","status":0}]
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
         * cost : 3423
         * endDate : 2019.08.09
         * typeName : 登山
         * favoriteStatus : false
         * joinStatus : -1
         * userId : null
         * content : 请尽量丰富描述你的活动内容及行程2222222222
         * activityId : 2
         * datetime : 2019.08.09 19:50 - 2019.08.09 19:50
         * payType : 1
         * userCount : 17
         * coverImage : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190421/1119899800179834880.jpg
         * location : 北京
         * startDate : 2019.08.09
         * status : 0
         */

        private int cost;
        private String endDate;
        private String typeName;
        private boolean favoriteStatus;
        private int joinStatus;
        private Object userId;
        private String content;
        private int activityId;
        private String datetime;
        private int payType;
        private int userCount;
        private String coverImage;
        private String location;
        private String startDate;
        private int status;

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

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
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

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
