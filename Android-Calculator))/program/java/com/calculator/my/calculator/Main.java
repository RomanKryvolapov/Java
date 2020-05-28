package com.calculator.my.calculator;
//made by Roman Kryvolapov
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.*;

import android.view.MotionEvent;


public class Main extends AppCompatActivity {

    BigDecimal digit1 = BigDecimal.valueOf(0);
    BigDecimal digit2 = BigDecimal.valueOf(0);
    String display = "";
    final int textSize = 80;
    final int maxDigits = 30;
    boolean doubleClockError = false;
    int methodRele = 0;
    boolean function = false;
    int[] buttonID = new int[11];
    String[] buttonNumber = new String[11];
    int inerator = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        TextView stroka0 = (TextView) findViewById(R.id.digital);
        stroka0.setTextSize(textSize);
        stroka0.setText("Hello!");
        onTouchListenerC();
        onTouchListenerSlojenie ();
        onTouchListenerVichetanie ();
        onTouchListenerUmnojenie ();
        onTouchListenerDelenie ();
        onTouchListenerRavno ();

        buttonID[0]=getResources().getIdentifier("button7", "id", getPackageName());
        buttonID[1]=getResources().getIdentifier("button8", "id", getPackageName());
        buttonID[2]=getResources().getIdentifier("button9", "id", getPackageName());
        buttonID[3]=getResources().getIdentifier("button4", "id", getPackageName());
        buttonID[4]=getResources().getIdentifier("button5", "id", getPackageName());
        buttonID[5]=getResources().getIdentifier("button6", "id", getPackageName());
        buttonID[6]=getResources().getIdentifier("button1", "id", getPackageName());
        buttonID[7]=getResources().getIdentifier("button2", "id", getPackageName());
        buttonID[8]=getResources().getIdentifier("button3", "id", getPackageName());
        buttonID[9]=getResources().getIdentifier("button0", "id", getPackageName());
        buttonID[10]=getResources().getIdentifier("buttonTochka", "id", getPackageName());

        buttonNumber[0]= "1";
        buttonNumber[1]= "2";
        buttonNumber[2]= "3";
        buttonNumber[3]= "4";
        buttonNumber[4]= "5";
        buttonNumber[5]= "6";
        buttonNumber[6]= "7";
        buttonNumber[7]= "8";
        buttonNumber[8]= "9";
        buttonNumber[9]= "0";
        buttonNumber[10]= ".";

