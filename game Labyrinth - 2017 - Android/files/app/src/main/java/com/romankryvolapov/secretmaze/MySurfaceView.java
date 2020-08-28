package com.romankryvolapov.secretmaze;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;

    public void update() {
        drawThread = null;
        drawThread = new DrawThread(getHolder(), getResources());
        drawThread.start();
        try {
            drawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        drawThread = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

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
        setWillNotDraw(false);
        drawThread = null;
        drawThread = new DrawThread(getHolder(), getResources());
        drawThread.start();
        try {
            drawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        drawThread = null;

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }
}
