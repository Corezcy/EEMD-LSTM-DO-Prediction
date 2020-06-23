package com.DO.Prediction.client.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng Chao You on 2020/1/10 0010.
 */

public class JsonReadUtil {
    public static String getJsonStr(Context context, String fileName) {
        StringBuilder stringBuffer = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }


    public static <T> List<T> fromJsonFile(Context context, String fileName, Class<T> clazz) {
        String jsonStr = getJsonStr(context, fileName);

        List<T> lst = new ArrayList<T>();

        JsonArray array = new JsonParser().parse(jsonStr).getAsJsonArray();
        for (final JsonElement elem : array) {
            lst.add(new Gson().fromJson(elem, clazz));
        }

        return lst;
    }


    public static <T> List<T> fromJsonArray(String jsonStr, Class<T> clazz) {
        List<T> lst = new ArrayList<T>();
        try {

            JsonArray array = new JsonParser().parse(jsonStr).getAsJsonArray();
            for (final JsonElement elem : array) {
                lst.add(new Gson().fromJson(elem, clazz));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lst;
    }
}
