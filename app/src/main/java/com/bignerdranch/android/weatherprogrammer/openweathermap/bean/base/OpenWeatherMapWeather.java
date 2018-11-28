package com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base;

import java.io.Serializable;

public class OpenWeatherMapWeather  implements Serializable {
    /**
     * id : 804
     * main : Clouds
     * description : overcast clouds
     * icon : 04d
     */

    private String id;
    private String main;
    private String description;
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}