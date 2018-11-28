package com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class OpenWeatherMapRain implements Serializable {
    /**
     * 3h : 3
     */

    @JSONField(name = "3h")
    private String _$3h;

    public String get_$3h() {
        return _$3h;
    }

    public void set_$3h(String _$3h) {
        this._$3h = _$3h;
    }
}