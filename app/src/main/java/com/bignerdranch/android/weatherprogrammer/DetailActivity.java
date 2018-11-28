package com.bignerdranch.android.weatherprogrammer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    private static final  String WEATHER_KEY = "weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Weather weather = (Weather) intent.getSerializableExtra(WEATHER_KEY);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_detail);
        ((WeatherDetailFragment) fragment).refresh(weather);
    }

    public static void start(Context context, Weather weather) {
        Intent intent = new Intent();
        intent.setClass(context, DetailActivity.class);
        intent.putExtra(WEATHER_KEY, weather);
        context.startActivity(intent);
    }

}
