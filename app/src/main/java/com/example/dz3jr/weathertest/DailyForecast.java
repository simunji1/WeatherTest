package com.example.dz3jr.weathertest;

/**
 * Created by dz3jr on 15.6.2017.
 */

public class DailyForecast {

    private final String city;
    private final String country;
    private final String status;
    private final double temperature;

    public DailyForecast(String city, String country, String status, double temperature) {
        this.city = city;
        this.country = country;
        this.status = status;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    public double getTemperature() {
        return temperature;
    }
}
