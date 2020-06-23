package com.DO.Prediction.client;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

public class NewsMainActivity extends AppCompatActivity
{

    private List<News> newsList;
    private NewsAdapter adapter;
    private Handler handler;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);

        newsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.news_lv);
        getNews();
        handler = new Handler()
        {
            @Override

            public void handleMessage(Message msg)
            {
                if( msg.what == 1 )
                {
                    adapter = new NewsAdapter(NewsMainActivity.this,newsList);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent,View view,int position,long id)
                        {
                            News news = newsList.get(position);
                            Intent intent = new Intent(NewsMainActivity.this,NewsDisplayActivity.class);
                            intent.putExtra("news_url",news.getNewsUrl());

//                            WebView webView = (WebView) findViewById(R.id.web_view);
//                            webView.getSettings().setJavaScriptEnabled(true);
//                            webView.setWebViewClient(new WebViewClient());
//                            webView.loadUrl(news.getNewsUrl());

                            startActivity(intent);
                        }
                    });
                }
            }
        };

    }


    private void getNews()
    {
        Log.e("Jsoup","Test");

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    //获取虎扑新闻20页的数据，网址格式为：https://voice.hupu.com/nba/第几页
                    for( int i = 1 ; i <= 10 ; i++ )
                    {

                        Document doc = Jsoup.connect("http://www.zgyy.com.cn/Techs/Page_" + Integer.toString(i) + "_NodeId_yy_js_szcl.shtml").get();

                        Elements titleLinks = doc.select("div.gongQiuRight.LieBiaoRight").select("ul").select("li");    //解析来获取每条新闻的标题与链接地址
                        Log.e("title",Integer.toString(titleLinks.size()));
                        for( int j = 0 ; j < titleLinks.size() ; j++ )
                        {
                            String title = titleLinks.get(j).select("a").attr("title");
                            String url = titleLinks.get(j).select("a").attr("href");
//                            System.out.println("http://www.zgyy.com.cn/" + url);
                            News news = new News(title,"http://www.zgyy.com.cn/"+url,null,null,null);
                            newsList.add(news);
                        }
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                }
                catch( Exception e )
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}



