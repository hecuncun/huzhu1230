package com.jzbn.huzhu1230.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hecuncun on 2020-5-30
 */
public class KnowledgeBean {

    /**
     * currentPageSize : 10
     * page : 1
     * result :
     * message :
     * total : 1
     * records : 1
     * rows : [{"magorid":"2","title":"意外事故伤员的创口止血包扎固定和搬运方法","photo":"/uploadtemp/image/2020/6/0c8ae6bec67d40798d10c40d15fa2ec4.jpg","path":"","content":"<p>文章内容简介<p>","numViews":0,"numPlay":0,"type":1,"title4":"","title3":"","title2":"","isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"1","createtime":"2020-06-05 00:12:51","updateid":"1","updatetime":"2020-06-05 00:12:42","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","myId":"2"}]
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

    public static class RowsBean implements Parcelable {
        /**
         * magorid : 2
         * title : 意外事故伤员的创口止血包扎固定和搬运方法
         * photo : /uploadtemp/image/2020/6/0c8ae6bec67d40798d10c40d15fa2ec4.jpg
         * path :
         * content : <p>文章内容简介<p>
         * numViews : 0
         * numPlay : 0
         * type : 1
         * title4 :
         * title3 :
         * title2 :
         * isdelete : 0
         * remark1 :
         * remark2 :
         * remark3 :
         * remark4 :
         * createid : 1
         * createtime : 2020-06-05 00:12:51
         * updateid : 1
         * updatetime : 2020-06-05 00:12:42
         * startIndex : 0
         * pageSize : 0
         * orderBy :
         * fieldName :
         * startDate :
         * endDate :
         * myId : 2
         */

        private String magorid;
        private String title;
        private String photo;
        private String path;
        private String content;
        private int numViews;
        private int numPlay;
        private int type;
        private String title4;
        private String title3;
        private String title2;
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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getNumViews() {
            return numViews;
        }

        public void setNumViews(int numViews) {
            this.numViews = numViews;
        }

        public int getNumPlay() {
            return numPlay;
        }

        public void setNumPlay(int numPlay) {
            this.numPlay = numPlay;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle4() {
            return title4;
        }

        public void setTitle4(String title4) {
            this.title4 = title4;
        }

        public String getTitle3() {
            return title3;
        }

        public void setTitle3(String title3) {
            this.title3 = title3;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.magorid);
            dest.writeString(this.title);
            dest.writeString(this.photo);
            dest.writeString(this.path);
            dest.writeString(this.content);
            dest.writeInt(this.numViews);
            dest.writeInt(this.numPlay);
            dest.writeInt(this.type);
            dest.writeString(this.title4);
            dest.writeString(this.title3);
            dest.writeString(this.title2);
            dest.writeInt(this.isdelete);
            dest.writeString(this.remark1);
            dest.writeString(this.remark2);
            dest.writeString(this.remark3);
            dest.writeString(this.remark4);
            dest.writeString(this.createid);
            dest.writeString(this.createtime);
            dest.writeString(this.updateid);
            dest.writeString(this.updatetime);
            dest.writeInt(this.startIndex);
            dest.writeInt(this.pageSize);
            dest.writeString(this.orderBy);
            dest.writeString(this.fieldName);
            dest.writeString(this.startDate);
            dest.writeString(this.endDate);
            dest.writeString(this.myId);
        }

        public RowsBean() {
        }

        protected RowsBean(Parcel in) {
            this.magorid = in.readString();
            this.title = in.readString();
            this.photo = in.readString();
            this.path = in.readString();
            this.content = in.readString();
            this.numViews = in.readInt();
            this.numPlay = in.readInt();
            this.type = in.readInt();
            this.title4 = in.readString();
            this.title3 = in.readString();
            this.title2 = in.readString();
            this.isdelete = in.readInt();
            this.remark1 = in.readString();
            this.remark2 = in.readString();
            this.remark3 = in.readString();
            this.remark4 = in.readString();
            this.createid = in.readString();
            this.createtime = in.readString();
            this.updateid = in.readString();
            this.updatetime = in.readString();
            this.startIndex = in.readInt();
            this.pageSize = in.readInt();
            this.orderBy = in.readString();
            this.fieldName = in.readString();
            this.startDate = in.readString();
            this.endDate = in.readString();
            this.myId = in.readString();
        }

        public static final Parcelable.Creator<RowsBean> CREATOR = new Parcelable.Creator<RowsBean>() {
            @Override
            public RowsBean createFromParcel(Parcel source) {
                return new RowsBean(source);
            }

            @Override
            public RowsBean[] newArray(int size) {
                return new RowsBean[size];
            }
        };
    }
}
