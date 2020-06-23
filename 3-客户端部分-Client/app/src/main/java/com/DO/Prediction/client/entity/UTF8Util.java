package com.DO.Prediction.client.entity;

import java.io.UnsupportedEncodingException;

/**
 * Created by Zheng Chao You on 2020/5/9 0009.
 */

public class UTF8Util {
    public static String getUTF8(String str) {
        String newstr = null;
        try {
            newstr =  new String(str.getBytes("ISO-8859-1"), "UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newstr;
    }
}
