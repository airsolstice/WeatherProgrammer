package com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base;

import java.io.Serializable;

public class OpenWeatherMapSys  implements Serializable {
    /**
     * pod : d
     */

    private String pod;

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }
}