package com.DO.Prediction.client;

import android.content.Intent;
import android.net.sip.SipSession;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DO.Prediction.client.coolweather.WeaMainActivity;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.DO.Prediction.client.app.MyApplication;
import com.DO.Prediction.client.coolweather.WeatherActivity;
import com.DO.Prediction.client.coolweather.util.HttpUtil;
import com.DO.Prediction.client.entity.Future;
import com.DO.Prediction.client.entity.JuheWeather;
import com.DO.Prediction.client.entity.Result;
import com.DO.Prediction.client.entity.SK;
import com.DO.Prediction.client.entity.Today;
import com.DO.Prediction.client.entity.User;
import com.DO.Prediction.client.entity.WeatherId;
import com.DO.Prediction.client.utils.ToastUtil;
import com.mob.MobSDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MenuActivity extends AppCompatActivity
{

    private static final String TAG = "MENU";

    private DrawerLayout drawerLayout;

    private Button usrInfo;

    private LinearLayout weatherLayout;
    private Button ZaiHai;
    private Button ShuiChanZhiDao;
    private Button History;
    private Button Predict;
    private Button Outlier;
    private Button YuyeInfo;
    private Button FeedBack;

    public static LocationClient mLocationClient;
    public static LocationClientOption option;
    public static String city;

    private String juheKey = "47922730be17470ac348cc0ba7bb83bf";
    private JuheWeather juheWeather;

    private TextView tempTv;
    private TextView weatherTv;
    private TextView lowhighTv;
    private TextView cityTv;
    private TextView monthdayTv;
    private TextView weekdayTv;
    private ImageView weatherImg;
    private TextView fengTv;

    private Button returnLog;
    private TextView returnLog1;
    private TextView user_name;
    private TextView view_all_bills;
    private TextView change_password;

    private int wid;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        drawerLayout = findViewById(R.id.drawer_layout);

        tempTv = (TextView) findViewById(R.id.temp);
        weatherTv = (TextView) findViewById(R.id.weather); //根据weather晴或多云设置图标
        lowhighTv = (TextView) findViewById(R.id.lowhigh);
        monthdayTv = (TextView) findViewById(R.id.monthday);
        weekdayTv = (TextView) findViewById(R.id.weekday);
        fengTv = (TextView) findViewById(R.id.feng);
        weatherImg = (ImageView) findViewById(R.id.weatherimg); //天气太阳图标等

        option = new LocationClientOption();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener((BDAbstractLocationListener) new MenuActivity.MyLocationListener());

        SDKInitializer.initialize(getApplicationContext());
        setLocation();

        user_name = findViewById(R.id.user_name);
        user_name.setText(MyApplication.getUser().getId());

        usrInfo = (Button) findViewById(R.id.usrInfo);
        usrInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                drawerLayout.openDrawer(GravityCompat.START, true); // 默认true, 执行动画

                returnLog = findViewById(R.id.returnLog);
                returnLog.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view)
                    {
                        startActivity(new Intent(MenuActivity.this, MainActivity.class));
                    }
                });

                returnLog1 = findViewById(R.id.returnLog1);
                returnLog1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view)
                    {
                        startActivity(new Intent(MenuActivity.this, MainActivity.class));
                    }
                });

                view_all_bills = findViewById(R.id.view_all_bills);
                view_all_bills.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view)
                    {
                        startActivity(new Intent(MenuActivity.this, FeedBackActivity.class));
                    }
                });

                change_password = findViewById(R.id.change_password);
                change_password.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view)
                    {
                        startActivity(new Intent(MenuActivity.this,ChangePasswordActivity.class));
                    }
                });

            }
        });
        cityTv = (TextView)findViewById(R.id.city);

        MyApplication.setCity("南京");
        juheWeather = new JuheWeather();


        //city中存在问题
//        if( cityTv.getText().toString() != city && city != "" )
//        {
//            cityTv.setText(city);
//            MyApplication.setCity(city);
//        }


        Toast.makeText(MenuActivity.this,MyApplication.getCity(), Toast.LENGTH_SHORT).show();

        MobSDK.init(this,"2b0c7cd5f3274","2f9405486d31d14c194ffffc212e3e45");

        weatherLayout = (LinearLayout) findViewById(R.id.weather_layout1);
        weatherLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MenuActivity.this,WeaMainActivity.class);
                intent.putExtra("temp",tempTv.getText().toString());
                intent.putExtra("wid",wid);
                intent.putExtra("city",MyApplication.getCity());
                intent.putExtra("weather",weatherTv.getText().toString());
                startActivity(intent);
            }
        });

        ZaiHai = findViewById(R.id.button7);
        ZaiHai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MenuActivity.this, ZaiHaiActivity.class));
            }
        });

        ShuiChanZhiDao = findViewById(R.id.button5);
        ShuiChanZhiDao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MenuActivity.this, ShuiChanZhiDaoActivity.class));
            }
        });

        History = findViewById(R.id.button1);
        History.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MenuActivity.this, HistoryActivity.class));
            }
        });

        Predict = findViewById(R.id.button4);
        Predict.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MenuActivity.this, PredictActivity.class));
            }
        });

        Outlier = findViewById(R.id.button3);
        Outlier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MenuActivity.this, OutlierActivity.class));
            }
        });

        YuyeInfo = findViewById(R.id.button6);
        YuyeInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MenuActivity.this, NewsMainActivity.class));
            }
        });

        FeedBack = findViewById(R.id.button8);
        FeedBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MenuActivity.this, FeedBackActivity.class));
            }
        });







