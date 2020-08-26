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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.util.Arrays;

public class activity_list extends AppCompatActivity {

    public static String requestUrl = "";
    private String[] filmList = new String[20];
    private int[] filmID = new int[20];
    private int[] filmStatus = new int[20];
    private SharedPreferences mSettings;
    private static final String api_key = "358d127cfc98138fe9a600b0b63bd94f";
    Spinner spinner_navigation;
    Button button_prev;
    Button button_next;
    private String[] navigation_names;
    private String[] navigation_links;
    private int number_of_page;
    private int total_pages;
    private int current_page = 1;
    private int total_results;
    ListView listView;

    @Override
    protected void onResume() {
        super.onResume();
        MyTask myTask = new MyTask();
        myTask.execute();

    }

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
        try {
            requestUrl = intent.getExtras().getString("requestUrl");
        } catch (NullPointerException e) {

        }

        Log.i("status", "activity_list - " + requestUrl);

        spinner_navigation = (Spinner) findViewById(R.id.spinner_navigation);
        button_next = (Button) findViewById(R.id.button_next);
        button_prev = (Button) findViewById(R.id.button_prev);


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
                filmStatus[i] = Integer.parseInt(mSettings.getString(Integer.toString(filmID[i]), ""));
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
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);


                final TextView text = (TextView) v.findViewById(R.id.textView_name);
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

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(filmID[position]!=0) {

                            String requestUrl = "https://api.themoviedb.org/3/movie/" +
                                    Integer.toString(filmID[position]) +
                                    "?api_key=" +
                                    api_key +
                                    "&language=ru-RU";

                            Log.i("status", "!!! Clicked on position " + position + " with ID " + filmID[position] + " with status " + filmStatus[position]);


                            Intent intent = new Intent(activity_list.this, DetailsActivity.class);
                            intent.putExtra("requestUrl", requestUrl);
                            startActivity(intent);
                        }

                    }
                });


                return v;
            }
        };


//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                return super.getView(position, convertView, parent);
//            }
//        };


        listView = findViewById(R.id.discoverList);
        listView.setAdapter(adapter);


    }

    private void spinner_navigation() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, navigation_names);
        spinner_navigation.setAdapter(adapter);
        spinner_navigation.setSelection(current_page - 1);
        if (spinner_navigation.getOnItemSelectedListener() == null) {
            spinner_navigation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position + 1 != current_page) {
                        current_page = position + 1;
                        MyTask myTask = new MyTask();
                        myTask.execute();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


    }

    private void add_values_to_navigarion_names_and_links() {

        navigation_names = new String[total_pages];
        navigation_links = new String[total_pages];

        for (int i = 0; i < total_pages; i++) {
            navigation_names[i] = Integer.toString(i + 1) + "  из  " + Integer.toString(total_pages);
            navigation_links[i] = requestUrl + Integer.toString(i);
        }


    }

    private void listiners() {

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_page < total_pages) {
                    current_page++;
                    MyTask myTask = new MyTask();
                    myTask.execute();
                }
            }
        });

        button_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_page > 1) {
                    current_page--;
                    MyTask myTask = new MyTask();
                    myTask.execute();
                }
            }
        });

    }


    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            URL url = null;
            try {
                url = new URL(requestUrl + Integer.toString(current_page));
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

                total_results = obj.getInt("total_results");
                if (total_results > 0) {
                    int results_count = obj.getJSONArray("results").length();
                    for (int i = 0; i < results_count; i++) {
                            filmList[i] = obj.getJSONArray("results").getJSONObject(i).getString("title");
                            filmID[i] = obj.getJSONArray("results").getJSONObject(i).getInt("id");
                    }
                    number_of_page = obj.getInt("page");
                    total_pages = obj.getInt("total_pages");
                    Log.i("status", "results_count = " + results_count);




                    if(results_count<20){

                        for (int i = results_count; i < 20; i++) {
                            filmList[i] = " ";
                            filmID[i] = 0;
                        }


                    }
//
//                        for (int i = 20; i == 11; i--) {
//                            Log.i("status", "!!!");
//                            filmList[i] = " ";
//                            filmID[i] = 0;
//                        }
//                    }
                    Log.i("status", "filmList = " + Arrays.toString(filmList));




                } else {
                    number_of_page = 0;
                    total_pages = 0;
                }


                urlConnection.disconnect();

                return null;


            } catch (Exception e) {

                Log.i("status", "NoConnection Exception");
            }

            return null;

        }

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);

            if (total_results > 0) {
                // после получения данных выполнится это
                statusCheck();
                arrayAdapter();
                add_values_to_navigarion_names_and_links();
                listiners();
                spinner_navigation();
            }

        }


    }


}



