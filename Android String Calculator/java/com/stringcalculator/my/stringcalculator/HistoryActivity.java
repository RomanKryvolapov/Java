package com.stringcalculator.my.stringcalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    static ArrayList<String> history = new ArrayList<String>();

    static StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
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

        if(history.size()>0) {
            String outHistory = "";
            for (int i = history.size() - 1; i >= 0; i--) {

                HistoryActivity.stringBuffer.setLength(0);
                HistoryActivity.stringBuffer.append(outHistory);
                HistoryActivity.stringBuffer.append(history.get(i) + "\n\n");
                outHistory = HistoryActivity.stringBuffer.toString();
                HistoryActivity.stringBuffer.setLength(0);
            }

            TextView vivod = (TextView) findViewById(R.id.historyView);
            vivod.setTextSize(30);
            vivod.setText(outHistory);
            outHistory = "";
        }

    }

    public void onClickBack(View view)
    {
        finish();
    }
    public void onClickClear(View view)
    {
        history.clear();
        TextView vivod = (TextView) findViewById(R.id.historyView);
        vivod.setTextSize(30);
        vivod.setText("");
    }

    public void onClickAbout(View view)
    {
        String about = "Made by Roman Kryvolapov";

        if(HistoryActivity.history.size()>0) {
            if (!HistoryActivity.history.get(HistoryActivity.history.size() - 1).equals(about)) {
                HistoryActivity.history.add(about);
            }
        } else{
            HistoryActivity.history.add(about);
        }

        String outHistory = "";
        for (int i = history.size() - 1; i >= 0; i--) {

            HistoryActivity.stringBuffer.setLength(0);
            HistoryActivity.stringBuffer.append(outHistory);
            HistoryActivity.stringBuffer.append(history.get(i) + "\n\n");
            outHistory = HistoryActivity.stringBuffer.toString();
            HistoryActivity.stringBuffer.setLength(0);
        }

        TextView vivod = (TextView) findViewById(R.id.historyView);
        vivod.setTextSize(30);
        vivod.setText(outHistory);
        outHistory = "";

    }
}
