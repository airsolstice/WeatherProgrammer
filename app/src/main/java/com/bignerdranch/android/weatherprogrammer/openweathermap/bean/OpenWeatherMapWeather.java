package com.bignerdranch.android.weatherprogrammer.openweathermap.bean;

import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapClouds;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapCoord;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapMain;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapRain;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapSys;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapWind;

import java.io.Serializable;
import java.util.List;

/**
 * @Package com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base
 * @Description Current weather data
 * @date 2018/11/28
 */
public class OpenWeatherMapWeather implements Serializable {

    /**
     * coord
     * coord.lon 城市地理位置，经度
     * coord.lat 城市地理位置，纬度
     * weather （更多信息天气状况代码）
     * weather.id 天气状况id
     * weather.main 一组天气参数（雨，雪，极端等）
     * weather.description 集团内的天气状况
     * weather.icon 天气图标ID
     * base 内部参数
     * main
     * main.temp温度。单位默认值：开尔文，公制：摄氏度，英制：华氏度。
     * main.pressure 大气压力（在海平面，如果没有sea_level或grnd_level数据），hPa
     * main.humidity 湿度，％
     * main.temp_min目前的最低温度。这是大城市和地理位置扩展的特大城市可能的当前温度的偏差（可选地使用这些参数）。单位默认值：开尔文，公制：摄氏度，英制：华氏度。
     * main.temp_max目前的最高温度。这是大城市和地理位置扩展的特大城市可能的当前温度的偏差（可选地使用这些参数）。单位默认值：开尔文，公制：摄氏度，英制：华氏度。
     * main.sea_level 海平面的大气压力，hPa
     * main.grnd_level 地面大气压力，hPa
     * wind
     * wind.speed风速。单位默认值：米/秒，公制：米/秒，英制：英里/小时。
     * wind.deg 风向，度（气象）
     * clouds
     * clouds.all 云量，％
     * rain
     * rain.1h 最近1小时的降雨量
     * rain.3h 最近3个小时的降雨量
     * snow
     * snow.1h 雪量为最后1小时
     * snow.3h 过去3个小时的雪量
     * dt 数据计算时间，unix，UTC
     * sys
     * sys.type 内部参数
     * sys.id 内部参数
     * sys.message 内部参数
     * sys.country 国家代码（GB，JP等）
     * sys.sunrise 日出时间，unix，UTC
     * sys.sunset 日落时间，unix，UTC
     * id 城市ID
     * name 城市名称
     * cod 内部参数
     */

    private OpenWeatherMapCoord coord;
    private String base;
    private OpenWeatherMapMain main;
    private OpenWeatherMapWind wind;
    private OpenWeatherMapClouds clouds;
    private OpenWeatherMapRain rain;
    private String dt;
    private OpenWeatherMapSys sys;
    private String id;
    private String name;
    private String cod;
    private List<com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapWeather> weather;

    public OpenWeatherMapCoord getCoord() {
        return coord;
    }

    public void setCoord(OpenWeatherMapCoord coord) {
        this.coord = coord;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public OpenWeatherMapMain getMain() {
        return main;
    }

    public void setMain(OpenWeatherMapMain main) {
        this.main = main;
    }

    public OpenWeatherMapWind getWind() {
        return wind;
    }

    public void setWind(OpenWeatherMapWind wind) {
        this.wind = wind;
    }

    public OpenWeatherMapClouds getClouds() {
        return clouds;
    }

    public void setClouds(OpenWeatherMapClouds clouds) {
        this.clouds = clouds;
    }

    public OpenWeatherMapRain getRain() {
        return rain;
    }

    public void setRain(OpenWeatherMapRain rain) {
        this.rain = rain;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public OpenWeatherMapSys getSys() {
        return sys;
    }

    public void setSys(OpenWeatherMapSys sys) {
        this.sys = sys;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public List<com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapWeather> weather) {
        this.weather = weather;
    }
}
