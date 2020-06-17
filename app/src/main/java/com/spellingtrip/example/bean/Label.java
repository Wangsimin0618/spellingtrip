package com.spellingtrip.example.bean;

import java.util.List;

/**
 * date:2020/5/16
 * author:王思敏
 * function
 */
public class Label {

    /**
     * code : 0
     * count : 0
     * data : [{"typeName":"唱歌","typeId":1},{"typeName":"登山","typeId":2},{"typeName":"吃饭","typeId":3},{"typeName":"逛街","typeId":4},{"typeName":"咖啡","typeId":5},{"typeName":"约酒","typeId":6},{"typeName":"约拍","typeId":7},{"typeName":"打球","typeId":8},{"typeName":"看展","typeId":10},{"typeName":"游泳","typeId":11},{"typeName":"跑步","typeId":12},{"typeName":"骑单车","typeId":13},{"typeName":"自驾游","typeId":14}]
     * msg : success
     * objId : 0
     */

    private int code;
    private int count;
    private String msg;
    private int objId;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * typeName : 唱歌
         * typeId : 1
         */

        private String typeName;
        private int typeId;

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }
    }
}
