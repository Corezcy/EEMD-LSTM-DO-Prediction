package com.DO.Prediction.client.app;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import com.DO.Prediction.client.entity.User;
import com.DO.Prediction.client.utils.ScreenUtil;

import java.util.Scanner;

import heweather.com.weathernetsdk.view.HeWeatherConfig;


public class MyApplication extends Application
{
    private static Context appContext;
    private static String localIP = "192.168.0.101";
    private static User user = new User();
    private static String city;

    public static double[] maxtemp;
    public static double[] mintemp;

    public static User getUser()
    {
        return user;
    }

    public static void setUser(User user)
    {
        MyApplication.user = user;
    }

    public static String getLocalIP()
    {
        return localIP;
    }

    public static void setLocalIP(String localIP)
    {
        MyApplication.localIP = localIP;
    }

    public static String getCity()
    {
        return city;
    }

    public static void setCity(String city)
    {
        MyApplication.city = city;
    }

    @Override
    public void onCreate()
    {
        appContext = this.getApplicationContext();
        super.onCreate();
        ScreenUtil.init(this);
    }

    public static Context getContext()
    {
        return appContext;
    }

}
