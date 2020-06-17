package com.spellingtrip.example.bean;

public class PinYouInfoBean {

    /**
     * code : 0
     * count : 0
     * data : {"expectedAge":"10-60","imUserName":"1111471551116476416","infoId":42,"toArea":"南京","endDate":"2019.04.05","sex":"女","idcardAuth":false,"headUrl":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190402/1112975828439793664.png","description":"miaoshu","expectedSex":"不限","totalCount":2,"userId":1,"nick":"游友","fromArea":"深圳","diffDays":1,"startDate":"2019.04.04"}
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
         * expectedAge : 10-60
         * imUserName : 1111471551116476416
         * infoId : 42
         * toArea : 南京
         * endDate : 2019.04.05
         * sex : 女
         * idcardAuth : false
         * headUrl : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190402/1112975828439793664.png
         * description : miaoshu
         * expectedSex : 不限
         * totalCount : 2
         * userId : 1
         * nick : 游友
         * fromArea : 深圳
         * diffDays : 1
         * startDate : 2019.04.04
         */

        private String expectedAge;
        private String imUserName;
        private int infoId;
        private String toArea;
        private String endDate;
        private String sex;
        private boolean idcardAuth;
        private String headUrl;
        private String description;
        private String expectedSex;
        private int totalCount;
        private int userId;
        private String nick;
        private String fromArea;
        private int diffDays;
        private String startDate;

        public String getExpectedAge() {
            return expectedAge;
        }

        public void setExpectedAge(String expectedAge) {
            this.expectedAge = expectedAge;
        }

        public String getImUserName() {
            return imUserName;
        }

        public void setImUserName(String imUserName) {
            this.imUserName = imUserName;
        }

        public int getInfoId() {
            return infoId;
        }

        public void setInfoId(int infoId) {
            this.infoId = infoId;
        }

        public String getToArea() {
            return toArea;
        }

        public void setToArea(String toArea) {
            this.toArea = toArea;
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

        public boolean isIdcardAuth() {
            return idcardAuth;
        }

        public void setIdcardAuth(boolean idcardAuth) {
            this.idcardAuth = idcardAuth;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getExpectedSex() {
            return expectedSex;
        }

        public void setExpectedSex(String expectedSex) {
            this.expectedSex = expectedSex;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getFromArea() {
            return fromArea;
        }

        public void setFromArea(String fromArea) {
            this.fromArea = fromArea;
        }

        public int getDiffDays() {
            return diffDays;
        }

        public void setDiffDays(int diffDays) {
            this.diffDays = diffDays;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
    }
}
