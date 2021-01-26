package com.romankryvolapov.secretmaze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static int layoutWidth;
    public static int layoutHeight;
    public static int startXcoord;
    public static int startYcoord;
    public static int SizeInSectionsForGenerator = 20; // до 49 включительно
    public static int SizeInSectionsForRender;
    public static int sectionSize;
    public static boolean[][] wallsArray;
    public static boolean[][] openedArray;
    public static boolean win = false;
    public static int colorCancasBackground;
    public static int colorCancasBackgroundWin;
    public static int colorWalls;
    public static int colorGame;

    private ConstraintLayout layout;
    private Button buttonLeft;
    private Button buttonRight;
    private Button buttonUp;
    private Button buttonDown;
    private Button buttonReset;
    private Button buttonSizeMinus;
    private Button buttonSizePlus;
    private SharedPreferences mSettings;
    private MySurfaceView mySurfaceView;
    private Ellers ellers;
    private ViewTreeObserver viewTreeObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarColor();
        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        layout = findViewById(R.id.layout_paint);
        viewTreeObserver = layout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                layoutWidth = layout.getMeasuredWidth();
                layoutHeight = layout.getMeasuredHeight();
                sectionSize = layoutWidth / SizeInSectionsForRender;
            }
        });

        try {
            if (mSettings.contains("SizeInSectionsForGenerator") && Integer.parseInt(mSettings.getString("SizeInSectionsForGenerator", "")) >= 6
                    && Integer.parseInt(mSettings.getString("SizeInSectionsForGenerator", "")) <= 49)
                SizeInSectionsForGenerator = Integer.parseInt(mSettings.getString("SizeInSectionsForGenerator", ""));
        } catch (Exception e) {
            SizeInSectionsForGenerator = 20;
        }

        findAllElements();
        addListiners();
        NewMaze();

        mySurfaceView = new MySurfaceView(MainActivity.this);
        layout.removeAllViews();
        layout.addView(mySurfaceView);
    }

    void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBackground));
        }
    }

    private void addListiners() {

        buttonReset.setText("reset (size " + SizeInSectionsForGenerator + ")");

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
                mySurfaceView.update();

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
                mySurfaceView.update();
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
                mySurfaceView.update();
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
                mySurfaceView.update();
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewMaze();
                mySurfaceView.update();
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
                    buttonReset.setText("reset (size " + SizeInSectionsForGenerator + ")");
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("SizeInSectionsForGenerator", Integer.toString(SizeInSectionsForGenerator));
                    editor.apply();
                    NewMaze();
                    mySurfaceView.update();
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
                    buttonReset.setText("reset (size " + SizeInSectionsForGenerator + ")");
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("SizeInSectionsForGenerator", Integer.toString(SizeInSectionsForGenerator));
                    editor.apply();
                    NewMaze();
                    mySurfaceView.update();
                }
            }
        });
    }

    private void findAllElements() {
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);
        buttonUp = findViewById(R.id.buttonUp);
        buttonDown = findViewById(R.id.buttonDown);
        buttonReset = findViewById(R.id.buttonReset);
        buttonSizeMinus = findViewById(R.id.buttonSizeMinus);
        buttonSizePlus = findViewById(R.id.buttonSizePlus);
        colorCancasBackground = getResources().getColor(R.color.colorCancasBackground);
        colorCancasBackgroundWin = getResources().getColor(R.color.colorCancasBackgroundWin);
        colorWalls = getResources().getColor(R.color.colorWalls);
        colorGame = getResources().getColor(R.color.colorGame);
    }


    private void NewMaze() {
        wallsArray = null;
        openedArray = null;
        ellers = null;
        win = false;
        SizeInSectionsForRender = SizeInSectionsForGenerator * 2 + 1;
        wallsArray = new boolean[100][100];
        openedArray = new boolean[100][100];
        ellers = new Ellers(SizeInSectionsForGenerator, SizeInSectionsForGenerator);
        ellers.makeMaze();
        ellers.printMaze();
        ellers = null;
    }
}