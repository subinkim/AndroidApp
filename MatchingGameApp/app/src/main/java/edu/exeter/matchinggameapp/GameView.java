package edu.exeter.matchinggameapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;
import static java.lang.Thread.sleep;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    int[] pictures = {R.drawable.apple, R.drawable.avocado, R.drawable.cherry, R.drawable.egg, R.drawable.kiwi, R.drawable.lemon, R.drawable.orange, R.drawable.tomato};
    CharacterSprite[] characterSprites = new CharacterSprite[16];
    CharacterSprite[] characterCover = new CharacterSprite[16];
    int[] rand;
    int screenWidth;
    int screenHeight;
    Main_Thread thread;
    int flips = 0;
    int firstLoc;
    int first;
    int second;
    float startingTime;
    int score;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new Main_Thread(getHolder(), this);

        setFocusable(true);

        rand = generator();
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    }

    public int[] generator() {
        int [] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        for (int i = 0; i < numbers.length; i++) {
            int pos = r.nextInt(numbers.length);
            int tmp = numbers[i];
            numbers[i] = numbers[pos];
            numbers[pos] = tmp;
        }

        return numbers;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            for (int i = 0; i < 16; i++){
                int xMin = characterCover[i].getX();
                int xMax = xMin + characterCover[i].getWidth();
                int yMin = characterCover[i].getY();
                int yMax = yMin + characterCover[i].getHeight();
                if (touchX >=  xMin && touchX <=  xMax && touchY >= yMin && touchY <= yMax){
                    characterCover[i] = characterSprites[i];
                    if (flips % 2 == 1){
                        second = rand[i];
                        if(first  % 8 != second % 8) {
                            float startTime = System.nanoTime();
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            int x = characterCover[firstLoc].getX();
                            int y = characterCover[firstLoc].getY();
                            characterCover[firstLoc] = new CharacterSprite(BitmapFactory.decodeResource(getResources(), R.drawable.card), x, y, firstLoc);
                            characterCover[i] = new CharacterSprite(BitmapFactory.decodeResource(getResources(), R.drawable.card), characterCover[i].getX(), characterCover[i].getY(), i);
                        } else score++;
                    } else {
                        firstLoc = i;
                        first = rand[i];
                    }
                    flips++;
                }
            }
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        for (int i = 0; i < characterSprites.length; i++){
            characterSprites[i] = new CharacterSprite(BitmapFactory.decodeResource(getResources(),pictures[rand[i]%8]), 10+(i%4)*(screenWidth/4), 200+(i/4)*(screenHeight/4-100), i);
            characterCover[i] = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.card), 10+(i%4)*(screenWidth/4), 200+(i/4)*(screenHeight/4-100), i);
        }

        thread.setRunning(true);
        thread.start();
        startingTime = System.nanoTime();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null){
            for(int i = 0; i < 16; i++){
                characterCover[i].draw(canvas);
            }
        }
    }

    public void drawText (Canvas canvas){
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        float timeL = (System.nanoTime() -  startingTime)/1000000000;
        int timeLeft = 30-(int)timeL;
        String time= ""+ timeLeft;
        if (timeLeft <= 0 || score == 8){
            if (score ==  8) canvas.drawText("You Win!", 50,100, textPaint);
            else canvas.drawText("You Lose!",50, 100, textPaint);
            textPaint.setTextSize(100);
            textPaint.setColor(Color.RED);
            thread.setRunning(false);
            thread.stop();
        } else {
            canvas.drawText("Time:" + time, 50, 100, textPaint);
        }

    }
}
