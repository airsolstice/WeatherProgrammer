package com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base;

import java.io.Serializable;

public class OpenWeatherMapWind  implements Serializable {
    /**
     * speed : 5.71
     * deg : 229.501
     */

    private String speed;
    private String deg;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }
}