package com.spellingtrip.example.bean;

import java.util.List;

public class ChatUserBean {
    private List<ChatUserInfoBean> users;

    public List<ChatUserInfoBean> getUsers() {
        return users;
    }

    public void setUsers(List<ChatUserInfoBean> users) {
        this.users = users;
    }

    public static class ChatUserInfoBean {
        private String name;
        private String url;
        private String hxname;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHxname() {
            return hxname;
        }

        public void setHxname(String hxname) {
            this.hxname = hxname;
        }
    }
}
