package com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base;

import java.io.Serializable;
import java.util.List;

public class OpenWeatherMapForecastList implements Serializable {
    /**
     * dt : 1406106000
     * main : {"temp":298.77,"temp_min":298.77,"temp_max":298.774,"pressure":1005.93,"sea_level":1018.18,"grnd_level":1005.93,"humidity":87,"temp_kf":0.26}
     * weather : [{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}]
     * clouds : {"all":88}
     * wind : {"speed":5.71,"deg":229.501}
     * sys : {"pod":"d"}
     * dt_txt : 2014-07-23 09:00:00
     */

    private String dt;
    private OpenWeatherMapMain main;
    private OpenWeatherMapClouds clouds;
    private OpenWeatherMapWind wind;
    private OpenWeatherMapSys sys;
    private String dt_txt;
    private List<OpenWeatherMapWeather> weather;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public OpenWeatherMapMain getMain() {
        return main;
    }

    public void setMain(OpenWeatherMapMain main) {
        this.main = main;
    }

    public OpenWeatherMapClouds getClouds() {
        return clouds;
    }

    public void setClouds(OpenWeatherMapClouds clouds) {
        this.clouds = clouds;
    }

    public OpenWeatherMapWind getWind() {
        return wind;
    }

    public void setWind(OpenWeatherMapWind wind) {
        this.wind = wind;
    }

    public OpenWeatherMapSys getSys() {
        return sys;
    }

    public void setSys(OpenWeatherMapSys sys) {
        this.sys = sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public List<OpenWeatherMapWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<OpenWeatherMapWeather> weather) {
        this.weather = weather;
    }


}