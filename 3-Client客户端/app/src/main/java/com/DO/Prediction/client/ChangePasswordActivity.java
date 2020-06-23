package com.DO.Prediction.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ChangePasswordActivity extends AppCompatActivity
{

    private TextView username;
    private TextView password;
    private TextView newpassword;
    private TextView passwordagain;
    private Button check_change;
    private ImageView backup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        backup = (ImageView)findViewById(R.id.backup);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        newpassword = findViewById(R.id.newpassword);
        passwordagain = findViewById(R.id.passwordagain);
        check_change = findViewById(R.id.check_change);
        check_change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if( newpassword.getText().toString().equals(passwordagain.getText().toString()) == false )
                    Toast.makeText(ChangePasswordActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                else if( username.getText().toString().equals("") || newpassword.getText().toString().equals("") || passwordagain.getText().toString().equals(""))
                    Toast.makeText(ChangePasswordActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                else
                    getCheckFromServer("http://" + MyApplication.getLocalIP() +":5000/change",
                            username.getText().toString(),
                            password.getText().toString(),
                            newpassword.getText().toString());

            }
        });
    }

    private void getCheckFromServer(String url,final String userName,String passWord,String newpassword)
    {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("username",userName);
        formBuilder.add("password",passWord);
        formBuilder.add("newpassword",newpassword);
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
                                Toast.makeText(ChangePasswordActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ChangePasswordActivity.this,"没有当前用户",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ChangePasswordActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ChangePasswordActivity.this,"更改密码成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });

                        }

                    }
                });
            }
        });

    }
}
