package edu.exeter.matchinggameapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite {
    private Bitmap image;
    private int x,y;
    private int id;

    public CharacterSprite(Bitmap bmp, int xCord, int yCord, int ID) {
        image = bmp;
        x = xCord;
        y = yCord;
        id = ID;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

    }
}
