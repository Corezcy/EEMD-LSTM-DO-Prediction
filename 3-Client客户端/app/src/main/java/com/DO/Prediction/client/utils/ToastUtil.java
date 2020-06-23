package com.DO.Prediction.client.utils;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.DO.Prediction.client.app.MyApplication;

import static android.content.ContentValues.TAG;


/**
 * Created by Zheng Chao You on 2020/1/8 0008.
 */

public class ToastUtil {

    public static void showToast(String msg){
        try {
            if (isMainThread()) {
                Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
            } else {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLongToast(String msg){
        if(isMainThread()){
            Log.d(TAG, "showLongToast: isMainThread()");
            Toast.makeText(MyApplication.getContext(),msg,Toast.LENGTH_LONG).show();
        }else{
            Log.d(TAG, "showLongToast: else");
            Looper.prepare();
            Toast.makeText(MyApplication.getContext(),msg,Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
