package com.labyrinth.my.labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static float xCoordFloat = 0;
    private static float yCoornFloat = 0;
    public static int xCoord = 0;
    public static int yCoorn = 0;
    public static int xCorrection = 0;
    public static int yCorrection = 0;
    public static int xSize = 0;
    public static int ySize = 0;
    public static int correction_1 = 14;
    public static int correction_2 = 20;
    public static int correction_3 = 90;
    public static int xFormCorrection = 20;
    public static int yFormCorrection = 28;







    public static Set<xyPoints> xyPointsArray = new HashSet<xyPoints>();

    public static int[][] xArray = new int[100][100];
    public static int[][] yArray = new int[100][100];

    public static boolean[][] xyArrey = new boolean[100][100];
    public static boolean[][] closedArray = new boolean[100][100];

    int capacity;
    float loadFactor;


    @Override
    public boolean onTouchEvent(MotionEvent e) {





        int action = e.getAction();

      //  if (action == MotionEvent.ACTION_UP) {

//                xCoord = (int) e.getX() - xCorrection;
//                yCoorn = (int) e.getY() - yCorrection;
//                xyPoints xyPoints = new xyPoints(xCoord, yCoorn);
//                xyPointsArray.add(xyPoints);



            try {

                for (int x = 0; x < 100; x++) {
                    for (int y = 0; y < 100; y++) {
                        if(xyArrey[x][y]) {

                            if (e.getX() > xArray[x][y] - correction_1 + xCorrection && e.getX() < xArray[x][y] + correction_1 + xCorrection) {
                                if (e.getY() < yArray[x][y] + correction_3 + yCorrection && e.getY() > yArray[x][y] + correction_2 + yCorrection) {
                                    if(!closedArray[x][y + 1])
                                    xyArrey[x][y + 1] = true;
                                }
                            }

                            if (e.getY() > yArray[x][y] - correction_1 + yCorrection && e.getY() < yArray[x][y] + correction_1 + yCorrection) {
                                if (e.getX() < xArray[x][y] + correction_3 + xCorrection && e.getX() > xArray[x][y] + correction_2 + xCorrection) {
                                    if(!closedArray[x + 1][y])
                                    xyArrey[x + 1][y] = true;
                                }
                            }

                            if (e.getX() > xArray[x][y] - correction_1 + xCorrection && e.getX() < xArray[x][y] + correction_1 + xCorrection) {
                                if (e.getY() > yArray[x][y] - correction_3 + yCorrection && e.getY() < yArray[x][y] - correction_2 + yCorrection) {
                                    if(!closedArray[x][y - 1])
                                    xyArrey[x][y - 1] = true;
                                }
                            }


                            if (e.getY() > yArray[x][y] - correction_1 + yCorrection && e.getY() < yArray[x][y] + correction_1 + yCorrection) {
                                if (e.getX() > xArray[x][y] - correction_3 + xCorrection && e.getX() < xArray[x][y] - correction_2 + xCorrection) {
                                    if(!closedArray[x-1][y])
                                    xyArrey[x-1][y] = true;
                                }
                            }








                        }


                    }

                }



            }catch (Exception e1){
            }

//
//        } else if (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_DOWN) {
//
//            try {
//                for (xyPoints xy : MainActivity.xyPointsArray) {
//                    if (e.getX() > xy.xPoint + xStep && e.getX() < xy.xPoint + xStep + 50) {
//                        xStep +=50;
//                        xyPoints xyPoints = new xyPoints(xStep, 50);
//                        xyPointsArray.add(xyPoints);
//                    }
//                }
//            }catch (Exception e1){
//            }




//        }
//
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));



        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        xSize = size.x;
        ySize = size.y;


        int a;

        for (int x = 0; x < xArray.length; x++) {
            for (int y = 0; y < yArray.length; y++) {
                xArray[x][y]=x*xFormCorrection;
                yArray[x][y]=y*yFormCorrection;
            }
        }

       // xyArrey[2][3]=true;



        Ellers ellers = new Ellers(30,30);
        ellers.makeMaze();
        ellers.printMaze();






            for (int i = 30; i < 100; i++) {
                if(!closedArray[i][30]) {
                    xyArrey[i][30] = true;
                    break;
                }
            }




//


//        super.onCreate(savedInstanceState);
//
////        setContentView(R.layout.activity_main);
//
//        Draw2D draw2D = new Draw2D(this);
//        setContentView(draw2D);








