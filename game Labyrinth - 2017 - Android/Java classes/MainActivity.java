package com.romankryvolapov.secretmaze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

//    public static int xCorrection = 0;
//    public static int yCorrection = 0;
//    public static int xSize = 0;
//    public static int ySize = 0;
//    public static final int correction_1 = 50;
//    public static final int correction_2 = 30;
//    public static final int correction_3 = 150;
//    public static int xFormCorrection = 2000;
//    public static int yFormCorrection = 2500;
//    public static int[][] xArray = new int[100][100];
//    public static int[][] yArray = new int[100][100];
//    public static boolean[][] xyArrey = new boolean[100][100];
//    public static boolean[][] closedArray = new boolean[100][100];

    public static int layoutWidth;
    public static int layoutHeight;
    public static boolean[][] wallsArray;
    public static boolean[][] openedArray;
    private ConstraintLayout layout;
    public static int SizeInSectionsForGenerator = 20; // до 49 включительно
    public static int SizeInSectionsForRender;
    public static int sectionSize;
    public static int startXcoord;
    public static int startYcoord;
    Button buttonLeft;
    Button buttonRight;
    Button buttonUp;
    Button buttonDown;
    Button buttonReset;
    Button buttonSizeMinus;
    Button buttonSizePlus;
    public static boolean win = false;
    private SharedPreferences mSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Display display = getWindowManager().getDefaultDisplay();

//        Point size = new Point();
//        display.getSize(size);
//        xSize = size.x;
//        ySize = size.y;

//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            yFormCorrection = (ySize * 100) / 66;
//            xFormCorrection = (xSize * 100) / 60;
//        }
//        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            yFormCorrection = (ySize * 100) / 66;
//            xFormCorrection = (xSize * 100) / 84;
//        }
//        else {
//            yFormCorrection = (ySize * 100) / 66;
//            xFormCorrection = (xSize * 100) / 60;
//        }

//        newNEW();

        // получение размера ConstraintLayout

        layout = (ConstraintLayout) findViewById(R.id.layout_paint);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                layoutWidth = layout.getMeasuredWidth();
                layoutHeight = layout.getMeasuredHeight();
                Log.i("status", "layoutWidth - " + layoutWidth);
                Log.i("status", "layoutheight - " + layoutHeight);
                sectionSize = layoutWidth / SizeInSectionsForRender;
                Log.i("status", "sectionSize - " + sectionSize);


            }
        });


        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        try {
            if (mSettings.contains("SizeInSectionsForGenerator") && Integer.parseInt(mSettings.getString("SizeInSectionsForGenerator", "")) >= 6
                    && Integer.parseInt(mSettings.getString("SizeInSectionsForGenerator", "")) <= 49)
                SizeInSectionsForGenerator = Integer.parseInt(mSettings.getString("SizeInSectionsForGenerator", ""));
        } catch (Exception e) {
            SizeInSectionsForGenerator = 20;
        }


        buttonLeft = (Button) findViewById(R.id.buttonLeft);
        buttonRight = (Button) findViewById(R.id.buttonRight);
        buttonUp = (Button) findViewById(R.id.buttonUp);
        buttonDown = (Button) findViewById(R.id.buttonDown);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonSizeMinus = (Button) findViewById(R.id.buttonSizeMinus);
        buttonSizePlus = (Button) findViewById(R.id.buttonSizePlus);
        buttonReset.setText("RESET (size=" + SizeInSectionsForGenerator + ")");

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < SizeInSectionsForRender; i++) {
                    for (int j = 0; j < SizeInSectionsForRender; j++) {
                        if (j >= 1)
                            if (openedArray[j][i] && !wallsArray[j - 1][i]) {
                                openedArray[j - 1][i] = true;
                                if (j == 1) {
                                    win = true;
                                }
                            }

                    }
                }
                layout.addView(new MySurfaceView(MainActivity.this));
            }
        });
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = SizeInSectionsForRender; i > 0; i--) {
                    for (int j = SizeInSectionsForRender; j > 0; j--) {
                        if (j <= SizeInSectionsForRender - 2)
                            if (openedArray[j][i] && !wallsArray[j + 1][i]) {
                                openedArray[j + 1][i] = true;
                                if (j == SizeInSectionsForRender - 2) {
                                    win = true;
                                }
                            }
                    }
                }
                layout.addView(new MySurfaceView(MainActivity.this));
            }
        });
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < SizeInSectionsForRender; i++) {
                    for (int j = 0; j < SizeInSectionsForRender; j++) {
                        if (i >= 1)
                            if (openedArray[j][i] && !wallsArray[j][i - 1]) {
                                openedArray[j][i - 1] = true;
                                if (i == 1) {
                                    win = true;
                                }
                            }

                    }
                }
                layout.addView(new MySurfaceView(MainActivity.this));
            }
        });
        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = SizeInSectionsForRender; i > 0; i--) {
                    for (int j = SizeInSectionsForRender; j > 0; j--) {
                        if (i <= SizeInSectionsForRender - 2)
                            if (openedArray[j][i] && !wallsArray[j][i + 1]) {
                                openedArray[j][i + 1] = true;
                                if (i == SizeInSectionsForRender - 2) {
                                    win = true;
                                }
                            }
                    }
                }
                layout.addView(new MySurfaceView(MainActivity.this));
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewMaze();
            }
        });

        buttonSizeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SizeInSectionsForGenerator > 6) {
                    SizeInSectionsForGenerator--;
                    SizeInSectionsForRender = SizeInSectionsForGenerator * 2 + 1;
                    sectionSize = layoutWidth / SizeInSectionsForRender;
                    startXcoord = (layoutWidth - (sectionSize * SizeInSectionsForRender)) / 2;
                    startYcoord = (layoutHeight - (sectionSize * SizeInSectionsForRender)) / 2;
                    buttonReset.setText("RESET (size=" + SizeInSectionsForGenerator + ")");
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("SizeInSectionsForGenerator", Integer.toString(SizeInSectionsForGenerator));
                    editor.apply();
                    NewMaze();
                }
            }
        });
        buttonSizePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SizeInSectionsForGenerator < 49) {
                    SizeInSectionsForGenerator++;
                    SizeInSectionsForRender = SizeInSectionsForGenerator * 2 + 1;
                    sectionSize = layoutWidth / SizeInSectionsForRender;
                    startXcoord = (layoutWidth - (sectionSize * SizeInSectionsForRender)) / 2;
                    startYcoord = (layoutHeight - (sectionSize * SizeInSectionsForRender)) / 2;
                    buttonReset.setText("RESET (size=" + SizeInSectionsForGenerator + ")");
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("SizeInSectionsForGenerator", Integer.toString(SizeInSectionsForGenerator));
                    editor.apply();
                    NewMaze();
                }
            }
        });

        NewMaze();


    }


    private void NewMaze() {

        win = false;

        SizeInSectionsForRender = SizeInSectionsForGenerator * 2 + 1;
        Log.i("status", "sectionSize - " + sectionSize);

        wallsArray = new boolean[100][100];
        openedArray = new boolean[100][100];
        Ellers ellers = new Ellers(SizeInSectionsForGenerator, SizeInSectionsForGenerator);
        ellers.makeMaze();
        ellers.printMaze();

//        Log.i("status", "wallsArray - " + Arrays.deepToString(wallsArray));


        layout.addView(new MySurfaceView(this));

//        for (int i = 0; i < SizeInSectionsForRender; i++) {
//            String temp = "";
//            for (int j = 0; j < SizeInSectionsForRender; j++) {
//                if (wallsArray[j][i])
//                    temp += "0";
//                else temp += "-";
//            }
//            Log.i("status", temp);
//        }


//        xArray = new int[100][100];
//        yArray = new int[100][100];
//
//        xyArrey = new boolean[100][100];
//        closedArray = new boolean[100][100];
//
//        for (int x = 0; x < xArray.length; x++) {
//            for (int y = 0; y < yArray.length; y++) {
//                xArray[x][y] = x;
//                yArray[x][y] = y;
//            }
//        }
//
//        Ellers ellers = new Ellers(29, 29);
//        ellers.makeMaze();
//        ellers.printMaze();
//
//        for (int i = 30; i < 100; i++) {
//            if (!closedArray[i][30]) {
//                xyArrey[i][30] = true;
//                break;
//            }
//        }
    }


}

