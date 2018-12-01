package com.bignerdranch.android.weatherprogrammer.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.WeatherApplication;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.OpenWeatherMapWeather;
import com.bignerdranch.android.weatherprogrammer.openweathermap.util.OpenWeatherMapRequestUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Package com.bignerdranch.android.weatherprogrammer.service
 * @Description 天气通知service
 * @date 2018/12/1
 */
public class WeatherNotifyService extends Service {

    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences sp = getSharedPreferences(WeatherApplication.FILE_LOCATION_OPTION, 0);
        boolean notification = sp.getBoolean(WeatherApplication.KEY_NOTIFICATIONS , WeatherApplication.DEFINE_NOTIFICATION);
        String cityId = sp.getString(WeatherApplication.KEY_CITY_ID, WeatherApplication.DEFINE_CITY_ID);
        String openWeatherMapUnit = sp.getString(WeatherApplication.KEY_OPEN_WATHER_MAP_UNIT, WeatherApplication.DEFINE_TOPEN_WATHER_MAP_UNIT);
        closeNotification();//无论如何，先关闭通知
        if (notification){
            startNotification(cityId,openWeatherMapUnit);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 关闭通知
     */
    public void closeNotification(){
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
        if (null != timer){
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 开启通知
     */
    public void startNotification(final String cityId,final String openWeatherMapUnit){
        long period = 10 * 60 * 1000; // 10分钟一个周期
        if (null == timer) {
            timer = new Timer();
        }
        final Context context = this;
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Map<String, String> params = new HashMap<>();

                params.put("id", cityId);
                params.put("units", openWeatherMapUnit);
                OpenWeatherMapRequestUtil.openWeatherMapRequest(OpenWeatherMapRequestUtil.OpenWeatherMapRequestType.WEATHER, params,
                        OpenWeatherMapWeather.class, new Response.Listener<OpenWeatherMapWeather>() {
                            @Override
                            public void onResponse(OpenWeatherMapWeather response) {
                                com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapWeather openWeatherMapWeather = response.getWeather().get(0);
                                final NotificationManager mn = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
                                final Notification.Builder builder = new Notification.Builder(context);
                                builder.setSmallIcon(R.mipmap.ic_launcher);
                                // 设置通知图标
                                builder.setTicker("weather"); // 测试通知栏标题
                                builder.setContentText(openWeatherMapWeather.getMain()); // 下拉通知啦内容
                                builder.setContentTitle("标题");// 下拉通知栏标题
                                builder.setAutoCancel(true);// 点击弹出的通知后,让通知将自动取消
                                builder.setVibrate(new long[] { 0, 2000, 1000, 4000 }); // 震动需要真机测试-延迟0秒震动2秒延迟1秒震动4秒
                                // builder.setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI,
                                // "5"));//获取Android多媒体库内的铃声
                                // builder.setSound(Uri.parse("file:///sdcard/xx/xx.mp3"))
                                // ;//自定义铃声
                                builder.setDefaults(Notification.DEFAULT_ALL);// 设置使用系统默认声音
                                // builder.addAction("图标", title, intent); //此处可设置点击后 打开某个页面
                                ImageRequest imageRequest = new ImageRequest(OpenWeatherMapRequestUtil.OPEN_WEATHER_MAP_ICON_URL + openWeatherMapWeather.getIcon() + ".png", new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap response) {
                                        builder.setLargeIcon(response);
                                        Notification notification = builder.build();
                                        notification.flags = Notification.FLAG_INSISTENT;// 声音无限循环
                                        mn.notify((int) System.currentTimeMillis(), notification);
                                    }
                                }, 200, 200, ImageView.ScaleType.CENTER_INSIDE, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });

                                WeatherApplication.getHttpQueues().add(imageRequest);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });

            }
        }, 0, period);
    }

}
