package com.spellingtrip.example.bean;

import java.util.List;

/**
 * date:2020/5/8
 * author:王思敏
 * function活动流程输入内容
 */
public class Proces {

    private List<ProcesBean> mBeanList;

    public List<ProcesBean> getBeanList() {
        return mBeanList;
    }

    public void setBeanList(List<ProcesBean> beanList) {
        mBeanList = beanList;
    }

    public static class ProcesBean {
        private int day;
        private String site;
        private String time;
        private String describe;
        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }
}
