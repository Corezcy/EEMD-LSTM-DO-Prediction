package com.DO.Prediction.client;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.DO.Prediction.client.app.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OutlierActivity extends AppCompatActivity
{

    private Button now;
    private Button one;
    private static String[] date;
    private static String[] date_copy;
    private static double[] value;
    private Button three;
    private Button seven;
    private ImageView backup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlier);

        now = findViewById(R.id.search_now);
        one = findViewById(R.id.search_past_one);
        three = findViewById(R.id.search_past_three);
        seven = findViewById(R.id.search_past_seven);
        backup = (ImageView)findViewById(R.id.backup);

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        now.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getNowData();
            }
        });

        one.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getOneData();
            }
        });

        three.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getThreeData();
            }
        });

        seven.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getSevenData();
            }
        });

    }

    public void getNowData()
    {
        //开启线程发送登录请求到服务器
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    OkHttpClient cilent = new OkHttpClient();
                    Request request = new Request.Builder()
                            //指定访问的服务器地址为电脑局域网l
                            .url("http://" + MyApplication.getLocalIP() + ":5000/search_now").build();
                    Response response = cilent.newCall(request).execute();
                    String result = response.body().string();

                    try
                    {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("b");

                        int length = jsonArray.length();

                        if( length == 0 )
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    AlertDialog alertDialog3 = new AlertDialog.Builder(OutlierActivity.this).setTitle("一切正常！").setIcon(R.mipmap.ic_launcher).setMessage("溶解氧数值正常！！！")//内容
                                            .create();

                                    alertDialog3.show();

                                }
                            });
                        }
                        else {
                            date = new String[length];
                            date_copy = new String[length];
                            value = new double[length];


                            for( int i = 0 ; i < jsonArray.length() ; i++ )
                            {
                                date[i] = jsonArray.getString(i);
                            }

                            JSONArray jsonArray1 = jsonObject.getJSONArray("c");
                            for( int i = 0 ; i < jsonArray1.length() ; i++ )
                            {
                                value[i] = jsonArray1.getDouble(i);
                            }

                            for( int i = 0, j = 0 ; i < jsonArray1.length() ; i++ )
                            {
                                if( value[i] < 5.0 )
                                {
                                    date_copy[j] = new String();
                                    date_copy[j] = date[i];
                                    j++;
                                }
                            }

                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {

                                        AlertDialog alertDialog3 = new AlertDialog.Builder(OutlierActivity.this).setTitle("检测到异常的日期").setIcon(R.mipmap.ic_launcher).setItems(date_copy,new DialogInterface.OnClickListener()
                                        {
                                            //添加列表
                                            @Override
                                            public void onClick(DialogInterface dialogInterface,int i)
                                            {

                                            }
                                        }).create();

                                        alertDialog3.show();
                                }
                            });

                        }



                    }
                    catch( JSONException e )
                    {
                        e.printStackTrace();
                    }


                }
                catch( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getOneData()
    {
        //开启线程发送登录请求到服务器
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    OkHttpClient cilent = new OkHttpClient();
                    Request request = new Request.Builder()
                            //指定访问的服务器地址为电脑局域网l
                            .url("http://" + MyApplication.getLocalIP() + ":5000/search_past_one").build();
                    Response response = cilent.newCall(request).execute();
                    String result = response.body().string();

                    try
                    {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("b");

                        int length = jsonArray.length();

                        if( length == 0 )
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    AlertDialog alertDialog3 = new AlertDialog.Builder(OutlierActivity.this).setTitle("一切正常！").setIcon(R.mipmap.ic_launcher).setMessage("溶解氧数值正常！！！")//内容
                                            .create();

                                    alertDialog3.show();

                                }
                            });
                        }
                        else {
                            date = new String[length];
                            date_copy = new String[length];
                            value = new double[length];


                            for( int i = 0 ; i < jsonArray.length() ; i++ )
                            {
                                date[i] = jsonArray.getString(i);
                            }

                            JSONArray jsonArray1 = jsonObject.getJSONArray("c");
                            for( int i = 0 ; i < jsonArray1.length() ; i++ )
                            {
                                value[i] = jsonArray1.getDouble(i);
                            }

                            for( int i = 0, j = 0 ; i < jsonArray1.length() ; i++ )
                            {
                                if( value[i] < 5.0 )
                                {
                                    date_copy[j] = new String();
                                    date_copy[j] = date[i];
                                    j++;
                                }
                            }

                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {

                                    AlertDialog alertDialog3 = new AlertDialog.Builder(OutlierActivity.this).setTitle("检测到异常的日期").setIcon(R.mipmap.ic_launcher).setItems(date_copy,new DialogInterface.OnClickListener()
                                    {
                                        //添加列表
                                        @Override
                                        public void onClick(DialogInterface dialogInterface,int i)
                                        {

                                        }
                                    }).create();

                                    alertDialog3.show();
                                }
                            });

                        }



                    }
                    catch( JSONException e )
                    {
                        e.printStackTrace();
                    }


                }
                catch( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getThreeData()
    {
        //开启线程发送登录请求到服务器
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    OkHttpClient cilent = new OkHttpClient();
                    Request request = new Request.Builder()
                            //指定访问的服务器地址为电脑局域网l
                            .url("http://" + MyApplication.getLocalIP() + ":5000/search_past_three").build();
                    Response response = cilent.newCall(request).execute();
                    String result = response.body().string();

                    try
                    {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("b");

                        int length = jsonArray.length();

                        if( length == 0 )
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    AlertDialog alertDialog3 = new AlertDialog.Builder(OutlierActivity.this).setTitle("一切正常！").setIcon(R.mipmap.ic_launcher).setMessage("溶解氧数值正常！！！")//内容
                                            .create();

                                    alertDialog3.show();

                                }
                            });
                        }
                        else {
                            date = new String[length];
                            date_copy = new String[length];
                            value = new double[length];


                            for( int i = 0 ; i < jsonArray.length() ; i++ )
                            {
                                date[i] = jsonArray.getString(i);
                            }

                            JSONArray jsonArray1 = jsonObject.getJSONArray("c");
                            for( int i = 0 ; i < jsonArray1.length() ; i++ )
                            {
                                value[i] = jsonArray1.getDouble(i);
                            }

                            for( int i = 0, j = 0 ; i < jsonArray1.length() ; i++ )
                            {
                                if( value[i] < 5.0 )
                                {
                                    date_copy[j] = new String();
                                    date_copy[j] = date[i];
                                    j++;
                                }
                            }

                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {

                                    AlertDialog alertDialog3 = new AlertDialog.Builder(OutlierActivity.this).setTitle("检测到异常的日期").setIcon(R.mipmap.ic_launcher).setItems(date_copy,new DialogInterface.OnClickListener()
                                    {
                                        //添加列表
                                        @Override
                                        public void onClick(DialogInterface dialogInterface,int i)
                                        {

                                        }
                                    }).create();

                                    alertDialog3.show();
                                }
                            });

                        }



                    }
                    catch( JSONException e )
                    {
                        e.printStackTrace();
                    }


                }
                catch( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getSevenData()
    {
        //开启线程发送登录请求到服务器
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    OkHttpClient cilent = new OkHttpClient();
                    Request request = new Request.Builder()
                            //指定访问的服务器地址为电脑局域网l
                            .url("http://" + MyApplication.getLocalIP() + ":5000/search_past_seven").build();
                    Response response = cilent.newCall(request).execute();
                    String result = response.body().string();

                    try
                    {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("b");

                        int length = jsonArray.length();

                        if( length == 0 )
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    AlertDialog alertDialog3 = new AlertDialog.Builder(OutlierActivity.this).setTitle("一切正常！").setIcon(R.mipmap.ic_launcher).setMessage("溶解氧数值正常！！！")//内容
                                            .create();

                                    alertDialog3.show();

                                }
                            });
                        }
                        else {
                            date = new String[length];
                            date_copy = new String[length];
                            value = new double[length];


                            for( int i = 0 ; i < jsonArray.length() ; i++ )
                            {
                                date[i] = jsonArray.getString(i);
                            }

                            JSONArray jsonArray1 = jsonObject.getJSONArray("c");
                            for( int i = 0 ; i < jsonArray1.length() ; i++ )
                            {
                                value[i] = jsonArray1.getDouble(i);
                            }

                            for( int i = 0, j = 0 ; i < jsonArray1.length() ; i++ )
                            {
                                if( value[i] < 5.0 )
                                {
                                    date_copy[j] = new String();
                                    date_copy[j] = date[i];
                                    j++;
                                }
                            }

                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {

                                    AlertDialog alertDialog3 = new AlertDialog.Builder(OutlierActivity.this).setTitle("检测到异常的日期").setIcon(R.mipmap.ic_launcher).setItems(date_copy,new DialogInterface.OnClickListener()
                                    {
                                        //添加列表
                                        @Override
                                        public void onClick(DialogInterface dialogInterface,int i)
                                        {

                                        }
                                    }).create();

                                    alertDialog3.show();
                                }
                            });

                        }



                    }
                    catch( JSONException e )
                    {
                        e.printStackTrace();
                    }


                }
                catch( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
