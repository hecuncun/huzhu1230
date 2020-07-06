package com.jzbn.huzhu1230.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by hecuncun on 2020/7/6
 */

public class AliVideoBean implements Parcelable {


    /**
     * GSLB : ["https://rgslb.rtc.aliyuncs.com"]
     * appId : 50ucoiom
     * appKey : 0d79eb619746d6f8625143cdf58b124f
     * userId : 56537287c027499eb05a406aa99d35c2
     * nonce : AK-9773cac87da048b1852d42d18ac87726
     * channelId : adsfasffsafasda11
     * timestamp : 1594092485
     * token : 9888cb90219717d2b444926772a8eed6f14edd52021f057ff7d5b2027c9191f0
     */

    private String appId;
    private String appKey;
    private String userId;
    private String nonce;
    private String channelId;
    private int timestamp;
    private String token;
    private List<String> GSLB;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getGSLB() {
        return GSLB;
    }

    public void setGSLB(List<String> GSLB) {
        this.GSLB = GSLB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appId);
        dest.writeString(this.appKey);
        dest.writeString(this.userId);
        dest.writeString(this.nonce);
        dest.writeString(this.channelId);
        dest.writeInt(this.timestamp);
        dest.writeString(this.token);
        dest.writeStringList(this.GSLB);
    }

    public AliVideoBean() {
    }

    protected AliVideoBean(Parcel in) {
        this.appId = in.readString();
        this.appKey = in.readString();
        this.userId = in.readString();
        this.nonce = in.readString();
        this.channelId = in.readString();
        this.timestamp = in.readInt();
        this.token = in.readString();
        this.GSLB = in.createStringArrayList();
    }

    public static final Parcelable.Creator<AliVideoBean> CREATOR = new Parcelable.Creator<AliVideoBean>() {
        @Override
        public AliVideoBean createFromParcel(Parcel source) {
            return new AliVideoBean(source);
        }

        @Override
        public AliVideoBean[] newArray(int size) {
            return new AliVideoBean[size];
        }
    };
}
