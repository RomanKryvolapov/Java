package com.example.cinemacollector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class activity_list extends AppCompatActivity {

    public static String requestUrl = "";
    private String[] filmList = new String[20];
    private int[] filmID = new int[20];
    private int[] filmStatus = new int[20];
    private SharedPreferences mSettings;
    private static final String api_key = "358d127cfc98138fe9a600b0b63bd94f";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        //   Toolbar toolbar = findViewById(R.id.toolbar);
        //   setSupportActionBar(toolbar);


//        SharedPreferences.Editor editor = mSettings.edit();
//        editor.putString("123", "123");
//        editor.apply();

        Intent intent = getIntent();
        requestUrl = intent.getExtras().getString("requestUrl");

        Log.i("status", requestUrl);

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

    private void statusCheck() {

        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        for (int i = 0; i < filmID.length; i++) {
            if (mSettings.contains(Integer.toString(filmID[i]))) {
                filmStatus[i] = mSettings.getInt(Integer.toString(filmID[i]), 1);
            } else {
                filmStatus[i] = 1;
            }
        }
//        filmStatus[5] = 2;
    }


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

//    public class ListTemplate {
//        public String nameOfMovie;
//        private String [] status = new String[] {"Новый","Посмотрел", "Хочу посмотреть", "Не хочу смотреть"};
//
//
//
//    }

//    public class UsersAdapter extends ArrayAdapter<ListTemplate> {
//
//        public UsersAdapter(Context context, ArrayList<User> users) {
//            super(context, 0, users);
//        }
//
//
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//        }
//
//    }

    private void arrayAdapter() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textView_name, filmList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);


                TextView text = (TextView) v.findViewById(R.id.textView_name);
                switch (filmStatus[position]) {
                    case 1:
                        text.setTextColor(Color.WHITE);
                        break;
                    case 2:
                        text.setTextColor(Color.RED);
                        break;
                    case 3:
                        text.setTextColor(Color.BLUE);
                        break;
                    case 4:
                        text.setTextColor(Color.BLACK);
                        break;
                }

                return v;
            }
        };


//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                return super.getView(position, convertView, parent);
//            }
//        };


        ListView listView = findViewById(R.id.discoverList);
        listView.setAdapter(adapter);


    }


    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            URL url = null;
            try {
                url = new URL(activity_list.requestUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //        urlConnection.setConnectTimeout(1000);
                urlConnection.connect();
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
                    Log.i("result", "Fllm Name = " + filmList[i]);
                    filmID[i] = obj.getJSONArray("results").getJSONObject(i).getInt("id");
                    Log.i("result", "Fllm ID = " + filmID[i]);
                }

                urlConnection.disconnect();

                return null;


            } catch (Exception e) {

                Log.i("status", "NoConnection");
            }

            return null;

        }

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            // после получения данных выполнится это
            statusCheck();
            arrayAdapter();
        }


    }


}



