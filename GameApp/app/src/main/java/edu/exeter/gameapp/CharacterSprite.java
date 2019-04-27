package edu.exeter.gameapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite {
    private Bitmap image;
    private int x,y;
    private int xVelocity = 10;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public CharacterSprite(Bitmap bmp, int xCord, int yCord, int xVel, int yVel) {
        image = bmp;
        x = xCord;
        y = yCord;
        xVelocity = xVel;
        yVelocity = yVel;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHeight(){
        return image.getHeight();
    }

    public int getWidth(){
        return image.getWidth();
    }

    public void setX(int xCord) {
        x = xCord;
    }

    public void setY(int yCord){
        y = yCord;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

    }

    public void update() {
        x += xVelocity;
        y += yVelocity;
        if ((x > screenWidth - image.getWidth()) || (x < 0)) {
            xVelocity = xVelocity * -1;
        }
        if ((y > screenHeight - image.getHeight()) || (y < 0)) {
            yVelocity = yVelocity * -1;
        }

    }
}
