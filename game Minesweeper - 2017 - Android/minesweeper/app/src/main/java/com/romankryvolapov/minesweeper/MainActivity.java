package com.romankryvolapov.minesweeper;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static int clickedQuantity = 0;
    static int minePercent = 15;
    static int Quantity = 0;
    boolean gameOver = false;
    static int x1 = 0;
    static int y1 = 0;
    static int proportion_correction = 6;

    static int xSize = 12;
    static int ySize = 10;
    static int Size = 10;
    static long startTime = 0;
    static long stopTime = 0;
    static long yourTime = 0;

    int display[][] = new int[xSize][ySize];
    int identifier[][] = new int[xSize][ySize];
    boolean clicked[][] = new boolean[xSize][ySize];
    boolean zeroMap[][] = new boolean[xSize][ySize];
    static Button buttonArray[][] = new Button[xSize][ySize];
    static int buttonArrayID[][] = new int[xSize][ySize];

    static int layout1Height = 14;
    static final int buttonMargin = 3;
    static int buttonID = 1000;

    static int displayWidth = 0;
    static int displayHeight = 0;

    TextView myTextView;

    Button buttonReset;

    Button buttonSizePlus;

    Button buttonSizeMinus;

    ConstraintLayout generalLayout;

    ConstraintLayout constraintLayout1;

    ConstraintLayout constraintLayout2;

    SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sPref = getPreferences(MODE_PRIVATE);
//        sPref.edit().clear().commit();
//        sPref.edit().clear().apply();
        Size = sPref.getInt("Size", 0);

        if(Size<3)
            Size=10;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layout1Height = 14;
            xSize = Size+proportion_correction;
            ySize = Size;
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layout1Height = 8;
            xSize = Size-2;
            ySize = Size+3;
        }
        else {
            layout1Height = 14;
            xSize = Size+proportion_correction;
            ySize = Size;
        }

        display = new int[xSize][ySize];
        identifier = new int[xSize][ySize];
        clicked = new boolean[xSize][ySize];
        zeroMap = new boolean[xSize][ySize];
        buttonArray = new Button[xSize][ySize];
        buttonArrayID = new int[xSize][ySize];


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayWidth = size.x;
        displayHeight = size.y;

        generalLayout = (ConstraintLayout) findViewById(R.id.generalLayout);
        constraintLayout1 = new ConstraintLayout(this);
        constraintLayout2 = new ConstraintLayout(this);


        createLayout1();
        createLayout2();
        createTopButtons();
        createButtons();
        setConstraints();

        resetListener();

        clickedQuantity = 0;
        changeColor(R.color.colorPrimary);
        clickedTOfalse();
        Quantity = 0;
        displayTOzero();
        mineCreate();
        mineQuantity();
        digitsCreateOn();
        testOut2();
        gameOver = false;
        mineRender();

        PlusMinusListiner();
        setStatusBarColor(R.color.colorPrimary);

        sPref = getPreferences(MODE_PRIVATE);
        if(sPref.getLong("Time" + Size, 0)!=0)
            Toast.makeText(getApplicationContext(), "Size " + ySize + " x " + xSize +
                    "\n\nBest time\n\n" + sPref.getLong("Time" + Size, 0) + " second", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "Size " + ySize + " x " + xSize, Toast.LENGTH_LONG).show();





