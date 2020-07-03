package com.example.cinemacollector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MyListsActivity extends AppCompatActivity {

    private int status;
    private SharedPreferences mSettings;
    ListView listView;
    private String[] filmListID;
    private String[] filmListName;
    private static final String api_key = "358d127cfc98138fe9a600b0b63bd94f";
    TextView textView_my_lists;

    @Override
    protected void onResume() {
        super.onResume();
        go();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lists);
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
            status = intent.getExtras().getInt("status");
        } catch (NullPointerException e) {

        }

        listView = (ListView) findViewById(R.id.myList);
        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        textView_my_lists = (TextView) findViewById(R.id.textView_my_lists);
        if(status == 2)
        textView_my_lists.setText("Список на просмотр");
        else if (status == 3)
            textView_my_lists.setText("Список просмотренных");
        go();

    }

    private void go (){

        Map<String, ?> all = new LinkedHashMap<>();
        all = mSettings.getAll();
        Map<String, String> my_list = new LinkedHashMap<>();

        for (String key : all.keySet()) {
            if (!key.contains("_name") && all.get(key).equals(Integer.toString(status)) && all.containsKey(key + "_name")) {
                my_list.put((key), all.get(key + "_name").toString());
            }
        }

        if (my_list.size() > 0) {

            filmListID = new String[my_list.size()];
            filmListName = new String[my_list.size()];

            Set<String> keys = my_list.keySet();
            int i = 0;
            for (String k : keys) {
                filmListID[i] = k;
                filmListName[i] = my_list.get(k);
                i++;
            }




        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textView_name, filmListName) {
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String requestUrl = "https://api.themoviedb.org/3/movie/" +
                                filmListID[position] +
                                "?api_key=" +
                                api_key +
                                "&language=ru-RU";

                        Intent intent = new Intent(MyListsActivity.this, DetailsActivity.class);
                        intent.putExtra("requestUrl", requestUrl);
                        startActivity(intent);

                    }
                });


                return v;
            }
        };

        listView.setAdapter(adapter);


    }


}
