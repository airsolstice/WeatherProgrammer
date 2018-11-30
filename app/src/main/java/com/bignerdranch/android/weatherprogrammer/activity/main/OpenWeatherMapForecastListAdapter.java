package com.bignerdranch.android.weatherprogrammer.activity.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.WeatherApplication;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapForecastList;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapWeather;
import com.bignerdranch.android.weatherprogrammer.openweathermap.util.OpenWeatherMapParamsUtil;
import com.bignerdranch.android.weatherprogrammer.openweathermap.util.OpenWeatherMapRequestUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Package com.bignerdranch.android.weatherprogrammer.activity.main
 * @Description 5天/3h天气适配器
 * @date 2018/11/28
 */
public class OpenWeatherMapForecastListAdapter extends BaseAdapter {

    private Context context;

    private List<OpenWeatherMapForecastList> data;
    private SharedPreferences sp;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat format2 = new SimpleDateFormat("E HH:mm");

    public OpenWeatherMapForecastListAdapter(Context context,List<OpenWeatherMapForecastList> data){
        this.context = context;
        this.data = data;
        sp = context.getSharedPreferences(WeatherApplication.FILE_LOCATION_OPTION, 0);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        OpenWeatherMapForecastListAdapterHolder viewHolder = null;
        if (null != convertView){
            view = convertView;
            viewHolder = (OpenWeatherMapForecastListAdapterHolder) view.getTag();
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.fragment_weather_list_item,parent,false);
            viewHolder = new OpenWeatherMapForecastListAdapterHolder();
            viewHolder.setIcon((ImageView) view.findViewById(R.id.icon));
            viewHolder.setTime((TextView) view.findViewById(R.id.time));
            viewHolder.setWeather((TextView) view.findViewById(R.id.weather));
            viewHolder.setTempMax((TextView) view.findViewById(R.id.temp_max));
            viewHolder.setTempMin((TextView) view.findViewById(R.id.temp_min));
            view.setTag(viewHolder);
        }

        OpenWeatherMapForecastList itemData = data.get(position);
        OpenWeatherMapWeather openWeatherMapWeather = itemData.getWeather().get(0);//默认有一条
        //图标
        final ImageView icon = viewHolder.getIcon();
        ImageRequest imageRequest = new ImageRequest(OpenWeatherMapRequestUtil.OPEN_WEATHER_MAP_ICON_URL + openWeatherMapWeather.getIcon() + ".png", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                icon.setImageBitmap(response);
            }
        }, 200, 200, ImageView.ScaleType.CENTER_INSIDE, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                icon.setImageResource(R.mipmap.icon_error);
            }
        });
        WeatherApplication.getHttpQueues().add(imageRequest);
        TextView time = viewHolder.getTime();
        String timeText = itemData.getDt_txt();
        try {
            Date date = format.parse(timeText);
            timeText = format2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time.setText(timeText);
        TextView weather = viewHolder.getWeather();
        weather.setText(openWeatherMapWeather.getMain());

        String openWeatherMapUnit = sp.getString(WeatherApplication.KEY_OPEN_WATHER_MAP_UNIT, WeatherApplication.DEFINE_TOPEN_WATHER_MAP_UNIT);
        String tempUnit = OpenWeatherMapParamsUtil.tempUnitMap.get(openWeatherMapUnit);

        TextView tempMax = viewHolder.getTempMax();
        tempMax.setText(itemData.getMain().getTempMax()+tempUnit);
        TextView tempMin = viewHolder.getTempMin();
        tempMin.setText(itemData.getMain().getTempMin()+tempUnit);

        return view;
    }

    class OpenWeatherMapForecastListAdapterHolder{

        private ImageView icon;

        private TextView time;

        private TextView weather;

        private TextView tempMin;

        private TextView tempMax;

        public ImageView getIcon() {
            return icon;
        }

        public void setIcon(ImageView icon) {
            this.icon = icon;
        }

        public TextView getTime() {
            return time;
        }

        public void setTime(TextView time) {
            this.time = time;
        }

        public TextView getWeather() {
            return weather;
        }

        public void setWeather(TextView weather) {
            this.weather = weather;
        }

        public TextView getTempMin() {
            return tempMin;
        }

        public void setTempMin(TextView tempMin) {
            this.tempMin = tempMin;
        }

        public TextView getTempMax() {
            return tempMax;
        }

        public void setTempMax(TextView tempMax) {
            this.tempMax = tempMax;
        }
    }
}
