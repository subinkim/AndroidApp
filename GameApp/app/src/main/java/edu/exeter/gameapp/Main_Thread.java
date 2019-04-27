package edu.exeter.gameapp;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

public class Main_Thread extends Thread {
    private GameView gameView;
    private SurfaceHolder surfaceHolder;
    private boolean running;
    public static Canvas canvas;
    private int targetFPS = 30;
    private double averageFPS;


    public Main_Thread(SurfaceHolder surfaceHolder, GameView gameView) {


        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

    }

    @Override
    public void run()
    {

        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/targetFPS;


        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == targetFPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
                System.out.println(averageFPS);
            }

        }

    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}