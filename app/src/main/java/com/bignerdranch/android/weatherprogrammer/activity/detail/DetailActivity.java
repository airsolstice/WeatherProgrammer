package com.bignerdranch.android.weatherprogrammer.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapForecastList;

public class DetailActivity extends AppCompatActivity {

    private static final  String WEATHER_KEY = "weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        OpenWeatherMapForecastList weather = (OpenWeatherMapForecastList) intent.getSerializableExtra(WEATHER_KEY);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_detail);
        ((WeatherDetailFragment) fragment).refresh(weather);
    }

    public static void start(Context context, OpenWeatherMapForecastList weather) {
        Intent intent = new Intent();
        intent.setClass(context, DetailActivity.class);
        intent.putExtra(WEATHER_KEY, weather);
        context.startActivity(intent);
    }

}
