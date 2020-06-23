package com.DO.Prediction.client.entity;

/**
 * Created by Zheng Chao You on 2020/5/6 0006.
 */

public class Future {
    public String temperature;
    public String weather;
    public WeatherId weatherId;
    public String wind;
    public String week;
    public String date;

    public String toString(){
        return temperature+weather+weatherId.fa+weatherId.fb+wind+week+date;
    }
}
