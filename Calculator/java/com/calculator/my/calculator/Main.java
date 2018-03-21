package com.calculator.my.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.*;
import android.view.MotionEvent;


public class Main extends AppCompatActivity {

    BigDecimal digit1 = new BigDecimal("0");
    BigDecimal digit2 = new BigDecimal("0");
    int methodRele = 0;
    boolean tochka = false;
    boolean bottonsError = false;
    int tochkaPosition = 0;
    int zeroOverload = 0;
    final int razrad = 30;
    String display = "";
    final int textSize = 80;
    final int textSizeChangeIndex = 11;
    int textSizeIndex = 0;

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
        onTouchListener0();
        onTouchListener1();
        onTouchListener2();
        onTouchListener3();
        onTouchListener4();
        onTouchListener5();
        onTouchListener6();
        onTouchListener7();
        onTouchListener8();
        onTouchListener9();
        onTouchListenerSlojenie ();
        onTouchListenerVichetanie ();
        onTouchListenerUmnojenie ();
        onTouchListenerDelenie ();
        onTouchTochka ();
        onTouchRavno ();

    }
    public void textSizeChange () {
        Main main = new Main();
        if(textSizeIndex > 0 && textSizeIndex < razrad && textSizeIndex > textSizeChangeIndex) {
            TextView vivod = (TextView) findViewById(R.id.digital);
           switch (textSizeIndex){
               case 12:
                   vivod.setTextSize(textSize-5);
                   break;
               case 13:
                   vivod.setTextSize(textSize-10);
                   break;
               case 14:
                   vivod.setTextSize(textSize-15);
                   break;
               case 15:
                   vivod.setTextSize(textSize-20);
                   break;
               case 16:
                   vivod.setTextSize(textSize-23);
                   break;
               case 17:
                   vivod.setTextSize(textSize-26);
                   break;
               case 18:
                   vivod.setTextSize(textSize-29);
                   break;
               case 19:
                   vivod.setTextSize(textSize-32);
                   break;
               case 20:
                   vivod.setTextSize(textSize-34);
                   break;
               case 21:
                   vivod.setTextSize(textSize-36);
                   break;
               case 22:
                   vivod.setTextSize(textSize-38);
                   break;
               case 23:
                   vivod.setTextSize(textSize-40);
                   break;
               case 24:
                   vivod.setTextSize(textSize-42);
                   break;
               case 25:
                   vivod.setTextSize(textSize-44);
                   break;
               case 26:
                   vivod.setTextSize(textSize-45);
                   break;
               case 27:
                   vivod.setTextSize(textSize-46);
                   break;
               case 28:
                   vivod.setTextSize(textSize-47);
                   break;
               case 29:
                   vivod.setTextSize(textSize-48);
                   break;
               case 30:
                   vivod.setTextSize(textSize-49);
                   break;
           }
        }
        else if (textSizeIndex==0){
            TextView vivod = (TextView) findViewById(R.id.digital);
            vivod.setTextSize(textSize);
        }
        else if (textSizeIndex >= razrad && textSizeIndex > textSizeChangeIndex){
            TextView vivod = (TextView) findViewById(R.id.digital);
            vivod.setTextSize(textSize-49);
        }
    }

    public void displayDisplay () {
        Main main = new Main();
        TextView vivod = (TextView) findViewById(R.id.digital);
        vivod.setText(display);
    }

    public void VvodCifr(String VvodCifri) {
        Main main = new Main();
        String digitLength = digit1.toString();
        if (digitLength.length() + zeroOverload < razrad) {
            display = display + VvodCifri;
            textSizeIndex = display.length();
            textSizeChange();
            displayDisplay ();
            if (tochka) {
                tochkaPosition++;
                BigDecimal input1 = new BigDecimal("10");
                BigDecimal addOne = new BigDecimal(VvodCifri);
                for (int i = 0; tochkaPosition > i; i++) {
                    addOne = addOne.divide(input1, i + 2, BigDecimal.ROUND_UNNECESSARY);
                }
                digit1 = digit1.add(addOne);
            }
            else {
                BigDecimal input1 = new BigDecimal("10");
                BigDecimal addOne = new BigDecimal(VvodCifri);
                digit1 = digit1.multiply(input1);
                digit1 = digit1.add(addOne);
            }
            zeroOverload = 0;
            bottonsError = false;
        }
    }

    public void onClickC() {
        Main main = new Main();
        TextView vivod = (TextView) findViewById(R.id.digital);
        vivod.setTextSize(textSize);
        vivod.setText("0");
        digit1 = new BigDecimal("0");
        digit2 = new BigDecimal("0");
        methodRele = 0;
        tochka = false;
        bottonsError = false;
        tochkaPosition = 0;
        zeroOverload = 0;
        display = "";
        textSizeIndex = 0;
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

    public void onTouchListener0 (){
        Button button = (Button) findViewById(R.id.button0);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.button0)).setBackgroundColor(0xFF95969B);
                        onClick0();
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.button0)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListener1 (){
        Button button = (Button) findViewById(R.id.button7);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.button7)).setBackgroundColor(0xFF95969B);
                        VvodCifr("1");
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.button7)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListener2 (){
        Button button = (Button) findViewById(R.id.button8);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.button8)).setBackgroundColor(0xFF95969B);
                        VvodCifr("2");
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.button8)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListener3 (){
        Button button = (Button) findViewById(R.id.button9);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.button9)).setBackgroundColor(0xFF95969B);
                        VvodCifr("3");
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.button9)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListener4 (){
        Button button = (Button) findViewById(R.id.button4);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.button4)).setBackgroundColor(0xFF95969B);
                        VvodCifr("4");
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.button4)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListener5 (){
        Button button = (Button) findViewById(R.id.button5);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.button5)).setBackgroundColor(0xFF95969B);
                        VvodCifr("5");
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.button5)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListener6 (){
        Button button = (Button) findViewById(R.id.button6);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.button6)).setBackgroundColor(0xFF95969B);
                        VvodCifr("6");
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.button6)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListener7 (){
        Button button = (Button) findViewById(R.id.button1);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.button1)).setBackgroundColor(0xFF95969B);
                        VvodCifr("7");
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.button1)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListener8 (){
        Button button = (Button) findViewById(R.id.button2);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.button2)).setBackgroundColor(0xFF95969B);
                        VvodCifr("8");
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.button2)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchListener9 (){
        Button button = (Button) findViewById(R.id.button3);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.button3)).setBackgroundColor(0xFF95969B);
                        VvodCifr("9");
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.button3)).setBackgroundColor(0xFFd0d1d5);
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

    public void onTouchTochka (){
        Button button = (Button) findViewById(R.id.buttonTochka);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.buttonTochka)).setBackgroundColor(0xFF95969B);
                        onClickTochka();
                        break;
                    case MotionEvent.ACTION_UP:
                        ((Button) findViewById(R.id.buttonTochka)).setBackgroundColor(0xFFd0d1d5);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void onTouchRavno (){
        Button button = (Button) findViewById(R.id.buttonRavno);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((Button) findViewById(R.id.buttonRavno)).setBackgroundColor(0xFFB46D16);
                        onClickRavno();
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

    public void onClickTochka() {
        Main main = new Main();
        String digitLength = digit1.toString();
        if (digitLength.length() + zeroOverload < razrad && !tochka) {
            if(display.length()==0){
                display = display + "0";
            }
            display = display + ".";
            displayDisplay ();
            zeroOverload = 0;
            bottonsError = false;
            textSizeIndex = display.length();
            textSizeChange();
            tochka = true;
        }
    }

    public void onClick0() {
        Main main = new Main();
        String digitLength = digit1.toString();
        if (digitLength.length() + zeroOverload < razrad) {
            if (tochka) {
                display = display + "0";
                tochkaPosition++;
                zeroOverload++;
            } else if(!tochka && !display.equals("0")){
                display = display + "0";
                BigDecimal input1 = new BigDecimal("10");
                digit1 = digit1.multiply(input1);
            }else{
                display = "0";
            }
            bottonsError = false;
            textSizeIndex = display.length();
            textSizeChange();
            displayDisplay ();
        }
    }

    public void onClickSwitch() {
        Main main = new Main();
        if(!bottonsError){
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
            bottonsError = true;
        }
    }

    public void onClickRavno() {
        Main main = new Main();
        onClickSwitch();
        digit1 = digit2;
        methodRele = 0;
        bottonsError = false;
        textSizeIndex = display.length();
        display = "";
        tochka = false;
        tochkaPosition = 0;
        zeroOverload = 0;
    }

    public void FirstOpen() {
        Main main = new Main();
        digit2 = digit1;
        digit1 = new BigDecimal("0");
        tochka = false;
        tochkaPosition = 0;
        zeroOverload = 0;
        textSizeIndex = display.length();
        textSizeChange();
        display = "";
    }

    public void eraseLastZero() {
        Main main = new Main();
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
            textSizeIndex = display.length();
        }

       /* else if(display.length()>2 && !display.contains(".")){
            String zero2 = display.substring(0, 1);
            while (zero2.equals("0")) {
                display = display.substring(1, display.length());
                zero2 = display.substring(0, 1);
            }
            textSizeIndex = display.length();
        }*/
    }

    public void Slojenie() {
        Main main = new Main();
        digit1 = digit2.add(digit1);
        Deistciya();
    }

    public void Vichetanie() {
        Main main = new Main();
        digit1 = digit2.subtract(digit1);
        Deistciya();
    }

    public void Umnojenie() {

        Main main = new Main();
        digit1 = digit2.multiply(digit1);
        Deistciya();
    }

    public void Delenie() {
        Main main = new Main();
        if(!digit1.equals(new BigDecimal("0"))){
        digit1 = digit2.divide(digit1, razrad, BigDecimal.ROUND_CEILING);
        Deistciya();
        }else {
            TextView vivod = (TextView) findViewById(R.id.digital);
            vivod.setTextSize(textSize);
            vivod.setText("error / 0");
            digit1 = new BigDecimal("0");
            digit2 = new BigDecimal("0");
            methodRele = 0;
            tochka = false;
            bottonsError = false;
            tochkaPosition = 0;
            zeroOverload = 0;
            display = "";
            textSizeIndex = 0;
        }
    }

    public void Deistciya() {
        digit2 = digit1;
        display = digit1.toString();
        eraseLastZero();
        displayDisplay ();
        digit1 = new BigDecimal("0");
        tochka = false;
        tochkaPosition = 0;
        zeroOverload = 0;
        textSizeIndex = display.length();
        textSizeChange();
        display = "";
    }
}