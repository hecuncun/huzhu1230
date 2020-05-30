package com.jzbn.huzhu1230.bean;

/**
 * Created by hecuncun on 2020-5-24
 */
public class EmergencyFindBean {
    private String duration;
    private String address;
    private String focusNum;
    private String picUrl;
    private String name;
    private String time;

    public EmergencyFindBean() {
    }

    public EmergencyFindBean(String name, String time, String duration, String address, String focusNum, String picUrl) {
        this.name = name;
        this.time = time;
        this.duration = duration;
        this.address = address;
        this.focusNum = focusNum;
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFocusNum() {
        return focusNum;
    }

    public void setFocusNum(String focusNum) {
        this.focusNum = focusNum;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }



}
