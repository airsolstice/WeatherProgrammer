package com.bignerdranch.android.weatherprogrammer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.WeatherProgrammerApplication;

public class SettingsActivity extends AppCompatActivity {

    private TextView locationTextView ;
    private TextView utilsTextView ;
    private TextView notificationsTextView ;
    private SharedPreferences sp;

    private String cityName;
    private int utils;
    private boolean notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sp = getSharedPreferences(WeatherProgrammerApplication.FILE_LOCATION_OPTION, 0);
        cityName = sp.getString(WeatherProgrammerApplication.KEY_CITY_NAME, WeatherProgrammerApplication.DEFINE_CITY_NAME);
        utils = sp.getInt(WeatherProgrammerApplication.KEY_CITY_NAME, WeatherProgrammerApplication.DEFINE_TEMPERATURE_UTILS);
        notification = sp.getBoolean(WeatherProgrammerApplication.KEY_CITY_NAME, WeatherProgrammerApplication.DEFINE_NOTIFICATION);

        locationTextView = findViewById(R.id.tv_location);
        locationTextView.setText(cityName);

        utilsTextView = findViewById(R.id.tv_utils);
        utilsTextView.setText("" + utils);

        notificationsTextView = findViewById(R.id.tv_notifications);
        notificationsTextView.setText(notification? "Enable" : "Disable");

        findViewById(R.id.btn_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocationOptionActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_utils).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btn_notifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
