package com.spellingtrip.example.retrofit.bean;

public class SendMessageData {
    private BaseBean baseBean;
    private String type;
    public SendMessageData(){

    }
    public SendMessageData(String type) {
        this.type = type;
    }

    public BaseBean getBaseBean() {
        return baseBean;
    }

    public void setBaseBean(BaseBean baseBean) {
        this.baseBean = baseBean;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
