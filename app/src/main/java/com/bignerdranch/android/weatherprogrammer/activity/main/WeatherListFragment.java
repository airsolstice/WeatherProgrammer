package com.bignerdranch.android.weatherprogrammer.activity.main;

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
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapCity;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapForecastList;
import com.bignerdranch.android.weatherprogrammer.openweathermap.util.OpenWeatherMapRequestUtil;

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

    private TextView tvTime;

    private TextView tvTempMax;

    private TextView tvTempMin;

    private ImageView ivIcon;

    private TextView tvMain;

    /**
     * 列表适配器
     */
    private OpenWeatherMapForecastListAdapter adapter = null;

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

    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);

        ListView lv = view.findViewById(R.id.lv_list);
        sp = sp = getActivity().getSharedPreferences(WeatherApplication.FILE_LOCATION_OPTION, 0);
        adapter = new OpenWeatherMapForecastListAdapter(getContext(), data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailActivity.start(getActivity(), forecast.getCity(),data.get(position));
            }
        });
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

        tvTime = view.findViewById(R.id.time);
        tvTempMax = view.findViewById(R.id.temp_max);
        tvTempMin = view.findViewById(R.id.temp_min);
        ivIcon = view.findViewById(R.id.icon);
        tvMain = view.findViewById(R.id.main);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshCurrentData();
        refreshList();
    }

    private void refreshCurrentData() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
        Map<String, String> params = new HashMap<>();
        String id = sp.getString(WeatherApplication.KEY_CITY_ID, WeatherApplication.DEFINE_CITY_ID);
        String temperatureUtils = sp.getString(WeatherApplication.KEY_TEMPERATURE_UTILS, WeatherApplication.DEFINE_TEMPERATURE_UTILS);
        params.put("id", id);
        params.put("units", temperatureUtils);
        OpenWeatherMapRequestUtil.openWeatherMapRequest(OpenWeatherMapRequestUtil.OpenWeatherMapRequestType.WEATHER, params,
                OpenWeatherMapWeather.class, new Response.Listener<OpenWeatherMapWeather>() {
                    @Override
                    public void onResponse(OpenWeatherMapWeather response) {
                        int i = loadingCount.decrementAndGet();
                        setCurrentData(response);
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
     *
     * @param weather
     */
    private void setCurrentData(OpenWeatherMapWeather weather) {
        String timeStr = new SimpleDateFormat("E,MMM dd").format(new Date());
        tvTime.setText(timeStr);
        tvTempMax.setText(weather.getMain().getTempMax());
        tvTempMin.setText(weather.getMain().getTempMin());
        com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapWeather openWeatherMapWeather = weather.getWeather().get(0);
        tvMain.setText(openWeatherMapWeather.getMain());
        ImageRequest imageRequest = new ImageRequest(OpenWeatherMapRequestUtil.OPEN_WEATHER_MAP_ICON_URL + openWeatherMapWeather.getIcon() + ".png", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ivIcon.setImageBitmap(response);
            }
        }, 200, 200, ImageView.ScaleType.CENTER_INSIDE, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ivIcon.setImageResource(R.mipmap.icon_error);
            }
        });
        WeatherApplication.getHttpQueues().add(imageRequest);
    }

    /**
     * 刷新列表数据
     */
    private void refreshList() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
        Map<String, String> params = new HashMap<>();
        params.put("id", "1815286");
        params.put("units", "metric");
        OpenWeatherMapRequestUtil.openWeatherMapRequest(OpenWeatherMapRequestUtil.OpenWeatherMapRequestType.FORECAST, params,
                OpenWeatherMapForecast.class, new Response.Listener<OpenWeatherMapForecast>() {
                    @Override
                    public void onResponse(OpenWeatherMapForecast response) {
                        int i = loadingCount.decrementAndGet();
                        forecast = response;
                        data.clear();
                        data.addAll(response.getList());
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isDoublePage = getActivity().findViewById(R.id.content_layout) != null;
    }

}
