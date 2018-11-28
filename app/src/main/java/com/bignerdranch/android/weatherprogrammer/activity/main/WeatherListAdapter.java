package com.bignerdranch.android.weatherprogrammer.activity.main;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bignerdranch.android.weatherprogrammer.model.Weather;

import java.util.List;

public class WeatherListAdapter extends BaseAdapter {

    List<Weather> data;

    public WeatherListAdapter(List<Weather> data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
