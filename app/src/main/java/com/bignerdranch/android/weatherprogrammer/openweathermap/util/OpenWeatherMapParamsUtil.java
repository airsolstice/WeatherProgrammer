package com.bignerdranch.android.weatherprogrammer.openweathermap.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.bignerdranch.android.weatherprogrammer.openweathermap.util
 * @Description openWeatherMap常量工具类
 * @date 2018/11/30
 */
public class OpenWeatherMapParamsUtil {

    /**
     * 单位类型
     */
    public enum OpenWeatherMapUnit{
        STANDARD("standard"),//标准
        METRIC("metric"),//公制
        IMPERIAL("imperial");//英制

        private String value;

        OpenWeatherMapUnit(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 单位请求拼接
     */
    public static Map<String,String> urlParamUnitMap = new HashMap<>();

    /**
     * 温度单位
     */
    public static Map<String,String> tempUnitMap = new HashMap<>();

    /**
     * 速度单位
     */
    public static Map<String,String> speedUnitMap = new HashMap<>();

    /**
     * 湿度 单位
     */
    public static final String HUMIDITY_UNIT = "%";

    /**
     * 气压 单位
     */
    public static final String PRESSURE_UNIT = "hPa";

    static {
        //单位请求参数
        urlParamUnitMap.put(OpenWeatherMapUnit.STANDARD.getValue(),"");
        urlParamUnitMap.put(OpenWeatherMapUnit.METRIC.getValue(),"metric");
        urlParamUnitMap.put(OpenWeatherMapUnit.IMPERIAL.getValue(),"imperial");

        //温度单位
        tempUnitMap.put(OpenWeatherMapUnit.STANDARD.getValue(),"°K");//Kelvin
        tempUnitMap.put(OpenWeatherMapUnit.METRIC.getValue(),"°C");//Celsius 摄氏度
        tempUnitMap.put(OpenWeatherMapUnit.IMPERIAL.getValue(),"°F");//Fahrenheit 华氏度

        //速度单位
        speedUnitMap.put(OpenWeatherMapUnit.STANDARD.getValue(),"m/s");
        speedUnitMap.put(OpenWeatherMapUnit.METRIC.getValue(),"m/s");
        speedUnitMap.put(OpenWeatherMapUnit.IMPERIAL.getValue(),"mile/h");

    }

    private OpenWeatherMapParamsUtil(){}

}
