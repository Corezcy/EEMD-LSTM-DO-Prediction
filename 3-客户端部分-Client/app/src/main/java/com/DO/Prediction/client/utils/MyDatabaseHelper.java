package com.DO.Prediction.client.utils;

/**
 * Created by Zheng Chao You on 2020/5/5 0005.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    //创建一个当地的用户表， 用来保存用户名和密码，确保用户退出时，下次打开可直接登陆上
    public static final String CREATE_USER = "create table User ( "
            + "phone text unique, "
            + "password text) ";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        //sqLiteDatabase.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext,"Create SQL Succeed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists User");
        onCreate(sqLiteDatabase);
    }
}
