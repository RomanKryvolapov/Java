package com.calculator.my.calculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.TextView;
import java.math.*;
import android.graphics.drawable.Drawable;
import java.time.format.TextStyle;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        TextView stroka0 = (TextView) findViewById(R.id.digital);
        stroka0.setTextSize(textSize);
        stroka0.setText("Hello!");
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

    public void onClickC(View c) {
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

    public void onClickTochka(View c) {
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

    public void onClick0(View c) {
        Main main = new Main();
        String digitLength = digit1.toString();
        if (digitLength.length() + zeroOverload < razrad) {

            if (tochka) {
                display = display + "0";
                displayDisplay ();
                tochkaPosition++;
                zeroOverload++;
            } else if(!tochka && !display.equals("0")){
                display = display + "0";
                displayDisplay ();
                BigDecimal input1 = new BigDecimal("10");
                digit1 = digit1.multiply(input1);
            }else{
                display = "0";
                displayDisplay ();
            }
            bottonsError = false;
            textSizeIndex = display.length() + zeroOverload;
            textSizeChange();
        }
    }

    public void onClick1(View c) {
        VvodCifr("1");
    }
    public void onClick2(View c) {
        VvodCifr("2");
    }
    public void onClick3(View c) {
        VvodCifr("3");
    }
    public void onClick4(View c) {
        VvodCifr("4");
    }
    public void onClick5(View c) {
        VvodCifr("5");
    }
    public void onClick6(View c) {
        VvodCifr("6");
    }
    public void onClick7(View c) {
        VvodCifr("7");
    }
    public void onClick8(View c) {
        VvodCifr("8");
    }
    public void onClick9(View c) {
        VvodCifr("9");
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


    public void onClickSlojenie(View c) {
        Main main = new Main();
        onClickSwitch();
        methodRele = 1;
    }
    public void onClickVichetanie(View c) {
        Main main = new Main();
        onClickSwitch();
        methodRele = 2;
    }
    public void onClickUmnojenie(View c) {
        Main main = new Main();
        onClickSwitch();
        methodRele = 3;
    }
    public void onClickDenelie(View c) {
        Main main = new Main();
        onClickSwitch();
        methodRele = 4;
    }
    public void onClickRavno(View c) {
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
        digit1 = digit2.divide(digit1, razrad, BigDecimal.ROUND_CEILING);
        Deistciya();
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