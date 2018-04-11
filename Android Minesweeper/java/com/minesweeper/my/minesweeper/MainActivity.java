package com.minesweeper.my.minesweeper;
// Made by Roman Kryvolapov
// Новая версия может изменять размер поля
// Содержит супер мега код по генерации и привязке кнопок
// В будущем хочу сделать покрасивей, задействовав XML
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    static int clickedQuantity = 0;
    static int minePercent = 15;
    static int Quantity = 0;
    Boolean gameOver = false;
    static int x1 = 0;
    static int y1 = 0;

    static int xSize = 14;
    static int ySize = 14;

    int display[][] = new int[xSize][ySize];
    int identifier[][] = new int[xSize][ySize];
    Boolean clicked[][] = new Boolean[xSize][ySize];

    static Button buttonArray[][] = new Button[xSize][ySize];
    static int buttonArrayID[][] = new int[xSize][ySize];
    static final int layout1Height = 14;
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

    ConstraintSet constraintSetButton[][] = new ConstraintSet[xSize][ySize];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

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
                buttonArray[x][y].setBackgroundColor(getResources().getColor(R.color.colorButton));
                constraintLayout2.addView(buttonArray[x][y]);
            }
        }
    }

    void setConstraints() {

        // супер мега код по привязке поля кнопок

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {

                constraintSetButton[x][y] = new ConstraintSet();

                if (x == 0 && y == 0) //1
                {
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                } else if (x == 0 && y != 0 && y != ySize - 1) //2
                {
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                } else if (x == 0 && y == ySize - 1) //3
                {
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                } else if (x != xSize - 1 && x != 0 && y == ySize - 1) {
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                } else if (x == xSize - 1 && y == ySize - 1) {
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                } else if (x == xSize - 1 && y != ySize - 1 && y != 0) {
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                } else if (x == xSize - 1 && y == 0) {
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                } else if (x != 0 && x != xSize - 1 && y == 0) {
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                } else {
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.LEFT, buttonArray[x][y - 1].getId(), ConstraintSet.RIGHT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.RIGHT, buttonArray[x][y + 1].getId(), ConstraintSet.LEFT);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonArray[x - 1][y].getId(), ConstraintSet.BOTTOM);
                    constraintSetButton[x][y].connect(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonArray[x + 1][y].getId(), ConstraintSet.TOP);
                }

                // Margin
                constraintSetButton[x][y].setMargin(buttonArray[x][y].getId(), ConstraintSet.END, buttonMargin);
                constraintSetButton[x][y].setMargin(buttonArray[x][y].getId(), ConstraintSet.START, buttonMargin);
                constraintSetButton[x][y].setMargin(buttonArray[x][y].getId(), ConstraintSet.TOP, buttonMargin);
                constraintSetButton[x][y].setMargin(buttonArray[x][y].getId(), ConstraintSet.BOTTOM, buttonMargin);
                // Размер
                constraintSetButton[x][y].constrainWidth(buttonArray[x][y].getId(), ConstraintSet.MATCH_CONSTRAINT);
                constraintSetButton[x][y].constrainHeight(buttonArray[x][y].getId(), ConstraintSet.MATCH_CONSTRAINT);
                constraintSetButton[x][y].applyTo(constraintLayout2);

            }
        }
    }

    void createLayout1() {

        int constraintLayout1ID = 10001;
        constraintLayout1.setId(constraintLayout1ID);
        constraintLayout1.setBackgroundColor(getResources().getColor(R.color.constraintLayout1));

        generalLayout.addView(constraintLayout1);

        ConstraintSet constraintSetConstraintLayout1 = new ConstraintSet();
        constraintSetConstraintLayout1.clone(generalLayout);
        constraintSetConstraintLayout1.connect(constraintLayout1.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSetConstraintLayout1.connect(constraintLayout1.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSetConstraintLayout1.connect(constraintLayout1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSetConstraintLayout1.connect(constraintLayout1.getId(), ConstraintSet.BOTTOM, constraintLayout2.getId(), ConstraintSet.TOP);
        constraintSetConstraintLayout1.constrainWidth(constraintLayout1.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetConstraintLayout1.constrainHeight(constraintLayout1.getId(), displayHeight / layout1Height);
        constraintSetConstraintLayout1.applyTo(generalLayout);

    }

    void createLayout2() {

        int constraintLayout2ID = 10002;
        constraintLayout2.setId(constraintLayout2ID);
        constraintLayout2.setBackgroundColor(getResources().getColor(R.color.constraintLayout1));

        generalLayout.addView(constraintLayout2);

        ConstraintSet constraintSetConstraintLayout2 = new ConstraintSet();
        constraintSetConstraintLayout2.clone(generalLayout);
        constraintSetConstraintLayout2.connect(constraintLayout2.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSetConstraintLayout2.connect(constraintLayout2.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSetConstraintLayout2.connect(constraintLayout2.getId(), ConstraintSet.TOP, constraintLayout1.getId(), ConstraintSet.BOTTOM);
        constraintSetConstraintLayout2.connect(constraintLayout2.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSetConstraintLayout2.constrainWidth(constraintLayout2.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetConstraintLayout2.constrainHeight(constraintLayout2.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSetConstraintLayout2.applyTo(generalLayout);

    }

    void createTopButtons() {

        myTextView = new TextView(this);
        int myTextViewID = 10003;
        myTextView.setId(myTextViewID);
        myTextView.setText("Some text");
        myTextView.setGravity(Gravity.CENTER);
        myTextView.setTextColor(getResources().getColor(R.color.colorWhite));
        myTextView.setBackgroundColor(getResources().getColor(R.color.constraintLayout1));

        buttonReset = new Button(this);
        int buttonResetID = 10004;
        buttonReset.setId(buttonResetID);
        buttonReset.setText("RESET");
        buttonReset.setGravity(Gravity.CENTER);
        buttonReset.setTextColor(getResources().getColor(R.color.colorWhite));
        buttonReset.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


        buttonSizePlus = new Button(this);
        int buttonSizePlusID = 10005;
        buttonSizePlus.setId(buttonSizePlusID);
        buttonSizePlus.setText("SIZE +");
        buttonSizePlus.setGravity(Gravity.CENTER);
        buttonSizePlus.setTextColor(getResources().getColor(R.color.colorWhite));
        buttonSizePlus.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        buttonSizeMinus = new Button(this);
        int buttonSizeMinusID = 10006;
        buttonSizeMinus.setId(buttonSizeMinusID);
        buttonSizeMinus.setText("SIZE -");
        buttonSizeMinus.setGravity(Gravity.CENTER);
        buttonSizeMinus.setTextColor(getResources().getColor(R.color.colorWhite));
        buttonSizeMinus.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

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

    public void mineListener() {
        Button button = (Button) buttonArray[x1][y1];
        button.setOnTouchListener(new View.OnTouchListener() {
            int buttonText = display[x1][y1];
            Button buttonNext = buttonArray[x1][y1];
            Boolean clicked1 = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!gameOver && !clicked1) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            mineQuantity();

                            if (buttonText == 9) {
                                gameOver = true;
                                ((Button) buttonNext).setText("M");
                                ((Button) buttonNext).setBackgroundColor(getResources().getColor(R.color.colorGameOver));
                                changeColor(R.color.colorGameOver);
                                testOut1();

                            } else if (clickedQuantity == 1) {
                                constraintLayout2.setBackgroundColor(getResources().getColor(R.color.colorGameWin));
                                changeColor(R.color.colorGameWin);
                                testOut3();

                            } else {
                                clickedQuantity--;
                                ((Button) buttonNext).setBackgroundColor(getResources().getColor(R.color.colorButtonOnClick));
                                ((Button) buttonNext).setText(Integer.toString(buttonText));
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            ((Button) buttonNext).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            ((Button) buttonNext).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            clicked1 = true;
                            return true;
                        case MotionEvent.ACTION_MOVE:
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

        Button button = (Button) buttonSizeMinus;
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        display = null;
                        identifier = null;
                        clicked = null;

                        buttonArray = null;
                        buttonArrayID = null;
                        constraintSetButton = null;

                        System.gc();

                        buttonID = 1000;

                        if(xSize>3&&ySize>3)
                        {
                            xSize-=1;
                            ySize-=1;
                        }


                        display = new int[xSize][ySize];
                        identifier = new int[xSize][ySize];
                        clicked = new Boolean[xSize][ySize];

                        buttonArray = new Button[xSize][ySize];
                        buttonArrayID = new int[xSize][ySize];
                        constraintSetButton = new ConstraintSet[xSize][ySize];

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

                        break;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });

        Button button1 = (Button) buttonSizePlus;
        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        display = null;
                        identifier = null;
                        clicked = null;

                        buttonArray = null;
                        buttonArrayID = null;
                        constraintSetButton = null;

                        System.gc();

                        buttonID = 1000;

                        if(xSize<50&&ySize<50)
                        {
                            xSize+=1;
                            ySize+=1;
                        }


                        display = new int[xSize][ySize];
                        identifier = new int[xSize][ySize];
                        clicked = new Boolean[xSize][ySize];

                        buttonArray = new Button[xSize][ySize];
                        buttonArrayID = new int[xSize][ySize];
                        constraintSetButton = new ConstraintSet[xSize][ySize];

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

                        break;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });


    }

    public void mineRender() {

        for (int y = 0; y < ySize; y++) {
            y1 = 0;
            for (int x = 0; x < xSize; x++) {
                mineListener();
                y1++;
            }
            x1++;
        }
        x1 = 0;
        y1 = 0;
    }

    public void clickedTOfalse() {

        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                clicked[x][y] = false;
            }
        }
    }

    public void resetListener() {
        Button button = (Button) buttonReset;
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        reset();
                        break;
                    case MotionEvent.ACTION_UP:
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

        ((ConstraintLayout) constraintLayout1).setBackgroundColor(getResources().getColor(colorID));
        ((ConstraintLayout) constraintLayout2).setBackgroundColor(getResources().getColor(colorID));
        ((TextView) myTextView).setBackgroundColor(getResources().getColor(colorID));

    }

    public void mineQuantity() {
        if (clickedQuantity != 0) {
            TextView vivod = (TextView) myTextView;
            vivod.setText("Deactivate " + (clickedQuantity - 1));
        } else {
            TextView vivod = (TextView) myTextView;
            vivod.setText("Deactivate " + (xSize * ySize - Quantity));
        }
    }

    public void testOut1() {
        changeColor(R.color.colorGameOver);
        TextView vivod = (TextView) myTextView;
        //vivod.setText("Game Over!");
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if (display[x][y] != 9) {
                    if (clicked[x][y]) {
                        Button button = (Button) buttonArray[x][y];
                        button.setText(Integer.toString(display[x][y]));
                    }
                } else {
                    Button button = (Button) buttonArray[x][y];
                    button.setText("M");
                    button.setBackgroundColor(getResources().getColor(R.color.colorGameOver));
                }
            }
        }

    }

    public void testOut2() {
        changeColor(R.color.colorPrimary);
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                Button button = (Button) buttonArray[x][y];
                button.setText("");
                button.setTextColor(getResources().getColor(R.color.colorWhite));
                button.setBackgroundColor(getResources().getColor(R.color.colorButton));
                if (display[x][y] != 9) {
                    clickedQuantity++;
                }
            }
        }

    }

    public void testOut3() {
        changeColor(R.color.colorGameWin);
        TextView vivod = (TextView) myTextView;
        vivod.setText("You Win!");
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                if (display[x][y] != 9) {
                    if (clicked[x][y]) {
                        Button button = (Button) buttonArray[x][y];
                        button.setText(Integer.toString(display[x][y]));
                    }
                } else {
                    Button button = (Button) buttonArray[x][y];
                    button.setText("M");
                    button.setBackgroundColor(getResources().getColor(R.color.colorGameWin));
                }
            }
        }


    }
}