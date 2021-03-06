package com.bignerdranch.android.weatherprogrammer.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.WeatherApplication;
import com.bignerdranch.android.weatherprogrammer.activity.detail.DetailActivity;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.OpenWeatherMapForecast;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.OpenWeatherMapWeather;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapForecastList;
import com.bignerdranch.android.weatherprogrammer.openweathermap.util.OpenWeatherMapParamsUtil;
import com.bignerdranch.android.weatherprogrammer.openweathermap.util.OpenWeatherMapRequestUtil;
import com.bignerdranch.android.weatherprogrammer.service.WeatherNotifyService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WeatherListFragment extends Fragment {

    private static final String TAG = "WeatherListFragment";
    /**
     * 是否为双页模式
     */
    private boolean isDoublePage;
    /**
     * 显示时间
     */
    private TextView timeTextView;
    /**
     * 显示最高温度
     */
    private TextView maxTemperatureTextView;
    /**
     * 显示最低温度
     */
    private TextView minTemperatureTextView;
    /**
     * 天气图标
     */
    private ImageView weatherIconImageView;
    /**
     * 天气状态
     */
    private TextView weatherStatusTextView;
    /**
     * 列表适配器
     */
    private WeatherListAdapter adapter = null;
    /**
     * 当前数据
     */
    private OpenWeatherMapWeather currentWeather;
    /**
     * 列表查询结果
     */
    private OpenWeatherMapForecast forecast;
    /**
     * 主页数据
     */
    private List<OpenWeatherMapForecastList> data = new ArrayList<>();
    /**
     * 下拉刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;
    /**
     * 多个控件空间使用同一加载控件，保存在加载中的而数据
     */
    private AtomicInteger loadingCount = new AtomicInteger(0);
    /**
     * 持久化存储引用
     */
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);

        ListView lv = view.findViewById(R.id.lv_list);
        sp = getActivity().getSharedPreferences(WeatherApplication.FILE_LOCATION_OPTION, 0);
        adapter = new WeatherListAdapter(getContext(), data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailActivity.start(getActivity(), forecast.getCity(),data.get(position));
            }
        });
        // 设置滑动监听
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //解决swipeRefreshLayout 和嵌套在LinearLayout中的ListView滑动冲突事件
                boolean enable = false;
                if (view.getChildCount() > 0) {
                    boolean firstItemVisible = view.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = view.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCurrentData();
                refreshList();
            }
        });

        timeTextView = view.findViewById(R.id.time);
        maxTemperatureTextView = view.findViewById(R.id.temp_max);
        minTemperatureTextView = view.findViewById(R.id.temp_min);
        weatherIconImageView = view.findViewById(R.id.icon);
        weatherStatusTextView = view.findViewById(R.id.main);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 从其他页面跳回此页面时，刷新数据
        refreshCurrentData();
        refreshList();
    }

    /**
     * 获取当前数据
     */
    private void refreshCurrentData() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
        Map<String, String> params = new HashMap<>();
        String id = sp.getString(WeatherApplication.KEY_CITY_ID, WeatherApplication.DEFINE_CITY_ID);
        String openWeatherMapUnit = sp.getString(WeatherApplication.KEY_OPEN_WATHER_MAP_UNIT, WeatherApplication.DEFINE_TOPEN_WATHER_MAP_UNIT);
        final String tempUnit = OpenWeatherMapParamsUtil.tempUnitMap.get(openWeatherMapUnit);
        params.put("id", id);
        params.put("units", openWeatherMapUnit);
        OpenWeatherMapRequestUtil.openWeatherMapRequest(OpenWeatherMapRequestUtil.OpenWeatherMapRequestType.WEATHER, params,
                OpenWeatherMapWeather.class, new Response.Listener<OpenWeatherMapWeather>() {
                    @Override
                    public void onResponse(OpenWeatherMapWeather response) {
                        int i = loadingCount.decrementAndGet();
                        currentWeather = response;
                        setCurrentData(response,tempUnit);
                        if (i <= 0 && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int i = loadingCount.decrementAndGet();
                        if (i <= 0 && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        Toast.makeText(getContext(), "当前天气数据加载失败" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        loadingCount.incrementAndGet();
    }

    /**
     * 重新设置当前天气数据
     */
    private void setCurrentData(OpenWeatherMapWeather weather,String tempUnit) {
        String timeStr = new SimpleDateFormat("E,MMM dd").format(new Date());
        timeTextView.setText(timeStr);

        maxTemperatureTextView.setText(weather.getMain().getTempMax() + tempUnit);
        minTemperatureTextView.setText(weather.getMain().getTempMin() + tempUnit);
        com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapWeather openWeatherMapWeather = weather.getWeather().get(0);
        weatherStatusTextView.setText(openWeatherMapWeather.getMain());

        ImageRequest imageRequest = new ImageRequest(OpenWeatherMapRequestUtil.OPEN_WEATHER_MAP_ICON_URL + openWeatherMapWeather.getIcon() + ".png", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                weatherIconImageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                weatherIconImageView.setImageResource(R.mipmap.icon_error);
            }
        });
        WeatherApplication.getHttpQueues().add(imageRequest);
        Intent intent = new Intent(getActivity(), WeatherNotifyService.class);
        getActivity().startService(intent);
    }

    /**
     * 刷新列表数据
     */
    private void refreshList() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
        Map<String, String> params = new HashMap<>();
        String id = sp.getString(WeatherApplication.KEY_CITY_ID, WeatherApplication.DEFINE_CITY_ID);
        String openWeatherMapUnit = sp.getString(WeatherApplication.KEY_OPEN_WATHER_MAP_UNIT, WeatherApplication.DEFINE_TOPEN_WATHER_MAP_UNIT);
        final String tempUnit = OpenWeatherMapParamsUtil.tempUnitMap.get(openWeatherMapUnit);
        params.put("id", id);
        params.put("units", openWeatherMapUnit);
        OpenWeatherMapRequestUtil.openWeatherMapRequest(OpenWeatherMapRequestUtil.OpenWeatherMapRequestType.FORECAST, params,
                OpenWeatherMapForecast.class, new Response.Listener<OpenWeatherMapForecast>() {
                    @Override
                    public void onResponse(OpenWeatherMapForecast response) {
                        int i = loadingCount.decrementAndGet();
                        forecast = response;
                        data.clear();
                        data.addAll(response.getList());
                        adapter.setTempUnit(tempUnit);
                        adapter.notifyDataSetChanged();
                        if (i <= 0 && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int i = loadingCount.decrementAndGet();
                        if (i <= 0 && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        Toast.makeText(getContext(), "5天天气数据加载失败" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        loadingCount.incrementAndGet();
    }

    /**
     * 适配平板的双页面情况
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isDoublePage = getActivity().findViewById(R.id.content_layout) != null;
    }

    public OpenWeatherMapWeather getCurrentWeather(){
        return currentWeather;
    }

}
