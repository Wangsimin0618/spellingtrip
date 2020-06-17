package com.spellingtrip.example.bean;

import java.util.List;

/**
 * date:2020/5/5
 * author:王思敏
 * function同城活动
 */
public class SameBean {

    /**
     * code : 0
     * count : 0
     * data : [{"joinUserList":["https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1193926700795166720.jpg"],"cost":54545,"endDate":"01.02 04:46","sex":"女","headUrl":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1193926700795166720.jpg","typeName":"自驾游","active":false,"favoriteStatus":false,"joinStatus":100,"userId":4,"content":"就睡觉睡觉睡觉就睡觉","nick":"你报看那南安卡看看","activityId":5,"datetime":"12-30 04:45 - 01-02 04:46","payType":3,"userCount":7,"coverImage":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/20191230/1577652348454.jpg","location":"河南省郑州市二七区.好饿牛腩面烧烤吧","authType":1,"joinedCount":"1/7","startDate":"12.30 04:45","age":null,"status":0},{"joinUserList":["https://pinpinlx.oss-cn-beijing.aliyuncs.com/avatar.png"],"cost":200,"endDate":"01.16 15:57","sex":null,"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/avatar.png","typeName":"登山","active":false,"favoriteStatus":false,"joinStatus":100,"userId":8970,"content":"我们不一样啊都是不一样的","nick":"游友","activityId":1,"datetime":"12-26 15:57 - 01-16 15:57","payType":3,"userCount":10,"coverImage":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/20191226/1577347103631.jpg","location":"北京市东城区.天安门","authType":2,"joinedCount":"1/10","startDate":"12.26 15:57","age":null,"status":0},{"joinUserList":["https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1193926700795166720.jpg"],"cost":500,"endDate":"12.28 20:28","sex":"女","headUrl":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1193926700795166720.jpg","typeName":"登山","active":false,"favoriteStatus":false,"joinStatus":100,"userId":4,"content":"哈哈哈哈就哈哈办吧","nick":"你报看那南安卡看看","activityId":4,"datetime":"12-28 18:28 - 12-28 20:28","payType":3,"userCount":12,"coverImage":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/20191228/1577528950822.jpg","location":"海南省三亚市天涯区.三亚汽车站","authType":1,"joinedCount":"1/12","startDate":"12.28 18:28","age":null,"status":0},{"joinUserList":["https://pinpinlx.oss-cn-beijing.aliyuncs.com/avatar.png"],"cost":200,"endDate":"12.29 16:12","sex":null,"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/avatar.png","typeName":"逛街","active":false,"favoriteStatus":false,"joinStatus":100,"userId":8970,"content":"我们都是不一样的","nick":"游友","activityId":2,"datetime":"12-26 16:12 - 12-29 16:12","payType":3,"userCount":10,"coverImage":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/20191226/1577347984958.jpg","location":"北京市东城区.天安门","authType":2,"joinedCount":"1/10","startDate":"12.26 16:12","age":null,"status":0},{"joinUserList":["https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1193926700795166720.jpg"],"cost":500,"endDate":"12.28 20:28","sex":"女","headUrl":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1193926700795166720.jpg","typeName":"登山","active":false,"favoriteStatus":false,"joinStatus":100,"userId":4,"content":"哈哈哈哈就哈哈办吧","nick":"你报看那南安卡看看","activityId":3,"datetime":"12-28 18:28 - 12-28 20:28","payType":3,"userCount":12,"coverImage":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/20191228/1577528950822.jpg","location":"海南省三亚市天涯区.三亚汽车站","authType":1,"joinedCount":"1/12","startDate":"12.28 18:28","age":null,"status":0}]
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
         * joinUserList : ["https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1193926700795166720.jpg"]
         * cost : 54545
         * endDate : 01.02 04:46
         * sex : 女
         * headUrl : https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1193926700795166720.jpg
         * typeName : 自驾游
         * active : false
         * favoriteStatus : false
         * joinStatus : 100
         * userId : 4
         * content : 就睡觉睡觉睡觉就睡觉
         * nick : 你报看那南安卡看看
         * activityId : 5
         * datetime : 12-30 04:45 - 01-02 04:46
         * payType : 3
         * userCount : 7
         * coverImage : https://testpinpinlx.oss-cn-beijing.aliyuncs.com/20191230/1577652348454.jpg
         * location : 河南省郑州市二七区.好饿牛腩面烧烤吧
         * authType : 1
         * joinedCount : 1/7
         * startDate : 12.30 04:45
         * age : null
         * status : 0
         */

        private int cost;
        private String endDate;
        private String sex;
        private String headUrl;
        private String typeName;
        private boolean active;
        private boolean favoriteStatus;
        private int joinStatus;
        private int userId;
        private String content;
        private String nick;
        private int activityId;
        private String datetime;
        private int payType;
        private int userCount;
        private String coverImage;
        private String location;
        private int authType;
        private String joinedCount;
        private String startDate;
        private Object age;
        private int status;
        private List<String> joinUserList;

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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public int getAuthType() {
            return authType;
        }

        public void setAuthType(int authType) {
            this.authType = authType;
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

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<String> getJoinUserList() {
            return joinUserList;
        }

        public void setJoinUserList(List<String> joinUserList) {
            this.joinUserList = joinUserList;
        }
    }
}
