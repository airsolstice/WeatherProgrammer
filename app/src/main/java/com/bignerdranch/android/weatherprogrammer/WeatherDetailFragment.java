package com.bignerdranch.android.weatherprogrammer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherDetailFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        return view;
    }

    public void refresh(Weather weather) {
        TextView tv = view.findViewById(R.id.txt);
        tv.setText(weather.toString());
    }

}
