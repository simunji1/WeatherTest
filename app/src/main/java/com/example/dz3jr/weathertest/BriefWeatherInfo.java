package com.example.dz3jr.weathertest;

import java.util.Date;

/**
 * Created by dz3jr on 15.6.2017.
 */

public class BriefWeatherInfo {
    private final Date date;
    private final String status;
    private final int temperature;

    public BriefWeatherInfo(Date date, String status, int temperature) {
        this.date = date;
        this.status = status;
        this.temperature = temperature;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getTemperature() {
        return temperature;
    }
}
