package com.stringcalculator.my.stringcalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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


        String outHistory = "";

        for (int i = history.size()-1; i >= 0; i--) {

            MainActivity.stringBuffer.setLength(0);
            MainActivity.stringBuffer.append(outHistory);
            MainActivity.stringBuffer.append(history.get(i) + "\n\n");
            outHistory = MainActivity.stringBuffer.toString();
            MainActivity.stringBuffer.setLength(0);

        }

        TextView vivod = (TextView) findViewById(R.id.historyView);
        vivod.setTextSize(30);
        vivod.setText(outHistory);

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

//    public void onTouchListenerActivity(){
//        Button button = (Button) findViewById(R.id.buttonBack);
//        button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        ((Button) findViewById(R.id.buttonBack)).setBackgroundColor(0xFFB46D16);
//
//                        finish();
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        ((Button) findViewById(R.id.buttonBack)).setBackgroundColor(0xFFFA9112);
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                }
//                return true;
//            }
//        });
//    }


//    public void onTouchListenerClear(){
//        Button button = (Button) findViewById(R.id.buttonClear);
//        button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        ((Button) findViewById(R.id.buttonClear)).setBackgroundColor(0xFF95969B);
//                        history.clear();
//
//                        TextView vivod = (TextView) findViewById(R.id.historyView);
//                        vivod.setTextSize(30);
//                        vivod.setText("");
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        ((Button) findViewById(R.id.buttonClear)).setBackgroundColor(0xFFd0d1d5);
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                }
//                return true;
//            }
//        });
//    }

}
