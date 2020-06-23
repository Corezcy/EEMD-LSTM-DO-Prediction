/**
 * 
 */
package com.DO.Prediction.client.utils;

import java.util.regex.Pattern;

import android.content.Context;
import android.text.format.Time;

import com.DO.Prediction.client.R;

/**
 * @author TANGYONG
 * @version 1.0 2010-05-27
 */
public class ForecastUtil {
	/**
     * Time when we consider daytime to begin. We keep this early to make sure
     * that our 6AM widget update will change icons correctly.
     */
    private static final int DAYTIME_BEGIN_HOUR = 8;

    /**
     * Time when we consider daytime to end. We keep this early to make sure
     * that our 6PM widget update will change icons correctly.
     */
    private static final int DAYTIME_END_HOUR = 20;
    
    private static final Pattern sIconStorm = Pattern.compile("(thunder|tstms)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconSnow = Pattern.compile("(snow|ice|frost|flurries)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconShower = Pattern.compile("(showers)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconSun = Pattern.compile("(sunny|breezy|clear)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconClouds = Pattern.compile("(cloud|overcast)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconPartlyCloudy = Pattern.compile("(partly_cloudy|mostly_sunny)", Pattern.CASE_INSENSITIVE);    
    private static final Pattern sIconMostCloudy = Pattern.compile("(most_cloudy)", Pattern.CASE_INSENSITIVE);    
    private static final Pattern sIconLightrain = Pattern.compile("(lightrain)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconChanceOfRain = Pattern.compile("(chance_of_rain)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconHeavyrain = Pattern.compile("(heavyrain)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconRain = Pattern.compile("(rain|storm)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconHaze = Pattern.compile("(haze)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconFog = Pattern.compile("(fog)", Pattern.CASE_INSENSITIVE);    
    
	public static int getForecastImage(String conditions, boolean isDaytime){
		int icon = 0;
		if (conditions == null){
        	icon = R.drawable.weather_sunny;
		}else if (sIconStorm.matcher(conditions).find()) {
            icon = isDaytime? R.drawable.weather_chancestorm : R.drawable.weather_chancestorm_n;
        } else if (sIconSnow.matcher(conditions).find()) {
            icon = isDaytime? R.drawable.weather_chancesnow : R.drawable.weather_chancesnow_n;
        } else if (sIconLightrain.matcher(conditions).find()) {
            icon = R.drawable.weather_lightrain;
        } else if (sIconShower.matcher(conditions).find()) {
            icon = R.drawable.weather_rain;
        } else if (sIconPartlyCloudy.matcher(conditions).find()) {
            icon = isDaytime ? R.drawable.weather_mostlysunny : R.drawable.weather_mostlysunny_n;
        } else if (sIconMostCloudy.matcher(conditions).find()) {
            icon = isDaytime ? R.drawable.weather_mostlycloudy : R.drawable.weather_mostlycloudy_n;
        } else if (sIconSun.matcher(conditions).find()) {
            icon = isDaytime ? R.drawable.weather_sunny : R.drawable.weather_sunny_n;
        } else if (sIconClouds.matcher(conditions).find()) {
            icon = R.drawable.weather_cloudy;
        } else if (sIconHeavyrain.matcher(conditions).find()) {
            icon = R.drawable.weather_rain;
        } else if (sIconRain.matcher(conditions).find()) {
            icon = R.drawable.weather_rain;
        } else if (sIconHaze.matcher(conditions).find()) {
            icon = R.drawable.weather_haze;
        } else if (sIconFog.matcher(conditions).find()) {
            icon = R.drawable.weather_fog;
        } else if (sIconChanceOfRain.matcher(conditions).find()) {
            icon = isDaytime ? R.drawable.weather_cloudyrain : R.drawable.weather_cloudyrain_n;
        } else {
        	icon = R.drawable.weather;
        }
		
		return icon;
	}
	
	public static int getCurrentForecastIcon(String url){
		int icon = 0;
		if (url == null){
			icon = R.drawable.weather_sunny;
		} else if (sIconClouds.matcher(url).find()){
			icon = R.drawable.weather_cloudy;
		} else if (sIconRain.matcher(url).find()){
			icon = R.drawable.weather_rain;
		}
		
		return icon;
	}
	
	public static int getDetailForecastIcon(String url){
		int icon = 0;
		
		if (url == null) {
			icon = R.drawable.sun;
		} else if (sIconPartlyCloudy.matcher(url).find()) {
            icon = R.drawable.mostlysunny;
        } else if (sIconSun.matcher(url).find()) {
			icon = R.drawable.sun;
		} else if (sIconClouds.matcher(url).find()) {
			icon = R.drawable.cloudy;
		} else if (sIconLightrain.matcher(url).find()) {
			icon = R.drawable.lightrain;
		} else if (sIconStorm.matcher(url).find()) {
			icon = R.drawable.storm;
		} else if (sIconChanceOfRain.matcher(url).find()) {
            icon = R.drawable.cloudyrain;
        } else if (sIconRain.matcher(url).find()) {
			icon = R.drawable.rain;
		} else if (sIconFog.matcher(url).find()) {
			icon = R.drawable.fog;
		} else if (sIconSnow.matcher(url).find()) {
			icon = R.drawable.rain;
		}
		
		return icon;
	}
	
	/**
     * Calcuate if it's currently "daytime" by our internal definition. Used to
     * decide which icons to show when updating widgets.
     */
    public static boolean isDaytime() {
        Time time = new Time();
        time.setToNow();
        return (time.hour >= DAYTIME_BEGIN_HOUR && time.hour <= DAYTIME_END_HOUR);
    }
    
    public static int getImageNumber(int num){
    	if (num == 0){
    		return R.drawable.number_0_tahoma;
    	} else if (num == 1){
    		return R.drawable.number_1_tahoma;
    	}else if (num == 2){
    		return R.drawable.number_2_tahoma;
    	}else if (num == 3){
    		return R.drawable.number_3_tahoma;
    	}else if (num == 4){
    		return R.drawable.number_4_tahoma;
    	}else if (num == 5){
    		return R.drawable.number_5_tahoma;
    	}else if (num == 6){
    		return R.drawable.number_6_tahoma;
    	}else if (num == 7){
    		return R.drawable.number_7_tahoma;
    	}else if (num == 8){
    		return R.drawable.number_8_tahoma;
    	}else if (num == 9){
    		return R.drawable.number_9_tahoma;
    	} else {
    		return -1;
    	}
    }
    
    public static String getDayofWeek(Context c, int day){
    	String week = null;
    	switch (day) {
		case 0:
			week = c.getString(R.string.sunday);
			break;
		case 1:
			week = c.getString(R.string.monday);
			break;
		case 2:
			week = c.getString(R.string.tuesday);
			break;
		case 3:
			week = c.getString(R.string.wednesday);
			break;
		case 4:
			week = c.getString(R.string.thursday);
			break;
		case 5:
			week = c.getString(R.string.friday);
			break;
		case 6:
			week = c.getString(R.string.saturday);
			break;
		default:
			break;
		}
    	
    	return week;
    }
}
