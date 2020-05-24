package com.jzbn.huzhu1230.bean;

/**
 * Created by hecuncun on 2020-5-24
 */
public class AedBean {
    private String address;
    private String time;

    public AedBean(String address, String time) {
        this.address = address;
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AedBean() {
    }
}
