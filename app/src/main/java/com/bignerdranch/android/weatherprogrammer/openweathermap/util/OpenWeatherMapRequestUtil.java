package com.bignerdranch.android.weatherprogrammer.openweathermap.util;

import com.android.volley.Request;
import com.android.volley.Response;
import com.bignerdranch.android.weatherprogrammer.util.VolleyRequestUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Package com.bignerdranch.android.weatherprogrammer.openweathermap.bean.openweathermap.util
 * @Description openWeatherMap网络请求工具类
 * @date 2018/11/27
 */
public class OpenWeatherMapRequestUtil {

    /**
     * openWeatherMap API KEY
     */
    public static final String openWeatherMapApiKey = "2faa3c6ed63499f6dad3ee684d1949a3";

    /**
     * openWeatherMap 接口URL前缀
     */
    public static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/";
    /**
     * openWeatherMap图标地址
     */
    public static final String OPEN_WEATHER_MAP_ICON_URL = "http://openweathermap.org/img/w/";

    /**
     * openWeatherMap请求
     * @param type 接口类型
     * @param params 参数
     * @param cls 返回数据类型
     * @param listener 成功回调
     * @param errorListener 失败回调
     * @param <T> 数据类型
     */
    public static <T> void openWeatherMapRequest(OpenWeatherMapRequestType type,Map<String,String> params,Class<T> cls,Response.Listener<T> listener,Response.ErrorListener errorListener){
        StringBuilder url = new StringBuilder(OPEN_WEATHER_MAP_URL+type.getReq()+"?APPID="+openWeatherMapApiKey);

        StringBuilder tag = new StringBuilder();
        if (null != params && params.size() > 0){
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (null != key && !"".equals(key) && null != value && !"".equals(value)) {
                    try {
                        value = URLEncoder.encode(value, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    url.append("&").append(key).append("=").append(value);
                    if (type.getTagKeys().contains(key)) {
                        tag.append(key).append("-").append(value);
                    }
                }
            }
        }
        String tagStr = tag.toString();
        if ("".equals(tagStr)){
            tagStr = "OPEN_WEATHER_MAP" + tagStr;

        }

        VolleyRequestUtil.beanRequest(Request.Method.GET,url.toString(),cls, listener,errorListener, tag.toString());
    }

    /**
     * OpenWeatherMap接口类型
     */
    public enum OpenWeatherMapRequestType{

        /**
         * Call current weather data for one location
         */
        WEATHER("weather",Arrays.asList("id","q","lat","lon")),

        /**
         * Call 5 day / 3 hour forecast data
         */
        FORECAST("forecast",Arrays.asList("id","zip","lat","lon"));;

        /**
         * 接口
         */
        private String req;

        /**
         * 关键请求字段，用于验重
         */
        private List<String> tagKeys;


        OpenWeatherMapRequestType(String req,List<String> tagKeys){
            this.req = req;
            this.tagKeys = tagKeys;
        }

        public String getReq() {
            return req;
        }

        public void setReq(String req) {
            this.req = req;
        }

        public List<String> getTagKeys() {
            return tagKeys;
        }

        public void setTagKeys(List<String> tagKeys) {
            this.tagKeys = tagKeys;
        }
    }

    private OpenWeatherMapRequestUtil(){}

}
