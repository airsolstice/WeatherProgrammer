package com.bignerdranch.android.weatherprogrammer.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapCity;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapForecastList;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapWeather;

import java.io.File;

public class DetailActivity extends AppCompatActivity {

    private static final  String WEATHER_KEY = "weather";
    private static final  String CITY_KEY = "city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        OpenWeatherMapForecastList weather = (OpenWeatherMapForecastList) intent.getSerializableExtra(WEATHER_KEY);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_detail);
        ((WeatherDetailFragment) fragment).refresh(weather);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_detail_menu, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                shareMsg("weather","weather detail",getWeatherDesc(),null);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 获取天气详细信息描述
     */
    public String getWeatherDesc(){
        Intent activityIntent = getIntent();
        OpenWeatherMapCity city = (OpenWeatherMapCity) activityIntent.getSerializableExtra(CITY_KEY);
        OpenWeatherMapForecastList weather = (OpenWeatherMapForecastList) activityIntent.getSerializableExtra(WEATHER_KEY);
        OpenWeatherMapWeather openWeatherMapWeather = weather.getWeather().get(0);
        return weather.getDt_txt() + "\ncity:" + city.getName() +
                "\nweather:" + openWeatherMapWeather.getDescription() +
                "\nmax temp:" + weather.getMain().getTempMax() +
                "\nmin temp:" + weather.getMain().getTempMin() +
                "\nhumidity:" + weather.getMain().getHumidity()+ "％" +
                "\npressure:" + weather.getMain().getPressure() +"hPa"+
                "\nsea_level:" + weather.getMain().getSeaLevel() + "hPa" +
                "\ngrnd_level:" + weather.getMain().getGrndLevel() + "hPa";
    }


    public static void start(Context context, OpenWeatherMapCity city,OpenWeatherMapForecastList weather) {
        Intent intent = new Intent();
        intent.setClass(context, DetailActivity.class);
        intent.putExtra(WEATHER_KEY, weather);
        intent.putExtra(CITY_KEY, city);
        context.startActivity(intent);
    }

    public void shareMsg(String activityTitle, String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }

}
