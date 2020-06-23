package com.DO.Prediction.client;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.DO.Prediction.client.app.MyApplication;
import com.DO.Prediction.client.entity.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "LoginActivity";
    private SharedPreferences sharedPreferences;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button login;
    private Button toregister; //转到注册活动的页面按钮
    private TextView user_name;

    private ImageView backup;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEdit = (EditText) findViewById(R.id.username);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        toregister = (Button) findViewById(R.id.register);
        toregister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        usernameEdit = (EditText) findViewById(R.id.username);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        toregister = (Button) findViewById(R.id.register);
        toregister.setOnClickListener(this);

        String userName = usernameEdit.getText().toString();
        String passWord = passwordEdit.getText().toString();

        switch( view.getId() )
        {
            case R.id.login:

                if( userName.equals("") || passWord.equals("") )
                {
                    Toast.makeText(this,"账号密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "http://" + MyApplication.getLocalIP() +":5000/user";/*在此处改变你的服务器地址*/
//                String url = "http://192.168.0.101:5000/user";/*在此处改变你的服务器地址*/
                getCheckFromServer(url,userName,passWord);
                break;
            case R.id.register:
//                String url2 = "http://" + MyApplication.getLocalIP() +":5000/register";/*在此处改变你的服务器地址*/
//                registeNameWordToServer(url2,userName,passWord);
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 将用户名和密码发送到服务器进行比对，若成功则跳转到app主界面，若错误则刷新UI提示错误登录信息
     *
     * @param url      服务器地址
     * @param userName 用户名
     * @param passWord 密码
     */
    private void getCheckFromServer(String url,final String userName,String passWord)
    {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("username",userName);
        formBuilder.add("password",passWord);
        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();


        Call call = client.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call,IOException e)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(MainActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException
            {
                final String res = response.body().string();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if( res.equals("0") )
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(MainActivity.this,"没有当前用户",Toast.LENGTH_SHORT).show();
//                                    goToMenuActivity();
                                }
                            });
                        }
                        else if( res.equals("1") )
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
//                                    goToMenuActivity();
                                }
                            });
                        }
                        else if( res.equals("2") )
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                                    User user = new User();
                                    user.setId(userName);
                                    MyApplication.setUser(user);
                                    goToMenuActivity();
                                }
                            });
                        }
                    }
                });
            }
        });

    }

    private void goToMenuActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
    }

}
