package com.example.tdpbreakout;

import android.graphics.Bitmap;

public abstract class Entity {

    protected Bitmap sprite;
    protected float x, y;
    protected int width, height;

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Bitmap getSprite() {
        return sprite;
    }

}
