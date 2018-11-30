package com.bignerdranch.android.weatherprogrammer.activity.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.activity.SettingsActivity;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.OpenWeatherMapWeather;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapCoord;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.my_location:
                WeatherListFragment weatherListFragment = (WeatherListFragment) getSupportFragmentManager().findFragmentById(R.id.frag_list);
                OpenWeatherMapWeather currentWeather = weatherListFragment.getCurrentWeather();
                if (null != currentWeather){
                    OpenWeatherMapCoord coord = currentWeather.getCoord();
                    Uri mUri = Uri.parse("geo:"+coord.getLat()+","+coord.getLon());
                    Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
                    startActivity(mIntent);
                }else{
                    Toast.makeText(this, "没有获取到位置", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.option_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

