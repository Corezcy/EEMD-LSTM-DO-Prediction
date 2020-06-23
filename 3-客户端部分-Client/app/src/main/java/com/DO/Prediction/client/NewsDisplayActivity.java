package com.DO.Prediction.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class NewsDisplayActivity extends AppCompatActivity
{

    private String newsUrl;
    private WebView webView;
    private ImageView backup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);

        backup = (ImageView)findViewById(R.id.backup);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        newsUrl = getIntent().getStringExtra("news_url");
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        WebView webView = (WebView) findViewById(R.id.web_view);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        settings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.setSupportZoom(true);//是否可以缩放，默认true
        settings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setAppCacheEnabled(true);//是否使用缓存
        settings.setDomStorageEnabled(true);//DOM Storage
        webView.loadUrl(newsUrl);

        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });



        webView.loadUrl(newsUrl);
    }
}
