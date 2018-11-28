package com.bignerdranch.android.weatherprogrammer.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.Weather;
import com.bignerdranch.android.weatherprogrammer.activity.detail.DetailActivity;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.OpenWeatherMapForecast;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapForecastList;
import com.bignerdranch.android.weatherprogrammer.openweathermap.util.OpenWeatherMapRequestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherListFragment extends Fragment {

    private static final String TAG = "WeatherListFragment";
    /**
     * 是否为双页模式
     */
    private boolean isDoublePage;

    /**
     * 列表适配器
     */
    private OpenWeatherMapForecastListAdapter adapter = null;
    /**
     * 主页数据
     */
    private List<OpenWeatherMapForecastList> data = new ArrayList<>();

    /**
     * 下拉刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);

        ListView lv = view.findViewById(R.id.lv_list);
        adapter = new OpenWeatherMapForecastListAdapter(getContext(),data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailActivity.start(getActivity(), data.get(position));
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshList();
    }

    /**
     * 刷新列表数据
     */
    public void refreshList(){
        if (!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        Map<String,String> params = new HashMap<>();
        params.put("id","524901");
        params.put("units","metric");
        OpenWeatherMapRequestUtil.openWeatherMapRequest(OpenWeatherMapRequestUtil.OpenWeatherMapRequestType.FORECAST, params,
                OpenWeatherMapForecast.class, new Response.Listener<OpenWeatherMapForecast>() {
                    @Override
                    public void onResponse(OpenWeatherMapForecast response) {
                        data.clear();
                        data.addAll(response.getList());
                        adapter.notifyDataSetChanged();
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        Toast.makeText(getContext(), "数据加载失败"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isDoublePage = getActivity().findViewById(R.id.content_layout) != null;
    }

}
