package com.bignerdranch.android.weatherprogrammer.util;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.bignerdranch.android.weatherprogrammer.WeatherApplication;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Package com.bignerdranch.android.weatherprogrammer.util
 * @Description volley网络请求工具类
 * @date 2018/11/27
 */
public class VolleyRequestUtil {

    private RequestQueue mRequestQueue = WeatherApplication.requestQueue;

    /**
     *
     * @param method 请求方法
     * @param url 请求地址
     * @param cls bean class
     * @param listener 成功回调
     * @param errorListener 失败回调
     * @param tag 验重标记
     * @param <T> bean class
     */
    public static <T> void beanRequest(int method, String url,final Class<T> cls, Response.Listener<T> listener, Response.ErrorListener errorListener, Object tag){
        beanRequest(method,url,null,null,null,cls,listener,errorListener,tag);
    }

    /**
     * 返回JSON数据
     * @param method 请求方法
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求参数
     * @param cls bean class
     * @param listener 成功回调
     * @param errorListener 失败回调
     * @param tag 标记，防止重复请求
     * @param <T> bean class
     */
    public static <T> void beanRequest(int method, String url, final String requestBody,
                                       final Map<String,String> headers,
                                       final Map<String,String> params,
                                       final Class<T> cls,
                                       Response.Listener<T> listener,
                                       Response.ErrorListener errorListener,
                                       Object tag){
        //防止重复请求，所以先取消tag标识的请求队列
        if (null != tag){
            WeatherApplication.getHttpQueues().cancelAll(tag);
        }
        JsonRequest<T> jsonRequest = new JsonRequest<T>(method, url, requestBody, listener, errorListener) {
            @Override
            protected Response<T> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                return Response.success(JSONObject.parseObject(parsed, cls), HttpHeaderParser.parseCacheHeaders(response));
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (null != params){
                    return params;
                }
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (null != headers){
                    return headers;
                }
                return super.getHeaders();
            }
        };

        if (null != tag){
            jsonRequest.setTag(tag);
        }
        WeatherApplication.getHttpQueues().add(jsonRequest);
    }

    private VolleyRequestUtil(){}

}
