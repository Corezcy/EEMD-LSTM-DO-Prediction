package com.DO.Prediction.client;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.DO.Prediction.client.R;
import com.DO.Prediction.client.app.App;
import com.DO.Prediction.client.app.MyApplication;
import com.DO.Prediction.client.entity.WeatherDataModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChartActivity extends AppCompatActivity
{
    private static final String TAG = "ChartActivity";

    private LineChartView lineChart;
    static String[] calendar;
    static double[] value;
    double[] maxtemp;
    double[] mintemp;
    double[] sunshinehours;
    double[] rainfall;

    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    private List<PointValue> mPointValues2 = new ArrayList<PointValue>();


    private ArrayList<WeatherDataModel> weatherDataModelArrayList;

    private String didian;
    private String cmyear;
    private String zhibiao;
    private String title;

    private TextView titleTv;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent intent = getIntent();
        didian = intent.getStringExtra("didian");
        cmyear = intent.getStringExtra("cmyear");
        zhibiao = intent.getStringExtra("zhibiao");
        title = didian + cmyear + "年" + zhibiao + "数据";

        titleTv = (TextView) findViewById(R.id.charttitle);
        titleTv.setText(title);

        getWeatherData();

        lineChart = (LineChartView) findViewById(R.id.linechart);
        lineChart.setVisibility(View.INVISIBLE);
    }

    private void initLineChart()
    {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        //        Line line2 = new Line(mPointValues2).setColor(Color.parseColor("#FF3010"));
        //        line2.setShape(ValueShape.SQUARE);
        //        line2.setCubic(false);
        //        line2.setFilled(false);
        //        line2.setHasLabels(true);
        //        line2.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        //        line2.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）

        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        //        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        //      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(false);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        //        lines.add(line2);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        axisX.setName("日期");  //表格名称        axisX.setTextSize(10);//设置字体大小
        //        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        switch( zhibiao )
        {
            case "耗氧量":
                axisY.setName("L");//y轴标注
                break;

            case "氨氮化合物":
                axisY.setName("mg/L");//y轴标注
                break;

            case "PH值":
                axisY.setName("单位 1");//y轴标注
                break;

            case "溶解氧":
                axisY.setName("mg/L");//y轴标注
                break;

        }
        axisY.setTextColor(Color.BLACK);
        axisY.setTextSize(10);//设置字体大小
        axisY.setHasLines(true);
        axisY.setLineColor(Color.BLACK);
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //        Axis axisY = new Axis().setHasLines(true);
        //        axisY.setMaxLabelChars(6);//max label length, for example 60
        //        List<AxisValue> values = new ArrayList<>();
        //        for(int i = 0; i < 100; i+= 10){
        //            AxisValue value = new AxisValue(i);
        //            String label = "";
        //            value.setLabel(label);
        //            values.add(value);
        //        }
        //        axisY.setValues(values);


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true,ContainerScrollType.HORIZONTAL);
        lineChart.setContainerScrollEnabled(true,ContainerScrollType.VERTICAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 4;
        lineChart.setCurrentViewport(v);

    }

    public void getAxisLables()
    {
        for( int i = 0 ; i < calendar.length ; i++ )
        {
            mAxisXValues.add(new AxisValue(i).setLabel(calendar[i]));
        }
    }

    public void getAxisPoint()
    {
        //        if (zhibiao.equals("耗氧量")) {
        //            for (int i = 0; i < maxtemp.length; i++) {
        //                mPointValues.add(new PointValue(i, (float) maxtemp[i]));
        //            }
        //        } else if (zhibiao.equals("氨氮化合物")) {
        //            for (int i = 0; i < mintemp.length; i++) {
        //                mPointValues.add(new PointValue(i, (float) mintemp[i]));
        //            }
        //        } else if (zhibiao.equals("PH值")) {
        //            for (int i = 0; i < sunshinehours.length; i++) {
        //                mPointValues.add(new PointValue(i, (float) sunshinehours[i]));
        //            }
        //        } else if (zhibiao.equals("溶解氧")) {
        //            for (int i = 0; i < rainfall.length; i++) {
        //                mPointValues.add(new PointValue(i, (float) rainfall[i]));
        //            }
        //        }
        for( int i = 0 ; i < calendar.length ; i++ )
        {
            mPointValues.add(new PointValue(i,(float) value[i]));
        }
    }

    public void getWeatherData()
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
                    RequestBody requestBody = new FormBody.Builder().add("didian",didian).add("cmyear",cmyear).add("zhibiao",zhibiao).build();
                    Request request = new Request.Builder()
                            //指定访问的服务器地址为电脑局域网l
                            .url("http://" + MyApplication.getLocalIP() + ":5000/chartdata").post(requestBody).build();
                    Response response = cilent.newCall(request).execute();
                    String result = response.body().string();

                    try
                    {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("b");

//                        int length = Integer.valueOf(jsonArray2.getString(0)) ;
                        int length = jsonArray.length();
                        calendar = new String[length];
                        value = new double[length];


                        for( int i = 0 ; i < jsonArray.length() ; i++ )
                        {
                            calendar[i] = jsonArray.getString(i);
                        }

                        JSONArray jsonArray1 = jsonObject.getJSONArray("c");
                        for( int i = 0 ; i < jsonArray1.length() ; i++ )
                        {
                            value[i] = jsonArray1.getDouble(i);
                        }
                        parseHaveHeaderJArray();

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


    private void parseHaveHeaderJArray()
    {

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                getAxisLables(); //获取x轴的标注
                getAxisPoint(); //获取坐标点
                initLineChart();//初始化
            }
        });

    }
}
