package com.minesweeper.my.minesweeper;
//made by Roman Kryvolapov
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Random;
import android.widget.TextView;
import android.widget.Button;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    int visota = 12;
    int shirina = 12;
    int display[][] = new int[visota][shirina];
    int identifier[][] = new int[visota][shirina];
    Boolean clicked[][] = new Boolean[visota][shirina];
    int clickedQuantity = 0;
    int minePercent = 15;
    int Quantity = 0;
    Boolean gameOver = false;
    int x1 = 0;
    int y1 = 0;

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
        buttonSetID();
        reset();
        resetListener();
    }
    public void clickedTOfalse(){
        for(int y=0; y<shirina; y++){
            for(int x=0; x<visota; x++) {
                clicked[x][y]= false;
                }
            }
        }
    public void mineRender (){
        for(int y=0; y< shirina; y++) {
            y1=0;
            for (int x = 0; x < visota; x++) {
                mineListener ();
                y1++;
            }
            x1++;
        }
        x1 = 0;
        y1 = 0;
    }
    public void mineListener () {
                 Button button = (Button) findViewById(identifier[x1][y1]);
                    button.setOnTouchListener(new View.OnTouchListener() {
                        int z1 = identifier[x1][y1];
                        int z2 = display[x1][y1];
                        Boolean clicked1 =false;
                         @Override
                        public boolean onTouch(View v, MotionEvent event) {
                             if(!gameOver && !clicked1) {
                                 switch (event.getAction()) {
                                     case MotionEvent.ACTION_DOWN:
                                         ((Button) findViewById(z1)).setBackgroundResource(R.color.colorButtonOnClick);

                                         ((Button) findViewById(z1)).setText(Integer.toString(z2));
                                         mineQuantity();
                                         if (z2 == 9) {
                                             ((Button) findViewById(z1)).setText("M");
                                             gameOver = true;
                                             ((Button) findViewById(z1)).setBackgroundResource(R.color.colorButton);
                                             testOut1();
                                         }else {
                                             clickedQuantity--;
                                             if(clickedQuantity==0){
                                                 ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout2);
                                                 constraintLayout.setBackgroundResource(R.color.colorGameWin);
                                                 testOut3();
                                             }
                                         }
                                         break;
                                     case MotionEvent.ACTION_UP:
                                         ((Button) findViewById(z1)).setBackgroundResource(R.color.colorButton);
                                         clicked1=true;
                                         return true;
                                     case MotionEvent.ACTION_MOVE:
                                         break;
                                 }
                             }
                            return true;
                        }
                    });
    }
    public void changeColor(int colorID){
        ((ConstraintLayout) findViewById(R.id.constraintLayout2)).setBackgroundResource(colorID);
        ((ConstraintLayout) findViewById(R.id.constraintLayout)).setBackgroundResource(colorID);
        ((Button) findViewById(R.id.resetButton)).setBackgroundResource(colorID);
        ((TextView) findViewById(R.id.mineQuantity)).setBackgroundResource(colorID);
    }
    public void resetListener () {
        Button button = (Button) findViewById(R.id.resetButton);
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
    public void reset(){
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
        mineRender ();
    }
    public void testOut1(){
        changeColor(R.color.colorGameOver);
        TextView vivod = (TextView) findViewById(R.id.mineQuantity);
        vivod.setText("Game Over!");
        for(int y=0; y<shirina; y++){
            for(int x=0; x<visota; x++) {
                if(display[x][y]!=9) {
                    if(clicked[x][y]) {
                        ((Button) findViewById(identifier[x][y])).setText(Integer.toString(display[x][y]));
                    }
                }else{
                    ((Button) findViewById(identifier[x][y])).setText("M");
                    ((Button) findViewById(identifier[x][y])).setBackgroundResource(R.color.colorGameOver);
                }
            }
        }
    }

    public void testOut2(){
        changeColor(R.color.colorPrimary);
        for(int y=0; y<shirina; y++){
            for(int x=0; x<visota; x++) {
                    ((Button) findViewById(identifier[x][y])).setText("");
                ((Button) findViewById(identifier[x][y])).setBackgroundResource(R.color.colorButton);
                if(display[x][y]!=9) {
                    clickedQuantity++;
                }
            }
        }
    }
    public void testOut3(){
        changeColor(R.color.colorGameWin);
        TextView vivod = (TextView) findViewById(R.id.mineQuantity);
        vivod.setText("You Win!");
        for(int y=0; y<shirina; y++){
            for(int x=0; x<visota; x++) {
                if(display[x][y]!=9) {
                    if(clicked[x][y]) {
                        ((Button) findViewById(identifier[x][y])).setText(Integer.toString(display[x][y]));
                    }
                }else{
                    ((Button) findViewById(identifier[x][y])).setText("M");
                    ((Button) findViewById(identifier[x][y])).setBackgroundResource(R.color.colorGameWin);
                }
            }
        }
    }
    public void displayTOzero(){
        for(int x=0; x < visota; x++){
            for(int y=0; y < shirina; y++){
                display[x][y]=0;
            }
        }
    }
    public void mineCreate(){
        Random random1 = new Random();
        for(int x=0; x < visota; x++){
            for(int y=0; y < shirina; y++){
                int z = random1.nextInt(100);
                if(z<minePercent){
                    display[x][y]=9;
                    Quantity++;
                }
            }
        }
    }
    public void digitsCreateOn(){
        digitsCreate(0,-1);
        digitsCreate(0,+1);
        digitsCreate(-1,0);
        digitsCreate(+1,0);
        digitsCreate(+1,+1);
        digitsCreate(-1,-1);
        digitsCreate(+1,-1);
        digitsCreate(-1,+1);
    }
    public void digitsCreate(int x1, int y1) {
        for (int x = 0; x < visota; x++) {
            for (int y = 0; y < shirina; y++) {
                try {
                    if (display[x][y] == 9) {
                        if (display[x+x1][y+y1] != 9) {
                            display[x+x1][y+y1] = display[x+x1][y+y1] + 1;
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }
    public void mineQuantity(){
        if(clickedQuantity!=0) {
            TextView vivod = (TextView) findViewById(R.id.mineQuantity);
            vivod.setText("Deactivate " + (clickedQuantity - 1));
        }else {
            TextView vivod = (TextView) findViewById(R.id.mineQuantity);
            vivod.setText("Deactivate " + (visota*shirina-Quantity));
        }
    }
    public void buttonSetID (){
        for(int y=0; y<shirina; y++){
            for(int x=0; x<visota; x++) {
                identifier[x][y] = getResources().getIdentifier("buttonX" + x + "Y" + y, "id", getPackageName());
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
