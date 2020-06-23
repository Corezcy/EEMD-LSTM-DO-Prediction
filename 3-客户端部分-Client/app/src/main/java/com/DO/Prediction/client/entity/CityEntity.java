package com.DO.Prediction.client.entity;

/**
 * Created by Zheng Chao You on 2020/1/10 0010.
 */

public class CityEntity {
    private String name;
    private String key;
    private String pinyin; //全拼
    private String first; //首字母
    private String cityCode;

    public CityEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
