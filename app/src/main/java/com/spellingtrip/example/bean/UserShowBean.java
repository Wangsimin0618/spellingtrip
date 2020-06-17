package com.spellingtrip.example.bean;

import java.util.List;

public class UserShowBean {

    /**
     * code : 0
     * count : 0
     * data : {"birthday":"1992-03-01","ImUsername":"1111471551116476416","city":"阿拉善盟","sex":"女","tell":"15224204068","idcardAuth":true,"authStatus":2,"ImPassword":"dd4b21e9ef71e1291183a46b913ae6f2","headUrl":"https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1194859777214119937.png","vipExpireTime":"2020-05-04 15:27:59","description":"朋友一起来","bgImages":["https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1197035308814696448.png"],"points":11,"labels":[{"labelId":2,"labelName":"旅行"},{"labelId":3,"labelName":"Cosplay"},{"labelId":4,"labelName":"自由行"},{"labelId":5,"labelName":"自驾游"},{"labelId":6,"labelName":"骑行"},{"labelId":9,"labelName":"科技"},{"labelId":10,"labelName":"漫威"},{"labelId":11,"labelName":"复古"},{"labelId":12,"labelName":"手办"},{"labelId":14,"labelName":"花农"},{"labelId":19,"labelName":"摄影"},{"labelId":20,"labelName":"美拍"}],"nick":"哈UC啊","loginTime":"2019-11-21 15:38:50","id":1,"vip":true,"username":"15224204068"}
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
         * birthday : 1992-03-01
         * ImUsername : 1111471551116476416
         * city : 阿拉善盟
         * sex : 女
         * tell : 15224204068
         * idcardAuth : true
         * authStatus : 2
         * ImPassword : dd4b21e9ef71e1291183a46b913ae6f2
         * headUrl : https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1194859777214119937.png
         * vipExpireTime : 2020-05-04 15:27:59
         * description : 朋友一起来
         * bgImages : ["https://testpinpinlx.oss-cn-beijing.aliyuncs.com/201911/1197035308814696448.png"]
         * points : 11
         * labels : [{"labelId":2,"labelName":"旅行"},{"labelId":3,"labelName":"Cosplay"},{"labelId":4,"labelName":"自由行"},{"labelId":5,"labelName":"自驾游"},{"labelId":6,"labelName":"骑行"},{"labelId":9,"labelName":"科技"},{"labelId":10,"labelName":"漫威"},{"labelId":11,"labelName":"复古"},{"labelId":12,"labelName":"手办"},{"labelId":14,"labelName":"花农"},{"labelId":19,"labelName":"摄影"},{"labelId":20,"labelName":"美拍"}]
         * nick : 哈UC啊
         * loginTime : 2019-11-21 15:38:50
         * id : 1
         * vip : true
         * username : 15224204068
         */

        private String birthday;
        private String ImUsername;
        private String city;
        private String sex;
        private String tell;
        private boolean idcardAuth;
        private int authStatus;
        private String ImPassword;
        private String headUrl;
        private String vipExpireTime;
        private String description;
        private int points;
        private String nick;
        private String loginTime;
        private int id;
        private boolean vip;
        private String username;
        private List<String> bgImages;
        private List<LabelsBean> labels;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getImUsername() {
            return ImUsername;
        }

        public void setImUsername(String ImUsername) {
            this.ImUsername = ImUsername;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTell() {
            return tell;
        }

        public void setTell(String tell) {
            this.tell = tell;
        }

        public boolean isIdcardAuth() {
            return idcardAuth;
        }

        public void setIdcardAuth(boolean idcardAuth) {
            this.idcardAuth = idcardAuth;
        }

        public int getAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(int authStatus) {
            this.authStatus = authStatus;
        }

        public String getImPassword() {
            return ImPassword;
        }

        public void setImPassword(String ImPassword) {
            this.ImPassword = ImPassword;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getVipExpireTime() {
            return vipExpireTime;
        }

        public void setVipExpireTime(String vipExpireTime) {
            this.vipExpireTime = vipExpireTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isVip() {
            return vip;
        }

        public void setVip(boolean vip) {
            this.vip = vip;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<String> getBgImages() {
            return bgImages;
        }

        public void setBgImages(List<String> bgImages) {
            this.bgImages = bgImages;
        }

        public List<LabelsBean> getLabels() {
            return labels;
        }

        public void setLabels(List<LabelsBean> labels) {
            this.labels = labels;
        }

        public static class LabelsBean {
            /**
             * labelId : 2
             * labelName : 旅行
             */

            private int labelId;
            private String labelName;

            public int getLabelId() {
                return labelId;
            }

            public void setLabelId(int labelId) {
                this.labelId = labelId;
            }

            public String getLabelName() {
                return labelName;
            }

            public void setLabelName(String labelName) {
                this.labelName = labelName;
            }
        }
    }
}
