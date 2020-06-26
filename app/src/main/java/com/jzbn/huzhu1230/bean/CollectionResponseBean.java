package com.jzbn.huzhu1230.bean;

import java.util.List;

/**
 * Created by hecuncun on 2020/6/26
 */

public class CollectionResponseBean {

    /**
     * currentPageSize : 10
     * page : 1
     * result :
     * message :
     * total : 1
     * records : 1
     * rows : [{"magorid":"bd1c53af04794f7e84ae221bac05a9ee","uid":"56537287c027499eb05a406aa99d35c2","objectId":"8","type":3,"picture":"/uploadtemp/image/2020/6/19ced90bacbb4614a54a1c0a6ef455de.png","remark2":"开放性伤口止血","remark3":"","remark4":"","createid":"56537287c027499eb05a406aa99d35c2","createtime":"2020-06-26 22:00:59","updateid":"56537287c027499eb05a406aa99d35c2","updatetime":"2020-06-26 22:00:59","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","myId":"bd1c53af04794f7e84ae221bac05a9ee"}]
     */

    private int currentPageSize;
    private int page;
    private String result;
    private String message;
    private int total;
    private int records;
    private List<RowsBean> rows;

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * magorid : bd1c53af04794f7e84ae221bac05a9ee
         * uid : 56537287c027499eb05a406aa99d35c2
         * objectId : 8
         * type : 3
         * picture : /uploadtemp/image/2020/6/19ced90bacbb4614a54a1c0a6ef455de.png
         * remark2 : 开放性伤口止血
         * remark3 :
         * remark4 :
         * createid : 56537287c027499eb05a406aa99d35c2
         * createtime : 2020-06-26 22:00:59
         * updateid : 56537287c027499eb05a406aa99d35c2
         * updatetime : 2020-06-26 22:00:59
         * startIndex : 0
         * pageSize : 0
         * orderBy :
         * fieldName :
         * startDate :
         * endDate :
         * myId : bd1c53af04794f7e84ae221bac05a9ee
         */
        private boolean isChecked =false;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        private String magorid;
        private String uid;
        private String objectId;
        private int type;
        private String picture;
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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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
