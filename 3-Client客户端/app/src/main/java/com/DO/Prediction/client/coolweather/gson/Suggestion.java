package com.DO.Prediction.client.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 浩比 on 2017/11/11.
 */

public class Suggestion {



    @SerializedName("comf")
    public Comfort comfort;//舒适度指数

    public class Comfort{

        @SerializedName("txt")
        public String info;

    }

    @SerializedName("cw")
    public CarWash carWash;//洗车指数

    public class CarWash{

        @SerializedName("txt")
        public String info;
    }


    public Sport sport;//运动指数

    public class Sport{

        @SerializedName("txt")
        public String info;
    }

}
