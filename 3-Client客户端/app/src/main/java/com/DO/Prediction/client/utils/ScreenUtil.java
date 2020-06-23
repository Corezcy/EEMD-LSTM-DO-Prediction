package com.DO.Prediction.client.utils;

import android.content.Context;
import android.view.WindowManager;

import com.DO.Prediction.client.app.MyApplication;

/**
 * Created by Zheng Chao You on 2020/1/10 0010.
 * TODO：屏幕工具类
 */

public class ScreenUtil {
        private static Context sContext;

        public static void init(Context context) {
            sContext = context.getApplicationContext();
        }

        public static int getScreenWidth() {
            WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
            return wm.getDefaultDisplay().getWidth();
        }

        public static int getScreenHeight() {
            WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
            return wm.getDefaultDisplay().getHeight();
        }

        /**
         * 获取状态栏高度
         */
        public static int getSystemBarHeight() {
            int result = 0;
            int resourceId = sContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = sContext.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }

        public static int dp2px(float dpValue) {
            final float scale = sContext.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        public static int px2dp(float pxValue) {
            final float scale = sContext.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

        public static int sp2px(float spValue) {
            final float fontScale = sContext.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }
}
