package com.labyrinth.my.labyrinth;
// made by Roman Kryvolapov
// Применил алгоритм Эллерса для генерации лабиринта
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Menu;
import android.view.MenuItem;
import android.content.res.Resources;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // разница между размером экрана и размером хооста
    public static int xCorrection = 0;
    public static int yCorrection = 0;
    public static int xSize = 0;
    public static int ySize = 0;
    public static int correction_1 = 50;
    public static int correction_2 = 30;
    public static int correction_3 = 150;
    public static int xFormCorrection = 2000;
    public static int yFormCorrection = 2500;
    public static int[][] xArray = new int[100][100];
    public static int[][] yArray = new int[100][100];
    public static boolean[][] xyArrey = new boolean[100][100];
    public static boolean[][] closedArray = new boolean[100][100];

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        int action = e.getAction();

        if (action == MotionEvent.ACTION_DOWN||action == MotionEvent.ACTION_MOVE) {

            DrawThread.newRunThread=true;

            try {
                for (int x = 0; x < 100; x++) {
                    for (int y = 0; y < 100; y++) {
                        if (xyArrey[x][y]) {
                            if (e.getX() > xArray[x][y] * (MainActivity.xFormCorrection / 100) - correction_1 + xCorrection && e.getX() < xArray[x][y] * (MainActivity.xFormCorrection / 100) + correction_1 + xCorrection) {
                                if (e.getY() < yArray[x][y] * (MainActivity.yFormCorrection / 100) + correction_3 + yCorrection && e.getY() > yArray[x][y] * (MainActivity.yFormCorrection / 100) + correction_2 + yCorrection) {
                                    if (!closedArray[x][y + 1])
                                        xyArrey[x][y + 1] = true;
                                    if (y > 60)
                                        newNEW();
                                }
                            }

                            if (e.getY() > yArray[x][y] * (MainActivity.yFormCorrection / 100) - correction_1 + yCorrection && e.getY() < yArray[x][y] * (MainActivity.yFormCorrection / 100) + correction_1 + yCorrection) {
                                if (e.getX() < xArray[x][y] * (MainActivity.xFormCorrection / 100) + correction_3 + xCorrection && e.getX() > xArray[x][y] * (MainActivity.xFormCorrection / 100) + correction_2 + xCorrection) {
                                    if (!closedArray[x + 1][y])
                                        xyArrey[x + 1][y] = true;
                                }
                            }

                            if (e.getX() > xArray[x][y] * (MainActivity.xFormCorrection / 100) - correction_1 + xCorrection && e.getX() < xArray[x][y] * (MainActivity.xFormCorrection / 100) + correction_1 + xCorrection) {
                                if (e.getY() > yArray[x][y] * (MainActivity.yFormCorrection / 100) - correction_3 + yCorrection && e.getY() < yArray[x][y] * (MainActivity.yFormCorrection / 100) - correction_2 + yCorrection) {
                                    if (!closedArray[x][y - 1]) {
                                        xyArrey[x][y - 1] = true;
                                        if (y < 4)
                                            newNEW();
                                    }
                                }
                            }


                            if (e.getY() > yArray[x][y] * (MainActivity.yFormCorrection / 100) - correction_1 + yCorrection && e.getY() < yArray[x][y] * (MainActivity.yFormCorrection / 100) + correction_1 + yCorrection) {
                                if (e.getX() > xArray[x][y] * (MainActivity.xFormCorrection / 100) - correction_3 + xCorrection && e.getX() < xArray[x][y] * (MainActivity.xFormCorrection / 100) - correction_2 + xCorrection) {
                                    if (!closedArray[x - 1][y])
                                        xyArrey[x - 1][y] = true;
                                }
                            }
                        }
                    }

                }

            } catch (Exception e1) {
            }
        }

        else if (action == MotionEvent.ACTION_UP||action == MotionEvent.ACTION_CANCEL){
            DrawThread.newRunThread=false;
        }
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

        yFormCorrection=(ySize*100)/64;
        xFormCorrection=(xSize*100)/60;

//        if(xSize==1440)
//            xFormCorrection=24;
//        if(xSize==1200) {
//            xFormCorrection = 200;
//            yFormCorrection = 200;
//        }
//        if(xSize==1080)
//            xFormCorrection=17;
//        if(xSize==768)
//            xFormCorrection=12;
//        if(xSize==720)
//            xFormCorrection=12;
//        if(xSize==480)
//            xFormCorrection=8;

