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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bignerdranch.android.weatherprogrammer.R;
import com.bignerdranch.android.weatherprogrammer.WeatherProgrammerApplication;
import com.bignerdranch.android.weatherprogrammer.activity.main.OpenWeatherMapForecastListAdapter;
import com.bignerdranch.android.weatherprogrammer.openweathermap.bean.base.OpenWeatherMapCity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LocationOptionActivity extends AppCompatActivity {


    private TextView cityTextView;
    private String cityName;
    private String cityId;
    private ListView cityListView;
    private SharedPreferences sp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_option);
        cityTextView = findViewById(R.id.tv_city);
        cityListView = findViewById(R.id.lv_city_list);

        sp = getSharedPreferences(WeatherProgrammerApplication.FILE_LOCATION_OPTION, 0);
        cityName = sp.getString(WeatherProgrammerApplication.KEY_CITY_NAME, WeatherProgrammerApplication.DEFINE_CITY_NAME);
        cityTextView.setText(WeatherProgrammerApplication.DISPLAY_WORD + cityName);

        final List<OpenWeatherMapCity> data = getData();
        cityListView.setAdapter(new CityListAdapter(this, getData()));
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sp.edit();
                OpenWeatherMapCity city = data.get(position);
                cityName = city.getName();
                cityId = city.getId();
                editor.putString(WeatherProgrammerApplication.KEY_CITY_NAME, cityName);
                editor.putString(WeatherProgrammerApplication.KEY_CITY_ID, cityId);
                editor.commit();
                cityTextView.setText(WeatherProgrammerApplication.DISPLAY_WORD + cityName);
            }
        });

    }

    public List<OpenWeatherMapCity> getData() {
        List<OpenWeatherMapCity> data = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            OpenWeatherMapCity city = new OpenWeatherMapCity();
            city.setId(WeatherProgrammerApplication.DEFINE_CITY_ID);
            city.setName(WeatherProgrammerApplication.DEFINE_CITY_NAME + i);
            data.add(city);
        }

        return data;
    }


    private class CityListAdapter extends BaseAdapter {

        private List<OpenWeatherMapCity> data;
        private Context context;

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

            View view = null;

            TextView holder = null;
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
