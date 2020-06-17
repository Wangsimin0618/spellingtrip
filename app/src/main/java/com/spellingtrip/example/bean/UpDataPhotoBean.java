package com.spellingtrip.example.bean;

public class UpDataPhotoBean {

    /**
     * code : 0
     * count : 0
     * data : {"fid":"1111568612524556288","fileName":"b78adaf05ed2cd2b809a1f8c3e893cf0.mp4","fileSize":688749,"success":true,"fileType":"video/mp4","url":"https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190329/1111568612524556288.mp4"}
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
         * fid : 1111568612524556288
         * fileName : b78adaf05ed2cd2b809a1f8c3e893cf0.mp4
         * fileSize : 688749
         * success : true
         * fileType : video/mp4
         * url : https://pinpinlx.oss-cn-beijing.aliyuncs.com/20190329/1111568612524556288.mp4
         */

        private String fid;
        private String fileName;
        private int fileSize;
        private boolean success;
        private String fileType;
        private String url;

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getFileSize() {
            return fileSize;
        }

        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