//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

















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


class xyPoints{
    public int xPoint;
    public int yPoint;
    public xyPoints(int xPoint, int yPoint) {
        this.xPoint = xPoint;
        this.yPoint = yPoint;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}


/*

class Draw2D extends View{

    static int pixelX = 0;
    static int pixelY = 0;

    private Paint mPaint = new Paint();
    private Rect mRect = new Rect();
    private Bitmap mBitmap;

    public Draw2D(Context context) {
        super(context);
        Resources res = this.getResources();
      //  mBitmap = BitmapFactory.decodeResource(res, R.drawable.cat_bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pixelX = canvas.getWidth();
        pixelY = canvas.getHeight();

        System.out.println(canvas.getWidth());
        System.out.println(canvas.getHeight());


        //invalidate
        Rect dstRect = new Rect();

        canvas.getClipBounds(dstRect);





        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // стиль Заливка
        mPaint.setStyle(Paint.Style.FILL);
        // закрашиваем холст белым цветом
        mPaint.setColor(Color.WHITE);
        canvas.drawPaint(mPaint);

        // Рисуем желтый круг

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

        // canvas.drawCircle(950, 30, 25, mPaint);

//        canvas.drawCircle(width - 30, 30, 25, mPaint);

        // Рисуем зеленый прямоугольник
//        mPaint.setColor(Color.BLACK);
          canvas.drawRect(800, 800, 2000, 2000, mPaint);
//        canvas.drawRect(0, canvas.getHeight() - 30, width, height, mPaint);
        // Рисуем текст
//        mPaint.setColor(Color.BLUE);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setAntiAlias(true);
//        mPaint.setTextSize(32);
//
//          canvas.drawText(Integer.toString(canvas.getWidth()) + Integer.toString(canvas.getHeight()), 30, 648, mPaint);
//        canvas.drawText("Лужайка только для котов", 30, height - 32, mPaint);
//        // Текст под углом
//        // int x = 810;
//        int x = width - 170;
//        int y = 190;

        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(27);
        String beam = "Лучик солнца!";

        canvas.save();
        // Создаем ограничивающий прямоугольник для наклонного текста
        // поворачиваем холст по центру текста
//        canvas.rotate(-45, x + mRect.exactCenterX(), y + mRect.exactCenterY());

        // Рисуем текст
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawText(beam, x, y, mPaint);

        // восстанавливаем холст
        canvas.restore();

        // Выводим изображение
        // canvas.drawBitmap(mBitmap, 450, 530, mPaint);
//        canvas.drawBitmap(mBitmap, width - mBitmap.getWidth(), height - mBitmap.getHeight() - 10, mPaint);
    }
}



*/

class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder(), getResources());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        // завершаем работу потока
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // если не получилось, то будем пытаться еще и еще
            }
        }
    }
}


