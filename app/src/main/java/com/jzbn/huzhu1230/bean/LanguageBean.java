package com.jzbn.huzhu1230.bean;

import java.util.List;

/**
 * Created by heCunCun on 2020/6/30
 */
public class LanguageBean {

    /**
     * code : 10001
     * message : 成功
     * data : [{"magorid":"1","name":"普通话","isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"","createtime":"2020-06-22 00:10:04","updateid":"","updatetime":"2020-06-22 00:10:24","startIndex":0,"pageSize":0,"orderBy":null,"fieldName":null,"startDate":null,"endDate":null,"myId":"1"},{"magorid":"da62c5614cfc4a199e51a9f7f87c79a1","name":"蒙古语","isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"","createtime":"2020-06-22 00:10:14","updateid":"","updatetime":"2020-06-22 00:10:14","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","myId":"da62c5614cfc4a199e51a9f7f87c79a1"}]
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
         * magorid : 1
         * name : 普通话
         * isdelete : 0
         * remark1 :
         * remark2 :
         * remark3 :
         * remark4 :
         * createid :
         * createtime : 2020-06-22 00:10:04
         * updateid :
         * updatetime : 2020-06-22 00:10:24
         * startIndex : 0
         * pageSize : 0
         * orderBy : null
         * fieldName : null
         * startDate : null
         * endDate : null
         * myId : 1
         */

        private String magorid;
        private String name;
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
        private Object orderBy;
        private Object fieldName;
        private Object startDate;
        private Object endDate;
        private String myId;

        public String getMagorid() {
            return magorid;
        }

        public void setMagorid(String magorid) {
            this.magorid = magorid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public Object getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(Object orderBy) {
            this.orderBy = orderBy;
        }

        public Object getFieldName() {
            return fieldName;
        }

        public void setFieldName(Object fieldName) {
            this.fieldName = fieldName;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
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
