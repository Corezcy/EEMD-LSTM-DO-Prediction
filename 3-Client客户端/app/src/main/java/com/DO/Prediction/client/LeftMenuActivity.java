package com.DO.Prediction.client;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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

public class LeftMenuActivity extends AppCompatActivity
{

    private Button returnLog;
    private TextView returnLog1;
    private TextView user_name;
    private TextView view_all_bills;
    private TextView change_password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_menu);

        user_name = findViewById(R.id.user_name);
        user_name.setText(MyApplication.getUser().getId());

        returnLog = findViewById(R.id.returnLog);
        returnLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(LeftMenuActivity.this, MainActivity.class));
            }
        });

        returnLog1 = findViewById(R.id.returnLog1);
        returnLog1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(LeftMenuActivity.this, MainActivity.class));
            }
        });

        view_all_bills = findViewById(R.id.view_all_bills);
        view_all_bills.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(LeftMenuActivity.this, FeedBackActivity.class));
            }
        });

        change_password = findViewById(R.id.change_password);
        change_password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(LeftMenuActivity.this,ChangePasswordActivity.class));
            }
        });

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }



}
