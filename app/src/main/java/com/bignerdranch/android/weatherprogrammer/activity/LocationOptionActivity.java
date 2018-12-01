package com.bignerdranch.android.weatherprogrammer.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.WeatherApplication;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapCity;


import java.util.List;

/**
 * @Description 城市地点选择页面
 *
 * @Package com.bignerdranch.android.weatherprogrammer.activity
 * @date 2018/11/28
 */
public class LocationOptionActivity extends AppCompatActivity {

    /**
     * 城市显示
     */
    private TextView cityTextView;
    /**
     * 城市名字
     */
    private String cityName;
    /**
     * 城市id
     */
    private String cityId;
    /**
     * 城市列表
     */
    private ListView cityListView;
    /**
     * 持久化存储引用
     */
    private SharedPreferences sp = null;
    /**
     * 文本json数据，取自open weather api，由于文件过大不能加载到apk中，因此取样部分数据
     * 如有需要需要手动抓取数据
     */
    private static final String countryData = "[{\"id\":1816670,\"name\":\"Beijing\",\"country\":\"CN\"},\n" +
            "{\"id\":1796236,\"name\":\"Shanghai\",\"country\":\"CN\"},\n" +
            "{\"id\":1792943,\"name\":\"Tianjin\",\"country\":\"CN\"},\n" +
            "{\"id\":1795269,\"name\":\"Shijiazhuang\",\"country\":\"CN\"},\n" +
            "{\"id\":1805754,\"name\":\"Jinan\",\"country\":\"CN\"},\n" +
            "{\"id\":1787093,\"name\":\"Yantai\",\"country\":\"CN\"},\n" +
            "{\"id\":1799963,\"name\":\"Nanjing\",\"country\":\"CN\"},\n" +
            "{\"id\":1815286,\"name\":\"Chengdu\",\"country\":\"CN\"},\n" +
            "{\"id\":1815577,\"name\":\"Changsha\",\"country\":\"CN\"},\n" +
            "{\"id\":1804651,\"name\":\"Kunming\",\"country\":\"CN\"},\n" +
            "{\"id\":1808926,\"name\":\"Hangzhou\",\"country\":\"CN\"},\n" +
            "{\"id\":1790925,\"name\":\"Wuxi\",\"country\":\"CN\"},\n" +
            "{\"id\":1791247,\"name\":\"Wuhan\",\"country\":\"CN\"},\n" +
            "{\"id\":1808722,\"name\":\"Hefei\",\"country\":\"CN\"},\n" +
            "{\"id\":1810821,\"name\":\"Fuzhou\",\"country\":\"CN\"},\n" +
            "{\"id\":1790645,\"name\":\"Xiamen\",\"country\":\"CN\"},\n" +
            "{\"id\":1809858,\"name\":\"Guangzhou\",\"country\":\"CN\"},\n" +
            "{\"id\":1795565,\"name\":\"Shenzhen\",\"country\":\"CN\"},\n" +
            "{\"id\":1809077,\"name\":\"Haikou\",\"country\":\"CN\"},\n" +
            "{\"id\":1914723,\"name\":\"Sanya\",\"country\":\"CN\"},\n" +
            "{\"id\":1914540,\"name\":\"Xianggang\",\"country\":\"CN\"}]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_option);
        cityTextView = findViewById(R.id.tv_city);
        cityListView = findViewById(R.id.lv_city_list);

        sp = getSharedPreferences(WeatherApplication.FILE_LOCATION_OPTION, 0);
        cityName = sp.getString(WeatherApplication.KEY_CITY_NAME, WeatherApplication.DEFINE_CITY_NAME);
        cityTextView.setText(WeatherApplication.DISPLAY_WORD + cityName);

        final List<OpenWeatherMapCity> data = getData();
        cityListView.setAdapter(new CityListAdapter(this, getData()));
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sp.edit();
                OpenWeatherMapCity city = data.get(position);
                cityName = city.getName();
                cityId = city.getId();
                editor.putString(WeatherApplication.KEY_CITY_NAME, cityName);
                editor.putString(WeatherApplication.KEY_CITY_ID, cityId);
                editor.commit();
                cityTextView.setText(WeatherApplication.DISPLAY_WORD + cityName);
            }
        });

    }

    /**
     * 获取城市数据
     */
    public List<OpenWeatherMapCity> getData() {
        List<OpenWeatherMapCity> data = JSON.parseArray(countryData, OpenWeatherMapCity.class);
        return data;
    }

    /**
     * 城市列表适配器
     */
    private class CityListAdapter extends BaseAdapter {

        /**
         * 城市列表数据
         */
        private List<OpenWeatherMapCity> data;
        /**
         * Activity上下文
         */
        private Context context;

        /**
         * 构造器
         */
        public CityListAdapter(Context context, List<OpenWeatherMapCity> data) {
            this.context = context;
            this.data = data;
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

            View view;
            // item复用优化，提高效率
            TextView holder;
            if (null != convertView) {
                view = convertView;
                holder = (TextView) view.getTag();
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.activity_location_city_list, parent, false);
                holder = view.findViewById(R.id.item_city_name);
                view.setTag(holder);
            }

            holder.setText(data.get(position).getName());

            return view;
        }
    }
}
