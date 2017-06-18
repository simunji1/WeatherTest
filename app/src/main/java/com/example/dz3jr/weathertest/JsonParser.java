package com.example.dz3jr.weathertest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dz3jr on 15.6.2017.
 */

public class JsonParser {

    public DailyForecast parseDailyForecast(String json) {
        String city, country, status;
        double temperature;

        city = country = status = "";
        temperature = -273.15;

        JSONObject jObject = null;

        try {
            jObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jObject != null) {
            JSONObject subObject = null;
            JSONArray subArray = null;

            //city
            try {
                city = jObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // country
            try {
                subObject = new JSONObject(jObject.getString("sys"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (subObject != null) {
                try {
                    country = subObject.getString("country");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // status
            try {
                subArray = new JSONArray(jObject.getString("weather"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (subArray != null) {
                try {
                    subObject = (JSONObject)subArray.get(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    status = subObject.getString("main");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //temperature
            try {
                subObject = new JSONObject(jObject.getString("main"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (subObject != null) {
                try {
                    temperature += subObject.getDouble("temp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return new DailyForecast(city, country, status, temperature);
    }

    public List<BriefWeatherInfo> parseWeeklyForecast(String json) {
        List<BriefWeatherInfo> forecast = new ArrayList<>();
        JSONObject jObject = null;

        try {
            jObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jObject != null) {
            JSONArray jArray = null;

            try {
                jArray = jObject.getJSONArray("list");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null) {
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject arrayObject = null;
                    try {
                        arrayObject = (JSONObject)jArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (arrayObject != null) {
                        JSONObject subObject = null;
                        JSONArray subArray = null;

                        String stringDate = null, status = null;
                        int temperature = -273;

                        try {
                            stringDate = arrayObject.getString("dt_txt");

                            subObject = arrayObject.getJSONObject("main");
                            if (subObject != null) {
                                temperature += (int)(subObject.getDouble("temp") + .65);
                            }
                            subObject = null;

                            subArray = arrayObject.getJSONArray("weather");
                            if (subArray != null) {
                                subObject = (JSONObject) subArray.get(0);
                                if (subObject != null) {
                                    status = subObject.getString("main");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (stringDate != null && status != null && temperature > -273) {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                            try {
                                forecast.add(new BriefWeatherInfo(df.parse(stringDate), status, temperature));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        return forecast;
    }
}
