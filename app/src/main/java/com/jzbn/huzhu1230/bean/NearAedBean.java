package com.jzbn.huzhu1230.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hecuncun on 2020/6/29
 */

public class NearAedBean implements Parcelable {

    /**
     * code : 10001
     * message : 成功
     * data : [{"magorid":"051f2cc2e8a84c35823bfd9c88b36af5","name":"å\u009c¨ä¹\u008eè\u0087ªå·±å\u009c¨ä¹\u008eä»\u0080ä¹\u0088æ\u0084\u008fæ\u0080\u009d","area":"å\u008c\u0097äº¬å\u008c\u0097äº¬å¸\u0082ä¸\u009cå\u009f\u008eå\u008cº","areaDetail":"å\u009c¨ä¹\u008eè¿\u0087ä¸\u008dä¸\u0080å®\u009aæ\u009c\u0089ä¸\u0080","longitude":"116.369849","latitude":"40.084084","phone":"159359647888","reliability":1,"isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"8f983c70467f4f579f30fdd63795b9cd","createtime":"2020-06-27 03:30:01","updateid":"8f983c70467f4f579f30fdd63795b9cd","updatetime":"2020-06-27 03:30:01","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","distance":"1366.0","myId":"051f2cc2e8a84c35823bfd9c88b36af5"},{"magorid":"1ddb0b6a0e3141c1b5b748deb4ae2658","name":"è¿\u0099é\u0087\u008cæ²¡æ\u009c\u0089å\u0085³ç³»","area":"å\u008c\u0097äº¬å\u008c\u0097äº¬å¸\u0082ä¸\u009cå\u009f\u008eå\u008cº","areaDetail":"ä¸\u008dè\u0083½ä¸\u008dç®¡ä¸\u008då\u0090\u0083ä¸\u008då¥½å\u0090\u0083","longitude":"116.369849","latitude":"40.084084","phone":"æ\u0088\u009112333365555","reliability":1,"isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"8f983c70467f4f579f30fdd63795b9cd","createtime":"2020-06-27 03:31:01","updateid":"8f983c70467f4f579f30fdd63795b9cd","updatetime":"2020-06-27 03:31:01","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","distance":"1366.0","myId":"1ddb0b6a0e3141c1b5b748deb4ae2658"},{"magorid":"c6af162b53794f4390040a5f7d640dd4","name":"12588666anjunwu","area":"å\u008c\u0097äº¬å\u008c\u0097äº¬å¸\u0082ä¸\u009cå\u009f\u008eå\u008cº","areaDetail":"2233666555lnyqaemwtgj","longitude":"116.369849","latitude":"40.084084","phone":"135096589985","reliability":1,"isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"8f983c70467f4f579f30fdd63795b9cd","createtime":"2020-06-27 03:33:16","updateid":"8f983c70467f4f579f30fdd63795b9cd","updatetime":"2020-06-27 03:33:16","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","distance":"1366.0","myId":"c6af162b53794f4390040a5f7d640dd4"},{"magorid":"d5cc56deaaa942e698e3d1f7f816608c","name":"张孝楠","area":"北京北京市海淀区","areaDetail":"颐和园","longitude":"116.278749","latitude":"40.004869","phone":"15210011001","reliability":1,"isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"a35907701b464f11b466f0ba4b661802","createtime":"2020-06-25 00:35:34","updateid":"a35907701b464f11b466f0ba4b661802","updatetime":"2020-06-25 00:35:34","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","distance":"10752.0","myId":"d5cc56deaaa942e698e3d1f7f816608c"},{"magorid":"00996517ae60403081adfdad2b993b49","name":"张孝楠","area":"北京北京市东城区","areaDetail":"天坛公园东门","longitude":"116.426333","latitude":"39.889992","phone":"15210011001","reliability":1,"isdelete":0,"remark1":"","remark2":"","remark3":"","remark4":"","createid":"a35907701b464f11b466f0ba4b661802","createtime":"2020-06-25 00:34:25","updateid":"a35907701b464f11b466f0ba4b661802","updatetime":"2020-06-25 00:34:25","startIndex":0,"pageSize":0,"orderBy":"","fieldName":"","startDate":"","endDate":"","distance":"22293.0","myId":"00996517ae60403081adfdad2b993b49"}]
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

    public static class DataBean implements Parcelable {
        /**
         * magorid : 051f2cc2e8a84c35823bfd9c88b36af5
         * name : å¨ä¹èªå·±å¨ä¹ä»ä¹ææ
         * area : åäº¬åäº¬å¸ä¸ååº
         * areaDetail : å¨ä¹è¿ä¸ä¸å®æä¸
         * longitude : 116.369849
         * latitude : 40.084084
         * phone : 159359647888
         * reliability : 1
         * isdelete : 0
         * remark1 :
         * remark2 :
         * remark3 :
         * remark4 :
         * createid : 8f983c70467f4f579f30fdd63795b9cd
         * createtime : 2020-06-27 03:30:01
         * updateid : 8f983c70467f4f579f30fdd63795b9cd
         * updatetime : 2020-06-27 03:30:01
         * startIndex : 0
         * pageSize : 0
         * orderBy :
         * fieldName :
         * startDate :
         * endDate :
         * distance : 1366.0
         * myId : 051f2cc2e8a84c35823bfd9c88b36af5
         */

        private String magorid;
        private String name;
        private String area;
        private String areaDetail;
        private String longitude;
        private String latitude;
        private String phone;
        private int reliability;
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
        private String distance;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getReliability() {
            return reliability;
        }

        public void setReliability(int reliability) {
            this.reliability = reliability;
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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
            dest.writeString(this.name);
            dest.writeString(this.area);
            dest.writeString(this.areaDetail);
            dest.writeString(this.longitude);
            dest.writeString(this.latitude);
            dest.writeString(this.phone);
            dest.writeInt(this.reliability);
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
            dest.writeString(this.distance);
            dest.writeString(this.myId);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.magorid = in.readString();
            this.name = in.readString();
            this.area = in.readString();
            this.areaDetail = in.readString();
            this.longitude = in.readString();
            this.latitude = in.readString();
            this.phone = in.readString();
            this.reliability = in.readInt();
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
            this.distance = in.readString();
            this.myId = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeTypedList(this.data);
    }

    public NearAedBean() {
    }

    protected NearAedBean(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<NearAedBean> CREATOR = new Parcelable.Creator<NearAedBean>() {
        @Override
        public NearAedBean createFromParcel(Parcel source) {
            return new NearAedBean(source);
        }

        @Override
        public NearAedBean[] newArray(int size) {
            return new NearAedBean[size];
        }
    };
}
