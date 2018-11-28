package com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class OpenWeatherMapMain  implements Serializable {
    /**
     * temp : 298.77
     * temp_min : 298.77
     * temp_max : 298.774
     * pressure : 1005.93
     * sea_level : 1018.18
     * grnd_level : 1005.93
     * humidity : 87
     * temp_kf : 0.26
     */

    /**
     * main.temp温度。单位默认值：开尔文，公制：摄氏度，英制：华氏度。
     * main.temp_min计算时的最低温度。这是对大城市和地理位置扩展的特大城市可能的“临时”的偏差（可选地使用这些参数）。单位默认值：开尔文，公制：摄氏度，英制：华氏度。
     * main.temp_max计算时的最高温度。这是对大城市和地理位置扩展的特大城市可能的“临时”的偏差（可选地使用这些参数）。单位默认值：开尔文，公制：摄氏度，英制：华氏度。
     * main.pressure 海平面上的大气压力默认为hPa
     * main.sea_level 海平面的大气压力，hPa
     * main.grnd_level 地面大气压力，hPa
     * main.humidity 湿度，％
     * main.temp_kf 内部参数
     */

    private String temp;
    @JSONField(name = "temp_min")
    private String tempMin;
    @JSONField(name = "temp_max")
    private String tempMax;
    private String pressure;
    @JSONField(name = "sea_level")
    private String seaLevel;
    @JSONField(name = "grnd_level")
    private String grndLevel;
    private String humidity;
    @JSONField(name = "temp_kf")
    private String tempKf;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(String seaLevel) {
        this.seaLevel = seaLevel;
    }

    public String getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(String grndLevel) {
        this.grndLevel = grndLevel;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTempKf() {
        return tempKf;
    }

    public void setTempKf(String tempKf) {
        this.tempKf = tempKf;
    }
}
