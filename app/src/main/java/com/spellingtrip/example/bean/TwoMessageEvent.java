package com.spellingtrip.example.bean;

/**
 * date:2020/5/11
 * author:王思敏
 * function
 */
public class TwoMessageEvent {
    public  String message;
    public  String content;

    public TwoMessageEvent(String message, String content) {
        this.message = message;
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
