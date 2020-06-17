package com.spellingtrip.example.bean;

public class ChatNumberBean {

    /**
     * code : 0
     * data : {"chat":true,"msg":"今日免费聊天次数已达上限,赶快开通会员享受无限制聊天呗"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * chat : true
         * msg : 今日免费聊天次数已达上限,赶快开通会员享受无限制聊天呗
         */

        private boolean chat;
        private String msg;

        public boolean isChat() {
            return chat;
        }

        public void setChat(boolean chat) {
            this.chat = chat;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
