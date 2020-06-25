package com.example.cinemacollector;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class activity_list extends AppCompatActivity {

    private Object IOException;
    private Object JSONException;
    public static String requestUrl = "";
    private static String[] filmList = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        MyTask myTask = new MyTask();
        myTask.execute();





/*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

 */


    }




/*

            InputStream in;

            int status = urlConnection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                in = urlConnection.getErrorStream();
            } else {
                in = urlConnection.getInputStream();
            }

            String response = convertStreamToString(in);
            JSONObject obj = new JSONObject(response);

            for (int i = 0; i < 20; i++) {
                filmList[i] = obj.getJSONArray("results").getJSONObject(i).getString("title");

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1, filmList);

            ListView listView = findViewById(R.id.discoverList);
            listView.setAdapter(adapter);
            urlConnection.disconnect();

 */


    private String convertStreamToString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        stream.close();
        return sb.toString();
    }

    private void arrayAdapter(){
        // тест пройден, названия приходят
        Log.i("qqq", "start arrayAdapter()");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filmList);
        ListView listView = findViewById(R.id.discoverList);
        listView.setAdapter(adapter);

        Log.i("qqq", requestUrl);


    }


    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Log.i("qqq", "doInBackground started");

            URL url = null;
            try {
                url = new URL(activity_list.requestUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //        urlConnection.setConnectTimeout(1000);
                urlConnection.connect();
                Log.i("qqq", "connect()");
                InputStream in;
                int status = urlConnection.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK) {
                    in = urlConnection.getErrorStream();
                } else {
                    in = urlConnection.getInputStream();
                }

                String response = convertStreamToString(in);
                JSONObject obj = new JSONObject(response);

                for (int i = 0; i < 20; i++) {
                    filmList[i] = obj.getJSONArray("results").getJSONObject(i).getString("title");

                }


                urlConnection.disconnect();

                Log.i("qqq", "disconnect()");
                return null;


            } catch (Exception e) {

            }

            return null;

        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            arrayAdapter();
        }


    }
}



