package com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base;

import java.io.Serializable;

public class OpenWeatherMapCoord  implements Serializable {
    /**
     * lon : 138.933334
     * lat : 34.966671
     */

    private String lon;
    private String lat;

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}