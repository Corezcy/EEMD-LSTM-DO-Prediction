package com.DO.Prediction.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.DO.Prediction.client.R;
import com.DO.Prediction.client.app.MyApplication;
import com.DO.Prediction.client.coolweather.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "HistoryActivity";

    private LinearLayout zhexiantuLayout;
    private ImageView backup;

    private String selectedDidian;
    private String selectedNianfen;
    private String selectedZhibiao;


    private Spinner didian_spinner;
    private Spinner nianfen_spinner;
    private Spinner zhibiao_spinner;

    private List<String> didian_list;
    private List<String> nianfen_list;
    private List<String> zhibiao_list;
    private ArrayAdapter<String> didian_arrayAdapter;
    private ArrayAdapter<String> nianfen_arrayAdapter;
    private ArrayAdapter<String> zhibiao_arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        zhexiantuLayout = (LinearLayout)findViewById(R.id.zhexiantulayout);
        zhexiantuLayout.setOnClickListener(this);

        backup = (ImageView)findViewById(R.id.backup);
        backup.setOnClickListener(this);

        didian_spinner = (Spinner)findViewById(R.id.spinner_didian);
        nianfen_spinner = (Spinner)findViewById(R.id.spinner_nianfen);
        zhibiao_spinner = (Spinner)findViewById(R.id.spinner_zhibiao);

        initData();
        initMonitor();
    }

    private void initMonitor() {

        didian_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int positioin, long l) {
                selectedDidian = didian_spinner.getItemAtPosition(positioin).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        nianfen_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int positioin, long l) {
                selectedNianfen = nianfen_spinner.getItemAtPosition(positioin).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        zhibiao_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int positioin, long l) {
                selectedZhibiao = zhibiao_spinner.getItemAtPosition(positioin).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void initData() {

        //地点数据加载
        didian_list  = new ArrayList<String>();
        HttpUtil.sendOkHttpRequest("http://" + MyApplication.getLocalIP() +":5000/alldidian", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HistoryActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseText);
                    JSONArray jsonArray = jsonObject.getJSONArray("b");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        didian_list.add(jsonArray.getString(i));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //适配器
                            didian_arrayAdapter = new ArrayAdapter<String>(HistoryActivity.this, android.R.layout.simple_spinner_item,didian_list);
                            //设置样式
                            didian_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //加载适配器
                            didian_spinner.setAdapter(didian_arrayAdapter);

                            selectedDidian = didian_list.get(0);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        //年份数据加载
        nianfen_list  = new ArrayList<String>();
        HttpUtil.sendOkHttpRequest("http://" + MyApplication.getLocalIP() +":5000/allyear", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HistoryActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseText);
                    JSONArray jsonArray = jsonObject.getJSONArray("b");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        nianfen_list.add(jsonArray.getString(i));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //适配器
                            nianfen_arrayAdapter = new ArrayAdapter<String>(HistoryActivity.this, android.R.layout.simple_spinner_item,nianfen_list);
                            //设置样式
                            nianfen_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //加载适配器
                            nianfen_spinner.setAdapter(nianfen_arrayAdapter);
                            selectedNianfen = nianfen_list.get(0);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        //指标
        zhibiao_list  = new ArrayList<String>();
        zhibiao_list.add("耗氧量");
        zhibiao_list.add("氨氮化合物");
        zhibiao_list.add("PH值");
        zhibiao_list.add("溶解氧");
        //适配器
        zhibiao_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,zhibiao_list);
        //设置样式
        zhibiao_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        zhibiao_spinner.setAdapter(zhibiao_arrayAdapter);

        selectedZhibiao = zhibiao_list.get(0);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.zhexiantulayout:
                Log.i(TAG, "onClick: " + selectedDidian + selectedNianfen  + selectedZhibiao);
                Intent intent = new Intent(HistoryActivity.this, ChartActivity.class);
                intent.putExtra("didian",selectedDidian);
                intent.putExtra("cmyear",selectedNianfen);
                intent.putExtra("zhibiao",selectedZhibiao);
                startActivity(intent);
                break;
            case R.id.backup:
                finish();
        }
    }
}
