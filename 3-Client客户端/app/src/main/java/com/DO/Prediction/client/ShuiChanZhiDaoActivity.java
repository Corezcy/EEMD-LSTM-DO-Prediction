package com.DO.Prediction.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class ShuiChanZhiDaoActivity extends AppCompatActivity {

    private LinearLayout zaihai1;
    private LinearLayout zaihai2;
    private LinearLayout zaihai3;
    private LinearLayout zaihai4;

    private Button previous;
    private Button next;

    private ImageView backup;

    private int currentzaihai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shui_chan_zhi_dao);
        initView();
    }

    public void initView() {
        zaihai1 = (LinearLayout)findViewById(R.id.zaihai_1);
        zaihai2 = (LinearLayout)findViewById(R.id.zaihai_2);
        zaihai3 = (LinearLayout)findViewById(R.id.zaihai_3);
        zaihai4 = (LinearLayout)findViewById(R.id.zaihai_4);

        previous = (Button)findViewById(R.id.previous1);
        next = (Button)findViewById(R.id.next1);

        backup = (ImageView)findViewById(R.id.backup1);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        currentzaihai = 1;
        zaihai1.setVisibility(View.VISIBLE);
        zaihai2.setVisibility(View.GONE);
        zaihai3.setVisibility(View.GONE);
        zaihai3.setVisibility(View.GONE);
        previous.setVisibility(View.GONE);
        next.setVisibility(View.VISIBLE);

        //上一条点击时
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentzaihai){
                    case 2:
                        currentzaihai = 1;
                        zaihai1.setVisibility(View.VISIBLE);
                        zaihai2.setVisibility(View.GONE);
                        zaihai3.setVisibility(View.GONE);
                        zaihai4.setVisibility(View.GONE);
                        previous.setVisibility(View.GONE);
                        break;
                    case 3:
                        currentzaihai = 2;
                        zaihai1.setVisibility(View.GONE);
                        zaihai2.setVisibility(View.VISIBLE);
                        zaihai3.setVisibility(View.GONE);
                        zaihai4.setVisibility(View.GONE);
                        previous.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        currentzaihai = 3;
                        zaihai1.setVisibility(View.GONE);
                        zaihai2.setVisibility(View.GONE);
                        zaihai3.setVisibility(View.VISIBLE);
                        zaihai4.setVisibility(View.GONE);
                        previous.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentzaihai){
                    case 1:
                        currentzaihai = 2;
                        zaihai1.setVisibility(View.GONE);
                        zaihai2.setVisibility(View.VISIBLE);
                        zaihai3.setVisibility(View.GONE);
                        zaihai4.setVisibility(View.GONE);
                        previous.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        currentzaihai = 3;
                        zaihai1.setVisibility(View.GONE);
                        zaihai2.setVisibility(View.GONE);
                        zaihai3.setVisibility(View.VISIBLE);
                        zaihai4.setVisibility(View.GONE);
                        previous.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        currentzaihai = 4;
                        zaihai1.setVisibility(View.GONE);
                        zaihai2.setVisibility(View.GONE);
                        zaihai3.setVisibility(View.GONE);
                        zaihai4.setVisibility(View.VISIBLE);
                        previous.setVisibility(View.VISIBLE);
                        next.setVisibility(View.GONE);
                        break;

                }
            }
        });

    }
}
