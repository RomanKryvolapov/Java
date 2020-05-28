package com.stringcalculator.my.stringcalculator;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    static ArrayList<String> history = new ArrayList<String>();

    static StringBuffer stringBuffer = new StringBuffer();

    Button buttonBack;
    Button buttonAbout;
    Button buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

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


        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonAbout = (Button) findViewById(R.id.buttonAbout);
        buttonClear = (Button) findViewById(R.id.buttonClear);

        button_back_listiner(buttonBack);
        button_about_listiner(buttonAbout);
        button_clear_listiner(buttonClear);

        setStatusBarColor(R.color.colorButtons2);

    }

    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, color));
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    void button_back_listiner(Button button){
        final Button newButton = button;
        newButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        newButton.setBackgroundResource(R.drawable.button_style_back_on_click);
                        break;
                    case MotionEvent.ACTION_UP:
                        newButton.setBackgroundResource(R.drawable.button_style_back);
                        finish();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    void button_about_listiner(Button button){
        final Button newButton = button;
        newButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        newButton.setBackgroundResource(R.drawable.button_style_2);
                        break;
                    case MotionEvent.ACTION_UP:
                        newButton.setBackgroundResource(R.drawable.button_style_3);
                        onClickAbout();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    void button_clear_listiner(Button button){
        final Button newButton = button;
        newButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        newButton.setBackgroundResource(R.drawable.button_style_clear_on_click);
                        setStatusBarColor(R.color.colorButtons1);
                        break;
                    case MotionEvent.ACTION_UP:
                        newButton.setBackgroundResource(R.drawable.button_style_clear);
                        setStatusBarColor(R.color.colorButtons2);
                        onClickClear();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onClickClear()
    {
        history.clear();
        TextView vivod = (TextView) findViewById(R.id.historyView);
        vivod.setTextSize(30);
        vivod.setText("");
    }

    public void onClickAbout()
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