package com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base;

import java.io.Serializable;

public class OpenWeatherMapCity  implements Serializable {

    /**
     * city.id 城市ID
     * city.name 城市名称
     * city.coord
     * city.coord.lat 城市地理位置，纬度
     * city.coord.lon 城市地理位置，经度
     * city.country 国家代码（GB，JP等）
     */

    private String id;
    private String name;

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
}