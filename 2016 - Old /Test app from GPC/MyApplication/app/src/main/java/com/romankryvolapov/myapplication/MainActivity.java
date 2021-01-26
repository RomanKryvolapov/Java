package com.romankryvolapov.myapplication;

// Roman Kryvolapov

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    public static final String LOG_TAG = "Status";
    private static ArrayList<Integer> id = new ArrayList<>();
    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> function = new ArrayList<>();
    private static ArrayList<String> param = new ArrayList<>();
    ConstraintLayout constraintLayout;
    Menu menu;
    View view;


    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // start of my code

        LayoutInflater ltInflater = getLayoutInflater();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        view = ltInflater.inflate(R.layout.fragment_home, null, false);
        constraintLayout = findViewById(R.id.constraintLayout);
        menu = navigationView.getMenu();
        try {
            InputStream inputStream = this.getAssets().open("menu.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonString = new String(buffer, "UTF-8");
            JSONObject jsonObject1 = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject1.getJSONArray("menu");
            int id_temp = 0;
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).has("name") && jsonArray.getJSONObject(i).has("function") && jsonArray.getJSONObject(i).has("param")) {
                    id_temp = View.generateViewId();
                    id.add(id_temp);
                    name.add(jsonArray.getJSONObject(i).getString("name"));
                    function.add(jsonArray.getJSONObject(i).getString("function"));
                    param.add(jsonArray.getJSONObject(i).getString("param"));
                    menu.add(Menu.NONE, id_temp, Menu.NONE, jsonArray.getJSONObject(i).getString("name"));
                    Log.d(LOG_TAG, "add item with id " + id_temp);
                }
            }
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (function.get(id.indexOf(item.getItemId()))) {
                        case "text":
                            Log.d(LOG_TAG, "text");
                            constraintLayout.removeAllViews();
                            TextView textView = new TextView(MainActivity.this);
                            textView.setText(param.get(id.indexOf(item.getItemId())));
                            textView.setTextColor(Color.BLACK);
                            textView.setGravity(Gravity.CENTER);
                            constraintLayout.addView(textView);
                            break;
                        case "image":
                            ImageDownloader imageDownloader = new ImageDownloader(param.get(id.indexOf(item.getItemId())));
                            imageDownloader.execute();
                            break;
                        case "url":
                            constraintLayout.removeAllViews();
                            WebView webView = new WebView(MainActivity.this);
                            webView.loadUrl(param.get(id.indexOf(item.getItemId())));
                            constraintLayout.addView(webView);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception " + e);
            e.printStackTrace();
        }
    }

    private class ImageDownloader extends AsyncTask<Void, Void, Void> {

        String url = "";
        Bitmap bitmap;

        public ImageDownloader(String url) {
            Log.d(LOG_TAG, "url " + url);
            this.url = url;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                InputStream is = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            constraintLayout.removeAllViews();
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageBitmap(bitmap);
            constraintLayout.addView(imageView);
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
