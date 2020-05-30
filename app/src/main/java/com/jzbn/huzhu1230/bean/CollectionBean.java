package com.jzbn.huzhu1230.bean;

/**
 * Created by hecuncun on 2020-5-25
 */
public class CollectionBean {
    private boolean isChecked =false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public CollectionBean() {
    }

    public CollectionBean(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
