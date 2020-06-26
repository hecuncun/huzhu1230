package com.jzbn.huzhu1230.bean;

import java.util.List;

/**
 * Created by hecuncun on 2020/6/25
 */

public class CommonRescueBean {

    /**
     * code : 10001
     * message : 成功
     * data : [{"magorid":"4","title":"高温","photo":"/uploadtemp/image/2020/6/19ced90bacbb4614a54a1c0a6ef455de.png","common":1,"pid":"0","isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"","createtime":"2020-06-24 00:00:11","updateid":"","updatetime":"2020-06-24 00:00:13","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","myId":"4"},{"magorid":"3","title":"骨折","photo":"/uploadtemp/image/2020/6/26646e11914249f39eb2c06ff8e0f22b.png","common":1,"pid":"0","isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"","createtime":"2020-06-23 23:58:20","updateid":"","updatetime":"2020-06-23 23:58:22","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","myId":"3"},{"magorid":"2","title":"哮喘","photo":"/uploadtemp/image/2020/6/5fe16edb509d44ab8cb90d7fd2ae6f10.png","common":1,"pid":"0","isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"","createtime":"2020-06-23 23:58:11","updateid":"","updatetime":"2020-06-23 23:58:13","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","myId":"2"},{"magorid":"dfadf1","title":"出血","photo":"/uploadtemp/image/2020/6/b5dd459183d74562b9745085c1b0b357.png","common":1,"pid":"0","isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"","createtime":"2020-06-23 23:57:09","updateid":null,"updatetime":"2020-06-23 23:57:11","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","myId":"dfadf1"}]
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * magorid : 4
         * title : 高温
         * photo : /uploadtemp/image/2020/6/19ced90bacbb4614a54a1c0a6ef455de.png
         * common : 1
         * pid : 0
         * isdelete : 0
         * remark1 :
         * remark2 :
         * remark3 :
         * remark4 :
         * createid :
         * createtime : 2020-06-24 00:00:11
         * updateid :
         * updatetime : 2020-06-24 00:00:13
         * startIndex : 0
         * pageSize : 0
         * orderBy :
         * fieldName :
         * startDate :
         * endDate :
         * myId : 4
         */

        private String magorid;
        private String title;
        private String photo;
        private int common;
        private String pid;
        private int isdelete;
        private String remark1;
        private String remark2;
        private String remark3;
        private String remark4;
        private String createid;
        private String createtime;
        private String updateid;
        private String updatetime;
        private int startIndex;
        private int pageSize;
        private String orderBy;
        private String fieldName;
        private String startDate;
        private String endDate;
        private String myId;

        public String getMagorid() {
            return magorid;
        }

        public void setMagorid(String magorid) {
            this.magorid = magorid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getCommon() {
            return common;
        }

        public void setCommon(int common) {
            this.common = common;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public int getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(int isdelete) {
            this.isdelete = isdelete;
        }

        public String getRemark1() {
            return remark1;
        }

        public void setRemark1(String remark1) {
            this.remark1 = remark1;
        }

        public String getRemark2() {
            return remark2;
        }

        public void setRemark2(String remark2) {
            this.remark2 = remark2;
        }

        public String getRemark3() {
            return remark3;
        }

        public void setRemark3(String remark3) {
            this.remark3 = remark3;
        }

        public String getRemark4() {
            return remark4;
        }

        public void setRemark4(String remark4) {
            this.remark4 = remark4;
        }

        public String getCreateid() {
            return createid;
        }

        public void setCreateid(String createid) {
            this.createid = createid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdateid() {
            return updateid;
        }

        public void setUpdateid(String updateid) {
            this.updateid = updateid;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getMyId() {
            return myId;
        }

        public void setMyId(String myId) {
            this.myId = myId;
        }
    }
}
