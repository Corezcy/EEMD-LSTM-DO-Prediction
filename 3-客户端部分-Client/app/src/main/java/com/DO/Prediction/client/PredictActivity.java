package com.DO.Prediction.client;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.DO.Prediction.client.app.MyApplication;
import com.DO.Prediction.client.coolweather.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PredictActivity extends AppCompatActivity
{

    private Button one;
    private Button three;
    private Button seven;
    private WebView result;
    private ImageView backup;
    private ProgressDialog progressDialog;//进度条(加载省市县信息时会出现)


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        one = findViewById(R.id.predict_one);
        three = findViewById(R.id.predict_three);
        seven = findViewById(R.id.predict_seven);
        result = findViewById(R.id.predict_result);
        backup = (ImageView)findViewById(R.id.backup);

        backup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                getPredictOne();
            }
        });

        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                getPredictThree();
            }
        });

        seven.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                getPredictSeven();
            }
        });



    }


    public void getPredictOne()
    {
//        showProgressDialog();
//        HttpUtil.sendOkHttpRequest("http://" + MyApplication.getLocalIP() +":5000/predictone", new Callback() {
//            @Override
//            public void onFailure(Call call,IOException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        closeProgressDialog();
//                        Toast.makeText(PredictActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String responseText = response.body().string();
//                try {
//                    JSONObject jsonObject = new JSONObject(responseText);
//                    JSONArray jsonArray = jsonObject.getJSONArray("b");
//                    String string = "未来1天溶解氧数据预测值是:\n";
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        string += jsonArray.getString(i);
//                        string +="\n";
//                    }
//                    result.setText(string);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            closeProgressDialog();
//                        }
//                    });
//                } catch ( JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        result.loadUrl("http://" + MyApplication.getLocalIP() +":5001/predictone");
//        closeProgressDialog();

        result.loadUrl("http://" + MyApplication.getLocalIP() +":5000/predictone");
        result.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        showProgressDialog();
                    }
                });
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        closeProgressDialog();
                    }
                });

            }
        });
    }

    public void getPredictThree()
    {
        result.loadUrl("http://" + MyApplication.getLocalIP() +":5000/predictthree");
        result.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgressDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                closeProgressDialog();

            }
        });
    }

    public void getPredictSeven()
    {
        result.loadUrl("http://" + MyApplication.getLocalIP() +":5000/predictthree");
        result.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgressDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                closeProgressDialog();

            }
        });
    }


    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

}
