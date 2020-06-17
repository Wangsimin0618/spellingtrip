package com.spellingtrip.example.bean;

import java.util.List;

public class ActivityDetailBean {

    /**
     * code : 0
     * count : 0
     * data : {"creator":1,"cost":313,"typeName":"咖啡","originator":true,"favoriteStatus":false,"joinStatus":1,"content":"uejjsks","datetime":"04-23 09:00 至 04-24 09:00","creatorImUsername":"1111471551116476416","payType":1,"userCount":2,"coverImage":["https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190425/1121342963079708672.png"],"members":[{"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190418/1118760459210588160.png","userId":1}],"location":"聊城市在经开南苑新城附近","joinedCount":"1/2","creatorImPassword":"29c3eea3f305d6b823f562ac4be35217","status":0}
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
         * creator : 1
         * cost : 313
         * typeName : 咖啡
         * originator : true
         * favoriteStatus : false
         * joinStatus : 1
         * content : uejjsks
         * datetime : 04-23 09:00 至 04-24 09:00
         * creatorImUsername : 1111471551116476416
         * payType : 1
         * userCount : 2
         * coverImage : ["https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190425/1121342963079708672.png"]
         * members : [{"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190418/1118760459210588160.png","userId":1}]
         * location : 聊城市在经开南苑新城附近
         * joinedCount : 1/2
         * creatorImPassword : 29c3eea3f305d6b823f562ac4be35217
         * status : 0
         */

        private int creator;
        private int cost;
        private String typeName;
        private boolean originator;
        private boolean favoriteStatus;
        private int joinStatus;
        private String content;
        private String nick;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        private String datetime;
        private String creatorImUsername;
        private int payType;
        private int userCount;
        private String location;
        private String joinedCount;
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

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
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
             * headUrl : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190418/1118760459210588160.png
             * userId : 1
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
