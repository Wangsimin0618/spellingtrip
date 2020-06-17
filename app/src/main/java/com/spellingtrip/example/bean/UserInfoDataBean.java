package com.spellingtrip.example.bean;

import org.litepal.crud.LitePalSupport;

import java.util.List;

public  class UserInfoDataBean extends LitePalSupport {


    /**
     * birthday : 1970-01-01
     * ImUsername : 1102871704486543361
     * city : 杭州
     * sex : 女
     * tell : 15224204068
     * idcardAuth : false
     * ImPassword : daa28096f9e8879ab3a02b90aa0e2f83
     * headUrl : http://test.upload.pinpinlx.cn/20190314/1552558542736.
     * description : 设置签名很费劲
     * labels : [{"labelId":11,"labelName":"复古"}]
     * nick : 海草
     * loginTime : 2019-03-14T14:33:13
     * id : 22
     * username : 15224204068
     */

    private String birthday;
    private String ImUsername;
    private String city;
    private String sex;
    private String tell;
    private boolean idcardAuth;
    private String ImPassword;
    private String headUrl;
    private String description;
    private String nick;
    private String vipExpireTime;
    private int authStatus;
    private boolean vip;
    public String getVipExpireTime() {
        return vipExpireTime;
    }

    public void setVipExpireTime(String vipExpireTime) {
        this.vipExpireTime = vipExpireTime;
    }
    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    private String loginTime;
    private int id;
    private String username;
    private List<String>bgImages;

    public List<String> getBgImages() {
        return bgImages;
    }

    public void setBgImages(List<String> bgImages) {
        this.bgImages = bgImages;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<LabelsBean> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelsBean> labels) {
        this.labels = labels;
    }

    public static class LabelsBean {


        /**
         * labelId : 11
         * labelName : 复古
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