        for(int i = 0; i<11; i++) {
            onTouchListener();
            inerator++;
        }
    }

    public void onTouchListener (){
        Button button = (Button) findViewById(buttonID[inerator]);
        button.setOnTouchListener(new View.OnTouchListener() {
            int id = buttonID[inerator];
            String number = buttonNumber[inerator];
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(id)).setBackgroundColor(0xFF95969B);
                        VvodCifr(number);
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(id)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void textSizeChange () {

        TextView vivod = (TextView) findViewById(R.id.digital);
        if(display.length()<=30) {
            switch (display.length()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    vivod.setTextSize(textSize);
                    break;
                case 12:
                    vivod.setTextSize(textSize - 5);
                    break;
                case 13:
                    vivod.setTextSize(textSize - 10);
                    break;
                case 14:
                    vivod.setTextSize(textSize - 15);
                    break;
                case 15:
                    vivod.setTextSize(textSize - 20);
                    break;
                case 16:
                    vivod.setTextSize(textSize - 23);
                    break;
                case 17:
                    vivod.setTextSize(textSize - 26);
                    break;
                case 18:
                    vivod.setTextSize(textSize - 29);
                    break;
                case 19:
                    vivod.setTextSize(textSize - 32);
                    break;
                case 20:
                    vivod.setTextSize(textSize - 34);
                    break;
                case 21:
                    vivod.setTextSize(textSize - 36);
                    break;
                case 22:
                    vivod.setTextSize(textSize - 38);
                    break;
                case 23:
                    vivod.setTextSize(textSize - 40);
                    break;
                case 24:
                    vivod.setTextSize(textSize - 42);
                    break;
                case 25:
                    vivod.setTextSize(textSize - 44);
                    break;
                case 26:
                    vivod.setTextSize(textSize - 45);
                    break;
                case 27:
                    vivod.setTextSize(textSize - 46);
                    break;
                case 28:
                    vivod.setTextSize(textSize - 47);
                    break;
                case 29:
                    vivod.setTextSize(textSize - 48);
                    break;
                case 30:
                    vivod.setTextSize(textSize - 49);
                    break;
            }
        }else{
            vivod.setTextSize(textSize - 49);
        }
    }

    public void displayDisplay () {
        textSizeChange ();
        if (display.equals("")) {
            TextView vivod = (TextView) findViewById(R.id.digital);
            vivod.setText("0");
        }else {
            TextView vivod = (TextView) findViewById(R.id.digital);
            vivod.setText(display);
        }
    }

    public void VvodCifr(String VvodCifri) {

        if (function){
            function = false;
            display = "";
        }

        if(display.equals("0") && !VvodCifri.equals(".")) {
            display = "";
        }

        if (VvodCifri.equals(".") && display.contains(".")){
            VvodCifri = "";
        }

        if (VvodCifri.equals(".") && display.equals("")){
            display = "0";
        }

        if(display.length()>=maxDigits){
            VvodCifri = "";
        }

        display = display.concat(VvodCifri);
        displayDisplay();
        doubleClockError = false;

    }

    public void onClickC() {
        digit1 = BigDecimal.valueOf(0);
        digit2 = BigDecimal.valueOf(0);
        display = "";
        doubleClockError = false;
        methodRele = 0;
        function = false;
        displayDisplay ();
    }

    public void onTouchListenerC () {
        Button button = (Button) findViewById(R.id.buttonC);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.buttonC)).setBackgroundColor(0xFF95969B);
                        onClickC();
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.buttonC)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListenerSlojenie (){
        Button button = (Button) findViewById(R.id.buttonSlojenie);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.buttonSlojenie)).setBackgroundColor(0xFFB46D16);
                        Main main = new Main();
                        onClickSwitch();
                        methodRele = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.buttonSlojenie)).setBackgroundColor(0xFFFA9112);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListenerVichetanie (){
        Button button = (Button) findViewById(R.id.buttonVichetanie);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.buttonVichetanie)).setBackgroundColor(0xFFB46D16);
                        Main main = new Main();
                        onClickSwitch();
                        methodRele = 2;
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.buttonVichetanie)).setBackgroundColor(0xFFFA9112);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListenerDelenie (){
        Button button = (Button) findViewById(R.id.buttonDelenie);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.buttonDelenie)).setBackgroundColor(0xFFB46D16);
                        Main main = new Main();
                        onClickSwitch();
                        methodRele = 4;
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.buttonDelenie)).setBackgroundColor(0xFFFA9112);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListenerUmnojenie (){
        Button button = (Button) findViewById(R.id.buttonUmnojenie);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.buttonUmnojenie)).setBackgroundColor(0xFFB46D16);
                        Main main = new Main();
                        onClickSwitch();
                        methodRele = 3;
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.buttonUmnojenie)).setBackgroundColor(0xFFFA9112);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListenerRavno (){
        Button button = (Button) findViewById(R.id.buttonRavno);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.buttonRavno)).setBackgroundColor(0xFFB46D16);

                        onClickSwitch();
                        methodRele = 0;

                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.buttonRavno)).setBackgroundColor(0xFFFA9112);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onClickSwitch() {
        if(!doubleClockError){
            switch (methodRele){
                case 0:
                    FirstOpen();
                    break;
                case 1:
                    Slojenie();
                    break;
                case 2:
                    Vichetanie();
                    break;
                case 3:
                    Umnojenie();
                    break;
                case 4:
                    Delenie();
                    break;
            }
            doubleClockError = true;
        }
    }

    public void FirstOpen() {
        digit2 = new BigDecimal(display);
        function = true;
    }

    public void eraseLastZero() {
        if(display.length()>2 && display.contains(".")) {
            String zero1 = display.substring(display.length() - 1, display.length());
            while (zero1.equals("0")) {
                display = display.substring(0, display.length() - 1);
                zero1 = display.substring(display.length() - 1, display.length());
            }
                String tochka = display.substring(display.length() - 1, display.length());
                if (tochka.equals(".")) {
                   display = display.substring(0, display.length() - 1);
               }
        }

    }

    public void Slojenie() {
        digit1 = new BigDecimal(display);
        digit1 = digit2.add(digit1);
        digit2 = digit1;
        display = digit1.toString();
        eraseLastZero();
        displayDisplay ();
        doubleClockError = false;
        function = true;
        digit1 = BigDecimal.valueOf(0);
        digit2 = new BigDecimal(display);
    }

    public void Vichetanie() {
        digit1 = new BigDecimal(display);
        digit1 = digit2.subtract(digit1);
        digit2 = digit1;
        display = digit1.toString();
        eraseLastZero();
        displayDisplay ();
        doubleClockError = false;
        function = true;
        digit1 = BigDecimal.valueOf(0);
    }

    public void Umnojenie() {
        digit1 = new BigDecimal(display);
        digit1 = digit2.multiply(digit1);
        digit2 = digit1;
        display = digit1.toString();
        eraseLastZero();
        displayDisplay ();
        doubleClockError = false;
        function = true;
        digit1 = BigDecimal.valueOf(0);
    }

    public void Delenie() {

        digit1 = new BigDecimal(display);
                if(!digit1.equals(BigDecimal.valueOf(0))) {
                    digit1 = digit2.divide(digit1, 30, BigDecimal.ROUND_CEILING);
                    digit2 = digit1;
                    display = digit1.toString();
                    eraseLastZero();
                    displayDisplay ();
                }
        else {
                    TextView vivod = (TextView) findViewById(R.id.digital);
                    display = "Error";
                    displayDisplay ();;
                    digit2 = BigDecimal.valueOf(0);
                    methodRele = 0;
                    display = "";
                }
        doubleClockError = false;
        function = true;
        digit1 = BigDecimal.valueOf(0);
    }
}
