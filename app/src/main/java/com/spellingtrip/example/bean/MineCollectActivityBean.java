package com.spellingtrip.example.bean;

import java.util.List;

public class MineCollectActivityBean {

    /**
     * code : 0
     * count : 0
     * data : {"total":0,"rows":[{"creator":false,"cost":1000,"endDate":"2019.08.09 20:50","typeName":null,"favoriteStatus":true,"joinStatus":-1,"content":"一起去玩啊","nick":"游友100023","activityId":6,"datetime":"2019.08.09 19:50 - 2019.08.09 20:50","payType":0,"userCount":13,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190422/1120011443148685312.jpg","location":"北京","joinedCount":"1/13","startDate":"2019.08.09 19:50","status":0},{"creator":true,"cost":313,"endDate":"2019.04.24 09:00","typeName":null,"favoriteStatus":true,"joinStatus":1,"content":"uejjsks","nick":"游友","activityId":10,"datetime":"2019.04.23 09:00 - 2019.04.24 09:00","payType":1,"userCount":2,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190425/1121342963079708672.png","location":"聊城市在经开南苑新城附近","joinedCount":"1/2","startDate":"2019.04.23 09:00","status":0}]}
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
         * total : 0
         * rows : [{"creator":false,"cost":1000,"endDate":"2019.08.09 20:50","typeName":null,"favoriteStatus":true,"joinStatus":-1,"content":"一起去玩啊","nick":"游友100023","activityId":6,"datetime":"2019.08.09 19:50 - 2019.08.09 20:50","payType":0,"userCount":13,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190422/1120011443148685312.jpg","location":"北京","joinedCount":"1/13","startDate":"2019.08.09 19:50","status":0},{"creator":true,"cost":313,"endDate":"2019.04.24 09:00","typeName":null,"favoriteStatus":true,"joinStatus":1,"content":"uejjsks","nick":"游友","activityId":10,"datetime":"2019.04.23 09:00 - 2019.04.24 09:00","payType":1,"userCount":2,"coverImage":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190425/1121342963079708672.png","location":"聊城市在经开南苑新城附近","joinedCount":"1/2","startDate":"2019.04.23 09:00","status":0}]
         */

        private int total;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * creator : false
             * cost : 1000
             * endDate : 2019.08.09 20:50
             * typeName : null
             * favoriteStatus : true
             * joinStatus : -1
             * content : 一起去玩啊
             * nick : 游友100023
             * activityId : 6
             * datetime : 2019.08.09 19:50 - 2019.08.09 20:50
             * payType : 0
             * userCount : 13
             * coverImage : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190422/1120011443148685312.jpg
             * location : 北京
             * joinedCount : 1/13
             * startDate : 2019.08.09 19:50
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
}
