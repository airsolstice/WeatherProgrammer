package com.bignerdranch.android.weatherprogrammer.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.WeatherApplication;

public class SettingsActivity extends AppCompatActivity {

    private TextView locationTextView ;
    private TextView utilsTextView ;
    private TextView notificationsTextView ;
    private SharedPreferences sp;

    private String cityName;
    private String temperatureUtils;
    private boolean notification;

    @Override
    protected void onRestart() {
        super.onRestart();
        if(locationTextView != null){
            cityName = sp.getString(WeatherApplication.KEY_CITY_NAME, WeatherApplication.DEFINE_CITY_NAME);
            locationTextView.setText(cityName);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sp = getSharedPreferences(WeatherApplication.FILE_LOCATION_OPTION, 0);
        cityName = sp.getString(WeatherApplication.KEY_CITY_NAME, WeatherApplication.DEFINE_CITY_NAME);
        temperatureUtils = sp.getString(WeatherApplication.KEY_TEMPERATURE_UTILS, WeatherApplication.DEFINE_TEMPERATURE_UTILS);
        //notification = sp.getBoolean(WeatherApplication.KEY_NOTIFICATIONS , WeatherApplication.DEFINE_NOTIFICATION);

        locationTextView = findViewById(R.id.tv_location);
        locationTextView.setText(cityName);

        utilsTextView = findViewById(R.id.tv_utils);
        utilsTextView.setText(temperatureUtils);

        notificationsTextView = findViewById(R.id.tv_notifications);
        notificationsTextView.setText(notification? "Enable" : "Disable");
        final Intent intent = new Intent(this, LocationOptionActivity.class);

        findViewById(R.id.btn_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        final String[] items = new String[] { "北京", "上海", "广州", "深圳" };
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher).setTitle("Utils")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = sp.edit();
                        String utils = items[which];
                        editor.putString(WeatherApplication.KEY_TEMPERATURE_UTILS, utils);
                        utilsTextView.setText(utils);
                        editor.commit();
                    }
                });

        findViewById(R.id.btn_utils).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.create().show();
            }
        });

        findViewById(R.id.btn_notifications).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
