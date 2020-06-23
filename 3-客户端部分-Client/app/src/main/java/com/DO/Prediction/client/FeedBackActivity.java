package com.DO.Prediction.client;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class FeedBackActivity extends AppCompatActivity
{
    private ImageView backup;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        backup = (ImageView)findViewById(R.id.backup);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textView = findViewById(R.id.help_feedback);

        button = findViewById(R.id.feedback);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                sendEMail();
            }
        });

    }

    private void sendEMail()
    {
        MailManager.getInstance().sendMail("意见反馈", textView.getText().toString());
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "fade.amr";
        MailManager.getInstance().sendMailWithFile("title", "content", path);
        Toast.makeText(FeedBackActivity.this,"反馈成功!谢谢您的宝贵意见！",Toast.LENGTH_SHORT).show();
    }
}
