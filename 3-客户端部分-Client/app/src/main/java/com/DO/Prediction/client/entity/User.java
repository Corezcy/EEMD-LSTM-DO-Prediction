package com.DO.Prediction.client.entity;

import android.graphics.Bitmap;

/**
 * Created by Zheng Chao You on 2020/5/5 0005.
 */

public class User {
    private String id;
    private String name;
    private String phone;
    private String sex;
    private String idnum;
    private String type;
    private Bitmap headimg;

    public User() {
        this.id="";
        this.name = "";
        this.phone = "";
        this.sex = "";
        this.idnum = "";
        this.type = "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getHeadimg() {
        return headimg;
    }

    public void setHeadimg(Bitmap headimg) {
        this.headimg = headimg;
    }
}
