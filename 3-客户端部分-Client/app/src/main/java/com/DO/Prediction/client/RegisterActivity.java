package com.DO.Prediction.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.DO.Prediction.client.app.MyApplication;
import com.DO.Prediction.client.utils.TimeCountUtil;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity
{

    private static final String TAG = "RegisterActivity";
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText passwordagainEdit;
    private Button register;
    private TextView fasong;
    private EditText yanzhengma;

    private EventHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MobSDK.init(this,"25f36984a1258","6fc9670aea98f8fecdd7a1e9a7854810");

        usernameEdit = (EditText) findViewById(R.id.username);
        passwordEdit = (EditText) findViewById(R.id.password);
        passwordagainEdit = (EditText) findViewById(R.id.passwordagain);
        register = (Button) findViewById(R.id.register);
        fasong = (TextView) findViewById(R.id.fasong);
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);


        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                String phone2= usernameEdit.getText().toString();
                String number = yanzhengma.getText().toString();
                if ( TextUtils.isEmpty(phone2)) {
                    Toast.makeText(RegisterActivity.this, "号码不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(number)) {
                    Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, phone2 + " " + number);
                    SMSSDK.submitVerificationCode("86", phone2, number);
                    //                    sendCode("86", phone2);
                }
            }
        });

        fasong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                String phone1 = usernameEdit.getText().toString();
                if (TextUtils.isEmpty(phone1)) {
                    Toast.makeText(RegisterActivity.this, "号码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    SMSSDK.getVerificationCode("86", phone1);
                    //                    sendCode("86",phone1);
                }
                TimeCountUtil timeCountUtil = new TimeCountUtil(
                        RegisterActivity.this, 60000, 1000, fasong);
                timeCountUtil.start();
            }
        });

        SMSSDK.registerEventHandler(new EventHandler()
        {
            @Override
            public void afterEvent(int event,int result,Object data)
            {
                if( result == SMSSDK.RESULT_COMPLETE )
                {

                    //回调完成
                    if( event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE )
                    {
                        //提交验证码成功
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(RegisterActivity.this,"验证码正确",Toast.LENGTH_SHORT).show();
                                if( usernameEdit.getText().toString().equals("") || passwordEdit.getText().toString().equals("") || passwordagainEdit.getText().toString().equals("") )
                                {
                                    //如果信息填写不完整，提示填写完整信息
                                    Toast.makeText(RegisterActivity.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
                                }
                                else if( passwordEdit.getText().toString().equals(passwordagainEdit.getText().toString()) == false )
                                {
                                    //如果两次输入密码不一致，提示重新输入
                                    Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //注册，发送注册请求至服务器
                                    String url = "http://" + MyApplication.getLocalIP() +":5000/register";
                                    registeNameWordToServer(url,usernameEdit.getText().toString(),passwordEdit.getText().toString());
                                }
                            }
                        });
                    }
                    else if( event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE )
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(RegisterActivity.this,"语音验证发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if( event == SMSSDK.EVENT_GET_VERIFICATION_CODE )
                    {
                        //获取验证码成功
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                fasong.setText("验证码已发送");
                            }
                        });
                        //                        TimeCountUtil timeCountUtil = new TimeCountUtil(
                        //                                RegisterActivity.this, 60000, 1000, fasong);
                        //                        timeCountUtil.start();
                    }
                    else if( event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES )
                    {

                    }
                    else
                    {
                        ((Throwable) data).printStackTrace();
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(RegisterActivity.this,"错误",Toast.LENGTH_SHORT);
                            }
                        });
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        try
                        {
                            JSONObject obj = new JSONObject(throwable.getMessage());
                            final String des = obj.optString("detail");
                            if( !TextUtils.isEmpty(des) )
                            {
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        Toast.makeText(RegisterActivity.this,des,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        catch( JSONException e )
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    private void registeNameWordToServer(String url,final String userName,String passWord)
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
                        Toast.makeText(RegisterActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RegisterActivity.this,"该用户名已被注册",Toast.LENGTH_SHORT).show();
                        }
                        else if( res.equals("1") )
                        {
                            Toast.makeText(RegisterActivity.this,"注册成功！请登录",Toast.LENGTH_SHORT).show();
                            goToMainActivity();
                        }

                    }
                });
            }
        });

    }

    private void goToMainActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

}
