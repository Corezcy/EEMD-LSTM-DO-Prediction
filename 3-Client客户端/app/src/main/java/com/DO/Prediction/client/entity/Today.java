package com.DO.Prediction.client.entity;

/**
 * Created by Zheng Chao You on 2020/5/6 0006.
 */

public class Today {
    public String city;
    public String date_y;
    public String week;
    public String temperature;
    public String weather;
    public WeatherId weather_id;
    public String wind;
    public String dressing_index;
    public String dressing_advice;
    public String uv_index;
    public String comfort_index;
    public String wash_index;
    public String travel_index;
    public String exercise_index;
    public String drying_index;

    public String toString() {
        return city+date_y+week+temperature+weather+weather_id.fa+weather_id.fb+wind+dressing_index+dressing_advice+uv_index+comfort_index+
                wash_index+travel_index+exercise_index+drying_index;
    }
}
