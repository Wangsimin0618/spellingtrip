package com.spellingtrip.example.bean;


import org.litepal.crud.LitePalSupport;

public  class DataBean extends LitePalSupport {
    /**
     * ImUsername : 1102871704486543361
     * tell :
     * ImPassword : daa28096f9e8879ab3a02b90aa0e2f83
     * userId : 22
     */

    private String ImUsername;
    private String tell;
    private String ImPassword;
    private int userId;

    public String getImUsername() {
        return ImUsername;
    }

    public void setImUsername(String ImUsername) {
        this.ImUsername = ImUsername;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getImPassword() {
        return ImPassword;
    }

    public void setImPassword(String ImPassword) {
        this.ImPassword = ImPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}