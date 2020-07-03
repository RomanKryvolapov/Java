package com.example.cinemacollector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailsActivity extends AppCompatActivity {

    public static String requestUrl = "";
    TextView textView_title;
    TextView textView_release_date;
    TextView textView_vote_average;
    TextView textView_vote_count;
    TextView textView_budget;
    TextView textView_revenue;
    TextView textView_production_countries;
    TextView textView_overview;
    ImageView poster;
    Bitmap bmp;
    Button button_add_to_watch_list;
    Button button_add_to_viewed;
    Button button_add_to_blacklist;
    private SharedPreferences mSettings;

    private String title = " ";
    private String release_date = " ";
    private String vote_average = " ";
    private String vote_count = " ";
    private String budget = " ";
    private int budget_int = 0;
    private String revenue = " ";
    private int revenue_int = 0;
    private String production_countries = " ";
    private String overview = "Описание: ";
    private String poster_path = "";
    private final String image_path = "https://image.tmdb.org/t/p/w1280";
    private int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        Intent intent = getIntent();
        try {
            requestUrl = intent.getExtras().getString("requestUrl");
        } catch (NullPointerException e) {

        }
        Log.i("status", "DetailsActivity - " + requestUrl);

        textView_title = (TextView) findViewById(R.id.textView_title);
        textView_release_date = (TextView) findViewById(R.id.textView_release_date);
        textView_vote_average = (TextView) findViewById(R.id.textView_vote_average);
        textView_vote_count = (TextView) findViewById(R.id.textView_vote_count);
        textView_budget = (TextView) findViewById(R.id.textView_budget);
        textView_revenue = (TextView) findViewById(R.id.textView_revenue);
        textView_production_countries = (TextView) findViewById(R.id.textView_production_countries);
        textView_overview = (TextView) findViewById(R.id.textView_overview);
        poster = (ImageView) findViewById(R.id.imageView_poster);
        button_add_to_watch_list = (Button) findViewById(R.id.button_add_to_watch_list);
        button_add_to_viewed = (Button) findViewById(R.id.button_add_to_viewed);
        button_add_to_blacklist = (Button) findViewById(R.id.button_add_to_blacklist);

        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);


        MyTask myTask = new MyTask();
        myTask.execute();


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

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            URL url = null;
            try {
                url = new URL(requestUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //        urlConnection.setConnectTimeout(1000);
                urlConnection.connect();
                Log.i("status", "urlConnection.connect();");
                InputStream in;
                int status = urlConnection.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK) {
                    in = urlConnection.getErrorStream();
                    Log.i("status", "urlConnection.getErrorStream()");
                } else {
                    Log.i("status", "urlConnection.getInputStream()");
                    in = urlConnection.getInputStream();
                }


                String response = convertStreamToString(in);
                Log.i("status", "response " + response);
                JSONObject obj = new JSONObject(response);


                id = obj.getInt("id");
                title = obj.getString("title");
                release_date = obj.getString("release_date");
                vote_average = obj.getString("vote_average");
                vote_count = obj.getString("vote_count");
                budget_int = obj.getInt("budget");
                if (budget_int > 0)
                    budget = String.format("%,d", budget_int);
                else budget = "Не указан";
                revenue_int = obj.getInt("revenue");
                if (revenue_int > 0)
                    revenue = String.format("%,d", revenue_int);
                else revenue = "Не указаны";
//                production_countries = obj.getString("production_countries");

                int production_countries_count = obj.getJSONArray("production_countries").length();

                for (int i = 0; i < production_countries_count; i++) {
                    production_countries = production_countries + obj.getJSONArray("production_countries").getJSONObject(i).getString("name") + "\n";
                }


                overview = obj.getString("overview");
                poster_path = image_path + obj.getString("poster_path");

                Log.i("status", "poster_path " + poster_path);

                try {
                    InputStream in_image = new URL(poster_path).openStream();
                    bmp = BitmapFactory.decodeStream(in_image);
                } catch (IOException e) {
                }


//                    filmList[i] = obj.getJSONArray("results").getJSONObject(i).getString("title");
//                    Log.i("result", "Fllm Name = " + filmList[i]);
//                    filmID[i] = obj.getJSONArray("results").getJSONObject(i).getInt("id");
//                    Log.i("result", "Fllm ID = " + filmID[i]);


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

            textView_title.setText(title);
            textView_release_date.setText(release_date);
            textView_vote_average.setText(vote_average);
            textView_vote_count.setText(vote_count);
            textView_budget.setText(budget);
            textView_revenue.setText(revenue);
            textView_production_countries.setText(production_countries);
            textView_overview.setText(overview);
            poster.setImageBitmap(bmp);
            button_add_to_watch_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(Integer.toString(id), "2");
                    editor.putString(Integer.toString(id) + "_name", title);
                    editor.apply();
                }
            });
            button_add_to_viewed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(Integer.toString(id), "3");
                    editor.putString(Integer.toString(id) + "_name", title);
                    editor.apply();
                }
            });


            button_add_to_blacklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(Integer.toString(id), "4");
                    editor.putString(Integer.toString(id) + "_name", title);
                    editor.apply();
                }
            });


        }


    }

}
