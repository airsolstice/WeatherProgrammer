package com.bignerdranch.android.weatherprogrammer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class WeatherListFragment extends Fragment {

    private static final String TAG = "WeatherListFragment";
    /**
     * 是否为双页模式
     */
    private boolean isDoublePage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);

        ListView lv = view.findViewById(R.id.lv_list);
        final List<Weather> data = getData();
        WeatherListAdapter adapter = new WeatherListAdapter(data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailActivity.start(getActivity(), data.get(position));
            }
        });

        return view;
    }

    private List<Weather> getData() {
        List<Weather> data = new ArrayList<>();
        for(int i = 10; i < 10; i++){
            Weather weather = new Weather();
            weather.setDate("June 1");
            weather.setWeek("Friday");
            weather.setHighersTemperature("30");
            weather.setLowestTemperature("15");
            weather.setStatus("Sunny");
            weather.setIcon(R.drawable.ic_launcher_background);
            data.add(weather);
        }
        return data;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isDoublePage = getActivity().findViewById(R.id.content_layout) != null;
    }

}