//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//
//            }
//        });

    }

    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, color));
        }
    }

    void openZeroWhile(int x4, int y4){
        boolean nullZero = true;
        openZero(x4,y4);
        while (nullZero) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    if (zeroMap[x][y]) {
                        openZero(x,y);
                    }
                }
            }
            nullZero = false;
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    if (zeroMap[x][y]) {
                        nullZero = true;
                    }
                }
            }
        }
    }

    void openZero(int x3, int y3) {
        clicked[x3][y3]=true;
        zeroMap[x3][y3]=false;
        if (y3 < ySize - 1)
        {
            if (display[x3][y3 + 1] == 0 && !clicked[x3][y3 + 1])
            {
                zeroMap[x3][y3 + 1] = true;
            }
            makeClicked(x3, y3 + 1);
        }
        if (y3 > 0)
        {
            if (display[x3][y3 - 1] == 0 && !clicked[x3][y3 - 1])
            {
                zeroMap[x3][y3 - 1] = true;
            }
            makeClicked(x3, y3 - 1);
        }
        if (x3 < xSize - 1)
        {
            if (display[x3 + 1][y3] == 0 && !clicked[x3 + 1][y3])
            {
                zeroMap[x3 + 1][y3] = true;
            }
            makeClicked(x3 + 1, y3);
        }
        if (x3 > 0)
        {
            if (display[x3 - 1][y3] == 0 && !clicked[x3 - 1][y3])
            {
                zeroMap[x3 - 1][y3] = true;
            }
            makeClicked(x3 - 1, y3);
        }
        if (x3 < xSize - 1 && y3 < ySize - 1)
        {
            if (display[x3 + 1][y3 + 1] == 0 && !clicked[x3 + 1][y3 + 1])
            {
                zeroMap[x3 + 1][y3 + 1] = true;
            }
            makeClicked(x3 + 1, y3 + 1);
        }
        if (x3 > 0 && y3 > 0)
        {
            if (display[x3 - 1][y3 - 1] == 0 && !clicked[x3 - 1][y3 - 1])
            {
                zeroMap[x3 - 1][y3 - 1] = true;
            }
            makeClicked(x3 - 1, y3 - 1);
        }
        if (x3 < xSize - 1 && y3 > 0)
        {
            if (display[x3 + 1][y3 - 1] == 0 && !clicked[x3 + 1][y3 - 1])
            {
                zeroMap[x3 + 1][y3 - 1] = true;
            }
            makeClicked(x3 + 1, y3 - 1);
        }
        if (x3 > 0 && y3 < ySize - 1)
        {
            if (display[x3 - 1][y3 + 1] == 0 && !clicked[x3 - 1][y3 + 1])
            {
                zeroMap[x3 - 1][y3 + 1] = true;
            }
            makeClicked(x3 - 1, y3 + 1);
        }

    }

    void makeClicked(int x4, int y4) {
        Animation anim = null;
        if (!clicked[x4][y4]) {
            if (clickedQuantity > 1) {
                clickedQuantity--;
                buttonArray[x4][y4].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                buttonArray[x4][y4].setBackgroundResource(R.drawable.button_style_white);
                if (display[x4][y4] != 0)
                    buttonArray[x4][y4].setText(Integer.toString(display[x4][y4]));
                clicked[x4][y4] = true;
                mineQuantity();

                anim = null;
                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha_1_0);
                buttonArray[x4][y4].startAnimation(anim);
                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha_0_1);
                buttonArray[x4][y4].startAnimation(anim);
                anim = null;


            }
            else{
                buttonArray[x4][y4].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                buttonArray[x4][y4].setBackgroundResource(R.drawable.button_style_white);
                if (display[x4][y4] != 0)
                    buttonArray[x4][y4].setText(Integer.toString(display[x4][y4]));
                changeColor(R.color.colorGameWin);
                anim = null;
                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha_1_0);
                buttonArray[x4][y4].startAnimation(anim);
                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha_0_1);
                buttonArray[x4][y4].startAnimation(anim);
                anim = null;
                testOut3();

            }
        }
    }

    void createButtons() {

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                buttonID++;
                buttonArray[x][y] = new Button(this);
                buttonArrayID[x][y] = buttonID;
                buttonArray[x][y].setId(buttonID);
                buttonArray[x][y].setGravity(Gravity.CENTER);
                buttonArray[x][y].setTextColor(getResources().getColor(R.color.colorWhite));
                buttonArray[x][y].setBackgroundResource(R.drawable.button_style);
                constraintLayout2.addView(buttonArray[x][y]);
            }
        }
    }

    void setConstraints() {

        // супер мега код по привязке поля кнопок

        ConstraintSet constraintSetButtons = new ConstraintSet();

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {

                if (x == 0 && y == 0) //1
                {
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                } else if (x == 0 && y != 0 && y != ySize - 1) //2
                {
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                } else if (x == 0 && y == ySize - 1) //3
                {
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                } else if (x != xSize - 1 && x != 0 && y == ySize - 1) {
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                } else if (x == xSize - 1 && y == ySize - 1) {
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                } else if (x == xSize - 1 && y != ySize - 1 && y != 0) {
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                } else if (x == xSize - 1 && y == 0) {
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                } else if (x != 0 && x != xSize - 1 && y == 0) {
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                } else {
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButtons.connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                }
                // Margin
                constraintSetButtons.setMargin(buttonArray[x][y].getId(), ConstraintSet.END, buttonMargin);
                constraintSetButtons.setMargin(buttonArray[x][y].getId(), ConstraintSet.START, buttonMargin);
                constraintSetButtons.setMargin(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonMargin);
                constraintSetButtons.setMargin(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonMargin);
                // Размер
                constraintSetButtons.constrainWidth(buttonArray[x][y].getId(), ConstraintSet.MATCH_CONSTRAINT);
                constraintSetButtons.constrainHeight(buttonArray[x][y].getId(), ConstraintSet.MATCH_CONSTRAINT);
                constraintSetButtons.applyTo(constraintLayout2);
                constraintSetButtons.clear(buttonArray[x][y].getId());

            }
        }
    }

    void createLayout1() {

        int constraintLayout1ID = 10001;
        constraintLayout1.setId(constraintLayout1ID);
        constraintLayout1.setBackgroundColor(getResources().getColor(R.color.constraintLayout1));

        generalLayout.addView(constraintLayout1);


        Log.d("status", "generalLayout.addView(constraintLayout1)");

        ConstraintSet constraintSetConstraintLayout1 = new ConstraintSet();
        constraintSetConstraintLayout1.clone(generalLayout);
        constraintSetConstraintLayout1.connect(constraintLayout1.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSetConstraintLayout1.connect(constraintLayout1.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSetConstraintLayout1.connect(constraintLayout1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSetConstraintLayout1.connect(constraintLayout1.getId(), ConstraintSet.BOTTOM, constraintLayout2.getId(), ConstraintSet.TOP);
        constraintSetConstraintLayout1.constrainWidth(constraintLayout1.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetConstraintLayout1.constrainHeight(constraintLayout1.getId(), displayHeight / layout1Height);
        constraintSetConstraintLayout1.applyTo(generalLayout);

        Log.d("status", "constraintSetConstraintLayout1.applyTo(generalLayout)");

    }

    void createLayout2() {

        int constraintLayout2ID = 10002;
        constraintLayout2.setId(constraintLayout2ID);
        constraintLayout2.setBackgroundColor(getResources().getColor(R.color.constraintLayout1));

        generalLayout.addView(constraintLayout2);
        Log.d("status", "generalLayout.addView(constraintLayout2)");

        ConstraintSet constraintSetConstraintLayout2 = new ConstraintSet();
        constraintSetConstraintLayout2.clone(generalLayout);
        constraintSetConstraintLayout2.connect(constraintLayout2.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSetConstraintLayout2.connect(constraintLayout2.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSetConstraintLayout2.connect(constraintLayout2.getId(), ConstraintSet.TOP, constraintLayout1.getId(), ConstraintSet.BOTTOM);
        constraintSetConstraintLayout2.connect(constraintLayout2.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSetConstraintLayout2.constrainWidth(constraintLayout2.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetConstraintLayout2.constrainHeight(constraintLayout2.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetConstraintLayout2.applyTo(generalLayout);

        Log.d("status", "constraintSetConstraintLayout2.applyTo(generalLayout)");

    }

    void createTopButtons() {

        myTextView = new TextView(this);
        int myTextViewID = 10003;
        myTextView.setId(myTextViewID);
        myTextView.setText("Some text");
        myTextView.setGravity(Gravity.CENTER);
        myTextView.setTextColor(getResources().getColor(R.color.colorWhite));
        myTextView.setBackgroundResource(R.drawable.button_style);
        myTextView.setBackgroundColor(getResources().getColor(R.color.constraintLayout1));

        buttonReset = new Button(this);
        int buttonResetID = 10004;
        buttonReset.setId(buttonResetID);
        buttonReset.setText("RESET");
        buttonReset.setGravity(Gravity.CENTER);
        buttonReset.setTextColor(getResources().getColor(R.color.colorWhite));
        buttonReset.setBackgroundResource(R.drawable.button_style);


        buttonSizePlus = new Button(this);
        int buttonSizePlusID = 10005;
        buttonSizePlus.setId(buttonSizePlusID);
        buttonSizePlus.setText("SIZE +");
        buttonSizePlus.setGravity(Gravity.CENTER);
        buttonSizePlus.setTextColor(getResources().getColor(R.color.colorWhite));
        buttonSizePlus.setBackgroundResource(R.drawable.button_style);

        buttonSizeMinus = new Button(this);
        int buttonSizeMinusID = 10006;
        buttonSizeMinus.setId(buttonSizeMinusID);
        buttonSizeMinus.setText("SIZE -");
        buttonSizeMinus.setGravity(Gravity.CENTER);
        buttonSizeMinus.setTextColor(getResources().getColor(R.color.colorWhite));
        buttonSizeMinus.setBackgroundResource(R.drawable.button_style);

        constraintLayout1.addView(myTextView);
        constraintLayout1.addView(buttonReset);
        constraintLayout1.addView(buttonSizePlus);
        constraintLayout1.addView(buttonSizeMinus);

        ConstraintSet constraintSetMyTextView = new ConstraintSet();
        constraintSetMyTextView.connect(myTextView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSetMyTextView.connect(myTextView.getId(), ConstraintSet.RIGHT, buttonSizeMinus.getId(), ConstraintSet.LEFT);
        constraintSetMyTextView.connect(myTextView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSetMyTextView.connect(myTextView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSetMyTextView.setMargin(myTextView.getId(), ConstraintSet.END, displayWidth / 30);
        constraintSetMyTextView.setMargin(myTextView.getId(), ConstraintSet.START, displayWidth / 30);
        constraintSetMyTextView.setMargin(myTextView.getId(), ConstraintSet.TOP, displayWidth / 50);
        constraintSetMyTextView.setMargin(myTextView.getId(), ConstraintSet.BOTTOM, displayWidth / 50);
        constraintSetMyTextView.constrainWidth(myTextView.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetMyTextView.constrainHeight(myTextView.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetMyTextView.applyTo(constraintLayout1);

        ConstraintSet constraintSetButtonReset = new ConstraintSet();
        constraintSetButtonReset.connect(buttonReset.getId(), ConstraintSet.LEFT, buttonSizeMinus.getId(), ConstraintSet.RIGHT);
        constraintSetButtonReset.connect(buttonReset.getId(), ConstraintSet.RIGHT, buttonSizePlus.getId(), ConstraintSet.LEFT);
        constraintSetButtonReset.connect(buttonReset.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSetButtonReset.connect(buttonReset.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSetButtonReset.setMargin(buttonReset.getId(), ConstraintSet.END, 0);
        constraintSetButtonReset.setMargin(buttonReset.getId(), ConstraintSet.START, 0);
        constraintSetButtonReset.setMargin(buttonReset.getId(), ConstraintSet.TOP, displayWidth / 50);
        constraintSetButtonReset.setMargin(buttonReset.getId(), ConstraintSet.BOTTOM, displayWidth / 50);
        constraintSetButtonReset.constrainWidth(buttonReset.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetButtonReset.constrainHeight(buttonReset.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetButtonReset.applyTo(constraintLayout1);

        ConstraintSet constraintSetButtonSizePlus = new ConstraintSet();
        constraintSetButtonSizePlus.connect(buttonSizePlus.getId(), ConstraintSet.LEFT, buttonReset.getId(), ConstraintSet.RIGHT);
        constraintSetButtonSizePlus.connect(buttonSizePlus.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSetButtonSizePlus.connect(buttonSizePlus.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSetButtonSizePlus.connect(buttonSizePlus.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSetButtonSizePlus.setMargin(buttonSizePlus.getId(), ConstraintSet.END, displayWidth / 30);
        constraintSetButtonSizePlus.setMargin(buttonSizePlus.getId(), ConstraintSet.START, displayWidth / 30);
        constraintSetButtonSizePlus.setMargin(buttonSizePlus.getId(), ConstraintSet.TOP, displayWidth / 50);
        constraintSetButtonSizePlus.setMargin(buttonSizePlus.getId(), ConstraintSet.BOTTOM, displayWidth / 50);
        constraintSetButtonSizePlus.constrainWidth(buttonSizePlus.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetButtonSizePlus.constrainHeight(buttonSizePlus.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetButtonSizePlus.applyTo(constraintLayout1);

        ConstraintSet constraintSetButtonSizeMinus = new ConstraintSet();
        constraintSetButtonSizeMinus.connect(buttonSizeMinus.getId(), ConstraintSet.LEFT, myTextView.getId(), ConstraintSet.RIGHT);
        constraintSetButtonSizeMinus.connect(buttonSizeMinus.getId(), ConstraintSet.RIGHT, buttonReset.getId(), ConstraintSet.LEFT);
        constraintSetButtonSizeMinus.connect(buttonSizeMinus.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSetButtonSizeMinus.connect(buttonSizeMinus.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSetButtonSizeMinus.setMargin(buttonSizeMinus.getId(), ConstraintSet.END, displayWidth / 30);
        constraintSetButtonSizeMinus.setMargin(buttonSizeMinus.getId(), ConstraintSet.START, displayWidth / 30);
        constraintSetButtonSizeMinus.setMargin(buttonSizeMinus.getId(), ConstraintSet.TOP, displayWidth / 50);
        constraintSetButtonSizeMinus.setMargin(buttonSizeMinus.getId(), ConstraintSet.BOTTOM, displayWidth / 50);
        constraintSetButtonSizeMinus.constrainWidth(buttonSizeMinus.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetButtonSizeMinus.constrainHeight(buttonSizeMinus.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetButtonSizeMinus.applyTo(constraintLayout1);

    }

    public void mineRender() {

        for (int x = 0; x < xSize; x++) {
            y1 = 0;
            for (int y = 0; y < ySize; y++) {
                mineListener();
                y1++;
            }
            x1++;
        }
        x1 = 0;
        y1 = 0;
    }

    public void mineListener() {

        buttonArray[x1][y1].setOnTouchListener(new View.OnTouchListener() {
            int buttonText = display[x1][y1];
            Button buttonNext = buttonArray[x1][y1];
            int x2 = x1;
            int y2 = y1;
            Boolean clicked1 = false;
            Animation anim = null;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!gameOver && !clicked[x2][y2]) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            mineQuantity();

                            if (buttonText == 9) {
                                gameOver = true;
                                buttonNext.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                buttonNext.setBackgroundResource(R.drawable.button_style_win);
                                buttonNext.setText("M");
                                changeColor(R.color.colorGameOver);
                                testOut1();
                            } else {
                                anim = null;
                                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha_1_0);
                                buttonNext.startAnimation(anim);
                                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha_0_1);
                                buttonNext.startAnimation(anim);
                                anim = null;
                                makeClicked(x2, y2);
                                if(buttonText == 0)
                                    openZeroWhile(x2, y2);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            buttonNext.setBackgroundResource(R.drawable.button_style_white);
                            clicked[x2][y2]=true;
                            clicked1 = true;
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            buttonNext.setBackgroundResource(R.drawable.button_style_white);
                            clicked[x2][y2]=true;
                            clicked1 = true;
                            break;
                    }
                }
                return true;
            }
        });
    }

    public void mineCreate() {
        Random random1 = new Random();
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                int z = random1.nextInt(100);
                if (z < minePercent) {
                    display[x][y] = 9;
                    Quantity++;
                }

            }

        }
    }

    void PlusMinusListiner(){

        buttonSizeMinus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        buttonSizeMinus.setBackgroundResource(R.drawable.button_style_white);
                        buttonSizeMinus.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                        break;
                    case MotionEvent.ACTION_UP:
                        buttonReset.setBackgroundResource(R.drawable.button_style);
                        buttonSizePlus.setBackgroundResource(R.drawable.button_style);
                        buttonSizeMinus.setBackgroundResource(R.drawable.button_style);
                        buttonSizeMinus.setTextColor(getResources().getColor(R.color.colorWhite));

                        if(Size>4)
                        {
                            Size--;
                            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                layout1Height = 14;
                                xSize = Size+proportion_correction;
                                ySize = Size;
                            }
                            else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                layout1Height = 8;
                                xSize = Size-2;
                                ySize = Size+3;
                            }
                            else {
                                layout1Height = 14;
                                xSize = Size+proportion_correction;
                                ySize = Size;
                            }

                            sPref = getPreferences(MODE_PRIVATE);
                            if(sPref.getLong("Time" + Size, 0)!=0)
                                Toast.makeText(getApplicationContext(), "Size " + ySize + " x " + xSize +
                                        "\n\nBest time\n\n" + sPref.getLong("Time" + Size, 0) + " second", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Size " + ySize + " x " + xSize, Toast.LENGTH_LONG).show();

                            buttonID = 1000;
                            sPref = getPreferences(MODE_PRIVATE);
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putInt("Size", Size);
                            ed.commit();

                            display = new int[xSize][ySize];
                            identifier = new int[xSize][ySize];
                            clicked = new boolean[xSize][ySize];
                            zeroMap = new boolean[xSize][ySize];
                            buttonArray = new Button[xSize][ySize];
                            buttonArrayID = new int[xSize][ySize];

                            constraintLayout2.removeAllViews();

                            createButtons();
                            setConstraints();
                            mineListener();

                            clickedQuantity = 0;
                            changeColor(R.color.colorPrimary);

                            clickedTOfalse();
                            Quantity = 0;
                            displayTOzero();
                            mineCreate();
                            mineQuantity();
                            digitsCreateOn();
                            testOut2();
                            gameOver = false;
                            mineRender();
                        }

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });

        buttonSizePlus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        buttonSizePlus.setBackgroundResource(R.drawable.button_style_white);
                        buttonSizePlus.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                        break;
                    case MotionEvent.ACTION_UP:
                        buttonReset.setBackgroundResource(R.drawable.button_style);
                        buttonSizeMinus.setBackgroundResource(R.drawable.button_style);
                        buttonSizePlus.setBackgroundResource(R.drawable.button_style);
                        buttonSizePlus.setTextColor(getResources().getColor(R.color.colorWhite));

                        if(Size<20)
                        {
                            Size++;
                            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                layout1Height = 14;
                                xSize = Size+proportion_correction;
                                ySize = Size;
                            }
                            else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                layout1Height = 8;
                                xSize = Size-2;
                                ySize = Size+3;
                            }
                            else {
                                layout1Height = 14;
                                xSize = Size+proportion_correction;
                                ySize = Size;
                            }

                            sPref = getPreferences(MODE_PRIVATE);
                            if(sPref.getLong("Time" + Size, 0)!=0)
                                Toast.makeText(getApplicationContext(), "Size " + ySize + " x " + xSize +
                                        "\n\nBest time\n\n" + sPref.getLong("Time" + Size, 0) + " second", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Size " + ySize + " x " + xSize, Toast.LENGTH_LONG).show();

                            buttonID = 1000;
                            sPref = getPreferences(MODE_PRIVATE);
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putInt("Size", Size);
                            ed.commit();

                            display = new int[xSize][ySize];
                            identifier = new int[xSize][ySize];
                            clicked = new boolean[xSize][ySize];
                            zeroMap = new boolean[xSize][ySize];
                            buttonArray = new Button[xSize][ySize];
                            buttonArrayID = new int[xSize][ySize];

                            constraintLayout2.removeAllViews();

                            createButtons();
                            setConstraints();

                            mineListener();

                            clickedQuantity = 0;
                            changeColor(R.color.colorPrimary);
                            clickedTOfalse();
                            Quantity = 0;
                            displayTOzero();
                            mineCreate();
                            mineQuantity();
                            digitsCreateOn();
                            testOut2();
                            gameOver = false;
                            mineRender();
                        }

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });


    }

    public void clickedTOfalse() {

        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                clicked[x][y] = false;
                zeroMap[x][y] = false;
            }
        }
    }

    public void resetListener() {

        buttonReset.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        buttonReset.setBackgroundResource(R.drawable.button_style_white);
                        buttonReset.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case MotionEvent.ACTION_UP:
                        buttonReset.setBackgroundResource(R.drawable.button_style);
                        buttonReset.setTextColor(getResources().getColor(R.color.colorWhite));
                        reset();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    public void reset() {
        clickedQuantity = 0;
        changeColor(R.color.colorPrimary);
        clickedTOfalse();
        Quantity = 0;
        displayTOzero();
        mineCreate();
        mineQuantity();
        digitsCreateOn();
        testOut2();
        gameOver = false;
        mineRender();
    }

    public void displayTOzero() {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                display[x][y] = 0;
            }
        }
    }

    public void digitsCreateOn() {

        digitsCreate(0, -1);
        digitsCreate(0, +1);
        digitsCreate(-1, 0);
        digitsCreate(+1, 0);
        digitsCreate(+1, +1);
        digitsCreate(-1, -1);
        digitsCreate(+1, -1);
        digitsCreate(-1, +1);

    }

    public void digitsCreate(int x1, int y1) {

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                try {
                    if (display[x][y] == 9) {
                        if (display[x + x1][y + y1] != 9) {
                            display[x + x1][y + y1] = display[x + x1][y + y1] + 1;
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public void changeColor(int colorID) {

        constraintLayout1.setBackgroundColor(getResources().getColor(colorID));
        constraintLayout2.setBackgroundColor(getResources().getColor(colorID));
        myTextView.setBackgroundColor(getResources().getColor(colorID));
        setStatusBarColor(colorID);

    }

    public void mineQuantity() {
        if (clickedQuantity != 0) {
            TextView vivod = (TextView) myTextView;
            vivod.setText("" + (clickedQuantity));
        } else {
            TextView vivod = (TextView) myTextView;
            vivod.setText("" + (xSize * ySize - Quantity));
        }
    }

    public void testOut1() {
        stopTime = System.currentTimeMillis();
        Animation anim = null;
        changeColor(R.color.colorGameOver);
        myTextView.setText("Game Over!");
        buttonReset.setBackgroundResource(R.drawable.button_style_game_over);
        buttonSizePlus.setBackgroundResource(R.drawable.button_style_game_over);
        buttonSizeMinus.setBackgroundResource(R.drawable.button_style_game_over);
        for(int y=0; y<ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if (display[x][y] == 9) {
                    buttonArray[x][y].setText("M");
                    buttonArray[x][y].setBackgroundResource(R.drawable.button_style_game_over_mine);
                    buttonArray[x][y].setTextColor(getResources().getColor(R.color.colorWhite));

                } else {
                    if (!clicked[x][y]) {
                        buttonArray[x][y].setBackgroundResource(R.drawable.button_style_game_over);
                        if(display[x][y]!=0)
                            buttonArray[x][y].setText(Integer.toString(display[x][y]));
                        buttonArray[x][y].setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                }
            }
        }

        yourTime = ((stopTime - startTime)/1000)+1;
        Toast.makeText(getApplicationContext(), "You time is\n\n" + yourTime + " second", Toast.LENGTH_LONG).show();

    }

    public void testOut2() {
        changeColor(R.color.colorPrimary);
        buttonReset.setBackgroundResource(R.drawable.button_style);
        buttonSizePlus.setBackgroundResource(R.drawable.button_style);
        buttonSizeMinus.setBackgroundResource(R.drawable.button_style);
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                buttonArray[x][y].setText("");
                buttonArray[x][y].setBackgroundResource(R.drawable.button_style);
                buttonArray[x][y].setTextColor(getResources().getColor(R.color.colorWhite));
                if (display[x][y] != 9) {
                    clickedQuantity++;
                }
            }
        }
        startTime = System.currentTimeMillis();

    }

    public void testOut3() {
        stopTime = System.currentTimeMillis();
        changeColor(R.color.colorGameWin);
        myTextView.setText("You Win!");
        buttonReset.setBackgroundResource(R.drawable.button_style_win);
        buttonSizePlus.setBackgroundResource(R.drawable.button_style_win);
        buttonSizeMinus.setBackgroundResource(R.drawable.button_style_win);
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if(display[x][y] == 9) {
                    buttonArray[x][y].setText("M");
                    buttonArray[x][y].setBackgroundResource(R.drawable.button_style_win);
                    buttonArray[x][y].setTextColor(getResources().getColor(R.color.colorWhite));
                } else {
                    if (!clicked[x][y]) {
                        buttonArray[x][y].setBackgroundResource(R.drawable.button_style_white);
                        if(display[x][y]!=0)
                            buttonArray[x][y].setText(Integer.toString(display[x][y]));
                        buttonArray[x][y].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                }
            }
        }

        sPref = getPreferences(MODE_PRIVATE);

        yourTime = ((stopTime - startTime)/1000)+1;

        if(sPref.getLong("Time" + Size, 0)!=0) {
            if (yourTime <= sPref.getLong("Time" + Size, 0)) {
                SharedPreferences.Editor ed = sPref.edit();
                ed.putLong("Time" + Size, yourTime);
                ed.commit();
                Log.d("TimeLog", "New record Time " + yourTime);
                Toast.makeText(getApplicationContext(), "You time is\n\n" + yourTime + " second" +
                        "\n\nBest time for size " + ySize + " x " + xSize +
                        " is\n\n" + sPref.getLong("Time" + Size, 0) +
                        " second\n\nYOU WIN WITH BEST TIME", Toast.LENGTH_LONG).show();
            }
            else {
                Log.d("TimeLog", "Time " + yourTime);
                Toast.makeText(getApplicationContext(), "You time is\n\n" + ((stopTime - startTime)/1000+1) + " second" +
                        "\n\nBest time for size " + ySize + " x " + xSize +
                        " is\n\n" + sPref.getLong("Time" + Size, 0) +
                        " second", Toast.LENGTH_LONG).show();
            }
        }

        else {
            Log.d("TimeLog", "New record Time " + ((stopTime - startTime)/1000+1));
            Toast.makeText(getApplicationContext(), "You time is\n\n" + yourTime + " second", Toast.LENGTH_LONG).show();
            SharedPreferences.Editor ed = sPref.edit();
            ed.putLong("Time" + Size, yourTime);
            ed.commit();
        }

    }
}