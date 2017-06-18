package com.example.dz3jr.weathertest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dz3jr on 12.6.2017.
 */

class DataLoader extends AsyncTask<String, Void, String[]> {
    private static final int TIMEOUT = 5000;

    @Override
    protected String[] doInBackground(String... urls) {
        String[] response = new String[urls.length];
        int index = -1;
        for (String url : urls) {
            index++;
            HttpURLConnection connection = null;
            try {
                URL u = new URL(url);
                connection = (HttpURLConnection) u.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-length", "0");
                connection.setUseCaches(false);
                connection.setAllowUserInteraction(false);
                connection.setConnectTimeout(TIMEOUT);
                connection.setReadTimeout(TIMEOUT);
                connection.connect();
                int status = connection.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        response[index] = sb.toString();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.disconnect();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return response;
    }
}
