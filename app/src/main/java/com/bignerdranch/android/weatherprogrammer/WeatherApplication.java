package com.bignerdranch.android.weatherprogrammer;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @Package com.bignerdranch.android.weatherprogrammer
 * @Description 应用初始化
 * @date 2018/11/27
 */
public class WeatherApplication extends Application {

    public static RequestQueue requestQueue;

    public static String FILE_LOCATION_OPTION = "settings";
    public static final String DISPLAY_WORD = "current city is :";

    public static final String KEY_CITY_NAME = "city_name";
    public static final String DEFINE_CITY_NAME = "Changsha";

    public static final String KEY_CITY_ID = "city_code";
    public static final String DEFINE_CITY_ID = "1815577";

    public static final String KEY_TEMPERATURE_UTILS = "city_code";
    public static final String DEFINE_TEMPERATURE_UTILS = "Metric";

    public static final String KEY_NOTIFICATIONS = "city_code";
    public static final Boolean DEFINE_NOTIFICATION = false;


    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
    }

    public static RequestQueue getHttpQueues() {
        return requestQueue;
    }

}