//        int a;

//        for (int x = 0; x < xArray.length; x++) {
//            for (int y = 0; y < yArray.length; y++) {
//                xArray[x][y]=(x*xSize)/62;
//                yArray[x][y]=(y*ySize)/64;
//            }
//        }


        for (int x = 0; x < xArray.length; x++) {
            for (int y = 0; y < yArray.length; y++) {
                xArray[x][y]=x;
                yArray[x][y]=y;
            }
        }




        Ellers ellers = new Ellers(29,29);
        ellers.makeMaze();
        ellers.printMaze();

            for (int i = 30; i < 100; i++) {
                if(!closedArray[i][30]) {
                    xyArrey[i][30] = true;
                    break;
                }
            }

    }

    public void newNEW(){

        xArray = new int[100][100];
        yArray = new int[100][100];

        xyArrey = new boolean[100][100];
        closedArray = new boolean[100][100];

        for (int x = 0; x < xArray.length; x++) {
            for (int y = 0; y < yArray.length; y++) {
                xArray[x][y]=x;
                yArray[x][y]=y;
            }
        }

        Ellers ellers = new Ellers(29,29);
        ellers.makeMaze();
        ellers.printMaze();

        for (int i = 30; i < 100; i++) {
            if(!closedArray[i][30]) {
                xyArrey[i][30] = true;
                break;
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

    public static boolean newRunThread = true;
    Paint mPaint = new Paint();

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources){
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        canvas = null;

        synchronized (surfaceHolder) {
        try {
                canvas = surfaceHolder.lockCanvas(null);
                MainActivity.xCorrection = MainActivity.xSize - canvas.getWidth();
                MainActivity.yCorrection = MainActivity.ySize - canvas.getHeight();
        }
        finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
        }

        while (runFlag) {
            canvas = null;
            if (!newRunThread) {
                try {
                    Thread.sleep(10);
                }catch (Exception e){
                }
            }
                else {
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {

                        mPaint.setStyle(Paint.Style.FILL);
                        mPaint.setColor(Color.WHITE);
                        canvas.drawPaint(mPaint);
                        mPaint.setAntiAlias(true);

                        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);

                        mPaint.setColor(Color.BLACK);

                            for (int x = 0; x < MainActivity.xArray.length; x++) {
                                for (int y = 0; y < MainActivity.yArray.length; y++) {
                                    if (MainActivity.closedArray[x][y])

                                        canvas.drawRect(
                                                (MainActivity.xArray[x][y] * (MainActivity.xFormCorrection / 100)) - (MainActivity.xFormCorrection / 200 + 1),
                                                (MainActivity.yArray[x][y] * (MainActivity.yFormCorrection / 100)) - (MainActivity.yFormCorrection / 200 + 1),
                                                (MainActivity.xArray[x][y] * (MainActivity.xFormCorrection / 100)) + (MainActivity.xFormCorrection / 200 + 1),
                                                (MainActivity.yArray[x][y] * (MainActivity.yFormCorrection / 100)) + (MainActivity.yFormCorrection / 200 + 1), mPaint);
                                }
                            }

                        mPaint.setColor(Color.RED);

                            for (int x = 0; x < MainActivity.xArray.length; x++) {
                                for (int y = 0; y < MainActivity.yArray.length; y++) {
                                    if (MainActivity.xyArrey[x][y])
                                        canvas.drawRect(
                                                (MainActivity.xArray[x][y] * (MainActivity.xFormCorrection / 100)) - (MainActivity.xFormCorrection / 200 + 1),
                                                (MainActivity.yArray[x][y] * (MainActivity.yFormCorrection / 100)) - (MainActivity.yFormCorrection / 200 + 1),
                                                (MainActivity.xArray[x][y] * (MainActivity.xFormCorrection / 100)) + (MainActivity.xFormCorrection / 200 + 1),
                                                (MainActivity.yArray[x][y] * (MainActivity.yFormCorrection / 100)) + (MainActivity.yFormCorrection / 200 + 1), mPaint);
                                }
                            }





                    }
                    DrawThread.newRunThread=false;
                } catch (Exception e){
                    DrawThread.newRunThread=true;
                }
                finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}

// Один из лучших алгоритмов генерации лабиритна- алгоритм Эллерса
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
                MainActivity.closedArray[i+1][j+3]=true;
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