//        getWeatherInfo();


    }

    public void getWeatherInfo()
    {
        HttpUtil.sendOkHttpRequest("http://v.juhe.cn/weather/index?format=2&cityname=" + MyApplication.getCity() + "&key=" + juheKey,new Callback()
        {
            @Override
            public void onFailure(Call call,IOException e)
            {
                Log.i(TAG,"onFailure: 查询天气失败");
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException
            {
                final String responseText = response.body().string();
                Log.i(TAG,"onResponse: 查询天气成功" + responseText);
                // parseWeatherWithJSON(responseText);
                try
                {
                    JSONObject obj = new JSONObject(responseText);
                    String resultcode = obj.getString("resultcode");
                    if( resultcode.equals("200") )
                    {
                        String reason = obj.getString("reason");
                        juheWeather.resultcode = resultcode;
                        juheWeather.reason = reason;

                        //获得内部result
                        JSONObject object = obj.getJSONObject("result");
                        Result result = new Result();
                        juheWeather.result = result;

                        //获得内部SK
                        JSONObject object1 = object.getJSONObject("sk");
                        SK sk = new SK();
                        sk.temp = object1.getString("temp");
                        sk.humidity = object1.getString("humidity");
                        sk.time = object1.getString("time");
                        sk.wind_strength = object1.getString("wind_strength");
                        sk.wing_direction = object1.getString("wind_direction");
                        juheWeather.result.sk = sk;

                        //获得内部today
                        JSONObject object2 = object.getJSONObject("today");
                        Today today = new Today();
                        today.city = object2.getString("city");
                        today.date_y = object2.getString("date_y");
                        today.week = object2.getString("week");
                        today.temperature = object2.getString("temperature");
                        today.weather = object2.getString("weather");

                        JSONObject object21 = object2.getJSONObject("weather_id");
                        WeatherId weatherId = new WeatherId();
                        weatherId.fa = object21.getString("fa");
                        weatherId.fb = object21.getString("fb");
                        today.weather_id = weatherId;
                        wid = Integer.parseInt(weatherId.fa);
                        weatherImg.getDrawable().setLevel(wid);

                        today.wind = object2.getString("wind");
                        today.dressing_index = object2.getString("dressing_index");
                        today.dressing_advice = object2.getString("dressing_advice");
                        today.uv_index = object2.getString("uv_index");
                        today.comfort_index = object2.getString("comfort_index");
                        today.wash_index = object2.getString("wash_index");
                        today.travel_index = object2.getString("travel_index");
                        today.exercise_index = object2.getString("exercise_index");
                        today.drying_index = object2.getString("drying_index");

                        juheWeather.result.today = today;

                        List<Future> futureList = new ArrayList<>();
                        JSONArray jsonArray = object.getJSONArray("future");
                        for( int i = 0 ; i < jsonArray.length() ; i++ )
                        {
                            JSONObject object3 = jsonArray.getJSONObject(i);
                            Future future = new Future();
                            future.date = object3.getString("date");
                            future.temperature = object3.getString("temperature");
                            future.weather = object3.getString("weather");
                            future.week = object3.getString("week");
                            future.wind = object3.getString("wind");
                            JSONObject object31 = object3.getJSONObject("weather_id");
                            WeatherId weatherId3 = new WeatherId();
                            weatherId3.fa = object21.getString("fa");
                            weatherId3.fb = object21.getString("fb");
                            future.weatherId = weatherId3;
                            Log.i(TAG,"onResponse: " + future.toString());
                            futureList.add(future);
                        }
                        juheWeather.result.future = futureList;
                    }
                    juheWeather.error_code = obj.getString("error_code");
                    Log.i(TAG,"onResponse: " + juheWeather.result.sk.toString() + juheWeather.result.today.toString());
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            tempTv.setText(juheWeather.result.sk.temp);
                            weatherTv.setText(juheWeather.result.today.weather);
                            fengTv.setText(juheWeather.result.sk.wing_direction + juheWeather.result.sk.wind_strength);
                            lowhighTv.setText(juheWeather.result.today.temperature);
                            cityTv.setText(juheWeather.result.today.city);
                            monthdayTv.setText(juheWeather.result.today.date_y.split("年")[1]);
                            weekdayTv.setText(juheWeather.result.today.week);
                        }
                    });
                }
                catch( JSONException e )
                {
                    e.printStackTrace();
                }

            }
        });
    }


    private void setLocation()
    {

        List<String> permissionList = new ArrayList<>();
        if( ContextCompat.checkSelfPermission(MenuActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED )
        {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if( ContextCompat.checkSelfPermission(MenuActivity.this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED )
        {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if( ContextCompat.checkSelfPermission(MenuActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )
        {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if( !permissionList.isEmpty() )
        {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MenuActivity.this,permissions,1);
        }
        else
        {
            requestLocation();
        }

    }

    private void requestLocation()
    {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation()
    {

        //设置每5秒钟更新一下当前的位置
        //option.setScanSpan(5000);
        option.setIsNeedAddress(true);

        /*百度定位一共有三种模式，
         *Hight_Accuracy，高精度模式，有GPS信号时使用GPS，没有使用网络定位
         * Battery_Saving， 节电模式，只会使用网络定位
         * Devive_Sensors，传感器模式，只会使用GPS定位
         * 默认就是第一种模式，不需要特意指定*/
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//GPS模式
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        switch( requestCode )
        {
            case 1:
                if( grantResults.length > 0 )
                {
                    for( int result: grantResults )
                    {
                        if( result != PackageManager.PERMISSION_GRANTED )
                        {
                            Toast.makeText(MenuActivity.this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }
                else
                {
                    Toast.makeText(MenuActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private class MyLocationListener extends BDAbstractLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation bdLocation)
        {
//            MyApplication.setCity(bdLocation.getCity());
            city = "阜阳市";
            Toast.makeText(MenuActivity.this,bdLocation.getCity(), Toast.LENGTH_SHORT).show();
        }
    }
}