//               mPaint.setStyle(Paint.Style.FILL);
//                        mPaint.setColor(0xFFCCCCCC);
//                        canvas.drawPaint(mPaint);
//                        mPaint.setAntiAlias(true);
//
//                        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);
//
//                        mPaint.setColor(0xFF333333);
//                        // отрисовка стен
//
//                        for (int x = 0; x < MainActivity.xArray.length; x++) {
//                            for (int y = 0; y < MainActivity.yArray.length; y++) {
//                                if (MainActivity.closedArray[x][y])
//
//                                    canvas.drawRect(
//                                            (MainActivity.xArray[x][y] * (MainActivity.xFormCorrection / 100)) - (MainActivity.xFormCorrection / 200 + 1),
//                                            (MainActivity.yArray[x][y] * (MainActivity.yFormCorrection / 100)) - (MainActivity.yFormCorrection / 200 + 1),
//                                            (MainActivity.xArray[x][y] * (MainActivity.xFormCorrection / 100)) + (MainActivity.xFormCorrection / 200 + 1),
//                                            (MainActivity.yArray[x][y] * (MainActivity.yFormCorrection / 100)) + (MainActivity.yFormCorrection / 200 + 1), mPaint);
//                            }
//                        }
//
//                        mPaint.setColor(0xFFFF0000);
//
//                        // отрисовка прохождения
//
//                        for (int x = 0; x < MainActivity.xArray.length; x++) {
//                            for (int y = 0; y < MainActivity.yArray.length; y++) {
//                                if (MainActivity.xyArrey[x][y])
//                                    canvas.drawRect(
//                                            (MainActivity.xArray[x][y] * (MainActivity.xFormCorrection / 100)) - (MainActivity.xFormCorrection / 200 + 1),
//                                            (MainActivity.yArray[x][y] * (MainActivity.yFormCorrection / 100)) - (MainActivity.yFormCorrection / 200 + 1),
//                                            (MainActivity.xArray[x][y] * (MainActivity.xFormCorrection / 100)) + (MainActivity.xFormCorrection / 200 + 1),
//                                            (MainActivity.yArray[x][y] * (MainActivity.yFormCorrection / 100)) + (MainActivity.yFormCorrection / 200 + 1), mPaint);
//                            }
//                        }