class DrawThread extends Thread{
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private Bitmap picture;
    private Matrix matrix;
    private long prevTime;

//
    public DrawThread(SurfaceHolder surfaceHolder, Resources resources){
        this.surfaceHolder = surfaceHolder;
//
//        // загружаем картинку, которую будем отрисовывать
//        picture = BitmapFactory.decodeResource(resources, R.drawable.icon);
//
//        // формируем матрицу преобразований для картинки
//        matrix = new Matrix();
//        matrix.postScale(3.0f, 3.0f);
//        matrix.postTranslate(100.0f, 100.0f);
//
//        // сохраняем текущее время
//        prevTime = System.currentTimeMillis();
    }


//    public void resetListener() {
//
//        buttonReset.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        buttonReset.setBackgroundResource(R.drawable.button_style_white);
//                        buttonReset.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        buttonReset.setBackgroundResource(R.drawable.button_style);
//                        buttonReset.setTextColor(getResources().getColor(R.color.colorWhite));
//                        reset();
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                }
//                return true;
//            }
//        });
//    }







    public void setRunning(boolean run) {
        runFlag = run;
    }


    @Override
    public void run() {



        Paint mPaint = new Paint();



        Canvas canvas;




        while (runFlag) {
            // получаем текущее время и вычисляем разницу с предыдущим
            // сохраненным моментом времени
//            long now = System.currentTimeMillis();
//            long elapsedTime = now - prevTime;
//            if (elapsedTime > 30){
//                // если прошло больше 30 миллисекунд - сохраним текущее время
//                // и повернем картинку на 2 градуса.
//                // точка вращения - центр картинки
//                prevTime = now;
//                matrix.preRotate(2.0f, picture.getWidth() / 2, picture.getHeight() / 2);
//            }
            canvas = null;
            try {
                // получаем объект Canvas и выполняем отрисовку
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                   // canvas.drawColor(Color.BLACK);
                    MainActivity.xCorrection = MainActivity.xSize - canvas.getWidth();
                    MainActivity.yCorrection = MainActivity.ySize - canvas.getHeight();



                    // стиль Заливка
//                    mPaint.setStyle(Paint.Style.FILL);
                    // закрашиваем холст белым цветом
//                    mPaint.setColor(Color.WHITE);
//                    canvas.drawPaint(mPaint);

//                    mPaint.setAntiAlias(true);








                    try {

                        mPaint.setColor(Color.BLACK);

                        for (int x = 0; x < MainActivity.xArray.length; x++) {
                            for (int y = 0; y < MainActivity.yArray.length; y++) {
                                if(MainActivity.closedArray[x][y])

                                    canvas.drawRect(MainActivity.xArray[x][y]-MainActivity.xFormCorrection/2, MainActivity.yArray[x][y]-MainActivity.yFormCorrection/2,
                                            MainActivity.xArray[x][y]+MainActivity.xFormCorrection/2, MainActivity.yArray[x][y]+MainActivity.yFormCorrection/2, mPaint);


                                  //  canvas.drawCircle(MainActivity.xArray[x][y], MainActivity.yArray[x][y], 20, mPaint);
                            }
                        }

                        mPaint.setColor(Color.RED);

                        for (int x = 0; x < MainActivity.xArray.length; x++) {
                            for (int y = 0; y < MainActivity.yArray.length; y++) {
                                if(MainActivity.xyArrey[x][y])
                                    canvas.drawRect(MainActivity.xArray[x][y]-MainActivity.xFormCorrection/2, MainActivity.yArray[x][y]-MainActivity.yFormCorrection/2,
                                            MainActivity.xArray[x][y]+MainActivity.xFormCorrection/2, MainActivity.yArray[x][y]+MainActivity.yFormCorrection/2, mPaint);
                            }
                        }





//                        for (xyPoints xy : MainActivity.xyPointsArray) {
//                            canvas.drawCircle(xy.xPoint, xy.yPoint, 10, mPaint);
//                        }


                    }catch (Exception e){

                    }

//





                    // Рисуем желтый круг





//                    canvas.drawCircle(500, 500, 100, mPaint);

//                    canvas.drawCircle(MainActivity.xCoord, MainActivity.yCoorn, 10, mPaint);

                 //   canvas.drawBitmap(picture, matrix, null);
                }
            }
            finally {
                if (canvas != null) {
                    // отрисовка выполнена. выводим результат на экран
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}

class Ellers
{
    static final char MAZE_WALL = '0';
    static final char MAZE_PATH = '1';
    static final int  UNDETERMINED = -2;
    static final int  SET_WALL = -1;

    int       rows;           //строки
    int       cols;           //столбцы

    int       act_rows;       //Текущий номер строки
    int       act_cols;       //Текущий номер столбца

    char[][]  feild;          //Поле лабиринта

    int[]     current;        //текущая строка, за исключением наружных стен
    int[]     next;           //следующая строка, за исключением наружных стен

    int       numSet;         //Проверка на совпадение
    private Random fRand;
    private int fNext;
    private int fNext2;


    /* конструктор */
    public Ellers (int nRows, int nCols)
    {
        act_rows = nRows;
        act_cols = nCols;

        rows = act_rows*2+1;
        cols = act_cols*2+1;

        feild   = new char[rows][cols];
        current = new int[act_cols*2-1];
        next    = new int[act_cols*2-1];


        /* Sets the maze to filled */
        for(int i =0; i<feild[0].length; i++){
            for(int j=0; j<feild.length; j++){
                feild[i][j] = MAZE_WALL;
            }
        }


        for(int i=0; i<next.length; i++){
            next[i] = UNDETERMINED;
        }



        /* Инициализация первой строки */
        for(int i=0; i<current.length; i+=2){
            current[i] = i/2+1;
            if(i != current.length-1)
                current[i+1] = SET_WALL;
        }
        numSet = current[current.length-1];
    }


    public void makeMaze()
    {

        setRand(new Random());


        for(int q=0; q<act_rows-1; q++){   //для всех строк кроме последней

            if(q != 0){

                /* получим текущую строку из последней итерации*/
                for(int i=0; i<current.length; i++){
                    current[i] = next[i];
                    next[i] = UNDETERMINED;
                }
            }


            joinSets();
            makeVerticalCuts();


            /* заполним остальную часть следующей строки */

            for(int j=0; j<current.length; j+=2){

                if(next[j] == UNDETERMINED)
                    next[j] = ++numSet;

                if(j != current.length-1)
                    next[j+1] = SET_WALL;
            }


            /* запишем текущую строку в поле */
            for(int k=0; k<current.length; k++){

                if(current[k] == SET_WALL){
                    feild[2*q+1][k+1] = MAZE_WALL;
                    feild[2*q+2][k+1] = MAZE_WALL;
                }else{
                    feild[2*q+1][k+1] = MAZE_PATH;

                    if(current[k] == next[k]){
                        feild[2*q+2][k+1] = MAZE_PATH;
                    }
                }

            }

        }

        makeLastRow();
        makeOpenings();

    }

    private void joinSets()
    {
        Random rand = new Random();

        /* Случайные наборы */
        for(int i=1; i<current.length-1; i+=2){ //проверка только где стены

            /* Проверка на объединение:
             *      Имеют ли стену между ними,
             *      не являются ли частью одного набора
             *Получаем случайный набор, при объединении
             */
            if(current[i] == SET_WALL && current[i-1] != current[i+1]
                    && rand.nextBoolean()){


                current[i] = 0; //Убрать стену

                int old  = Math.max(current[i-1],current[i+1]);
                fNext= Math.min(current[i-1],current[i+1]);

                // Объединяем два набора в один


                for(int j=0; j<current.length; j++){

                    if(current[j] == old)
                        current[j] = fNext;
                }
            }
        }
    }


    /* Случайно выбрать вертикальные пути для каждого набора, убедившись,
     * что есть по крайней мере 1 вертикальный путь в наборе
     */
    private void makeVerticalCuts()
    {
        Random   rand          = new Random();

        int      begining;     //Начало набора(Включительно)
        int      end;          //Конец набор(Включительно)

        boolean madeVertical;  //Создание вертикального прохода


        int i;
        begining = 0;
        do{

            /* Поиск конца */
            i=begining;
            while(i<current.length-1 && current[i] == current[i+2]){
                i+=2;
            }
            end = i;

            //Наличие петли


            madeVertical = false;
            do{
                for(int j=begining; j<=end; j+=2){

                    if(rand.nextBoolean()){
                        next[j] = current[j];
                        madeVertical = true;
                    }
                }
            }while(!madeVertical);

            begining = end+2;  //перейти к следующему набору в строке

        }while(end != current.length-1);
    }




    private void makeLastRow()
    {

        /* Получение текущей строки */
        for(int i=0; i<current.length; i++){
            current[i] = next[i];
        }

        for(int i=1; i<current.length-1; i+=2){

            if(current[i] == SET_WALL && current[i-1] != current[i+1]){

                current[i] = 0;

                int old  = Math.max(current[i-1],current[i+1]);
                fNext2= Math.min(current[i-1],current[i+1]);

                // Объединяем два набора в один
                for(int j=0; j<current.length; j++){

                    if(current[j] == old)
                        current[j] = fNext2;
                }
            }
        }


        /* Добавление последней строки */
        for(int k=0; k<current.length; k++){

            if(current[k] == SET_WALL){
                feild[rows-2][k+1] = MAZE_WALL;
            }else{
                feild[rows-2][k+1] = MAZE_PATH;
            }
        }

    }

    public void makeOpenings(){

        Random rand = new Random(); //два различных генераторов случайных чисел
        Random rand2 = new Random();//на всякий случай

        //случайное место для входа и выхода
        int entrance_row = rand.nextInt(act_rows-1) * 2 +1;
        int exit_row = rand2.nextInt(act_rows-1) * 2 +1;

        //очистить место
        feild[entrance_row][0] = MAZE_PATH;
        feild[exit_row][cols-1] = MAZE_PATH;

    }

    public void printMaze ()
    {
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                if(feild[i][j]=='0')
                MainActivity.closedArray[i][j]=true;
              //  System.out.print(feild[i][j]);
            }
         //   System.out.println();
        }
    }

    public char[][] getMaze()
    {
        return feild;
    }


    public Random getRand() {
        return fRand;
    }


    public void setRand(Random rand) {
        fRand = rand;
    }



}
