package com.DO.Prediction.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class HefengActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HeConfig.init("HE2003281021561278", "b2e02510f6ad489fbf1bc31bb348f0d4");
        HeConfig.switchToFreeServerNode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hefeng);

       HeWeather.getSearch(this, new HeWeather.OnResultSearchBeansListener() {

           @Override
           public void onError(Throwable throwable) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(HefengActivity.this,"查找城市失败",Toast.LENGTH_SHORT).show();
                   }
               });
           }

           @Override
           public void onSuccess(interfaces.heweather.com.interfacesmodule.bean.search.Search search) {
               if (search.getStatus().equals("ok")) {
                   String str = "";
                   str = search.getBasic().get(0).getLocation() + search.getBasic().get(0).getCid();

                   final String finalStr = str;
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {

                           Toast.makeText(HefengActivity.this, finalStr, Toast.LENGTH_SHORT).show();
                       }
                   });
               }
           }
       });
    }
}
