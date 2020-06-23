package com.DO.Prediction.client.coolweather.gson;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 浩比 on 2017/11/11.
 */

public class Weather {
    /**
     * Weather类作为总的实例类来引用以上各个实体类
     */

    public String status;

    public Basic basic;

    public AQI aqi;

    @SerializedName("now")
    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
