package com.spellingtrip.example.bean;

public class PinCityCallBack {

    /**
     * code : 0
     * count : 0
     * data : {"groupName":"聊城市在当代国际广场附近～约拍","groupId":84774773391361}
     * msg : 发布成功
     * objId : 84774773391361
     */

    private int code;
    private int count;
    private DataBean data;
    private String msg;
    private long objId;

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

    public long getObjId() {
        return objId;
    }

    public void setObjId(long objId) {
        this.objId = objId;
    }

    public static class DataBean {
        /**
         * groupName : 聊城市在当代国际广场附近～约拍
         * groupId : 84774773391361
         */

        private String groupName;
        private long groupId;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public long getGroupId() {
            return groupId;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }
    }
}
