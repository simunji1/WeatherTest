package com.example.dz3jr.weathertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String DAILY_URL = "http://api.openweathermap.org/data/2.5/weather?lat=50.36609&lon=15.63183&appid=9c02bed8208fd52ae9ddd8e6d1ffea13";
    private static final String WEEKLY_URL = "http://api.openweathermap.org/data/2.5/forecast?lat=50.36609&lon=15.63183&appid=9c02bed8208fd52ae9ddd8e6d1ffea13";

    private DataLoader dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = new DataLoader();

        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO: refresh
        //refresh();
    }

    protected void refresh() {
        String[] jsons = null;
        try {
            jsons = dl.execute(DAILY_URL, WEEKLY_URL).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonParser jp = new JsonParser();
        DailyForecast daily = jp.parseDailyForecast(jsons[0]);

        TextView tvTemperature, tvWeather, tvLocation;
        tvTemperature = (TextView) findViewById(R.id.tvTemperature);
        tvWeather = (TextView) findViewById(R.id.tvWeather);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        String text = daily.getTemperature() + " °C";
        tvTemperature.setText(text);

        text = daily.getCity() + ", " + daily.getCountry();
        tvLocation.setText(text);

        tvWeather.setText(daily.getStatus());

        List<BriefWeatherInfo> list = jp.parseWeeklyForecast(jsons[1]);
        ListView listView = (ListView) findViewById(R.id.listView);

        String[] arrayData = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            BriefWeatherInfo info = list.get(i);
            DateFormat df = new SimpleDateFormat("d.M HH:mm");
            arrayData[i] = df.format(info.getDate()) + ", " + info.getTemperature() + " °C, " + info.getStatus();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayData);
        listView.setAdapter(adapter);
    }
}
