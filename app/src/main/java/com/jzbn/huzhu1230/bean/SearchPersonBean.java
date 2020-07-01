package com.jzbn.huzhu1230.bean;

import java.util.List;

/**
 * Created by heCunCun on 2020/7/1
 */
public class SearchPersonBean {

    /**
     * currentPageSize : 10
     * page : 1
     * result :
     * message :
     * total : 1
     * records : 3
     * rows : [{"magorid":"8b57dc630a1d498d8fac8967cacf6e04","name":"王二","card":"220221188809089876","photo":"/uploadtemp/image/2020/6/86e6043631384935829564d22b6bb3af.jpg","ddate":"2020-06-27 00:10:54","area":"北京北京市","areaDetail":"丰台区新发地菜篮子","way":"1","reason":"不清楚","longitude":"116.353686","latitude":"39.814988","features":"身高1.8米,微胖","region":"北京","relation":"朋友","contact":"张小15210001000","qrcode":"/uploadtemp/image/2020/6/0c1be910a5d9484788ba6b8bf9c7ec07.jpg","content":"","numView":0,"type":2,"reliability":1,"status":1,"longitudeNew":"116.305434","latitudeNew":"39.96549","spreadDistance":188000,"findStatus":1,"isdelete":0,"remark1":"2020-06-27 22:10:54","remark2":"","remark3":"","remark4":"","createid":"a35907701b464f11b466f0ba4b661802","createtime":"2020-06-27 00:36:08","updateid":"a35907701b464f11b466f0ba4b661802","updatetime":"2020-06-28 00:55:32","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","createUserName":"张二","createUserPhoto":"/uploadtemp/image/2020/6/a51c86728fe74eb197f3029d79e7266d.jpg","duration":"4天8时","findClueList":"","myId":"8b57dc630a1d498d8fac8967cacf6e04"},{"magorid":"28b4a9a164e24e10a9a8f943e211a818","name":"王4","card":"220221188809089876","photo":"/uploadtemp/image/2020/6/2c6dbb78a88f42389c249a74db0337a6.jpg,/uploadtemp/image/2020/6/86e6043631384935829564d22b6bb3af.jpg","ddate":"2020-06-26 12:10:54","area":"北京北京市","areaDetail":"丰台区新发地菜篮子","way":"2","reason":"不清楚","longitude":"116.353686","latitude":"39.814988","features":"身高1.8米,微胖","region":"北京","relation":"朋友","contact":"张小15210001000","qrcode":"/uploadtemp/image/2020/6/88442fc2407d49f0b28912a585bd136e.jpg","content":null,"numView":0,"type":2,"reliability":1,"status":1,"longitudeNew":"116.467035","latitudeNew":"39.916456","spreadDistance":1800000,"findStatus":1,"isdelete":0,"remark1":"2020-06-26 18:10:54","remark2":null,"remark3":null,"remark4":null,"createid":"a35907701b464f11b466f0ba4b661802","createtime":"2020-06-27 00:56:03","updateid":"a35907701b464f11b466f0ba4b661802","updatetime":"2020-06-27 00:56:03","startIndex":0,"pageSize":0,"orderBy":null,"fieldName":null,"startDate":null,"endDate":null,"createUserName":"张二","createUserPhoto":"/uploadtemp/image/2020/6/a51c86728fe74eb197f3029d79e7266d.jpg","duration":"4天20时","findClueList":null,"myId":"28b4a9a164e24e10a9a8f943e211a818"},{"magorid":"639bc01ef1ee4e79b75440e49e56cbe0","name":"王3","card":"220221188809089876","photo":"/uploadtemp/image/2020/6/86e6043631384935829564d22b6bb3af.jpg","ddate":"2020-06-25 00:10:54","area":"北京北京市","areaDetail":"丰台区新发地菜篮子","way":"1","reason":"不清楚","longitude":"116.353686","latitude":"39.814988","features":"身高1.8米,微胖","region":"北京","relation":"朋友","contact":"张小15210001000","qrcode":"/uploadtemp/image/2020/6/88442fc2407d49f0b28912a585bd136e.jpg","content":null,"numView":0,"type":2,"reliability":1,"status":1,"longitudeNew":"115.999242","latitudeNew":"39.699947","spreadDistance":null,"findStatus":1,"isdelete":0,"remark1":"2020-06-25 00:10:54","remark2":null,"remark3":null,"remark4":null,"createid":"a35907701b464f11b466f0ba4b661802","createtime":"2020-06-27 00:36:51","updateid":"a35907701b464f11b466f0ba4b661802","updatetime":"2020-06-27 00:36:51","startIndex":0,"pageSize":0,"orderBy":null,"fieldName":null,"startDate":null,"endDate":null,"createUserName":"张二","createUserPhoto":"/uploadtemp/image/2020/6/a51c86728fe74eb197f3029d79e7266d.jpg","duration":"6天8时","findClueList":null,"myId":"639bc01ef1ee4e79b75440e49e56cbe0"}]
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
         * magorid : 8b57dc630a1d498d8fac8967cacf6e04
         * name : 王二
         * card : 220221188809089876
         * photo : /uploadtemp/image/2020/6/86e6043631384935829564d22b6bb3af.jpg
         * ddate : 2020-06-27 00:10:54
         * area : 北京北京市
         * areaDetail : 丰台区新发地菜篮子
         * way : 1
         * reason : 不清楚
         * longitude : 116.353686
         * latitude : 39.814988
         * features : 身高1.8米,微胖
         * region : 北京
         * relation : 朋友
         * contact : 张小15210001000
         * qrcode : /uploadtemp/image/2020/6/0c1be910a5d9484788ba6b8bf9c7ec07.jpg
         * content :
         * numView : 0
         * type : 2
         * reliability : 1
         * status : 1
         * longitudeNew : 116.305434
         * latitudeNew : 39.96549
         * spreadDistance : 188000
         * findStatus : 1
         * isdelete : 0
         * remark1 : 2020-06-27 22:10:54
         * remark2 :
         * remark3 :
         * remark4 :
         * createid : a35907701b464f11b466f0ba4b661802
         * createtime : 2020-06-27 00:36:08
         * updateid : a35907701b464f11b466f0ba4b661802
         * updatetime : 2020-06-28 00:55:32
         * startIndex : 0
         * pageSize : 0
         * orderBy :
         * fieldName :
         * startDate :
         * endDate :
         * createUserName : 张二
         * createUserPhoto : /uploadtemp/image/2020/6/a51c86728fe74eb197f3029d79e7266d.jpg
         * duration : 4天8时
         * findClueList :
         * myId : 8b57dc630a1d498d8fac8967cacf6e04
         */

