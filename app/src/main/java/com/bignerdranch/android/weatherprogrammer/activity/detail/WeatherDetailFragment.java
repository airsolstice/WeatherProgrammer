package com.bignerdranch.android.weatherprogrammer.activity.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapForecastList;

public class WeatherDetailFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        return view;
    }

    public void refresh(OpenWeatherMapForecastList weather) {
        TextView tv = view.findViewById(R.id.txt);
        tv.setText(weather.toString());
    }

}
