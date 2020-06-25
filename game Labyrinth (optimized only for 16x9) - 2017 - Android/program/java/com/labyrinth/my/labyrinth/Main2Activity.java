package com.labyrinth.my.labyrinth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Application_Status", "First screen: onCreate");
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Application_Status", "First screen: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Application_Status", "First screen: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Application_Status", "First screen: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Application_Status", "First screen: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Application_Status", "First screen: onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Application_Status", "First screen: onRestart");
    }

    public void onClickMode1(View v) {
        DrawThread.newRunThread=true;
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickMode2(View v) {
        DrawThread.newRunThread=true;
        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
        startActivity(intent);
    }




}