        private String magorid;
        private String name;
        private String card;
        private String photo;
        private String ddate;
        private String area;
        private String areaDetail;
        private String way;
        private String reason;
        private String longitude;
        private String latitude;
        private String features;
        private String region;
        private String relation;
        private String contact;
        private String qrcode;
        private String content;
        private int numView;
        private int type;
        private int reliability;
        private int status;
        private String longitudeNew;
        private String latitudeNew;
        private int spreadDistance;
        private int findStatus;
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
        private String createUserName;
        private String createUserPhoto;
        private String duration;
        private String findClueList;
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

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getDdate() {
            return ddate;
        }

        public void setDdate(String ddate) {
            this.ddate = ddate;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAreaDetail() {
            return areaDetail;
        }

        public void setAreaDetail(String areaDetail) {
            this.areaDetail = areaDetail;
        }

        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getFeatures() {
            return features;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getNumView() {
            return numView;
        }

        public void setNumView(int numView) {
            this.numView = numView;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getReliability() {
            return reliability;
        }

        public void setReliability(int reliability) {
            this.reliability = reliability;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getLongitudeNew() {
            return longitudeNew;
        }

        public void setLongitudeNew(String longitudeNew) {
            this.longitudeNew = longitudeNew;
        }

        public String getLatitudeNew() {
            return latitudeNew;
        }

        public void setLatitudeNew(String latitudeNew) {
            this.latitudeNew = latitudeNew;
        }

        public int getSpreadDistance() {
            return spreadDistance;
        }

        public void setSpreadDistance(int spreadDistance) {
            this.spreadDistance = spreadDistance;
        }

        public int getFindStatus() {
            return findStatus;
        }

        public void setFindStatus(int findStatus) {
            this.findStatus = findStatus;
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

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getCreateUserPhoto() {
            return createUserPhoto;
        }

        public void setCreateUserPhoto(String createUserPhoto) {
            this.createUserPhoto = createUserPhoto;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getFindClueList() {
            return findClueList;
        }

        public void setFindClueList(String findClueList) {
            this.findClueList = findClueList;
        }

        public String getMyId() {
            return myId;
        }

        public void setMyId(String myId) {
            this.myId = myId;
        }
    }
}
