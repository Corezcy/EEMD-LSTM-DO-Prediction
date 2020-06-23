package com.DO.Prediction.client.entity;

/**
 * Created by Zheng Chao You on 2020/5/6 0006.
 */

public class SK {
    public String temp;
    public String wing_direction;
    public String wind_strength;
    public String humidity;
    public String time;
    public String toString() {
        return temp+wing_direction+wind_strength+humidity+time;
    }
}
