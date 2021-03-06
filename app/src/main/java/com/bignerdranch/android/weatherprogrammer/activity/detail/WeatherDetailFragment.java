package com.bignerdranch.android.weatherprogrammer.activity.detail;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.WeatherApplication;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapForecastList;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapWeather;
import com.bignerdranch.android.weatherprogrammer.openweathermap.util.OpenWeatherMapParamsUtil;
import com.bignerdranch.android.weatherprogrammer.openweathermap.util.OpenWeatherMapRequestUtil;
import com.bignerdranch.android.weatherprogrammer.util.OwnUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDetailFragment extends Fragment {

    /**
     * fragment视图
     */
    private View view;
    /**
     * 持久化存储引用
     */
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sp = getActivity().getSharedPreferences(WeatherApplication.FILE_LOCATION_OPTION, 0);
        view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        return view;
    }

    /**
     * 刷新内容
     */
    public void refresh(OpenWeatherMapForecastList weather) {
        TextView time = view.findViewById(R.id.time);
        String timeStr = weather.getDt_txt();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr);
            timeStr = new SimpleDateFormat("\nE, MMM dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time.setText(timeStr);

        String openWeatherMapUnit = sp.getString(WeatherApplication.KEY_OPEN_WATHER_MAP_UNIT, WeatherApplication.DEFINE_TOPEN_WATHER_MAP_UNIT);
        String tempUnit = OpenWeatherMapParamsUtil.tempUnitMap.get(openWeatherMapUnit);

        TextView tempMax = view.findViewById(R.id.temp_max);
        tempMax.setText(weather.getMain().getTempMax()+ tempUnit);
        TextView tempMin = view.findViewById(R.id.temp_min);
        tempMin.setText(weather.getMain().getTempMin()+ tempUnit);

        TextView main = view.findViewById(R.id.main);
        OpenWeatherMapWeather openWeatherMapWeather = weather.getWeather().get(0);
        main.setText(openWeatherMapWeather.getMain());


        TextView humidity = view.findViewById(R.id.humidity);
        humidity.setText("Humidity: "+weather.getMain().getHumidity() + OpenWeatherMapParamsUtil.HUMIDITY_UNIT);
        TextView pressure = view.findViewById(R.id.pressure);
        pressure.setText("Pressure: "+weather.getMain().getPressure() + OpenWeatherMapParamsUtil.PRESSURE_UNIT);

        TextView wind = view.findViewById(R.id.wind);

        wind.setText("Wind: " + weather.getWind().getSpeed() + " "+OpenWeatherMapParamsUtil.speedUnitMap.get(openWeatherMapUnit)+" "
                + OwnUtil.changeAngleToDirection(weather.getWind().getDeg()));

        final ImageView icon = view.findViewById(R.id.icon);
        ImageRequest imageRequest = new ImageRequest(OpenWeatherMapRequestUtil.OPEN_WEATHER_MAP_ICON_URL + openWeatherMapWeather.getIcon() + ".png", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                icon.setImageBitmap(response);
            }
        }, 200, 200, ImageView.ScaleType.CENTER_INSIDE, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                icon.setImageResource(R.mipmap.icon_error);
            }
        });
        WeatherApplication.getHttpQueues().add(imageRequest);
    }
}
