package com.DO.Prediction.client.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 浩比 on 2017/11/10.
 */

public class County extends DataSupport{

    private int id;//实体类的id

    private String countyName;//县的名字

    private String weatherId;//县所对应天气的id值

    private int cityId;//当前县所属市的id值

    //getter和setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
