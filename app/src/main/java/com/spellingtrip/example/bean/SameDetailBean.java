package com.spellingtrip.example.bean;

import java.util.List;

/**
 * date:2020/5/6
 * author:王思敏
 * function同城活动详情
 */
public class SameDetailBean {

    /**
     * code : 0
     * count : 0
     * data : {"creator":17,"cost":150,"endDate":"2019.05.20 23:50","typeName":"唱歌","headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/201912/1208036749788315649.jpg","originator":false,"favoriteStatus":false,"joinStatus":100,"content":"唱歌","nick":"双子","datetime":"05-27 18:44 至 05-20 23:50","creatorImUsername":"1115100727782408193","payType":0,"userCount":15,"coverImage":["https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190521/1130759539658850304.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190521/1130759539977617408.jpg"],"members":[{"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/202004/1254817730977071104.png","userId":8},{"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190407/1554607000230.jpg","userId":12},{"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/201912/1208036749788315649.jpg","userId":17},{"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190522/1131114889985654784.jpg","userId":9929}],"location":"北京南站","joinedCount":"4/15","startDate":"2019.05.27 18:44","creatorImPassword":"2b8489fefed33d14e6f30e992cbecafe","status":2}
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
         * creator : 17
         * cost : 150
         * endDate : 2019.05.20 23:50
         * typeName : 唱歌
         * headUrl : https://pinpinlx.oss-cn-beijing.aliyuncs.com/201912/1208036749788315649.jpg
         * originator : false
         * favoriteStatus : false
         * joinStatus : 100
         * content : 唱歌
         * nick : 双子
         * datetime : 05-27 18:44 至 05-20 23:50
         * creatorImUsername : 1115100727782408193
         * payType : 0
         * userCount : 15
         * coverImage : ["https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190521/1130759539658850304.jpg","https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190521/1130759539977617408.jpg"]
         * members : [{"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/202004/1254817730977071104.png","userId":8},{"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190407/1554607000230.jpg","userId":12},{"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/201912/1208036749788315649.jpg","userId":17},{"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190522/1131114889985654784.jpg","userId":9929}]
         * location : 北京南站
         * joinedCount : 4/15
         * startDate : 2019.05.27 18:44
         * creatorImPassword : 2b8489fefed33d14e6f30e992cbecafe
         * status : 2
         */

        private int creator;
        private int cost;
        private String endDate;
        private String typeName;
        private String headUrl;
        private boolean originator;
        private boolean favoriteStatus;
        private int joinStatus;
        private String content;
        private String nick;
        private String datetime;
        private String creatorImUsername;
        private int payType;
        private int userCount;
        private String location;
        private String joinedCount;
        private String startDate;
        private String creatorImPassword;
        private int status;
        private List<String> coverImage;
        private List<MembersBean> members;

        public int getCreator() {
            return creator;
        }

        public void setCreator(int creator) {
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

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public boolean isOriginator() {
            return originator;
        }

        public void setOriginator(boolean originator) {
            this.originator = originator;
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

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getCreatorImUsername() {
            return creatorImUsername;
        }

        public void setCreatorImUsername(String creatorImUsername) {
            this.creatorImUsername = creatorImUsername;
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

        public String getCreatorImPassword() {
            return creatorImPassword;
        }

        public void setCreatorImPassword(String creatorImPassword) {
            this.creatorImPassword = creatorImPassword;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<String> getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(List<String> coverImage) {
            this.coverImage = coverImage;
        }

        public List<MembersBean> getMembers() {
            return members;
        }

        public void setMembers(List<MembersBean> members) {
            this.members = members;
        }

        public static class MembersBean {
            /**
             * headUrl : https://pinpinlx.oss-cn-beijing.aliyuncs.com/202004/1254817730977071104.png
             * userId : 8
             */

            private String headUrl;
            private int userId;

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
