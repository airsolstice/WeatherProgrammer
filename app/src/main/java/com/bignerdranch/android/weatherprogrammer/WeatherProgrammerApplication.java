package com.bignerdranch.android.weatherprogrammer;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @Package com.bignerdranch.android.weatherprogrammer
 * @Description 应用初始化
 * @date 2018/11/27
 */
public class WeatherProgrammerApplication extends Application {

    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
    }

    public static RequestQueue getHttpQueues() {
        return requestQueue;
    }

}
