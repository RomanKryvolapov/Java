package com.romankryvolapov.minecraftmaps;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Status";
    private Fragment selectedFragment;
    private BottomNavigationView bottomNavigationView;
    public static SharedPreferences mSettings;
    private static String remove_from_favorites;
    private static String add_to_favorites;
    public static int height = 0;

    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "onBackPressed()");
        selectedFragment = new MapsFragment();
        bottomNavigationView.setSelectedItemId(R.id.maps_button);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d(LOG_TAG, "WRITE_EXTERNAL_STORAGE NO!!!");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 112);
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Log.d(LOG_TAG, "WRITE_EXTERNAL_STORAGE YES!!!");
        }

        writeFile();


        Log.d(LOG_TAG, "MainActivity onCreate");
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBrown));
        }
        selectedFragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        remove_from_favorites = getResources().getString(R.string.remove_from_favorites);
        add_to_favorites = getResources().getString(R.string.add_to_favorites);
        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = mSettings.edit();
//        editor.clear();
//        editor.apply();

    }

    private void writeFile() {

        Log.d(LOG_TAG, "ExternalStorageUtils.isExternalStorageMounted() = " + ExternalStorageUtils.isExternalStorageMounted());
        Log.d(LOG_TAG, "ExternalStorageUtils.isExternalStorageReadOnly() = " + ExternalStorageUtils.isExternalStorageReadOnly());
        Log.d(LOG_TAG, "ExternalStorageUtils.getPrivateExternalStorageBaseDir = " + ExternalStorageUtils.getPrivateExternalStorageBaseDir(this, ""));



        try {
//            String path = "/storage/emulated/0/games/com.mojang/minecraftWorlds/";
            String path = "/storage/emulated/0/Android/data/com.mojang/minecraftWorlds/";
            File file = new File(path);
            String q = "eaibnbne";
            // ==> /storage/emulated/0/note.txt  (API < 29)
            // ==> /storage/emulated/0/Android/data/org.o7planning.externalstoragedemo/files/note.txt (API >=29)
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(q.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.d(LOG_TAG, "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException");
            e.printStackTrace();
        }
    }



    public static void femove_from_favorites(int id, Context context, String description) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.remove(Integer.toString(id));
        editor.putString(Integer.toString(id), "0");
        editor.apply();
        Toast toast = Toast.makeText(context, remove_from_favorites + "\n" + description, Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if (v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    public static void add_to_favorites(int id, Context context, String description) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.remove(Integer.toString(id));
        editor.putString(Integer.toString(id), "1");
        editor.apply();
        Toast toast = Toast.makeText(context, add_to_favorites + "\n" + description, Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if (v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    public static boolean favorite_or_not(int id) {
        String key = MainActivity.mSettings.getString(Integer.toString(id), "");
        if (key.equals("0") || key.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.maps_button:
                            Log.d(LOG_TAG, "MainActivity R.id.maps_button");
                            selectedFragment = new MapsFragment();
                            break;
                        case R.id.favorites_button:
                            Log.d(LOG_TAG, "MainActivity R.id.favorites_button");
                            selectedFragment = new FavoritesFragment();
                            break;
                        case R.id.gide_button:
                            Log.d(LOG_TAG, "MainActivity R.id.gide_button");
                            selectedFragment = new GideFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };


}
