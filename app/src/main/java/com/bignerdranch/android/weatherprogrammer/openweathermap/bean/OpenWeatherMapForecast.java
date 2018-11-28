package com.bignerdranch.android.weatherprogrammer.openweathermap.bean;

import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapCity;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapCoord;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapForecastList;

import java.io.Serializable;
import java.util.List;

/**
 * @Package com.bignerdranch.android.weatherprogrammer.openweathermap.bean
 * @Description 5 day weather forecast
 * @date 2018/11/28
 */
public class OpenWeatherMapForecast  implements Serializable {

    /**
     * code 内部参数
     * message 内部参数
     * city
     * city.id 城市ID
     * city.name 城市名称
     * city.coord
     * city.coord.lat 城市地理位置，纬度
     * city.coord.lon 城市地理位置，经度
     * city.country 国家代码（GB，JP等）
     * cnt 此API调用返回的行数
     * list
     * list.dt 预测数据的时间，unix，UTC
     * list.main
     * list.main.temp温度。单位默认值：开尔文，公制：摄氏度，英制：华氏度。
     * list.main.temp_min计算时的最低温度。这是对大城市和地理位置扩展的特大城市可能的“临时”的偏差（可选地使用这些参数）。单位默认值：开尔文，公制：摄氏度，英制：华氏度。
     * list.main.temp_max计算时的最高温度。这是对大城市和地理位置扩展的特大城市可能的“临时”的偏差（可选地使用这些参数）。单位默认值：开尔文，公制：摄氏度，英制：华氏度。
     * list.main.pressure 海平面上的大气压力默认为hPa
     * list.main.sea_level 海平面的大气压力，hPa
     * list.main.grnd_level 地面大气压力，hPa
     * list.main.humidity 湿度，％
     * list.main.temp_kf 内部参数
     * list.weather （更多信息天气状况代码）
     * list.weather.id 天气状况id
     * list.weather.main 一组天气参数（雨，雪，极端等）
     * list.weather.description 集团内的天气状况
     * list.weather.icon 天气图标ID
     * list.clouds
     * list.clouds.all 云量，％
     * list.wind
     * list.wind.speed风速。单位默认值：米/秒，公制：米/秒，英制：英里/小时。
     * list.wind.deg 风向，度（气象）
     * list.rain
     * list.rain.3h 降雨量持续3小时，mm
     * list.snow
     * list.snow.3h 雪量最近3小时
     * list.dt_txt 数据/计算时间，UTC
     */

    private OpenWeatherMapCity city;
    private OpenWeatherMapCoord coord;
    private String country;
    private String cod;
    private String message;
    private String cnt;
    private List<OpenWeatherMapForecastList> list;

    public OpenWeatherMapCity getCity() {
        return city;
    }

    public void setCity(OpenWeatherMapCity city) {
        this.city = city;
    }

    public OpenWeatherMapCoord getCoord() {
        return coord;
    }

    public void setCoord(OpenWeatherMapCoord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public List<OpenWeatherMapForecastList> getList() {
        return list;
    }

    public void setList(List<OpenWeatherMapForecastList> list) {
        this.list = list;
    }
}
