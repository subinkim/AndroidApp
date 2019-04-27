package edu.exeter.gameapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.MotionEvent;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private Main_Thread thread;
    private CharacterSprite characterSprite;
    private CharacterSprite characterSprite2;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new Main_Thread(getHolder(), this);

        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        int action  = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            characterSprite.setX(touchX-characterSprite.getWidth()/2);
            characterSprite.setY(touchY-characterSprite.getHeight()/2);
        }
        return true;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.avdgreen), 100, 100, 10, 5);
        //characterSprite2 = new CharacterSprite(BitmapFactory.decodeResource(getResources(), R.drawable.avdyellow), 350, 500, 20, 10);

        thread.setRunning(true);
        thread.start();

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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

    public void update() {
        characterSprite.update();
        //characterSprite2.update();
    }

//    public boolean touch(CharacterSprite one, CharacterSprite two){
//
//        if (one.getY() + one.getHeight()/2 < two.getY() - two.getHeight()/2) return false;
//        else if (one.getY() - one.getHeight()/2 > two.getY() + two.getHeight()/2) return false;
//        else if (one.getX() + one.getWidth()/2 < two.getX() - two.getWidth()/2) return false;
//        else if (one.getX() - one.getWidth()/2 > two.getX() + two.getHeight()/2) return false;
//        else return true;
//    }

    @Override
    public void draw(Canvas canvas)
    {

        super.draw(canvas);
        if (canvas != null){
            //if (touch(characterSprite, characterSprite2)) canvas.drawColor(Color.RED);
            if (characterSprite.getY() < Resources.getSystem().getDisplayMetrics().heightPixels/2 -characterSprite.getHeight()/2)
                canvas.drawColor(Color.WHITE);
            else canvas.drawColor(Color.BLACK);
            characterSprite.draw(canvas);
            //characterSprite2.draw(canvas);
        }
    }
}