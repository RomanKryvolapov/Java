package com.romankryvolapov.secretmaze;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import androidx.core.content.ContextCompat;

class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Canvas canvas;

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources) {
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {

        MainActivity.startXcoord = (MainActivity.layoutWidth - (MainActivity.sectionSize * MainActivity.SizeInSectionsForRender)) / 2;
        MainActivity.startYcoord = (MainActivity.layoutHeight - (MainActivity.sectionSize * MainActivity.SizeInSectionsForRender)) / 2;
        try {

            canvas = surfaceHolder.lockCanvas(null);
            paint = new Paint();
            synchronized (surfaceHolder) {


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
                    canvas.drawColor(MainActivity.colorCancasBackgroundWin);
                else
                    canvas.drawColor(MainActivity.colorCancasBackground);

                for (int i = 0; i < MainActivity.SizeInSectionsForRender; i++) {
                    for (int j = 0; j < MainActivity.SizeInSectionsForRender; j++) {
                        if (MainActivity.wallsArray[j][i]) {
                            // стены
                            paint.setColor(MainActivity.colorWalls);
                            canvas.drawRect(
                                    MainActivity.startXcoord + MainActivity.sectionSize * j,
                                    MainActivity.startYcoord + MainActivity.sectionSize * i,
                                    MainActivity.startXcoord + MainActivity.sectionSize * j + MainActivity.sectionSize,
                                    MainActivity.startYcoord + MainActivity.sectionSize * i + MainActivity.sectionSize, paint
                            );
                        } else if (MainActivity.openedArray[j][i]) {
                            // прохождение
                            paint.setColor(MainActivity.colorGame);
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
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            canvas = null;
            paint = null;
            surfaceHolder = null;
        }
    }
}
