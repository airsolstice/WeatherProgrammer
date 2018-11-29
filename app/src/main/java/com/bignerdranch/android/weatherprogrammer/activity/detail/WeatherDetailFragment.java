package com.bignerdranch.android.weatherprogrammer.activity.detail;

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
import com.bignerdranch.android.weatherprogrammer.openweathermap.util.OpenWeatherMapRequestUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDetailFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        return view;
    }

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
        TextView tempMax = view.findViewById(R.id.temp_max);
        tempMax.setText(weather.getMain().getTempMax()+"°");
        TextView tempMin = view.findViewById(R.id.temp_min);
        tempMin.setText(weather.getMain().getTempMin()+"°");

        TextView main = view.findViewById(R.id.main);
        OpenWeatherMapWeather openWeatherMapWeather = weather.getWeather().get(0);
        main.setText(openWeatherMapWeather.getMain());


        TextView humidity = view.findViewById(R.id.humidity);
        humidity.setText("Humidity: "+weather.getMain().getHumidity() + "%");
        TextView pressure = view.findViewById(R.id.pressure);
        pressure.setText("Pressure: "+weather.getMain().getPressure() + "hPa");

        TextView wind = view.findViewById(R.id.wind);
        wind.setText("Wind: " + weather.getWind().getSpeed() + " km/h SE");



        final ImageView icon = view.findViewById(R.id.icon);
        ImageRequest imageRequest = new ImageRequest(OpenWeatherMapRequestUtil.OPEN_WEATHER_MAP_ICON_URL + openWeatherMapWeather.getIcon() + ".png", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                icon.setImageBitmap(response);
            }
        }, 200, 200, ImageView.ScaleType.CENTER_INSIDE, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                icon.setImageResource(R.mipmap.ic_launcher);
            }
        });
        WeatherApplication.getHttpQueues().add(imageRequest);

    }

}
