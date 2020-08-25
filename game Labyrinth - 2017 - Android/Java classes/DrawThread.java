package com.romankryvolapov.secretmaze;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

class DrawThread extends Thread {
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private Paint paint = new Paint();

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources) {
        this.surfaceHolder = surfaceHolder;
    }

//    public void setRunning(boolean run) {
//        runFlag = run;
//    }

    @Override
    public void run() {
        Log.i("status", "DrawThread.run()");
        Canvas canvas = null;
        MainActivity.startXcoord = (MainActivity.layoutWidth - (MainActivity.sectionSize * MainActivity.SizeInSectionsForRender)) / 2;
        MainActivity.startYcoord = (MainActivity.layoutHeight - (MainActivity.sectionSize * MainActivity.SizeInSectionsForRender)) / 2;
        Log.i("status", "startXcoord - " + MainActivity.startXcoord);
        Log.i("status", "startYcoord - " + MainActivity.startYcoord);
//        while (runFlag) {
//            Log.i("status", "runFlag = true");
//            runFlag = false;
//            Log.i("status", "runFlag = false");
//        }
        try {
            // получаем объект Canvas и выполняем отрисовку
            canvas = surfaceHolder.lockCanvas(null);
            synchronized (surfaceHolder) {
                // рисовать здесь
                Log.i("status", "canvas started");
                canvas.drawColor(Color.WHITE);
//                canvas.drawCircle(100, 100, 100, paint);

                boolean haveNoWalls = false;
                int xFirstOpen = MainActivity.SizeInSectionsForRender / 2;
                int yFirstOpen = MainActivity.SizeInSectionsForRender / 2;
                while (!haveNoWalls) {
                    if (!MainActivity.wallsArray[xFirstOpen][yFirstOpen]) {
                        MainActivity.openedArray[xFirstOpen][yFirstOpen] = true;
                        haveNoWalls = true;
                    } else yFirstOpen--;
                }

                if (MainActivity.win)
                    canvas.drawColor(0xFF00FF00);
                else
                    canvas.drawColor(0xFFFFFFFF);

                for (int i = 0; i < MainActivity.SizeInSectionsForRender; i++) {
                    for (int j = 0; j < MainActivity.SizeInSectionsForRender; j++) {
                        if (MainActivity.wallsArray[j][i]) {
                            paint.setColor(0xFF333333);
                            canvas.drawRect(
                                    MainActivity.startXcoord + MainActivity.sectionSize * j,
                                    MainActivity.startYcoord + MainActivity.sectionSize * i,
                                    MainActivity.startXcoord + MainActivity.sectionSize * j + MainActivity.sectionSize,
                                    MainActivity.startYcoord + MainActivity.sectionSize * i + MainActivity.sectionSize, paint
                            );
                        } else if (MainActivity.openedArray[j][i]) {
                            paint.setColor(0xFFFF0000);
                            canvas.drawRect(
                                    MainActivity.startXcoord + MainActivity.sectionSize * j,
                                    MainActivity.startYcoord + MainActivity.sectionSize * i,
                                    MainActivity.startXcoord + MainActivity.sectionSize * j + MainActivity.sectionSize,
                                    MainActivity.startYcoord + MainActivity.sectionSize * i + MainActivity.sectionSize, paint
                            );


                        }
                    }
                }


            }
        } finally {
            if (canvas != null) {
                // отрисовка выполнена. выводим результат на экран
                Log.i("status", "canvas finally");
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
